<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
        
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<c:set var="path" value="${ pageContext.request.contextPath }"/>
<link rel="stylesheet" href="${ path }/css/applyView.css">
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
        <div class="date-header">'${loginMember.memberName }'님이 지원한 채용 공고 리스트</div>

		<div class="card-grid">
		    <c:choose>
		        <c:when test="${not empty applyList}">
				<c:forEach var="apply" items="${applyList}">
				    <div class="job-card"
			    		data-company="${apply.companyName}"
				     	data-result="${apply.result}"
				     	onclick="goToDetail(${apply.rmNo})">
				        <img src="${path}/img/company1.png" alt="${apply.companyName}" class="card-image" />
				        <div class="card-content">
				            <div class="card-title">
							    ${apply.companyName}[
							    <c:choose>
							        <c:when test="${apply.result == 'M' || apply.result == 'm'}">
							            <span class="status waiting">발표 예정</span>
							        </c:when>
							        <c:when test="${apply.result == 'N'}">
							            <span class="status rejected">불합격</span>
							        </c:when>
							        <c:when test="${apply.result == 'Y'}">
							            <span class="status passed">합격</span>
							        </c:when>
							        <c:otherwise>
							            <span class="status unknown">결과 미제출</span>
							        </c:otherwise>
							    </c:choose>
							    ]
							</div>
				            <div class="card-subtitle">지원일: ${apply.applyDate}</div>
				            <div class="card-author">${apply.read != null ? (apply.read == 'Y' ? '읽음' : '안 읽음') : '상태 미확인'}</div>
				        </div>
				        <div class="card-footer">
				            <div></div>
				        </div>
				    </div>
				</c:forEach>
		        </c:when>
		        <c:otherwise>
		            <p style="padding: 20px;">지원한 채용 공고가 없습니다.</p>
		        </c:otherwise>
		    </c:choose>
		</div>
    </div>
</div>

<script>

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
    
    //==============================================================검색창 기능구혀현
	    document.addEventListener("DOMContentLoaded", function () {
			  const searchInput = document.querySelector(".search-input");
			
			  searchInput.addEventListener("keydown", function (e) {
			    if (e.key === "Enter") {
			      const keyword = this.value.trim().toLowerCase();
			
			      const cards = document.querySelectorAll(".job-card");
			      cards.forEach(card => {
			        const company = card.dataset.company?.toLowerCase() || "";
			        const result = card.dataset.result?.toLowerCase() || "";
			
			        // 'M', 'F', 'Y' 등을 실제 텍스트로 변환
			        let resultText = "";
			        switch (result) {
			          case "m": resultText = "발표 예정"; break;
			          case "f": resultText = "불합격"; break;
			          case "y": resultText = "합격"; break;
			          default: resultText = "결과 미제출";
			        }
			
			        const isMatch =
			          company.includes(keyword) ||
			          resultText.includes(keyword);
			
			        card.style.display = isMatch ? "block" : "none";
			      });
			    }
			  });
			});

    
</script>
</body>
</html>