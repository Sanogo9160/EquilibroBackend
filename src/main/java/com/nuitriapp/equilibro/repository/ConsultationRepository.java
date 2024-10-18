package com.nuitriapp.equilibro.repository;

import com.nuitriapp.equilibro.model.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
    long count();

}
