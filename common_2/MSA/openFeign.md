# 🌍 Spring Cloud OpenFegin

## 🌐 개요

### 🚀 서버 간 동기 통신 클라이언트
MSA와 관련된 패턴 중 어플리케이션 패턴으로 마이크로서비스 간 동기 통신 / 비동기 통신이 있다. <br>
기능/관심사/도메인 별로 분리된 마이크로 서비스 간 협력이나 트랜잭션을 수해해야 할 떄 가장 쉽게 사용하는 것이 REST API를 활용한 동기 통신이다. <br>
이 때 서버 측에서는 다른 서버로 통신을 보내는 동기 통신 클라이언트가 있어야 하는데 대표적으로 RestTemplate와 Spring Cloud OpenFegin이 있다.

### 🚀 Spring Cloud OpenFeign이란?
Spring Cloud OpenFegin은 Spring Cloud 프로젝트에 포함된 동기 통신 클라이언트로, **선언적 REST 클라이언트로서 웹 서비스 클라이언트 작성을 보다 쉽게 할 수 있다.** <br>
직접 RestTemplate을 호출해서 대상 서버에게 통신을 요청하는 기존 방식과는 달리 인테페이스로 선언만 해두면 자동으로 구현체가 생성된는 형식이다. 

```
// 인터페이스를 선언
@FeignClient(name = "example-service", url = "${example-service.url}")
public interface ExampleFeignClient {

    @GetMapping("/api/resource")
    String getResource();
}
```

### 🚀 RestTemplate과 Spring Cloud OpenFegin의 차이점
RestTemplate와 Spring Cloud OpenFeign의 주요 차이점은 다음과 같다.

**1. 명령적 API vs 선언적 API**
- **RestTemplate**은 명령적 API로 HTTP 요청을 생성하고 수행
- **Spring Cloud OpenFeign**은 **선언적 API로 인터페이스**를 정의, 어노테이션을 사용하여 해당 인터페이스를 통해 HTTP 클라이언트를 생성 -> 명시적으로 HTTP 요청을 작성하지 않고도 원격 서비스 호출이 가능

**2. 자동 구성**
- RestTemplate은 RestTemplate 빈 등록 및 호출, 메서드를 통한 명령을 직접 처리해야 함
- Spring Cloud OpenFegin은 인터페이스만 선언하더라도 **클라이언트 구현체가 자동 구성됨**

**3. URL 템플릿**
- RestTemplate은 URL 템플릿을 사용하여 동적인 요청 URL을 생성할 수 있음
- Spring Cloud OpenFegin은 어노테이션을 사용하여 동적인 URL을 정의할 수 있음




### 출처
<a href="https://velog.io/@mrcocoball2/Spring-Cloud-Spring-Cloud-OpenFeign-%EA%B8%B0%EB%B3%B8-%EA%B0%9C%EB%85%90-%EB%B0%8F-%ED%99%9C%EC%9A%A9">[Spring Cloud] Spring Cloud OpenFeign - 기본 개념 및 활용</a>