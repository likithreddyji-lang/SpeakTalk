package com.likith.SpeakTalk;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatService {

    private ChatClient chatClient;
    private List<Message> history =new ArrayList<>();
    public ChatService(ChatClient.Builder builder){
        this.chatClient=builder.build();
    }
    public String chat(String message){
        String PROMPT = """
        You are a friendly programming mentor for our learning app called SpeakTalk.
        Answer questions professionally, in a short and simple way.

        You only answer questions about these topics:
        Java, OOPs, Collections, Multithreading, Spring Boot,
        Docker, Data Structures and Algorithms, and SQL.

        If the user is confused or stuck, use words like
        "No worries, let's understand this step by step"
        and then explain the answer clearly.

        If the message is not related to programming, do not answer it.
        Reply politely with:
        "Sorry, this is not related to programming. Please ask me
        a programming question, like Java, Spring Boot, or SQL."
        """ + message;
        history.add(new UserMessage(message));
        String output =chatClient.prompt()
                .system(PROMPT)
                .messages(history)
                .call()
                .content();
        history.add(new AssistantMessage(output));
        return output;

    }

}
