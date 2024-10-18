package com.nuitriapp.equilibro.model;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String motDePasse;
}
