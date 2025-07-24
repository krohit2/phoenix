package com.example.genaisummary.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.util.*;

@Component
public class GeminiConfig {

    @Value("${gemini.api-key}")
    private String apiKey;

    @Value("${gemini.endpoint}")
    private String endpoint;

    private final ObjectMapper objectMapper = new ObjectMapper();

    /*
     * Method to accept @input as parameter
     * Responsible to provide summary of the file
     */
    public String callGeminiForSummary(String inputText) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        Map<String, Object> body = Map.of("contents", List.of(
                Map.of("parts", List.of(Map.of("text", "Summarize this:\n" + inputText)))
        ));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint + "?key=" + apiKey))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(body)))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonNode jsonNode = objectMapper.readTree(response.body());
        System.out.println("Gemini response: " + response.body());
        JsonNode parts = jsonNode.path("candidates").get(0)
                .path("content")
                .path("parts");
        if (parts.isArray() && !parts.isEmpty()) {
            return parts.get(0).path("text").asText();
        }
        throw new RuntimeException("Failed to extract summary from Gemini response");
    }
}
