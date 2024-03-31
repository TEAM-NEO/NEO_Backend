package com.neo.needeachother.starpage.domain.domainservice;

import com.neo.needeachother.starpage.domain.Article;

import java.util.List;

public interface StarPageExposureContentService {
    List<Article> getExposureContentByLayoutId(Long layoutId);
}
