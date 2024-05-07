package com.neo.needeachother.post.application;

import com.neo.needeachother.post.domain.repository.PostRepository;
import com.neo.needeachother.post.domain.StarPagePost;
import com.neo.needeachother.post.domain.domainservice.PostFeatureUseAbleQualificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.neo.needeachother.post.application.PostServiceHelper.*;

@Service
@RequiredArgsConstructor
public class LikePostService {

    private final PostRepository postRepository;
    private final PostFeatureUseAbleQualificationService postFeatureUseAbleQualificationService;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public void likeIt(Long postId, String email){
        StarPagePost foundPost = findExistingStarPagePost(postRepository, postId);
        foundPost.doLike(postFeatureUseAbleQualificationService, applicationEventPublisher, email);
    }

    @Transactional
    public void unLikeIt(Long postId, String email){
        StarPagePost foundPost = findExistingStarPagePost(postRepository, postId);
        foundPost.cancelLike(postFeatureUseAbleQualificationService, email);
    }
}
