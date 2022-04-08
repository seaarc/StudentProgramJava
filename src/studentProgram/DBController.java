package studentProgram;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBController {

	// �����ͺ��̽� �Է� : insert ����
	public static int stuModelIns(StudentModel stuModel) {
		// �Է��� Ȯ���� ��ȯ��
		int result = 0;
		// SQL ������ �����ϴ� ��ü�� �޴� ��������
		PreparedStatement ps = null;

		// �����ͺ��̽� ����
		Connection con = DBUtility.getConnection();
		if(con == null) {
			System.out.println("DB Connection failed");
			return 0;
		}
		
		// insert ������ ����
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
			
			// ���� ��� ����
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
		
		// ����� ��ȯ
		return result;
	}

	// ������ ��ȸ : select ����
	public static List<StudentModel> stuModelSelect() {
		// executeQuery�� ���� �������� ������ �� ��ȯ���� ������ ��������
		ResultSet resultSet = null;
		// SQL ������ �����ϴ� ��ü�� �޴� ��������
		PreparedStatement ps = null;
		
		// DB�� �ִ� ���ڵ���� �������� ���� ArrayList ����
		List<StudentModel> list = new ArrayList<StudentModel>();
		
		// �����ͺ��̽� ����
		Connection con = DBUtility.getConnection();
		if(con == null) {
			System.out.println("DB Connection failed");
			return null;
		}
		
		// select ������ ����
		String selectQuery = "select * from studentdb.studenttbl";
		
		try {
			ps = (PreparedStatement) con.prepareStatement(selectQuery);
			// ���� ��� ����
			resultSet = ps.executeQuery();
			// ������ ArrayList�� ���ڵ��(resultSet) ��������
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
		
		// ����� ��ȯ
		return list;
	}

	// ������ ArrayList�� ���ڵ��(resultSet) ��������
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
				
				// StudentModel ��ü�� �����Ͽ� ����Ʈ�� �߰�
				StudentModel stuModel = new StudentModel(name, id, age, gender, sociology, physics, linguistics, total, avg, grade);
				list.add(stuModel);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// ������ �˻�
	public static List<StudentModel> stuModelSearch(String searchData, int searchType) {
		final int NAMESEARCH = 1, IDSEARCH = 2; 
		// executeQuery�� ���� �������� ������ �� ��ȯ���� ������ ��������
		ResultSet resultSet = null;
		// SQL ������ �����ϴ� ��ü�� �޴� ��������
		PreparedStatement ps = null;
		
		// DB�� �ִ� ���ڵ���� �������� ���� ArrayList ����
		List<StudentModel> list = new ArrayList<StudentModel>();
		
		// �����ͺ��̽� ����
		Connection con = DBUtility.getConnection();
		if(con == null) {
			System.out.println("DB Connection failed");
			return null;
		}
		
		// select ������ ����
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
			
			// ���� ��� ����
			resultSet = ps.executeQuery();
			
			// ������ ArrayList�� ���ڵ��(resultSet) ��������
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
		
		// ����� ����		
		return list;
	}

	// ������ ����
	public static int stuModelDel(String deleteData, int searchType) {
		// �Է��� Ȯ���� ��ȯ��
		int result = 0;
		// executeQuery�� ���� �������� ������ �� ��ȯ���� ������ ��������
		PreparedStatement ps = null;

		// �����ͺ��̽� ����
		Connection con = DBUtility.getConnection();
		if(con == null) {
			System.out.println("DB Connection failed");
			return 0;
		}
		
		// delete ������ ����
		String deleteQuery = null;
		deleteQuery = "delete from studentdb.studenttbl where id like ?";
		
		try {
			ps = (PreparedStatement) con.prepareStatement(deleteQuery);
			deleteData = "%" + deleteData + "%";
			ps.setString(1, deleteData);
			
			// ���� ��� ����
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
		
		// ����� ��ȯ
		return result;
	}

	// ������ ���� : update ����
	public static int stuModelUpdate(StudentModel stuModel, int category, int updateData) {
		final int SOC = 1, PHY = 2, LING = 3;
		// ������ �����͸� Ȯ���� ��ȯ��
		int result = 0;
		// SQL ������ �����ϴ� ��ü�� �޴� ��������
		PreparedStatement ps = null;
		
		// ���ڵ���� �����ϱ� ���� ArrayList ����
		List<StudentModel> list = new ArrayList<StudentModel>();
		
		// �����ͺ��̽� ����
		Connection con = DBUtility.getConnection();
		if(con == null) {
			System.out.println("DB Connection failed");
			return 0;
		}
		
		// update ������ ����
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
			
			// ���� ��� ����
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

		// ����� ��ȯ	
		return result;
	}

	// �����͸� ���� �������� ����
	public static List<StudentModel> stuModelSort(int order) {
		final int DESC =1, ASC = 2;
		// executeQuery�� ���� �������� ������ �� ��ȯ���� ������ ��������
		ResultSet resultSet = null;
		// SQL ������ �����ϴ� ��ü�� �޴� ��������
		PreparedStatement ps = null;
		
		// ���ڵ���� �����ϱ� ���� ArrayList ����
		List<StudentModel> list = new ArrayList<StudentModel>();
		
		// �����ͺ��̽� ����
		Connection con = DBUtility.getConnection();
		if(con == null) {
			System.out.println("DB Connection failed");
			return null;
		}
		
		// order by ���� ����
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
			
			// ���� ��� ����
			resultSet = ps.executeQuery();
			
			// ������ ArrayList�� ���ڵ��(resultSet) ��������
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
		
		// ����� ��ȯ
		return list;
	}

}
