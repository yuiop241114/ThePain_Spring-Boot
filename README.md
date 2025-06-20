# Project Thepain 고도화


# 📘 프로젝트 소개
기존에 개발자를 위한 채용공고 사이트 프로젝트 Thepain은 Spring Legacy Project를 사용하여 프로젝트를 진행하였습니다.
그래서 코드를 작성하여 유지 보수나 설정을 하는 과정이 복잡하고 힘들든 단점이 존재하였습니다. 그래서 Spring Boot로 프레임워크 마이크레이션을 하였고
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

# 고도화 작업
## 1. 프레임워크 마이그레이션
Spring Legacy Proeject를 Spring Boot로 전환하는 작업을 진행하였습니다.

**설정 파일 간소화**
기존에 Maven 프로젝트에서 Gradle-Groovy로 바뀌면서 설정 파일을 build.gradle, application.properties 파일에서 설정 및 라이브러리 등을 관리하면서 더 빠르게 프로젝트를 시작할 수 있었습니다. 

## 2. RESTful API 설계 및 개발**
