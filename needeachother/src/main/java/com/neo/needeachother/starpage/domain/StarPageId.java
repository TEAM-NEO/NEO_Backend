package com.neo.needeachother.starpage.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
public class StarPageId implements Serializable {

    @Column(name = "star_page_id")
    String value;

}
