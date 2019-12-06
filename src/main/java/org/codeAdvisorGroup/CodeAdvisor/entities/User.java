package org.codeAdvisorGroup.CodeAdvisor.entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @NotEmpty
    @Column(nullable=false)
    private String name;

    @OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    private List<Code> codeList;

    public User() {
    }

    public User(String name, List<Code> codeList){
        this.name=name;
        this.codeList=codeList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Code> getCodeList() {
        return codeList;
    }

    public void setCodeList(List<Code> codeList) {
        this.codeList = codeList;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", codes='" + codeList.size() + '\'' +
                '}';
    }
}
