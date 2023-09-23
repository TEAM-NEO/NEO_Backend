package com.neo.needeachother.auth.dto;

import com.neo.needeachother.users.enums.NEOGenderType;
import com.neo.needeachother.users.enums.NEOUserType;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Collection;
import java.util.Map;

/**
 * {@code DefaultOAuth2User}를 상속합니다.
 */
@Getter
public class NEOCustomOAuth2User extends DefaultOAuth2User {

    private String email;
    private NEOUserType role;
    private String name;
    private String phoneNumber;
    private NEOGenderType gender;

    /**
     * Constructs a {@code DefaultOAuth2User} using the provided parameters.
     *
     * @param authorities      the authorities granted to the user
     * @param attributes       the attributes about the user
     * @param nameAttributeKey the key used to access the user's &quot;name&quot; from
     *                         {@link #getAttributes()}
     */
    public NEOCustomOAuth2User(Collection<? extends GrantedAuthority> authorities,
                               Map<String, Object> attributes, String nameAttributeKey,
                               String email, NEOUserType role, String name, String phoneNumber, NEOGenderType gender) {
        super(authorities, attributes, nameAttributeKey);
        this.email = email;
        this.role = role;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }
}
