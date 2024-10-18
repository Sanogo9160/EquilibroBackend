package com.nuitriapp.equilibro.service;

import com.nuitriapp.equilibro.model.Article;
import com.nuitriapp.equilibro.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    // Récupérer tous les articles
    public List<Article> recupererTousLesArticles() {
        return articleRepository.findAll();
    }

    // Créer un nouvel article
    public Article creerArticle(Article article) {
        return articleRepository.save(article);
    }

    // Récupérer un article par ID
    public Optional<Article> recupererArticleParId(Long id) {
        return articleRepository.findById(id);
    }

    // Mettre à jour un article
    public Article mettreAJourArticle(Long id, Article articleMisAJour) {
        articleMisAJour.setId(id);
        return articleRepository.save(articleMisAJour);
    }

    // Supprimer un article
    public void supprimerArticle(Long id) {
        articleRepository.deleteById(id);
    }
}
