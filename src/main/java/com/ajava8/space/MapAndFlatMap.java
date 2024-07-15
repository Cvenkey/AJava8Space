package com.ajava8.space;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MapAndFlatMap {
    public static void main(String[] args) {
        MapAndFlatMap mapAndFlatMap = new MapAndFlatMap();
        mapAndFlatMap.applyMap();
        mapAndFlatMap.applyFlatMap();
    }

    public void applyMap() {
        //convert double sal to String sal using map
        List<Employee> emps = Employee.getEmployess();
        System.out.println("Double form salaries");
        emps.stream().map(e -> String.valueOf(e.getSal())).collect(Collectors.toList()).forEach(System.out::println);

        //extract a particular field from an object
        System.out.println("Names of the emps");
        emps.stream().map(Employee::getName).collect(Collectors.toList()).forEach(System.out::println);
        emps.stream().map(e->e.getName()).collect(Collectors.toList()).forEach(System.out::println);

        //to apply a function to each element
        System.out.println("Reverse Names of the emps");
        emps.stream().map(e->new StringBuilder(e.getName()).reverse()).forEach(System.out::println);

        //To handle null checks
        Optional.ofNullable(emps)
                .map(List::stream)
                .orElseGet(Stream::empty)
                .filter(e->e.getSal()>5000).map(Employee::getName)
                .collect(Collectors.toList()).forEach(n->System.out.print(n+" "));



    }

    public void applyFlatMap() {
        //Flattening Nested Collections
        List<Employee> employeesList1 = Arrays.asList(
                  new Employee("Sudeep", 50000, 28, 101, 'M', "IT"),
                  new Employee("Kundan", 35000, 20, 105, 'F', "HR"),
                  new Employee("Densi", 600000, 35, 109, 'F', "HR"));

        List<Employee> employeesList2 = Arrays.asList(
                  new Employee("Deb", 900000, 25, 100, 'F', "Finance"),
                  new Employee("Doung", 900000, 25, 100, 'T', "IT"));

        List<List<Employee>> masterList = Arrays.asList(employeesList1,employeesList2);
        int totalEmps = masterList.stream().flatMap(eachList->eachList.stream()).collect(Collectors.toList()).size();
        System.out.println("No. of employees:"+totalEmps);

        //flat map used for both transformation and flattering
        //transformation like for splitting
        //flattering making single list with strings
        List<String> sentences = Arrays.asList("Hello world", "Java streams", "flat map example");
        List<String> words = sentences.stream()
                .flatMap(sentence -> Arrays.stream(sentence.split(" ")))
                .collect(Collectors.toList());
        words.forEach(System.out::print);

        words.stream().flatMap(w-> Stream.of(w.length()))
                .collect(Collectors.toList()).forEach(l->System.out.print(" "+l));

    }
}
