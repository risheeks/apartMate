package edu.purdue.raj5.apartmate;

/*
*   Object containting the sender email and the message content   
*
*/
public class ChatBubble {

    private String sender;
    private String content;

    /*
    *   Constructor for the Chat Bubble object
    */
    public ChatBubble(String sender, String content) {
        this.sender = sender;
        this.content = content;
    }

    /*
    *return the sender email
    */
    public String getSender() {
        return sender;
    }

    /*
    *return the contents of the message
    */
    public String getContent() {
        return content;
    }
}