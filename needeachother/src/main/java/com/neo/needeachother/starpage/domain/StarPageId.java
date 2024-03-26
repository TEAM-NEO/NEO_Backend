package com.neo.needeachother.starpage.domain;


import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class StarPageId implements Serializable {
    String value;
}
