package edu.purdue.raj5.apartmate;

public class ChatBubble {

    private String sender;
    private String content;

    public ChatBubble(String sender, String content) {
        this.sender = sender;
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }
}