package com.neo.needeachother.starpage.infra;

import com.neo.needeachother.category.domain.Category;
import com.neo.needeachother.category.domain.ContentRestriction;
import com.neo.needeachother.category.domain.repository.CategoryRepository;
import com.neo.needeachother.post.domain.*;
import com.neo.needeachother.post.domain.repository.PostRepository;
import com.neo.needeachother.starpage.domain.SNSLine;
import com.neo.needeachother.starpage.domain.SNSType;
import com.neo.needeachother.starpage.domain.StarPage;
import com.neo.needeachother.starpage.domain.domainservice.CreateCategoryFromStarPageService;
import com.neo.needeachother.starpage.domain.domainservice.StarPageIdGenerateService;
import com.neo.needeachother.starpage.domain.dto.RepresentativeArticleHeadLine;
import com.neo.needeachother.starpage.domain.dto.VotePostHeadLine;
import com.neo.needeachother.starpage.domain.repository.StarPageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@SpringBootTest
class StarPageRepositoryImplTest {

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
    public void searchHeadLineByStarPageIdAndLimitTest() {

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

        savedPost.get(0).selectToPopularPost();
        savedPost.get(1).selectToPopularPost();
        savedPost.get(3).selectToPopularPost();
        savedPost.get(4).selectToPopularPost();

        CommonPost post = (CommonPost) savedPost.get(0);
        System.out.println(post.getRepresentativeImage());

        postRepository.save(savedPost.get(0));
        postRepository.save(savedPost.get(1));
        postRepository.save(savedPost.get(3));
        postRepository.save(savedPost.get(4));

        List<RepresentativeArticleHeadLine> representativeArticleHeadLines = starPageRepository.searchHeadLineByStarPageIdAndLimit(dummyStarPage1.getStarPageId(), 5);
        representativeArticleHeadLines.forEach(headLine -> {
                    System.out.println(headLine.toString());
                }
        );
    }


    @Test
    @Transactional
    public void testtest(){

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

        List<VotePostHeadLine> testQuery = starPageRepository.searchVotePostHeadLineByCategoryId(dummyStarPage1.getStarPageId(), savedCategory.get(3).getCategoryId(), 4);
        for (VotePostHeadLine votePostHeadLine : testQuery) {
            System.out.println(votePostHeadLine.toString());
        }
    }


    @Test
    @Transactional
    public void testForFindStarPageWithLayout(){
        Optional<StarPage> foundStarPage = starPageRepository.findStarPageWithLayout(idGenerateService.getNextId());
    }

    @Test
    @Transactional
    public void test(){
        StarPage dummyStarPage1 = starPageRepository.save(StarPage.create(idGenerateService.getNextId(), "박보영", "boyoung@naver.com", Set.of("ACTOR"),
                List.of(SNSLine.of(SNSType.YOUTUBE, "boyoung.youtube.com")), "보영"));

        StarPage foundStarPage = starPageRepository.findStarPageWithInformation(dummyStarPage1.getStarPageId())
                .orElseThrow();

        System.out.println(Arrays.toString(foundStarPage.getInformation().getHost().getStarTypes().toArray()));
    }

    @Test
    @Transactional
    public void testForLazy(){
        StarPage dummyStarPage1 = starPageRepository.save(StarPage.create(idGenerateService.getNextId(), "박보영1234", "boyoung@naver.com", Set.of("ACTOR"),
                List.of(SNSLine.of(SNSType.YOUTUBE, "boyoung.youtube.com")), "보영"));

        StarPage foundStarPage = starPageRepository.findByStarPageId(dummyStarPage1.getStarPageId())
                .orElseThrow();

        System.out.println(Arrays.toString(foundStarPage.getInformation().getHost().getStarTypes().toArray()));
    }
}