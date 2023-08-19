package com.neo.needeachother.users.repository;

import com.neo.needeachother.users.entity.NEOUserRelationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NEOUserRelationRepository extends JpaRepository<NEOUserRelationEntity, Long> {
}
