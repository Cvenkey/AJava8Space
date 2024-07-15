package com.ajava8.space.cutomAnnotation.paramLevel;

import com.ajava8.space.Employee;

public class Main {
    public static void main(String[] args) {
        Employee emp1 = new Employee("Sudeep", 50000, 28, 101, 'M', "IT");

        // Process annotations
        AnnotationProcessor.processAnnotations(emp1);

        // Verify the changes
        System.out.println("First Name: " + emp1.getName());
        System.out.println("Last Name: " + emp1.getName());
    }
}
