package org.codeAdvisorGroup.CodeAdvisor.messages;

public class CodeDetailRequestMessage {
    private long codeID;

    public CodeDetailRequestMessage(){

    }

    public CodeDetailRequestMessage(long codeID) {
        this.codeID = codeID;
    }

    public long getCodeID() {
        return codeID;
    }
}
