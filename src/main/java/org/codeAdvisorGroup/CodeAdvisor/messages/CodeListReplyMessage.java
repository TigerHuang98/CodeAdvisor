package org.codeAdvisorGroup.CodeAdvisor.messages;

import org.codeAdvisorGroup.CodeAdvisor.digest.CodeDigest;

import java.util.List;

public class CodeListReplyMessage {
    private boolean userFind;
    private List<CodeDigest> codeList;

    public CodeListReplyMessage(boolean userFind,List<CodeDigest> codeList) {
        this.userFind = userFind;
        this.codeList = codeList;
    }

    public boolean isUserFind() {
        return userFind;
    }

    public List<CodeDigest> getCodeList() {
        return codeList;
    }
}
