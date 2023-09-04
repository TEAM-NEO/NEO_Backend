package com.neo.needeachother.users.service;

import com.neo.needeachother.common.enums.NEOErrorCode;
import com.neo.needeachother.common.enums.NEOResponseCode;
import com.neo.needeachother.common.exception.NEOUnexpectedException;
import com.neo.needeachother.users.document.NEOStarInfoDocument;
import com.neo.needeachother.users.dto.*;
import com.neo.needeachother.users.entity.*;
import com.neo.needeachother.users.enums.NEOGenderType;
import com.neo.needeachother.users.enums.NEOStarDetailClassification;
import com.neo.needeachother.users.enums.NEOUserApiOrder;
import com.neo.needeachother.users.enums.NEOUserType;
import com.neo.needeachother.users.exception.NEOUserExpectedException;
import com.neo.needeachother.users.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public ResponseEntity<NEOUserInformationDTO> doCreateNewStarInformationOrder(final NEOAdditionalStarInfoRequest createStarInfoRequest, final NEOUserApiOrder userOrder) {
        // 추가 유효성 검사 및 스타 분류 집합 획득
        Set<NEOStarDetailClassification> starClassificationSet = validateAndGetStarClassification(createStarInfoRequest, userOrder);

        // 요청으로부터, 엔티티 생성
        NEOStarEntity createdStar = NEOStarEntity.fromRequest(createStarInfoRequest);
        NEOStarInfoDocument starCustomInfo = NEOStarInfoDocument.fromRequest(createStarInfoRequest);

        // 엔티티 저장 및 연관관계 설정, 영속성 전이 PERSIST
        starClassificationSet.stream()
                .map(classification -> NEOStarTypeEntity.builder().starType(classification).build())
                .forEach(createdStar::addStarType);

        // 스타 분류 연관관계와 함께 저장
        NEOStarEntity savedStar = saveStarWithInformationAndClassificationType(createdStar, userOrder);

        // 위키 및 소개글 MongoDB 별도 저장
        NEOStarInfoDocument savedStarWikiDoc = starCustomInfoRepository.save(starCustomInfo);

        // 최종 응답 생성 (생성된 사용자 정보)
        NEOUserInformationDTO createdUserInformation = NEOUserInformationDTO.from(savedStar, savedStarWikiDoc, true, true);

        return ResponseEntity
                .created(URI.create("/api/v1/users/" + savedStar.getUserID()))
                .body(createdUserInformation);
    }

    private NEOStarEntity saveStarWithInformationAndClassificationType(NEOStarEntity wantSaveStar, NEOUserApiOrder userOrder){
        try {
            return starRepository.save(wantSaveStar);
        } catch (DataIntegrityViolationException ex){
            if (ex.getCause().toString().contains("UNIQUE_ID_EMAIL_NAME_IDX")){
                throw new NEOUserExpectedException(NEOErrorCode.ALREADY_EXIST_USER, userOrder);
            }
            throw new NEOUnexpectedException("새로운 스타 추가 정보 입력 중 서버에 문제가 생겼습니다.");
        }
    }

    /**
     * 새로운 팬 정보를 생성합니다.
     *
     * @param createFanInfoRequest 팬 정보 생성 요청
     * @param userOrder            요청 타입
     * @return {@code ResponseEntity<NEOFinalErrorResponse>} 요청 응답 결과
     */
    @Override
    public ResponseEntity<NEOUserInformationDTO> doCreateNewFanInformationOrder(final NEOAdditionalFanInfoRequest createFanInfoRequest, final NEOUserApiOrder userOrder) {

        // 잘못된 성별 문자열이 요청으로 들어온 경우
        if (createFanInfoRequest.getGender().equals(NEOGenderType.NONE)) {
            throw new NEOUserExpectedException(NEOErrorCode.INVALID_FORMAT_GENDER, "gender에 적합한 코드를 입력하세요. gender code API를 통해 확인할 수 있습니다.", userOrder);
        }

        // 요청으로부터 엔티티 생성
        NEOFanEntity createdFan = NEOFanEntity.fromRequest(createFanInfoRequest);

        // 팔로우 관계를 맺을 스타 탐색 및 예외
        NEOStarEntity favoriteStarOfFan = starRepository.findByUserID(createFanInfoRequest.getFavoriteStarID())
                .orElseThrow(() -> new NEOUserExpectedException(NEOErrorCode.NOT_EXIST_STAR_ID,
                        "에러 대상 : " + createFanInfoRequest.getFavoriteStarID(),
                        userOrder));

        // 엔티티 저장 및 연관관계 설정 및 저장
        createdFan.subscribeNeoStar(favoriteStarOfFan);
        NEOFanEntity savedFan = saveFanWithInformationAndFavoriteStar(createdFan, userOrder);

        // 최종 응답 생성 (생성된 사용자 정보)
        NEOUserInformationDTO createdUserInformation = NEOUserInformationDTO.from(savedFan, true);

        return ResponseEntity
                .created(URI.create("/api/v1/users/" + savedFan.getUserID()))
                .body(createdUserInformation);
    }

    private NEOFanEntity saveFanWithInformationAndFavoriteStar(NEOFanEntity wantSaveFan, NEOUserApiOrder userOrder){
        try {
            return fanRepository.save(wantSaveFan);
        } catch (DataIntegrityViolationException ex){
            if (ex.getCause().toString().contains("UNIQUE_ID_EMAIL_NAME_IDX")){
                throw new NEOUserExpectedException(NEOErrorCode.ALREADY_EXIST_USER, userOrder);
            }
            throw new NEOUnexpectedException("새로운 팬 추가 정보 입력 중 서버에 문제가 생겼습니다.");
        }
    }

    @Override
    public ResponseEntity<NEOUserInformationDTO> doGetStarInformationOrder(String userID, boolean isPrivacy, boolean isDetail, NEOUserApiOrder userOrder) {

        // 스타 엔티티 획득
        NEOStarEntity foundStar = starRepository.findByUserID(userID)
                .orElseThrow(() -> new NEOUserExpectedException(NEOErrorCode.NOT_EXIST_USER, "not exist this star : " + userID, userOrder));

        // 스타 위키 획득
        NEOStarInfoDocument starCustomInfo = starCustomInfoRepository.findByUserID(foundStar.getUserID())
                .orElseGet(() -> createEmptyStarInfoDocument(userID));

        // 스타 정보 객체로 렌더링
        NEOUserInformationDTO foundStarInformation = NEOUserInformationDTO.from(foundStar, starCustomInfo, isPrivacy, isDetail);

        return ResponseEntity.ok()
                .body(foundStarInformation);
    }

    @Override
    public ResponseEntity<NEOUserInformationDTO> doGetFanInformationOrder(String userID, boolean isPrivacy, NEOUserApiOrder userOrder) {

        // 팬 엔티티 획득
        NEOFanEntity foundFan = fanRepository.findByUserID(userID)
                .orElseThrow(() -> new NEOUserExpectedException(NEOErrorCode.NOT_EXIST_USER, "not exist this fan : " + userID, userOrder));

        // 팬 정보 객체로 렌더링
        NEOUserInformationDTO foundFanInformation = NEOUserInformationDTO.from(foundFan, isPrivacy);

        return ResponseEntity.ok()
                .body(foundFanInformation);
    }

    @Override
    public ResponseEntity<NEOUserInformationDTO> doChangePartialInformationOrder(String userID, NEOUserApiOrder userOrder, NEOChangeableInfoDTO changeInfoDto) {

        // 요청과 매핑되는 유저 찾기
        NEOUserEntity foundUser = userRepository.findByUserID(userID)
                .orElseThrow(() -> new NEOUserExpectedException(NEOErrorCode.NOT_EXIST_USER, "에러 대상 : " + userID, userOrder));

        // 사용자 유형별 다른 처리
        switch(foundUser.getUserType()){
            case STAR:
                NEOStarInfoDocument starDoc = starCustomInfoRepository.findByUserID(userID)
                        .orElseGet(() -> NEOStarInfoDocument.builder().userID(foundUser.getUserID()).build());
                return modifyStarDataFromDto((NEOStarEntity) foundUser, starDoc, userOrder, changeInfoDto);
            case FAN:
                return modifyFanDataFromDto((NEOFanEntity) foundUser, userOrder, changeInfoDto);
            default:
                throw new NEOUnexpectedException("찾아낸 유저가 star / fan 엔티티 어느쪽에도 속하지 않습니다.");
        }

    }

    @Override
    public ResponseEntity<?> doDeleteUserInformationOrder(final String userID, final NEOUserApiOrder userOrder) {
        NEOUserEntity foundUser = userRepository.findByUserID(userID)
                .orElseThrow(() -> new NEOUserExpectedException(NEOErrorCode.NOT_EXIST_USER, "에러 대상 : " + userID, userOrder));

        userRepository.delete(foundUser);
        return ResponseEntity.ok()
                .body(null);
    }

    private Set<NEOStarDetailClassification> validateAndGetStarClassification(final NEOAdditionalStarInfoRequest createStarInfoRequest, final NEOUserApiOrder userOrder){
        Set<NEOStarDetailClassification> starClassificationSet = createStarInfoRequest.getStarClassificationSet();

        // 잘못된 성별 문자열이 요청으로 들어온 경우
        if (createStarInfoRequest.getGender().equals(NEOGenderType.NONE)) {
            throw new NEOUserExpectedException(NEOErrorCode.INVALID_FORMAT_GENDER, "gender에 적합한 코드를 입력하세요. gender code API를 통해 확인할 수 있습니다.", userOrder);
        }

        // 스타 분류를 설정하지 않은 요청
        if (starClassificationSet.isEmpty()) {
            throw new NEOUserExpectedException(NEOErrorCode.BLANK_VALUE, "star_classification_list : null or blank", userOrder);
        }

        // 스타 분류 설정 중 잘못된 코드 포함 요청
        if (starClassificationSet.contains(NEOStarDetailClassification.NONE)) {
            throw new NEOUserExpectedException(NEOErrorCode.INVALID_FORMAT_STAR_CLASSIFICATION, "스타 구분자에 적합한 코드를 입력하세요. 스타 구분자 API를 통해 확인할 수 있습니다.", userOrder);
        }
        return starClassificationSet;
    }


    @Transactional
    public NEOStarInfoDocument createEmptyStarInfoDocument(String starID){
        return starCustomInfoRepository.save(NEOStarInfoDocument.builder().userID(starID).build());
    }


    private void modifyStarClassificationFromDto(NEOStarEntity star, NEOChangeableInfoDTO changeInfoDto){
        // 변경사항 없다면, early return
        if (changeInfoDto.getStarClassificationSet() == null){
            return;
        }

        HashSet<NEOStarDetailClassification> alreadyExistClassificationSet = new HashSet<>();
        List<NEOStarTypeEntity> deleteTargetStarTypeList = new ArrayList<>();

        // 기존 스타가 가진 스타 분류와 변경 대상을 비교
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

        // 기존 분류에 없던 분류만 생성
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

    private ResponseEntity<NEOUserInformationDTO> modifyStarDataFromDto(NEOStarEntity star, NEOStarInfoDocument starDoc, NEOUserApiOrder userOrder, NEOChangeableInfoDTO changeInfoDto) {
        // 변경사항 검토 후 값 교체
        Optional.ofNullable(changeInfoDto.getNeoNickName()).ifPresent(star::setNeoNickName);
        Optional.ofNullable(changeInfoDto.getStarNickName()).ifPresent(star::setStarNickName);
        Optional.ofNullable(changeInfoDto.getIntroduction()).ifPresent(starDoc::setIntroduction);
        Optional.ofNullable(changeInfoDto.getSubmittedUrl()).ifPresent(starDoc::setSubmittedUrl);

        Optional.ofNullable(changeInfoDto.getCustomIntroductionList())
                .ifPresent(list -> starDoc.setStarCustomIntroductionList(list.stream()
                        .map(NEOStarInfoDocument.NEOCustomStarInformationDocument::fromDTO)
                        .toList()));

        // 스타 분류 변경 및 저장
        modifyStarClassificationFromDto(star, changeInfoDto);

        // 변경 내용 반영
        NEOStarEntity savedStar = starRepository.save(star);
        NEOStarInfoDocument savedStarDoc = starCustomInfoRepository.save(starDoc);

        return ResponseEntity.ok()
                .body(NEOUserInformationDTO.from(savedStar, savedStarDoc, true, true));
    }

    private ResponseEntity<NEOUserInformationDTO> modifyFanDataFromDto(NEOFanEntity fan, NEOUserApiOrder userOrder, NEOChangeableInfoDTO changeInfoDto) {
        String changedNeoNickName = Optional.ofNullable(changeInfoDto.getNeoNickName())
                .orElseThrow(() -> new NEOUserExpectedException(NEOErrorCode.BLANK_VALUE, "팬 정보 변경은, 닉네임 변경만 가능합니다. neo_nick_name 값이 null입니다.", userOrder));

        fan.setNeoNickName(changedNeoNickName);
        NEOFanEntity savedFan = fanRepository.save(fan);


        return ResponseEntity.ok()
                .body(NEOUserInformationDTO.from(savedFan, true));
    }
}