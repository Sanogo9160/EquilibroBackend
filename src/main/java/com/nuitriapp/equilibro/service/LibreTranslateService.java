package com.nuitriapp.equilibro.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.Map;

@Service
public class LibreTranslateService {

    @Value("${libretranslate.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public LibreTranslateService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Traduire une liste de textes dans une langue cible.
     *
     * @param texts     La liste de textes à traduire.
     * @param targetLang La langue cible (ex: "fr" pour français).
     * @return Une liste de textes traduits.
     */
    public List<String> translateTexts(List<String> texts, String targetLang) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Prépare le corps de la requête
        Map<String, Object> requestBody = Map.of(
                "q", texts,
                "source", "auto",
                "target", targetLang,
                "format", "text"
        );

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<List> response = restTemplate.exchange(
                    apiUrl + "/translate",
                    HttpMethod.POST,
                    requestEntity,
                    List.class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                return (List<String>) response.getBody();
            } else {
                System.err.println("Erreur lors de la traduction : " + response.getStatusCode());
                return texts;  // Retourne les textes originaux en cas d'erreur
            }
        } catch (RestClientException e) {
            System.err.println("Erreur de communication avec l'API de traduction : " + e.getMessage());
            return texts;  // Retourne les textes originaux en cas d'erreur
        }
    }
}
