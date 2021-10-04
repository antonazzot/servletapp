package Users;

import DataBase.DataBaseInf;
import ThreadModel.Salary;

import java.util.List;

public class Trainer extends UserImpl{

    private List<Salary> salarylist;

    public Trainer( String name, String login, String password, int age, List <Salary> salarylist ) {
        super( Role.TRAINER, name, login, password, age);
        this.salarylist = salarylist;
        DataBaseInf.trainerHashMap.put(this.getId(), this);
    }

    public List<Salary> getSalarylist() {
        return salarylist;
    }

    public void setSalarylist(List<Salary> salarylist) {
        this.salarylist = salarylist;
    }

    public void printInfAboutSalary () {
        System.out.println(toString() + "Has next salary: ");
        for (Salary salary:
             salarylist) {
            System.out.println(salary.getBigDecimalSalary().toString()   );
        }
    }

    @Override
    public String toString() {
        return "Trainer: " +
               getName() + " "

                ;
    }
}
