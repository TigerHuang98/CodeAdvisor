package org.codeAdvisorGroup.CodeAdvisor.messages;

import org.codeAdvisorGroup.CodeAdvisor.entities.Comment;

public class RealTimeCommentReplyMessage {
    private boolean commentSuccess;
    private Comment comment;

    public RealTimeCommentReplyMessage(boolean commentSuccess, Comment comment) {
        this.commentSuccess = commentSuccess;
        this.comment = comment;
    }

    public boolean isCommentSuccess() {
        return commentSuccess;
    }

    public Comment getComment() {
        return comment;
    }
}
