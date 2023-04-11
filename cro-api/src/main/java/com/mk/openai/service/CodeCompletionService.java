package com.mk.openai.service;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;

import java.util.List;
import java.util.Scanner;

public class CodeCompletionService {
    private String token;

    public CodeCompletionService() {
        token = "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";
    }

    public void codeWrite() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("请输入您想要实现的代码: ");
        String text = scanner.nextLine();

        String answer = codeCompletion(text);
        System.out.println("A:" + answer);
    }

    private String codeCompletion(String text) {
        OpenAiService service = new OpenAiService(token, 20);
        if ("exit".equals(text)) {
            return "";
        }
        if (text.length() == 0) {
            return "";
        }
        CompletionRequest completionRequest =
                CompletionRequest.builder()
                        .prompt(text)
                        .model("code-cushman-001")
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
