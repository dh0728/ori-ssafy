에러1
```
### Error querying database.  Cause: org.springframework.jdbc.CannotGetJdbcConnectionException: Failed to obtain JDBC Connection
### The error may exist in class path resource [mapper/FindingMapper.xml]
### The error may involve funding.funding.mapper.FundingMapper.getAllFunding
### The error occurred while executing a query
### Cause: org.springframework.jdbc.CannotGetJdbcConnectionException: Failed to obtain JDBC Connection] with root cause

com.mysql.cj.exceptions.UnableToConnectException: Public Key Retrieval is not allowed
```

1. url에 설정 추가

applicatein.yml
```
url: jdbc:mysql://mysql-container:3306/funding?useSSL=false&serverTimezone=Asia/Seoul&useLegacyDatetimeCode=false&allowPublicKeyRetrieval=true
```

2. root 계정의 인증 방식을 mysql_native_password로 변경
```
SELECT user, host, plugin FROM mysql.user;

ALTER USER 'root'@'%' IDENTIFIED WITH mysql_native_password BY 'rootpassword';
FLUSH PRIVILEGES;
```

에러2
Access denied for user 'root'@'172.18.0.3' 오류 해결 (MySQL 접속 문제)

MySQL에서 root@172.18.0.4 계정에 대한 접근이 허용되지 않았거나, 비밀번호가 일치하지 않았을 때 생기는 오류이다. 

root@% 계정에 비밀번호 및 전체 권한 부여
```
ALTER USER 'root'@'%' IDENTIFIED WITH mysql_native_password BY 'rootpassword';
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' WITH GRANT OPTION;
FLUSH PRIVILEGES;

```

변경된 셜정 다시 확인
```
SELECT user, host FROM mysql.user WHERE user = 'root';
```