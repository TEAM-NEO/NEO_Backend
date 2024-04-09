package com.neo.needeachother.starpage.domain.domainservice;

import com.neo.needeachother.starpage.domain.StarPageId;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StarPageIdGenerateService {
    public StarPageId getNextId(){
        return new StarPageId("SP_" + UUID.randomUUID().toString().toLowerCase());
    }
}
