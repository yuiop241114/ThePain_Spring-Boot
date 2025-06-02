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
<link rel="stylesheet" href="${ path }/resources/css/jobPostList.css">
</head>
<body>
    <jsp:include page="../member/menubar.jsp"/>
	<div class="container">
    
    <div class="main-content">
      <a href="goMainPage.me" class="back-button">
        ← 홈
      </a>
      
      <div class="search-container">
        <input type="text" class="search-input" placeholder="검색어를 입력하세요">
      </div>
      
      <div class="categories">
        <div class="category">정규직</div>
        <div class="category">비정규직</div>
        <div class="category">기간직</div>
        <div class="category">시간제 정규직</div>
        <div class="category">파견직</div>
      </div>
      
      <div class="checkbox-filters">
			  연봉 :
			  <label class="checkbox-filter">
			    <input type="checkbox" value="2500-999999"> 2500만원 ~
			  </label>
			  <label class="checkbox-filter">
			    <input type="checkbox" value="3500-999999"> 3500만원 ~
			  </label>
			  <label class="checkbox-filter">
			    <input type="checkbox" value="4500-999999"> 4500만원 ~
			  </label>
			  <label class="checkbox-filter">
			    <input type="checkbox" value="5500-999999"> 5500만원 ~
			  </label>
			  <label class="checkbox-filter">
			    <input type="checkbox" value="6500-999999"> 6500만원 ~
			  </label>
			</div>

      <!-- =========================================채용공고 글 리스트 ================= -->
      <!-- 첫 번째 행 카드 -->
      <div class="card-grid">
			  <c:forEach var="j" items="${list}">
					    <div class="card" 
						     data-company="${j.companyName}" 
						     data-hiretype="${j.hireType}"
					         data-salary="${j.salaryMax}"
						     onclick="goToDetail(${j.recruitmentNo})">

					      <img src="${pageContext.request.contextPath}/resources/img/company1.png" alt="회사 이미지" class="card-image">
					
					      <div class="card-content">
					        <div class="card-title">${j.companyName}[${j.hireType}]</div>
					        <div class="card-subtitle"> ${fn:substring(j.description, 0, 30)}...</div>
					        <div class="card-author">연봉: ${j.salaryMin}만 ~ ${j.salaryMax}만</div>
					      </div>
					        <c:if test="${not empty loginMember}">
					
							      <c:if test="${not empty loginMember}">
									  <svg class="heart-icon ${fn:contains(jjimList, j.recruitmentNo) ? 'filled' : ''}"
									       data-postno="${j.recruitmentNo}" 
									       width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="#ccc" stroke-width="2">
									    <path d="M20.84 4.61a5.5 5.5 0 0 0-7.78 0L12 5.67l-1.06-1.06a5.5 5.5 0 0 0-7.78 7.78L12 21.23l7.78-7.78a5.5 5.5 0 0 0 0-7.78z"/>
									  </svg>
									</c:if>
			      			</c:if>
					    </div>
			  </c:forEach>
		</div>

      </div>
    </div>
  
 <script>
 
 
 
    // =========================================================모든 하트 아이콘에 이벤트 부여
    // 하트 아이콘 이벤트 바인딩
  document.addEventListener("DOMContentLoaded", function () {
    const heartIcons = document.querySelectorAll('.heart-icon');

    heartIcons.forEach(icon => {
      icon.addEventListener('click', function (e) {
        e.stopPropagation(); // 카드 클릭 막기
        const postNo = this.getAttribute('data-postno');
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
  
    //====================================================================================디테일뷰 가는 onclick
    function goToDetail(postNo) {
    	 window.location.href = 'detail.pl?no=' + postNo;
    }
    
    
    //=============================================================JS: 검색 후 필터링 기능
    	 document.addEventListener("DOMContentLoaded", function () {
		    const searchInput = document.querySelector(".search-input");
		
		    searchInput.addEventListener("keydown", function (e) {
		      if (e.key === "Enter") {
		        const keyword = this.value.toLowerCase();
		
		        const cards = document.querySelectorAll(".card");
		        cards.forEach(card => {
		          const company = card.dataset.company.toLowerCase();
		          const hireType = card.dataset.hiretype.toLowerCase();
		
		          const isMatch = company.includes(keyword) || hireType.includes(keyword);
		
		          card.style.display = isMatch ? "block" : "none";
		        });
		      }
		    });
		  });
    
    //===============================================================js: 클릭형 필터링 기능
    document.addEventListener("DOMContentLoaded", function () {
		  const categories = document.querySelectorAll('.category');
		  const cards = document.querySelectorAll('.card');
		
		  categories.forEach(category => {
		    category.addEventListener('click', function () {
		      const wasActive = this.classList.contains('active');
		
		      // 모든 category 버튼 비활성화
		      categories.forEach(c => c.classList.remove('active'));
		
		      if (!wasActive) {
		        // 이전에 선택 안 되어 있으면 선택
		        this.classList.add('active');
		      }
		
		      const activeCategory = document.querySelector('.category.active');
		      if (activeCategory) {
		        const selectedCategory = activeCategory.textContent.trim();
		        cards.forEach(card => {
		          const hireType = card.dataset.hiretype;
		          card.style.display = (hireType === selectedCategory) ? "block" : "none";
		        });
		      } else {
		        // 선택된 카테고리가 없으면 전체 표시
		        cards.forEach(card => {
		          card.style.display = "block";
		        });
		      }
		    });
		  });
		});

	//===========================================================js: 연봉별 필터링 기능 구현
	document.addEventListener("DOMContentLoaded", function () {
		  const checkboxes = document.querySelectorAll('.checkbox-filter input[type="checkbox"]');
		  const cards = document.querySelectorAll('.card');
		
		  checkboxes.forEach(checkbox => {
		    checkbox.addEventListener('change', function () {
		      const selectedRanges = Array.from(checkboxes)
		        .filter(cb => cb.checked)
		        .map(cb => cb.value);
		
		      cards.forEach(card => {
		        const salary = parseInt(card.dataset.salary); // 카드의 연봉 정보
		
		        const isMatch = selectedRanges.some(range => {
		          const [min, max] = range.split('-').map(Number);
		          return salary >= min && salary <= max;
		        });
		
		        // 체크가 없으면 전체 보이고, 있으면 필터된 것만 보임
		        card.style.display = (selectedRanges.length === 0 || isMatch) ? "block" : "none";
		      });
		    });
		  });
		});


</script>
</body>
</html>