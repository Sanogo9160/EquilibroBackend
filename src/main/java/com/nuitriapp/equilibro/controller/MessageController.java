package com.nuitriapp.equilibro.controller;

import com.nuitriapp.equilibro.model.Message;
import com.nuitriapp.equilibro.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    // Récupérer tous les messages
    @GetMapping
    public ResponseEntity<List<Message>> obtenirTousLesMessages() {
        List<Message> messages = messageService.getAllMessages();
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    // Récupérer un message par ID
    @GetMapping("/{id}")
    public ResponseEntity<Message> obtenirMessageParId(@PathVariable Long id) {
        Optional<Message> message = messageService.getMessageById(id);
        return message.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Créer un nouveau message
    @PostMapping("/creer")
    public ResponseEntity<Message> creerMessage(@RequestBody Message message) {
        Message nouveauMessage = messageService.createMessage(message);
        return new ResponseEntity<>(nouveauMessage, HttpStatus.CREATED);
    }

    // Supprimer un message par ID
    @DeleteMapping("/supprimer/{id}")
    public ResponseEntity<Void> supprimerMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
        return ResponseEntity.noContent().build();
    }
}