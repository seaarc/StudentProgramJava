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
			default: System.out.println("�ٽ� �õ��ϼ���."); break;
			}
		}
	}
	
	// �޴� ����
	private static int selectMenu(int type) {
		boolean flag = false;
		int menu = 0;
		
		while(!flag) {
			// ���� �޴� ���
			if(type == MENU) {				
				System.out.println("------------------------------------------------------------");
				System.out.println("\t \t   �л� ���� ���� ���α׷�");
				System.out.println("1. �Է�   2. ��ȸ   3. �˻�   4. ����   5. ����   6. ����   7. ����");
				System.out.println("------------------------------------------------------------");
			// �˻� �޴� ���
			}else if(type == SEARCHITEM) {
				System.out.println("1. �̸����� �˻�   2. �й����� �˻�   3. ���ư���");
			// ���� �޴� ���
			}else if(type == DELETEITEM) {
				System.out.println("1. ������ �л��� �й� �Է�   2. ���ư���");
			// ���� ���� �޴� ���
			}else if(type == UPDATEITEM) {
				System.out.println("1. ��ȸ�� ���� ����   2. ������ ���� ����   3. ����� ���� ����   4. ���ư���");
			}
			System.out.print("�޴� ���� >> ");
			
			try {				
				menu = Integer.parseInt(scan.nextLine());
			} catch(InputMismatchException e) {
				System.out.println("��ȣ�� �Է��ϼ���.");
				continue;
			} catch(Exception e) {
				System.out.println("��ȣ�� �Է��ϼ���.");
				continue;
			}
			
			// �޴��� ���� ��ȣ�� �Է��ϴ� ��츦 ����
			if(type == MENU && menu>=1 && menu <= 7) flag = true;
			else if(type == SEARCHITEM && menu>=1 && menu<=3) flag = true;
			else if(type == DELETEITEM && menu>=1 && menu<=2) flag = true;
			else if(type == UPDATEITEM && menu>=1 && menu<=4) flag = true;
			else System.out.println("�����ϴ� �׸� ��ȣ�� �Է��ϼ���.");
		}
		
		return menu;
	}
	
	// ������ �Է� : �л� �̸�, �й�, ����, ����, ���� ����(��ȸ��sociology, ������physics, �����linguistics), ����, ���, ���
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
		
		// �̸� �Է�
		while(true) {
			System.out.print("�̸� �Է� >> ");
			name = scan.nextLine();
			if(patternCheck(name, NAMEPATTERN)) break;
			else System.out.println("�ٽ� �Է��ϼ���.");
		}
		
		// �й� �Է�
		while(true) {
			System.out.print("�й� 10�ڸ� �Է� >> ");
			id = Integer.parseInt(scan.nextLine());
			if(patternCheck(Integer.toString(id), IDPATTERN)) break;
			else System.out.println("�ٽ� �Է��ϼ���.");
		}
		
		// �й� �ߺ� Ȯ��
		List<StudentModel> list = new ArrayList<StudentModel>();
		list = DBController.stuModelSelect();
		if(list.size()>0) {
			for(StudentModel data : list) {
				if(id==data.getId()) {
					System.out.println("�ߺ��Ǵ� �й��̴� �ٽ� �Է��ϼ���.");
					return;
				}
			}
		}
		
		// ���� �Է�
		System.out.print("���� �Է� >> ");
		age = Integer.parseInt(scan.nextLine());
		
		// ���� �Է�
		while(true) {
			System.out.print("���� �Է�(����/����) >> ");
			gender = scan.nextLine();
			if(gender.equals("����") || gender.equals("����")) break;
			else System.out.println("�ٽ� �Է��ϼ���.");
		}
		
		// ���� ���� �Է� : ��ȸ��sociology, ������physics, �����linguistics
		while(true) {
			System.out.print("��ȸ�� ���� >> ");
			sociology = Integer.parseInt(scan.nextLine());
			if(patternCheck(Integer.toString(sociology), SCOREPATTERN)) break;
			else System.out.println("�ٽ� �Է��ϼ���.");
		}
		
		while(true) {
			System.out.print("������ ���� >> ");
			physics = Integer.parseInt(scan.nextLine());
			if(patternCheck(Integer.toString(physics), SCOREPATTERN)) break;
			else System.out.println("�ٽ� �Է��ϼ���.");
		}
		
		while(true) {
			System.out.print("����� ���� >> ");
			linguistics = Integer.parseInt(scan.nextLine());
			if(patternCheck(Integer.toString(linguistics), SCOREPATTERN)) break;
			else System.out.println("�ٽ� �Է��ϼ���.");
		}
		
		// �����͸� ������ �� ����
		StudentModel stuModel = new StudentModel(name, id, age, gender, sociology, physics, linguistics, total, avg, grade);

		// ����, ���, ��� ����
		stuModel.calTotal();
		stuModel.calAvg();
		stuModel.calGrade();
		
		// �����ͺ��̽��� ����
		int result = DBController.stuModelIns(stuModel);
		
		if(result != 0) System.out.println(stuModel.getName() + " �߰� ����");
		else System.out.println(stuModel.getName() + " �߰� ����");

	}
	
	// ���� Ȯ��
	private static boolean patternCheck(String patternData, int patternType) {
		String filter = null;
		switch(patternType) {
			case NAMEPATTERN: filter = "^[��-�R]{2,5}$"; break;
			case IDPATTERN: filter = "([0-9]{10})"; break;
			case SCOREPATTERN: filter = "([0-9]{1,3})"; break;
		}
		
		Pattern pattern = Pattern.compile(filter);
		Matcher matcher = pattern.matcher(patternData);
		
		return matcher.matches();
	}

	// ������ ��ȸ
	private static void StuModelPrt() {
		List<StudentModel> list = new ArrayList<StudentModel>();
		
		list = DBController.stuModelSelect();
		
		// ���� ���� Ȯ��
		if(list.size()<=0) {
			System.out.println("�����Ͱ� �����ϴ�.");
			return;
		}
		
		// ��ü ������ ���
		for (StudentModel data : list) {
			System.out.println(data);
		}
	}

	// ������ �˻�
	private static void StuModelSearch() {
		final int NAMESEARCH = 1, IDSEARCH = 2, BACK = 3;
		List<StudentModel> list = new ArrayList<StudentModel>();
		String name = null;
		int id = 0;
		String searchData = null;	// �˻��� ������
		int searchType = 0;			// �˻��� �������� Ÿ��
		
		// �˻� �׸� ����
		searchType = selectMenu(SEARCHITEM);
		switch(searchType) {
			// �̸����� �˻�
			case NAMESEARCH:
				while(true) {
					System.out.print("�˻��� �л��� �̸� >> ");
					name = scan.nextLine();
					if(patternCheck(name, NAMEPATTERN)) break;
					else System.out.println("�ٽ� �Է��ϼ���.");
				}
				searchData = name;
				break;
			// �й����� �˻�
			case IDSEARCH:
				while(true) {
					System.out.print("�˻��� �л��� �й� >> ");
					id = Integer.parseInt(scan.nextLine());
					if(patternCheck(Integer.toString(id), IDPATTERN)) break;
					else System.out.println("�ٽ� �Է��ϼ���.");
				}
				searchData = String.valueOf(id);
				break;
			// ���ư���
			case BACK:
				System.out.println("�˻� ���");
				return;
		}
		
		// �����ͺ��̽����� ��ȸ
		list = DBController.stuModelSearch(searchData, searchType);

		// ���� ���� Ȯ��
		if(list.size()<=0) {
			System.out.println("�����Ͱ� �����ϴ�.");
			return;
		}
		
		// �˻� ��� ���� ���
		for (StudentModel data : list) {
			System.out.println(data);
		}
	}

	// ������ ����
	private static void StuModelDel() {
		final int IDSEARCH = 1, BACK = 2;
		int id = 0;
		String deleteData = null;	// ������ ������
		int deleteType = 0;			// �����Ϸ��� �˻��� �������� Ÿ��
		int result = 0;

		deleteType = selectMenu(DELETEITEM);
		switch(deleteType) {
			//�й����� �˻��Ͽ� ����
			case IDSEARCH: 	
				while(true) {
					System.out.print("������ �л��� �й� >> ");
					id = Integer.parseInt(scan.nextLine());
					if(patternCheck(Integer.toString(id), IDPATTERN)) break;
					else System.out.println("�ٽ� �Է��ϼ���.");
				}
				deleteData = String.valueOf(id);
				break;
			// ���ư���
			case BACK:
				System.out.println("���� ���");
				return;
			}
		
		// �����ͺ��̽����� ����
		result = DBController.stuModelDel(deleteData, deleteType);
		
		// ���� ����
		if(result != 0) System.out.println(deleteData + " �л� ���� �Ϸ�");
		else System.out.println(deleteData + " �л� ���� ����");
		
		return;
	}

	// ������ ����
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
		
		// �л� �˻�
		while(true) {
			System.out.print("���� ������ �л��� �й� �Է� >> ");
			id = Integer.parseInt(scan.nextLine());
			if(patternCheck(Integer.toString(id), IDPATTERN)) break;
			else System.out.println("�ٽ� �Է��ϼ���.");
		}
		
		// ��� �л� ���� ��ȸ
		list = DBController.stuModelSearch(String.valueOf(id), 2);
		if(list.size()<=0) {
			System.out.println("�����Ͱ� �����ϴ�.");
			return;
		}
		for (StudentModel data : list) {
			System.out.println(data);
		}
		
		StudentModel studentModel = list.get(0);

		// ���� ������ ���� ����
		category = selectMenu(UPDATEITEM);
		switch(category) {
			// ��ȸ�� ���� ����
			case SOC:
				while(true) {
					System.out.print("��ȸ�� ���� ���� >> ");
					sociology = Integer.parseInt(scan.nextLine());
					if(patternCheck(Integer.toString(sociology), SCOREPATTERN)) break;
					else System.out.println("�ٽ� �Է��ϼ���.");					
				}
				studentModel.setSociology(sociology);
				updateData = sociology;
				break;
			// ������ ���� ����
			case PHY:
				while(true) {
					System.out.print("������ ���� ���� >> ");
					physics = Integer.parseInt(scan.nextLine());
					if(patternCheck(Integer.toString(physics), SCOREPATTERN)) break;
					else System.out.println("�ٽ� �Է��ϼ���.");
				}
				studentModel.setPhysics(physics);
				updateData = physics;
				break;
			// ����� ���� ����
			case LING:
				while(true) {
					System.out.print("����� ���� ���� >> ");
					linguistics = Integer.parseInt(scan.nextLine());
					if(patternCheck(Integer.toString(linguistics), SCOREPATTERN)) break;
					else System.out.println("�ٽ� �Է��ϼ���.");
				}
				studentModel.setLinguistics(linguistics);
				updateData = linguistics;
				break;
			// ���ư���
			case BACK:
				System.out.println("���� ���");
				return;
		}
		
		// ������ ������ ���� ���� ����
		studentModel.calTotal();
		studentModel.calAvg();
		studentModel.calGrade();
		
		// �����ͺ��̽����� ����
		result = DBController.stuModelUpdate(studentModel, category, updateData);
		
		// ���� ����
		if(result != 0) System.out.println("���� �Ϸ�");
		else System.out.println("���� ����");
		
		return;
	}

	// ������ ����
	private static void StuModelSort() {
		int order = 0;
		boolean flag = false;
		List<StudentModel> list = new ArrayList<StudentModel>();
		
		// ���� ��� ����
		while(!flag) {
			System.out.println("������ �������� 1. ��������   2. ��������");
			System.out.print("���� ��� ���� >> ");
			
			try {				
				order = Integer.parseInt(scan.nextLine());
			} catch(InputMismatchException e) {
				System.out.println("���ڷ� �Է��ϼ���.");
				continue;
			} catch(Exception e) {
				System.out.println("���ڷ� �Է��ϼ���.");
				continue;
			}
			
			if(order == 1 || order == 2) flag = true;
			else System.out.println("�����ϴ� �׸� ��ȣ�� �Է��ϼ���.");
		}
		
		// �����ͺ��̽� ��ȸ - ����
		list = DBController.stuModelSort(order);
		
		// ���� ���� Ȯ��
		if(list.size() <=0) {
			System.out.println("�����Ͱ� �����ϴ�");
			return;
		}
		
		// ���ĵ� ����Ʈ ���
		for(StudentModel data : list) {
			System.out.println(data);
		}
	}
}
