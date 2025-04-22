package com.stayeasy.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document(collection = "reviews")
public class Review {
    @Id
    private String id;
    private String userId; // ID of the user who wrote the review
    private String listingId; // ID of the listing being reviewed
    private String comment;
    private int rating; // Rating out of 5
    private Date createdAt;

    // Constructors
    public Review() {}

    public Review(String userId, String listingId, String comment, int rating) {
        this.userId = userId;
        this.listingId = listingId;
        this.comment = comment;
        this.rating = rating;
        this.createdAt = new Date();
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getListingId() { return listingId; }
    public void setListingId(String listingId) { this.listingId = listingId; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}