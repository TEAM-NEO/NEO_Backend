package com.neo.needeachother.post.infra;

import com.neo.needeachother.category.domain.Category;
import com.neo.needeachother.category.domain.CategoryId;
import com.neo.needeachother.category.domain.ContentRestriction;
import com.neo.needeachother.category.domain.repository.CategoryRepository;
import com.neo.needeachother.post.domain.*;
import com.neo.needeachother.post.domain.dto.PostSearchCondition;
import com.neo.needeachother.post.domain.dto.StarPagePostHeadlineDto;
import com.neo.needeachother.post.domain.repository.PostRepository;
import com.neo.needeachother.starpage.domain.SNSLine;
import com.neo.needeachother.starpage.domain.SNSType;
import com.neo.needeachother.starpage.domain.StarPage;
import com.neo.needeachother.starpage.domain.domainservice.CreateCategoryFromStarPageService;
import com.neo.needeachother.starpage.domain.domainservice.StarPageIdGenerateService;
import com.neo.needeachother.starpage.domain.repository.StarPageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;
import static com.neo.needeachother.post.application.PostServiceHelper.*;

import java.util.*;


@SpringBootTest
class PostRepositoryImplTest {

    @Autowired
    private StarPageRepository starPageRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private StarPageIdGenerateService idGenerateService;

    @Autowired
    private CreateCategoryFromStarPageService createCategoryFromStarPageService;

    @Test
    @Transactional
    public void testForSearchPostHeadlineByCategoryIdWithPaging() {
        StarPage dummyStarPage1 = starPageRepository.save(StarPage.create(idGenerateService.getNextId(), "박보영", "boyoung@naver.com", Set.of("ACTOR"),
                List.of(SNSLine.of(SNSType.YOUTUBE, "boyoung.youtube.com")), "보영"));

        Category category = categoryRepository.save(dummyStarPage1.createCommonTypeCategory(createCategoryFromStarPageService, "category1", ContentRestriction.of(false, false, false, 0)));

        List<StarPagePost> postList = new ArrayList<>();

        for(int i = 0; i<100; i++){
            postList.add(new CommonPost(category.getCategoryId(), String.valueOf(i), Author.of("hihi", "aaa@naver.com"), PostStatus.OPEN, new ArrayList<>()));
        }
        postRepository.saveAll(postList);

        System.out.println(postRepository.findAll().size());

        Page<StarPagePostHeadlineDto> result = postRepository.searchPostHeadlineByCategoryIdWithPaging(category.getCategoryId(),
                PostSearchCondition.builder()
                        .orderByCreatedAt(true)
                        .orderByLikeCount(false)
                        .onlySearchHostHearted(false)
                        .onlySearchHostWritten(false)
                        .build(),
                PageRequest.of(0, 20));

        Page<StarPagePostHeadlineDto> result2 = postRepository.searchPostHeadlineByCategoryIdWithPaging(category.getCategoryId(),
                PostSearchCondition.builder()
                        .orderByCreatedAt(true)
                        .orderByLikeCount(false)
                        .onlySearchHostHearted(false)
                        .onlySearchHostWritten(false)
                        .build(),
                PageRequest.of(2, 20));


        System.out.println(result.getTotalElements() + " " + result.getTotalPages() + " " + result.getNumber() + " " + result.getNumberOfElements());
        System.out.println(result.getContent().stream().map(StarPagePostHeadlineDto::toString).toList());

        System.out.println(result2.getTotalElements() + " " + result2.getTotalPages() + " " + result2.getNumber() + " " + result2.getNumberOfElements());
        System.out.println(result2.getContent().stream().map(StarPagePostHeadlineDto::toString).toList());
    }


    @Test
    @Transactional
    public void testForSearchPostHeadlineByCategoryIdWithNoOffset() {
        StarPage dummyStarPage1 = starPageRepository.save(StarPage.create(idGenerateService.getNextId(), "박보영", "boyoung@naver.com", Set.of("ACTOR"),
                List.of(SNSLine.of(SNSType.YOUTUBE, "boyoung.youtube.com")), "보영"));

        Category category = categoryRepository.save(dummyStarPage1.createCommonTypeCategory(createCategoryFromStarPageService, "category1", ContentRestriction.of(false, false, false, 0)));

        List<StarPagePost> postList = new ArrayList<>();

        for(int i = 0; i<100; i++){
            postList.add(new CommonPost(category.getCategoryId(), String.valueOf(i), Author.of("hihi", "aaa@naver.com"), PostStatus.OPEN, new ArrayList<>()));
        }
        postRepository.saveAll(postList);

        System.out.println(postRepository.findAll().size());

        Slice<StarPagePostHeadlineDto> result = postRepository.searchPostHeadlineByCategoryIdWithNoOffset(category.getCategoryId(),
                PostSearchCondition.builder()
                        .orderByCreatedAt(true)
                        .orderByLikeCount(false)
                        .onlySearchHostHearted(false)
                        .onlySearchHostWritten(false)
                        .build(),
                3000L,
                PageRequest.ofSize(20));

        Long lastPostId = result.getContent().get(result.getSize() -1).getPostId();
        System.out.println(result.getSize() + " " + result.hasNext() + " " + result.getNumber() + " " + result.getNumberOfElements());
        System.out.println(result.getContent().stream().map(StarPagePostHeadlineDto::toString).toList());

        Slice<StarPagePostHeadlineDto> result2 = postRepository.searchPostHeadlineByCategoryIdWithNoOffset(category.getCategoryId(),
                PostSearchCondition.builder()
                        .orderByCreatedAt(true)
                        .orderByLikeCount(false)
                        .onlySearchHostHearted(false)
                        .onlySearchHostWritten(false)
                        .build(),
                lastPostId,
                PageRequest.ofSize(20));

        System.out.println(result2.getSize() + " " + result2.hasNext() + " " + result2.getNumber() + " " + result2.getNumberOfElements());
        System.out.println(result2.getContent().stream().map(StarPagePostHeadlineDto::toString).toList());
    }

    @Test
    @Transactional
    public void testForFindCommonPostById(){
        postRepository.findCommonPostById(1012L);
    }

    @Test
    @Transactional
    public void testForFindAlbumPostById(){
        postRepository.findAlbumPostById(1012L);
    }

    @Test
    @Transactional
    public void testForFindGoldBalancePostById(){
        postRepository.findGoldBalancePostById(1012L);
    }

    @Test
    @Transactional
    public void testForFindVotePostById(){
        postRepository.findVotePostById(1012L);
    }
}