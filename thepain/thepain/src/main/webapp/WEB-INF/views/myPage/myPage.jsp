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
<link rel="stylesheet" href="${ path }/css/myPage.css">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/codemirror@5/lib/codemirror.css" />
</head>
<body>
	<!-- 메인 콘텐츠 -->
	<jsp:include page="../member/menubar.jsp" />
	<div class="main-content">
		<div class="profile-card">
			<div class="profile-header">
				<div class="profile-avatar">
					<c:choose>
						<c:when test="${not empty gitLoginMember.profile}">
							<img src="${gitLoginMember.profile}" alt="프로필 사진"
								style="height: 100%; width: 100%; border-radius: 50%;">
						</c:when>
						<c:otherwise>
							<img src="${profileFileName}" alt="프로필 사진"
								style="height: 100%; width: 100%; border-radius: 50%;">
						</c:otherwise>
					</c:choose>
				</div>
				<form action="update.me" method="post">
					<div class="profile-details">
						<div class="profile-field">
							<input type="hidden" name="memberNo"
								value="${ loginMember.memberNo }"> <label>이름</label> <input
								type="text" id="name" name="memberName" placeholder="Name"
								value="${ loginMember.memberName }" readonly>
						</div>
						<div class="profile-field">
							<label>전화번호</label> <input type="text" id="phone" name="phone"
								placeholder="phone" value="${ loginMember.phone }">
						</div>
						<div class="profile-field">
							<label>Skill</label>
							<div class="value" style="position: relative;">

								<div id="skillSelected" class="selected-skills"
									style="margin-bottom: 10px;">
									<c:forEach var="skill" items="${skills}">
										<div class="selected-skill-item">
											<input type="hidden" name="skillNos"
												value="${skill.SKILL_NO}"> ${skill.SKILL_NAME} <span
												class="material-icons remove-icon"
												style="vertical-align: middle; line-height: 1; cursor: pointer;">remove_circle_outline</span>
										</div>
									</c:forEach>
								</div>

								<!-- 입력창: 조건에 따라 렌더링 -->
								<c:choose>
									<c:when test="">
										<!-- 스킬 검색 입력창 -->
										<input type="text" id="skillSearchInput"
											placeholder="스킬을 검색하세요" autocomplete="off" />
									</c:when>
									<c:otherwise>
										<input type="text" id="skillSearchInput"
											placeholder="추가할 스킬을 검색하세요" autocomplete="off" value="">
									</c:otherwise>
								</c:choose>

								<!-- 자동완성 결과 표시 영역 -->
								<div id="skillSuggestions" class="suggestions"
									style="display: none; margin-top: 5px;"></div>

								<!-- 숨김 input (form 제출용) -->
								<input type="hidden" id="skillHidden" />
							</div>
						</div>
						<div class="profile-field">
							<label>E-mail</label> <input type="email" id="email" name="email"
								placeholder="Email" value="${ loginMember.email }">
						</div>
						<button type="submit" id="updateUser">회원정보 수정</button>
					</div>
				</form>
			</div>

			<div class="documents-section">
				<a class="document-card" href="jjim.me">
					<h2>찜 목록</h2>
					<div class="upload-box2">
						<h2>${jjimList.size()}</h2>
					</div>
				</a> <a class="document-card" href="cvList.my">
					<h2>이력서</h2>
					<div class="upload-box2">
						<h2>${attachmentList.size()}</h2>
					</div>
				</a> <a class="document-card"
					href="applyState.pl?memberNo=${loginMember.memberNo}">
					<h2>지원현황</h2>
					<div class="upload-box2">
						<h2>${applyList.size()}</h2>
					</div>
				</a>
			</div>
		</div>

		<div class="project-section">
			<div class="project-header">
				<h3>${ loginMember.memberName }님 프로젝트</h3>
			</div>
			<div class="project-content">
				<c:choose>
					<c:when test="${not empty readme}">
						<c:forEach var="i" begin="0" end="${repoTitle.size() - 1}">
							<div class="readme-section">
								<div class="readme-title">
									<h3 id="readmefile">${repoTitle[i]}<span>🔍</span>
									</h3>
								</div>

								<div class="readme-content">
									<!-- 수정 버튼 -->
									<button class="editReadmeBtn" data-index="${i}">ReadMe
										수정하기</button>
									<hr>
									<h3>
										프로젝트 링크 : <a id="readmelink" href="${repoLink[i]}">${repoTitle[i]}</a>
									</h3>
									<hr>
									<h3>ReadMe</h3>
									<div id="readmeDisplay${i}" class="readme-html">${readme[i]}</div>
								</div>
							</div>
							<br>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<p>레파지토리가 없습니다.</p>
					</c:otherwise>
				</c:choose>
			</div>

			<!-- ReadMe 수정 모달 -->
			<div id="readmeEditModal" class="modal"
				style="display: none; position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(0, 0, 0, 0.5); z-index: 1000; justify-content: center; align-items: center;">
				<div
					style="background: #fff; padding: 20px; width: 80%; max-width: 1600px; border-radius: 8px; position: relative;">
					<h2 style="margin-bottom: 15px;">ReadMe 수정</h2>
					<div id="readmeEditTextarea"
						style="height: 500px; border: 1px solid #ccc; font-size: 14px;"></div>
					<br> <br>
					<button id="saveReadmeBtn">저장</button>
					<button id="cancelReadmeBtn">취소</button>
				</div>
			</div>
		</div>


	</div>

	<script>
	// ✅ [1] jQuery 문서 준비
	$(function () {
	  // Readme 제목 토글
	  $(".readme-title").click(function () {
	    $(this).next().slideToggle(300);
	  });

	  // 기술 검색
	  $("#skillSearchInput").on("input", function () {
	    var keyword = $(this).val();

	    if (keyword.length > 0) {
	      $.ajax({
	        url: 'skill.search',
	        method: 'GET',
	        data: { keyword: keyword },
	        success: function (response) {
	          var suggestionsDiv = $("#skillSuggestions");
	          suggestionsDiv.empty();

	          if (response.length > 0) {
	            suggestionsDiv.show();

	            response.forEach(function (skill) {
	              var skillName = skill.SKILL_NAME;
	              var skillNo = skill.SKILL_NO;

	              var icon = '<span class="material-icons add-icon" style="vertical-align: middle; line-height: 1;">add_circle_outline</span>';
	              var skillClass = 'skill-name';
	              var isSelected = $("#skillSelected input[value='" + skillNo + "']").length > 0;

	              if (isSelected) {
	                suggestionsDiv.append(
	                  '<div class="suggestion-item" data-skill-no="' + skillNo + '" data-skill-name="' + skillName + '">' +
	                  '<span class="' + skillClass + '" style="color: #28a745;">' + skillName + '</span>' +
	                  '<span class="material-icons" style="color: #28a745; vertical-align: middle; line-height: 1;">remove_circle_outline</span>' +
	                  '</div>'
	                );
	              } else {
	                suggestionsDiv.append(
	                  '<div class="suggestion-item" data-skill-no="' + skillNo + '" data-skill-name="' + skillName + '">' +
	                  '<span class="' + skillClass + '" style="color: black;">' + skillName + '</span>' + icon +
	                  '</div>'
	                );
	              }
	            });
	          } else {
	            suggestionsDiv.hide();
	          }
	        },
	        error: function (xhr, status, error) {
	          console.error("검색 중 오류 발생:", error);
	        }
	      });
	    } else {
	      $("#skillSuggestions").hide();
	    }
	  });

	  // 기술 선택 또는 제거
	  $(document).on("click", ".suggestion-item", function () {
	    var skillNo = $(this).data("skill-no");
	    var skillName = $(this).data("skill-name");
	    var $this = $(this);

	    var existingItem = $("#skillSelected input[value='" + skillNo + "']").closest(".selected-skill-item");

	    if (existingItem.length === 0) {
	      $("#skillSelected").append(
	        '<div class="selected-skill-item">' +
	        '<input type="hidden" name="skillNos" value="' + skillNo + '">' +
	        skillName +
	        '<span class="material-icons remove-icon" style="vertical-align: middle; line-height: 1; cursor: pointer;">remove_circle_outline</span>' +
	        '</div>'
	      );
	    } else {
	      existingItem.remove();

	      $.ajax({
	        url: "skill.delete",
	        method: "POST",
	        data: {
	          memberNo: ${loginMember.memberNo},
	          skillNo: skillNo
	        },
	        success: function (response) {
	          if (response === "success") {
	            $this.remove();
	            updateHiddenSkills();
	          } else {
	            alert("삭제에 실패했습니다.");
	          }
	        },
	        error: function () {
	          alert("서버 오류가 발생했습니다.");
	        }
	      });
	    }

	    updateHiddenSkills();
	    $("#skillSearchInput").val('');
	    $("#skillSuggestions").hide();
	  });

	  $(document).on("click", ".selected-skill-item", function () {
	    var skillNo = $(this).find("input").val();
	    var $this = $(this);

	    $.ajax({
	      url: "skill.delete",
	      method: "POST",
	      data: {
	        memberNo: ${loginMember.memberNo},
	        skillNo: skillNo
	      },
	      success: function (response) {
	        if (response === "success") {
	          $this.remove();
	          updateHiddenSkills();
	        } else {
		          $this.remove();
		          updateHiddenSkills();
	        }
	      },
	      error: function () {
	        alert("서버 오류가 발생했습니다.");
	      }
	    });
	  });

	  function updateHiddenSkills() {
	    var selectedSkills = [];
	    $("#skillSelected input").each(function () {
	      selectedSkills.push($(this).val());
	    });
	    $("#skillHidden").val(selectedSkills.join(","));
	  }
	});


	// ✅ [2] README 관련 기능 (Vanilla JS)
	document.addEventListener("DOMContentLoaded", function () {
	  // ✅ 초기 README 렌더링 처리 (개행만 <br>로 변환)
	  document.querySelectorAll('.readme-html').forEach(el => {
	    const encoded = el.getAttribute('data-markdown');
	    if (encoded) {
	      const decoded = decodeURIComponent(escape(window.atob(encoded)));
	      const htmlWithBreaks = decoded.replace(/(?:\r\n|\r|\n)/g, '<br>');
	      el.innerHTML = htmlWithBreaks;
	    }
	  });

	  const modal = document.getElementById('readmeEditModal');
	  const editorContainer = document.getElementById('readmeEditTextarea');
	  const saveBtn = document.getElementById('saveReadmeBtn');
	  const cancelBtn = document.getElementById('cancelReadmeBtn');

	  let currentIndex = null;
	  let editor = null;

	  const repoNames = [
	    <c:forEach var="name" items="${repoTitle}" varStatus="status">
	      '${name}'<c:if test="${!status.last}">,</c:if>
	    </c:forEach>
	  ];

	  const repoFilePaths = [
	    <c:forEach var="path" items="${repoFilePath}" varStatus="status">
	      '${path}'<c:if test="${!status.last}">,</c:if>
	    </c:forEach>
	  ];

	  const turndownService = new TurndownService({
	    headingStyle: 'atx',
	    codeBlockStyle: 'fenced',
	  });

	  turndownService.addRule('imgTag', {
	    filter: 'img',
	    replacement: function (content, node) {
	      return node.outerHTML;
	    }
	  });

	  function initEditor() {
	    if (!editor) {
	      editor = CodeMirror(editorContainer, {
	        mode: 'markdown',
	        lineNumbers: true,
	        lineWrapping: true,
	        viewportMargin: Infinity,
	        theme: 'default'
	      });
	      editor.setSize("100%", "500px");
	    }
	  }

	  document.querySelectorAll('.editReadmeBtn').forEach(button => {
	    button.addEventListener('click', function () {
	      currentIndex = parseInt(this.getAttribute('data-index'));
	      const readmeElem = document.getElementById('readmeDisplay' + currentIndex);
	      if (!readmeElem) {
	        console.error(`readmeDisplay${currentIndex} 요소를 찾을 수 없습니다.`);
	        return;
	      }

	      initEditor();

	      const rawHtml = readmeElem.innerHTML || "";
	      const markdownText = turndownService.turndown(rawHtml);

	      editor.setValue(markdownText);
	      modal.style.display = 'flex';
	      editor.refresh();
	      editor.focus();
	    });
	  });

	  saveBtn.addEventListener('click', function () {
	    if (currentIndex === null || !editor) {
	      alert('에디터가 초기화되지 않았거나 index가 지정되지 않았습니다.');
	      return;
	    }

	    const rawMarkdown = editor.getValue();
	    if (!rawMarkdown) {
	      alert('내용을 입력해주세요.');
	      return;
	    }

	    const encodedContent = btoa(unescape(encodeURIComponent(rawMarkdown)));

	    const dataToSend = {
	      repoName: repoNames[currentIndex],
	      params: {
	        filePath: repoFilePaths[currentIndex],
	        content: encodedContent
	      }
	    };

	    $.ajax({
	      url: "updateReadme",
	      method: 'POST',
	      contentType: 'application/json; charset=utf-8',
	      data: JSON.stringify(dataToSend),
	      success: function () {
	        alert('README 파일이 성공적으로 수정되었습니다.');
	        const displayElem = document.getElementById('readmeDisplay' + currentIndex);
	        if (displayElem) {
	          displayElem.innerHTML = marked.parse(rawMarkdown);
	        }
	        modal.style.display = 'none';
	        currentIndex = null;
	      },
	      error: function (xhr) {
	        console.error('README 업데이트 실패:', xhr.responseText);
	        alert('README 수정 중 오류가 발생했습니다.');
	      }
	    });
	  });

	  cancelBtn.addEventListener('click', function () {
	    modal.style.display = 'none';
	    currentIndex = null;
	  });

	  modal.addEventListener('click', function (e) {
	    if (e.target === modal) {
	      modal.style.display = 'none';
	      currentIndex = null;
	    }
	  });
	});
	</script>
	<script
		src="https://cdn.jsdelivr.net/npm/codemirror@5/lib/codemirror.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/codemirror@5/mode/markdown/markdown.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/marked/marked.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/turndown/dist/turndown.min.js"></script>
</body>
</html>