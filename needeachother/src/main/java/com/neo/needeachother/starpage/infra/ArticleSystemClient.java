package com.neo.needeachother.starpage.infra;

import com.neo.needeachother.starpage.domain.Article;
import com.neo.needeachother.starpage.domain.domainservice.StarPageExposureContentService;

import java.util.List;

public class ArticleSystemClient implements StarPageExposureContentService {

    @Override
    public List<Article> getExposureContentByLayoutId(Long layoutId) {
        return null;
    }

}
