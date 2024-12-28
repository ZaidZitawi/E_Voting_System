package com.example.e_voting_system.Repositories;

import com.example.e_voting_system.Model.Entity.ElectionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElectionTypeRepository extends JpaRepository<ElectionType, Long> {

}