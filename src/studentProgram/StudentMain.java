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
			default: System.out.println("´Ù½Ã ½ÃµµÇÏ¼¼¿ä."); break;
			}
		}
	}
	
	// ¸Ş´º ¼±ÅÃ
	private static int selectMenu(int type) {
		boolean flag = false;
		int menu = 0;
		
		while(!flag) {
			// °ü¸® ¸Ş´º Ãâ·Â
			if(type == MENU) {				
				System.out.println("------------------------------------------------------------");
				System.out.println("\t \t   ÇĞ»ı ¼ºÀû °ü¸® ÇÁ·Î±×·¥");
				System.out.println("1. ÀÔ·Â   2. Á¶È¸   3. °Ë»ö   4. »èÁ¦   5. ¼öÁ¤   6. Á¤·Ä   7. Á¾·á");
				System.out.println("------------------------------------------------------------");
			// °Ë»ö ¸Ş´º Ãâ·Â
			}else if(type == SEARCHITEM) {
				System.out.println("1. ÀÌ¸§À¸·Î °Ë»ö   2. ÇĞ¹øÀ¸·Î °Ë»ö   3. µ¹¾Æ°¡±â");
			// »èÁ¦ ¸Ş´º Ãâ·Â
			}else if(type == DELETEITEM) {
				System.out.println("1. »èÁ¦ÇÒ ÇĞ»ıÀÇ ÇĞ¹ø ÀÔ·Â   2. µ¹¾Æ°¡±â");
			// Á¡¼ö Á¤Á¤ ¸Ş´º Ãâ·Â
			}else if(type == UPDATEITEM) {
				System.out.println("1. »çÈ¸ÇĞ Á¡¼ö Á¤Á¤   2. ¹°¸®ÇĞ Á¡¼ö Á¤Á¤   3. ¾ğ¾îÇĞ Á¡¼ö Á¤Á¤   4. µ¹¾Æ°¡±â");
			}
			System.out.print("¸Ş´º ¼±ÅÃ >> ");
			
			try {				
				menu = Integer.parseInt(scan.nextLine());
			} catch(InputMismatchException e) {
				System.out.println("¹øÈ£·Î ÀÔ·ÂÇÏ¼¼¿ä.");
				continue;
			} catch(Exception e) {
				System.out.println("¹øÈ£·Î ÀÔ·ÂÇÏ¼¼¿ä.");
				continue;
			}
			
			// ¸Ş´º¿¡ ¾ø´Â ¹øÈ£¸¦ ÀÔ·ÂÇÏ´Â °æ¿ì¸¦ ¹æÁö
			if(type == MENU && menu>=1 && menu <= 7) flag = true;
			else if(type == SEARCHITEM && menu>=1 && menu<=3) flag = true;
			else if(type == DELETEITEM && menu>=1 && menu<=2) flag = true;
			else if(type == UPDATEITEM && menu>=1 && menu<=4) flag = true;
			else System.out.println("Á¸ÀçÇÏ´Â Ç×¸ñ ¹øÈ£¸¦ ÀÔ·ÂÇÏ¼¼¿ä.");
		}
		
		return menu;
	}
	
	// µ¥ÀÌÅÍ ÀÔ·Â : ÇĞ»ı ÀÌ¸§, ÇĞ¹ø, ³ªÀÌ, ¼ºº°, °ú¸ñº° Á¡¼ö(»çÈ¸ÇĞsociology, ¹°¸®ÇĞphysics, ¾ğ¾îÇĞlinguistics), ÃÑÁ¡, Æò±Õ, µî±Ş
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
		
		// ÀÌ¸§ ÀÔ·Â
		while(true) {
			System.out.print("ÀÌ¸§ ÀÔ·Â >> ");
			name = scan.nextLine();
			if(patternCheck(name, NAMEPATTERN)) break;
			else System.out.println("´Ù½Ã ÀÔ·ÂÇÏ¼¼¿ä.");
		}
		
		// ÇĞ¹ø ÀÔ·Â
		while(true) {
			System.out.print("ÇĞ¹ø 10ÀÚ¸® ÀÔ·Â >> ");
			id = Integer.parseInt(scan.nextLine());
			if(patternCheck(Integer.toString(id), IDPATTERN)) break;
			else System.out.println("´Ù½Ã ÀÔ·ÂÇÏ¼¼¿ä.");
		}
		
		// ÇĞ¹ø Áßº¹ È®ÀÎ
		List<StudentModel> list = new ArrayList<StudentModel>();
		list = DBController.stuModelSelect();
		if(list.size()>0) {
			for(StudentModel data : list) {
				if(id==data.getId()) {
					System.out.println("Áßº¹µÇ´Â ÇĞ¹øÀÌ´Ï ´Ù½Ã ÀÔ·ÂÇÏ¼¼¿ä.");
					return;
				}
			}
		}
		
		// ³ªÀÌ ÀÔ·Â
		System.out.print("³ªÀÌ ÀÔ·Â >> ");
		age = Integer.parseInt(scan.nextLine());
		
		// ¼ºº° ÀÔ·Â
		while(true) {
			System.out.print("¼ºº° ÀÔ·Â(¿©ÀÚ/³²ÀÚ) >> ");
			gender = scan.nextLine();
			if(gender.equals("¿©ÀÚ") || gender.equals("³²ÀÚ")) break;
			else System.out.println("´Ù½Ã ÀÔ·ÂÇÏ¼¼¿ä.");
		}
		
		// °ú¸ñº° Á¡¼ö ÀÔ·Â : »çÈ¸ÇĞsociology, ¹°¸®ÇĞphysics, ¾ğ¾îÇĞlinguistics
		while(true) {
			System.out.print("»çÈ¸ÇĞ Á¡¼ö >> ");
			sociology = Integer.parseInt(scan.nextLine());
			if(patternCheck(Integer.toString(sociology), SCOREPATTERN)) break;
			else System.out.println("´Ù½Ã ÀÔ·ÂÇÏ¼¼¿ä.");
		}
		
		while(true) {
			System.out.print("¹°¸®ÇĞ Á¡¼ö >> ");
			physics = Integer.parseInt(scan.nextLine());
			if(patternCheck(Integer.toString(physics), SCOREPATTERN)) break;
			else System.out.println("´Ù½Ã ÀÔ·ÂÇÏ¼¼¿ä.");
		}
		
		while(true) {
			System.out.print("¾ğ¾îÇĞ Á¡¼ö >> ");
			linguistics = Integer.parseInt(scan.nextLine());
			if(patternCheck(Integer.toString(linguistics), SCOREPATTERN)) break;
			else System.out.println("´Ù½Ã ÀÔ·ÂÇÏ¼¼¿ä.");
		}
		
		// µ¥ÀÌÅÍ¸¦ »ğÀÔÇÒ ¸ğµ¨ »ı¼º
		StudentModel stuModel = new StudentModel(name, id, age, gender, sociology, physics, linguistics, total, avg, grade);

		// ÃÑÁ¡, Æò±Õ, µî±Ş »êÁ¤
		stuModel.calTotal();
		stuModel.calAvg();
		stuModel.calGrade();
		
		// µ¥ÀÌÅÍº£ÀÌ½º¿¡ ÀúÀå
		int result = DBController.stuModelIns(stuModel);
		
		if(result != 0) System.out.println(stuModel.getName() + " Ãß°¡ ¼º°ø");
		else System.out.println(stuModel.getName() + " Ãß°¡ ½ÇÆĞ");

	}
	
	// ÆĞÅÏ È®ÀÎ
	private static boolean patternCheck(String patternData, int patternType) {
		String filter = null;
		switch(patternType) {
			case NAMEPATTERN: filter = "^[°¡-ÆR]{2,5}$"; break;
			case IDPATTERN: filter = "([0-9]{10})"; break;
			case SCOREPATTERN: filter = "([0-9]{1,3})"; break;
		}
		
		Pattern pattern = Pattern.compile(filter);
		Matcher matcher = pattern.matcher(patternData);
		
		return matcher.matches();
	}

	// µ¥ÀÌÅÍ Á¶È¸
	private static void StuModelPrt() {
		List<StudentModel> list = new ArrayList<StudentModel>();
		
		list = DBController.stuModelSelect();
		
		// Á¸Àç ¿©ºÎ È®ÀÎ
		if(list.size()<=0) {
			System.out.println("µ¥ÀÌÅÍ°¡ ¾ø½À´Ï´Ù.");
			return;
		}
		
		// ÀüÃ¼ µ¥ÀÌÅÍ Ãâ·Â
		for (StudentModel data : list) {
			System.out.println(data);
		}
	}

	// µ¥ÀÌÅÍ °Ë»ö
	private static void StuModelSearch() {
		final int NAMESEARCH = 1, IDSEARCH = 2, BACK = 3;
		List<StudentModel> list = new ArrayList<StudentModel>();
		String name = null;
		int id = 0;
		String searchData = null;	// °Ë»öµÈ µ¥ÀÌÅÍ
		int searchType = 0;			// °Ë»öµÈ µ¥ÀÌÅÍÀÇ Å¸ÀÔ
		
		// °Ë»ö Ç×¸ñ ¼±ÅÃ
		searchType = selectMenu(SEARCHITEM);
		switch(searchType) {
			// ÀÌ¸§À¸·Î °Ë»ö
			case NAMESEARCH:
				while(true) {
					System.out.print("°Ë»öÇÒ ÇĞ»ıÀÇ ÀÌ¸§ >> ");
					name = scan.nextLine();
					if(patternCheck(name, NAMEPATTERN)) break;
					else System.out.println("´Ù½Ã ÀÔ·ÂÇÏ¼¼¿ä.");
				}
				searchData = name;
				break;
			// ÇĞ¹øÀ¸·Î °Ë»ö
			case IDSEARCH:
				while(true) {
					System.out.print("°Ë»öÇÒ ÇĞ»ıÀÇ ÇĞ¹ø >> ");
					id = Integer.parseInt(scan.nextLine());
					if(patternCheck(Integer.toString(id), IDPATTERN)) break;
					else System.out.println("´Ù½Ã ÀÔ·ÂÇÏ¼¼¿ä.");
				}
				searchData = String.valueOf(id);
				break;
			// µ¹¾Æ°¡±â
			case BACK:
				System.out.println("°Ë»ö Ãë¼Ò");
				return;
		}
		
		// µ¥ÀÌÅÍº£ÀÌ½º¿¡¼­ Á¶È¸
		list = DBController.stuModelSearch(searchData, searchType);

		// Á¸Àç ¿©ºÎ È®ÀÎ
		if(list.size()<=0) {
			System.out.println("µ¥ÀÌÅÍ°¡ ¾ø½À´Ï´Ù.");
			return;
		}
		
		// °Ë»ö ´ë»ó Á¤º¸ Ãâ·Â
		for (StudentModel data : list) {
			System.out.println(data);
		}
	}

	// µ¥ÀÌÅÍ »èÁ¦
	private static void StuModelDel() {
		final int IDSEARCH = 1, BACK = 2;
		int id = 0;
		String deleteData = null;	// »èÁ¦ÇÒ µ¥ÀÌÅÍ
		int deleteType = 0;			// »èÁ¦ÇÏ·Á°í °Ë»öÇÑ µ¥ÀÌÅÍÀÇ Å¸ÀÔ
		int result = 0;

		deleteType = selectMenu(DELETEITEM);
		switch(deleteType) {
			//ÇĞ¹øÀ¸·Î °Ë»öÇÏ¿© »èÁ¦
			case IDSEARCH: 	
				while(true) {
					System.out.print("»èÁ¦ÇÒ ÇĞ»ıÀÇ ÇĞ¹ø >> ");
					id = Integer.parseInt(scan.nextLine());
					if(patternCheck(Integer.toString(id), IDPATTERN)) break;
					else System.out.println("´Ù½Ã ÀÔ·ÂÇÏ¼¼¿ä.");
				}
				deleteData = String.valueOf(id);
				break;
			// µ¹¾Æ°¡±â
			case BACK:
				System.out.println("»èÁ¦ Ãë¼Ò");
				return;
			}
		
		// µ¥ÀÌÅÍº£ÀÌ½º¿¡¼­ »èÁ¦
		result = DBController.stuModelDel(deleteData, deleteType);
		
		// ¼º°ø ¿©ºÎ
		if(result != 0) System.out.println(deleteData + " ÇĞ»ı »èÁ¦ ¿Ï·á");
		else System.out.println(deleteData + " ÇĞ»ı »èÁ¦ ½ÇÆĞ");
		
		return;
	}

	// µ¥ÀÌÅÍ ¼öÁ¤
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
		
		// ÇĞ»ı °Ë»ö
		while(true) {
			System.out.print("¼ºÁ¤ Á¤Á¤ÇÒ ÇĞ»ıÀÇ ÇĞ¹ø ÀÔ·Â >> ");
			id = Integer.parseInt(scan.nextLine());
			if(patternCheck(Integer.toString(id), IDPATTERN)) break;
			else System.out.println("´Ù½Ã ÀÔ·ÂÇÏ¼¼¿ä.");
		}
		
		// ´ë»ó ÇĞ»ı Á¤º¸ Á¶È¸
		list = DBController.stuModelSearch(String.valueOf(id), 2);
		if(list.size()<=0) {
			System.out.println("µ¥ÀÌÅÍ°¡ ¾ø½À´Ï´Ù.");
			return;
		}
		for (StudentModel data : list) {
			System.out.println(data);
		}
		
		StudentModel studentModel = list.get(0);

		// ¼ºÀû Á¤Á¤ÇÒ °ú¸ñ ¼±ÅÃ
		category = selectMenu(UPDATEITEM);
		switch(category) {
			// »çÈ¸ÇĞ Á¡¼ö Á¤Á¤
			case SOC:
				while(true) {
					System.out.print("»çÈ¸ÇĞ Á¡¼ö Á¤Á¤ >> ");
					sociology = Integer.parseInt(scan.nextLine());
					if(patternCheck(Integer.toString(sociology), SCOREPATTERN)) break;
					else System.out.println("´Ù½Ã ÀÔ·ÂÇÏ¼¼¿ä.");					
				}
				studentModel.setSociology(sociology);
				updateData = sociology;
				break;
			// ¹°¸®ÇĞ Á¡¼ö Á¤Á¤
			case PHY:
				while(true) {
					System.out.print("¹°¸®ÇĞ Á¡¼ö Á¤Á¤ >> ");
					physics = Integer.parseInt(scan.nextLine());
					if(patternCheck(Integer.toString(physics), SCOREPATTERN)) break;
					else System.out.println("´Ù½Ã ÀÔ·ÂÇÏ¼¼¿ä.");
				}
				studentModel.setPhysics(physics);
				updateData = physics;
				break;
			// ¾ğ¾îÇĞ Á¡¼ö Á¤Á¤
			case LING:
				while(true) {
					System.out.print("¾ğ¾îÇĞ Á¡¼ö Á¤Á¤ >> ");
					linguistics = Integer.parseInt(scan.nextLine());
					if(patternCheck(Integer.toString(linguistics), SCOREPATTERN)) break;
					else System.out.println("´Ù½Ã ÀÔ·ÂÇÏ¼¼¿ä.");
				}
				studentModel.setLinguistics(linguistics);
				updateData = linguistics;
				break;
			// µ¹¾Æ°¡±â
			case BACK:
				System.out.println("¼öÁ¤ Ãë¼Ò");
				return;
		}
		
		// ¼öÁ¤ÇÑ Á¡¼ö¿¡ µû¶ó ¼ºÀû Á¤Á¤
		studentModel.calTotal();
		studentModel.calAvg();
		studentModel.calGrade();
		
		// µ¥ÀÌÅÍº£ÀÌ½º¿¡¼­ ¼öÁ¤
		result = DBController.stuModelUpdate(studentModel, category, updateData);
		
		// ¼º°ø ¿©ºÎ
		if(result != 0) System.out.println("Á¤Á¤ ¿Ï·á");
		else System.out.println("Á¤Á¤ ½ÇÆĞ");
		
		return;
	}

	// µ¥ÀÌÅÍ Á¤·Ä
	private static void StuModelSort() {
		int order = 0;
		boolean flag = false;
		List<StudentModel> list = new ArrayList<StudentModel>();
		
		// Á¤·Ä ¹æ½Ä ¼±ÅÃ
		while(!flag) {
			System.out.println("ÃÑÁ¡À» ±âÁØÀ¸·Î 1. ³»¸²Â÷¼ø   2. ¿À¸§Â÷¼ø");
			System.out.print("Á¤·Ä ¹æ½Ä ¼±ÅÃ >> ");
			
			try {				
				order = Integer.parseInt(scan.nextLine());
			} catch(InputMismatchException e) {
				System.out.println("¼ıÀÚ·Î ÀÔ·ÂÇÏ¼¼¿ä.");
				continue;
			} catch(Exception e) {
				System.out.println("¼ıÀÚ·Î ÀÔ·ÂÇÏ¼¼¿ä.");
				continue;
			}
			
			if(order == 1 || order == 2) flag = true;
			else System.out.println("Á¸ÀçÇÏ´Â Ç×¸ñ ¹øÈ£¸¦ ÀÔ·ÂÇÏ¼¼¿ä.");
		}
		
		// µ¥ÀÌÅÍº£ÀÌ½º Á¶È¸ - Á¤·Ä
		list = DBController.stuModelSort(order);
		
		// Á¸Àç ¿©ºÎ È®ÀÎ
		if(list.size() <=0) {
			System.out.println("µ¥ÀÌÅÍ°¡ ¾ø½À´Ï´Ù");
			return;
		}
		
		// Á¤·ÄµÈ ¸®½ºÆ® Ãâ·Â
		for(StudentModel data : list) {
			System.out.println(data);
		}
	}
}
