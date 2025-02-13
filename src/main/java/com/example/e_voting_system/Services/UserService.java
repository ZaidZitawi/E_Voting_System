package com.example.e_voting_system.Services;

import com.example.e_voting_system.Exceptions.ResourceNotFoundException;
import com.example.e_voting_system.Model.DTO.*;
import com.example.e_voting_system.Model.Entity.*;
import com.example.e_voting_system.Model.Mapper.UserMapper;
import com.example.e_voting_system.Repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final FileUploadService fileUploadService;

    public UserService(UserRepository userRepository,
                       UserMapper userMapper,
                       FileUploadService fileUploadService) {
        this.userRepository = userRepository;
        this.userMapper=userMapper;
        this.fileUploadService = fileUploadService;
    }

    //profile Management
    public UserDTO updateUser(Long id, String name, String bio, MultipartFile profilePicture) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Update fields
        if (name != null) {
            user.setName(name);
        }
        if (bio != null) {
            user.setBio(bio);
        }
        if (profilePicture != null && !profilePicture.isEmpty()) {
            try {
                // Use FileUploadService to upload the file
                String filePath = fileUploadService.uploadFile(profilePicture, "profile_images");
                user.setProfilePicture(filePath); // Save the relative path in the database
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload profile picture", e);
            }
        }

        userRepository.save(user);
        return userMapper.toDTO(user);
    }


    public UserDTO getUserProfileByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        return userMapper.toDTO(user); // Convert User entity to UserDTO
    }


    public UserDTO getUserProfileById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));

        return userMapper.toDTO(user); // Convert User entity to UserDTO
    }


    // Save or update FCM token for a user
    public void updateFcmToken(Long userId, String fcmToken) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
        user.setFcmToken(fcmToken); // Update the FCM token
        userRepository.save(user); // Save the updated user
    }

    public Map<String, Object> getUserStatistics() {
        Long totalUsers = userRepository.countTotalUsers();
        List<Object[]> usersByRole = userRepository.countUsersByRole();

        Map<String, Object> statistics = new HashMap<>();
        statistics.put("totalUsers", totalUsers);

        Map<String, Long> roleBreakdown = new HashMap<>();
        for (Object[] result : usersByRole) {
            String roleName = (String) result[0];
            Long count = (Long) result[1];
            roleBreakdown.put(roleName, count);
        }
        statistics.put("roleBreakdown", roleBreakdown);

        return statistics;
    }
}
