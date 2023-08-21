package com.neo.needeachother.users.repository;

import com.neo.needeachother.users.document.NEOStarInfoDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface NEOStarCustomInfoRepository extends MongoRepository<NEOStarInfoDocument, String> {

    List<NEOStarInfoDocument> findAllByUserID(String userID);

    Optional<NEOStarInfoDocument> findByUserID(String userID);
}
