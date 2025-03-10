# HA 정의 및 필요성

## HA 정의


## HA 필요성
- 장애 유연성 : 장애 발생 시 서비스 중단 최소화(99.999% Five Nine)
- 서비스 연속성 : 기업의 Mission Critical한 업무에 대한 지속적인 서비스 필요성 증대
- Semi-FT시스템 : 고비용의 결함허용시스템(FT) 대안


# HA 용어 정리
High Availability (고가용성)
- 서버와 네트워크, 프로그램등의 정보 시스템이 오랜 시간 지속적으로 정상 운영이 가능한 성질
- 시스템이 이슈 발생시 얼마나 빨리 복구하는가

기본적인 용어
Active, Standby, Master, Slave, Backup

Active : client로 부터 request를 받아서 처리하는 역할(ing 현재 진행)
Standby : 예측한 에벤트(장애 등)가 발생했을 때, Active 대신 request를 처리하는 역할(active 감시)

Master : 하나의 역할을 수행 하는데 있어 동작의 주체가 되는 역할을 수행
Slave : '주로' 마스터의 지시에 따라, 종속적인 역할을 수행
Backup : 특정 서버의 역할을 대체하기 위해, 준비된 서버 (Standby 서버)

사용 예시
Cache 서버의 이중화
- cache 서버의 다운 발생 시, 대체할 수 있는 미러링 서버를 구성
- Active/ Standby 구성

cache 서버 특징
휘발성 메모리에 저장하는 방식으로 서버에 이슈가 발생했을 경우에 데이터를 손실할 가능성이 크다.

이중화와 미러링
이중화는 클러스터링의 방식 중 하나로 서버를 다중화하는 방식이다. 

Sharding
- 