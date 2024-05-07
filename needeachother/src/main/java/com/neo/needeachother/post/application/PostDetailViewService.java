package com.neo.needeachother.post.application;

import com.neo.needeachother.common.exception.NEOUnexpectedException;
import com.neo.needeachother.post.application.dto.PostDetailDto;
import com.neo.needeachother.post.domain.*;
import com.neo.needeachother.post.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostDetailViewService {

    private final PostRepository postRepository;

    public PostDetailDto getPostDetailView(String contentType, Long postId) {
        PostType postType = PostType.valueOf(contentType);
        switch (postType) {
            case COMMON -> {
                return getCommonPostDetailView(postId);
            }
            case ALBUM -> {
                return getAlbumPostDetailView(postId);
            }
            case GOLD_BALANCE -> {
                return getGoldBalancePostDetailView(postId);
            }
            case VOTE -> {
                return getVotePostDetailView(postId);
            }
            default -> throw new NEOUnexpectedException("적절하지 않은 컨텐츠 타입의 입력");
        }
    }

    private PostDetailDto getCommonPostDetailView(Long postId) {
        return postRepository.findCommonPostById(postId)
                .map(CommonPost::toPostDetailDto)
                // TODO : orElseThrow로 변경
                .orElse(null);
    }

    private PostDetailDto getAlbumPostDetailView(Long postId) {
        return postRepository.findAlbumPostById(postId)
                .map(AlbumPost::toPostDetailDto)
                // TODO : orElseThrow로 변경
                .orElse(null);
    }

    private PostDetailDto getGoldBalancePostDetailView(Long postId) {
        return postRepository.findGoldBalancePostById(postId)
                .map(GoldBalancePost::toPostDetailDto)
                // TODO : orElseThrow로 변경
                .orElse(null);
    }

    private PostDetailDto getVotePostDetailView(Long postId) {
        return postRepository.findVotePostById(postId)
                .map(VotePost::toPostDetailDto)
                // TODO : orElseThrow로 변경
                .orElse(null);
    }
}
