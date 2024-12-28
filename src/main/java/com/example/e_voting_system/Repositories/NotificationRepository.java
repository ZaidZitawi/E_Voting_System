package com.example.e_voting_system.Repositories;

import com.example.e_voting_system.Model.Entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUser_UserIdAndIsRead(Long userId, Boolean isRead);
}
