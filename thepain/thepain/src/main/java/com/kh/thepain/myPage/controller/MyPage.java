package com.kh.thepain.myPage.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.thepain.common.model.service.EmailService;
import com.kh.thepain.common.model.service.GitService;
import com.kh.thepain.common.model.service.GitTemplate;
import com.kh.thepain.common.model.vo.Attachment;
import com.kh.thepain.common.model.vo.Email;
import com.kh.thepain.common.model.vo.Git;
import com.kh.thepain.jjim.model.service.JjimService;
import com.kh.thepain.member.model.service.MemberService;
import com.kh.thepain.member.model.vo.Member;
import com.kh.thepain.myPage.model.service.MypageService;
import com.kh.thepain.postList.model.vo.Apply;
import com.kh.thepain.postList.model.vo.PostList;
import com.kh.thepain.postList.model.vo.PostWrite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class MyPage {

	@Autowired
	private GitTemplate gTemplate;

	@Autowired
	private GitService gService;

	@Autowired
	private MemberService mService;

	@Autowired
	private MypageService myService;

	@Autowired
	private JjimService jService;

	@Autowired
	private EmailService emailService;

	/**
	 * @param session
	 * @param model
	 * @return git 유저 전체 정보 조회
	 */
	@RequestMapping("myPage.me")
	public String myPage(HttpSession session, Model model) {
		// 토큰이 비어 있다면 해당 코드 블록을 실행하지 않음
		Member loginMember = (Member) session.getAttribute("loginMember"); // 로그인 객체 가져오기
		if (loginMember.getToken() == null || loginMember.getToken().isEmpty()) {

			// 👉 회원 상세정보
			Member updateMember = mService.selectMember(loginMember);
			model.addAttribute("updateMember", updateMember);

			// 👉 기술 스택 정보
			List<Map<String, Object>> skills = mService.selectSkills(loginMember);
			model.addAttribute("skills", skills);

			// 👉 찜 목록 개수
			ArrayList<PostList> jjimList = jService.selectJjimList(loginMember.getMemberNo());
			model.addAttribute("jjimList", jjimList);

			// 👉 이력서 목록 개수
			model.addAttribute("attachmentList", myService.resumeList((Member) session.getAttribute("loginMember")));

			// 👉 지원현황 개수
			List<Apply> applyList = mService.selectApplyList(loginMember);
			model.addAttribute("applyList", applyList);

			String profileFileName = myService.selectLatestProfileByMemberNo(loginMember.getMemberNo());
			if (profileFileName == null || "null".equals(profileFileName)) {
				model.addAttribute("profileFileName", null);
			} else {
				model.addAttribute("profileFileName", "/thepain/resources/img/" + profileFileName);

			}

			return "/myPage/myPage";
		}
		Member githubInfo = gService.getUserInfo((String) session.getAttribute("token"));

		Git g = new Git();
		g.setMethod("GET");
		g.setToken((String) session.getAttribute("token"));
		g.setUri(githubInfo.getGitRepos()); // GitService에서 정보 가져오는 서비스 메소드 수정
		// ObjectMapper objecMapper = new ObjectMapper();
		// JsonNode repoTotal;
		// try {
		/*
		 * repoTotal = objecMapper.readTree(gTemplate.getGitHubCode(g)); //readme 파일 개수에
		 * 따라서 ArrayList에 담아서 저장 후 전달 ArrayList<String> readme = new
		 * ArrayList<String>(); ArrayList<String> repoTitle = new ArrayList<String>();
		 * ArrayList<String> repoLink = new ArrayList<String>(); ArrayList<String>
		 * repoFilePath = new ArrayList<String>(); ArrayList<String> repoFileUrl = new
		 * ArrayList<String>(); for(int i=0; i<repoTotal.size(); i++) { String owner =
		 * repoTotal.get(i).get("owner").get("login").asText(); String repoName =
		 * repoTotal.get(i).get("name").asText();
		 * 
		 * // readme String readmeContent = gTemplate.getReadme(
		 * (String)session.getAttribute("token"), owner, repoName); String a =
		 * gService.convertMarkdownToHtml(readmeContent); readme.add(a);
		 * 
		 * // 레파지토리 이름 repoTitle.add(repoName);
		 * 
		 * // 레파지토리 링크 repoLink.add(repoTotal.get(i).get("html_url").asText());
		 * 
		 * // 파일 경로 (API 요청용) String filePath = "README.md"; repoFilePath.add(filePath);
		 * 
		 * // GitHub 웹 URL (보기용) String fileUrl = "https://github.com/" + owner + "/" +
		 * repoName + "/blob/main/" + filePath; repoFileUrl.add(fileUrl); }
		 * 
		 * model.addAttribute("readme", readme); model.addAttribute("repoTitle",
		 * repoTitle); model.addAttribute("repoLink", repoLink);
		 * model.addAttribute("repoFilePath", repoFilePath);
		 * model.addAttribute("repoFileUrl", repoFileUrl);
		 * 
		 * model.addAttribute("readme",readme); model.addAttribute("repoTitle",
		 * repoTitle); model.addAttribute("repoLink", repoLink);
		 * model.addAttribute("repoFilePath", repoFilePath);
		 */
		/*
		 * } catch (JsonProcessingException e) { e.printStackTrace(); }
		 */
		gTemplate.getGitHubCode(g).subscribe(repos -> {
			ObjectMapper objecMapper = new ObjectMapper();

			try {
				JsonNode repoTotal = objecMapper.readTree(repos);

				// readme 파일 개수에 따라서 ArrayList에 담아서 저장 후 전달
				ArrayList<String> readme = new ArrayList<String>();
				ArrayList<String> repoTitle = new ArrayList<String>();
				ArrayList<String> repoLink = new ArrayList<String>();
				ArrayList<String> repoFilePath = new ArrayList<String>();
				ArrayList<String> repoFileUrl = new ArrayList<String>();
				for (int i = 0; i < repoTotal.size(); i++) {
					String owner = repoTotal.get(i).get("owner").get("login").asText();
					String repoName = repoTotal.get(i).get("name").asText();

					// readme
					String readmeContent = gTemplate.getReadme((String) session.getAttribute("token"), owner, repoName);
					String a = gService.convertMarkdownToHtml(readmeContent);
					readme.add(a);

					// 레파지토리 이름
					repoTitle.add(repoName);

					// 레파지토리 링크
					repoLink.add(repoTotal.get(i).get("html_url").asText());

					// 파일 경로 (API 요청용)
					String filePath = "README.md";
					repoFilePath.add(filePath);

					// GitHub 웹 URL (보기용)
					String fileUrl = "https://github.com/" + owner + "/" + repoName + "/blob/main/" + filePath;
					repoFileUrl.add(fileUrl);
				}

				model.addAttribute("readme", readme);
				model.addAttribute("repoTitle", repoTitle);
				model.addAttribute("repoLink", repoLink);
				model.addAttribute("repoFilePath", repoFilePath);
				model.addAttribute("repoFileUrl", repoFileUrl);

				model.addAttribute("readme", readme);
				model.addAttribute("repoTitle", repoTitle);
				model.addAttribute("repoLink", repoLink);
				model.addAttribute("repoFilePath", repoFilePath);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		});

		// 👉 회원 상세정보
		Member updateMember = mService.selectMember(loginMember);
		model.addAttribute("updateMember", updateMember);

		// 👉 기술 스택 정보
		List<Map<String, Object>> skills = mService.selectSkills(loginMember);
		model.addAttribute("skills", skills);

		// 👉 찜 목록 개수
		ArrayList<PostList> jjimList = jService.selectJjimList(loginMember.getMemberNo());
		model.addAttribute("jjimList", jjimList);

		// 👉 이력서 목록 개수
		model.addAttribute("attachmentList", myService.resumeList((Member) session.getAttribute("loginMember")));

		// 👉 지원현황 개수
		List<Apply> applyList = mService.selectApplyList(loginMember);
		model.addAttribute("applyList", applyList);

		return "/myPage/myPage";
	}

	/**
	 * @return 채용담당자 마이페이지로 이동
	 */
	@RequestMapping("staffMyPage.me")
	public String staffMypage(HttpSession session, Model model) {
		Member loginMember = (Member) session.getAttribute("loginMember");

		// 로그인 상태 확인
		if (loginMember == null) {
			session.setAttribute("alertMsg", "로그인 후 이용할 수 있습니다.");
			return "redirect:/"; // 로그인 페이지로 리다이렉트
		}

		// 로그인 된 경우, 마이페이지 처리
		String companyName = myService.companyName(loginMember.getEnterpriseNo());
		model.addAttribute("companyName", companyName);

		ArrayList<PostWrite> postWriteList = myService.postWriteList(loginMember.getMemberNo());
		model.addAttribute("postWriteList", postWriteList);

		String profileFileName = myService.selectLatestProfileByMemberNo(loginMember.getMemberNo());

		if (profileFileName == null || "null".equals(profileFileName)) {
			model.addAttribute("profileFileName", null);
		} else {
			model.addAttribute("profileFileName", "/thepain/resources/img/" + profileFileName);
		}

		return "myPage/staffMyPage"; // 마이페이지 화면으로 이동
	}

	/**
	 * @return 해당 공고 지원자 리스트 페이지 이동 컨트롤러
	 */
	@RequestMapping("applierList.my")
	public String applierList(int postNo, Model model) {
		// 채용공고 출력을 위한 정보 조회
		PostList postList = myService.postWriteInfo(postNo);
		model.addAttribute("postWrite", postList);

		// 채용공고에 지원한 지원 정보 조회
		ArrayList<Apply> applyList = new ArrayList<Apply>();
		applyList = myService.applyList(postNo);
		model.addAttribute("applyList", applyList);

		ArrayList<Member> memberList = new ArrayList<Member>();
		ArrayList<String> resumeNameList = new ArrayList<String>();
		if (!applyList.isEmpty()) {

			// 지원자의 회원 정보 조회
			memberList = myService.memberList(applyList);
			model.addAttribute("memberList", memberList);

			// 지원자가 제출한 이력서 정보 조회
			resumeNameList = new ArrayList();
			for (Apply a : applyList) {
				if (a.getReadMe() == null) { // 리드미가 비어 있다면
					resumeNameList.add(myService.fileName(a.getFileNo()));
				} else { // 리드미가 있는 경우
					resumeNameList.add(a.getReadMe());
				}
			}
			model.addAttribute("fileNameList", resumeNameList);

		}
		return "myPage/staffMypageResumeList";
	}

	/**
	 * @return 지원자 디테일 정보 페이지 이동 컨트롤러
	 */
	@RequestMapping("applierDeatil.my")
	public String applierDeatil(int mNo, int postNo, Model model) {
		// 회원번호로 회원 정보 조회
		Member memberInfo = new Member();
		memberInfo.setMemberNo(mNo);
		memberInfo.setCareer(postNo);

		Member user = mService.resumeMemberInfo(memberInfo);
		model.addAttribute("memberInfo", user);

		// git 프로필 조회(토큰을 이용해서 정보 깃허브 정보 조회)
		model.addAttribute("gitProfile", gService.getUserProfile(user.getGitNick()));

		// 해당회원이 제출한 이력서 조회
		// 채용공고 번호외 회원 번호로 이력서 번호 조회
		Member mrInfo = new Member();
		mrInfo.setMemberNo(mNo);
		mrInfo.setCareer(postNo);
		Apply apply = myService.memberResumeInfo(mrInfo);
		model.addAttribute("apply", apply);

		// readme 콘텐츠 조회
		if (apply.getReadMe() != null) {
			String projectName = apply.getReadMe().substring(apply.getReadMe().lastIndexOf("/") + 1);
			String readme = gTemplate.getReadme("", // 토큰 없이 진행
					user.getGitNick(), // 깃허브 이름
					projectName // 해당 프로젝트 이름
			);
			String readmeCon = gService.convertMarkdownToHtml(readme);
			model.addAttribute("readmeCon", readmeCon);
		}

		// 이력서 인지 리드미 인지 구분 후 전달
		String resumeName = "";
		if (apply.getReadMe() == null) {
			resumeName = myService.fileName(apply.getFileNo());
		} else {
			resumeName = apply.getReadMe() + "@README";
		}
		model.addAttribute("fileName", resumeName);
		return "myPage/applierDetail";
	}

	/**
	 * @return 채용담당자 프로필 저장 후 마이페이지로 이동
	 */
	@RequestMapping("insert.img")
	public String insertStaffProfile(@RequestParam("upfile") MultipartFile file, @RequestParam("memberNo") int memberNo,
			HttpSession session) {

		// 파일이 존재하는지 확인
		if (file != null && !file.isEmpty()) {

			// 파일 정보 설정
			Attachment fileVo = new Attachment();
			fileVo.setFileOriginName(file.getOriginalFilename());

			// 기존 파일 조회
			String selectloadedFile = myService.selectLatestProfileByMemberNo(memberNo);

			// 기존 파일 삭제
			if (selectloadedFile != null && !selectloadedFile.isEmpty()) {
				String directoryPath = session.getServletContext().getRealPath("/resources/img/");
				String filePathToDelete = directoryPath + selectloadedFile;
				new File(filePathToDelete).delete(); // 로컬 파일 삭제

				// DB에서 파일 정보 삭제
				int deleteResult = myService.deleteProfileByMemberNo(memberNo);
				if (deleteResult > 0) {
					// System.out.println("DB 프로필 정보 삭제 성공");
				} else {
					// System.out.println("DB 프로필 정보 삭제 실패");
				}

			}

			// 파일을 저장할 경로 설정
			String directoryPath = session.getServletContext().getRealPath("/resources/img/");
			File directory = new File(directoryPath);
			if (!directory.exists()) {
				directory.mkdirs();
			}

			// 원본 파일명 가져오기
			String originName = file.getOriginalFilename();

			// 확장자 추출
			String ext = originName.substring(originName.lastIndexOf("."));

			// 현재 시간과 랜덤 숫자 생성
			String currentTime = String.valueOf(System.currentTimeMillis());
			String ranNum = String.valueOf((int) (Math.random() * 1000));

			// 변경된 파일명 생성
			String changeName = currentTime + ranNum + ext;

			// 저장할 경로 설정
			String filePath = directoryPath + changeName;

			// 파일 저장
			try {
				file.transferTo(new File(filePath));
			} catch (IOException e) {
				e.printStackTrace();
			}

			// 파일 정보 DB에 저장
			fileVo.setFileEditName(changeName);
			fileVo.setFileRoot("/resources/img/");
			fileVo.setMemberNo(memberNo);
			fileVo.setFileType("P");

			// 프로필 이미지 정보 DB에 저장
			int fileResult = myService.resumeInsertAs(fileVo);
			if (fileResult > 0) {
				session.setAttribute("alertMsg", "프로필 이미지가 성공적으로 업로드되었습니다.");
			} else {

				session.setAttribute("alertMsg", "파일 업로드 실패.");
			}
		} else {
			session.setAttribute("alertMsg", "파일이 선택되지 않았습니다.");
		}

		return "redirect:/staffMyPage.me"; // staffMyPage.me 요청을 호출
	}

	/**
	 * @param email 채용담당자가 지원자에게 이메일 보내는 컨트롤러
	 */
	@RequestMapping("sendEmailApplier.my")
	public String sendEmailApplier(Email email, HttpServletRequest request, String rmNo) {
		emailService.sendApplierEmail(email);
		return "redirect:/applierList.my?postNo=" + rmNo;
	}

	/**
	 * @param passed 지원자 합격 불합격 처리 컨트롤러
	 */
	@ResponseBody
	@RequestMapping("applyPassed.my")
	public int applyPassed(int passed, int postNo, int applyNum) {
		int result = myService.applyPassed(passed, postNo, applyNum);
		return result;
	}

}
