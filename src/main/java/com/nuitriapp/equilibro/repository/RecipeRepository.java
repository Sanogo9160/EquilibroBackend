package com.nuitriapp.equilibro.repository;

import com.nuitriapp.equilibro.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
        Recipe findByLabel(String label);

}


