package com.stayeasy.controller;

import com.stayeasy.model.Listing;
import com.stayeasy.service.ListingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/listings")
@CrossOrigin(origins = {"https://eclectic-pithivier-94ff85.netlify.app/"},
        allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE},
        allowCredentials = "true")
public class ListingController {
    private final ListingService listingService;

    public ListingController(ListingService listingService) {
        this.listingService = listingService;
    }

    @GetMapping("/user")
    public ResponseEntity<List<Listing>> getUserListings(@RequestParam String userId) {
        List<Listing> userListings = listingService.getListingsByOwnerId(userId);
        return ResponseEntity.ok(userListings);
    }

    @GetMapping("/")
    public ResponseEntity<List<Listing>> getListingsWithFilters(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String roomType,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Integer rentMin,
            @RequestParam(required = false) Integer rentMax
    ) {
        List<Listing> filteredListings = listingService.getListingsWithFilters(search, roomType, category, rentMin, rentMax);
        return ResponseEntity.ok(filteredListings);
    }

    @PostMapping("/create")
    public ResponseEntity<Listing> createListing(@RequestBody Listing listing) {
        if (listing.getOwnerId() == null || listing.getOwnerId().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Listing savedListing = listingService.createListing(listing);
        return ResponseEntity.ok(savedListing);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Listing> getListingById(@PathVariable String id) {
        return listingService.getListingById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteListing(
            @PathVariable String id,
            @RequestParam String userId) {

        try {
            // Basic validation
            if (userId == null || userId.isEmpty()) {
                return ResponseEntity.badRequest().body("User ID is required");
            }
            if (id == null || id.isEmpty()) {
                return ResponseEntity.badRequest().body("Listing ID is required");
            }

            listingService.deleteListing(id, userId);
            return ResponseEntity.noContent().build();

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Failed to delete listing: " + e.getMessage()));
        }
    }

    @GetMapping("/suggestions")
    public ResponseEntity<List<Listing>> getSuggestions(@RequestParam String q) {
        List<Listing> suggestions = listingService.getSuggestions(q);
        return ResponseEntity.ok(suggestions);
    }
}