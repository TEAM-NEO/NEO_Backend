package com.neo.needeachother.post.application;

import com.neo.needeachother.post.domain.repository.PostRepository;
import com.neo.needeachother.post.domain.StarPagePost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.neo.needeachother.post.application.PostServiceHelper.*;

@Service
@RequiredArgsConstructor
public class ModifyPostInformationService {

    private final PostRepository postRepository;

    @Transactional
    public void modifyTitle(String email, Long postId, String changingTitle){
        StarPagePost foundPost = findExistingStarPagePost(postRepository, postId);
        foundPost.changeTitle(email, changingTitle);
    }
}
