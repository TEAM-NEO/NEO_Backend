package com.neo.needeachother.starpage.domain;

import com.neo.needeachother.starpage.domain.dto.StarPageHeadLine;
import com.neo.needeachother.starpage.domain.repository.StarPageRepository;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Entity
@Table(name = "neo_starpage_layout")
@DiscriminatorColumn(name = "unique_or_categorical")
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

    public abstract List<? extends StarPageHeadLine> getHeadLineByLayout(StarPageId starPageId, StarPageRepository repo);

}
