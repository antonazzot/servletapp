package repository.threadmodelrep.threadservices.crudthreadservices;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import threadmodel.Theams;

import java.util.Set;

public interface TheamCrudRepository extends CrudRepository <Theams, Integer> {
    Set<Theams> findByGroupId (Integer GroupId);
    @Modifying
    @Query("update Theams t set t.theamName = ?2 where t.id = ?1")
    void setTheamById(@Param("id") Integer Id, @Param("theamName") String theamName);
}
