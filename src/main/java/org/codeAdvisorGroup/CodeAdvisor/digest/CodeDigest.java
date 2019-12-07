package org.codeAdvisorGroup.CodeAdvisor.digest;

public class CodeDigest {
    public long code_id;
    public String code_title;
    public String code_author;

    public CodeDigest(long code_id, String code_title, String code_author) {
        this.code_id = code_id;
        this.code_title = code_title;
        this.code_author = code_author;
    }
}