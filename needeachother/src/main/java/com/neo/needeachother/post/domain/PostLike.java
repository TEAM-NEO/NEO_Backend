package com.neo.needeachother.post.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLike {

    private String likerEmail;

    public static PostLike of(String likerEmail){
        return new PostLike(likerEmail);
    }
}
