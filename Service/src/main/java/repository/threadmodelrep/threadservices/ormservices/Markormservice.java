package repository.threadmodelrep.threadservices.ormservices;

import lombok.extern.slf4j.Slf4j;
import repository.RepositoryFactory;
import threadmodel.Mark;
import threadmodel.Theams;
import users.Student;

import java.util.*;
@Slf4j
public class Markormservice {
    public static Map<Theams, List<Mark>> getTheamsListHashMap(int studentId, Set<Theams> theams) {
        Map<Theams, List<Mark>> theamsListHashMap = new HashMap<>();
        for (Theams theam : theams) {
            theamsListHashMap.put(theam, dogetMarkListbyTheam(theam, studentId));
        }
        return theamsListHashMap;
    }

    public static Set<Theams> getTheamsSet(Student student) {
        Set<Theams> theams = new HashSet<>();
        for (Mark mark : student.getMarkMap().values()) {
            theams.add(mark.getTheams());
        }
        return theams;
    }
    public static List<Mark> dogetMarkListbyTheam(Theams theam, int studentId) {
        List<Mark> marks = new ArrayList<>();
        log.info("In getMarkListMethd getTheam method = {}", theam.getTheamName() + studentId);
        Student student = RepositoryFactory.getRepository().getStudentById(studentId);
        for (Mark mark : student.getMarkMap().values()) {
            if (mark.getTheams().equals(theam)) {
                marks.add(mark);
            }
        }
        return marks;
    }
}
