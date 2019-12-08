package org.codeAdvisorGroup.CodeAdvisor.entities;

import javax.persistence.*;

@Entity
@Table
public class Comment {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
    @JoinColumn(name="code_id")
    private Code code;

    @Lob
    private String commentContent;

    public Comment() {
    }

    public Comment(long id,String username,String commentContent) {
        this.id=id;
        User user=new User();
        user.setUsername(username);
        this.user=user;
        this.commentContent=commentContent;
    }

    public Comment(User user, Code code, String commentContent) {
        this.user = user;
        this.code = code;
        this.commentContent = commentContent;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Code getCode() {
        return code;
    }

    public void setCode(Code code) {
        this.code = code;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }
}
