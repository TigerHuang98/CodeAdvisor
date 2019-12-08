package org.codeAdvisorGroup.CodeAdvisor.messages;

import org.codeAdvisorGroup.CodeAdvisor.entities.Code;
import org.codeAdvisorGroup.CodeAdvisor.entities.Comment;

import java.util.List;

public class CodeDetailReplyMessage {
    private boolean codeFind;
    private Code code;
    private List<Comment> commentList;

    public CodeDetailReplyMessage(boolean codeFind, Code code, List<Comment> commentList) {
        this.codeFind = codeFind;
        this.code = code;
        this.commentList = commentList;
    }

    public boolean isCodeFind() {
        return codeFind;
    }

    public Code getCode() {
        return code;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }
}
