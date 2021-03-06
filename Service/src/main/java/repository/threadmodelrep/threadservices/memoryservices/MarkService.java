package repository.threadmodelrep.threadservices.memoryservices;

import action.IdFactory;
import database.DataBaseInf;
import lombok.extern.slf4j.Slf4j;
import repository.RepositoryFactory;
import threadmodel.Mark;
import threadmodel.Theams;
import users.Student;
import users.UserImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class MarkService {

    public static Map<UserImpl, Map<Theams, List<Mark>>> getstudentTheamMark(int studentId) {
        Map<UserImpl, Map<Theams, List<Mark>>> result = new HashMap<>();
        for (UserImpl student :
                DataBaseInf.getStudentHashMap().values()) {
            result.put(student, ((Student) student).getListOfMark());
        }
        return result;
    }

    public static List<Mark> dogetMarkListbyTheam(Theams theam, int studentId) {
        Student student = (Student) DataBaseInf.getStudentHashMap().get(studentId);
        return student.getListOfMark().get(theam);
    }

    public static Map<Integer, Mark> dogetMarkIDListbyTheam(Theams theam, int studentId) {
        Map<Integer, Mark> result = new HashMap<>();
        for (Mark mark :
                dogetMarkListbyTheam(theam, studentId)) {
            result.put(mark.getId(), mark);
        }
        return result;
    }

    public static void doaddMarkToStudent(int studentId, int theamID, int markValue) {
        Student student = (Student) DataBaseInf.getStudentHashMap().get(studentId);
        student.getListOfMark()
                .get(TheamService.gettheamById(theamID))
                .add(new Mark()
                        .withId(IdFactory.idBuilder())
                        .withValue(markValue));
    }

    public static void dodeleteMarksById(int[] tempMarksId, int theamId, int studentid) {
        Student student = (Student) DataBaseInf.getStudentHashMap().get(studentid);
        Theams tempTheam = TheamService.gettheamById(theamId);
        for (int tempId : tempMarksId) {
            Mark markforDelete = student.getListOfMark().get(tempTheam).stream().filter(mark -> mark.getId() == tempId).findAny().get();
            student.getListOfMark().get(tempTheam).remove(markforDelete);
        }
    }

    public static void dochangeMark(Map<Integer, Integer> markIdMarkValue, int studentId, int theamId) {
        Student student = (Student) RepositoryFactory.getRepository().allStudent().get(studentId);
        for (Map.Entry<Integer, Integer> entry : markIdMarkValue.entrySet()) {
            Mark tempMark = student.getListOfMark()
                    .get(TheamService.gettheamById(theamId))
                    .stream().filter(mark -> mark.getId() == entry.getKey()).findAny().get();
            log.info("Old mark id and value = {}", tempMark.getId() + " value: " + tempMark.getValuesOfMark());
            tempMark.setValuesOfMark(entry.getValue());
            log.info("New mark id and value = {}", tempMark.getId() + " value: " + tempMark.getValuesOfMark());
        }
    }

}
