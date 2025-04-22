package com.stayeasy.service;

import com.stayeasy.model.Listing;
import com.stayeasy.model.User;
import com.stayeasy.repository.ListingRepository;
import com.stayeasy.repository.UserRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ListingService {
    private final ListingRepository listingRepository;
    private final UserRepository userRepository;

    public ListingService(ListingRepository listingRepository,
                          UserRepository userRepository) {
        this.listingRepository = listingRepository;
        this.userRepository = userRepository;
    }

    public List<Listing> getListingsByOwnerId(String ownerId) {
        return listingRepository.findByOwnerId(ownerId);
    }

    @Transactional
    public Listing createListing(Listing listing) {
        if (listing.getImages() == null || listing.getImages().isEmpty()) {
            throw new IllegalArgumentException("At least one image is required");
        }

        Listing savedListing = listingRepository.save(listing);

        // Update user's listingIds
        User user = userRepository.findById(listing.getOwnerId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (user.getListingIds() == null) {
            user.setListingIds(new ArrayList<>());
        }
        user.getListingIds().add(savedListing.getId());
        userRepository.save(user);

        return savedListing;
    }

    public List<Listing> getListings() {
        return listingRepository.findAll();
    }

    public Optional<Listing> getListingById(String id) {
        return listingRepository.findById(id);
    }

    public List<Listing> getListingsWithFilters(String search, String roomType,
                                                String category, Integer rentMin,
                                                Integer rentMax) {
        return listingRepository.searchWithFilters(search, roomType, category, rentMin, rentMax);
    }

    public List<Listing> getSuggestions(String query) {
        return listingRepository.getSuggestions(query);
    }

    @Transactional
    public void deleteListing(String listingId, String userId) {
        try {
            // Verify listing exists and belongs to user
            Listing listing = listingRepository.findById(listingId)
                    .orElseThrow(() -> new IllegalArgumentException("Listing not found"));

            if (!listing.getOwnerId().equals(userId)) {
                throw new IllegalArgumentException("User doesn't own this listing");
            }

            // Remove from user's listings
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            if (user.getListingIds() != null) {
                user.getListingIds().remove(listingId);
                userRepository.save(user);
            }

            // Delete using the new repository method
            listingRepository.deleteByIdAndOwnerId(listingId, userId);

        } catch (DataAccessException e) {
            throw new RuntimeException("Database error while deleting listing", e);
        }
    }
}