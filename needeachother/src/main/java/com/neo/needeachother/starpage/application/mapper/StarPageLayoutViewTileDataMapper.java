package com.neo.needeachother.starpage.application.mapper;

import com.neo.needeachother.starpage.application.dto.StarPageLayoutViewTileData;
import com.neo.needeachother.starpage.domain.dto.*;
import org.springframework.stereotype.Component;

@Component
public class StarPageLayoutViewTileDataMapper implements Mapper<StarPageHeadLine, StarPageLayoutViewTileData> {

    @Override
    public StarPageLayoutViewTileData map(StarPageHeadLine input) {
        StarPageLayoutViewTileData.StarPageLayoutViewTileDataBuilder builder = StarPageLayoutViewTileData
                .builder()
                .postId(input.getPostId())
                .likeCount(input.getLikeCount())
                .categoryType(input.getCategoryType());

        if (input instanceof CommonPostHeadLine) {
            builder.author(((CommonPostHeadLine) input).getAuthor())
                    .title(((CommonPostHeadLine) input).getTitle())
                    .representativeImage(((CommonPostHeadLine) input).getRepresentativeImage());
        } else if (input instanceof GoldBalancePostHeadLine) {
            builder.question(((GoldBalancePostHeadLine) input).getQuestion())
                    .leftExample(((GoldBalancePostHeadLine) input).getLeftExample())
                    .rightExample(((GoldBalancePostHeadLine) input).getRightExample())
                    .leftCount(((GoldBalancePostHeadLine) input).getLeftCount())
                    .rightCount(((GoldBalancePostHeadLine) input).getRightCount())
                    .leftRate(((GoldBalancePostHeadLine) input).getLeftRate())
                    .rightRate(((GoldBalancePostHeadLine) input).getRightRate());
        } else if (input instanceof ImagePostHeadLine) {
            builder.representativeImage(((ImagePostHeadLine) input).getRepresentativeImage());
        } else if (input instanceof VotePostHeadLine) {
            builder.question(((VotePostHeadLine) input).getQuestion())
                    .optionCount(((VotePostHeadLine) input).getOptionCount());
        } else if (input instanceof RepresentativeArticleHeadLine) {
            builder.author(((RepresentativeArticleHeadLine) input).getAuthor())
                    .categoryTitle(((RepresentativeArticleHeadLine) input).getCategoryTitle())
                    .title(((RepresentativeArticleHeadLine) input).getTitle())
                    .representativeImage(((RepresentativeArticleHeadLine) input).getRepresentativeImage());
        }

        return builder.build();
    }

}
