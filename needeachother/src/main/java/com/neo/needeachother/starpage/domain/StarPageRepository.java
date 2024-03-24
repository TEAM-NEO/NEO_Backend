package com.neo.needeachother.starpage.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StarPageRepository extends JpaRepository<Long, StarPage>, StarPageCustomRepository {
}
