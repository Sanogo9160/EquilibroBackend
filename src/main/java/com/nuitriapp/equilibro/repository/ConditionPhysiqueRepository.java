package com.nuitriapp.equilibro.repository;

import com.nuitriapp.equilibro.model.ConditionPhysique;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConditionPhysiqueRepository extends JpaRepository<ConditionPhysique, Long> {

    List<ConditionPhysique> findByProfilId(Long profilId);
}
