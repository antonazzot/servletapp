package Users;

import DataBase.*;
import ThreadModel.Salary;

import java.util.List;
import java.util.stream.Collectors;

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

    public String getInf () {
        return "Trainer: " + this.getName() + " " + " have salsry: " + '\n' +
                salarylist.stream().map(b -> b.getBigDecimalSalary().toString()).collect(Collectors.toList()).toString();
    }

    @Override
    public String toString() {
        return "Trainer: " +
               getName() + " "

                ;
    }
}
