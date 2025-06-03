<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">

<!-- jQuery library -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

<!-- Popper JS -->
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>

<!-- Latest compiled JavaScript -->
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
<c:set var="path" value="${ pageContext.request.contextPath }" />
<link rel="stylesheet" href="${ path }/css/cvList.css">
</head>
<body>
	<jsp:include page="../member/menubar.jsp" />
	<div class="container">
		<div class="header">
			<div class="title">이력서 목록 (<c:out value="${fn:length(attachmentList)}"/>)</div>
			<div class="buttons">
				<button class="button upload" class="btn btn-primary"
					data-toggle="modal" data-target="#myModal">업로드</button>
				<button class="button save">삭제</button>
			</div>
		</div>

		<div class="file-list">
			<c:choose>
				<c:when test="${empty attachmentList}">
					<!-- 파일이 없을 경우 -->
					<p>파일이 없습니다.</p>
				</c:when>
				<c:otherwise>
					<!-- 파일 목록이 있을 경우 -->
					<c:forEach var="i" begin="0" end="${ attachmentList.size() - 1 }">
						<div class="file-item">
							<input type="checkbox" id="file${i}"
								value="${ attachmentList[i].fileNo }"> <input
								type="hidden" value="${ attachmentList[i].fileEditName }">
							<label for="file${i}" class="file-name">${ attachmentList[i].fileOriginName }</label>
						</div>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</div>
	</div>

	<!-- 이력서 추가 모달 -->
	<div class="modal" id="myModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<!-- Modal Header -->
				<div class="modal-header">
					<h4 class="modal-title">이력서 추가</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>

				<!-- Modal body -->
				<!-- 첨부파일 시 method => post , enctype => multipart/form-data -->
				<form action="resumeInsert.my" method="post"
					enctype="multipart/form-data">
					<div class="modal-body">
						<input type="file" name="resumeFile">
					</div>

					<!-- Modal footer -->
					<div class="modal-footer">
						<button type="submit" class="btn btn-primary">등록</button>
						<button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
					</div>
				</form>
			</div>
		</div>
	</div>

	<!-- 이력서 삭제 스크립트 -->
	<script>
		$(function(){
			//이력서 삭제 스크립트
			$(".save").click(function(){ //삭제 버튼 클릭 시 
				//체크한 이력서를 리스트에 저장
				let delNumList = [];
				let delNameList = [];
				$("input[type=checkbox]:checked").each(function(){
					delNumList.push( $(this).val() );
					delNameList.push( $(this).next().val() );
					//console.log(delInputList)
				})
				
				let delList = confirm("정말 삭제하시겠습니까?")
				//console.log(delList);
				//확인 === true , 취소 === false
				//traditional => 배열을 넘겨주기 위한 ajax 속성
				if(delList){
					$.ajax({
						url:"resumeDelete.my",
						data:{ 
							resumeNumList: delNumList ,
							resumeNameList : delNameList
							},
						traditional: true, 
						success:function(result){
							//AJAX이 성공적으로 되면 새로고침
							location.reload();
						},
						error:function(){
							console.log("이력서 삭제 ajax 실패")
						}
					})
				}
				
			})
		})
	</script>

</body>
</html>