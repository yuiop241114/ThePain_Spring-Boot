<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style>

@keyframes turtleRave {
  0%   { transform: rotate(0deg) translateY(0) scale(1); }
  10%  { transform: rotate(20deg) translateY(-10px) scale(1.2); }
  20%  { transform: rotate(-20deg) translateY(10px) scale(1.2); }
  30%  { transform: rotate(25deg) translateY(-15px) scale(1.3); }
  40%  { transform: rotate(-25deg) translateY(15px) scale(1.3); }
  50%  { transform: rotate(0deg) translateY(0) scale(1); }
  60%  { transform: rotate(15deg) translateX(10px) scale(1.2); }
  70%  { transform: rotate(-15deg) translateX(-10px) scale(1.2); }
  80%  { transform: rotate(10deg) translateY(-5px) scale(1.1); }
  90%  { transform: rotate(-10deg) translateY(5px) scale(1.1); }
  100% { transform: rotate(0deg) translateY(0) scale(1); }
}

.turtle-logo:hover{
  animation: turtleRave 0.9s infinite ease-in-out;
  transform-origin: center center;
  display: inline-block;
  transition: all 0.2s ease;
}

</style>


</head>
<body>
	<div class="header">
		<div class="purple-banner"></div>

		<div class="logo-container">
			<div class="logo" onclick="location.href='goMainPage.me';" style="background-color: white;">
				<img src="img/loginFormLogo.png" alt=""
				 class="turtle-logo"	style="height: 100%; width:100%; border-radius: 50%; ">
			</div>
			<div class="logo-text">
				DrsP<br>RECRUITMENT
			</div>
		</div>
		<c:choose>
			<c:when test="${not empty loginMember}">
				<div class="user-profile">
					<div class="profile-pic">
						<c:choose>
							<c:when test="${empty loginMember.token}">
								<c:choose>
									<c:when test="${not empty profileFileName}">
										<img src="${profileFileName}" alt="일반 유저 프로필"
											style="height: 100%; width: 100%; border-radius: 50%;">
									</c:when>
									<c:otherwise>
										<img
											src="https://www.gravatar.com/avatar/00000000000000000000000000000000?d=mp&f=y"
											alt="기본 이미지"
											style="height: 100%; width: 100%; border-radius: 50%;">
									</c:otherwise>
								</c:choose>
							</c:when>

							<c:otherwise>
								<!-- 깃허브 유저 프로필 이미지 처리 -->
								<c:choose>
									<c:when test="${not empty gitLoginMember.profile}">
										<img src="${gitLoginMember.profile}" alt="깃허브 프로필"
											style="height: 100%; width: 100%; border-radius: 50%;">
									</c:when>
									<c:otherwise>
										<img
											src="https://www.gravatar.com/avatar/00000000000000000000000000000000?d=mp&f=y"
											alt="기본 이미지"
											style="height: 100%; width: 100%; border-radius: 50%;">
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
					</div>

					<div class="user-info">
						<div class="user-name">${loginMember.memberName}</div>
						<div class="user-email">${loginMember.email}</div>
					</div>

					<div class="login-box2">
						<c:choose>
							<c:when test="${empty loginMember.token and loginMember.enterpriseNo != 0}">
								<a href="staffMyPage.me">마이페이지</a>
							</c:when>
							<c:otherwise>
								<a href="myPage.me">마이페이지</a>
							</c:otherwise>
						</c:choose>
						&nbsp;|&nbsp; <a href="logout.me">로그아웃</a>
					</div>
				</div>
			</c:when>

			<c:otherwise>
				<a href="loginForm.me" class="login-box">로그인</a>
			</c:otherwise>
		</c:choose>
	</div>

	<c:if test="${ not empty alertMsg }">
		<script>
			alert("${ alertMsg }")
		</script>
		<c:remove var="alertMsg" scope="session" />
	</c:if>

</body>
</html>