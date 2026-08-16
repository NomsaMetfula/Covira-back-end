package com.covira.backend.ai;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;
import org.springframework.stereotype.Service;

@Service
public class AIQuestionService {

    private final OpenAIClient client;

    public AIQuestionService() {
        this.client = OpenAIOkHttpClient.fromEnv();
    }

   public String generateQuestion(
        String jobTitle,
        String category,
        String difficulty
){

    String prompt = """
        You are an expert interview-question designer for Covira,
        a professional video interview platform.

        Generate ONE professional interview question.

        Job Title: %s
        Category: %s
        Difficulty: %s

        Requirements:
        - Make the question professional and suitable for a real job interview.
        - Make it clear and easy for a candidate to understand.
        - Do not include the answer.
        - Return only the interview question itself.
        """.formatted(jobTitle, category, difficulty);

        ResponseCreateParams params = ResponseCreateParams.builder()
                .input(prompt)
                .model(ChatModel.GPT_5_2)
                .build();

        Response response = client.responses().create(params);

        return response.output().stream()
                .flatMap(item -> item.message().stream())
                .flatMap(message -> message.content().stream())
                .flatMap(content -> content.outputText().stream())
                .map(outputText -> outputText.text())
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("AI did not return a question.")
                );
    }
}