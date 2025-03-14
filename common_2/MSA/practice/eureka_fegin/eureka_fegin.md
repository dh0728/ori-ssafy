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

#### 처음에 해본 yml 설정
```
server:
  port: 8761

spring:
  application:
    name: test-eureka-local
  config: 
    activate:
      on-profile: local # 이거 있으면 오류남

eureka:
  instance:
    prefer-ip-address: true
  server:
    enable-self-preservation: true
  client:
    register-with-eureka: false # 자기 자신을 서비스로 등록하지 않음
    fetch-registry: false 
    service-url:
      defaultZone: http://localhost:8761/eureka/
```

- 위에 코드를 작성하니 오류가 났다. 
- 찾아보니 아래 설정이 문제였다.
```
config:
    activate:
      on-profile: local
```
- 찾아보니 Eureka 서버가 기본 프로파일(default)로 실행되면, 이 설정이 적용되지 않아서 Eureka 클라이언트가 Eureka 서버를 찾지 못할 가능성이 있다고 한다.
- 프로파일 설정이 필요하다면 실행 시 --spring.profiles.active=local 옵션을 추가하고 아니면 지우자!!

#### 좀더 설정을 추가해서 수정한 yml 설정
```
server:
  port: 8761

spring:
  application:
    name: test-eureka-local

eureka:
  client:
    register-with-eureka: false
    fetch-registry: false
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/
  server:
    wait-time-in-ms-when-sync-empty: 0
    enable-self-preservation: true
    renewal-percent-threshold: 0.85
  instance:
    lease-renewal-interval-in-seconds: 15

```
**✔ wait-time-in-ms-when-sync-empty: 0**
→ Eureka 서버가 처음 시작될 때, 서비스가 없는 경우 기다리지 않고 바로 동작하도록 설정.

**✔ renewal-percent-threshold: 0.85**
→ Eureka 서버가 "자기 보호 모드"에서 얼마나 많은 인스턴스를 허용할지 설정.
→ 기본값(0.85)은 서버가 85% 이상의 서비스 등록 요청을 받지 못하면, 보호 모드가 활성화됨.

**✔ lease-renewal-interval-in-seconds: 15**
→ 서비스가 Eureka 서버에 자신이 살아있음을 알리는 주기를 15초로 설정.
→ 기본값(30초)보다 짧게 설정하여 서비스 상태를 더 자주 업데이트할 수 있음.

**🚀 두 번째 설정은 성능 최적화를 포함해서, Eureka 서버가 빠르게 응답하고 자기 보호 모드에서 안정적으로 동작하도록 설정을 추가했다.**

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