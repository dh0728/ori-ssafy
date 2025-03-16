package funding_review.funding_review.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Client 선언부, name 또는 url 사용 가능
// name or value 둘중 하나는 있어야 오류가 안남
//@FeignClient(name = "funding_review-client", url = "http://localhost:8080")
@FeignClient(name = "funding-review-service")
public interface FundingReviewClient {


    @GetMapping("/api/funding-review/")
    ResponseEntity<Object> getAllFundingReview();


    @GetMapping("/api/funding-review/{findingReviewId}")
    ResponseEntity<Object> getFundingReview(@PathVariable int findingReviewId);


}