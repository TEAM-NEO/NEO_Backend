package com.neo.needeachother.post.domain.domainservice;

import com.neo.needeachother.category.domain.Category;
import com.neo.needeachother.category.domain.CategoryId;
import com.neo.needeachother.category.domain.repository.CategoryRepository;
import com.neo.needeachother.post.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.neo.needeachother.category.application.CategoryServiceHelper.*;

@Service
@RequiredArgsConstructor
public class CreatePostByCategoryService {

    private final CategoryRepository categoryRepository;

    public CommonPost createCommonPost(String title, Author author, CategoryId categoryId){
        Category foundCategory = findExistingCategory(categoryRepository, categoryId);
        return foundCategory.writeCommonPost(title, author);
    }

    public AlbumPost createAlbumPost(String title, Author author, AlbumImage albumImage, CategoryId categoryId){
        Category foundCategory = findExistingCategory(categoryRepository, categoryId);
        return foundCategory.writeAlbumPost(title, author, albumImage);
    }

    public GoldBalancePost createGoldBalancePost(String title, Author author, String question,
                                                 String leftExample, String rightExample, CategoryId categoryId){
        Category foundCategory = findExistingCategory(categoryRepository, categoryId);
        return foundCategory.writeGoldBalancePost(title, author, question, leftExample, rightExample);
    }

    public VotePost createVotePost(String title, Author author, String question, int timeToLive,
                                   List<VoteItem> voteItemList, CategoryId categoryId){
        Category foundCategory = findExistingCategory(categoryRepository, categoryId);
        return foundCategory.writeVotePost(title, author, question, timeToLive, voteItemList);
    }
}
