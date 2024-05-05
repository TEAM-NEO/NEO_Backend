package com.neo.needeachother.category.application;

import com.neo.needeachother.category.domain.Category;
import com.neo.needeachother.category.domain.ContentRestriction;
import com.neo.needeachother.category.domain.ContentType;
import com.neo.needeachother.category.domain.repository.CategoryRepository;
import com.neo.needeachother.starpage.domain.StarPage;
import com.neo.needeachother.starpage.domain.StarPageId;
import com.neo.needeachother.starpage.domain.domainservice.CreateCategoryFromStarPageService;
import com.neo.needeachother.starpage.domain.repository.StarPageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.neo.needeachother.starpage.application.StarPageServiceHelper.*;

@Service
@RequiredArgsConstructor
public class CreateCategoryService {

    private final StarPageRepository starPageRepository;
    private final CreateCategoryFromStarPageService createCategoryFromStarPageService;
    private final CategoryRepository categoryRepository;

    public void createCategory(String starPageId, String title, String categoryType,
                               boolean isWriteHostOnly,
                               boolean isCommentWriteAble,
                               boolean isUsingRatingFilter,
                               int filteringRate){
        ContentType categoryContentType = ContentType.valueOf(categoryType);
        switch (categoryContentType){
            case COMMON -> this.createCommonCategory(starPageId, title, isWriteHostOnly,
                    isCommentWriteAble, isUsingRatingFilter, filteringRate);
            case ALBUM -> this.createAlbumCategory(starPageId, title, isWriteHostOnly,
                    isCommentWriteAble, isUsingRatingFilter, filteringRate);
            case VOTE -> this.createVoteCategory(starPageId, title, isWriteHostOnly,
                    isCommentWriteAble, isUsingRatingFilter, filteringRate);
            case GOLD_BALANCE -> this.createGoldBalanceCategory(starPageId, title, isWriteHostOnly,
                    isCommentWriteAble, isUsingRatingFilter, filteringRate);
        }
    }

    @Transactional
    public void createCommonCategory(String starPageId, String title,
                                     boolean isWriteHostOnly,
                                     boolean isCommentWriteAble,
                                     boolean isUsingRatingFilter,
                                     int filteringRate){
       StarPage foundStarPage = findExistingStarPage(starPageRepository, StarPageId.of(starPageId));
       Category createdCategory = foundStarPage
               .createCommonTypeCategory(createCategoryFromStarPageService, title,
                       ContentRestriction.of(isWriteHostOnly, isCommentWriteAble, isUsingRatingFilter, filteringRate));
       categoryRepository.save(createdCategory);
    }


    @Transactional
    public void createAlbumCategory(String starPageId, String title,
                                     boolean isWriteHostOnly,
                                     boolean isCommentWriteAble,
                                     boolean isUsingRatingFilter,
                                     int filteringRate){
        StarPage foundStarPage = findExistingStarPage(starPageRepository, StarPageId.of(starPageId));
        Category createdCategory = foundStarPage
                .createAlbumTypeCategory(createCategoryFromStarPageService, title,
                        ContentRestriction.of(isWriteHostOnly, isCommentWriteAble, isUsingRatingFilter, filteringRate));
        categoryRepository.save(createdCategory);
    }

    @Transactional
    public void createGoldBalanceCategory(String starPageId, String title,
                                    boolean isWriteHostOnly,
                                    boolean isCommentWriteAble,
                                    boolean isUsingRatingFilter,
                                    int filteringRate){
        StarPage foundStarPage = findExistingStarPage(starPageRepository, StarPageId.of(starPageId));
        Category createdCategory = foundStarPage
                .createGoldBalanceTypeCategory(createCategoryFromStarPageService, title,
                        ContentRestriction.of(isWriteHostOnly, isCommentWriteAble, isUsingRatingFilter, filteringRate));
        categoryRepository.save(createdCategory);
    }


    @Transactional
    public void createVoteCategory(String starPageId, String title,
                                          boolean isWriteHostOnly,
                                          boolean isCommentWriteAble,
                                          boolean isUsingRatingFilter,
                                          int filteringRate){
        StarPage foundStarPage = findExistingStarPage(starPageRepository, StarPageId.of(starPageId));
        Category createdCategory = foundStarPage
                .createVoteTypeCategory(createCategoryFromStarPageService, title,
                        ContentRestriction.of(isWriteHostOnly, isCommentWriteAble, isUsingRatingFilter, filteringRate));
        categoryRepository.save(createdCategory);
    }
}
