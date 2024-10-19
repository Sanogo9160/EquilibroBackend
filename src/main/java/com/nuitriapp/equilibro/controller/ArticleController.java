package com.nuitriapp.equilibro.controller;

import com.nuitriapp.equilibro.model.Article;
import com.nuitriapp.equilibro.repository.ArticleRepository;
import com.nuitriapp.equilibro.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/articles")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:49889"})
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("liste")
    public List<Article> recupererTousLesArticles() {
        return articleService.recupererTousLesArticles();
    }

    @PostMapping("/ajouter")
    public Article creerArticle(@RequestBody Article article) {
        return articleService.creerArticle(article);
    }

    @GetMapping("/obtenir/{id}")
    public ResponseEntity<Article> recupererArticleParId(@PathVariable Long id) {
        Optional<Article> article = articleService.recupererArticleParId(id);
        return article.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/modifier/{id}")
    public ResponseEntity<Article> mettreAJourArticle(@PathVariable Long id, @RequestBody Article articleMisAJour) {
        Article article = articleService.mettreAJourArticle(id, articleMisAJour);
        return ResponseEntity.ok(article);
    }

    @DeleteMapping("/supprimer/{id}")
    public ResponseEntity<Void> supprimerArticle(@PathVariable Long id) {
        articleService.supprimerArticle(id);
        return ResponseEntity.noContent().build();
    }


}