// src/main/java/com/stayeasy/controller/ReviewController.java
package com.stayeasy.controller;

import com.stayeasy.model.Review;
import com.stayeasy.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "https://elegant-beijinho-199497.netlify.app/",
        allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE},
        allowCredentials = "true")
public class ReviewController {
    private final ReviewService reviewService;
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<Review> addReview(@RequestBody Review review) {
        // review.name and review.email will bind automatically
        return ResponseEntity.ok(reviewService.addReview(review));
    }

    @GetMapping("/listing/{listingId}")
    public ResponseEntity<List<Review>> getByListing(@PathVariable String listingId) {
        return ResponseEntity.ok(reviewService.getReviewsByListing(listingId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Review>> getByUser(@PathVariable String userId) {
        return ResponseEntity.ok(reviewService.getReviewsByUser(userId));
    }
}
