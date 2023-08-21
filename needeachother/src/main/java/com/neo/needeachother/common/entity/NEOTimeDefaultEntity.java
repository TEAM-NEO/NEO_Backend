package com.neo.needeachother.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * @author 이승훈<br>
 * @since 23.08.21<br>
 * NEO에서 사용하는 엔티티 중, 생성 및 수정시간 컬럼이 필요한 엔티티를 위한 기본 엔티티 <br>
 * {@code @MappedSuperclass}이기 때문에, {@code @Entity}가 포함된 객체에만 상속해서 사용합니다. <br>
 * 단독으로는 생성할 수 없고 해당 컬럼이 필요한 엔티티에 상속해서 사용합니다.
 */
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass // JPA Entity 클래스들이 이 클래스 상속 하면, 필드인 createdAt, modifiedAt을 인식.
@EntityListeners(AuditingEntityListener.class) // JPA Entity 에서 이벤트가 발생할 때마다 특정 로직을 실행 가능
public abstract class NEOTimeDefaultEntity {

    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

}