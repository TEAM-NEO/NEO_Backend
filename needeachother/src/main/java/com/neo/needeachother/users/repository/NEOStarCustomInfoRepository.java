package com.neo.needeachother.users.repository;

import com.neo.needeachother.users.document.NEOStarInfoDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NEOStarCustomInfoRepository extends MongoRepository<NEOStarInfoDocument, String> {
}
