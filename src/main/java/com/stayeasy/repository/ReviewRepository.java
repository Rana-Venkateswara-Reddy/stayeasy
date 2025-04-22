package com.stayeasy.repository;

import com.stayeasy.model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;

public interface ReviewRepository extends MongoRepository<Review, String> {
    @Query("{ 'listingId': ?0 }")
    List<Review> findByListingId(String listingId);

    @Query("{ 'userId': ?0 }")
    List<Review> findByUserId(String userId);

    @Query(value = "{ 'listingId': ?0 }", count = true)
    long countByListingId(String listingId);

    @Query(value = "{ 'userId': ?0, 'listingId': ?1 }", exists = true)
    boolean existsByUserAndListing(String userId, String listingId);
}