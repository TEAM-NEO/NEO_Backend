package com.neo.needeachother.post.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<StarPagePost, Long>, PostCustomRepository {
}
