# mybatis 실제로 사용해보기 (gradle)

## 1. Gradle Dependencies 추가
```
dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-web'
	implementation 'org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.4'
	compileOnly 'org.projectlombok:lombok'
	annotationProcessor 'org.projectlombok:lombok'
	runtimeOnly 'com.mysql:mysql-connector-j' //mysql (spring boot 2.7.8 이후)
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
	testImplementation 'org.mybatis.spring.boot:mybatis-spring-boot-starter-test:3.0.4'
	testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
}

```

## 2. Database 설정 (application.yml)
```
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/mybatis_db?serverTimezone=Asia/Seoul&characterEncoding=UTF-8
    username: root
    password: mypassword
    driver-class-name: com.mysql.cj.jdbc.Driver

mybatis:
  mapper-locations: classpath:mapper/**/*.xml
  configuration:
    map-underscore-to-camel-case: true
```

- **classpath 기준**으로 resources/mapper/ 디렉토리 내의 모든 하위 폴더에 있는 .xml 파일을 자동으로 인식한다.
- **/*.xml → 모든 서브폴더 안에 있는 XML 파일들을 찾는다.
- map-underscore-to-camel-case: true : DB 컬럼이 snake_case -> Java camelCase로 자동 변환

## 3. Mapper 인테페이스 작성 (UserMapper.java)
```
@Mapper
public interface UserMapper {
    User findById(@Param("id") Long id);
    void insertUser(User user);
}
```

## 4. Mapper XML파일 작성 (UserMapper.xml)
```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
    PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
    "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.example.mapper.UserMapper">
    
    <select id="findById" parameterType="long" resultType="com.example.domain.User">
        SELECT * FROM users WHERE id = #{id}
    </select>

    <insert id="insertUser" parameterType="com.example.domain.User">
        INSERT INTO users(name, email) VALUES(#{name}, #{email})
    </insert>

</mapper>
```

