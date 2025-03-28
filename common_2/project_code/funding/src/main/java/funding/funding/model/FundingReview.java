package funding.funding.model;

import lombok.Getter;

@Getter
public class FundingReview {

    private int fundingReviewId;
    private int fundingId;
    private int userId;
    private int rating; //1~5점
    private String content;
}

