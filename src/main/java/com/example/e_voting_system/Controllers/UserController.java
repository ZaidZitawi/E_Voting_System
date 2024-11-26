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
            @RequestBody UserUpdateDTO userUpdateDTO,
            Principal principal) {

        // Get the email of the logged-in user from Principal
        String loggedInUserEmail = principal.getName();

        // Fetch the logged-in user's ID from the database
        User loggedInUser = userRepository.findByEmail(loggedInUserEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Logged-in user not found"));

        // Ensure the logged-in user can only update their own profile
        if (!loggedInUser.getUserId().equals(id)) {
            throw new UnauthorizedException("You are not authorized to update this profile");
        }

        // Proceed with the update
        UserDTO updatedUser = userService.updateUser(id, userUpdateDTO);
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


}
