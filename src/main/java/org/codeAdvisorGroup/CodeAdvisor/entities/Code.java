package org.codeAdvisorGroup.CodeAdvisor.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table
public class Code {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
    @JoinColumn(name="user_id")
    private User user;


    private String title;

    @Lob
    private String content;

    @OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    private List<Comment> commentList;


    public Code() {
    }

    public Code(long id, String username, String title, String content){//Constructor for safe query
        this.id=id;
        User user=new User();
        user.setUsername(username);
        this.user=user;
        this.title=title;
        this.content=content;
    }

    public Code(long id, String username, String title, String content, Collection<Comment> commentList){//Constructor for safe query
        this.id=id;
        User user=new User();
        user.setUsername(username);
        this.user=user;
        this.title=title;
        this.content=content;
        this.commentList= (List<Comment>) commentList;
    }

    public Code(User user,String title,String content){
        this.user=user;
        this.title=title;
        this.content=content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }
}
