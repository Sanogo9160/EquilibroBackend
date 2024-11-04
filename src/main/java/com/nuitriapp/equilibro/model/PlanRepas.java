package com.nuitriapp.equilibro.model;

import lombok.Data;

import java.util.List;


@Data

public class PlanRepas {

    private List<RepasSuggestion> petitDejeuner;
    private List<RepasSuggestion> dejeuner;
    private List<RepasSuggestion> diner;

}
