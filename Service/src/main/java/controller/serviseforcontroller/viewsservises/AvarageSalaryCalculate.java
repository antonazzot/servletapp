package controller.serviseforcontroller.viewsservises;

import threadmodel.Salary;

import java.util.List;

public class AvarageSalaryCalculate {
    public static long avarageSalaryCalc(List<Salary> salaryArrayList, int time) {
        long avarageSalary = 0;
        for (int i = time; i > 0; i--) {
            int temp = salaryArrayList.size() - i;
            avarageSalary = salaryArrayList.get(temp).getBigDecimalSalary().longValue() + avarageSalary;
        }
        avarageSalary = avarageSalary / time;
        return avarageSalary;
    }
}