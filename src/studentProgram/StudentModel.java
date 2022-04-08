package studentProgram;

import java.io.Serializable;
import java.util.Objects;

public class StudentModel implements Comparable<Object>, Serializable {
	
	// 과목 수
	public static final int SUBJECT_CNT = 3;
	
	// 인스턴스 멤버 변수
	private String name;
	private int id;
	private int age;
	private String gender;
	private int sociology;
	private int physics;
	private int linguistics;
	private int total;
	private double avg;
	private char grade;

	// 생성자
	public StudentModel(String name, int id, int age, String gender, int sociology, int physics, int linguistics,
			int total, double avg, char grade) {
		super();
		this.name = name;
		this.id = id;
		this.age = age;
		this.gender = gender;
		this.sociology = sociology;
		this.physics = physics;
		this.linguistics = linguistics;
		this.total = total;
		this.avg = avg;
		this.grade = grade;
	}
	
	// 인스턴스 멤버 함수 : 총점 계산
	public void calTotal() {
		this.total = this.sociology + this.physics + this.linguistics;
	}
	
	// 인스턴스 멤버 함수 : 평균 계산
	public void calAvg() {
		this.avg = this.total / (double)SUBJECT_CNT;
	}
	
	// 인스턴스 멤버 함수 : 등급 계산
	public void calGrade() {
		if(this.avg>=90.0) {
			this.grade = 'A';
		}else if(this.avg>=80.0) {
			this.grade = 'B';
		}else if(this.avg>=70.0) {
			this.grade = 'C';
		}else if(this.avg>=60.0) {
			this.grade = 'D';
		}else {
			this.grade = 'F';
		}
	}

	// hashcode : id
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
	// equals : id
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof StudentModel)) {
			throw new IllegalArgumentException("StudentModel Class Not Found");
		}
		StudentModel stuModel = (StudentModel)obj;
		
		return this.id == stuModel.id;
	}
	
	// compareTo : total
	@Override
	public int compareTo(Object obj) {
		if(!(obj instanceof StudentModel)) {
			throw new IllegalArgumentException("StudentModel Class Not Found");
		}
		StudentModel stuModel = (StudentModel)obj;
		
		return (this.total - stuModel.total);
	}

	// toString
	@Override
	public String toString() {
		return name + "\t" + id + "\t" + age + "세\t" + gender + "\t sociology = "
				+ String.format("%-5s", sociology) + "\t physics = " + String.format("%-5s", physics) + "\t linguistics = " + String.format("%-5s", linguistics)
				+ "\t 총 " + total + "점\t 평균 " + String.format("%6.2f", avg) + "\t" + grade;
	}

	// getters and setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getSociology() {
		return sociology;
	}

	public void setSociology(int sociology) {
		this.sociology = sociology;
	}

	public int getPhysics() {
		return physics;
	}

	public void setPhysics(int physics) {
		this.physics = physics;
	}

	public int getLinguistics() {
		return linguistics;
	}

	public void setLinguistics(int linguistics) {
		this.linguistics = linguistics;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public double getAvg() {
		return avg;
	}

	public void setAvg(double avg) {
		this.avg = avg;
	}

	public char getGrade() {
		return grade;
	}

	public void setGrade(char grade) {
		this.grade = grade;
	}
}
