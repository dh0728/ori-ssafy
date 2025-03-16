package funding_review.funding_review.mapper;

import funding_review.funding_review.model.FundingReview;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FundingReviewMapper {

    List<FundingReview> getAllFundingReview();

    FundingReview getFundingReviewById(int fundingReviewId);
}
