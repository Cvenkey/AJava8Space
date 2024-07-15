package com.ajava8.space.threads;

import com.ajava8.space.Employee;

import java.io.*;
import java.nio.file.*;
import java.util.concurrent.CompletableFuture;

public class CompletableFutureNonBlockingExample {
    public static void main(String[] args) {
        CompletableFutureNonBlockingExample futureExample = new CompletableFutureNonBlockingExample();
        Employee employee = new Employee("Vikhi", 95000, 31, 10007, 'M', "BA");

        futureExample.doWriteOrUpdate(employee, "Update");
        System.out.println("I will execute before future response since used join method");
        try {
            Thread.sleep(3000); // Wait for 3 seconds to see the result of asynchronous tasks
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    void doWriteOrUpdate(Employee emp, String operation) {
        if(operation.equals("Insert")) {
            writeEmployee(emp)
                    .thenCompose(e -> displayEmployee(e))
                    .exceptionally(ex -> {
                        System.err.println("Exception occurred: " + ex.getMessage());
                        ex.printStackTrace();
                        return null;
                    });
        } else if (operation.equals("Update")) {
            updateEmployee(emp)
                    .thenCompose(e -> displayEmployee(e))
                    .exceptionally(ex -> {
                        System.err.println("Exception occurred: " + ex.getMessage());
                        ex.printStackTrace();
                        return null;
                    });
        }
    }

    private CompletableFuture<Employee> writeEmployee(Employee emp) {
        System.out.println("Writing employee by thread:"+Thread.currentThread().getName());
        return CompletableFuture.supplyAsync(() -> {
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
                        StandardOpenOption.APPEND);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return emp;
        });
    }

    public CompletableFuture<Employee> updateEmployee(Employee emp) {
        System.out.println("Updating employee thread:"+Thread.currentThread().getName());
        return CompletableFuture.supplyAsync(()->{
        Path originalPath = Paths.get("employees.txt");
        Path tempPath = Paths.get(originalPath + ".tmp");

        try (BufferedReader reader = Files.newBufferedReader(originalPath);
             BufferedWriter writer = Files.newBufferedWriter(tempPath)) {

            String line;
            while ((line = reader.readLine()) != null) {
                Employee employee = Employee.fromCsv(line);
                if (employee.getId() == emp.getId()) {
                    writer.write(Employee.toCVS(emp));
                } else {
                    writer.write(line);
                }
                writer.newLine();
            }
            Files.move(tempPath, originalPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to update the CSV file", e);
        }
            return emp;
        });
    }

    private CompletableFuture<Employee> displayEmployee(Employee emp) {
        return CompletableFuture.supplyAsync(()->{
            System.out.println("displaying employee thread:"+Thread.currentThread().getName());
            System.out.println("contents of "+emp.getId()+" \n"+emp);
            return emp;
        });
    }
}
