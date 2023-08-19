package com.neo.needeachother.users.service;

import com.neo.needeachother.common.enums.NEOErrorCode;
import com.neo.needeachother.common.enums.NEOResponseCode;
import com.neo.needeachother.common.response.NEOResponseBody;
import com.neo.needeachother.users.controller.NEOUserInformationController;
import com.neo.needeachother.users.document.NEOStarInfoDocument;
import com.neo.needeachother.users.entity.NEOFanEntity;
import com.neo.needeachother.users.entity.NEOStarEntity;
import com.neo.needeachother.users.entity.NEOStarTypeEntity;
import com.neo.needeachother.users.entity.NEOUserRelationEntity;
import com.neo.needeachother.users.enums.NEOStarDetailClassification;
import com.neo.needeachother.users.exception.NEOUserExpectedException;
import com.neo.needeachother.users.repository.*;
import com.neo.needeachother.users.request.NEOCreateFanInfoRequest;
import com.neo.needeachother.users.request.NEOCreateStarInfoRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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


    @Override
    public ResponseEntity<NEOResponseBody> doCreateNewStarInformationOrder(final NEOCreateStarInfoRequest createStarInfoRequest, final NEOUserInformationController.NEOUserOrder userOrder) {
        // 1. OAuth를 통해 가입한 엔티티를, request의 userID를 통해 찾아낸다. => 도입 이전까지 우선은 해당 파트 없이, 아예 새로 엔티티를 생성하는 방향으로 구현.
        // 2. DTO를 MySQL과 MongoDB에 들어갈 DTO 혹은 Entity로 분리한다.
        // 3. 각자 맞는 데이터베이스에 삽입한다.

        log.info("request 값 체크 : " + createStarInfoRequest.toString());
        Set<NEOStarDetailClassification> starClassificationSet = createStarInfoRequest.getStarClassificationSet();

        if (starClassificationSet.isEmpty()) {
            throw new NEOUserExpectedException(NEOErrorCode.BLANK_VALUE, "star_classification_list : null or blank", userOrder);
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
                .body(NEOResponseBody.builder()
                        .responseCode(NEOResponseCode.SUCCESS)
                        .msg(userOrder.getSuccessMessage())
                        .requestedPath(userOrder.getRequestedPath())
                        .build());
    }

    @Override
    public ResponseEntity<NEOResponseBody> doCreateNewFanInformationOrder(final NEOCreateFanInfoRequest createFanInfoRequest, final NEOUserInformationController.NEOUserOrder userOrder){
        // 1. OAuth를 통해 가입한 엔티티를, request의 userID를 통해 찾아낸다. => 도입 이전까지는 우선 해당 파트 없이 아예 새로운 엔티티를 만드는 방법으로 구현.
        // 2. 들어온 요청을 DTO로 변환후, 엔티티를 업데이트 한다. (여기서는 생성)
        // 3. 들어온 요청의 favoriteStarID를 기반으로 존재하는 스타를 탐색한 후, 해당 엔티티를 찾아온다.
        // 4. 해당 엔티티와 연관관계를 맺고 삽입.

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
                .body(NEOResponseBody.builder()
                        .responseCode(NEOResponseCode.SUCCESS)
                        .msg(userOrder.getSuccessMessage())
                        .requestedPath(userOrder.getRequestedPath())
                        .build());
    }

    @Override
    public ResponseEntity<?> doGetUserInformationOrder(String userID, NEOUserInformationController.NEOUserOrder userOrder) {
        if(userID.isEmpty()){
            throw new NEOUserExpectedException(NEOErrorCode.BLANK_VALUE, "user_id : null or blank", userOrder);
        }

        userRepository.findByUserID(userID);

        return null;
    }

    @Override
    public ResponseEntity<?> doGetPublicUserInformationOrder(String userID, NEOUserInformationController.NEOUserOrder userOrder) {
        return null;
    }
}
