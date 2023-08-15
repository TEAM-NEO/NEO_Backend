package com.neo.needeachother.users.entity;

import com.neo.needeachother.users.converter.NEOStarDetailClassificationConverter;
import com.neo.needeachother.users.enums.NEOStarDetailClassification;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Table(name = "star_type")
@NoArgsConstructor
@AllArgsConstructor
public class NEOStarTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private NEOStarEntity neoStar;

    @Setter
    @Column(name = "star_type")
    @Convert(converter = NEOStarDetailClassificationConverter.class)
    private NEOStarDetailClassification starType;

    public NEOStarTypeEntity setNeoStar(NEOStarEntity star){
        this.neoStar = star;
        star.getStarTypeList().add(this);
        return this;
    }

}
