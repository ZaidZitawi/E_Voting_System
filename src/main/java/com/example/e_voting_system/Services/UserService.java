package com.example.e_voting_system.Services;

import com.example.e_voting_system.Exceptions.ResourceNotFoundException;
import com.example.e_voting_system.Model.DTO.*;
import com.example.e_voting_system.Model.Entity.*;
import com.example.e_voting_system.Model.Mapper.UserMapper;
import com.example.e_voting_system.Repositories.*;
import com.example.e_voting_system.Security.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository,
                       UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper=userMapper;
    }

    //profile Management
    public UserDTO updateUser(Long id, UserUpdateDTO userUpdateDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));


        // Update allowed fields only
        user.setProfilePicture(userUpdateDTO.getProfilePicture());
        user.setBio(userUpdateDTO.getBio());
        user.setName(userUpdateDTO.getName());

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




}
