package com.neo.needeachother.users.repository;

import com.neo.needeachother.auth.enums.NEOOAuth2ProviderType;
import com.neo.needeachother.users.entity.NEOUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NEOUserRepository extends JpaRepository<NEOUserEntity, Long> {
    Optional<NEOUserEntity> findByNeoID(String neoID);
    Optional<NEOUserEntity> findByProviderTypeAndSocialID(NEOOAuth2ProviderType providerType, String socialID);
    Optional<NEOUserEntity> findByEmail(String email);
}
