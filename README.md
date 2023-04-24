# Blog-v2
https://github.com/codegyeon/Blog

기존 Blog 프로젝트에서 코드 개선과 기능 추가

- 웹소켓을 사용하여 댓글 작성시 해당 게시글 작성자에게 알림이 가게하기
- 실시간 익명 채팅 기능
- oAuth2.0 적용
- redis 적용


### 프로젝트 진행중 변경, 에러사항
spring security 5 -> 6 으로 전환하면서 변경사항이 있음
  - antMatchers -> requestMatchers 로 변경

