package org.codeAdvisorGroup.CodeAdvisor.messages;

public class RealTimeCommentRequestMessage {
    private String commentToSend;

    public RealTimeCommentRequestMessage(){

    }

    public RealTimeCommentRequestMessage(String commentToSend) {
        this.commentToSend = commentToSend;
    }

    public String getCommentToSend() {
        return commentToSend;
    }
}
