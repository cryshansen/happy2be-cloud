package com.artogco.happy2be.controller;


import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.artogco.happy2be.service.TokenizerService;

@RestController
@RequestMapping("/tokenize")
public class TokenizerController {

    private final TokenizerService tokenizerService;

    public TokenizerController(TokenizerService tokenizerService) {
        this.tokenizerService = tokenizerService;
    }

    // ✅ Expose a REST API to tokenize input text
    @PostMapping
    public List<Long> tokenize(@RequestBody String text) {
        return tokenizerService.tokenize(text);
    }



}

