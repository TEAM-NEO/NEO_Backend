package com.neo.needeachother.post.domain;

import com.neo.needeachother.category.domain.ContentType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "star_page_vote_post")
@DiscriminatorValue(value = ContentType.TypeCode.VOTE)
public class VotePost extends StarPagePost {

}
