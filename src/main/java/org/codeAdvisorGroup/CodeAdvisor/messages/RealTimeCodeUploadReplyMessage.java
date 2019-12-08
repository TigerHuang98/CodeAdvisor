package org.codeAdvisorGroup.CodeAdvisor.messages;

import org.codeAdvisorGroup.CodeAdvisor.entities.Code;

public class RealTimeCodeUploadReplyMessage {
    private boolean codeUploadSuccess;
    private Code code;

    public RealTimeCodeUploadReplyMessage(boolean codeUploadSuccess, Code code) {
        this.codeUploadSuccess = codeUploadSuccess;
        this.code = code;
    }

    public boolean isCodeUploadSuccess() {
        return codeUploadSuccess;
    }

    public Code getCode() {
        return code;
    }
}
