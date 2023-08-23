package com.neo.needeachother.users.service;

import com.neo.needeachother.common.enums.NEOErrorCode;
import com.neo.needeachother.common.enums.NEOResponseCode;
import com.neo.needeachother.common.exception.NEOUnexpectedException;
import com.neo.needeachother.common.response.NEOFinalErrorResponse;
import com.neo.needeachother.users.document.NEOStarInfoDocument;
import com.neo.needeachother.users.dto.*;
import com.neo.needeachother.users.entity.*;
import com.neo.needeachother.users.enums.NEOGenderType;
import com.neo.needeachother.users.enums.NEOStarDetailClassification;
import com.neo.needeachother.users.enums.NEOUserOrder;
import com.neo.needeachother.users.exception.NEOUserExpectedException;
import com.neo.needeachother.users.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NEOUserServiceImpl implements NEOUserInformationService {

    private final NEOStarRepository starRepository;
    private final NEOFanRepository fanRepository;
    private final NEOUserRepository userRepository;
    private final NEOStarTypeRepository starTypeRepository;
    private final NEOStarCustomInfoRepository starCustomInfoRepository;
    private final NEOUserRelationRepository userRelationRepository;


    /**
     * 새로운 스타 정보를 생성합니다.
     *
     * @param createStarInfoRequest 스타 정보 생성 요청
     * @param userOrder             요청 타입
     * @return {@code ResponseEntity<NEOFinalErrorResponse>} 요청 응답 결과
     */
    @Override
    public ResponseEntity<?> doCreateNewStarInformationOrder(final NEOStarInfoDto createStarInfoRequest, final NEOUserOrder userOrder) {
        // 1. OAuth를 통해 가입한 엔티티를, request의 userID를 통해 찾아낸다. => 도입 이전까지 우선은 해당 파트 없이, 아예 새로 엔티티를 생성하는 방향으로 구현.
        // 2. DTO를 MySQL과 MongoDB에 들어갈 DTO 혹은 Entity로 분리한다.
        // 3. 각자 맞는 데이터베이스에 삽입한다.

        log.info("request 값 체크 : " + createStarInfoRequest.toString());
        Set<NEOStarDetailClassification> starClassificationSet = createStarInfoRequest.getStarClassificationSet();

        if (createStarInfoRequest.getGender().equals(NEOGenderType.NONE)) {
            throw new NEOUserExpectedException(NEOErrorCode.INVALID_FORMAT_GENDER, "gender에 적합한 코드를 입력하세요. gender code API를 통해 확인할 수 있습니다.", userOrder);
        }

        if (starClassificationSet.isEmpty()) {
            throw new NEOUserExpectedException(NEOErrorCode.BLANK_VALUE, "star_classification_list : null or blank", userOrder);
        }

        if (starClassificationSet.contains(NEOStarDetailClassification.NONE)) {
            throw new NEOUserExpectedException(NEOErrorCode.INVALID_FORMAT_STAR_CLASSIFICATION, "스타 구분자에 적합한 코드를 입력하세요. 스타 구분자 API를 통해 확인할 수 있습니다.", userOrder);
        }

        NEOStarEntity createdStar = NEOStarEntity.fromRequest(createStarInfoRequest);
        NEOStarInfoDocument starCustomInfo = NEOStarInfoDocument.fromRequest(createStarInfoRequest);

        log.info("엔티티 매핑 : " + createdStar.toString());
        log.info("도큐멘트 매핑 : " + starCustomInfo.toString());

        NEOStarEntity savedStar = starRepository.save(createdStar);
        List<NEOStarTypeEntity> starTypeEntities = starClassificationSet.stream()
                .map(classification -> NEOStarTypeEntity.builder().starType(classification).build())
                .map(starTypeEntity -> starTypeEntity.setNeoStar(savedStar))
                .collect(Collectors.toList());
        starTypeRepository.saveAll(starTypeEntities);
        starCustomInfoRepository.save(starCustomInfo);


        return ResponseEntity
                .created(URI.create("/api/v1/users/" + savedStar.getUserID()))
                .headers(userOrder.renderHttpHeadersByUserOrderAndResponseCode(NEOResponseCode.SUCCESS))
                .body(null);
    }

    /**
     * 새로운 팬 정보를 생성합니다.
     *
     * @param createFanInfoRequest 팬 정보 생성 요청
     * @param userOrder            요청 타입
     * @return {@code ResponseEntity<NEOFinalErrorResponse>} 요청 응답 결과
     */
    @Override
    public ResponseEntity<?> doCreateNewFanInformationOrder(final NEOFanInfoDto createFanInfoRequest, final NEOUserOrder userOrder) {
        // 1. OAuth를 통해 가입한 엔티티를, request의 userID를 통해 찾아낸다. => 도입 이전까지는 우선 해당 파트 없이 아예 새로운 엔티티를 만드는 방법으로 구현.
        // 2. 들어온 요청을 DTO로 변환후, 엔티티를 업데이트 한다. (여기서는 생성)
        // 3. 들어온 요청의 favoriteStarID를 기반으로 존재하는 스타를 탐색한 후, 해당 엔티티를 찾아온다.
        // 4. 해당 엔티티와 연관관계를 맺고 삽입.

        if (createFanInfoRequest.getGender().equals(NEOGenderType.NONE)) {
            throw new NEOUserExpectedException(NEOErrorCode.INVALID_FORMAT_GENDER, "gender에 적합한 코드를 입력하세요. gender code API를 통해 확인할 수 있습니다.", userOrder);
        }

        NEOFanEntity createdFan = NEOFanEntity.fromRequest(createFanInfoRequest);
        NEOStarEntity favoriteStarOfFan = starRepository.findByUserID(createFanInfoRequest.getFavoriteStarID())
                .orElseThrow(() -> new NEOUserExpectedException(NEOErrorCode.NOT_EXIST_STAR_ID,
                        "에러 대상 : " + createFanInfoRequest.getFavoriteStarID(),
                        userOrder));

        NEOFanEntity savedFan = fanRepository.save(createdFan);
        NEOUserRelationEntity userRelation = new NEOUserRelationEntity();
        userRelation.makeRelationFanWithStar(savedFan, favoriteStarOfFan);
        userRelationRepository.save(userRelation);

        return ResponseEntity
                .created(URI.create("/api/v1/users/" + savedFan.getUserID()))
                .headers(userOrder.renderHttpHeadersByUserOrderAndResponseCode(NEOResponseCode.SUCCESS))
                .body(null);
    }

    /**
     * 사용자 정보를 얻어오는 메소드입니다.
     *
     * @param userID    사용자 아이디
     * @param userOrder 요청 타입
     * @return {@code ResponseEntity<NEOFinalErrorResponse<NEOStarInfoDto>>}, {@code ResponseEntity<NEOFinalErrorResponse<NEOFanInfoDto>>}
     */
    @Override
    public ResponseEntity<?> doGetUserInformationOrder(String userID, NEOUserOrder userOrder) {
        if (userID.isEmpty()) {
            throw new NEOUserExpectedException(NEOErrorCode.BLANK_VALUE, "user_id : null or blank", userOrder);
        }

        NEOUserEntity foundUser = userRepository.findByUserID(userID)
                .orElseThrow(() -> new NEOUserExpectedException(NEOErrorCode.NOT_EXIST_USER, "not exist this id : " + userID, userOrder));

        if (foundUser instanceof NEOStarEntity) {
            return renderStarUserInformation((NEOStarEntity) foundUser, userOrder);
        } else if (foundUser instanceof NEOFanEntity) {
            return renderFanUserInformation((NEOFanEntity) foundUser, userOrder);
        } else {
            throw new NEOUnexpectedException("찾아낸 유저가 star / fan 엔티티 어느쪽에도 속하지 않습니다.");
        }
    }

    /**
     * 스타 유저 정보를 렌더링하는 메소드입니다.
     *
     * @param star      스타
     * @param userOrder 요청 타입
     * @return {@code ResponseEntity<NEOFinalErrorResponse<NEOStarInfoDto>>} 유저 정보 요청에 대한 응답 결과
     */
    private ResponseEntity<NEOStarInfoDto> renderStarUserInformation(NEOStarEntity star, NEOUserOrder userOrder) {
        Optional<NEOStarInfoDocument> maybeStarCustomInfo = starCustomInfoRepository.findByUserID(star.getUserID());
        NEOStarInfoDto starDto = star.toDTO();
        if (maybeStarCustomInfo.isPresent()) {
            starDto = maybeStarCustomInfo.get().fetchDTO(starDto);
        }
        return ResponseEntity.ok()
                .headers(userOrder.renderHttpHeadersByUserOrderAndResponseCode(NEOResponseCode.SUCCESS))
                .body(starDto);
    }

    /**
     * 팬 유저 정보를 렌더링 하는 메소드입니다.
     *
     * @param fan       팬
     * @param userOrder 요청 타입
     * @return {@code ResponseEntity<NEOFinalErrorResponse<NEOFanInfoDto>>} 유저 정보 요청에 대한 응답 결과
     */
    private ResponseEntity<NEOFanInfoDto> renderFanUserInformation(NEOFanEntity fan, NEOUserOrder userOrder) {
        return ResponseEntity.ok()
                .headers(userOrder.renderHttpHeadersByUserOrderAndResponseCode(NEOResponseCode.SUCCESS))
                .body(fan.toDTO());
    }

    /**
     * 사용자 공개 정보를 얻어오는 메소드입니다.
     *
     * @param userID    사용자 아이디
     * @param userOrder 요청 타입
     * @return {@code ResponseEntity<NEOFinalErrorResponse<NEOPublicFanInfoDto>>}, {@code ResponseEntity<NEOFinalErrorResponse<NEOPublicStarInfoDto>>}
     */
    @Override
    public ResponseEntity<?> doGetPublicUserInformationOrder(String userID, NEOUserOrder userOrder) {
        if (userID.isEmpty()) {
            throw new NEOUserExpectedException(NEOErrorCode.BLANK_VALUE, "user_id : null or blank", userOrder);
        }

        NEOUserEntity foundUser = userRepository.findByUserID(userID)
                .orElseThrow(() -> new NEOUserExpectedException(NEOErrorCode.NOT_EXIST_USER, "not exist this id : " + userID, userOrder));

        if (foundUser instanceof NEOStarEntity) {
            return renderPublicStarUserInformation((NEOStarEntity) foundUser, userOrder);
        } else if (foundUser instanceof NEOFanEntity) {
            return renderPublicFanUserInformation((NEOFanEntity) foundUser, userOrder);
        } else {
            throw new NEOUnexpectedException("찾아낸 유저가 star / fan 엔티티 어느쪽에도 속하지 않습니다.");
        }
    }

    /**
     * 스타 공개 정보를 렌더링 하는 메소드입니다.
     *
     * @param star      스타
     * @param userOrder 요청 타입
     * @return {@code ResponseEntity<NEOFinalErrorResponse<NEOPublicStarInfoDto>>} 유저 정보 요청에 대한 응답 결과
     */
    private ResponseEntity<NEOPublicStarInfoDto> renderPublicStarUserInformation(NEOStarEntity star, NEOUserOrder userOrder) {
        Optional<NEOStarInfoDocument> maybeStarCustomInfo = starCustomInfoRepository.findByUserID(star.getUserID());
        NEOPublicStarInfoDto starDto = star.toPublicDto();
        if (maybeStarCustomInfo.isPresent()) {
            starDto = maybeStarCustomInfo.get().fetchPublicDTO(starDto);
        }
        return ResponseEntity.ok()
                .headers(userOrder.renderHttpHeadersByUserOrderAndResponseCode(NEOResponseCode.SUCCESS))
                .body(starDto);
    }

    /**
     * 팬 공개 정보를 렌더링 하는 메소드입니다.
     *
     * @param fan       팬
     * @param userOrder 요청 타입
     * @return {@code ResponseEntity<NEOFinalErrorResponse<NEOPublicFanInfoDto>>} 유저 정보 요청에 대한 응답 결과
     */
    private ResponseEntity<NEOPublicFanInfoDto> renderPublicFanUserInformation(NEOFanEntity fan, NEOUserOrder userOrder) {
        return ResponseEntity.ok()
                .headers(userOrder.renderHttpHeadersByUserOrderAndResponseCode(NEOResponseCode.SUCCESS))
                .body(fan.toPublicDTO());
    }

    @Override
    public ResponseEntity<?> doChangePartialInformationOrder(String userID, NEOUserOrder userOrder, NEOChangeableInfoDto changeInfoDto) {
        NEOUserEntity foundUser = userRepository.findByUserID(userID)
                .orElseThrow(() -> new NEOUserExpectedException(NEOErrorCode.NOT_EXIST_USER, "에러 대상 : " + userID, userOrder));

        if (foundUser instanceof NEOStarEntity) {
            NEOStarInfoDocument starDoc = starCustomInfoRepository.findByUserID(userID)
                    .orElseGet(() -> NEOStarInfoDocument.builder().userID(foundUser.getUserID()).build());
            return modifyStarDataFromDto((NEOStarEntity) foundUser, starDoc, userOrder, changeInfoDto);
        } else if (foundUser instanceof NEOFanEntity) {
            return modifyFanDataFromDto((NEOFanEntity) foundUser, userOrder, changeInfoDto);
        } else {
            throw new NEOUnexpectedException("찾아낸 유저가 star / fan 엔티티 어느쪽에도 속하지 않습니다.");
        }
    }

    @Override
    public ResponseEntity<?> doDeleteUserInformationOrder(final String userID, final NEOUserOrder userOrder) {
        NEOUserEntity foundUser = userRepository.findByUserID(userID)
                .orElseThrow(() -> new NEOUserExpectedException(NEOErrorCode.NOT_EXIST_USER, "에러 대상 : " + userID, userOrder));
        
        userRepository.delete(foundUser);
        return ResponseEntity.ok()
                .headers(userOrder.renderHttpHeadersByUserOrderAndResponseCode(NEOResponseCode.SUCCESS))
                .body(null);
    }

    private ResponseEntity<NEOStarInfoDto> modifyStarDataFromDto(NEOStarEntity star, NEOStarInfoDocument starDoc, NEOUserOrder userOrder, NEOChangeableInfoDto changeInfoDto) {
        if (changeInfoDto.getNeoNickName() != null) {
            star.setNeoNickName(changeInfoDto.getNeoNickName());
        }

        if (changeInfoDto.getStarNickName() != null) {
            star.setStarNickName(changeInfoDto.getStarNickName());
        }

        if (changeInfoDto.getIntroduction() != null) {
            starDoc.setIntroduction(changeInfoDto.getIntroduction());
        }

        if (changeInfoDto.getSubmittedUrl() != null) {
            starDoc.setSubmittedUrl(changeInfoDto.getSubmittedUrl());
        }

        if (changeInfoDto.getCustomIntroductionList() != null) {
            starDoc.setStarCustomIntroductionList(changeInfoDto.getCustomIntroductionList().stream()
                    .map(NEOStarInfoDocument.NEOCustomStarInformationDocument::fromDTO)
                    .collect(Collectors.toList()));
        }

        if (changeInfoDto.getStarClassificationSet() != null) {
            HashSet<NEOStarDetailClassification> alreadyExistClassificationSet = new HashSet<>();
            List<NEOStarTypeEntity> deleteTargetStarTypeList = new ArrayList<>();

            for (NEOStarTypeEntity starTypeEntity : star.getStarTypeList()) {
                if (changeInfoDto.getStarClassificationSet().contains(starTypeEntity.getStarType())) {
                    // 변경 집합에도 포함 (엔티티 삭제 X)
                    alreadyExistClassificationSet.add(starTypeEntity.getStarType());
                } else {
                    // 변경 집합에 미포함 (엔티티 삭제 O)
                    deleteTargetStarTypeList.add(starTypeEntity);
                    starTypeRepository.delete(starTypeEntity);
                }
            }
            star.getStarTypeList().removeAll(deleteTargetStarTypeList);

            List<NEOStarTypeEntity> newCreatedClassificationList = changeInfoDto.getStarClassificationSet().stream()
                    .filter(classification -> !alreadyExistClassificationSet.contains(classification))
                    .map(classification -> {
                        NEOStarTypeEntity newStarType = NEOStarTypeEntity.builder().starType(classification).build();
                        newStarType.setNeoStar(star);
                        return newStarType;
                    })
                    .collect(Collectors.toList());

            starTypeRepository.saveAll(newCreatedClassificationList);
        }

        NEOStarEntity savedStar = starRepository.save(star);
        NEOStarInfoDocument savedStarDoc = starCustomInfoRepository.save(starDoc);

        return ResponseEntity.ok()
                .headers(userOrder.renderHttpHeadersByUserOrderAndResponseCode(NEOResponseCode.SUCCESS))
                .body(savedStarDoc.fetchDTO(savedStar.toDTO()));
    }

    private ResponseEntity<NEOFanInfoDto> modifyFanDataFromDto(NEOFanEntity fan, NEOUserOrder userOrder, NEOChangeableInfoDto changeInfoDto) {
        String changedNeoNickName = Optional.ofNullable(changeInfoDto.getNeoNickName())
                .orElseThrow(() -> new NEOUserExpectedException(NEOErrorCode.BLANK_VALUE, "팬 정보 변경은, 닉네임 변경만 가능합니다. neo_nick_name 값이 null입니다.", userOrder));

        fan.setNeoNickName(changedNeoNickName);
        NEOFanEntity savedFan = fanRepository.save(fan);

        return ResponseEntity.ok()
                .headers(userOrder.renderHttpHeadersByUserOrderAndResponseCode(NEOResponseCode.SUCCESS))
                .body(savedFan.toDTO());
    }
}