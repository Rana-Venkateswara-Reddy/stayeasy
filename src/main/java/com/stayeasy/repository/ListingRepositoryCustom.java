package com.stayeasy.repository;

import com.stayeasy.model.Listing;
import java.util.List;

public interface ListingRepositoryCustom {
    List<Listing> searchWithFilters(String search, String roomType, String category, Integer rentMin, Integer rentMax);
    List<Listing> getSuggestions(String query);

}
