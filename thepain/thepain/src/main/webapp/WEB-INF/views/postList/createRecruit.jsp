<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	form {
	  max-width: 600px;
	  margin: auto;
	  display: flex;
	  flex-direction: column;
	  gap: 15px;
	}
	
	input[type="text"], select, textarea {
	  padding: 10px;
	  border: 1px solid #ddd;
	  border-radius: 6px;
	  font-size: 14px;
	}
	
	textarea {
	  height: 100px;
	  resize: vertical;
	}
	
	button {
	  padding: 10px 20px;
	  border: none;
	  border-radius: 6px;
	  background-color: #4F46E5;
	  color: white;
	  font-weight: bold;
	  cursor: pointer;
	}
	
	button[type="button"] {
	  background-color: #ddd;
	  color: #333;
	  margin-right: 10px;
	}
		
</style>
</head>
<body>
	
		<form action="insertRecruit.po" method="post" enctype="multipart/form-data">
			  <h2>구인공고 글 작성하기</h2>
			
			  <label>직무명</label>
			  <input type="text" name="jobTitle" required>
			
			  <label>회사 로고</label>
			  <input type="file" name="logoFile" id="logoFileInput" accept="image/*">
				
			  <!-- 미리보기 영역 구현하면 좋을 것 같음 -->
			  <div id="filePreview" style="margin-top: 10px;"></div>

			
			  <label>근무 위치</label>
			  <input type="text" name="location">
			
			  <label>고용 형태</label>
			  <select name="employmentType">
			    <option value="정규직">정규직</option>
			    <option value="계약직">계약직</option>
			    <option value="프리랜서">프리랜서</option>
			  </select>
			
			  <label>급여 (예: 24,000,000 ~ 32,000,000)</label>
			  <input type="text" name="salaryRange">
			
			  <label>업무 내용</label>
			  <textarea name="taskDetail"></textarea>
			
			  <label>필요 역량</label>
			  <textarea name="skills"></textarea>
			
			  <label>자격 요건</label>
			  <textarea name="qualification"></textarea>
			
			  <label>우대 사항</label>
			  <textarea name="preferred"></textarea>
			
			  <label>추가 설명</label>
			  <textarea name="note"></textarea>
			
			  <div style="margin-top:20px;">
			    <button type="button" onclick="history.back()">취소하기</button>
			    <button type="submit">등록하기</button>
			  </div>
		</form>

	
</body>
</html>