<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>DRSP RECRUITMENT</title>
<c:set var="path" value="${ pageContext.request.contextPath }" />
<link rel="stylesheet"
	href="${ path }/css/memberLoginForm.css">
</head>

<body>
	<div class="container">
		<a href="goMainPage.me" id="logohome">
			<div class="logo">
				<div class="logo-content">
					<img src="/img/loginFormLogo.png" alt="DrsP 로고"
						class="drsp-logo" />
				</div>
			</div>
			<div class="brand-name">
				<p class="drsp">DrsP</p>
				<p class="recruitment">RECRUITMENT</p>
			</div>
		</a>


		<div class="login-form">
			<form action="login.me" method="post" id="loginForm">
				<div class="form-group">
					<label for="email">아이디</label> <input class="form-control"
						id="email" type="text" name="email" placeholder="이메일을 입력해주세요"
						required>
				</div>
				<div class="form-group">
					<label for="memberPwd">비밀번호</label> <input class="form-control"
						id="memberPwd" type="password" name="memberPwd"
						placeholder="비밀번호를 입력해주세요" required>
				</div>
				<button type="submit" class="login-btn" id="loginButton">로그인</button>

				<div class="form-footer">
					<a href="#"></a> | <a href="enrollForm.me">회원가입</a> |
				</div>
			</form>
		</div>

		<div class="divider">또는</div>

		<a
			href="https://github.com/login/oauth/authorize?scope=user%20repo%20delete_repo%20project&client_id=Ov23liv8JaJ8nBI9KDhb"
			class="github-btn"> <svg class="github-icon"
				xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                <path
					d="M12 0c-6.626 0-12 5.373-12 12 0 5.302 3.438 9.8 8.207 11.387.599.111.793-.261.793-.577v-2.234c-3.338.726-4.033-1.416-4.033-1.416-.546-1.387-1.333-1.756-1.333-1.756-1.089-.745.083-.729.083-.729 1.205.084 1.839 1.237 1.839 1.237 1.07 1.834 2.807 1.304 3.492.997.107-.775.418-1.305.762-1.604-2.665-.305-5.467-1.334-5.467-5.931 0-1.311.469-2.381 1.236-3.221-.124-.303-.535-1.524.117-3.176 0 0 1.008-.322 3.301 1.23.957-.266 1.983-.399 3.003-.404 1.02.005 2.047.138 3.006.404 2.291-1.552 3.297-1.23 3.297-1.23.653 1.653.242 2.874.118 3.176.77.84 1.235 1.911 1.235 3.221 0 4.609-2.807 5.624-5.479 5.921.43.372.823 1.102.823 2.222v3.293c0 .319.192.694.801.576 4.765-1.589 8.199-6.086 8.199-11.386 0-6.627-5.373-12-12-12z" />
            </svg> Continue with GitHub
		</a>
	</div>

	<script>
        // 폼 제출 이벤트 처리
        document.getElementById('loginForm').addEventListener('submit', function(e) {
            e.preventDefault();
            
            // 로그인 버튼에 로딩 효과 추가
            const loginButton = document.getElementById('loginButton');
            const originalText = loginButton.innerHTML;
            loginButton.innerHTML = '<span style="display: inline-block; width: 20px; height: 20px; border: 3px solid rgba(255,255,255,0.3); border-radius: 50%; border-top-color: white; animation: spin 1s linear infinite;"></span> 처리 중...';
            
            // 실제 로그인 로직이 여기에 들어갈 것입니다
            // 데모 목적으로 1초 후 폼 제출
            setTimeout(function() {
                document.getElementById('loginForm').submit();
            }, 1000);
        });

        // 입력 필드 포커스 애니메이션
        const inputs = document.querySelectorAll('.form-control');
        
        inputs.forEach(input => {
            input.addEventListener('focus', function() {
                this.parentElement.classList.add('focused');
            });
            
            input.addEventListener('blur', function() {
                if (this.value === '') {
                    this.parentElement.classList.remove('focused');
                }
            });
        });

        // 페이지 로드 시 요소 순차적 애니메이션
        document.addEventListener('DOMContentLoaded', function() {
            const elements = [
                document.querySelector('.logo'),
                document.querySelector('.brand-name'),
                document.querySelector('.login-form'),
                document.querySelector('.divider'),
                document.querySelector('.github-btn')
            ];
            
            elements.forEach((element, index) => {
                setTimeout(() => {
                    element.style.opacity = '0';
                    element.style.transform = 'translateY(20px)';
                    element.style.transition = 'opacity 0.5s ease, transform 0.5s ease';
                    
                    setTimeout(() => {
                        element.style.opacity = '1';
                        element.style.transform = 'translateY(0)';
                    }, 100);
                }, index * 150);
            });
        });
		window.addEventListener("pageshow", function (event) {
		    if (event.persisted || window.performance.getEntriesByType("navigation")[0].type === "back_forward") {
		        // 뒤로가기 감지됨
		        fetch('deleteSession', {
		            method: 'POST'
		        });
		    }
		});
    </script>

	<!-- 단순 alert 메시지 -->
	<c:if test="${not empty loginAlertMsg}">
		<script>
	    alert("${loginAlertMsg}");
	  </script>
		<c:remove var="loginAlertMsg" scope="session" />
	</c:if>

	<c:if test="${not empty loginConfirmMsg}">
		<script>
	    if (confirm("${loginConfirmMsg}")) {
	      location.href = "<c:url value='/enrollForm.me' />";
	    }
	  </script>
		<c:remove var="loginConfirmMsg" scope="session" />
	</c:if>
</body>
</html>