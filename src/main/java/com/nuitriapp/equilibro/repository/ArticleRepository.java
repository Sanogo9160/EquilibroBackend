package com.nuitriapp.equilibro.repository;

import com.nuitriapp.equilibro.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
