package com.neo.needeachother.post.application;

import com.neo.needeachother.common.exception.NEOUnexpectedException;
import com.neo.needeachother.post.domain.*;

public final class PostServiceHelper {
    public static CommonPost findExistingCommonPost(PostRepository repository, Long postId){
        StarPagePost foundStarPagePost = findExistingStarPagePost(repository, postId);
        if(foundStarPagePost instanceof CommonPost){
            return (CommonPost) foundStarPagePost;
        }
        throw new NEOUnexpectedException("해당 포스트는 통합형 포스트가 아닙니다.");
    }

    public static AlbumPost findExistingAlbumPost(PostRepository repository, Long postId){
        StarPagePost foundStarPagePost = findExistingStarPagePost(repository, postId);
        if(foundStarPagePost instanceof AlbumPost){
            return (AlbumPost) foundStarPagePost;
        }
        throw new NEOUnexpectedException("해당 포스트는 앨범형 포스트가 아닙니다.");
    }

    public static GoldBalancePost findExistingGoldBalancePost(PostRepository repository, Long postId){
        StarPagePost foundStarPagePost = findExistingStarPagePost(repository, postId);
        if(foundStarPagePost instanceof GoldBalancePost){
            return (GoldBalancePost) foundStarPagePost;
        }
        throw new NEOUnexpectedException("해당 포스트는 황밸형 포스트가 아닙니다.");
    }

    public static VotePost findExistingVotePost(PostRepository repository, Long postId){
        StarPagePost foundStarPagePost = findExistingStarPagePost(repository, postId);
        if(foundStarPagePost instanceof VotePost){
            return (VotePost) foundStarPagePost;
        }
        throw new NEOUnexpectedException("해당 포스트는 투표형 포스트가 아닙니다.");
    }

    public static StarPagePost findExistingStarPagePost(PostRepository repository, Long postId){
        return repository.findById(postId)
                .orElseThrow(() -> new NEOUnexpectedException("포스트 아이디와 매칭되는 포스트가 없습니다."));
    }
}
