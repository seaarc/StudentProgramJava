package studentProgram;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBController {

	// 데이터베이스 입력 : insert 쿼리
	public static int stuModelIns(StudentModel stuModel) {
		// 입력을 확인할 반환값
		int result = 0;
		// SQL 구문을 실행하는 객체를 받는 참조변수
		PreparedStatement ps = null;

		// 데이터베이스 연결
		Connection con = DBUtility.getConnection();
		if(con == null) {
			System.out.println("DB Connection failed");
			return 0;
		}
		
		// insert 쿼리문 전달
		String insertQuery = "insert into studentTBL values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			ps = (PreparedStatement) con.prepareStatement(insertQuery);
			ps.setString(1, stuModel.getName());
			ps.setInt(2, stuModel.getId());
			ps.setInt(3, stuModel.getAge());
			ps.setString(4, stuModel.getGender());
			ps.setInt(5, stuModel.getSociology());
			ps.setInt(6, stuModel.getPhysics());
			ps.setInt(7, stuModel.getLinguistics());
			ps.setInt(8, stuModel.getTotal());
			ps.setDouble(9, stuModel.getAvg());
			ps.setString(10, String.valueOf(stuModel.getGrade()));
			
			// 쿼리 명령 실행
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(ps != null && !ps.isClosed()) {
					ps.close();
				}
				if(con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		// 결과값 반환
		return result;
	}

	// 데이터 조회 : select 쿼리
	public static List<StudentModel> stuModelSelect() {
		// executeQuery를 통해 쿼리문을 실행할 때 반환값을 저장할 참조변수
		ResultSet resultSet = null;
		// SQL 구문을 실행하는 객체를 받는 참조변수
		PreparedStatement ps = null;
		
		// DB에 있는 레코드셋을 가져오기 위한 ArrayList 생성
		List<StudentModel> list = new ArrayList<StudentModel>();
		
		// 데이터베이스 연결
		Connection con = DBUtility.getConnection();
		if(con == null) {
			System.out.println("DB Connection failed");
			return null;
		}
		
		// select 쿼리문 전달
		String selectQuery = "select * from studentdb.studenttbl";
		
		try {
			ps = (PreparedStatement) con.prepareStatement(selectQuery);
			// 쿼리 명령 실행
			resultSet = ps.executeQuery();
			// 생성한 ArrayList로 레코드셋(resultSet) 가져오기
			recordSetToList(resultSet, list);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(ps != null && !ps.isClosed()) {
					ps.close();
				}
				if(con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		// 결과값 반환
		return list;
	}

	// 생성한 ArrayList로 레코드셋(resultSet) 가져오기
	private static void recordSetToList(ResultSet resultSet, List<StudentModel> list) {
		try {
			while(resultSet.next()) {
				String name = resultSet.getString(1);
				int id = resultSet.getInt(2);
				int age = resultSet.getInt(3);
				String gender = resultSet.getString(4);
				int sociology = resultSet.getInt(5);
				int physics = resultSet.getInt(6);
				int linguistics = resultSet.getInt(7);
				int total = resultSet.getInt(8);
				double avg = resultSet.getDouble(9);
				char grade = resultSet.getString(10).charAt(0);
				
				// StudentModel 객체를 생성하여 리스트에 추가
				StudentModel stuModel = new StudentModel(name, id, age, gender, sociology, physics, linguistics, total, avg, grade);
				list.add(stuModel);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 데이터 검색
	public static List<StudentModel> stuModelSearch(String searchData, int searchType) {
		final int NAMESEARCH = 1, IDSEARCH = 2; 
		// executeQuery를 통해 쿼리문을 실행할 때 반환값을 저장할 참조변수
		ResultSet resultSet = null;
		// SQL 구문을 실행하는 객체를 받는 참조변수
		PreparedStatement ps = null;
		
		// DB에 있는 레코드셋을 가져오기 위한 ArrayList 생성
		List<StudentModel> list = new ArrayList<StudentModel>();
		
		// 데이터베이스 연결
		Connection con = DBUtility.getConnection();
		if(con == null) {
			System.out.println("DB Connection failed");
			return null;
		}
		
		// select 쿼리문 전달
		String searchQuery = null;
		switch(searchType) {
			case NAMESEARCH:
				searchQuery = "select * from studentdb.studenttbl where name like ?";
				break;
			case IDSEARCH:
				searchQuery = "select * from studentdb.studenttbl where id like ?";
				break;
		}
		
		try {
			ps = (PreparedStatement) con.prepareStatement(searchQuery);
			searchData = "%" + searchData + "%";
			ps.setString(1, searchData);
			
			// 쿼리 명령 실행
			resultSet = ps.executeQuery();
			
			// 생성한 ArrayList로 레코드셋(resultSet) 가져오기
			recordSetToList(resultSet, list);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(ps != null && !ps.isClosed()) {
					ps.close();
				}
				if(con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		// 결과값 리턴		
		return list;
	}

	// 데이터 삭제
	public static int stuModelDel(String deleteData, int searchType) {
		// 입력을 확인할 반환값
		int result = 0;
		// executeQuery를 통해 쿼리문을 실행할 때 반환값을 저장할 참조변수
		PreparedStatement ps = null;

		// 데이터베이스 연결
		Connection con = DBUtility.getConnection();
		if(con == null) {
			System.out.println("DB Connection failed");
			return 0;
		}
		
		// delete 쿼리문 전달
		String deleteQuery = null;
		deleteQuery = "delete from studentdb.studenttbl where id like ?";
		
		try {
			ps = (PreparedStatement) con.prepareStatement(deleteQuery);
			deleteData = "%" + deleteData + "%";
			ps.setString(1, deleteData);
			
			// 쿼리 명령 실행
			result = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(ps != null && !ps.isClosed()) {
					ps.close();
				}
				if(con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		// 결과값 반환
		return result;
	}

	// 데이터 수정 : update 쿼리
	public static int stuModelUpdate(StudentModel stuModel, int category, int updateData) {
		final int SOC = 1, PHY = 2, LING = 3;
		// 수정된 데이터를 확인할 반환값
		int result = 0;
		// SQL 구문을 실행하는 객체를 받는 참조변수
		PreparedStatement ps = null;
		
		// 레코드셋을 저장하기 위한 ArrayList 생성
		List<StudentModel> list = new ArrayList<StudentModel>();
		
		// 데이터베이스 연결
		Connection con = DBUtility.getConnection();
		if(con == null) {
			System.out.println("DB Connection failed");
			return 0;
		}
		
		// update 쿼리문 전달
		String updateQuery = null;
		switch(category) {
			case SOC:
				updateQuery = "update studentdb.studenttbl set sociology = ?, total = ?, avg = ?, grade = ? where id = ?";
				break;
			case PHY:
				updateQuery = "update studentdb.studenttbl set physics = ?, total = ?, avg = ?, grade = ? where id = ?";
				break;
			case LING:
				updateQuery = "update studentdb.studenttbl set linguistics = ?, total = ?, avg = ?, grade = ? where id = ?";
				break;
		}
		
		try {
			ps = (PreparedStatement) con.prepareStatement(updateQuery);
			ps.setInt(1, updateData);
			ps.setInt(2, stuModel.getTotal());
			ps.setDouble(3, stuModel.getAvg());
			ps.setString(4, String.valueOf(stuModel.getGrade()));
			ps.setInt(5, stuModel.getId());
			
			// 쿼리 명령 실행
			result = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(ps != null && !ps.isClosed()) {
					ps.close();
				}
				if(con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// 결과값 반환	
		return result;
	}

	// 데이터를 총점 기준으로 정렬
	public static List<StudentModel> stuModelSort(int order) {
		final int DESC =1, ASC = 2;
		// executeQuery를 통해 쿼리문을 실행할 때 반환값을 저장할 참조변수
		ResultSet resultSet = null;
		// SQL 구문을 실행하는 객체를 받는 참조변수
		PreparedStatement ps = null;
		
		// 레코드셋을 저장하기 위한 ArrayList 생성
		List<StudentModel> list = new ArrayList<StudentModel>();
		
		// 데이터베이스 연결
		Connection con = DBUtility.getConnection();
		if(con == null) {
			System.out.println("DB Connection failed");
			return null;
		}
		
		// order by 쿼리 전달
		String sortQuery = null;
		switch(order) {
			case ASC:
				sortQuery = "select * from studentdb.studenttbl order by total asc;";
				break;
			case DESC:
				sortQuery = "select * from studentdb.studenttbl order by total desc";
				break;
		}

		try {
			ps = con.prepareStatement(sortQuery);
			
			// 쿼리 명령 실행
			resultSet = ps.executeQuery();
			
			// 생성한 ArrayList로 레코드셋(resultSet) 가져오기
			recordSetToList(resultSet, list);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(ps != null && ps.isClosed()) {
					ps.close();
				}
				if(con != null && con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		// 결과값 반환
		return list;
	}

}
