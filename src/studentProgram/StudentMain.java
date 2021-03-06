package studentProgram;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StudentMain {

	public static Scanner scan = new Scanner(System.in);
	public static final int SUBJECTNUM = 3;
	public static final int INSERT = 1, PRINT = 2, SEARCH = 3, DELETE = 4, UPDATE = 5, SORT = 6, EXIT = 7;
	public static final int NAMEPATTERN = 1, IDPATTERN = 2, SCOREPATTERN = 3;
	public static final int MENU = 1, SEARCHITEM = 2, DELETEITEM = 3, UPDATEITEM = 4;
	
	public static void main(String[] args) {
		boolean flag = false;
		
		while(!flag) {			
			int menu = selectMenu(MENU);
			switch(menu) {
			case INSERT: StuModelIns(); break;
			case PRINT: StuModelPrt(); break;
			case SEARCH: StuModelSearch(); break;
			case DELETE: StuModelDel(); break;
			case UPDATE: StuModelUpdate(); break;
			case SORT: StuModelSort(); break;
			case EXIT: flag = true; break;
			default: System.out.println("다시 시도하세요."); break;
			}
		}
	}
	
	// 메뉴 선택
	private static int selectMenu(int type) {
		boolean flag = false;
		int menu = 0;
		
		while(!flag) {
			// 관리 메뉴 출력
			if(type == MENU) {				
				System.out.println("------------------------------------------------------------");
				System.out.println("\t \t   학생 성적 관리 프로그램");
				System.out.println("1. 입력   2. 조회   3. 검색   4. 삭제   5. 수정   6. 정렬   7. 종료");
				System.out.println("------------------------------------------------------------");
			// 검색 메뉴 출력
			}else if(type == SEARCHITEM) {
				System.out.println("1. 이름으로 검색   2. 학번으로 검색   3. 돌아가기");
			// 삭제 메뉴 출력
			}else if(type == DELETEITEM) {
				System.out.println("1. 삭제할 학생의 학번 입력   2. 돌아가기");
			// 점수 정정 메뉴 출력
			}else if(type == UPDATEITEM) {
				System.out.println("1. 사회학 점수 정정   2. 물리학 점수 정정   3. 언어학 점수 정정   4. 돌아가기");
			}
			System.out.print("메뉴 선택 >> ");
			
			try {				
				menu = Integer.parseInt(scan.nextLine());
			} catch(InputMismatchException e) {
				System.out.println("번호로 입력하세요.");
				continue;
			} catch(Exception e) {
				System.out.println("번호로 입력하세요.");
				continue;
			}
			
			// 메뉴에 없는 번호를 입력하는 경우를 방지
			if(type == MENU && menu>=1 && menu <= 7) flag = true;
			else if(type == SEARCHITEM && menu>=1 && menu<=3) flag = true;
			else if(type == DELETEITEM && menu>=1 && menu<=2) flag = true;
			else if(type == UPDATEITEM && menu>=1 && menu<=4) flag = true;
			else System.out.println("존재하는 항목 번호를 입력하세요.");
		}
		
		return menu;
	}
	
	// 데이터 입력 : 학생 이름, 학번, 나이, 성별, 과목별 점수(사회학sociology, 물리학physics, 언어학linguistics), 총점, 평균, 등급
	private static void StuModelIns() {
		String name = null;
		int id = 0;
		int age = 0;
		String gender = null;
		int sociology = 0;
		int physics = 0;
		int linguistics = 0;
		int total = 0;
		double avg = 0.0;
		char grade = '\u0000';
		
		// 이름 입력
		while(true) {
			System.out.print("이름 입력 >> ");
			name = scan.nextLine();
			if(patternCheck(name, NAMEPATTERN)) break;
			else System.out.println("다시 입력하세요.");
		}
		
		// 학번 입력
		while(true) {
			System.out.print("학번 10자리 입력 >> ");
			id = Integer.parseInt(scan.nextLine());
			if(patternCheck(Integer.toString(id), IDPATTERN)) break;
			else System.out.println("다시 입력하세요.");
		}
		
		// 학번 중복 확인
		List<StudentModel> list = new ArrayList<StudentModel>();
		list = DBController.stuModelSelect();
		if(list.size()>0) {
			for(StudentModel data : list) {
				if(id==data.getId()) {
					System.out.println("중복되는 학번이니 다시 입력하세요.");
					return;
				}
			}
		}
		
		// 나이 입력
		System.out.print("나이 입력 >> ");
		age = Integer.parseInt(scan.nextLine());
		
		// 성별 입력
		while(true) {
			System.out.print("성별 입력(여자/남자) >> ");
			gender = scan.nextLine();
			if(gender.equals("여자") || gender.equals("남자")) break;
			else System.out.println("다시 입력하세요.");
		}
		
		// 과목별 점수 입력 : 사회학sociology, 물리학physics, 언어학linguistics
		while(true) {
			System.out.print("사회학 점수 >> ");
			sociology = Integer.parseInt(scan.nextLine());
			if(patternCheck(Integer.toString(sociology), SCOREPATTERN)) break;
			else System.out.println("다시 입력하세요.");
		}
		
		while(true) {
			System.out.print("물리학 점수 >> ");
			physics = Integer.parseInt(scan.nextLine());
			if(patternCheck(Integer.toString(physics), SCOREPATTERN)) break;
			else System.out.println("다시 입력하세요.");
		}
		
		while(true) {
			System.out.print("언어학 점수 >> ");
			linguistics = Integer.parseInt(scan.nextLine());
			if(patternCheck(Integer.toString(linguistics), SCOREPATTERN)) break;
			else System.out.println("다시 입력하세요.");
		}
		
		// 데이터를 삽입할 모델 생성
		StudentModel stuModel = new StudentModel(name, id, age, gender, sociology, physics, linguistics, total, avg, grade);

		// 총점, 평균, 등급 산정
		stuModel.calTotal();
		stuModel.calAvg();
		stuModel.calGrade();
		
		// 데이터베이스에 저장
		int result = DBController.stuModelIns(stuModel);
		
		if(result != 0) System.out.println(stuModel.getName() + " 추가 성공");
		else System.out.println(stuModel.getName() + " 추가 실패");

	}
	
	// 패턴 확인
	private static boolean patternCheck(String patternData, int patternType) {
		String filter = null;
		switch(patternType) {
			case NAMEPATTERN: filter = "^[가-힣]{2,5}$"; break;
			case IDPATTERN: filter = "([0-9]{10})"; break;
			case SCOREPATTERN: filter = "([0-9]{1,3})"; break;
		}
		
		Pattern pattern = Pattern.compile(filter);
		Matcher matcher = pattern.matcher(patternData);
		
		return matcher.matches();
	}

	// 데이터 조회
	private static void StuModelPrt() {
		List<StudentModel> list = new ArrayList<StudentModel>();
		
		list = DBController.stuModelSelect();
		
		// 존재 여부 확인
		if(list.size()<=0) {
			System.out.println("데이터가 없습니다.");
			return;
		}
		
		// 전체 데이터 출력
		for (StudentModel data : list) {
			System.out.println(data);
		}
	}

	// 데이터 검색
	private static void StuModelSearch() {
		final int NAMESEARCH = 1, IDSEARCH = 2, BACK = 3;
		List<StudentModel> list = new ArrayList<StudentModel>();
		String name = null;
		int id = 0;
		String searchData = null;	// 검색된 데이터
		int searchType = 0;			// 검색된 데이터의 타입
		
		// 검색 항목 선택
		searchType = selectMenu(SEARCHITEM);
		switch(searchType) {
			// 이름으로 검색
			case NAMESEARCH:
				while(true) {
					System.out.print("검색할 학생의 이름 >> ");
					name = scan.nextLine();
					if(patternCheck(name, NAMEPATTERN)) break;
					else System.out.println("다시 입력하세요.");
				}
				searchData = name;
				break;
			// 학번으로 검색
			case IDSEARCH:
				while(true) {
					System.out.print("검색할 학생의 학번 >> ");
					id = Integer.parseInt(scan.nextLine());
					if(patternCheck(Integer.toString(id), IDPATTERN)) break;
					else System.out.println("다시 입력하세요.");
				}
				searchData = String.valueOf(id);
				break;
			// 돌아가기
			case BACK:
				System.out.println("검색 취소");
				return;
		}
		
		// 데이터베이스에서 조회
		list = DBController.stuModelSearch(searchData, searchType);

		// 존재 여부 확인
		if(list.size()<=0) {
			System.out.println("데이터가 없습니다.");
			return;
		}
		
		// 검색 대상 정보 출력
		for (StudentModel data : list) {
			System.out.println(data);
		}
	}

	// 데이터 삭제
	private static void StuModelDel() {
		final int IDSEARCH = 1, BACK = 2;
		int id = 0;
		String deleteData = null;	// 삭제할 데이터
		int deleteType = 0;			// 삭제하려고 검색한 데이터의 타입
		int result = 0;

		deleteType = selectMenu(DELETEITEM);
		switch(deleteType) {
			//학번으로 검색하여 삭제
			case IDSEARCH: 	
				while(true) {
					System.out.print("삭제할 학생의 학번 >> ");
					id = Integer.parseInt(scan.nextLine());
					if(patternCheck(Integer.toString(id), IDPATTERN)) break;
					else System.out.println("다시 입력하세요.");
				}
				deleteData = String.valueOf(id);
				break;
			// 돌아가기
			case BACK:
				System.out.println("삭제 취소");
				return;
			}
		
		// 데이터베이스에서 삭제
		result = DBController.stuModelDel(deleteData, deleteType);
		
		// 성공 여부
		if(result != 0) System.out.println(deleteData + " 학생 삭제 완료");
		else System.out.println(deleteData + " 학생 삭제 실패");
		
		return;
	}

	// 데이터 수정
	private static void StuModelUpdate() {
		final int SOC = 1, PHY = 2, LING = 3, BACK = 4;
		int id = 0;
		int sociology = 0;
		int physics = 0;
		int linguistics = 0;

		List<StudentModel> list = new ArrayList<StudentModel>();
		int category = 0;
		int updateData = 0;
		int result = 0;
		
		// 학생 검색
		while(true) {
			System.out.print("성정 정정할 학생의 학번 입력 >> ");
			id = Integer.parseInt(scan.nextLine());
			if(patternCheck(Integer.toString(id), IDPATTERN)) break;
			else System.out.println("다시 입력하세요.");
		}
		
		// 대상 학생 정보 조회
		list = DBController.stuModelSearch(String.valueOf(id), 2);
		if(list.size()<=0) {
			System.out.println("데이터가 없습니다.");
			return;
		}
		for (StudentModel data : list) {
			System.out.println(data);
		}
		
		StudentModel studentModel = list.get(0);

		// 성적 정정할 과목 선택
		category = selectMenu(UPDATEITEM);
		switch(category) {
			// 사회학 점수 정정
			case SOC:
				while(true) {
					System.out.print("사회학 점수 정정 >> ");
					sociology = Integer.parseInt(scan.nextLine());
					if(patternCheck(Integer.toString(sociology), SCOREPATTERN)) break;
					else System.out.println("다시 입력하세요.");					
				}
				studentModel.setSociology(sociology);
				updateData = sociology;
				break;
			// 물리학 점수 정정
			case PHY:
				while(true) {
					System.out.print("물리학 점수 정정 >> ");
					physics = Integer.parseInt(scan.nextLine());
					if(patternCheck(Integer.toString(physics), SCOREPATTERN)) break;
					else System.out.println("다시 입력하세요.");
				}
				studentModel.setPhysics(physics);
				updateData = physics;
				break;
			// 언어학 점수 정정
			case LING:
				while(true) {
					System.out.print("언어학 점수 정정 >> ");
					linguistics = Integer.parseInt(scan.nextLine());
					if(patternCheck(Integer.toString(linguistics), SCOREPATTERN)) break;
					else System.out.println("다시 입력하세요.");
				}
				studentModel.setLinguistics(linguistics);
				updateData = linguistics;
				break;
			// 돌아가기
			case BACK:
				System.out.println("수정 취소");
				return;
		}
		
		// 수정한 점수에 따라 성적 정정
		studentModel.calTotal();
		studentModel.calAvg();
		studentModel.calGrade();
		
		// 데이터베이스에서 수정
		result = DBController.stuModelUpdate(studentModel, category, updateData);
		
		// 성공 여부
		if(result != 0) System.out.println("정정 완료");
		else System.out.println("정정 실패");
		
		return;
	}

	// 데이터 정렬
	private static void StuModelSort() {
		int order = 0;
		boolean flag = false;
		List<StudentModel> list = new ArrayList<StudentModel>();
		
		// 정렬 방식 선택
		while(!flag) {
			System.out.println("총점을 기준으로 1. 내림차순   2. 오름차순");
			System.out.print("정렬 방식 선택 >> ");
			
			try {				
				order = Integer.parseInt(scan.nextLine());
			} catch(InputMismatchException e) {
				System.out.println("숫자로 입력하세요.");
				continue;
			} catch(Exception e) {
				System.out.println("숫자로 입력하세요.");
				continue;
			}
			
			if(order == 1 || order == 2) flag = true;
			else System.out.println("존재하는 항목 번호를 입력하세요.");
		}
		
		// 데이터베이스 조회 - 정렬
		list = DBController.stuModelSort(order);
		
		// 존재 여부 확인
		if(list.size() <=0) {
			System.out.println("데이터가 없습니다");
			return;
		}
		
		// 정렬된 리스트 출력
		for(StudentModel data : list) {
			System.out.println(data);
		}
	}
}
