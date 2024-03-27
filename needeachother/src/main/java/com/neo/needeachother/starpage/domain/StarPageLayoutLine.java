package com.neo.needeachother.starpage.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "neo_starpage_layout")
@DiscriminatorColumn(name = "layout_type")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class StarPageLayoutLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long layoutId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "layout_title"))
    private LayoutTitle layoutTitle;

    @Column(name = "layout_type")
    @Enumerated(EnumType.STRING)
    private StarPageLayoutType type;

    public StarPageLayoutLine(LayoutTitle layoutTitle, StarPageLayoutType type){
        this.layoutTitle = layoutTitle;
        this.type = type;
    }

    public abstract boolean isRemoveAble();
}
