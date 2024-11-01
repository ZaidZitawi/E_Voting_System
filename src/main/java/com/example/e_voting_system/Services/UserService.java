package com.example.e_voting_system.Services;

import com.example.e_voting_system.Model.DTO.LoginRequestDTO;
import com.example.e_voting_system.Model.DTO.RegistrationCompletionDTO;
import com.example.e_voting_system.Model.DTO.RegistrationRequestDTO;
import com.example.e_voting_system.Model.DTO.VerificationRequestDTO;
import com.example.e_voting_system.Model.Entity.Department;
import com.example.e_voting_system.Model.Entity.Faculty;
import com.example.e_voting_system.Model.Entity.Role;
import com.example.e_voting_system.Model.Entity.User;
import com.example.e_voting_system.Repositories.DepartmentRepository;
import com.example.e_voting_system.Repositories.FacultyRepository;
import com.example.e_voting_system.Repositories.RoleRepository;
import com.example.e_voting_system.Repositories.UserRepository;
import com.example.e_voting_system.Security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final FacultyRepository facultyRepository;

    private final DepartmentRepository departmentRepository;

    private final RoleRepository roleRepository;

    private final EmailService emailService;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository,
                       FacultyRepository facultyRepository,
                       DepartmentRepository departmentRepository,
                       RoleRepository roleRepository,
                       EmailService emailService,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.facultyRepository = facultyRepository;
        this.departmentRepository = departmentRepository;
        this.roleRepository = roleRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    public void registerUser(RegistrationRequestDTO registrationRequest) throws Exception {
        String email = registrationRequest.getEmail();
        if (!email.matches("^[0-9]{7}@student\\.birzeit\\.edu$")) {
            throw new Exception("Invalid university email address.");
        }

        // Check if email already exists
        if (userRepository.existsByEmail(email)) {
            throw new Exception("Email is already in use.");
        }

        // Validate password and confirmation
        if (!registrationRequest.getPassword().equals(registrationRequest.getConfirmPassword())) {
            throw new Exception("Passwords do not match.");
        }

        // Create new user
        User user = new User();
        user.setName(registrationRequest.getName());
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(registrationRequest.getPassword()));
        user.setVerified(false);

        // Assign default role (e.g., ROLE_USER)
        Role role = roleRepository.findByRoleName("ROLE_USER")
                .orElseThrow(() -> new Exception("Default role not found."));
        user.setRole(role);

        // Generate verification code
        String verificationCode = UUID.randomUUID().toString();
        user.setVerificationCode(verificationCode);

        // Save user to database
        userRepository.save(user);

        // Send verification email
        emailService.sendVerificationEmail(email, verificationCode);
    }

    public String verifyUser(VerificationRequestDTO verificationRequest) throws Exception {
        Optional<User> userOptional = userRepository.findByEmailAndVerificationCode(
                verificationRequest.getEmail(), verificationRequest.getVerificationCode());

        if (userOptional.isEmpty()) {
            throw new Exception("Invalid verification code.");
        }

        User user = userOptional.get();
        user.setVerified(true);
        user.setVerificationCode(null); // Clear the verification code
        user.setVerifiedAt(ZonedDateTime.now());
        userRepository.save(user);

        // Generate JWT token
        return jwtUtil.generateToken(user.getEmail(), user.getRole());
    }

    public void completeRegistration(String email, RegistrationCompletionDTO completionDTO) throws Exception {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("User not found."));

        if (!user.getVerified()) {
            throw new Exception("User is not verified.");
        }

        Faculty faculty = facultyRepository.findById(completionDTO.getFacultyId())
                .orElseThrow(() -> new Exception("Faculty not found."));

        Department department = departmentRepository.findById(completionDTO.getDepartmentId())
                .orElseThrow(() -> new Exception("Department not found."));

        user.setFaculty(faculty);
        user.setDepartment(department);

        userRepository.save(user);
    }

    public String login(LoginRequestDTO loginRequest) throws Exception {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        if (authentication.isAuthenticated()) {
            // Fetch role from the authenticated user
            User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(
                    () -> new UsernameNotFoundException("User not found")
            );
            Role role = user.getRole();

            // Generate token with role
            return jwtUtil.generateToken(loginRequest.getEmail(), role);
        } else {
            throw new Exception("Invalid credentials");
        }
    }
}
