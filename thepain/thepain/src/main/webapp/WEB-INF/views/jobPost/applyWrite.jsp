<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${post.companyName}|채용상세</title>
<c:set var="path" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" href="${path}/css/jobPostDetail.css" />
</head>
<body>

	<jsp:include page="../member/menubar.jsp" />

	<div class="container">
		<div class="main-content">

			<div class="hero-image">
				<img
					src="${pageContext.request.contextPath}//img/company1.png"
					alt="회사 이미지" class="card-image">
			</div>

			<div class="content-header">
				<h1 class="content-title">${post.companyName}</h1>
				<p class="content-description">${post.description}</p>

				<div class="info-box">
					<div class="info-item">
						<div class="info-label">고용 형태</div>
						<div class="info-value">${post.hireType}</div>
					</div>
					<div class="info-item">
						<div class="info-label">급여</div>
						<div class="info-value">${post.salaryMin}만~
							${post.salaryMax}만</div>
					</div>
					<div class="info-item">
						<div class="info-label">상태</div>
						<div class="info-value">${post.status eq 'Y' ? '진행 중' : '마감'}</div>
					</div>
				</div>
			</div>
			<form id="applyForm" action="insert.at" method="post" enctype="multipart/form-data">	
			    <div class="job-description">
			        <h2 class="section-title">지원 정보</h2>
			        <p class="info-paragraph">${post.duty}</p>
			
			        <h2 class="section-title">자격 요건</h2>
			        <p class="info-paragraph">${post.description}</p>
			
			        <h2 class="section-title">우대 사항</h2>
			        <p class="info-paragraph">${post.benefits}</p>
			
			        <input type="hidden" name="postNo" value="${post.postNo}">
			        <input type="hidden" name="memberNo" value="${loginMember.memberNo}">
			        
			        <!-- 숨겨진 readme 값 -->
			        <input type="hidden" id="readmeContent" name="readmeContent" />
			
			        <div class="center-wrapper">
			            <div class="form-group">
			                <label for="memberName">이름</label>
			                <input id="memberName" value="${loginMember.memberName}" readonly class="styled-input">
			            </div>
			
			            <div class="form-group">
			                <label for="email">이메일</label>
			                <input type="text" id="email" name="email" value="${loginMember.email}" readonly class="styled-input">
			            </div>
			
			            <div class="form-group">
			                <label for="phone">연락처</label>
			                <input type="text" id="phone" name="phone" value="${loginMember.phone}" readonly class="styled-input">
			            </div>
			
						<!-- GitHub 레포 선택 영역 -->
						<div class="form-group" id="repoSelectGroup">
						    <label for="readmeSelect">GitHub 레포지토리 선택</label>
						    <select id="readmeSelect" name="readmeSelect" class="styled-input" onchange="handleSelectChange()">
						        <option value="">선택안함</option>
						        <option value="${ gitUrl }">전체 공개</option>
						        <c:forEach var="i" begin="0" end="${repoCount - 1}">
						            <option value="${repoLink[i]}">${repoTitle[i]}</option>
						        </c:forEach>
						    </select>
						</div>
						
						<!-- 파일 업로드 영역 (이것만 숨김/보임) -->
						<div class="form-group" id="fileUploadGroup">
						    <label for="resumeSelect">이력서 선택</label>
						    <select id="resumeSelect" name="resumeSelect" class="styled-input">
						        <option value="">이력서를 선택하세요</option>
						        <c:forEach var="file" items="${list}">
						            <option value="${file.fileNo}">${file.fileOriginName}</option>
						        </c:forEach>
						    </select>
						</div>
						
						<!-- 제출 버튼은 항상 보이게 -->
						
						    <!-- 버튼의 type을 button으로! -->
						<input type="hidden" name="alarmNo" value="1" id="alarmNo">
						<button type="button" class="btn btn-primary" onclick="handleApply()">지원하기</button>

			        </div>
			    </div>
			</form>

<script>

//===================================버튼 한개로 2개 메서드 출발. insert.at 이 먼저, sendAlarm dl 나중에. 


function handleApply() {
	    const form = document.getElementById("applyForm");
	    const formData = new FormData(form);
	    const postNo = formData.get("postNo");
	    const alarmNo = 1;
	
	    $.ajax({
	        url: "sendAlarm",
	        type: "POST",
	        data: { postNo, alarmNo },
	        success: function(response) {
	            console.log("🔔 알림 전송 성공:", response);
	            form.submit(); // 알림 성공 후 form 전송
	        },
	        error: function(xhr, status, error) {
	            console.error("❌ 알림 전송 실패:", error);
	            alert("알림 전송 중 문제가 발생했습니다.");
	        }
	    });
	}





    function validateForm() {
        const readmeSelect = document.getElementById("readmeSelect");
        const applyFile = document.getElementById("applyFile");

        const selectedReadme = readmeSelect.value.trim();
        const fileSelected = applyFile.files.length > 0;

        if (selectedReadme === "" && !fileSelected) {
            alert("README 파일 또는 이력서를 반드시 하나 이상 선택해주세요.");
            return false; // 폼 제출 막기
        }

        return true; // 통과 시 제출
    }

    function toggleFileInput() {
        const select = document.getElementById('readmeSelect');
        const fileGroup = document.getElementById('fileInputGroup');

        if (select.value !== '') {
            fileGroup.style.display = 'none';
        } else {
            fileGroup.style.display = 'block';
        }
    }
    
    function handleSelectChange() {
        const select = document.getElementById("readmeSelect");
        const selectedValue = select.value;
        const fileGroup = document.getElementById("fileUploadGroup");

        // GitHub 레포 선택 시 이력서 선택 그룹 숨기기
        if (selectedValue !== "") {
            fileGroup.style.display = "none";
        } else {
            fileGroup.style.display = "block";
        }
    }

    function handleFileChange() {
        const fileInput = document.getElementById("applyFile");
        const repoGroup = document.getElementById("repoSelectGroup");

        if (fileInput.files.length > 0) {
            repoGroup.style.display = "none"; // 파일이 선택되면 레포 숨기기
        } else {
            repoGroup.style.display = "block"; // 파일이 선택되지 않으면 레포 보이기
        }
    }

    // 이력서 선택 시 동작
    function handleResumeSelectChange() {
        const resumeSelect = document.getElementById("resumeSelect");
        const fileUploadGroup = document.getElementById("fileUploadGroup");
        const repoSelectGroup = document.getElementById("repoSelectGroup");

        // 이력서가 선택되면 레포지토리 선택 그룹 숨기기
        if (resumeSelect.value !== "") {
            fileUploadGroup.style.display = "block"; // 파일 업로드 그룹 숨기기
            repoSelectGroup.style.display = "none"; // 레포지토리 선택 그룹 숨기기
        } else {
            fileUploadGroup.style.display = "block"; // 파일 업로드 그룹 보이기
            repoSelectGroup.style.display = "block"; // 레포지토리 선택 그룹 보이기
        }
    }

    window.onload = function () {
        // 드롭다운 선택 변경시마다 이벤트 처리
        const resumeSelect = document.getElementById("resumeSelect");
        const readmeSelect = document.getElementById("readmeSelect");

        if (resumeSelect) {
            resumeSelect.addEventListener("change", handleResumeSelectChange);
        }

        if (readmeSelect) {
            readmeSelect.addEventListener("change", handleSelectChange);
        }
    };
</script>

</body>
</html>
