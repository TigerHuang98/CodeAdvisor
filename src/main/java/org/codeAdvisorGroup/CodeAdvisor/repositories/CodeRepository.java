package org.codeAdvisorGroup.CodeAdvisor.repositories;

import org.codeAdvisorGroup.CodeAdvisor.entities.Code;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CodeRepository extends CrudRepository<Code,Long> {
    @Query("select new Code (c.id,c.user.username,c.title,c.content) from Code c WHERE c.id=:id")
    Optional<Code> findSafe(long id);
}