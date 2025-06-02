<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
  <!-- div에 넣고 while 문 써서 채용공고 리스트를 하나씩 뽑아올 수 있게 하는 jsp-->
  <div class="card">
    <img src="" alt="썸네일" />
    <c:if test="${ not empty b.originName }">
    	
    	<a href="${ p.changeName }" download="${ b.originName }"></a>
    </c:if>
    <div class="card-body">
      <h3>채용공고 글 제목</h3>
      <p class="career">경력기간</p>
      <p class="company">회사명</p>
      <button class="heart">🤍</button>
    </div>
  </div>

</body>
</html>