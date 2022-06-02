# 스프링 데이터 베이스

---
해당 프로젝트는 데이터 접근 기술의 발전 과정을 알기 위해서 커넥션 연결부터 sql 작성 mapper를 통한 매핑 과정까지 작업을 수행하던

jdbc를 사용 던 시점부터 현재 JPA 까지에 이르기 까지의 반복적인 요소를 줄이는 과정을 몸소 경험할 수 있도록 부분 별로 나누어 진행 한 프로젝트이며

커넥션 풀의 개념과 Spring에서의 Db 정보를 가지는 DataSource , 트랜 잭션 처리, 예외 처리와 관련한 것들을 다루는 프로젝트 입니다.

## 빠른 요약 

### 커넥션 풀
JDBC를 직접적으로 사용하는 경우 매 요청 마다 DB에 커넥션 연결을 요청하고 쿼리를 보낸 후 커넥션을 반납해 주어야 했는데 이러한 반복 하는 과정이 
비효율적이기에 DB와 연결된 커넥션을 서버 시작과 함께 연결 한 뒤에 DB 접근 시 커넥션을 주고 다시 반납하는 구조를 사용(보통 커넥션 풀 오픈 소르로 HikariCP 사용)

[HikariCP 관련 Config 설정 ](https://github.com/brettwooldridge/HikariCP)

### 데이터 소스
DataSource 는 DB 관련 된 정보를 가지고 있는 클래스로 해당 클래스에 있는 정보를 사용하여 Connection을 생성하거나 커넥션 풀에서 
받아온다. DataSource 를 사용하면 기존 JDBC에서 HikariCP로 변경을 해도 다른 기존 코드를 수정하지 않고 Connection을 받아 올 수 있는 장점을 가지고 있다.

### 트랜잭션
기존 jdbc에 코드에 경우 매번 try~Catch를 통해 SQLException을 잡아 주어야하지만 Spring에서는 Proxy 기법을 사용하여 @Transactional 하나로
예외 발생시 롤백을 수행 할 수 있도록 해준다.

### 예외처리
예외는 CheckException 과 UnCheckException 으로 분류 되는데 CheckException 에 경우 tryCatch를 통해 예외를 잡아주거나 Throws를 
통해 예외 발생을 무조건 나타내 주어야 한다. 그렇기 때문에 향후 코드를 수정할 경우 다른 코드들도 다 수정해주어야 한다. 그래서 대부분의 경우
UnCheckException 으로 전환하여 예외를 처리해주면 좋다. 단 진짜 중요한 예외에 경우 CheckException 을 사용해서 알려주면 좋다.

CheckException은 Exception을 상속 하여 구현 하며 UnCheckException은 RuntimeException을 상속받아 구현 한다.

CustomException 생성 시 기존 발생 예외를 정보를  trace에 꼭 넣어주어야 한다. 
