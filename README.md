# 학생 성적 관리 프로그램

학생들의 정보를 입·출력하고, 검색하여 정보를 조회하고, 학생 정보를 삭제하거나 성적을 수정하고, 학생들을 총점순으로 정렬하는 기능들로 구성된 성적 관리 프로그램

## Environment
Eclipse 4.22.0

## Descripton
> #### 실행 화면


> #### 클래스
DBUtility : mySQL 데이터베이스와 연결
DBController : 데이터베이스 관리
StudentModel : 데이터(와 관련된 일을 처리하는) 클래스
StudentMain : 실행 클래스

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


> #### 내부 클래스