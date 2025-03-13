# 🌍 Eureka 실습해보기
- 전 실습에서는 openFeging + Gateway를 연계해서 클라이언트 요청을 받아서 특정 마이크로서비스로 전달하는 하는 것을 구현해 보았는데
- 마이크로서비스 : Ribbon이나 Eureka를 사용하지 않아 서비스 이름을 식별하지 못해 url을 직접 사용했었다.
- gateway : url로 직접 라우팅을 하였지만, lb://서비스 이름 으로 라우팅 및 로드 밸런싱이 가능해진다.
- 이번 시간에는 Eureka Server / Client를 통해 서비스 이름을 식별할 수 있도록 해보자!!

## 🚩 설정 : Spring Cloud Eureka Server

### 🌐 1. Eureka server 프로젝트 생성

### 🌐 2. Eureka 의존성 주입하기 
- Eureka server 프로젝트 생성
- spring boot 버전에 맞는 Spring Cloud version으로 의존성을 주입해준다.

```
ext {
	set('springCloudVersion', "2024.0.0")
}

dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-web'
	implementation 'org.springframework.cloud:spring-cloud-starter-netflix-eureka-server' //server를 주입해야함
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
	testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
}

dependencyManagement {
	imports {
		mavenBom "org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}"
	}
}
```

### 🌐 3. Eureka Server로 해당 애플리케이션 등록

```
@EnableEurekaServer // Eureka Server 등록
@SpringBootApplication
public class EurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaApplication.class, args);
	}

}
```

### 🌐 4. application.yml 파일 수정

```
server:
  port: 8761

spring:
  application:
    name: test-eureka-local
  config:
    activate:
      on-profile: local

eureka:
  instance:
    prefer-ip-address: true
  server:
    enable-self-preservation: true
  client:
    register-with-eureka: false # 자기 자신을 서비스로 등록하지 않음
    fetch-registry: true # 마이크로서비스 인스턴스 목록을 로컬에 캐시할 것인지 여부. 서비스 탐색 등의 목적.
    service-url:
      defaultZone: http://localhost:8761/eureka

management:
  security:
    enabled: false

ribbon:
  IsSecure: false

security:
  basic:
    enabled: true
  user:
    name: user
  password: secret

```

## 🚩 설정 : Spring Cloud Eureka Client 기본 세팅

### 🌐 2. Eureka 의존성 주입하기 
- 위에 서버와 동일

### 🌐 3. yml 파일 수정하기
```
# 각 마이크로서비스 공통 사항
spring:
  application:
    name: funding-service
...

eureka:
  instance:
    instance-id: ${spring.cloud.client.hostname}:${spring.application.instance_id:${random.value}}
    leaseRenewalIntervalInSeconds: 10
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/
```


```
//@FeignClient(name = "funding-client", url = "http://localhost:8080/funding")
@FeignClient(name = "funding-service")
public interface FundingClient {


    @GetMapping("/check")
    String check();

}

```

### 🌐 4. 메인 클래스에 **@EnableDiscoveryClient** 어노테이션 부착
```
//@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class FundingApplication {

	public static void main(String[] args) {

		SpringApplication.run(FundingApplication.class, args);
	}

}
```

## 🚩 Spring Cloud OpenFeign과의 연계
- 이전 실습에서는 Ribbon을 사용하지 않아 서비스 이름을 식별하지 못해 url을 직접 사용했었음
- 이제 Eureka Server / Client를 통해 서비스 이름을 식별할 수 있게 만들 수 있다.
- @EnableDiscoveryClient 어노테이션을 메인 클래스에 부착한 후 이전에 LoadBalancer나 Ribbon을 사용하지 않아 기본 클라이언트를 설정했던 설정 클래스를 주석 처리 합니다.


```
@Configuration
public class FeignConfig {

    // Ribbon 로드 밸런서를 사용하는 것이 기본값이라 이 부분을 null로 처리
    // 처리하지 않을 경우 아래의 오류 메시지 출력
    // No Feign Client for loadBalancing defined. Did you forget to include spring-cloud-starter-loadbalancer?
//    @Bean
//    public Client feignClient() {
//        return new Client.Default(null, null);
//    }
}

```


- url로 동기 통신 대상(localhost:8081)을 지정하던 인터페이스 설정을 서비스 이름(funding-service)으로 대체합니다.

```
//@FeignClient(name = "funding-client", url = "http://localhost:8080/funding")
@FeignClient(name = "funding-service")
public interface FundingClient {


    @GetMapping("/check")
    String check();

}
```

## 🚩 Spring Cloud Gateway와의 연계
- 이전 게시글에서는 url로 직접 라우팅을  
- 이제 **lb://서비스 이름** 으로 라우팅 및 로드 밸런싱이 가능해짐

- 마찬가지로 의존성 주입 후, @EnableDiscoveryClient 어노테이션을 메인 클래스에 부착한 후 기존 application.yml을 수정합니다.

```

```