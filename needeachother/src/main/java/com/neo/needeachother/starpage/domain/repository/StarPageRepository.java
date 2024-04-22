package com.neo.needeachother.starpage.domain.repository;

import com.neo.needeachother.starpage.domain.StarPage;
import com.neo.needeachother.starpage.domain.StarPageId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface StarPageRepository extends JpaRepository<StarPage, StarPageId>, StarPageRepositoryCustom {
    Optional<StarPage> findByStarPageId(StarPageId starPageId);
}
