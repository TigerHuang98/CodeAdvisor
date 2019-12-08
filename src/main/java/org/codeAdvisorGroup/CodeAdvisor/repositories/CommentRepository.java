package org.codeAdvisorGroup.CodeAdvisor.repositories;

import org.codeAdvisorGroup.CodeAdvisor.entities.Comment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment,Long> {
    @Query("select new Comment (c.id,c.user.username,c.commentContent) from Comment c join c.code co WHERE co.id=:id")
    List<Comment> findSafeByCode_Id(long id);

    @Query("select new Comment (c.id,c.user.username,c.commentContent) from Comment c WHERE c.id=:id")
    Comment findSafeById(long id);
}
