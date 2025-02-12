package com.example.e_voting_system.Services;


import com.example.e_voting_system.Exceptions.ResourceNotFoundException;
import com.example.e_voting_system.Model.DTO.ElectionDTO;
import com.example.e_voting_system.Model.DTO.ElectionDTO2;
import com.example.e_voting_system.Model.Entity.*;
import com.example.e_voting_system.Model.Mapper.ElectionMapper;
import com.example.e_voting_system.Repositories.*;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;


import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ElectionService {

    private final ElectionRepository electionRepository;
    private final ElectionMapper electionMapper;
    private final ElectionTypeRepository electionTypeRepository;
    private final FacultyRepository facultyRepository;
    private final DepartmentRepository departmentRepository;
    private final EligibilityService eligibilityService;
    private final NotificationService notificationService;
    private final UserRepository userRepository;
    private final VoteRepository voteRepository;

    public ElectionService(ElectionRepository electionRepository,
                           ElectionMapper electionMapper,
                           ElectionTypeRepository electionTypeRepository,
                           FacultyRepository facultyRepository,
                           DepartmentRepository departmentRepository,
                           EligibilityService eligibilityService,
                           NotificationService notificationService,
                           UserRepository userRepository,
                           VoteRepository voteRepository) {
        this.electionRepository = electionRepository;
        this.electionMapper = electionMapper;
        this.electionTypeRepository= electionTypeRepository;
        this.facultyRepository = facultyRepository;
        this.departmentRepository = departmentRepository;
        this.eligibilityService = eligibilityService;
        this.notificationService = notificationService;
        this.userRepository=userRepository;
        this.voteRepository = voteRepository;
    }

    // Get all elections
    public List<ElectionDTO> getAllElections() {
        return electionRepository.findAll()
                .stream()
                .map(electionMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Get election by ID
    public Optional<ElectionDTO> getElectionById(Long id) {
        return electionRepository.findById(id).map(electionMapper::toDTO);
    }

    // Search elections by title
    public List<ElectionDTO> searchElections(String searchTerm) {
        return electionRepository.findByTitleContainingIgnoreCase(searchTerm)
                .stream()
                .map(electionMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Create new election (Admin only)
    public ElectionDTO createElection(ElectionDTO electionDTO) {
        // 1. Save the election
        Election election = electionMapper.toEntity(electionDTO);
        election.setIsActive(true); // Set the default state if needed
        Election savedElection = electionRepository.save(election);

        // 2. Fetch eligible users for this election
        List<User> eligibleUsers = eligibilityService.getEligibleUsers(savedElection);

        // 3. Notify eligible users
        String notificationMessage = "A new election has been created: " + savedElection.getTitle();
        notificationService.notifyUsers(eligibleUsers, notificationMessage);

        // 4. Return the created election as a DTO
        return electionMapper.toDTO(savedElection);
    }

    // filterElections method with pagination
    public Page<ElectionDTO2> filterElections(Long faculty, Long department, Boolean upcoming, Boolean active, Integer type, Pageable pageable) {
        Specification<Election> spec = Specification.where(null);

        if (faculty != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("faculty").get("facultyId"), faculty));
        }
        if (department != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("department").get("departmentId"), department));
        }
        if (upcoming != null && upcoming) {
            spec = spec.and((root, query, cb) -> cb.greaterThan(root.get("startDatetime"), LocalDate.now()));
        }
        if (active != null && active) {
            spec = spec.and((root, query, cb) -> cb.isTrue(root.get("isActive")));
        }
        if (type != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("type").get("typeId"), type));
        }

        return electionRepository.findAll(spec, pageable).map(electionMapper::toDTO2);
    }


    public ElectionDTO updateElection(Long electionId, ElectionDTO electionDTO) {
        Election election = electionRepository.findById(electionId)
                .orElseThrow(() -> new ResourceNotFoundException("Election not found with ID: " + electionId));

        // Update the election fields
        election.setTitle(electionDTO.getTitle());
        election.setDescription(electionDTO.getDescription());
        election.setStartDatetime(electionDTO.getStartDatetime());
        election.setEndDatetime(electionDTO.getEndDatetime());
        election.setImageUrl(electionDTO.getImageUrl());

        // Update election type
        if (electionDTO.getTypeId() != null) {
            ElectionType electionType = electionTypeRepository.findById(electionDTO.getTypeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Election Type not found with ID: " + electionDTO.getTypeId()));
            election.setType(electionType);
        }

        // Update faculty
        if (electionDTO.getFacultyId() != null) {
            Faculty faculty = facultyRepository.findById(electionDTO.getFacultyId())
                    .orElseThrow(() -> new ResourceNotFoundException("Faculty not found with ID: " + electionDTO.getFacultyId()));
            election.setFaculty(faculty);
        }

        // Update department
        if (electionDTO.getDepartmentId() != null) {
            Department department = departmentRepository.findById(electionDTO.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found with ID: " + electionDTO.getDepartmentId()));
            election.setDepartment(department);
        }

        // Update active status
        if (electionDTO.getIsActive() != null) {
            election.setIsActive(electionDTO.getIsActive());
        }

        // Save the updated election
        Election updatedElection = electionRepository.save(election);

        // Convert to DTO and return
        return electionMapper.toDTO(updatedElection);
    }

    public void deleteElection(Long electionId) {
        Election election = electionRepository.findById(electionId)
                .orElseThrow(() -> new ResourceNotFoundException("Election not found with ID: " + electionId));

        // Check if the election has started
        if (ZonedDateTime.now().isAfter(election.getStartDatetime())) {
            throw new IllegalStateException("Cannot delete an election that has already started.");
        }

        // Proceed with deletion
        electionRepository.delete(election);
    }



    public List<ElectionDTO> getFeaturedElections(Authentication authentication) {
        // 1. Retrieve the authenticated user's email from the Authentication object
        String email = authentication.getName();

        // 2. Fetch the User entity from the database using the email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // 3. Fetch elections the user is eligible for
        List<Election> eligibleElections = eligibilityService.getEligibleElectionsForUser(user);

        // 4. Initialize the list with eligible elections
        List<Election> featuredElections = new ArrayList<>(eligibleElections);

        // 5. Check if we need to add more elections to reach 8
        if (featuredElections.size() < 5) {
            int needed = 5 - featuredElections.size();

            // Extract IDs of already selected elections to exclude them from random selection
            List<Long> excludedIds = featuredElections.stream()
                    .map(Election::getElectionId)
                    .collect(Collectors.toList());

            // Fetch random elections excluding the already selected ones if there are exclusions
            List<Election> randomElections;
            if (!excludedIds.isEmpty()) {
                randomElections = electionRepository.findRandomElectionsExcluding(needed, excludedIds);
            } else {
                randomElections = electionRepository.findRandomElections(needed);
            }

            // Add the random elections to the featured list
            featuredElections.addAll(randomElections);
        }

        // 6. Convert the elections to DTOs and return
        return featuredElections.stream()
                .map(electionMapper::toDTO)
                .collect(Collectors.toList());
    }


    public List<ElectionDTO> getParticipatedElections(Long userId) {
        // Fetch the user entity
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        // Fetch all votes by the user
        List<Vote> votes = voteRepository.findAllByVoter(user);

        // Extract unique elections from votes
        List<ElectionDTO> participatedElections = votes.stream()
                .map(Vote::getElection)
                .distinct()
                .map(electionMapper::toDTO)
                .collect(Collectors.toList());

        return participatedElections;
    }

    public Long getElectionIdByUserId(Long userId) {
        return electionRepository.findElectionIdByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Election not found for user ID: " + userId));
    }

    public Long getElectionIdByCandidateUserId(Long userId) {
        return electionRepository.findElectionIdByCandidateUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Election not found for candidate user ID: " + userId));
    }
}
