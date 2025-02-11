package com.example.e_voting_system.Controllers;

import com.example.e_voting_system.Exceptions.ResourceNotFoundException;
import com.example.e_voting_system.Exceptions.UnauthorizedException;
import com.example.e_voting_system.Model.DTO.UserDTO;
import com.example.e_voting_system.Model.DTO.UserUpdateDTO;
import com.example.e_voting_system.Model.Entity.User;
import com.example.e_voting_system.Repositories.UserRepository;
import com.example.e_voting_system.Services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable Long id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "bio", required = false) String bio,
            @RequestParam(value = "profilePicture", required = false) MultipartFile profilePicture,
            Principal principal) {

        // Get the email of the logged-in user from Principal
        String loggedInUserEmail = principal.getName();

        User loggedInUser = userRepository.findByEmail(loggedInUserEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Logged-in user not found"));

        if (!loggedInUser.getUserId().equals(id)) {
            throw new UnauthorizedException("You are not authorized to update this profile");
        }

        UserDTO updatedUser = userService.updateUser(id, name, bio, profilePicture);
        return ResponseEntity.ok(updatedUser);
    }


    // Get the profile of the logged-in user
    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getLoggedInUserProfile(Principal principal) {
        // Get the logged-in user's email from Principal
        String email = principal.getName();
        // Fetch the user's profile using the email
        UserDTO userDTO = userService.getUserProfileByEmail(email);
        return ResponseEntity.ok(userDTO);
    }


    // Get the profile of a user by ID
    @GetMapping("/profile/{id}")
    public ResponseEntity<UserDTO> getUserProfileById(@PathVariable Long id) {
        UserDTO userDTO = userService.getUserProfileById(id);
        return ResponseEntity.ok(userDTO);
    }


    // Endpoint to update FCM token
    @PutMapping("/{userId}/fcm-token")
    public ResponseEntity<Void> updateFcmToken(@PathVariable Long userId, @RequestBody String fcmToken) {
        userService.updateFcmToken(userId, fcmToken);
        return ResponseEntity.noContent().build();
    }


}
