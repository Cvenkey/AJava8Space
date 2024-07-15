package com.ajava8.space.threads;

import com.ajava8.space.Employee;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CompletableFutureExample {
    static int  employeeId = 10005;
    public static void main(String[] args) {
        CompletableFutureExample futureExample = new CompletableFutureExample();
        Employee employee = new Employee("VikhiNew", 85000, 31, employeeId, 'M', "BA");
        futureExample.doWriteOrUpdateBlocking(employee,"Insert");
        //futureExample.doWriteOrUpdate(employee,"Insert");
        System.out.println("I will execute after future response since used join method, by thread:"+Thread.currentThread().getName());
    }

    // Main thread blocked until the CompletableFuture completes since we used 'Join'
    //Here feature objects executes independently
    void doWriteOrUpdateBlocking(Employee employee, String operationType){
        List<CompletableFuture<Employee>> featureList = new ArrayList<>();
        if(operationType.equals("Insert")){
            featureList.add(writeEmployee(employee));
            featureList.add(displayEmployee(employeeId));
        } else if (operationType.equals("Update")) {
            featureList.add(updateEmployee(employee));
            featureList.add(displayEmployee(employeeId));
        }
        for (CompletableFuture<Employee> future : featureList) {
            CompletableFuture.allOf(future).join();
        }
    }

    // Main thread blocked until the CompletableFuture completes since we used 'Join'
    //Here feature objects executes interdependently
    void doWriteOrUpdate(Employee employee, String operationType){

        CompletableFuture<Employee> processFuture = null;
        int empId = 0;
        if(operationType.equals("Insert")){
            processFuture = writeEmployee(employee).thenCompose(emp -> displayEmployee(employee))
                    .exceptionally(ex->{
                            System.out.println("Get employee failed, retrying write: " + ex.getMessage());
                return null;
            });
        } else if (operationType.equals("Update")) {
            processFuture = displayEmployee(employee).thenCompose(emp -> updateEmployee(employee))
                    .exceptionally(ex->{
                        System.out.println("Get employee failed, retrying write: " + ex.getMessage());
               return null;
            });
        }
        Employee result = processFuture.join();
        if (result == null) {
            System.out.println("The process did not complete successfully.");
        }
    }

    private CompletableFuture<Employee> writeEmployee(Employee emp) {
        return CompletableFuture.supplyAsync(()->{
        System.out.println("writeEmployee by thread " + Thread.currentThread().getName());
        String lastLine = null;
        int empId = 10001;

        try (BufferedReader reader = new BufferedReader(new FileReader("employees1.txt"))) {
            String line;
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                lastLine = line;
                empId = Employee.fromCsv(lastLine).getId() + 1;
            }

            employeeId = empId;
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
        //return CompletableFuture.completedFuture(emp);
    }

    public CompletableFuture<Employee> updateEmployee(Employee emp) {
        return CompletableFuture.supplyAsync(()-> {
            System.out.println("updateEmployee by thread " + Thread.currentThread().getName());
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
        //return CompletableFuture.completedFuture(emp);
    }

    private CompletableFuture<Employee> displayEmployee(Employee emp) {
        return CompletableFuture.supplyAsync(()-> {
        System.out.println("contents of "+emp.getId()+" \n"+emp);
            return emp;
        });
    }

    private CompletableFuture<Employee> displayEmployee(int empId) {
        return CompletableFuture.supplyAsync(()-> {
            System.out.println("displayEmployee by thread " + Thread.currentThread().getName());
            Employee emp = null;
            emp = getEmployee(empId);
            System.out.println("contents of " + empId + " \n" + emp.toString());
            return emp;
        });
    }

    private Employee getEmployee(int empId) {
        Employee employee = null;
        try (BufferedReader reader = new BufferedReader(new FileReader("employees.txt"))) {
            String line;
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                employee = Employee.fromCsv(line);
                if (employee.getId() == empId)
                    break;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return employee;
    }
}




