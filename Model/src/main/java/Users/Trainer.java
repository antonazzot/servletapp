package Users;

import DataBase.*;
import ThreadModel.*;

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

    @Override
    public String getInf () {
        long l = this.salarylist.stream().map(salary ->
                salary.getBigDecimalSalary()).count() / (long) this.salarylist.size();
        return "ID: " + getId() +  "Trainer name : " + this.getName() + "  " + " have salsry: " + '\n' +
                salarylist.stream().map(b -> b.getBigDecimalSalary().toString()).collect(Collectors.toList()).toString()
                + " " + '\n' + " avarage salary - "  + l;
    }

    @Override
    public String toString() {
        return "Trainer: " +
               getName() + " "

                ;
    }
}
