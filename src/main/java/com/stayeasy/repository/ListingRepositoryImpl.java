package com.stayeasy.repository;

import com.stayeasy.model.Listing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ListingRepositoryImpl implements ListingRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Listing> searchWithFilters(String search, String roomType, String category, Integer rentMin, Integer rentMax) {
        List<Criteria> criteriaList = new ArrayList<>();

        if (search != null && !search.isEmpty()) {
            criteriaList.add(new Criteria().orOperator(
                    Criteria.where("propertyName").regex(search, "i"),
                    Criteria.where("address.city").regex(search, "i"),
                    Criteria.where("address.state").regex(search, "i")
            ));
        }

        if (roomType != null && !roomType.isEmpty()) {
            criteriaList.add(Criteria.where("roomType").is(roomType));
        }

        if (category != null && !category.isEmpty()) {
            criteriaList.add(Criteria.where("category").is(category));
        }

        if (rentMin != null) {
            criteriaList.add(Criteria.where("rent").gte(rentMin));
        }

        if (rentMax != null) {
            criteriaList.add(Criteria.where("rent").lte(rentMax));
        }

        Query query = new Query();
        if (!criteriaList.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])));
        }

        return mongoTemplate.find(query, Listing.class);
    }
    @Override
    public List<Listing> getSuggestions(String query) {
        Criteria criteria = new Criteria().orOperator(
                Criteria.where("propertyName").regex(query, "i"),
                Criteria.where("address.city").regex(query, "i"),
                Criteria.where("address.state").regex(query, "i")
        );

        Query mongoQuery = new Query(criteria).limit(5); // Limit to 5 suggestions
        return mongoTemplate.find(mongoQuery, Listing.class);
    }

}
