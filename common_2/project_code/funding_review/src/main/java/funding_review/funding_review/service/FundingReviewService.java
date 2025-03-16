package funding_review.funding_review.service;

import funding_review.funding_review.mapper.FundingReviewMapper;
import funding_review.funding_review.model.FundingReview;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FundingReviewService implements FundingReviewServiceImpl{

    private final FundingReviewMapper fundingReviewMapper;

    public List<FundingReview> getAllFundingReview() {
        return fundingReviewMapper.getAllFundingReview();
    }

    public FundingReview getFundingReviewById(int fundingReviewId) {
        return fundingReviewMapper.getFundingReviewById(fundingReviewId);
    }
}
