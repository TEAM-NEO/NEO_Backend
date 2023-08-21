package com.neo.needeachother.users.entity;

import com.neo.needeachother.users.converter.NEOStarDetailClassificationConverter;
import com.neo.needeachother.users.enums.NEOStarDetailClassification;
import jakarta.persistence.*;
import lombok.*;

/**
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
    @JoinColumn(name = "user_id")
    private NEOStarEntity neoStar;

    /* 스타 유형 */
    @Setter
    @Column(name = "star_type")
    @Convert(converter = NEOStarDetailClassificationConverter.class)
    private NEOStarDetailClassification starType;

    public NEOStarTypeEntity setNeoStar(NEOStarEntity star){
        if(this.neoStar != null){
            this.neoStar.getStarTypeList().remove(this);
        }
        this.neoStar = star;
        star.getStarTypeList().add(this);
        return this;
    }

}
