package com.neo.needeachother.post.application;

import com.neo.needeachother.category.domain.CategoryId;
import com.neo.needeachother.post.domain.Author;
import com.neo.needeachother.post.domain.CommonPost;
import com.neo.needeachother.post.domain.PostRepository;
import com.neo.needeachother.post.domain.domainservice.CreatePostByCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WritePostService {

    private final PostRepository postRepository;
    private final CreatePostByCategoryService createPostByCategoryService;

    @Transactional
    public void writeCommonPost(String title, Author author, CategoryId categoryId){
        CommonPost createdCommonPost = createPostByCategoryService.createCommonPost(title, author, categoryId);
        postRepository.save(createdCommonPost);
    }


}
