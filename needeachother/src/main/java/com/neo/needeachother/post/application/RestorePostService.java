package com.neo.needeachother.post.application;

import com.neo.needeachother.post.domain.PostRepository;
import com.neo.needeachother.post.domain.StarPagePost;
import com.neo.needeachother.post.domain.domainservice.PostFeatureUseAbleQualificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.neo.needeachother.post.application.PostServiceHelper.*;

@Service
@RequiredArgsConstructor
public class RestorePostService {

    private final PostRepository postRepository;
    private final PostFeatureUseAbleQualificationService postFeatureUseAbleQualificationService;

    @Transactional
    public void restorePost(Long postId, String email){
        StarPagePost foundPost = findExistingStarPagePost(postRepository, postId);
        foundPost.restore(email);
    }
}
