package com.nuitriapp.equilibro.repository;

import com.nuitriapp.equilibro.model.Dieteticien;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DieteticienRepository extends JpaRepository<Dieteticien, Long> {

    Optional<Dieteticien> findByEmail(String email);
}
