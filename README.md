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

**📍 설정 파일 간소화 및 정적 리소스 위치 수정** 

- 기존에 Maven 프로젝트에서 Gradle-Groovy로 바뀌면서 설정 파일을 build.gradle, application.properties 파일에서 설정 및 라이브러리 등을 관리하면서 더 빠르게 프로젝트를 시작할 수 있었습니다.
  
| 프로젝트 형태                     | 설정 파일                 | 
|--------------------------|---------------------|
| Spring Legacy Project(Maven)        | pom.xml, .springBeans, root-context.xml 등...        | 
| Spring Boot(Gradle-Groovy) | application.properties, build.gradle     |


           
- Spring Boot에서는 이미지 파일이나 CSS 파일 같은 정적 리소스들은 static 파일에 위치해야 읽기 때문에 위치도 변경하였습니다
  
| 프로젝트 형태                     | 정적 리소스 위치                 | 
|--------------------------|---------------------|
| Spring Legacy Project(Maven)        | `webapp/resources/정적리소스`        | 
| Spring Boot(Gradle-Groovy) | `resources/static/정적리소스`     |



**♻️ MyBatis 소스코드 리팩토링** <br>
- Spring Legacy Projecy에서는 SqlSession 객체를 사용하여 직접 호출 했지만 Spring Boot에서는 Mapper 클래스를 생성하여 매핑하는 방식으로 코드를 리팩토링 하였습니다
  
| 프로젝트 형태                     | 요청 처리 흐름                 | 
|--------------------------|---------------------|
| Spring Legacy Project(Maven)        | `Controller -> Service -> Dao`        | 
| Spring Boot(Gradle-Groovy) | `Controller -> Service -> Mapper`     |


**📂 이미지 및 첨부 파일 위치 수정 및 소스 코드 리팩토링** <br>
- 이미지 및 첨부 파일을 프로젝트에 static 경로로 저장하는 경우 배포나 빌드 시 소실되는 경우가 발생할 가능성이 존재 하기 때문에 만일을 대비하여 프로젝트 외부에 파일을 저장하는 방식으로 변경 하였습니다
- 외부 경로를 사용하기 위해 application.properties에 첨부 파일을 저장하기 위한 절대 경로를 설정하였습니다.
<img src="https://raw.githubusercontent.com/yuiop241114/ThePain_Spring-Boot/main/thepain/src/main/resources/static/img/외부경로.png" width="500"/>

- 첨부 파일을 용도에 따라 구분하여 저장 및 사용하기 위해 원하는 경로에 따라 다른 디렉토리에 저장하게 설정하였습니다
<img src="https://raw.githubusercontent.com/yuiop241114/ThePain_Spring-Boot/main/thepain/src/main/resources/static/img/외부 경로2.png" width="700"/>


## 2. RESTful API 설계 및 개발
**🔐 JWT를 사용하여 Token 발행 및 검증** <br>
- API 설계 과정에서 만약 API를 개발한다고 하더라도 URL만 알고 있다면 누구나 접근할 수 있는 보안 상 허점이 존재하여 JWT를 사용하여 고도화 하는 프로젝트의 회원인 경우에만 해당 API를 사용 가능하게 설계를 진행하였습니다. <br>
<img src="https://raw.githubusercontent.com/yuiop241114/ThePain_Spring-Boot/main/thepain/thepain/src/main/resources/static/img/JWT.png" width="700"/>

**🔌 RESTful 방식으로 API 개발** <br>
- API를 개발을 끝마치더라도 코드를 유지 보수 하거나 API를 사용하는 개발자가 코드를 이해하기 쉽게 하기 위해 RESTful 방식으로 개발을 진행하였습니다

**API Method**

- GET

| 설명                     | URL                 | Token 필요 여부 |
|--------------------------|---------------------|------------------|
| 채용공고 전체 조회        | `/getApi/allJobList`       | O                |
| 회사 이름으로 채용공고 조회 | `/getApi/jobList/회사명`     | O                |
| 회원 아이디로 회원 정보 조회 | `/getApi/member/아이디`       | O                |


- POST

| 설명                     | URL                | Token 필요 여부 |
|--------------------------|--------------------|------------------|
| 아이디/비밀번호로 토큰 발행 | `/postApi/loginToken`      | X               |
| 채용공고 등록 | `/postApi/insertPost`      | X               |

## 3. AWS를 사용한 배포
**데이터 베이스 설치**
- 데이터 베이스는 오라클을 사용하는데 AWS에 있는 RDS의 경우 오라클 사용을 유료로 해야하기 때문에 EC2 환경에 직접 설치하여 사용했습니다.

**Github Actions를 이용한 CI/CD 파이프라인 구축**
- 수동으로 배포 및 빌드 하는 경우 매우 번거롭기 때문에 GitHub에 수정한 코드를 올릴 경우 자동으로 빌드 및 EC2로 배포되게 하였습니다.
- Workflow를 통해 원하는 동작을 설정하여 Github에 Push 할 경우 자동 빌드 및 배포를 할 수 있게 하였습니다.
