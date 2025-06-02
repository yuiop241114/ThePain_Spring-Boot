<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	/* 경력 쪽 input checkbox 스타일 */
	form#filterForm {
	  display: flex;
	  gap: 15px;
	  margin: 20px 0;
	  align-items: center;
	  flex-wrap: wrap;
	}
	
	/* 공고 목록 중 리스트 스타일 */
	.card-container {
	  display: grid;
	  grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
	  gap: 20px;
	  padding: 20px;
	}
	
	.card {
	  background: white;
	  border-radius: 12px;
	  overflow: hidden;
	  box-shadow: 0 2px 6px rgba(0,0,0,0.1);
	  transition: transform 0.2s;
	  cursor: pointer;
	}
	
	.card:hover {
	  transform: translateY(-5px);
	}
	
	.card img {
	  width: 100%;
	  height: 150px;
	  object-fit: cover;
	}
	
	.card-body {
	  padding: 10px;
	}
	
	.card-body h3 {
	  font-size: 14px;
	  font-weight: bold;
	  margin: 0;
	}
	
	.card-body .career {
	  color: #4F46E5;
	  font-size: 13px;
	  margin-top: 5px;
	}
	
	.card-body .company {
	  font-size: 12px;
	  color: #555;
	}
	
	.card-body .heart {
	  background: none;
	  border: none;
	  font-size: 18px;
	  float: right;
	  cursor: pointer;
	}



</style>
</head>
<body>

	<jsp:include page="../common/header.jsp"/>
	
	<div class="recruitment">
	<h2>채용공고 게시판</h2>
	<!-- 탭 메뉴 -->
	<div class="tab-menu">
		<a href="?role=fullstack" class="tab-item <%= "fullstack".equals(request.getParameter("role")) ? "active" : "" %>">풀스택 개발자</a>
		<a href="?role=fullstack" class="tab-item <%= "backend".equals(request.getParameter("role")) ? "active" : "" %>">백엔드 개발자</a>
		<a href="?role=fullstack" class="tab-item <%= "frontend".equals(request.getParameter("role")) ? "active" : "" %>">프론트 개발자</a>
		<a href="?role=fullstack" class="tab-item <%= "server".equals(request.getParameter("role")) ? "active" : "" %>">서버 개발자</a>
		<a href="?role=fullstack" class="tab-item <%= "drinks".equals(request.getParameter("role")) ? "active" : "" %>">Drinks</a>
	</div>
		
	<form id="filterForm" action="list.jsp" method="get">
		<input type="hidden" name="role" value="<%= request.getParameter("role") %>"/>
	  
		<label><input type="radio" name="career" value="1-3"
			<%= "1-3".equals(request.getParameter("career")) ? "checked" : "" %>
			onchange="document.getElementById('filterForm').submit()"
		/> 1년 이상 3년 이하</label>
	  
		<label><input type="radio" name="career" value="3-7"
			<%= "3-7".equals(request.getParameter("career")) ? "checked" : "" %>
			onchange="document.getElementById('filterForm').submit()"
		/> 3년 이상 7년 이하</label>
	  
		<label><input type="radio" name="career" value="7+"
			<%= "7+".equals(request.getParameter("career")) ? "checked" : "" %>
			onchange="document.getElementById('filterForm').submit()"
		/> 7년 이상</label>
	  </form>
		<br><br>

		
		<div id="card-container" class="card-container">
			
			  <div class="card">
				<img src="" alt="썸네일" />
				<div class="card-body">
				  <h3>[공통] Strategy - 성장전략</h3>
				  <p class="career">경력 3년 이상</p>
				  <p class="company">현대오토에버</p>
				  <button class="heart">🤍</button>
				</div>
			  </div>
		</div>
		
	</div>

	<!-- loadMore.jsp 에서 해당 기능 <% %> 로 정리하기 -->
	<script>
		let page = 1;
		let isLoading = false; // 중복 호출 방지
		
		window.addEventListener("scroll", () => {
		  const scrollTop = window.scrollY;
		  const clientHeight = document.documentElement.clientHeight;
		  const scrollHeight = document.documentElement.scrollHeight;
		
		  if (scrollTop + clientHeight >= scrollHeight - 100 && !isLoading) {
		    isLoading = true;
		    page++;
		
		    fetch(`loadMore.jsp?page=${page}`)
		      .then(response => response.text())
		      .then(html => {
		        const container = document.getElementById("card-container");
		        container.insertAdjacentHTML("beforeend", html);
		        isLoading = false;
		      })
		      .catch(error => {
		        console.error("로딩 실패:", error);
		        isLoading = false;
		      });
		  }
		});
	</script>

</body>
</html>