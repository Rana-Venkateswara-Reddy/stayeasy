package com.stayeasy.service;

import com.stayeasy.model.Review;
import com.stayeasy.model.Listing;
import com.stayeasy.repository.ReviewRepository;
import com.stayeasy.repository.ListingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ListingRepository listingRepository;

    public ReviewService(ReviewRepository reviewRepository, ListingRepository listingRepository) {
        this.reviewRepository = reviewRepository;
        this.listingRepository = listingRepository;
    }

    // Add review and update listing's reviewIds
    @Transactional
    public Review addReview(Review review) {
        Review savedReview = reviewRepository.save(review);

        Listing listing = listingRepository.findById(review.getListingId())
                .orElseThrow(() -> new RuntimeException("Listing not found"));

        // Update listing's reviewIds
        List<String> reviewIds = listing.getReviewIds();
        reviewIds.add(savedReview.getId());
        listing.setReviewIds(reviewIds);
        listingRepository.save(listing);

        return savedReview;
    }

    // Get reviews for a listing
    public List<Review> getReviewsByListing(String listingId) {
        return reviewRepository.findByListingId(listingId);
    }

    // Get reviews by a user
    public List<Review> getReviewsByUser(String userId) {
        return reviewRepository.findByUserId(userId);
    }
}