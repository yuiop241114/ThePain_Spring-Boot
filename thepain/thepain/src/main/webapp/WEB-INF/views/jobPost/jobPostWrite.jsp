<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<c:set var="path" value="${ pageContext.request.contextPath }"/>
<link rel="stylesheet" href="${ path }/css/jobPostWrite.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/themes/material_green.css">
<script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>

</head>
<body>
	<jsp:include page="../member/menubar.jsp"/>
		
	 <div class="container">
        <h1>구인공고 글 작성하기</h1>
        
        <c:if test="${not empty sessionScope.alertMsg}">
		    <script>
		        alert("${sessionScope.alertMsg}");
		    </script>
		    <c:remove var="alertMsg" scope="session"/>
		</c:if>
		
        <form action="insertJob.pl" method="post" enctype="multipart/form-data" >
			<div class="form-group">
			    <h2 for="companyLogo" style="display: block; margin-bottom: 10px;">회사 로고</h2>
			
			    <!-- 이미지 미리보기 (가운데 정렬) -->
			    <div class="preview-container" style="text-align: center; margin-bottom: 10px;">
			        <img id="logoPreview" src="#" alt="미리보기" style="display: none;"  />
			    </div>
			
			    <!-- 파일 업로드 input (왼쪽 정렬) -->
			    <input type="file" id="companyLogo" name="companyLogo" required />
			</div>
						
			<div class="form-group">
			    <label for="companyName">회사 이름</label>
			    <div style="display: flex; gap: 10px;">
			        <input type="text" id="companyName" name="companyName" placeholder="회사 이름" value="${loginMember.corporation}" readonly>
			    </div>
			</div>
	        
	        <div class="form-group">
	            <label for="companyName">직무 명</label>
	            <div style="display: flex; gap: 10px;">
	                <input type="text" id="companyName" name="title" placeholder="직무 명" value="${post.title}">
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
				  <label for="salaryMax">최소 급여 (원)</label>
				  <select id="salaryMax" name="salaryMin">
				    <option disabled <c:if test="${empty post.salaryMax}">selected</c:if>>선택하세요</option>
				    
				    <c:forEach var="i" begin="2400" end="6600" step="100">
				      <option value="${i}" <c:if test="${post.salaryMax == i}">selected</c:if>>${i}만원</option>
				    </c:forEach>
				  </select>
				</div>
	&nbsp;&nbsp;
	            <div>  <br>~<br>  </div>
	&nbsp;&nbsp;
	            <div class="form-group" id="triple">
				  <label for="salaryMax">최대 급여 (원)</label>
				  <select id="salaryMax" name="salaryMax">
				    <option disabled <c:if test="${empty post.salaryMax}">selected</c:if>>선택하세요</option>
				    
				    <c:forEach var="i" begin="2400" end="6600" step="100">
				      <option value="${i}" <c:if test="${post.salaryMax == i}">selected</c:if>>${i}만원</option>
				    </c:forEach>
				    
				  </select>
				</div>
	&nbsp;&nbsp;
	&nbsp;&nbsp;
	&nbsp;&nbsp;
				<div class="form-group" id="triple">
				  <label for="deadline">마감일자</label>
				  <input type="text" id="deadline" name="deadLine"
				         class="form-control"
				         placeholder="날짜 선택"
				         value="${post.deadLine}" />
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
	            <button type="submit" class="submit-button">글 올리기</button>
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
  
  //날짜 선택 태그 
  flatpickr("#deadline", {
	    dateFormat: "Y-m-d",
	    defaultDate: "${post.deadline}"
	  });

	  // textarea 자동 높이 조절 유지
	  document.addEventListener("DOMContentLoaded", function () {
	    document.querySelectorAll("textarea").forEach(textarea => {
	      const resize = () => {
	        textarea.style.height = 'auto';
	        textarea.style.height = (textarea.scrollHeight) + 'px';
	      };
	      resize();
	      textarea.addEventListener("input", resize);
	    });
	  });
	  
	  $(document).ready(function () {
		    $("#companyLogo").change(function () {
		        const file = this.files[0];
		        if (file) {
		            const reader = new FileReader();
		            reader.onload = function (e) {
		                // 이미지 미리보기를 업데이트
		                $("#logoPreview").attr("src", e.target.result).show();
		            };
		            reader.readAsDataURL(file);
		        }
		    });
		});
</script>

</body>
</html>