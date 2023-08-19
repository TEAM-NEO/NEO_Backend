package com.neo.needeachother.users.repository;

import com.neo.needeachother.users.entity.NEOUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NEOUserRepository extends JpaRepository<NEOUserEntity, Long> {
    Optional<NEOUserEntity> findByUserID(String userID);
}
