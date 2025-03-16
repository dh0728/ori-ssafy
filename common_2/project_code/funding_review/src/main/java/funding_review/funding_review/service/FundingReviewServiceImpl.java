package funding_review.funding_review.service;

import funding_review.funding_review.model.FundingReview;

import java.util.List;

public interface FundingReviewServiceImpl {

    public List<FundingReview> getAllFundingReview();

    public FundingReview getFundingReviewById(int fundingReviewId);

}
