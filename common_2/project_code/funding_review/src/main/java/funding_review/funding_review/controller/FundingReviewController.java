package funding_review.funding_review.controller;

import funding_review.funding_review.model.FundingReview;
import funding_review.funding_review.service.FundingReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/funding-review")
@RequiredArgsConstructor
public class FundingReviewController {

    private final FundingReviewService fundingReviewService;

    @GetMapping
    public ResponseEntity<Object> getAllFundingReview() {
        return ResponseEntity.ok(fundingReviewService.getAllFundingReview());
    }

    @GetMapping("/{findingReviewId}")
    public ResponseEntity<Object> getFundingReview(@PathVariable int findingReviewId) {
        FundingReview fundingReview = fundingReviewService.getFundingReviewById(findingReviewId);

        if (fundingReview != null) {
            return ResponseEntity.ok(fundingReview);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

