package com.nuitriapp.equilibro.repository;

import com.nuitriapp.equilibro.model.Forum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForumRepository extends JpaRepository<Forum, Long> {
}
