package com.nuitriapp.equilibro.repository;


import com.nuitriapp.equilibro.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUtilisateurId(Long utilisateurId);

    Long countByUtilisateurId(Long utilisateurId);
}
