<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<c:set var="path" value="${ pageContext.request.contextPath }" />
<link rel="stylesheet" href="${ path }/resources/css/sidebar.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

</head>
<body>
<div id="wrap">
<!-- 사이드바 -->
	<div class="sidebar">
		<div class="logo">
			<a href="goMainPage.me"><img src="resources/img/logo.png"
				alt="로고"></a>
		</div>

		<div class="profile-brief">
			<c:choose>
				<c:when test="${empty loginMember}">
					<img
						src="https://www.gravatar.com/avatar/00000000000000000000000000000000?d=mp&f=y"
						alt="기본 이미지" style="border-radius: 50%;">
					<div class="profile-info">
						<div class="name">
							<a href="loginForm.me" class="login-box">로그인하세요</a>
						</div>
						<div class="email">회원 전용 기능입니다</div>
					</div>
				</c:when>

				<c:otherwise>
					<a >
					 <c:choose>
							<c:when test="${empty loginMember.token}">
								<c:choose>
									<c:when test="${not empty profileFileName}">
										<img src="${profileFileName}" alt="프로필 이미지"
											style="border-radius: 50%;">
									</c:when>
									<c:otherwise>
										<img
											src="https://www.gravatar.com/avatar/00000000000000000000000000000000?d=mp&f=y"
											alt="기본 이미지" style="border-radius: 50%;">
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${not empty gitLoginMember.profile}">
										<img src="${gitLoginMember.profile}" alt="깃허브 프로필"
											style="border-radius: 50%;">
									</c:when>
									<c:otherwise>
										<img
											src="https://www.gravatar.com/avatar/00000000000000000000000000000000?d=mp&f=y"
											alt="기본 이미지" style="border-radius: 50%;">
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
					</a>
					<div class="profile-info">
						<div class="name">${loginMember.memberName}</div>
						<div class="email">${loginMember.email}</div>
					</div>
					<div id="bell">
						<img src="resources/img/bell.png" alt="알림">
					</div>
				</c:otherwise>
			</c:choose>
		</div>



		<c:choose>
			<c:when test="${not empty loginMember}">
				<!-- 로그인한 경우에만 메뉴를 보이도록 -->
				<div class="menu">
					<a href="jobPostList.pl" class="menu-item"> <i>🔍</i> 채용 공고
					</a>
					<c:choose>
						<c:when test="${empty loginMember.token}">
							<a href="staffMyPage.me" class="menu-item"> <i>📅</i> 마이페이지
							</a>
						</c:when>
						<c:otherwise>
							<a href="myPage.me" class="menu-item"> <i>📅</i> 마이페이지
							</a>
						</c:otherwise>
					</c:choose>
					<a href="cvList.my" class="menu-item"> <i>📂</i> 이력서
					</a> <a href="applyState.pl?memberNo=${loginMember.memberNo}"
						class="menu-item"> <i>📄</i> 지원현황
					</a>
				</div>
			</c:when>
			<c:otherwise>
				<!-- 로그인하지 않은 경우에는 메뉴를 아예 보이지 않도록 -->
				<!-- 메뉴가 출력되지 않습니다. -->
			</c:otherwise>
		</c:choose>




	</div>
	<!-- 알림 모달 -->
	<div id="notification-modal" class="notification-modal">
		<div class="notification-header">
			<span class="time">8:47</span> <span class="title">알림창</span>
		</div>
		<ul class="notification-list">

		</ul>


	</div>

	<!-- 알림창 -->
	<c:if test="${ not empty alertMsg }">
		<script>
      alert("${ alertMsg }")
   </script>
		<c:remove var="alertMsg" scope="session" />
	</c:if>
</div>
	
	<script>
//-------------------------------------------알림 내역 조회 
$(document).ready(function() {
    const contextPath = "${path}";
    $.ajax({
        url: contextPath + "/alarmList",
        type: "GET",
        dataType: "json",
        success: function(response) {
        	 if (response.length === 0) {
        	        return;
        	    }
        	
        	
            const ul = $(".notification-list");
            let alarmList = response;
            alarmList.forEach(function(alarmHistory) {
                if (alarmHistory) {
                    const li = $("<li>");
                    const createdAt = new Date(Number(alarmHistory.createDate)); // 여기 중요!
                    const timeAgo = getTimeAgoText(createdAt);

                    const content = alarmHistory.alarmHistoryContent || "알림 없음";
                    li.text(`\${content} (\${timeAgo})`)
                    	  .css({
							    "color": "#888888",  // 연한 회색
							    "font-size": "14px"  // (선택) 글자 크기도 살짝 줄일 수 있어
							  });
                    ul.append(li);
                }
            });
        },
        error: function(xhr, status, error) {
            console.error(":rotating_light: 알림 리스트 가져오기 실패:", error);
        }
    });
    
    // 알림 요청
    $.ajax({
	        url: contextPath + "/alarmDeadLine.pl",
	        method: "POST",
	        data: {
	            alarmNo: 2  // 마감임박 알림 템플릿 번호
	        },
	        dataType: "json",
	        success: function (response) {
	        	
	        	 if (response.length === 0) {
	        	        return;
	        	    }
	        	
	        	
	        	
	            const now = new Date();
	            const ul = $(".notification-list");
	
	            response.forEach(function (content) {
	                const li = $("<li>");
	                const timeAgo = getTimeAgoText(now);
	                li.text(`\${content} (\${timeAgo})`)
              	  		.css({
	                    "color": "#888888",
	                    "font-size": "14px"
	                });
	                ul.append(li);
	            });
	        },
	        error: function (xhr, status, error) {
	            console.error("🚨 마감 알림 전송 실패:", error);
	        }
	    });
    
    
    $.ajax({
        url: contextPath + "/postClosed.pl",
        method: "POST",
        data: {
            alarmNo: 3  // 마감임박 알림 템플릿 번호
        },
        dataType: "json",
        success: function (response) {
            const now = new Date();
            const ul = $(".notification-list");

            response.forEach(function (content) {
                const li = $("<li>");
                const timeAgo = getTimeAgoText(now);
                li.text(`\${content} (\${timeAgo})`)
          	  		.css({
                    "color": "#888888",
                    "font-size": "14px"
                });
                ul.append(li);
            });
        },
        error: function (xhr, status, error) {
            console.error("🚨 마감 알림 전송 실패:", error);
        }
    });
    
    
});
//-----------------------------------------------------알림 시간 계산처리 함수.
function getTimeAgoText(createdAt) {
    if (!(createdAt instanceof Date) || isNaN(createdAt.getTime())) {
        return "방금 전";
    }

    const now = new Date();
    const diffMs = now - createdAt;
    const diffMinutes = Math.floor(diffMs / (1000 * 60));

    if (diffMinutes <= 2) {
        return "방금 전";
    } else if (diffMinutes < 60) {
        return `\${diffMinutes}분 전`;
    } else if (diffMinutes < 24 * 60) {
        const diffHours = Math.floor(diffMinutes / 60);
        return `\${diffHours}시간 전`;
    } else {
        const diffDays = Math.floor(diffMinutes / (60 * 24));
        return `\${diffDays}일 전`;
    }
}
//------------------------------------------------------------------클릭했을때 모달창 열리고 닫힘. ()토글)
		document.addEventListener("DOMContentLoaded", () => {
		    const currentPath = window.location.pathname;
		    const contextPath = "${path}";
		    const ul = document.querySelector(".notification-list");
		    const modal = document.getElementById("notification-modal");
		
		 // 메뉴 활성화
            document.querySelectorAll(".menu-item").forEach(item => {
                const href = item.getAttribute("href");
                const hrefPath = href ? href.split("?")[0] : ""; // 쿼리스트링 제거
                const currentPath = window.location.pathname;

                if (hrefPath && currentPath.includes(hrefPath)) {
                    item.classList.add("active");
                }
            });
		    // 메뉴 활성화
			document.querySelectorAll(".menu-item").forEach(item => {
			    const href = item.getAttribute("href");
			    const hrefPath = href ? href.split("?")[0] : ""; // 쿼리스트링 제거
			    const currentPath = window.location.pathname;
			
			    if (hrefPath && currentPath.includes(hrefPath)) {
			        item.classList.add("active");
			    }
			});
		    // 종 아이콘 클릭 → 모달 토글
		    document.getElementById("bell")?.addEventListener("click", () => {
		        modal.classList.toggle("active");
		    });
		    // WebSocket 연결
		    const ws = new WebSocket("ws://localhost:8444/thepain/ws/notify");
		    ws.onerror = err => console.error("🚨 WebSocket 오류:", err);
		    // 알림 추가 함수-------------------------------------------------------클릭할떄마다 모달창에 쌓인다. 
		    const addNotification = text => {
		        const li = document.createElement("li");
		        li.innerText = text;
		        ul.prepend(li);
		        if (!modal.classList.contains("active")) {
		            modal.classList.add("active");
		        }
		    };
		
		    // 여러 알림 버튼 (폼마다)
		    $(".alarm-form").on("submit", function(e) {
		        e.preventDefault(); // 기본 submit 막기
		
		        const alarmNo = $(this).find("input[name='alarmNo']").val();
		        const receiverMemberNo = $(this).find("input[name='receiverMemberNo']").val();
		
		        $.ajax({
		            url: contextPath + "/sendAlarmByNo",
		            type: "POST",
		            data: {
		                alarmNo: alarmNo,
		                receiverMemberNo: receiverMemberNo
		            },
		            success: function(data) {
		                addNotification(data); // 모달에 추가
		            },
		            error: function(xhr, status, error) {
		                console.error("🚨 알림 저장  실패:", error);
		            }
		        });
		    });
		});
		
		
		

		
</script>


</body>
</html>