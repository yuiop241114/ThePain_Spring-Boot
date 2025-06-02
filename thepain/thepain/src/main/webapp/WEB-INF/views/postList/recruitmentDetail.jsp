<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<title>${recruit.title}</title>
  <style>
    body { 
		font-family: sans-serif;
		margin: 0; 
		padding: 20px; 
		background: 
		#f5f5f5; 
	}
    .banner-img { 
		width: 100%; 
		height: 200px; 
		object-fit: cover; 
		border-radius: 12px; 
	}
    .container { 
		max-width: 900px; 
		margin: auto; 
		background: white; 
		padding: 30px; 
		border-radius: 16px; 
		box-shadow: 0 2px 8px rgba(0,0,0,0.1); }
    h1 { 
		margin-bottom: 10px; 
	}
    .job-meta { 
		font-size: 14px; 
		color: #666; 
		margin-bottom: 20px; 
	}
    .section-title { 
		font-size: 20px; 
		margin-top: 40px; 
	}
    .btns { 
		display: flex; 
		gap: 10px; 
		margin-top: 30px; 
	}
    .btn { 
		padding: 10px 20px; 
		border: none; 
		border-radius: 8px; 
		cursor: pointer; 
	}
    .btn-primary { 
		background-color: #4F46E5; 
		color: white; 
	}
    .btn-secondary { 
		background-color: #eee; 
	}
    .desc { 
		white-space: pre-wrap; 
		line-height: 1.6; 
		margin-top: 10px; 
	}
  </style>
</head>
<body>
	<div class="container">
	  
	  <img src="${recruit.bannerUrl}" class="banner-img" alt="회사 배너">
		
	  <!-- 회사 명, 회사 사진, 회사 address, 회사 연봉 등-->
	  <h1>${recruit.title}</h1>
	  <p class="job-meta">${recruit.company} | ${recruit.location} | 연봉 ${recruit.salary}</p>
	  <p>${recruit.summary}</p>
  
	  <div class="btns">
		<button class="btn btn-primary">매칭하기</button>
		<button class="btn btn-primary">신청하기</button>
	  </div>

	  <h1 class="section-title">직무명이 올 자리</h1>
	  <div class="desc"> </div>
  
	  <h3 class="section-title">주요 업무</h3>
	  <div class="desc"> </div>
  
	  <h3 class="section-title">자격 요건</h3>
	  <div class="desc"> </div>
  
	  <h3 class="section-title">우대 사항</h3>
	  <div class="desc"> </div>
  
	  <h3 class="section-title">추가 설명</h3>
	  <div class="desc"> </div>
	</div>
  </body>
</html>