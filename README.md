# 학생 성적 관리 프로그램

학생들의 정보를 입력 및 전체 조회하는 기능, 검색하여 개별로 조회하는 기능, 학생 정보를 삭제하거나 성적을 수정하는 기능, 학생들을 총점순으로 정렬하는 기능들로 구성된 성적 관리 프로그램
mySQL과 연결함
<br>

## Environment
Eclipse 4.22.0

## Descripton
> #### 실행 화면
학생 입력
![image](https://user-images.githubusercontent.com/71351412/162450162-c6ca3b2d-736f-4d17-b0b2-bdc443e3f251.png)
<br>
학생 전체 조회
![image](https://user-images.githubusercontent.com/71351412/162450546-5b356ca8-d98d-4bdc-b556-0937b871f44a.png)
<br>
학생 검색 조회
![image](https://user-images.githubusercontent.com/71351412/162451355-a72bb8bf-ce75-44e2-b1b1-e962885c15d7.png)
<br>
학생 정보 삭제
![image](https://user-images.githubusercontent.com/71351412/162451607-71112417-3226-48e7-ba37-22833a4fd77b.png)
<br>
학생 성적 수정
![image](https://user-images.githubusercontent.com/71351412/162451951-7f7f734c-2f2e-48ec-a166-a89b9e0ededf.png)
<br>
총점순으로 학생 정렬
![image](https://user-images.githubusercontent.com/71351412/162452028-f2961e74-eb1d-4f8b-8317-1d9596381da1.png)
<br><br>

> #### 클래스
DBUtility : mySQL 데이터베이스와 연결
DBController : 데이터베이스 관리
StudentModel : 데이터(와 관련된 일을 처리하는) 클래스
StudentMain : 실행 클래스
<br>

> #### 패턴 확인
요청하는 데이터의 형식에 어긋나는 값을 받게 되는 경우를 대비해 패턴 설정

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

<br>

> #### 자바와 DB를 연결하기 위해 Connection 객체 생성
DBUtility.java
<pre>
Connection con = (Connection) DriverManager.getConnection(URL, userID, userPassword);
</pre>
<br>

> #### 자바에서 DB로 쿼리 전달
SQL 질의문을 전달하기 위한 PreparedStatement 인터페이스 사용
DBController.java
<pre>
String insertQuery = "insert into studentTBL values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
try {
	ps = (PreparedStatement) con.prepareStatement(insertQuery);
} catch (SQLException e) {
	e.printStackTrace();
}
</pre>
<br>

> #### executeupdate 와 executequery
select 구문을 수행할 때 executequery 사용
<pre>
String selectQuery = "select * from studentdb.studenttbl";
try {
	ps = (PreparedStatement) con.prepareStatement(selectQuery);
	resultSet = ps.executeQuery();
} catch  (SQLException e) {
	e.printStackTrace();
}
</pre>
<br>

그 외의 구문을 수행할 때 executeupdate 사용
<pre>
String deleteQuery = "delete from studentdb.studenttbl where id like ?";
try {
	ps = (PreparedStatement) con.prepareStatement(deleteQuery);
	deleteData = "%" + deleteData + "%";
	ps.setString(1, deleteData);
	result = ps.executeUpdate();
			
} catch (SQLException e) {
	e.printStackTrace();
}
</pre>
