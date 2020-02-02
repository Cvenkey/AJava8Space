package com.ajava8.space;

public class Employee {
	private String name;
	private double sal;
	private int age;
	private int id;
	private char gender;
	private String dept;

	public Employee(String name, double sal, int age, int id, char gender, String dept) {
		super();
		this.name = name;
		this.sal = sal;
		this.age = age;
		this.id = id;
		this.gender = gender;
		this.dept = dept;
	}

	public String getName() {
		return name;
	}

	public double getSal() {
		return sal;
	}

	public int getAge() {
		return age;
	}

	public int getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSal(double sal) {
		this.sal = sal;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setId(int empId) {
		this.id = empId;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	@Override
	public String toString() {
		return "Employee [name=" + name + ", sal=" + sal + ", age=" + age + ", id=" + id + ",gender=" + gender
				+ ",dept=" + dept + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
