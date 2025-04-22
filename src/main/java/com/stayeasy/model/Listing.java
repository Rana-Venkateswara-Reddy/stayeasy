package com.stayeasy.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "listings")
public class Listing {
    @Id
    private String id;
    private String ownerId; // ID of the user who owns this listing
    private String propertyName;
    private String category;
    private Address address;
    private String roomType;
    private int rent;
    private int deposit;
    private String availability;
    private int maxOccupancy;
    private List<String> amenities;
    private List<String> images;
    private List<String> reviewIds; // IDs of reviews for this listing

    // Constructors
    public Listing() {}

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getOwnerId() { return ownerId; }
    public void setOwnerId(String ownerId) { this.ownerId = ownerId; }

    public String getPropertyName() { return propertyName; }
    public void setPropertyName(String propertyName) { this.propertyName = propertyName; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }

    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) { this.roomType = roomType; }

    public int getRent() { return rent; }
    public void setRent(int rent) { this.rent = rent; }

    public int getDeposit() { return deposit; }
    public void setDeposit(int deposit) { this.deposit = deposit; }

    public String getAvailability() { return availability; }
    public void setAvailability(String availability) { this.availability = availability; }

    public int getMaxOccupancy() { return maxOccupancy; }
    public void setMaxOccupancy(int maxOccupancy) { this.maxOccupancy = maxOccupancy; }

    public List<String> getAmenities() { return amenities; }
    public void setAmenities(List<String> amenities) { this.amenities = amenities; }

    public List<String> getImages() { return images; }
    public void setImages(List<String> images) { this.images = images; }

    public List<String> getReviewIds() { return reviewIds; }
    public void setReviewIds(List<String> reviewIds) { this.reviewIds = reviewIds; }

    // Address Inner Class
    public static class Address {
        private String street;
        private String city;
        private String state;
        private String postalCode;
        private String country;
        private double[] coordinates;

        public Address() {}

        // Getters and Setters
        public String getStreet() { return street; }
        public void setStreet(String street) { this.street = street; }

        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }

        public String getState() { return state; }
        public void setState(String state) { this.state = state; }

        public String getPostalCode() { return postalCode; }
        public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

        public String getCountry() { return country; }
        public void setCountry(String country) { this.country = country; }

        public double[] getCoordinates() { return coordinates; }
        public void setCoordinates(double[] coordinates) { this.coordinates = coordinates; }
    }
}