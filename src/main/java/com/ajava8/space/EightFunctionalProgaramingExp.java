package com.ajava8.space;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EightFunctionalProgaramingExp {
	public static void main(String[] args) {
		
		System.out.println("Playing with Objects");
		List<Employee> employees = Employee.getEmployess();

		// find emp's whoe's salary lesser than 40000
		List<Employee> emps = employees.stream().filter(emp -> emp.getSal() < 40000).collect(Collectors.toList());
		System.out.println("Employees more than 40k salary :" + emps);

		// Applying more filters (sal and gender filter)
		emps = employees.stream().filter(emp -> emp.getSal() > 40000).filter(emp -> emp.getGender() == 'M').distinct()
				.collect(Collectors.toList());
		System.out.println("Male Employees more than 40k salary :" + emps);

		// Sort employees based on Id
		employees.stream().sorted(Comparator.comparingInt(Employee::getId)).forEach(System.out::print);
		employees.stream().sorted(Comparator.comparingInt(Employee::getId)).forEach(emp -> System.out.println(emp));

		//sort employees based on sal and display in descending order
		employees.stream().sorted(Comparator.comparingDouble(Employee::getSal).reversed())
				.collect(Collectors.toList()).forEach(e -> System.out.println(e.getName()+" "+e.getSal()));

		// find first three Employee names whoe's getting high salary
		employees.stream().sorted(Comparator.comparingDouble(Employee::getSal).reversed()).limit(3)
				.forEach(emp -> System.out.print("\n" + emp.getName() + " and" + emp.getSal()));

		// Even we can sort to collection object also
		Set<Employee> empSet = employees.stream().sorted(Comparator.comparingInt(Employee::getId))
				.collect(Collectors.toSet());
		System.out.print("\n" + empSet);

		// Find only Female emp's
		Set<Employee> femaleEmps = employees.stream().filter(emp -> emp.getGender() == 'F').collect(Collectors.toSet());
		femaleEmps.forEach(emp -> System.out.println(emp.getName() + " " + emp.getGender()));

		// Find female and male emp's count
		Map<Character, Long> cntByGender = employees.stream()
				.collect(Collectors.groupingBy(e -> e.getGender(), Collectors.counting()));
		System.out.println("Count of Male and Female : " + cntByGender);

		// Convert employees into unmodifiableset
		Set<Employee> employeeSet = employees.stream()
				.collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet));

		// employeeSet.remove(employeeSet.stream().findFirst()); //You can't
		System.out.println("Total no.of Employees with dups: " + employees.size());
		employees = employees.stream().distinct().collect(Collectors.toList());
		System.out.println("Total no.of Employees after remove dups" + employees.size());

		// Contains method
		Employee gaurav = new Employee("Gaurav", 25000, 26, 102, 'M', "IT");
		System.out.println("Gaurav is Available :" + employees.contains(gaurav));

		// Get all emp names specified by ',''
		String empNames = employees.stream().map(Employee::getName).collect(Collectors.joining(",", "[", "]"));
		System.out.println("Employee Names :" + empNames);

		// Group by gender
		Map<Character, List<Employee>> empsByGender = employees.stream()
				.collect(Collectors.groupingBy(Employee::getGender));

		System.out.println(empsByGender); // removed duplicate

		// Group by department and then gender
		Map<String,Map<Character,Long>>  genderCountByDepartment=employees.stream().collect(Collectors.groupingBy(Employee::getDept,
				Collectors.groupingBy(Employee::getGender,Collectors.counting())));
		genderCountByDepartment.entrySet().forEach(oe->{
			System.out.println("Department Name: "+oe.getKey());
			oe.getValue().entrySet().forEach(ie->{
				System.out.println("   Gender: "+ie.getKey()+" Count: "+ie.getValue());
			});
		});

		// On fly map creation from set and iterating
		employeeSet.stream().collect(Collectors.toMap(Employee::getId, Employee::getName)).entrySet()
				.forEach(emp -> System.out.println(emp.getValue()));

		// Group by dept, and key is String
		Map<String, List<Employee>> empsByDept = employees.stream().collect(Collectors.groupingBy(Employee::getDept));
		System.out.println(empsByDept);

		// Dividing stream’s elements into groups according to some predicate
		Map<Boolean, List<Employee>> empsBySal = employees.stream()
				.collect(Collectors.partitioningBy(emp -> emp.getSal() >= 50000));
		System.out.println(empsBySal);

		// Converting List to Map
		List<Employee> eMps = Employee.getEmployess();
		Map<Integer, String> empMap = eMps.stream().distinct()
				.collect(Collectors.toMap(Employee::getId, Employee::getName));
		System.out.println("Map is :" + empMap);
	
        // Using predicate
		Employee emp5 = new Employee("Deb", 900000, 65, 100, 'F', "Finance");
		System.out.println("Is Senior Citizen " + eMps.stream().anyMatch(isSeniorCitizen(emp5)));
		eMps.stream().forEach(e->System.out.println("Is Senior Citizen...."+hasEmployeeMoreThan50.test(e)));

		// Modify Employee sal based on Name
		CopyOnWriteArrayList<Employee> copyEmps = new CopyOnWriteArrayList<>();
		copyEmps.addAll(Employee.getEmployess());
		copyEmps.stream().distinct().forEach(e -> {
			if (e.getName().equals("Sudeep")) {
				e.setSal(16000);
			}
		});
		System.out.println("After modified" + copyEmps);

		//Update all employees salaries
		employees.stream().map(e->{
			e.setSal(e.getSal()+1);
			return e;
		}).collect(Collectors.toList());
        System.out.println("Salaries are updated: "+employees);

		// Modifying stream object and returning new employee with modified content
		Employee newEmp=  employees.stream().filter(e->e.getDept().equals("Finance"))
				.map(f->new Employee(f.getName().toUpperCase() , f.getSal() , f.getAge(), f.getId(),
						f.getGender(), f.getDept())).collect(Collectors.toList()).get(0);
		System.out.println("Modified emp is : "+newEmp);

		// Modify Using Peek method
		eMps = eMps.stream().peek(emp -> emp.setSal(200000)).collect(Collectors.toList());
		eMps.forEach(emp -> System.out.println(emp.getName() + " " + emp.getSal()));

		// Find prime numbers
		IntStream.range(2, 25).forEach(number -> System.out.print(number + " is Prime: " + isPrime(number) + " "));

		// List primes using noneMatch
		System.out.println();
		long startTime, endTime;

		startTime = System.currentTimeMillis();
		IntStream.range(2, 50)
				.filter(number -> (IntStream.rangeClosed(2, (int) Math.sqrt(number))
                        .noneMatch(divider -> (number % divider) == 0)))
				.boxed().collect(Collectors.toList()).forEach(num -> System.out.print(+num + " "));
		endTime = System.currentTimeMillis();
		System.out.println("Stream time taken: " + (endTime - startTime));

		// List primes using noneMatch and parallel streams
		startTime = System.currentTimeMillis();
		IntStream.range(2, 50).parallel()
				.filter(number -> (IntStream.rangeClosed(2, (int) Math.sqrt(number))
						.noneMatch(devider -> (number % devider) == 0) == true))
				.boxed().collect(Collectors.toList()).forEach(num -> System.out.print(+num + " "));
		endTime = System.currentTimeMillis();
		System.out.println("Parallel Stream time taken: " + (endTime - startTime));

		Predicate<Employee> salaryPredicate = e -> e.getSal() >= 50000;
		List<Employee> es = Employee.getEmployess();
	
		System.out.println("Predicate Result:" + es.stream().anyMatch(salaryPredicate));

		// Convert List of emps to Array of Emps
		Employee[] arrayOfEmpl = emps.stream().toArray(Employee[]::new); 
		               // 'new' will create empty Employee array and stream() will fill it
																			
		System.out.println("Size of Employee array : " + arrayOfEmpl.length);

		// use toArray, but returns Object type array
		System.out.println("Employee array size " + emps.stream().toArray().length);

		// Throw exception while you not found given emp.
		Employee empToFind = new Employee("Deb Shill", 900000, 65, 100, 'F', "Finance");
		employees = Employee.getEmployess();
		Employee emp = employees.stream().filter(e -> e.equals(empToFind)).findAny()
				.orElseThrow(NoSuchElementException::new);
		System.out.println("Employee found :" + emp);

		Optional<Employee> e = employees.stream().findAny();
		e.ifPresent(name->System.out.println(e.get().getName()));

		// Get boolean about existence of given emp
		boolean flag = employees.stream().anyMatch(e1 -> e1.equals(empToFind.getId() == e1.getId()));
		System.out.println("Result is :" + flag);

		// Print emp details 'ifPresent'
		employees.stream().limit(1).findFirst()
				.ifPresent(employee -> System.out.println("With 'ifPresent', details are :" + employee));

	   // Gender types
		employees.stream().map(Employee::getGender)
				.distinct().collect(Collectors.toList())
				.forEach(System.out::println);


		//Fill list with add All
		List<Integer> ints = new ArrayList<>();
		ints.addAll(IntStream.range(0,10).boxed().collect(Collectors.toList()));
	}

	public static Predicate<Employee> isSeniorCitizen(Employee e) {
		return emp -> emp.getAge() > 50 ? true : false;
	}

	public static Predicate<Employee> hasEmployeeMoreThan50 = new Predicate<Employee>() {
		@Override
		public boolean test(Employee t) {
			return t.getAge() > 50;
		}
	}; 

	private static boolean isPrime(int number) {
		return IntStream.rangeClosed(2, (int) Math.sqrt(number)).noneMatch(devider -> (number % devider) == 0) == true;
	}
}
