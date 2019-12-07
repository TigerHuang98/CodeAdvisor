package org.codeAdvisorGroup.CodeAdvisor.messages;

public class CodeListRequestMessage {
    private String username;

    public CodeListRequestMessage(){

    }

    public CodeListRequestMessage(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}
