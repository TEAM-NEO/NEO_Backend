package com.neo.needeachother.category.infra;

import com.neo.needeachother.category.domain.Category;
import com.neo.needeachother.category.domain.ContentRestriction;
import com.neo.needeachother.category.domain.dto.CategoryDetailViewDto;
import com.neo.needeachother.category.domain.dto.CategoryViewDto;
import com.neo.needeachother.category.domain.repository.CategoryRepository;
import com.neo.needeachother.post.domain.*;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@SpringBootTest
class CategoryCustomRepositoryImplTest {

    @Autowired
    private StarPageRepository starPageRepository;

    @Autowired
    private StarPageIdGenerateService idGenerateService;

    @Autowired
    private CreateCategoryFromStarPageService createCategoryFromStarPageService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PostRepository postRepository;

    @Test
    @Transactional
    public void testForSearchCategoryViewByStarPageId(){
        StarPage dummyStarPage1 = starPageRepository.save(StarPage.create(idGenerateService.getNextId(), "박보영", "boyoung@naver.com", Set.of("ACTOR"),
                List.of(SNSLine.of(SNSType.YOUTUBE, "boyoung.youtube.com")), "보영"));

        List<Category> savedCategory = categoryRepository.saveAll(List.of(dummyStarPage1.createCommonTypeCategory(createCategoryFromStarPageService, "공통형카테고리",
                        ContentRestriction.of(false, false, false, 0)),
                dummyStarPage1.createAlbumTypeCategory(createCategoryFromStarPageService, "앨범형카테고리",
                        ContentRestriction.of(false, false, false, 0)),
                dummyStarPage1.createGoldBalanceTypeCategory(createCategoryFromStarPageService, "황밸형카테고리",
                        ContentRestriction.of(false, false, false, 0)),
                dummyStarPage1.createVoteTypeCategory(createCategoryFromStarPageService, "투표형카테고리",
                        ContentRestriction.of(false, false, false, 0))));

        List<StarPagePost> savedPost = postRepository.saveAll(List.of(
                savedCategory.get(0).writeCommonPost("글1번", Author.of("아무개1", "aaa@naver.com"),
                        List.of(CommonPostParagraph.ofTextParagraph("텍스트1"),
                                CommonPostParagraph.ofImageParagraph("이미지경로1"),
                                CommonPostParagraph.ofTextParagraph("텍스트2"),
                                CommonPostParagraph.ofImageParagraph("이미지경로2"))),
                savedCategory.get(0).writeCommonPost("글2번", Author.of(dummyStarPage1.getInformation().getHost().getStarNickName(),
                                dummyStarPage1.getInformation().getHost().getEmail()),
                        List.of(CommonPostParagraph.ofTextParagraph("텍스트1"),
                                CommonPostParagraph.ofTextParagraph("텍스트2"),
                                CommonPostParagraph.ofImageParagraph("이미지경로2"))),
                savedCategory.get(0).writeCommonPost("글3번", Author.of("아무개1", "aaa@naver.com"),
                        List.of(CommonPostParagraph.ofTextParagraph("텍스트1"),
                                CommonPostParagraph.ofImageParagraph("이미지경로1"),
                                CommonPostParagraph.ofTextParagraph("텍스트2"),
                                CommonPostParagraph.ofImageParagraph("이미지경로2"))),
                savedCategory.get(1).writeAlbumPost("글4번", Author.of("아무개2", "bbb@naver.com"),
                        AlbumImage.of("이미지경로3")),
                savedCategory.get(2).writeGoldBalancePost("글5번", Author.of("아무개3", "ccc@naver.com"),
                        "짜장면대짬뽕", "짜장면", "짬뽕"),
                savedCategory.get(3).writeVotePost("글6번", Author.of("아무개4", "ddd@naver.com"),
                        "몇시", 3600, List.of(VoteItem.of("3시"), VoteItem.of("4시"), VoteItem.of("5시")))
        ));

        Optional<CategoryViewDto> maybeCategoryViewDto =
                categoryRepository.searchCategoryViewByStarPageId(dummyStarPage1.getStarPageId());

        System.out.println(maybeCategoryViewDto
                .map(CategoryViewDto::toString)
                .orElse("no data"));

    }

    @Test
    @Transactional
    public void testForSearchCategoryDetailViewByStarPageId(){
        StarPage dummyStarPage1 = starPageRepository.save(StarPage.create(idGenerateService.getNextId(), "박보영", "boyoung@naver.com", Set.of("ACTOR"),
                List.of(SNSLine.of(SNSType.YOUTUBE, "boyoung.youtube.com")), "보영"));

        List<Category> savedCategory = categoryRepository.saveAll(List.of(dummyStarPage1.createCommonTypeCategory(createCategoryFromStarPageService, "공통형카테고리",
                        ContentRestriction.of(false, false, false, 0)),
                dummyStarPage1.createAlbumTypeCategory(createCategoryFromStarPageService, "앨범형카테고리",
                        ContentRestriction.of(false, false, false, 0)),
                dummyStarPage1.createGoldBalanceTypeCategory(createCategoryFromStarPageService, "황밸형카테고리",
                        ContentRestriction.of(false, false, false, 0)),
                dummyStarPage1.createVoteTypeCategory(createCategoryFromStarPageService, "투표형카테고리",
                        ContentRestriction.of(false, false, false, 0))));

        List<StarPagePost> savedPost = postRepository.saveAll(List.of(
                savedCategory.get(0).writeCommonPost("글1번", Author.of("아무개1", "aaa@naver.com"),
                        List.of(CommonPostParagraph.ofTextParagraph("텍스트1"),
                                CommonPostParagraph.ofImageParagraph("이미지경로1"),
                                CommonPostParagraph.ofTextParagraph("텍스트2"),
                                CommonPostParagraph.ofImageParagraph("이미지경로2"))),
                savedCategory.get(0).writeCommonPost("글2번", Author.of(dummyStarPage1.getInformation().getHost().getStarNickName(),
                                dummyStarPage1.getInformation().getHost().getEmail()),
                        List.of(CommonPostParagraph.ofTextParagraph("텍스트1"),
                                CommonPostParagraph.ofTextParagraph("텍스트2"),
                                CommonPostParagraph.ofImageParagraph("이미지경로2"))),
                savedCategory.get(0).writeCommonPost("글3번", Author.of("아무개1", "aaa@naver.com"),
                        List.of(CommonPostParagraph.ofTextParagraph("텍스트1"),
                                CommonPostParagraph.ofImageParagraph("이미지경로1"),
                                CommonPostParagraph.ofTextParagraph("텍스트2"),
                                CommonPostParagraph.ofImageParagraph("이미지경로2"))),
                savedCategory.get(1).writeAlbumPost("글4번", Author.of("아무개2", "bbb@naver.com"),
                        AlbumImage.of("이미지경로3")),
                savedCategory.get(2).writeGoldBalancePost("글5번", Author.of("아무개3", "ccc@naver.com"),
                        "짜장면대짬뽕", "짜장면", "짬뽕"),
                savedCategory.get(3).writeVotePost("글6번", Author.of("아무개4", "ddd@naver.com"),
                        "몇시", 3600, List.of(VoteItem.of("3시"), VoteItem.of("4시"), VoteItem.of("5시")))
        ));

        Optional<CategoryDetailViewDto> maybeCategoryDetailViewDto =
                categoryRepository.searchCategoryDetailViewByStarPageId(dummyStarPage1.getStarPageId());

        System.out.println(maybeCategoryDetailViewDto
                .map(CategoryDetailViewDto::toString)
                .orElse("no data"));
    }
}