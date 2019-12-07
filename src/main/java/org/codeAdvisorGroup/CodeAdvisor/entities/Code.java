package org.codeAdvisorGroup.CodeAdvisor.entities;

import javax.persistence.*;
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

    @OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private List<Comment> CommentList;


    public Code() {
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
}
