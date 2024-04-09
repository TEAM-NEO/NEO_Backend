package com.neo.needeachother.post.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Author {

    private String authorName;

    private String email;

    // 멤버 도메인 개편 이후 멤버 아이디 삽입.

    public static Author of(String authorName, String email){
        return new Author(authorName, email);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(email, author.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
