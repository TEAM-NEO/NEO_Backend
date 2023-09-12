package com.neo.needeachother.users.entity;

import com.neo.needeachother.users.converter.NEOStarDetailClassificationConverter;
import com.neo.needeachother.users.enums.NEOStarDetailClassification;
import jakarta.persistence.*;
import lombok.*;

/**
 * @author 이승훈<br>
 * @since 23.08.21<br>
 * 스타의 여러 유형에 대한 엔티티입니다. <br>
 * {@code NEOStarEntity} 엔티티와 1:N 관계를 갖고, 한 스타에 대해 여러 스타 유형을 갖게 합니다. <br>
 */
@Entity
@Builder
@Getter
@Table(name = "star_type")
@NoArgsConstructor
@AllArgsConstructor
public class NEOStarTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* 스타 FK */
    @ManyToOne
    @JoinColumn(name = "star_id")
    private NEOStarEntity neoStar;

    /* 스타 유형 */
    @Column(name = "star_type")
    @Convert(converter = NEOStarDetailClassificationConverter.class)
    private NEOStarDetailClassification starType;

    /**
     * {@code NEOStarEntity}와 연관관계를 설정하는 컨비니언스 메서드입니다.
     * @param star 스타
     * @return {@code NEOStarTypeEntity} 스타 구분
     */
    public NEOStarTypeEntity setNeoStar(NEOStarEntity star){
        if(this.neoStar != null){
            this.neoStar.getStarTypeList().remove(this);
        }
        this.neoStar = star;
        star.getStarTypeList().add(this);
        return this;
    }

}
