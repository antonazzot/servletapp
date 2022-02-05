package controller.serviseforcontroller.viewsservises;

import helperutils.myexceptionutils.AppValidException;
import threadmodel.Salary;

import java.util.List;

public class AvarageSalaryCalculate {
    public static long avarageSalaryCalc(List<Salary> salaryArrayList, int time) throws AppValidException {
        long avarageSalary = 0;
            if (time<0)
                throw new AppValidException("Period must be upper then null");
            for (int i = time; i > 0; i--) {
                int temp = salaryArrayList.size() - i;
                if (temp<0) throw new AppValidException("Period not correct");
                avarageSalary = salaryArrayList.get(temp).getBigDecimalSalary().longValue() + avarageSalary;
            }
            avarageSalary = avarageSalary / time;
            return avarageSalary;
    }
}