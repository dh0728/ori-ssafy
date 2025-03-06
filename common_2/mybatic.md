# 🐣 MyBatis란?

- MyBatis는 자바 개발자들이 데이터베이스를 쉽게 다룰 수 있도록 도와주는
오픈 소스 ORM(Object-Relational Mapping) 프레임워크이다.
- MyBatis는 데이터베이스 쿼리 <-> 프로그래밍 언어 코드를 분리하여 유지보수성과 생산성을 높이는 것.


## JdbcTemplate, JPA와 비교했을 때 쓰면 좋은 상황황
- **MyBatis**는 JdbcTemplate이 제공하는 대부분의 기능을 제공하지만 **JdbcTemplate**의 문제점 중 하나인 **여러 줄의 String 형 sql을 작성해야 한다는 점**을 **MyBatis를 이용**하여 **해결**할 수 있다.
- MyBatis는 SQL을 **xml**에 편리하게 작성할 수 있고, xml 내에서 동적 쿼리를 매우 편리하게 작성할 수 있다.
- MyBatis는 약간의 설정이 필요하기 때문에 단순한 쿼리가 많은 경우는 스프링에 내장되어 별도의 설정없이 사용할 수 있는 JdbcTemplate을 사용하는 것이 좋다

- 또한 단순한 도메인 관련 엔티티를 불러오는 경우 JPA를 사용하는 것을 권장한다.
- 그러나 JPA를 사용하다 보면 JOIN을 통해 하나의 쿼리로 불러올 수 있는 것들을 어쩔 수 없이 여러 개의 쿼리를 사용해야 하거나, JPQL을 복잡하게 작성하거나, 심지어는 JPQL로 불가한 경우가 있는데 이럴 땐 MyBatis를 사용해서 JOIN 쿼리를 작성해서 해결하는 것이 좋은 것 같다.


## 😮 MyBatis의 주요 장점

**유연성**: SQL 쿼리를 직접 작성할 수 있으므로 매우 유연하다. 또한, MyBatis는 동적 쿼리를 작성할 수 있다.
**간결성**: MyBatis는 SQL 쿼리와 프로그래밍 언어 코드를 분리하기 때문에 코드가 간결해져 유지보수에 용이
**성능**: MyBatis는 캐시 기능을 제공하여 데이터베이스 연산 속도를 높일 수 있다.
**다양한 데이터베이스 지원**: MyBatis는 다양한 데이터베이스에 대한 지원을 제공합니다.

동적 쿼리 : 
- **실행 시점에 조건에 따라 SQL 쿼리를 동적으로 생성**하는 것.
- 데이터베이스의 검색 조건이나 결괏값 등이 동적으로 변화할 때 유용하게 사용
- 여러 태그를 사용해 구현가능능
```
<if>, <choose>, <when>, <otherwise>, <foreach>
```

### 출처 
<a href="https://ccomccomhan.tistory.com/130">꼼꼼한 개발자(MyBatis란?)</a>
<a href="https://innovation123.tistory.com/82">[MyBatis] 마이바티스 기본 사용법</a>