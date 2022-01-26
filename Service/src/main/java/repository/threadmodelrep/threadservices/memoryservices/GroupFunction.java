package repository.threadmodelrep.threadservices.memoryservices;

import action.IdFactory;
import database.DataBaseInf;
import lombok.extern.slf4j.Slf4j;
import repository.RepositoryFactory;
import threadmodel.Group;
import threadmodel.Theams;
import users.Student;
import users.UserImpl;

import java.util.*;

@Slf4j
public class GroupFunction {

    public static Map<Integer, Group> getAllGroup() {
        return  DataBaseInf.getGroupHashMap();
    }

    public static Map<Integer, Student> getstudentsFromGroup(int groupId) {
        Group group = DataBaseInf.getGroupHashMap().get(groupId);
        return  group.getStudentMap();
    }

    public static void doaddGroup(List<UserImpl> studentList, List<Integer> theamsIdList, Integer trainerId) {
        Group group = new Group();
        Set<Theams> theams = new HashSet<>();
        HashMap<Integer, Student> students = new HashMap<>();
        for (UserImpl user :
                studentList) {
            Student student = (Student) user;
            for (Integer theamId :
                    theamsIdList) {
                Theams tempTheam = TheamFunction.gettheamById(theamId);
                student.addTheam(tempTheam);
            }
            students.put(student.getId(), student);
        }
        for (Integer theamId :
                theamsIdList) {
            theams.add(TheamFunction.gettheamById(theamId));
        }
        group.withId(IdFactory.idBuilder())
                .withName("Groups_" + group.getId())
                .withStudents(students)
                .withTrainer(RepositoryFactory.getRepository().getUserById(trainerId))
                .withTheam(theams);
        log.info("Group was create = {}", "Group name:" + group.getName() +
                " group student: " + group.getStudentMap().values() + " " + group.getTheamsSet().toString());
        DataBaseInf.getGroupHashMap().put(group.getId(), group);
    }


    public static void doupdateGroup(int groupId, String act, int[] entytiIdforact) {
        Group group = DataBaseInf.getGroupHashMap().get(groupId);
        switch (act) {
            case "studentdelete": {
                for (int item : entytiIdforact) {
                    group.getStudentMap().remove(item);
                }
            }
            break;
            case "studentadd": {
                for (int value : entytiIdforact) {
                    Student student = (Student) RepositoryFactory.getRepository().allStudent().get(value);
                    group.addStudent(student);
                }
            }
            break;
            case "theamdelete": {
                for (int k : entytiIdforact) {
                    Theams theams = DataBaseInf.getTheamsHashMap().get(k);
                    group.getTheamsSet().remove(theams);
                }
            }
            break;
            case "theamadd": {
                for (int j : entytiIdforact) {
                    Theams theams = DataBaseInf.getTheamsHashMap().get(j);
                    group.getTheamsSet().add(theams);
                }
            }
            break;
            case "trainer": {
                group.withTrainer(DataBaseInf.getTrainerHashMap().get(entytiIdforact[0]));
            }
        }
    }


}
