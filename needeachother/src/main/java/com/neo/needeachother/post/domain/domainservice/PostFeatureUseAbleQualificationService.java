package com.neo.needeachother.post.domain.domainservice;

import com.neo.needeachother.category.domain.CategoryId;
import com.neo.needeachother.category.domain.repository.CategoryRepository;
import com.neo.needeachother.starpage.domain.repository.StarPageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostFeatureUseAbleQualificationService {

    private CategoryRepository categoryRepository;
    private StarPageRepository starPageRepository;

    public void canUseLikeFeature(CategoryId categoryId){
        // 1. 카테고리 아이디 기반 -> 스타페이지 탐색
        // 2. 스타페이지 -> 호스트 정보 획득
        // 3. 호스트 정보를 기반으로 팔로우 도메인의 호스트 팔로우 정보 대조.
        // 4. 팔로우 정보에 속해있으면 가능.
        // 3번에 대한 도메인 서비스기능은 어디에 속해야 하는가? 팔로우 여부 확인????
    }

    public void canUseHostHeartFeature(){

    }
}
