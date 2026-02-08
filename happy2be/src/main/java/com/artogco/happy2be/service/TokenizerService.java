package com.artogco.happy2be.service;

import ai.djl.ModelException;
import ai.djl.huggingface.tokenizers.HuggingFaceTokenizer;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;


@Service
public class TokenizerService {
    private final HuggingFaceTokenizer tokenizer;

    public TokenizerService() throws ModelException, IOException {
        // ✅ Load the tokenizer once during service initialization
        this.tokenizer = HuggingFaceTokenizer.newInstance("distilbert-base-uncased");
    }

    public List<Long> tokenize(String text) {
        // ✅ Tokenize input and return token IDs
        long[] tokenIds = tokenizer.encode(text).getIds();
        return Arrays.stream(tokenIds).boxed().collect(Collectors.toList());
    }
}
