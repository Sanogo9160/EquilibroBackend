package com.nuitriapp.equilibro.service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nuitriapp.equilibro.model.Recette;
import com.nuitriapp.equilibro.model.RecetteResponse;
import com.nuitriapp.equilibro.repository.ProfilDeSanteRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class EdamamService {

    @Value("${edamam.applicationId}")
    private String appId;

    @Value("${edamam.applicationKey}")
    private String appKey;

    private final RestTemplate restTemplate;

    public EdamamService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getRecipes(String query) {
        String url = String.format("https://api.edamam.com/search?q=%s&app_id=%s&app_key=%s", query, appId, appKey);
        return restTemplate.getForObject(url, String.class);
    }




}

