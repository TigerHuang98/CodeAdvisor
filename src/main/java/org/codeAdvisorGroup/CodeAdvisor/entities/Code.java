package org.codeAdvisorGroup.CodeAdvisor.entities;

import javax.persistence.*;

@Entity
@Table
public class Code {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
    @JoinColumn(name="user_id")
    private User user;

    @Lob
    private String content;


    public Code() {
    }

    public Code(User user,String content){
        this.user=user;
        this.content=content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
