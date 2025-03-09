# 1. 사용할 DB 및 ERD

이번 경우 나는 NOSQL 기반의 DB를 선택하기로 했다.

선택한 이유
1. 키/값 저장소는 수평적 규모 확장이 쉽다.
2. 키/값 저장소는 데이터 접근 지연시간(latency)가 낮다
3. 관계형 데이터베이스는 롱 테일(long tail)에 해당하는 부분을 잘 처리하지 못하는 경향이 있다.
**롱테일**: 오래되었거나 자주 사용되지 않는 데이터를 포함하는 방대한 데이터 집합
4. 인덱스가 커지면 데이터에 대한 무작위적 접근(random access)를 처리하는 비용이 늘어나다.
5. 페이스북 메신저는 HBase를 사용하고 디스코드는 카산드라 NoSQL을 사용하고 있다. 

## 2. 개념적 데이터 모델링
설계전 생각해 봐야 하는 문제제
- 회원은 **여러 개**의 채팅방을 가질 수 있다.
- 채팅방은 **여러 명**의 회원을 수용할 수 있다.
- 회원- 채팅방의 관계는 **N:N** 관계로 이루어져 있다.

## 3. 논리적 데이터 모델링

### 1. 채팅방(chatRoom)
- 채팅방 정보를 저장하는 컬렉션 
- 멤버 리스트를 함꼐 저장해, 채팅방 참여자를 쉽게 찾을 수 있도록 함

#### 컬럼 
✅ _id: 채팅방 고유 ID
✅ type: 1:1 채팅이면 "dm", 그룹 채팅이면 "group"
✅ members: 채팅방에 속한 사용자 리스트
✅ last_message: 최신 메시지를 저장해서 채팅 목록을 빠르게 조회할 수 있도록 함.

예시
```
{
  "_id": "room_1234",
  "name": "개발자 채팅방",
  "type": "group",  
  "members": ["user_1", "user_2", "user_3"],
  "created_at": "2024-03-09T12:00:00Z",
  "last_message": {
    "message_id": "msg_5678",
    "sender": "user_2",
    "content": "안녕하세요!",
    "timestamp": "2024-03-09T12:30:00Z"
  }
}

```

### 2. 메시지 (Message)
- 채팅방별로 메시지를 저장하는 컬렉션 (또는 테이블)
- 각 메시지는 고유한 ID를 가짐

#### 컬럼 
✅ room_id: 어떤 채팅방의 메시지인지 표시
✅ sender: 메시지를 보낸 사용자
✅ content: 메시지 내용 (텍스트, 이미지, 동영상 등 가능)
✅ type: "text", "image", "video" 같은 메시지 유형
✅ status: 메시지 상태 (sent, delivered, read)

예시
```
{
  "_id": "msg_5678",
  "room_id": "room_1234",
  "sender": "user_2",
  "content": "안녕하세요!",
  "timestamp": "2024-03-09T12:30:00Z",
  "type": "text", 
  "status": "delivered"
}

```

### 3. 사용자 Users
- 사용자 정보 및 최근 접속 상태 저장
- 알림 기능을 위해 FCM 토큰을 저장할 수 있음

#### 컬럼 설명
✅ status: "online", "offline" 등의 접속 상태
✅ last_seen: 마지막 접속 시간 (1:1 채팅에서 "마지막 활동 시간" 표시할 때 사용)
✅ push_token: 푸시 알림을 위한 FCM 토큰

예시
```
{
  "_id": "user_1",
  "name": "홍길동",
  "profile_image": "https://example.com/profile.jpg",
  "status": "online",
  "last_seen": "2024-03-09T12:45:00Z",
  "push_token": "abcd1234efgh5678"
}

```

### 4. 메시지 읽음 여부 (Read Receipts)
- 누가 어떤 메시지를 읽었는지 추적하기 위한 컬렉션

#### 컬럼 설명
✅ read_by: 해당 메시지를 읽은 사용자 리스트
✅ 메시지를 보낼 때 기본적으로 read_by는 빈 배열 → 사용자가 읽으면 업데이트


예시
{
  "message_id": "msg_5678",
  "room_id": "room_1234",
  "read_by": ["user_1", "user_3"]
}


참고 자료
https://velog.io/@jay/software-architecture-chat
https://velog.io/@hiy7030/TIL-%EC%B1%84%ED%8C%85-%EA%B8%B0%EB%8A%A5-ERD-%EC%84%A4%EA%B3%84
https://khdscor.tistory.com/115