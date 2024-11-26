package com.example.e_voting_system.Repositories;

import com.example.e_voting_system.Model.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);


    //Searches the database for a user whose email field matches
    // the provided email and whose verificationCode field matches the provided verification code.
    Optional<User> findByEmailAndVerificationCode(String email, String verificationCode);
}