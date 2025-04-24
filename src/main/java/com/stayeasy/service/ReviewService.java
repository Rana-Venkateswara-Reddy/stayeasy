// src/main/java/com/stayeasy/service/ReviewService.java
package com.stayeasy.service;

import com.stayeasy.model.Review;
import com.stayeasy.model.Listing;
import com.stayeasy.repository.ReviewRepository;
import com.stayeasy.repository.ListingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.ArrayList;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ListingRepository listingRepository;

    public ReviewService(ReviewRepository reviewRepository, ListingRepository listingRepository) {
        this.reviewRepository = reviewRepository;
        this.listingRepository = listingRepository;
    }

    @Transactional
    public Review addReview(Review review) {
        Review savedReview = reviewRepository.save(review);

        Listing listing = listingRepository.findById(review.getListingId())
                .orElseThrow(() -> new RuntimeException("Listing not found"));

        List<String> reviewIds = listing.getReviewIds();
        if (reviewIds == null) {
            reviewIds = new ArrayList<>();
        }
        reviewIds.add(savedReview.getId());
        listing.setReviewIds(reviewIds);
        listingRepository.save(listing);

        return savedReview;
    }

    public List<Review> getReviewsByListing(String listingId) {
        return reviewRepository.findByListingId(listingId);
    }

    public List<Review> getReviewsByUser(String userId) {
        return reviewRepository.findByUserId(userId);
    }
}
