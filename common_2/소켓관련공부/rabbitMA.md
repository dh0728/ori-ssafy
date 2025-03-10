# RabbitMQ
- AMQP를 구현한 오픈소스 메세지 브로커
- producers에서 consumers로 메세지(요청)을 전달할 때 중간에서 브로커 역할을 한다.

사용 케이스
- 요청을 많은 사용자에게 전달할 때
- 요청에 대한 처리시간이 길 때
- 많은 작업이 요청되어 처리를 해야 할 떄

해당하는 요청을 다른 API에게 위임하고 빠른 응답을 할 때 많이 사용한다. 
MQ를 사용하면 애플리케이션에 결합도를 낮출 수 있다는 장점이 있다.

![alt text](image-11.png)

- **Producer**: 요청을 보내는 주체, 보내고자 하는 메세지를 exchange에 publish한다.
- **Consumer**: producer로부터 메세지를 받아 처리하는 주체
- **Exchange**: producer로부터 전달받은 메세지를 어떤 queue로 보낼지 결정하는 장소, 4가지 타입이 있음
- **Queue**: consumer가 메세지를 consume하기 전까지 보관하는 장소
- **Binding**: Exchange와 Queue의 관계, 보통 사용자가 특정 exchange가 특정 queue를 binding하도록 정의한다. (fanout 타입은 예외)


https://kkangi.tistory.com/19