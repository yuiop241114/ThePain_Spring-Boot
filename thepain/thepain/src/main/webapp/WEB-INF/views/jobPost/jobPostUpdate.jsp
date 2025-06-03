<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>채용 공고 수정</title>
<c:set var="path" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" href="${path}/css/jobPostUpdate.css" />
</head>
<body>

	<jsp:include page="../member/menubar.jsp"/>
	
	 <div class="container">
        <h1>구인공고 글 수정하기</h1>
        
        <c:if test="${not empty sessionScope.alertMsg}">
		    <script>
		        alert("${sessionScope.alertMsg}");
		    </script>
		    <c:remove var="alertMsg" scope="session"/>
		</c:if>
		
        <form action="updatePost.pl" method="post">
        <input type="hidden" name="postNo" value="${post.postNo}">
	        <div class="form-group">
	        
	            <label for="companyName">회사 이름</label>
	            <div style="display: flex; gap: 10px;">
	                <input type="text" id="companyName" name="companyName" placeholder="회사 이름" value="${post.companyName}">
	                <!-- <button class="blue-button">회사 검색</button> -->
	            </div>
	        </div>
        
	       <div class="form-group">
	            <label for="hireType">근무 형태</label>
	             <select id="hireType" name="hireType">
			            <option disabled <c:if test="${empty post.hireType}">selected</c:if>>선택하세요</option>
			            <option <c:if test="${post.hireType eq '정규직'}">selected</c:if>>정규직</option>
			            <option <c:if test="${post.hireType eq '비정규직'}">selected</c:if>>비정규직</option>
			            <option <c:if test="${post.hireType eq '기간직'}">selected</c:if>>기간직</option>
			            <option <c:if test="${post.hireType eq '시간제 정규직'}">selected</c:if>>시간제 정규직</option>
			            <option <c:if test="${post.hireType eq '파견직'}">selected</c:if>>파견직</option>
			        </select>
	        </div>
	        <div id="hh">
	            <div class="form-group" id="triple">
	                <label for="salaryMin">최소 급여 (원)</label>
	                <select id="salaryMin" name="salaryMin">
		                <option disabled <c:if test="${empty post.salaryMin}">selected</c:if>>선택하세요</option>
		                <option value="2400" <c:if test="${post.salaryMin == 2400}">selected</c:if>>2400만</option>
		                <option value="3000" <c:if test="${post.salaryMin == 3000}">selected</c:if>>3000만</option>
		            </select>
	            </div>
	&nbsp;&nbsp;
	            <div>  <br>~<br>  </div>
	&nbsp;&nbsp;
	            <div class="form-group" id="triple">
	                <label for="salaryMax">최대 급여 (원)</label>
	                <select id="salaryMax" name="salaryMax">
		                <option disabled <c:if test="${empty post.salaryMax}">selected</c:if>>선택하세요</option>
		                <option value="2400" <c:if test="${post.salaryMax == 2400}">selected</c:if>>2400만</option>
		                <option value="3000" <c:if test="${post.salaryMax == 3000}">selected</c:if>>3000만</option>
		            </select>
	            </div>
	        </div>
	      
	        
	        
	
	        <div class="form-group">
	            <label for="jobDescription">자격요건</label>
	            <textarea id="jobDescription" name="description">${post.description}</textarea>
	        </div>
	
	        <div class="form-group">
	            <label for="requirements">주요업무</label>
	            <textarea id="requirements" name="duty">${post.duty}</textarea>
	        </div>
	
	        <div class="form-group">
	            <label for="benefits">우대사항</label>
	            <textarea id="benefits" name="benefits">${post.benefits}</textarea>
	        </div>
	
	        <div class="button-group">
	            <button type="button" class="cancel-button" onclick="history.back()">취소하기</button>
	            <button type="submit" class="submit-button">글 수정하기</button>
	        </div>
     </form>
    </div>
	<script>
  // 모든 textarea에 입력 이벤트 발생 시 높이 자동 조절
  document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll("textarea").forEach(textarea => {
      const resize = () => {
        textarea.style.height = 'auto'; // 높이 초기화
        textarea.style.height = (textarea.scrollHeight) + 'px'; // 스크롤 높이만큼 재설정
      };

      // 페이지 로드 시 높이 맞춤
      resize();

      // 입력할 때마다 높이 맞춤
      textarea.addEventListener("input", resize);
    });
  });
</script>

</body>
</html>
