# Redis

Redis는 메모리 기반의 데이터 저장소이다.(In-Momory DB) 키-벨류 데이터 구조를 기반한 다양한형태의 자료 구조를 제공하며, 데이터들을 저장할 수 있는 저장소이다.

- In-Memory 데이터 구조를 가진 저장소
- DB, Cache, Message Queue, Shared Memory 용도로 사용
- **웹 서버의 부담을 줄이고, 고속으로 데이터 제공이 가능**

## 인메모리 (In-Memory)
- 컴퓨터의 주기억장치인 RAM에 데이터를 올려서 사용하는 방법
- RAM에 데이터를 저장학 되면 메모리 내부에서 처리가 되므로 데이터르 저장/조회할 때 하드디스크를 오고가는 과정을 거치지 않아도 되어 속도가 빠름

👉 그러나 서버의 메모리 용량을 초과하는 데이터를 처리할 경우, RAM의 특성인 **휘발성**에 따라 **데이터가 유실**될 수 있음

#### 휘발성
- 전원이 꺼지면 가지고 이는 데이터가 사라지는 특성


## 기존 DB가 있지만 Redis를 사용하는 이유는??
- DB는 데이터를 디스크에 직접 저장(write)하기 때문에 서버에 문제가 발생하여 다운되더라도 데이터가 손실되지 않음
- 매번 디스크에 접근해야하기 때문에 사용자가 많이질수록 부하가 많아지고 느려질 수 있음
- 따라서 캐시 **서버를 도입하여 사용하는데 이것이 Redis**

👉 같은 요청이 여러 번 들어올 때 Redis를 사용함으로써 매번DB를 거치지 않고 캐시 서버에서 저장해놨던 값을 바로 가져와 **DB의 부하를 줄이고 서비스 속도도 느려지지 않게** 할 수 있다.

# Redis의 특징
- Key, Value 구조
- 빠른 처리 속도
  - 디스크가 아닌 메모리에서 데이터를 처리하기 때문에 속도가 빠름
- Data Type(Collection)을 지원
  - 개발의 편의성, 생산성이 좋아지고 난이도가 낮아짐

<img src="./images/Redis_struct.png">

 
👉 Redis에서 제공하는 **Sorted-Set**자료구조를 사용하면 좀 더 빠르고 간단하게 데이터 정렬가능

## AOF, RDB 방식
- 인메모리 데이터 저장소가 가지는 휘발성의 특성으로 데이터가 유실될 경우를 방지하여 백업 기븡을 제공

### AOP (Append On File) 방식
- Redis의 모든 write/update 연산 자체를 모두 log 파일에 기록하는 형태

### RDB (Snapshotting) 방식
- 순간적으로 메모리에 있는 내용 전체를 디스크에 담아 여구 저장하는 방식

### Redis Sentinel 및 Redis Cluster를 통한 자동 파티셔닝을 제공
- Master와 Slaves로 구성하여 여러대의 복제본을 만들수 있고, 여러대의 서버로 읽기를 확장 가능

#### 파티셔닝 (Partitioning)
- 다수의 Redis 인스턴스가 존재할 떄 데이터를 여러 곳으로 분산시키는기술
- 각 Redis 인스턴스는 전체 키 중 자신에게 할당된 일부 파티션의 키들만 관리하게 됨

## 싱글 스레드
- 한번에 하나의 명령만 수행이 가능하므로 Race Condidtion이 거의 발생하지 않음

🚨 Race condition 
- 공유 자워에 대해 여러 프로세스가 동시에 접근을 시도할 때, 타이밍이나 순서 등이 결과값에 영향을 줄 수 있는 상태
- 즉, 두개의 스레드가 하나의 자원을 놓고 서로 사용하려고 경쟁하는 상황에서 발생
- 프로그램의 일관성과 정확성을 손사시킬 수 있음

👉 두개의 스레드 A, B가 X라는 공유 변수를 사용하는데, A의 역할은 X에 +1 후 저장/B의 역할은 X에 -1 후 저장일 때, A가 X를 읽은 후 B가 실행되기 전에 결과를 저장한다고 하면, A의 결과가 스레드 B의 결과에 덮어씌워지게 됨 

## Redis 사용시 주의할 점
- **시간 복잡도**
  - Single Threaded(싱글 스레드)사용으로 한번에 하나의 명령만수행이 가능하므로 처리 시간이 긴 요청의 경우 장애가 발생
- **메모리 단편화**
  - 크고 작은 데이터를 할당하고 해제하는 과정에서 메모리의 파편화가 발생하여 응답 속도가 느려질 수 있음

🚨 메모리 단편화 (Memory Fragmentation)

<img src="./images/memory_frag.png" alt="no">

- RAM에서 메모리를 할당받고 햊하는 과정에서 위와 같이 부분부분 빈 공간이 생기는데, 새로운 메모리 할당 시에 사용 가능한 메모리가 충분히 존재하지만 메모리의 크기만큼의 부분이 없어 마지막 부분에 할당되어 메모리 낭비가 심하게 됨

- 이 현상이 계속되면 실제 physical 메모리가 커져 프로세스가 죽는 현상이 발생 할 수 있으므로 redis사용 시에 메모리를 적당히 여우롭게 사용하느 것이 좋음

## Redis 설치 방법
<a href="https://redis.io/docs/latest/operate/oss_and_stack/install/">Redis 설치 방법</a>

<a href="https://server-talk.tistory.com/472">Redis- 서버 설치 및 기본 설정 알아보기</a>

### 참고한 사이트
<a href="https://velog.io/@wnguswn7/Redis%EB%9E%80-%EB%AC%B4%EC%97%87%EC%9D%BC%EA%B9%8C-Redis%EC%9D%98-%ED%8A%B9%EC%A7%95%EA%B3%BC-%EC%82%AC%EC%9A%A9-%EC%8B%9C-%EC%A3%BC%EC%9D%98%EC%A0%90">Redis란 무엇일까</a>

### 참고할 사이트
<a href="https://inpa.tistory.com/entry/REDIS-%F0%9F%93%9A-%EB%8D%B0%EC%9D%B4%ED%84%B0-%ED%83%80%EC%9E%85Collection-%EC%A2%85%EB%A5%98-%EC%A0%95%EB%A6%AC">Redis 데이터 타입입</a>

<a href="https://velog.io/@kimdy0915/SpringBoot-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8%EC%97%90%EC%84%9C-%EC%84%B1%EB%8A%A5-%ED%96%A5%EC%83%81%EC%9D%84-%EC%9C%84%ED%95%9C-Redis-Cache-%EC%A0%81%EC%9A%A9%ED%95%98%EA%B8%B0">SpringBoot에 Redis Cache 적용하기</a>

