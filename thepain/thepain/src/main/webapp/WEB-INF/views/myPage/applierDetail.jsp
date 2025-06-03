<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>DrsP Recruitment</title>
<c:set var="path" value="${ pageContext.request.contextPath }" />
<link rel="stylesheet" href="${ path }/css/applierDetail.css">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">

<!-- jQuery library -->
<script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.slim.min.js"></script>

<!-- Popper JS -->
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<jsp:include page="../member/menubar.jsp"/>
    
    <div class="main-content">
        <div class="profile-card">
            <div class="profile-header">
                <div class="profile-image">
                    <img src="${ gitProfile.profile }" alt="프로필 이미지">
                </div>
            </div>
            
            <div class="profile-fields">
                <div class="profile-field">
                    <div class="field-label">이름</div>
                    <div class="field-value">${ memberInfo.memberName }</div>
                </div>
                
                <div class="profile-field">
                    <div class="field-label">Skill</div>
                    <div class="field-value">${ memberInfo.skill }</div>
                </div>
                
                <div class="profile-field">
                    <div class="field-label">경력</div>
                    <div class="field-value">
                    	<c:choose>
                    		<c:when test="${ memberInfo.career eq 0 }">
                    			신입
                    		</c:when>
                    		<c:otherwise>
                    			${ memberInfo.career }년
                    		</c:otherwise>
                    	</c:choose>
                    </div>
                </div>
                
                <div class="profile-field">
                    <div class="field-label">E-mail</div>
                    <div class="field-value">${ memberInfo.email }</div>
                    <button class="send-email" data-toggle="modal" data-target="#myModal">이메일 보내기</button>
                </div>
                
                <div class="profile-field">
                	<c:choose>
                		<c:when test="${fn:contains(fileName, '@README')}">
                			<div class="field-label">프로젝트 링크</div>
		                    <div class="field-value">
		                    	<a href="${ fn:substringBefore(fileName, "@") }">${ fn:substringBefore(fileName, "@") }</a>
		                    </div>
                		</c:when>
                		
                		<c:otherwise>
		                    <div class="field-label">이력서(자기소개서)</div>
							<div class="field-value">
								<a href="#"
									onclick="downloadFile('${memberInfo.memberNo}', '${memberInfo.recruitmentNo}'); return false;">
									${fileName} </a>
							</div>
                		</c:otherwise>
                	</c:choose>
                </div>
                <c:if test="${ not empty readmeCon }">
	                <div class="profile-field">
		                <div class="field-label">ReadMe</div>
	                    <div class="field-value readmeContent">${ readmeCon }</div>
	                </div>
                </c:if>
            </div>
        </div>
    </div>
    
    <!-- 이메일 전송 model -->
	<div class="modal" id="myModal">
	  <div class="modal-dialog">
	    <div class="modal-content">
	
	      <!-- Modal Header -->
	      <div class="modal-header">
	        <h4 class="modal-title">이메일 전송</h4>
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	    	  </div>
	
	      <!-- Modal body -->
	      <form action="sendEmailApplier.my" method="post">
		      <div class="modal-body">
		      		<table>
		      			<tr>
		      				<th>수신자</th>
		      				<td><input type="text" name="receptionEmail" class="form-control" value="${ memberInfo.email }" required></td>
		      			</tr>
			      		<tr>
			      			<th>이메일 제목</th>
			      			<td><input class="form-control" type="text" name="subject"></td>
			      		</tr>
			      		<tr>
			      			<th>이메일 내용</th>
			      			<td><textarea rows="5" cols="50" class="form-control" name="content"></textarea></td>
			      		</tr>
		      		</table>
		      		<br>
		      </div>
		
		      <!-- Modal footer -->
		      <div class="modal-footer">
		      	<input type="hidden" value="${ apply.rmNo }" name="rmNo">
		      	<button type="submit" class="btn btn-primary">전송</button>
		        <button type="button" class="btn btn-danger" data-dismiss="modal">취소</button>
		      </div>
		  </form>
	    </div>
	  </div>
	</div>

	<script>
    function downloadFile(memberNo, recruitmentNo) {
	        $.ajax({
	            url: 'downloadResume.do',
	            type: 'GET',
	            data: {
	                memberNo: memberNo,            // memberNo 추가
	                recruitmentNo: recruitmentNo   // recruitmentNo 추가
	            },
	            xhrFields: {
	                responseType: 'blob'
	            },
	            success: function(blob, status, xhr) {
	                const disposition = xhr.getResponseHeader('Content-Disposition');
	                let downloadFileName = "defaultFileName"; // 파일 이름을 서버에서 자동으로 설정할 예정
	
	                if (disposition && disposition.indexOf('filename=') !== -1) {
	                    const matches = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/.exec(disposition);
	                    if (matches != null && matches[1]) {
	                        downloadFileName = decodeURIComponent(matches[1].replace(/['"]/g, ''));
	                    }
	                }
	
	                const url = window.URL.createObjectURL(blob);
	                const a = document.createElement('a');
	                a.href = url;
	                a.download = downloadFileName;
	                document.body.appendChild(a);
	                a.click();
	                document.body.removeChild(a);
	                window.URL.revokeObjectURL(url);
	            },
	            error: function() {
	                alert('파일 다운로드에 실패했습니다.');
	            }
	        });
	    }
	</script>
</body>
</html>
