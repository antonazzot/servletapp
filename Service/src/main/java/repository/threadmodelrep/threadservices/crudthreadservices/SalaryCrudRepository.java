package repository.threadmodelrep.threadservices.crudthreadservices;

import org.springframework.data.repository.CrudRepository;
import threadmodel.Salary;
import threadmodel.Theams;

public interface SalaryCrudRepository extends CrudRepository <Salary, Integer> {
}
