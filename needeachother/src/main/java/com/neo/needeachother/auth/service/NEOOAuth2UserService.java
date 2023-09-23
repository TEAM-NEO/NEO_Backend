package com.neo.needeachother.auth.service;

import com.neo.needeachother.auth.dto.NEOCustomOAuth2User;
import com.neo.needeachother.auth.dto.NEOOAuth2AttributesDTO;
import com.neo.needeachother.auth.enums.NEOOAuth2ProviderType;
import com.neo.needeachother.common.exception.NEOUnexpectedException;
import com.neo.needeachother.users.entity.NEOUserEntity;
import com.neo.needeachother.users.repository.NEOUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class NEOOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final NEOUserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("CustomOAuth2UserService.loadUser() 실행 - OAuth2 로그인 요청 진입");

        /**
         * DefaultOAuth2UserService 객체를 생성하여, loadUser(userRequest)를 통해 DefaultOAuth2User 객체를 생성 후 반환
         * DefaultOAuth2UserService의 loadUser()는 소셜 로그인 API의 사용자 정보 제공 URI로 요청을 보내서
         * 사용자 정보를 얻은 후, 이를 통해 DefaultOAuth2User 객체를 생성 후 반환한다.
         * 결과적으로, OAuth2User는 OAuth 서비스에서 가져온 유저 정보를 담고 있는 유저
         */
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        /**
         * userRequest에서 registrationId 추출 후 registrationId으로 SocialType 저장
         * http://localhost:8080/oauth2/authorization/kakao에서 kakao가 registrationId
         * userNameAttributeName은 이후에 nameAttributeKey로 설정된다.
         */
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        NEOOAuth2ProviderType providerType = NEOOAuth2ProviderType.ofRegistrationId(registrationId);
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName(); // OAuth2 로그인 시 키(PK)가 되는 값
        Map<String, Object> attributes = oAuth2User.getAttributes(); // 소셜 로그인에서 API가 제공하는 userInfo의 Json 값(유저 정보들)

        // socialType 에 따라 유저 정보를 통해 OAuthAttributes 객체 생성
        NEOOAuth2AttributesDTO extractAttributes = NEOOAuth2AttributesDTO.of(providerType, userNameAttributeName, attributes);

        NEOUserEntity createdUser = getUser(extractAttributes, providerType); // getUser() 메소드로 User 객체 생성 후 반환

        // DefaultOAuth2User를 구현한 CustomOAuth2User 객체를 생성해서 반환
        return new NEOCustomOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(createdUser.getUserType().getKey())),
                attributes,
                extractAttributes.getNameAttributeKey(),
                createdUser.getEmail(),
                createdUser.getUserType(),
                Optional.ofNullable(createdUser.getUserName()).orElse(createdUser.getEmail()),
                createdUser.getPhoneNumber(),
                createdUser.getGender()
        );
    }

    /**
     * SocialType과 attributes에 들어있는 소셜 로그인의 식별값 id를 통해 회원을 찾아 반환하는 메소드
     * 만약 찾은 회원이 있다면, 그대로 반환하고 없다면 saveUser()를 호출하여 회원을 저장한다.
     */
    private NEOUserEntity getUser(NEOOAuth2AttributesDTO attributes, NEOOAuth2ProviderType providerType) {
        NEOUserEntity foundUser;

        try {
            foundUser = userRepository.findByProviderTypeAndSocialID(providerType,
                            attributes.getOauth2UserInfo().getId())
                    .orElseGet(() -> saveUser(attributes, providerType));
        } catch (Exception e){
            throw new NEOUnexpectedException("OAuth 진행 과정 중, DB 에러가 발생했습니다. 서버 관린자에게 문의하세요.");
        }

        return foundUser;
    }

    /**
     * {@code NEOOAuth2AttributesDTO}의 {@code toEntity()} 메소드를 통해 빌더로 User 객체 생성 후 반환
     * providerType, socialId, email, role, gender, phoneNumber, name 중 프로바이더가 제공하는 값만 있는 상태로 DB에 저장합니다.
     */
    private NEOUserEntity saveUser(NEOOAuth2AttributesDTO attributes, NEOOAuth2ProviderType socialType) {
        NEOUserEntity createdUser = attributes.toEntity(socialType, attributes.getOauth2UserInfo());
        return userRepository.save(createdUser);
    }

}
