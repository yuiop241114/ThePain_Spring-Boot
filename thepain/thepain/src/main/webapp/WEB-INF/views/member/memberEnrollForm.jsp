<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>DRSP RECRUITMENT</title>
<c:set var="path" value="${ pageContext.request.contextPath }" />
<link rel="stylesheet"
	href="${ path }/resources/css/memberEnrollForm.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
	<div class="container">
		<div class="logo">
			<div class="logo-inner">
				<span class="braces">{</span> <span
					style="color: #65c466; font-size: 30px;">✻</span> <span
					class="braces">}</span>
			</div>
		</div>

		<div class="brand-name">
			<p class="drsp">DRSP</p>
			<p class="recruitment">RECRUITMENT</p>
		</div>

		<form action="enroll.me" method="post">
			<div class="form-group email-section">
				<input type="hidden" id="token" name="token"
					value="${ gitLoginMember.token }"> <input type="email"
					id="email" name="email" placeholder="Email" oninput="check()">
				<button type="button" class="emailCheckBtn" id="emailCheckBtn1"
					required="required" disabled>인증</button>
			</div>
			<div class="form-group" id="codeResult2"></div>
			<div class="form-group email-section" id="emailCode">
				<input type="text" id="code" placeholder="인증코드를 입력해주세요">
				<button type="button" class="emailCheckBtn" id="emailCheckBtn2"
					required="required">인증</button>
			</div>
			<div class="form-group" id="codeResult"></div>
			<div class="form-group">
				<input type="password" id="password" name="memberPwd"
					placeholder="Password" required="required">
			</div>
			<div class="form-group">
				<input type="text" id="name" name="memberName" placeholder="Name"
					required="required">
			</div>
			<c:if test="${empty gitLoginMember}">
				<div class="form-group">
					<input type="text" id="corporation" name="corporation"
						placeholder="Corporation">
				</div>
			</c:if>
			<div class="form-group">
				<input type="tel" id="phone" name="phone" placeholder="phone"
					required="required">
			</div>

			<button type="submit" class="sign-in-btn" disabled>Sign in</button>
		</form>

	</div>

	<script>
		//이메일 인정번호 전송 ajax    	
		if ($("#email").val() != null) {
			$("#emailCheckBtn1").click(function() {
				$("#emailCode").css("display", "flex");
				$.ajax({
					url : "emailCheck.me",
					data : {
						email : $("#email").val()
					},
					success : function(msg) {
						console.log(msg)
					},
					error : function() {
						console.log("이메일 전송 ajax 실패")
					}
				})
			})
		}

		//인증코드 인증
		if ($("#code").val() != null) {
			$("#emailCheckBtn2").click(function() {
				$.ajax({
					url : "emailCodeCheck.me",
					data : {
						email : $("#email").val(),
						code : $("#code").val()
					},
					success : function(result) {
						if (result === "Y") {
							$("#codeResult").text("인증 성공!");
							$(".sign-in-btn").removeAttr("disabled");
						} else {
							$("#codeResult").text("인증 실패! 인증 코드를 다시 확인해주세요.");
						}

					},
					error : function() {
						console.log("인증코드 ajax 실패");
					}
				})
			})
		}

		function check() {
		    $.ajax({
		        url : 'check.me',
		        data : {
		            email : $('#email').val(),
		        },
		        success : function(result) {
		            if (result == 0) {
		                $("#codeResult2").text("사용가능한 아이디 입니다.");
		                $(".emailCheckBtn").removeAttr("disabled");
		            } else {
		                $("#codeResult2").text("중복된 아이디 입니다. 다시 입력해 주세요");
		            }
		        },
		        error : function() {
		            console.log("중복체크 ajax 실패");
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