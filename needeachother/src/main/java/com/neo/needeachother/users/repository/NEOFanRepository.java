package com.neo.needeachother.users.repository;

import com.neo.needeachother.users.entity.NEOFanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NEOFanRepository extends JpaRepository<NEOFanEntity, Long> {
    Optional<NEOFanEntity> findByUserID(String userID);
}
