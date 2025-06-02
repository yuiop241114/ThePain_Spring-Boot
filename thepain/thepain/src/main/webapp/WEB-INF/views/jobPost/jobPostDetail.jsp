<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${post.companyName}| 채용 상세</title>
<c:set var="path" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" href="${path}/resources/css/jobPostDetail.css" />
</head>
<body>

	<jsp:include page="../member/menubar.jsp" />

	<div class="container">
		<div class="main-content">

			<div class="hero-image">
				<img
					src="${path}${img.fileRoot}${img.fileEditName}"
					alt="회사 이미지" class="card-image">
			</div>

			<div class="content-header">
				<h1 class="content-title">${post.companyName} - ${post.title}</h1>
				<div style="white-space: pre-line; margin-bottom: 30px;">
					  ${post.description}
					</div>


				<div class="info-box">
					<div class="info-item">
						<div class="info-label">고용 형태</div>
						<div class="info-value">${post.hireType}</div>
					</div>
					<div class="info-item">
						<div class="info-label">급여</div>
						<div class="info-value">${post.salaryMin}만~
							${post.salaryMax}만</div>
					</div>
					<div class="info-item">
						<div class="info-label">상태</div>
						<div class="info-value">${post.status eq 'Y' ? '진행 중' : '마감'}</div>
					</div>
				</div>

				<div class="button-group">
					<div class="left-buttons">
						<c:if test="${empty loginMember}">
							<!-- 로그인되지 않은 경우 -->
							<h2>로그인 후 지원 가능합니다.</h2>
						</c:if>

						<c:if test="${not empty loginMember && loginMember.token != null && gitLoginMember != null}">
							<!-- 로그인 후 지원하기 버튼 -->
							<form action="applyinsert.at">
								<input type="hidden" name="postNo" value="${post.postNo}">
								<input type="hidden" name="memberNo"
									value="${loginMember.memberNo}"> <input type="hidden"
									name="alarmNo" value="1">
								<button class="btn btn-primary" type="submit">지원하기</button>
							</form>
							
						</c:if>
					</div>

					<div class="right-buttons">
						<c:if test="${not empty loginMember and loginMember.memberNo eq post.memberNo}">
						    <!-- 로그인된 사람 중 작성자만 수정, 삭제 버튼 표시 -->
						    <button class="btn btn-primary" onclick="editPost(${post.postNo})">글 수정하기</button>
						    <button class="btn btn-danger" onclick="deletePost(${post.postNo})">글 삭제하기</button>
						</c:if>
					</div>
				</div>
			</div>

			<div class="job-description">
				<h2 class="section-title">주요 업무</h2>
					<div style="white-space: pre-line; margin-bottom: 30px;">
					  ${post.duty}
					</div>
					
					<h2 class="section-title">우대 사항</h2>
					<div style="white-space: pre-line; margin-bottom: 30px;">
						  ${post.benefits}
						</div>

			</div>

			<!-- 매칭 모달 -->
			<div class="modal-overlay" id="matchModal" style="display: none;">
				<div class="modal">
					<h2>구직자 매칭 시스템</h2>
					<form>
						<label for="skill">기술</label> <input type="text" id="skill"
							placeholder="예: Java, Spring"> <label for="experience">경력</label>
						<select id="experience">
							<option value="" id="ff">선택해주세요.</option>
							<option value="1">1년 미만</option>
							<option value="2">1~3년</option>
							<option value="3">3~5년</option>
							<option value="4">5년 이상</option>
						</select> <label for="age">나이</label> <input type="number" id="age"
							placeholder="예: 29">

						<div class="button-group">
							<button type="button" class="btn-cancel" onclick="closeModal()">닫기</button>
							<button type="submit" class="btn-submit">찾기</button>
						</div>
					</form>
				</div>
			</div>

		</div>
	</div>

	<script>
  // 모달 열기
  document.getElementById("openMatchModal").addEventListener("click", function () {
    document.getElementById("matchModal").style.display = "flex";
  });

  // 모달 닫기
  function closeModal() {
    document.getElementById("matchModal").style.display = "none";
  }
  
  // 글 삭제하기
  function deletePost(postNo) {
	    if (confirm("정말 삭제하시겠습니까?")) {
	      window.location.href = "deletePost.pl?no=" + postNo;
	    }
	  }
  // 글 수정하기.
  function editPost(postNo) {
	    window.location.href = "updateForm.pl?no=" + postNo;
	  }
  
  
</script>

</body>
</html>
