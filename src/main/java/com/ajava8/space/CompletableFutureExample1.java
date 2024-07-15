package com.ajava8.space;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.CompletableFuture;

public class CompletableFutureExample1 {
    public static void main(String[] args) {
        CompletableFutureExample1 futureExample = new CompletableFutureExample1();
        Employee employee = new Employee("Vikhi", 85000, 31, 0, 'M', "BA");

        futureExample.writeEmployee(employee)
                .thenCompose(futureExample::displayEmployee)
                .thenAccept(emp -> System.out.println("Final Employee: " + emp))
                .exceptionally(ex -> {
                    System.err.println("Exception occurred: " + ex.getMessage());
                    return null;
                });

        System.out.println("I will execute before future response since used join method");

        // Optional sleep to allow asynchronous tasks to complete for demonstration
        try {
            Thread.sleep(3000); // Wait for 3 seconds to see the result of asynchronous tasks
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private CompletableFuture<Employee> writeEmployee(Employee emp) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("Writing employee");
            String lastLine = null;
            int empId = 10001;

            try (BufferedReader reader = new BufferedReader(new FileReader("employees.txt"))) {
                String line;
                while ((line = reader.readLine()) != null && !line.isEmpty()) {
                    lastLine = line;
                    empId = Employee.fromCsv(lastLine).getId() + 1;
                }
                emp.setId(empId);
                Files.write(Paths.get("employees.txt"), Employee.toCVS(emp).concat("\n").getBytes(),
                        StandardOpenOption.APPEND, StandardOpenOption.CREATE);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return emp;
        });
    }

    private CompletableFuture<Employee> displayEmployee(Employee emp) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("Displaying employee");
            System.out.println("Contents of " + emp.getId() + ":\n" + emp);
            return emp;
        });
    }
}
