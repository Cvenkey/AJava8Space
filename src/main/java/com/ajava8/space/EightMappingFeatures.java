/**
 * 
 */
package com.ajava8.space;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 
 * This class denotes Java8 mappings features
 * Having techniques Creating Map, handling dups, handling NPE, add,remove,update map records 
 * Note : Java 8 Map api have overloaded methods toMap, toConcurrent, computeXXX, putXXX, removeXXX, and replaceXXX methods. 
 * Above declarative methods consice tradational coding styles.
 *  
 */
public class EightMappingFeatures {
	public static void main(String[] args) {
		
		List<Employee> employees = Employee.getEmployess();
        // Creating Map from List
		Map<Integer,Employee> empMap = employees.stream().collect(Collectors.toMap(Employee :: getId, e->e));
		System.out.println("Default Implementation"+empMap.getClass()+" Size:"+employees.size());
        
		// When duplicate exists in List
		// Use distinct when Employee pojo  override hashCode and equals methods. 
		Employee temp = new Employee("Sudeep", 50000, 28, 101, 'M', "IT");
		employees.add(temp);
		Map<Integer, Employee> employeeMap = employees.stream()
				.collect(Collectors.toMap(Employee::getId, e -> e, (preVal, newVal) -> preVal));
		System.out.println("-----------------------\nRemoving Dup(s)");
		employeeMap.entrySet().stream().forEach(entry -> System.out.println(entry.getKey() + " " + entry.getValue()));
        
		// Specifying Map implementation
		LinkedHashMap<Integer, Employee> linkedHashmap = employees.stream().collect(Collectors.toMap(Employee::getId,
				employee -> employee, (preVal, newVal) -> preVal, LinkedHashMap::new));
		System.out.println("######################\nReturning LinkedHashMap");
		linkedHashmap.entrySet()
				.forEach(entry -> System.out.println("Key : " + entry.getKey() + " Value :" + entry.getValue()));

		// When having null key or value
		temp = null;
		employees.add(temp);
		final Function<Employee, Integer> fn = e1 -> e1 == null ? null : e1.getId();
		final Function<Employee, Employee> fn1 = e1 -> e1 == null ? null : e1;
		employeeMap = employees.stream().collect(HashMap::new, (m, e) -> m.put(fn.apply(e), fn1.apply(e)), Map::putAll);
		System.out.println("-----------------------\nHandling null with functional interface");
		employeeMap.entrySet()
				.forEach(entry -> System.out.println("Key : " + entry.getKey() + " Value :" + entry.getValue()));
		
		// Creating List from Map
	    List<Employee> empList=empMap.values().stream().collect(Collectors.toList());
	    System.out.println("######################\nDefault Implementation"+empList.getClass()+" Size:"+empList.size());
	    // With out streams 
	    empList = new ArrayList<>(empMap.values());
	   
	    // Modifying Map Content
		// Get Concurrent Map with removeIf condition
	    employees = Employee.getEmployess();
		ConcurrentMap<Integer, Employee>  concurrentMap= employees.stream().
				collect(Collectors.toConcurrentMap(Employee::getId, Function.identity()));
		concurrentMap.entrySet().removeIf(entry->entry.getValue().getName().equals("Sudeep"));
        System.out.println("-----------------------"+"\n"+"toConcurrentMap"+" Size:"+concurrentMap.size());
        
        // Update salary based on filter with compute 
        System.out.println("######################\n102 Key sal before update : "+concurrentMap.get(102).getSal());
        concurrentMap.compute(102, (key,value)->EightMappingFeatures.updateEmpSalary(value));
        double salary = concurrentMap.values().stream().filter(emp->emp.getId()==102).findAny().get().getSal();
        System.out.println("102 Key sal after update : "+ salary);
        
        // Use computeIfPresent to avoid NPE, when key not exist
        concurrentMap.computeIfPresent(107, (key,value)->EightMappingFeatures.updateEmpSalary(value));
        
        // Add employee when employee not found for given key
        Employee newEmp = new Employee("Kevin", 45000, 30, 107, 'M', "Dev");
        System.out.println("-----------------------\nSize before adding :"+concurrentMap.size());
        concurrentMap.putIfAbsent(107, newEmp);
        System.out.println(" Upon adding Size :"+concurrentMap.size());
        
        // To update all the recs in map
        System.out.println("######################\nSalary Before replace :"+concurrentMap.get(102).getSal());
        concurrentMap.replaceAll((key,val)->EightMappingFeatures.updateEmpSalary(val));
        System.out.println("Salary after replace :"+concurrentMap.get(102).getSal());
       
        // 'merge()' : Merge value for given key if key is present else add newly
        System.out.println("--------------------\nis Kevin rec exist ? "+employeeMap.containsKey(107));
        employeeMap.merge(107, newEmp, (preVal,newVal)->new Employee("Kevin", 45000, 30, 107, 'M', "Dev"));
        System.out.println("is Kevin rec exist now? "+employeeMap.containsKey(107));
        
	}
	private static Employee updateEmpSalary(Employee emp){
		emp.setSal(emp.getSal()*2);
		return emp;
	}
}
