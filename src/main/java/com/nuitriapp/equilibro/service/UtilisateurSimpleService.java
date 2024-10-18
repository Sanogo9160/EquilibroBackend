package com.nuitriapp.equilibro.service;

import com.nuitriapp.equilibro.model.UtilisateurSimple;
import com.nuitriapp.equilibro.repository.UtilisateurSimpleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurSimpleService {

    @Autowired
    private UtilisateurSimpleRepository utilisateurSimpleRepository;



}