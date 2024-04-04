package com.neo.needeachother.post.domain;

import com.neo.needeachother.category.domain.ContentType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "star_page_album_post")
@DiscriminatorValue(value = ContentType.TypeCode.ALBUM)
public class AlbumPost extends StarPagePost{
}
