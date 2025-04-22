package com.stayeasy.repository;

import com.stayeasy.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);

    @Query("{ 'listingIds': { $in: [?0] } }")
    List<User> findUsersByListingIds(String listingId);

    @Query(value = "{ 'email': ?0 }", exists = true)
    boolean existsByEmail(String email);
}