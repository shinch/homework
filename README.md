## Kakao Bank 사전 과제

### 개발 환경
  * gradle 7.4
  * gradle Version Catalog
  * JDK 17
  * springboot 3.0.4
  * springdoc 2.0.4
  * spock 2.1-groovy-3.0

### Module 설명
  * domain : Domain 관련 정의
  * application : API 제공 Controller

### 개발 참고 사항
  * 카카오 블로그 검색 API 장애 대응 처리
    * 카카오 Api 시간내 10번 에러 발생시 Naver로 자동 변경
    * 장애 복구 후 원복 할 경우는 제공 되는 API를 통한 수동 처리
  * 검색 횟수 증가
    * 검색 결과 반환 과 횟수 증가를 격리
      * 트래픽이 많은 것을 가정 하여 검색 횟수의 실시간성 보다 결과 반환에 중요도를 준 구조
    * 순차적으로 검색 횟수 증가 처리로 하여 동시성 고려
    * 성능도 고려할 수 있으나 추가 요건 범위에 벗어나 고려 하지 않음

### 참고화일
  * 실행 jar
    * 