package mypkg.bean;

import java.util.Arrays;

public class Member {
	private String id ;
	private String name ;
	private String password ;
	private int salary ;
	private String hiredate ; 
	private String gender ;
	
	// 체크 박스는 배열로 처리하도록 합니다.	
	private String[] hobby ;
	
	private String job ;
	private String zipcode ;
	private String address1 ;
	private String address2 ;
	private int mpoint ;	
	

	@Override
	public String toString() {
		return "Member [id=" + id + ", name=" + name + ", password=" + password + ", salary=" + salary + ", hiredate="
				+ hiredate + ", gender=" + gender + ", hobby=" + Arrays.toString(hobby) + ", job=" + job + ", zipcode="
				+ zipcode + ", address1=" + address1 + ", address2=" + address2 + ", mpoint=" + mpoint + "]";
	}

	public Member() {
	
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	public String getHiredate() {
		return hiredate;
	}
	public void setHiredate(String hiredate) {
		this.hiredate = hiredate;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getHobby() {
		String hobbies = "" ;
		if(this.hobby != null) {
			for (int i = 0; i < hobby.length; i++) {
				hobbies += hobby[i] + "," ; 
			}
		}
		if(hobbies.equals("") == false) {
			hobbies = hobbies.substring(0, hobbies.length() - 1) ; 
		}
		return hobbies ; 
	}

	public void setHobby(String[] hobby) {
		this.hobby = hobby;
	}
	
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public int getMpoint() {
		return mpoint;
	}
	public void setMpoint(int mpoint) {
		this.mpoint = mpoint;
	}
	
	
}