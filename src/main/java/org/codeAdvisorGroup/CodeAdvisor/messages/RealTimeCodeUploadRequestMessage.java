package org.codeAdvisorGroup.CodeAdvisor.messages;

public class RealTimeCodeUploadRequestMessage {
    private String codeTitleToSend;
    private String codeToSend;

    public RealTimeCodeUploadRequestMessage(){

    }

    public RealTimeCodeUploadRequestMessage(String codeTitleToSend, String codeToSend) {
        this.codeTitleToSend = codeTitleToSend;
        this.codeToSend = codeToSend;
    }

    public String getCodeTitleToSend() {
        return codeTitleToSend;
    }

    public String getCodeToSend() {
        return codeToSend;
    }
}
