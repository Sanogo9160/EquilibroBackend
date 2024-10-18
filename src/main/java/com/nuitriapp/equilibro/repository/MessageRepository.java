package com.nuitriapp.equilibro.repository;

import com.nuitriapp.equilibro.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
