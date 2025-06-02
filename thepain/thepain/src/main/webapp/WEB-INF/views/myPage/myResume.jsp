<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>마이페이지</title>
<c:set var="path" value="${ pageContext.request.contextPath }"/>
<link rel="stylesheet" href="${ path }/resources/css/myResume.css">
</head>
<body>
    <jsp:include page="../member/menubar.jsp"/>
    <!-- 사이드바 -->
    <div class="sidebar">
        <div class="logo">
            <img src="/api/placeholder/60/60" alt="로고">
        </div>
        
        <div class="profile-brief">
            <img src="/api/placeholder/40/40" alt="프로필">
            <div class="profile-info">
                <div class="name">Sarah</div>
                <div class="email">sarah@corp.com</div>
            </div>
        </div>
        
        <div class="menu">
            <a href="#" class="menu-item active">
                <i>📝</i> 메인페이지
            </a>
            <a href="#" class="menu-item">
                <i>🔍</i> 채용 공고
            </a>
            <a href="#" class="menu-item">
                <i>📅</i> 채용 매칭 시스템
            </a>

            <a href="#" class="menu-item">
                <i>📄</i> 내가 올린 채용공고
            </a>
            <a href="#" class="menu-item">
                <i>🚪</i> 회원탈퇴
            </a>
        </div>
    </div>
    
    <!-- 메인 콘텐츠 -->
    <div class="main-content">
        <div class="profile-card">
            <div class="documents-section">
                <div class="document-card">  
                    <div id="fileDiv">
                        <h1 style="width: 150px;">이력서 목록</h1>
                        <button id="uploadFile">업로드</button>
                        <button id="deletFile">삭제</button>
                    </div>
                    <form action="">
                        <div class="upload-box">
                            <input type="checkbox">백엔드 개발자 모십니다.
                        </div>
                        <div class="upload-box">
                            <input type="checkbox">프론트 개발자 모십니다. 
                        </div>
                        <div class="upload-box">
                            <input type="checkbox">디자인 개발자 모십니다. 
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</body>
</html>