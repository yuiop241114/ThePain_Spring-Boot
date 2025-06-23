package com.kh.thepain.myPage.controller;

import com.kh.thepain.common.model.vo.Attachment;
import com.kh.thepain.member.model.vo.Member;
import com.kh.thepain.myPage.model.service.MypageService;
import com.kh.thepain.postList.model.vo.Apply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Controller
//이력서 컨트롤러
public class cvListController {

	@Autowired
	private MypageService mService;

	@Value("${file.upload-dir}")
	private String uploadDir; // 주입받을 필드명과 타입 일치

	/**
	 * @return 마이페이지에서 이력서 확인 페이지 이동 컨트롤러
	 */
	@RequestMapping("cvList.my")
	public String cvList(HttpSession session, Model model) {
		// 이력서 정보 전체 조회하는 코드 작성
		// 회원번호를 이용해서 해당 회원이 가지고 있는 이력서 파일 정보 조회
		// Attachment VO는 common 패키지에 존재
		model.addAttribute("attachmentList", mService.resumeList((Member) session.getAttribute("loginMember")));
		return "myPage/cvList";
	}

	/**
	 * @param resumeFile
	 * @param session    이력서 등록 컨트롤러
	 */
	@RequestMapping("resumeInsert.my")
	public String resumeInsert(MultipartFile resumeFile, HttpSession session) {
		// 첨부파일명이 빈문자가 아니다 => 첨부파일이 있다
		Attachment fileVo = new Attachment();
		if (!resumeFile.getOriginalFilename().equals("")) {
			// 원본명 저장
			fileVo.setFileOriginName(resumeFile.getOriginalFilename());
			// 변경명 저장
			// saveFile 메소드를 만들어서 이름 변경 및 원하는 경로에 첨부파일 저장
			fileVo.setFileEditName(saveFile(resumeFile, "resume"));
			// 이력서를 저장할 회원번호
			fileVo.setMemberNo(((Member) session.getAttribute("loginMember")).getMemberNo());
			// fileTyep 지정
			fileVo.setFileType("이력서");
			// 저장 경로 작성
			fileVo.setFileRoot("/resume/");

		}
		// db에 이력서 관련 정보 저장
		int result = mService.resumeInsert(fileVo);

		// 이력서 등록 성공
		if (result > 0) {
			session.setAttribute("alertMsg", "이력서 등록 성공");
			return "redirect:cvList.my";
		} else { // 이력서 등록 실패
			session.setAttribute("alertMsg", "이력서 등록 실패 다시 시도해 주세요");
			return "redirect:cvList.my";
		}

	}

	/**
	 * @param upfile
	 * @return 첨부파일 객체와 session을 받아서 첨부파일 저장 및 변경명으로 바꿔주는 메소드
	 */
	public String saveFile(MultipartFile upfile, String root) {
		String originName = upfile.getOriginalFilename();
		String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		int ranNum = (int) (Math.random() * 90000 + 10000); // 5자리 랜덤값
		// .다음에 있는 확장자명을 가져가기 위한 코드
		// 마지막에 있는 .을 기준으로 가져온다
		String ext = originName.substring(originName.lastIndexOf("."));

		// 변경된 파일명
		String changeName = currentTime + ranNum + ext;

		// 업로드 시키고자 하는 폴더의 물리적 경로
		// session.getServletContext() => Application Scope
		//String savePath = session.getServletContext().getRealPath("resources/static/" + root + "/");
		String savePath = uploadDir + root + "/";

		try {
			// 해당 경로에 변경된 이름으로 첨부파일을 저장
			upfile.transferTo(new File(savePath + changeName));
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}

		return changeName;
	}

	/**
	 * @return 이력서 삭제 컨트롤러
	 */
	@ResponseBody
	@RequestMapping(value="resumeDelete.my", produces="text/html; charset=utf-8")
	//@RequestParam("resumeList") 정확하게 배열을 받기 위해 해당 어노테이션으로 ajax에 data에 작성해 놓은 키값 작성
	public String resumeDelete(
			HttpSession session, 
			@RequestParam("resumeNumList") ArrayList<String> resumeNumList,
			@RequestParam("resumeNameList") ArrayList<String> resumeNameList
			) {
		//resumeNumList : 이력서 번호
		//resumeNameList : 이력서 변경명
		//이력서 번호와 이름을 ArrayList에 한번에 저장
		ArrayList<Apply> resumeList = new ArrayList<Apply>();
		for(int i=0; i<resumeNumList.size(); i++) {
			Apply a = new Apply();
			a.setFileNo( Integer.parseInt(resumeNumList.get(i)) );
			a.setResult( resumeNameList.get(i));
			resumeList.add(a);
		}
		
		//지원서로 사용중인 이력서를 제외한 이력서 번호 리스트를 반환
		ArrayList<Apply> deleteResumeList = new ArrayList<>();
		deleteResumeList = mService.selectApplyResume(resumeList);

		if(!deleteResumeList.isEmpty()) { //삭제할 이력서가 존재하는 경우
			//db로 가서 attachment 테이블에 있는 해당 첨부파일 정보를 delete
			int result = mService.resumeDelete(deleteResumeList);
			if(result > 0) { //delete 성공
				for(String name : resumeNameList) { //선택한 이력서 개수 만큼 진행
					//File 객체를 물리적 경로를 이용해서 생성 후 delete 함수로 삭제
					new File(session.getServletContext().getRealPath("/resources/resume/" + name)).delete();
				}
				session.setAttribute("alertMsg", "사용중인 이력서를 제외한 이력서 삭제 성공");
				return "success";
			}else { //delete 실패 
				session.setAttribute("alertMsg", "이력서 삭제 실패");
				return "fail";
			}
		}else { //삭제할 이력서가 없는 경우
			session.setAttribute("alertMsg", "사용중인 이력서입니다");
			return "success";
		}

	}

	// 파일 다운로드 처리
	@RequestMapping("downloadResume.do")
	@ResponseBody
	public ResponseEntity<byte[]> downloadResume(@RequestParam("memberNo") int memberNo,
	                                             @RequestParam("recruitmentNo") int recruitmentNo,
	                                             HttpSession session) {
	    try {
	        // 회원 객체 생성 및 정보 설정
	        Member m = new Member();
	        m.setMemberNo(memberNo);
	        m.setRecruitmentNo(recruitmentNo);

	        // 단일 이력서 조회
	        Attachment attachment = mService.applyResumeSelect(m);

	        // 이력서가 없으면 404 반환
	        if (attachment == null) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	        }

	        // 파일 경로 설정
	        String savePath = session.getServletContext().getRealPath("/resources/resume/");
	        File file = new File(savePath + attachment.getFileEditName());

	        // 파일 존재 여부 확인
	        if (!file.exists()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	        }

	        // 파일을 바이트 배열로 읽음
	        byte[] fileContent = java.nio.file.Files.readAllBytes(file.toPath());

	        // 파일 이름 인코딩 (한글 대응)
	        String encodedFileName = URLEncoder.encode(attachment.getFileOriginName(), "UTF-8").replaceAll("\\+", "%20");

	        // HTTP 헤더 설정
	        HttpHeaders headers = new HttpHeaders();
	        headers.add("Content-Disposition", "attachment; filename=\"" + encodedFileName + "\"");
	        headers.add("Content-Type", "application/octet-stream");

	        // 파일 반환
	        return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);

	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}

}
