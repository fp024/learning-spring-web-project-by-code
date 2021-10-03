## 데이터 베이스 설정

## 유저 생성
* 유저명: book_ex
    * Oracle에서 유저명 대소문자를 구분하진 않는 것 같다. 
* 테이블스페이스: SPRING_TEST
    * 가상머신의 CentOS 7.x 에 Oracle 18c XE 설치할 때, SPRING_TEST라는 테이블 스페이스를 별도 설정해줬었음. 
* 임시 테이블 스페이스: TEMP
* 권한은 "RESOURCE", "CONNECT"
	* RESOURCE 권한도 테이블은 만들수 있어서, 일단 이렇게 해보고 필요시 권한을 늘린다. 

	```sql
	/*
	  유저 생성할 때, 아래와 같은 오류가 나면, 
	  
	  ORA-65096: invalid common user or role name
	  65096. 00000 -  "invalid common user or role name"
	  *Cause:    An attempt was made to create a common user or role with a name
	           that was not valid for common users or roles. In addition to the
	           usual rules for user and role names, common user and role names
	           must consist only of ASCII characters, and must contain the prefix
	           specified in common_user_prefix parameter.
	  *Action:   Specify a valid common user or role name.
	  https://docs.oracle.com/database/121/REFRN/GUID-516ADCCF-3661-4B54-908A-7041854EA14F.htm#REFRN10354
	  
	  아래 환경 변수를 설정해야함.
	  
	  Oracle 12c 부터 일반 유저 생성시 유저명이 C##을 붙여줘야한다고 함.
	*/
	ALTER SESSION SET "_ORACLE_SCRIPT"=true;
	
	CREATE USER book_ex IDENTIFIED BY book_ex
	DEFAULT TABLESPACE SPRING_TEST
	TEMPORARY TABLESPACE TEMP;
	
	GRANT CONNECT, RESOURCE TO book_ex;
	
	```
	
	* book_ex로 접속할 때는... Driver Properties의 internal_login에 sysdba 설정 내용을 제거해주자!

## 8080 포트 변경

* Oracle 18c에서 `SELECT dbms_xdb.gethttpport() FROM DUAL;` 쿼리 수행시 결과가 0임
    * Oracle 18c XE를 설치한 가상머신은 Oralce 전용으로만 사용할 것이여서, 일단 추가 설정은 하지 않는다. 

    
## 프로젝트의 JDBC 연경
* https://www.oracle.com/database/technologies/faq-jdbc.html 

* ojdbc8을 사용하면서, 버전도 드라이버버전도 데이터베이스 버전에 맞게 18.3.0.0 을 사용하도록 합니다.

* MVN REPOSITORY에서 제공됨
    * https://mvnrepository.com/artifact/com.oracle.database.jdbc/ojdbc8/18.3.0.0  
    
    
## 드라이버 클래스 
* Oracle 9이후로 oracle.jdbc.driver.OracleDriver 대신에 oracle.jdbc.OracleDriver를 사용해야한다고 함. 예전 클래스가 경로에 남아있긴 하지만, HikariCP 초기화시 경고가 나타남 

