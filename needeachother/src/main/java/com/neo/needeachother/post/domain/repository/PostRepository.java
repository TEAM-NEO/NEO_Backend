package com.neo.needeachother.post.domain.repository;

import com.neo.needeachother.post.domain.CommonPost;
import com.neo.needeachother.post.domain.StarPagePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<StarPagePost, Long>, PostRepositoryCustom {
}
