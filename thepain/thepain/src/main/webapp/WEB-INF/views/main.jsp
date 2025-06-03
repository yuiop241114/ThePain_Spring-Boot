<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>DrsP 채용 사이트</title>
<c:set var="path" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" href="${path}/css/main.css">
</head>
<body>
	<div class="container">
		<jsp:include page="common/header.jsp" />

		<!-- 카테고리 섹션 -->
		<div class="categories">
			<a class="category" href="postWriter.pl">
				<div class="category-icon">
					<span class="glasses-icon">👓</span>
				</div>
				<div class="category-name">글 작성하기</div>
			</a>
			<c:choose>
				<c:when test="${empty loginMember.token}">
					<a class="category" href="staffMyPage.me">
						<div class="category-icon">
							<span class="dress-icon">👗</span>
						</div>
						<div class="category-name">마이페이지</div>
					</a>
				</c:when>

				<c:otherwise>
					<a class="category" href="myPage.me">
						<div class="category-icon">
							<span class="dress-icon">👗</span>
						</div>
						<div class="category-name">마이페이지</div>
					</a>
				</c:otherwise>
			</c:choose>
			<a href="jjim.me" class="category">
				<div class="category-icon">
					<span class="watch-icon">⌚</span>
				</div>
				<div class="category-name">찜하기</div>
			</a> <a href="applyState.pl"
				class="category">
				<div class="category-icon">
					<span class="hat-icon">🧢</span>
				</div>
				<div class="category-name">지원현황</div>
			</a>
		</div>

		<!-- 채용공고 리스트 -->
		<div class="section-title">
			우리와 같이 일해요. <a href="jobPostList.pl" class="see-more">See more ></a>
		</div>

		<div class="job-list">
			<c:forEach var="j" items="${previewList}">
				<div class="job-card" onclick="goToDetail('${j.recruitmentNo}')">
					<div class="job-image">
						<img src="${path}/img/company1.png"
							alt="${j.companyName}" class="cyber-img" />
					</div>
					<div class="job-info">
						<div class="job-title">${j.companyName}[${j.hireType}] - ${j.title}</div>
						<div class="job-subtitle">${fn:substring(j.description, 0, 30)}...</div>
						<div class="job-company">연봉: ${j.salaryMin}만 ~
							${j.salaryMax}만</div>
					</div>

					<c:if test="${not empty loginMember}">
						<svg class="heart-icon ${j.jjim ? 'filled' : ''}"
							data-postno="${j.recruitmentNo}" width="18" height="18"
							viewBox="0 0 24 24" fill="none" stroke="#ccc" stroke-width="2">
			    <path
								d="M20.84 4.61a5.5 5.5 0 0 0-7.78 0L12 5.67l-1.06-1.06a5.5 5.5 0 0 0-7.78 7.78L12 21.23l7.78-7.78a5.5 5.5 0 0 0 0-7.78z" />
			  </svg>
					</c:if>
				</div>
			</c:forEach>
		</div>
	</div>

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script>
  // 상세 페이지 이동
  function goToDetail(postNo) {
    window.location.href = "detail.pl?no=" + postNo;
  }

  // 하트 아이콘 이벤트 바인딩
  document.addEventListener("DOMContentLoaded", function () {
    const heartIcons = document.querySelectorAll('.heart-icon');

    heartIcons.forEach(icon => {
      icon.addEventListener('click', function (e) {
        e.stopPropagation(); // 카드 클릭 막기
        const postNo = this.getAttribute('data-postno');
        console.log(e)
        toggleJjim(postNo, this);
      });
    });
  });

  // 찜 토글 Ajax 요청
  function toggleJjim(postNo, iconElement) {
    $.ajax({
      url: "jjimToggle.pl",
      method: "POST",
      data: { postNo: postNo },
      success: function (result) {
        if (result === "added") {
          iconElement.classList.add("filled");
          iconElement.setAttribute("fill", "#ff3366");
          iconElement.setAttribute("stroke", "#ff3366");
        } else if (result === "removed") {
          iconElement.classList.remove("filled");
          iconElement.setAttribute("fill", "none");
          iconElement.setAttribute("stroke", "#ccc");
        } else if (result === "loginRequired") {
          alert("로그인이 필요합니다.");
          window.location.href = "/loginPage";
        }
      },
      error: function () {
        alert("찜하기 처리 실패");
      }
    });
  }
  
  
</script>

<c:if test="${not empty alertMsg}">
    <script>
        alert("${alertMsg}");
    </script>
    <c:remove var="alertMsg" scope="session" />
</c:if>

</body>
</html>
