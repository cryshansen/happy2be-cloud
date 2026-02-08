package com.artogco.happy2be.service;

//import opennlp.tools.tokenize.SimpleTokenizer;
//import opennlp.tools.postag.POSModel;
//import opennlp.tools.postag.POSTaggerME;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Service
public class NLPService {
    private final WebClient webClient;

    @Value("${openai.api.key}")
    private String openAiApiKey;
    private static final String OPENAI_API_URL = "https://api.openai.com/v1/completions";


    //private POSTaggerME posTagger;

   /* public NLPService() {
        try (InputStream modelIn = getClass().getResourceAsStream("/en-pos-maxent.bin")) {
           // POSModel model = new POSModel(modelIn);
            //posTagger = new POSTaggerME(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

   /*  public List<String> extractKeywords(String text) {
        SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
        String[] tokens = tokenizer.tokenize(text);
        String[] tags = posTagger.tag(tokens);

        // Extract only nouns & adjectives (NN, JJ)
        List<String> keywords = new ArrayList<>();
        for (int i = 0; i < tokens.length; i++) {
            if (tags[i].startsWith("NN") || tags[i].startsWith("JJ")) {
                keywords.add(tokens[i].toLowerCase());
            }
        }
        return keywords;
    }*/

    // ✅ Inject API key via constructor
    public NLPService(@Value("${openai.api.key}") String openAiApiKey, WebClient.Builder webClientBuilder) {
        this.openAiApiKey = openAiApiKey;

        //System.out.println("🔑 Using API Key: " + this.openAiApiKey); // Should print correctly now

        this.webClient = webClientBuilder.baseUrl("https://api.openai.com/v1") 
            .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + this.openAiApiKey)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();


    }

    public Mono<String> getNLPHappy(String inmessage, String tone) {
        //String prompt = "Give me a fortune about " + category + ".";
        String prompt = "Analyze the following message for anxiety or depression, and provide a supportive response: " + (inmessage != null && !inmessage.isBlank() ? inmessage : "happiness") + "using the tone:" + (tone != null && !tone.isBlank() ? tone : "humorous") + ".";
        

       return webClient.post()
            .uri("/chat/completions")
            .bodyValue(Map.of(
                    "model", "gpt-3.5-turbo",
                    "messages", List.of(Map.of("role", "user", "content", prompt)),
                    "max_tokens", 250
            ))
            .retrieve()
            .bodyToMono(Map.class)
            .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(5)) // 🔄 Retries up to 3 times with a 5s delay
            .filter(throwable -> throwable instanceof WebClientResponseException.TooManyRequests))
            .map(response -> {
                List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
                if (choices != null && !choices.isEmpty()) {
                    Map<String, Object> firstChoice = choices.get(0);
                    Map<String, Object> message = (Map<String, Object>) firstChoice.get("message");
                    

                    return (String) message.get("content");
                }
                return "No help available right now.";
            });



     }





}
