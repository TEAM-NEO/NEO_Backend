package com.neo.needeachother.users.repository;

import com.neo.needeachother.users.entity.NEOStarEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NEOStarRepository extends JpaRepository<NEOStarEntity, Long> {
}
