package com.mk.openai.service;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

import static com.mk.openai.constants.OpenAiModelConstants.DAVINCI;

@Service
public class TextCompletionService {

    @Value("${cro.open-ai-token}")
    private String token;

    public TextCompletionService() {
        token = "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";
    }

    public void chat() {
        Scanner scanner = new Scanner(System.in);

        String texts = "";
        int count = 0;
        while (true) {
            System.out.println("Q: ");
            String text = scanner.nextLine();
            if ("exit".equals(text)) {
                break;
            }
            if (text.length() == 0) {
                continue;
            }
            if (texts.length() == 0) {
                texts += "Q: " + text;
            } else {
                texts += "\n\nQ: " + text;
            }

            String answer = textCompletion(texts);

            // 第二次后他会按照提交的格式返回格式所以不需要再
            if (count > 0) {
                answer = answer.replace("\nA: ", "");
            }
            System.out.println("A:" + answer);
            texts += "\nA: " + answer;
            count++;
        }
        System.out.println("再见");
    }

    public void question() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("请输入您的问题: ");
        String text = scanner.nextLine();

        String answer = textCompletion(text);
        System.out.println("A:" + answer);
    }

    public String ask(String question) {
//        System.out.println("请输入您的问题: ");
        return textCompletion(question);
    }

    private String textCompletion(String text) {
        OpenAiService service = new OpenAiService(token, 60);
        if ("exit".equals(text)) {
            return "";
        }
        if (text.length() == 0) {
            return "";
        }
        CompletionRequest completionRequest =
                CompletionRequest.builder()
                        .prompt(text)
                        .model(DAVINCI)
                        .temperature(0.8d)
                        .maxTokens(1000)
                        .echo(true)
                        .build();

        List<CompletionChoice> choices = service.createCompletion(completionRequest).getChoices();
        CompletionChoice completionChoice = choices.get(0);

        String res = completionChoice.getText();

        return res.replace(text, "").replace("\n\n", "");
    }
}
