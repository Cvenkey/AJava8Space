package com.ajava8.space;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

public class EightAggregateOperations {
    public static void main(String[] args) {

        List<Employee> employees = Employee.getEmployess();
        double maxSal = employees.stream().max(Comparator.comparing(Employee::getSal)).get().getSal();
        double minSal = employees.stream().min(Comparator.comparing(Employee::getSal)).get().getSal();
        double secMaxSal =employees.stream().sorted(Comparator.comparing(Employee::getSal).reversed())
                .collect(Collectors.toList()).get(1).getSal();
        double secMinSal =employees.stream().sorted(Comparator.comparing(Employee::getSal))
                .collect(Collectors.toList()).get(1).getSal();
        System.out.println("maxSal="+maxSal+" minSal="+minSal+" secMaxSal="+secMaxSal+" secMinSal="+secMinSal);

        long empCount= employees.stream().count();
        System.out.println("empCount="+empCount);
        System.out.println("Male and Female emp count");
        employees.stream().collect(Collectors.groupingBy(Employee::getGender,Collectors.counting()))
                .entrySet().forEach(System.out::println);

        DoubleSummaryStatistics employeeSalaryStatistics  = employees.stream().
                collect(Collectors.summarizingDouble(Employee::getSal));
        double avgSal = employeeSalaryStatistics.getAverage();
        double totalSal = employeeSalaryStatistics.getSum();
        System.out.println("avgSal="+avgSal+" totalSal="+totalSal);

        //Department wise maxsal
        System.out.println("Highest Sal of Each Department");
        employees.stream().collect(Collectors.groupingBy(Employee::getDept,
                Collectors.maxBy(Comparator.comparing(Employee::getSal))))
                .entrySet().forEach(System.out::print);

        System.out.println("Average Sal of Each Department");
        employees.stream().collect(Collectors.groupingBy(Employee::getDept,
                Collectors.averagingDouble(Employee::getSal)))
                .entrySet().forEach(System.out::println);

        //Department wise SummaryStatistics
        System.out.println("Department wise SummaryStatistics");
        employees.stream().collect(Collectors.groupingBy(Employee::getDept,
               Collectors.summarizingDouble(Employee::getSal)))
               .entrySet().forEach(System.out::println);
    }

}
