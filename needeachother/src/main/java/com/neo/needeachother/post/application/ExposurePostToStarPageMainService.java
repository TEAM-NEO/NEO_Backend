package com.neo.needeachother.post.application;

import com.neo.needeachother.post.domain.PostRepository;
import com.neo.needeachother.post.domain.StarPagePost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static com.neo.needeachother.post.application.PostServiceHelper.*;

@Service
@RequiredArgsConstructor
public class ExposurePostToStarPageMainService {

    private final PostRepository postRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void exposureMain(Long postId){
        StarPagePost foundPost = findExistingStarPagePost(postRepository, postId);
        foundPost.selectToPopularPost();
    }
}
