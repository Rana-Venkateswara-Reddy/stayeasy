package com.stayeasy.service;

import com.stayeasy.model.User;
import com.stayeasy.model.Listing;
import com.stayeasy.repository.UserRepository;
import com.stayeasy.repository.ListingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ListingRepository listingRepository;

    public UserService(UserRepository userRepository, ListingRepository listingRepository) {
        this.userRepository = userRepository;
        this.listingRepository = listingRepository;
    }

    @Transactional
    public User registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already registered");
        }
        return userRepository.save(user);
    }

    public boolean verifyUser(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        return userOptional.isPresent() && userOptional.get().getPassword().equals(password);
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<Listing> getUserListings(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return listingRepository.findAllById(user.getListingIds());
    }

    @Transactional
    public User updateUser(String id, User updatedUser) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (updatedUser.getName() != null) existingUser.setName(updatedUser.getName());
        if (updatedUser.getPhone() != null) existingUser.setPhone(updatedUser.getPhone());
        if (updatedUser.getPassword() != null) {
            existingUser.setPassword(updatedUser.getPassword());
        }

        return userRepository.save(existingUser);
    }

    @Transactional
    public void deleteUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Delete all user's listings
        listingRepository.deleteAllById(user.getListingIds());
        userRepository.delete(user);
    }
}