<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>찜한 채용 목록</title>
<c:set var="path" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" href="${path}/css/jjim.css">
</head>
<body>
<jsp:include page="../member/menubar.jsp"/>

<div class="container">
    <div class="header">
        <a href="goMainPage.me" class="back-button">← 홈</a>
        <div class="search-container">
            <input type="text" class="search-input" placeholder="검색어를 입력하세요" />
        </div>
    </div>

    <div class="date-section">
        <div class="date-header">찜한 채용 공고</div>

        <div class="card-grid">
            <c:choose>
                <c:when test="${not empty jjimList}">
                    <c:forEach var="j" items="${jjimList}">
                        <div class="job-card" 
	                        	data-company="${j.companyName}" 
							    data-hiretype="${j.hireType}"
						        data-salary="${j.salaryMax}"
	                        	onclick="goToDetail(${j.recruitmentNo})">
	                        	
                            <img src="${path}//img/company1.png" alt="${j.companyName}" class="card-image" />
                            <div class="card-content">
                                <div class="card-title">[${j.hireType}] ${fn:substring(j.description, 0, 30)}...</div>
                                <div class="card-subtitle">연봉: ${j.salaryMin}만 ~ ${j.salaryMax}만</div>
                                <div class="card-author">${j.companyName}</div>
                            </div>
                            <div class="card-footer">
                                <div></div>
                                <svg class="heart-icon filled" 
								     data-postno="${j.recruitmentNo}" 
								     width="18" height="18" viewBox="0 0 24 24">
                                    <path d="M20.84 4.61a5.5 5.5 0 0 0-7.78 0L12 5.67l-1.06-1.06a5.5 5.5 0 0 0-7.78 7.78l1.06 1.06L12 21.23l7.78-7.78 1.06-1.06a5.5 5.5 0 0 0 0-7.78z"></path>
                                </svg>
                            </div>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <p style="padding: 20px;">찜한 채용 공고가 없습니다.</p>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<script>
    // 하트 클릭 이벤트 초기화
    document.addEventListener("DOMContentLoaded", function () {
        const heartIcons = document.querySelectorAll('.heart-icon');

        heartIcons.forEach(icon => {
            icon.addEventListener('click', function (e) {
                e.stopPropagation(); // 카드 클릭 방지
                const postNo = this.dataset.postno;

                if (!postNo) return;

                toggleJjimUniversal(this, postNo);
            });
        });
    });

    // 상세 페이지 이동
    function goToDetail(postNo) {
        window.location.href = 'detail.pl?no=' + postNo;
    }

    // ------------------------------찜 처리 함수 (공통)
    function toggleJjimUniversal(iconElement, postNo) {
        $.ajax({
            url: "jjimToggle.pl",
            method: "POST",
            data: { postNo: postNo },
            success: function(result) {
                console.log("찜 Ajax 응답: ", result);

                if(result === "added") {
                    iconElement.classList.add("filled");
                } else if(result === "removed") {
                    iconElement.classList.remove("filled");

                    // 찜목록 페이지라면, 카드에서 완전히 제거
                    const card = iconElement.closest(".card") || iconElement.closest(".job-card");
                    if (card && window.location.href.includes("jjim.me")) {
                        card.remove();

                        // 남은 찜 항목 없으면 안내 문구 추가
                        if (document.querySelectorAll('.card, .job-card').length === 0) {
                            const listContainer = document.querySelector('.card-grid') || document.querySelector('.job-list');
                            if (listContainer) {
                                listContainer.innerHTML = "<p style='padding:20px;'>찜한 채용 공고가 없습니다.</p>";
                            }
                        }
                    }
                } else if(result === "loginRequired") {
                    alert("로그인이 필요합니다.");
                    window.location.href = "/loginPage";
                }
            },
            error: function() {
                alert("찜하기 처리 실패");
            }
        });
    }
    
    //=============================================================JS: 검색 후 필터링 기능
	 document.addEventListener("DOMContentLoaded", function () {
	    const searchInput = document.querySelector(".search-input");
	
	    searchInput.addEventListener("keydown", function (e) {
	      if (e.key === "Enter") {
	        const keyword = this.value.toLowerCase();
	
	        const cards = document.querySelectorAll(".job-card");
	        cards.forEach(card => {
	          const company = card.dataset.company.toLowerCase();
	          const hireType = card.dataset.hiretype.toLowerCase();
	
	          const isMatch = company.includes(keyword) || hireType.includes(keyword);
	
	          card.style.display = isMatch ? "block" : "none";
	        });
	      }
	    });
	  });
    
    
</script>



</body>
</html>
