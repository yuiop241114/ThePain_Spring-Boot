# Project Thepain 고도화


# 📘 프로젝트 소개
기존에 개발자를 위한 채용공고 사이트 프로젝트 Thepain은 Spring Legacy Project를 사용하여 프로젝트를 진행하였습니다.
그래서 코드를 작성하여 유지 보수나 설정을 하는 과정이 복잡하고 힘들든 단점이 존재하였습니다. 그래서 Spring Boot로 프레임워크 마이그레이션을 하였고
이 후 해당 작업을 기반으로 채용공고 사이트 API 개발까지 진행하였습니다


# ⏱ 개발기간
25.06.08 ~ 25.06.19

# ⚙ 개발 환경
- OS : Windows 11  
- 개발 도구 : Intellij, SQL Developer  
- 서버 : Apache Tomcat 9.0  
- DBMS : Oracle  
- Front-end : HTML5 / CSS3 / JavaScript / jQuery  
- Back-end : Java 11 / Spring Framework(Spring Boot) / MyBatis  
- 협업 도구 : GitHub, Git

# 🔼 고도화 작업
## 1. 프레임워크 마이그레이션
Spring Legacy Proeject를 Spring Boot로 전환하는 작업을 진행하였습니다.

**설정 파일 간소화 및 정적 리소스 위치 수정** <br>
기존에 Maven 프로젝트에서 Gradle-Groovy로 바뀌면서 설정 파일을 build.gradle, application.properties 파일에서 설정 및 라이브러리 등을 관리하면서 더 빠르게 프로젝트를 시작할 수 있었습니다. 
Spring Boot에서는 이미지 파일이나 CSS 파일 같은 정적 리소스들은 static 파일에 위치해야 읽기 때문에 위치도 변경하였습니다

**MyBatis 소스코드 리팩토링** <br>
Spring Legacy Projecy에서는 SqlSession 객체를 사용하여 직접 호출 했지만 Spring Boot에서는 Mapper 클래스를 생성하여 매핑하는 방식으로 코드를 리팩토링 하였습니다

## 2. RESTful API 설계 및 개발**
**JWT를 사용하여 Token 발행 및 검증** <br>
API 설계 과정에서 만약 API를 개발한다고 하더라도 URL만 알고 있다면 누구나 접근할 수 있는 보안 상 허점이 존재하여 
JWT를 사용하여 고도화 하는 프로젝트의 회원인 경우에만 해당 API를 사용 가능하게 설계를 진행하였습니다.

**RESTful 방식으로 API 개발** <br>
API를 개발을 끝마치더라도 코드를 유지 보수 하거나 API를 사용하는 개발자가 코드를 이해하기 쉽게 하기 위해 RESTful 방식으로 개발을 진행하였습니다

**API Method**
- GET <br>
***채용공고 전체 조회*** <br>
***회사 이름으로 채용공고 조회*** <br>
***회원 아이디로 회원 정보 조회*** <br>

| 설명     | URL              | 이모지 |
|---------------|-------------------|--------|
| 채용공고 전체 조회          | /allJobList       | ☕     |
| 회사 이름으로 채용공고 조회   | /jobList/회사명        | 🌱     |
| 회원 아이디로 회원 정보 조회         | /member/아이디         | 🐬     |


  
- POST
***아이디/비밀번호로 토큰 발행*** <br>
