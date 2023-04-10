package com.mk.openai;

import com.mk.openai.service.CodeCompletionService;
import com.mk.openai.service.TextCompletionService;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        while (true) {
            System.out.println("请选择你要使用的功能：1: 聊天 2: 单次询问 3: 代码编写 exit：退出");
            Scanner scanner = new Scanner(System.in);
            String chose = scanner.nextLine();
            if ("1".equals(chose)) {
                TextCompletionService service = new TextCompletionService();
                service.chat();
            } else if ("2".equals(chose)) {
                TextCompletionService service = new TextCompletionService();
                service.question();
            } else if ("3".equals(chose)) {
                CodeCompletionService service = new CodeCompletionService();
                service.codeWrite();
            } else if ("exit".equals(chose)) {
                return;
            }
        }


    }

}
