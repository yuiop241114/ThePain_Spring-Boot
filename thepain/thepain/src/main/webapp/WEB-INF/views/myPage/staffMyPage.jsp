<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>마이페이지</title>
<c:set var="path" value="${ pageContext.request.contextPath }" />
<link rel="stylesheet" href="${ path }/css/staffMypage.css">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">
</head>
<body>

	<!-- 사이드바 -->
	<jsp:include page="../member/menubar.jsp" />

	<!-- 메인 콘텐츠 -->
	<div class="main-content">
		<div class="profile-card">
			<div class="profile-header">
				<div class="profile-avatar" id="openProfileModal">
					<c:choose>
						<c:when test="${not empty profileFileName}">
							<img src="${profileFileName}" alt="프로필 이미지" class="profile-img">
						</c:when>
						<c:otherwise>
							<img
								src="https://www.gravatar.com/avatar/00000000000000000000000000000000?d=mp&f=y"
								alt="기본 이미지" class="profile-img">
						</c:otherwise>
					</c:choose>
					<span class="material-icons profile-icon">add</span>
				</div>

				<!-- ✅ 프로필 수정 모달 -->
				<div id="profileModal" class="profile-modal">
					<div class="profile-modal-content">
						<span class="close-profile">&times;</span>
						<h2 style="margin-bottom: 30px">프로필 사진 변경</h2>
						<form id="profileForm" action="insert.img" method="POST"
							enctype="multipart/form-data" class="profile-form">
							<div class="profile-preview">
								<img id="previewImage"
									src="${empty profileFileName ? 'https://www.gravatar.com/avatar/00000000000000000000000000000000?d=mp&f=y' : profileFileName}"
									alt="현재 프로필 이미지">
							</div>
							<div class="profile-button-container">
								<input type="file" id="profileUpload" name="upfile"
									style="display: none;"> <input type="hidden"
									id="userNo" name="memberNo" value="${ loginMember.memberNo }"
									style="display: none;"> <label for="profileUpload"
									class="custom-file-upload">파일 선택</label>
								<button type="submit" id="saveProfile">프로필 저장</button>
							</div>
						</form>
					</div>
				</div>

				<div class="profile-details">
					<div class="profile-field">
						<label>이름</label>
						<div class="value">${ loginMember.memberName }</div>
					</div>
					<div class="profile-field">
						<label>회사명</label>
						<div class="value">${ companyName }</div>
					</div>
					<div class="profile-field">
						<label>E-mail</label>
						<div class="value">${ loginMember.email }</div>
					</div>
				</div>

			</div>
		</div>

		<div class="documents-section">
			<div class="document-card">
				<h3>공고현황</h3>
				<div class="post-wrapper">
					<c:choose>
						<c:when test="${not empty postWriteList}">
							<c:forEach var="post" items="${postWriteList}">
								<div class="upload-box" onclick="applierList(${post.recruitmentNo})">
									<div class="job-image">
										<img src="${path}//img/company1.png"
											alt="${post.companyName}" class="cyber-img" />
									</div>
									<div class="job-info">
										<div class="job-title">[${post.hireType}]
											${fn:substring(post.description, 0, 15)}...</div>
										<div class="job-subtitle">연봉: ${post.salaryMin}만 ~
											${post.salaryMax}만</div>
										<div class="job-company">${post.companyName}</div>
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
	</div>

	<script>
	//지원자 리스트로 이동
	function applierList(postNo) {
	    location.href = 'applierList.my?postNo=' + postNo;
	}

    // 모달 열기: .profile-avatar 클릭 시
    document.querySelector('.profile-avatar').addEventListener('click', () => {
        document.getElementById('profileModal').style.display = 'block';
    });

    // 모달 닫기: X 버튼 클릭 시
    document.querySelector('.close-profile').addEventListener('click', () => {
        document.getElementById('profileModal').style.display = 'none';
    });

    // 모달 외부 클릭 시 닫기
    window.addEventListener('click', (e) => {
        const modal = document.getElementById('profileModal');
        if (e.target === modal) {
            modal.style.display = 'none';
        }
    });

    // 프로필 이미지 업로드 미리보기
    document.getElementById('profileUpload').addEventListener('change', function (event) {
        const file = event.target.files[0];
        const previewImage = document.getElementById('previewImage');  // 미리보기 이미지

        if (file && previewImage) {
            const reader = new FileReader();
            reader.onload = function (e) {
                previewImage.src = e.target.result; // 미리보기 이미지 변경
            };
            reader.readAsDataURL(file);
        }
    });

    // 폼 제출 시 더블클릭 방지
    let isSubmitting = false;
    document.getElementById('profileForm').addEventListener('submit', function(event) {
        if (isSubmitting) {
            event.preventDefault(); // 이미 제출 중이면 중지
            return;
        }
        
        isSubmitting = true; // 제출 중 표시
        const submitButton = document.getElementById('saveProfile');
        submitButton.disabled = true; // 제출 버튼 비활성화
        
        // 폼 제출이 끝난 후에는 버튼을 활성화하고 isSubmitting을 false로 설정할 수 있도록
        setTimeout(() => {
            submitButton.disabled = false;
            isSubmitting = false;
        }, 2000); // 잠시 후에 다시 활성화, 예를 들어 2초 후
    });
</script>
</body>
</html>