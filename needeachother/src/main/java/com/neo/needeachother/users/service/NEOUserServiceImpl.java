package com.neo.needeachother.users.service;

import com.neo.needeachother.users.document.NEOStarInfoDocument;
import com.neo.needeachother.users.entity.NEOStarEntity;
import com.neo.needeachother.users.entity.NEOStarTypeEntity;
import com.neo.needeachother.users.enums.NEOStarDetailClassification;
import com.neo.needeachother.users.exception.NEOUserExpectedException;
import com.neo.needeachother.users.mapper.NEOStarMapper;
import com.neo.needeachother.users.repository.NEOFanRepository;
import com.neo.needeachother.users.repository.NEOStarCustomInfoRepository;
import com.neo.needeachother.users.repository.NEOStarRepository;
import com.neo.needeachother.users.repository.NEOStarTypeRepository;
import com.neo.needeachother.users.request.NEOCreateStarInfoRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NEOUserServiceImpl implements NEOUserInformationService {

    private final NEOStarRepository starRepository;

    private final NEOFanRepository fanRepository;

    private final NEOStarTypeRepository starTypeRepository;

    private final NEOStarCustomInfoRepository starCustomInfoRepository;

    private final NEOStarMapper starMapper;


    @Override
    public void handleCreateNewStarInformationOrder(NEOCreateStarInfoRequest createStarInfoRequest) {
        // 1. OAuth를 통해 가입한 엔티티를, request의 userID를 통해 찾아낸다. => 도입 이전까지 우선은 해당 파트 없이, 아예 새로 엔티티를 생성하는 방향으로 구현.
        // 2. DTO를 MySQL과 MongoDB에 들어갈 DTO 혹은 Entity로 분리한다.
        // 3. 각자 맞는 데이터베이스에 삽입한다.

        log.info("request 값 체크 : " + createStarInfoRequest.toString());
        Set<NEOStarDetailClassification> starClassificationSet = createStarInfoRequest.getStarClassificationSet();

        if (starClassificationSet.isEmpty()) {
            throw new NEOUserExpectedException("스타 구분을 선택하지 않음.");
        }

        if (starClassificationSet.contains(NEOStarDetailClassification.NONE)) {
            throw new NEOUserExpectedException("정상적이지 않은 스타 구분 코드가 포함되어 있음.");
        }

        NEOStarEntity createdStar = NEOStarMapper.INSTANCE.toStarEntity(createStarInfoRequest);
        NEOStarInfoDocument starCustomInfo = NEOStarMapper.INSTANCE.toStarInfoDocument(createStarInfoRequest);

        log.info("엔티티 매핑 : " + createdStar.toString());
        log.info("도큐멘트 매핑 : " + starCustomInfo.toString());

        NEOStarEntity savedStar = starRepository.save(createdStar);
        List<NEOStarTypeEntity> starTypeEntities = starClassificationSet.stream()
                .map(classification -> NEOStarTypeEntity.builder().starType(classification).build())
                .map(starTypeEntity -> starTypeEntity.setNeoStar(savedStar))
                .collect(Collectors.toList());
        starTypeRepository.saveAll(starTypeEntities);


//        for(NEOStarDetailClassification starClassification : starClassificationSet){
//            NEOStarTypeEntity starTypeEntity = NEOStarTypeEntity.builder().starType(starClassification).build();
//            starTypeEntity.setNeoStar(savedStar);
//            NEOStarTypeEntity savedStarTypeEntity = starTypeRepository.save(starTypeEntity);
//            log.info(savedStarTypeEntity.toString());
//        }

    }
}
