package com.neo.needeachother.starpage.domain.repository;

import com.neo.needeachother.starpage.domain.StarPage;
import com.neo.needeachother.starpage.domain.StarPageId;
import com.neo.needeachother.starpage.domain.dto.HeadLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface StarPageRepository extends JpaRepository<StarPage, StarPageId>, StarPageRepositoryCustom {
}
