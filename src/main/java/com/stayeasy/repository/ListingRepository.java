package com.stayeasy.repository;

import com.stayeasy.model.Listing;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;

public interface ListingRepository extends MongoRepository<Listing, String>, ListingRepositoryCustom {
    @Query("{ 'ownerId': ?0 }")
    List<Listing> findByOwnerId(String ownerId);
    @Query("{ '_id': ?0 }")
    void deleteById(String id);
    @Query(value = "{ '_id' : ?0, 'ownerId' : ?1 }", delete = true)
    void deleteByIdAndOwnerId(String id, String ownerId);
}