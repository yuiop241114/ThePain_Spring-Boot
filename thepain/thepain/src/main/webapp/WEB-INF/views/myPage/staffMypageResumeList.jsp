<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<c:set var="path" value="${ pageContext.request.contextPath }"/>
<link rel="stylesheet" href="${ path }/css/staffMypageResumeList.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">

<!-- Popper JS -->
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
	<jsp:include page="../member/menubar.jsp"/>
	
	<div class="appliaer-list"> 
		<div class="list-div">
			<h3>${postWrite.title}</h3>
			<hr>
			<div class="content-wrapper">
				<div class="post-div">
					<div class="upload-box">
				      <div class="job-image">
				        <img src="${path}//img/company1.png" alt="${postWrite.companyName}" class="cyber-img" />
				      </div>
				      <div class="job-info">
				        <div class="job-title">[${postWrite.hireType}] ${fn:substring(postWrite.description, 0, 30)}...</div>
				        <div class="job-subtitle">연봉: ${postWrite.salaryMin}만 ~ ${postWrite.salaryMax}만</div>
				        <div class="job-company">${postWrite.companyName}</div>
				      </div>
				    </div>
				</div>
				<div class="apply-div">
					<div class="apply-info title">
					지원자&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;제출 서류
					</div>
					<c:if test="${not empty memberList}">
						<c:forEach var="member" items="${memberList}" varStatus="status">
							<div class="apply-info content">
								<input type="hidden" value="${member.memberNo}">	
								<span>${member.memberName}</span>
								<p>${fileNameList[status.index]}</p>
					
								<div class="result-btn">
									<c:if test="${applyList[status.index].result eq 'M'}">
										<button class="btn btn-success">합격</button>
										<button class="btn btn-danger">불합격</button>
									</c:if>
									<c:if test="${applyList[status.index].result ne 'M'}">
										<button class="btn btn-success" disabled>합격</button>
										<button class="btn btn-danger" disabled>불합격</button>
									</c:if>
								</div>
							</div>	
						</c:forEach>
					</c:if>
				</div>
			</div>
		</div>
	</div>
	
	<script>
		//지원자 이름 , 제출 서류만 클릭했을 경우 이동하게 조건작성
		$(".apply-info span, .apply-info p").click(function(){
    const memberNo = $(this).siblings("input[type=hidden]").val();
    const recruitmentNo = ${ postWrite.recruitmentNo };

    // 1. 알림 먼저 전송
	    $.ajax({
	        url: "alarmApplyState.pl",
	        type: "GET",
	        data: {
	            memberNo: memberNo,
	            recruitmentNo: recruitmentNo
	        },
	        success: function(response) {
	            console.log("알림 전송 결과:", response);
	
	            // 2. 알림 전송 성공 후 상세페이지 이동
	            location.href = 'applierDeatil.my?mNo=' + memberNo + '&postNo=' + recruitmentNo;
	        },
	        error: function() {
	            alert("알림 전송 실패... 상세페이지로 이동합니다.");
	            location.href = 'applierDeatil.my?mNo=' + memberNo + '&postNo=' + recruitmentNo;
	        }
	    });
	});
		//합격 불합격 처리 스크립트
		let result1 = 0;
		let applyNum = 0;
		$(".btn").click(function(){
			applyNum = $(this).parent().siblings("input[type=hidden]").val();	
			if( $(this).attr("class") == "btn btn-success" ){
				result1 = 1;
			}else{
				result1 = 0;
			}
			
			let passedResult = "";
			if(result1 == 1){
				passedResult = confirm("합격처리 하시겠습니까?")
			}else{
				passedResult = confirm("불합격처리 하시겠습니까?")
			}
			
			
			if(passedResult == true){
				$(this).attr("disabled", "disabled");
				$(this).siblings(".btn").attr("disabled", "disabled");
				$.ajax({
					url:"applyPassed.my",
					data:{ 
						passed:result1
					  , postNo:${ postWrite.recruitmentNo }
					  , applyNum:applyNum
					},
					success:function(result){
						console.log("합격 불합격 처리 결과 result : " + result);
					},
					error:function(){
						console.log("합격 불합격 처리 ajax 실패")
					}
				})
			}
			
		}) 
		
	</script>
</body>
</html>