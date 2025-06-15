package com.kh.thepain.member.controller;

import com.kh.thepain.common.model.service.EmailService;
import com.kh.thepain.common.model.service.GitService;
import com.kh.thepain.common.model.vo.Git;
import com.kh.thepain.jjim.model.service.JjimService;
import com.kh.thepain.member.model.service.MemberService;
import com.kh.thepain.member.model.vo.Member;
import com.kh.thepain.myPage.model.service.MypageService;
import com.kh.thepain.postList.model.service.PostListServiceImpl;
import com.kh.thepain.postList.model.vo.PostList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class MemberController {

//	private static final String CLIENT_ID = "Ov23liydoA44JqnHWLOb"; // GitHub에서 받은 클라이언트 아이디
//  private static final String CLIENT_SECRET = "abd0691fe63d78e15b81a5d4df6d4d20862aff32"; // GitHub에서 받은 클라이언트 시크릿

	@Autowired
	private GitService gService;

	@Autowired
	private MemberService mService;

	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;

	@Autowired
	private JjimService jService;

	@Autowired
	private MypageService myService;

	@Autowired
	private PostListServiceImpl pService;

	@Autowired
	private EmailService emailService;

	/**
	 * @return 로그인 페이지 이동 컨트롤러
	 */
	@RequestMapping("loginForm.me")
	public String loginFormController(HttpSession session) {
		session.removeAttribute("logingAlertMsg"); // 이전 메시지 제거
		session.removeAttribute("logingConfirmMsg"); // confirm 메시지도 제거
		session.invalidate();
		return "member/memberLoginForm";
	}

	/**
	 * @param session
	 * @return 로그인 컨트롤러
	 */
	@RequestMapping("login.me")
	public String loginController(Member loginMember, HttpSession session, HttpServletRequest request) {

		Member m = mService.loginController(loginMember); // 아이디로 조회
		if (m != null) { // 아이디가 존재하는지 확인
			// 비밀번호 암호화문과 평문을 비교
			if (bcryptPasswordEncoder.matches(loginMember.getMemberPwd(), m.getMemberPwd())) {
				// GitHub 연동된 사용자 체크
				if (m.getToken() != null && !m.getToken().isEmpty()) {
					// GitHub 연동된 사용자라면 알림 메시지 설정하고 로그인 폼으로 리다이렉트
					session.setAttribute("loginAlertMsg", "GitHub 계정입니다. GitHub 로그인 버튼을 눌러주세요.");
					return "member/memberLoginForm"; // GitHub 로그인 폼으로 리다이렉트
				}

				// GitHub 연동 안 된 경우 로그인 처리
				session.setAttribute("loginMember", m);
				return "redirect:/"; // 정상 로그인 후 홈 화면으로 리다이렉트

			} else {
				session.setAttribute("loginAlertMsg", "아이디 혹은 비밀번호가 맞지 않습니다. 다시 로그인해주세요");
				return "member/memberLoginForm";
			}
		} else {
			session.setAttribute("loginConfirmMsg", "존재하지 않는 계정입니다. 회원가입 하시겠습니까?");
			return "member/memberLoginForm";
		}
	}

	/**
	 * @param session
	 * @return 로그아웃 컨트롤러
	 */
	@RequestMapping("logout.me")
	public String logoutController(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

	/**
	 * @return 회원가입 페이지 이동 컨트롤러
	 */
	@RequestMapping("enrollForm.me")
	public String memberEnrollFormController() {
		return "member/memberEnrollForm";
	}

	@RequestMapping("check.me")
	@ResponseBody
	public int checkMember(String email) {
		return mService.checkMember(email);
	}
	
	/**
	 * @return 회원가입 페이지 뒤로가기 세션 삭제
	 */
	@RequestMapping("deleteSession")
	@ResponseBody
	public void deleteSession(HttpSession session) {
		session.invalidate();
	}

	/**
	 * @param m
	 * @param request
	 * @throws IOException 회원가입 컨트롤러
	 */
	@RequestMapping("enroll.me")
	public String memberEnrollController(Member m, HttpServletRequest request, HttpSession session) throws IOException {
		// web.xml 파일에 인코딩해주는 코드 추가 후 진행
		// 이 코드와 mapper에서 gitMemberEnroll에 있는 insert 문에 git_username 컬럼에 추가
		Member userName = ((Member) session.getAttribute("gitLoginMember"));
		if (userName != null) {
			m.setGitNick(((Member) session.getAttribute("gitLoginMember")).getGitNick());
		}
		String encodePwd = bcryptPasswordEncoder.encode(m.getMemberPwd());
		m.setMemberPwd(encodePwd);

		int result = mService.memberEnroll(m);
		if (result > 0) {
			session.setAttribute("alertMsg", "회원가입 성공");
			return "redirect:/";
		} else {
			session.setAttribute("alertMsg", "회원가입 실패");
			return "redirect:/";
		}

	}

	@RequestMapping("update.me")
	public String updateMember(Member loginMember,
			@RequestParam(value = "skillNos", required = false) List<Integer> skillNos, HttpServletRequest request,
			HttpSession session) {

		// 1. 회원 정보 수정
		int result = mService.updateMember(loginMember);

		if (result > 0) {
			// 2. 기존 스킬 조회
			List<Map<String, Object>> Memberskills = mService.selectSkills(loginMember);

			// 기존 스킬 번호를 Set에 넣어서 중복 체크
			Set<Integer> existingSkillno = new HashSet<>();
			for (Map<String, Object> skill : Memberskills) {
				// BigDecimal을 integer로 변환
				Integer skillNo = ((BigDecimal) skill.get("SKILL_NO")).intValue();
				existingSkillno.add(skillNo);
			}

			// 3. 새로 추가할 스킬 목록이 있을 경우
			if (skillNos != null && !skillNos.isEmpty()) {
				// 중복되지 않는 스킬만 필터링
				List<Integer> newSkillNos = new ArrayList<>();
				for (Integer skillNo : skillNos) {
					if (!existingSkillno.contains(skillNo)) {
						newSkillNos.add(skillNo);
					}
				}
				// 새로 추가할 스킬이 있다면 insertSkill실행
				if (!newSkillNos.isEmpty()) {
					Map<String, Object> map = new HashMap<>();
					map.put("memberNo", loginMember.getMemberNo());
					map.put("skillList", newSkillNos);

					int updateSkill = mService.insertSkill(map); // 스킬 추가

					if (updateSkill <= 0) {
						request.setAttribute("errorMsg", "스킬 정보 등록에 실패했습니다.");
						return "common/erroPage";
					}
				}
			}

			// 4. 회원 정보를 다시 조회하여 세션에 반영
			Member updateMember = mService.selectMember(loginMember);
			if (updateMember != null) {
				// 5. 회원정보 조회 후 스킬 정보 조회
				List<Map<String, Object>> skills = mService.selectSkills(loginMember);
				session.setAttribute("skills", skills);
				session.setAttribute("loginMember", updateMember);
				return "redirect:/myPage.me";
			} else {
				request.setAttribute("errorMsg", "회원 정보 조회에 실패했습니다.");
				return "/myPage/myPage";
			}

		} else {
			// 회원 정보 수정 실패 시
			request.setAttribute("errorMsg", "회원 정보 수정에 실패 했습니다.");
			return "common/errorPage";
		}
	}

	@RequestMapping("skill.search")
	@ResponseBody
	public List<Map<String, Object>> skillSearch(@RequestParam String keyword) {
		// 대소문자 구분 없이 검색되도록 서비스에 바로 keyword 전달
		return mService.skillSearch(keyword.toLowerCase());
	}

	@RequestMapping("skill.delete")
	@ResponseBody
	public String deleteSkill(@RequestParam int memberNo, @RequestParam int skillNo) {
		int result = mService.deleteMemberSkill(memberNo, skillNo);
		return result > 0 ? "success" : "fail";
	}

	/**
	 * @param emailx`
		// 인증 코드 이메일 발송
		emailService.sendCodeEmail(email, code + "");

		return "success";
	}

	/**
	 * @param email
	 * @param code
	 * @return 인증 코드 검사 컨트롤러
	 */
	@ResponseBody
	@RequestMapping(value = "emailCodeCheck.me", produces = "text/html; charset=utf-8")
	public String verifyCode(String email, String code) {
		// 인증 코드 검증
		boolean isVerified = emailService.verifyCode(email, code);

		if (isVerified) {
			return "Y";
		} else {
			return "N";
		}
	}

	/**
	 * @param code
	 * @param session
	 * @return
	 * @throws IOException
	 * 
	 *                     git 로그인 메소드
	 */
	@RequestMapping("/callback")
	public String getCode(@RequestParam String code, HttpSession session) throws IOException {

		// code를 통해 token 얻어오기
		String token = gService.getToken(code, session);

		// access_token을 이용한 유저 정보 얻어오기
		Member githubInfo = gService.getUserInfo(token); // 토큰으로 회원정보 주회
		session.setAttribute("gitLoginMember", githubInfo); // 토큰으로 조회한 깃 정보를 세션에 등록
		session.setAttribute("token", token);

		Member m = mService.selectGitMember(githubInfo); // 깃 정보로 회원조회
		if (m != null) { // 해당 깃허브 유저가 우리 회원이면
			session.setAttribute("loginMember", m);
			return "redirect:/"; // 성공 시 홈 페이지로 리디렉션
		} else {
			return "member/memberEnrollForm"; // 회원이 아니면 회원가입 폼 이동

		}
	}

	/**
	 * index에서 main 으로 가기위한 메서드.
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("goMainPage.me")
	public String goMainPage(HttpSession session, Model model) {

		// 메인페이지 호출시 채용공고 데드라인에 맞춰 상태(STATUS) 'N' 으로 변경. 따로 사용처는 없음.
		pService.expiredJobPost();

		pService.expiredJobWritePost();

		ArrayList<PostList> previewList = pService.selectJobPostList();

		Member loginMember = (Member) session.getAttribute("loginMember");

		if (loginMember != null) {
			int memberNo = loginMember.getMemberNo();

			// 찜 목록 가져오기
			ArrayList<PostList> jjimList = jService.selectJjimList(memberNo);
			Set<Integer> jjimPostNos = jjimList.stream().map(PostList::getRecruitmentNo).collect(Collectors.toSet());

			// 각 공고에 jjim 여부 설정
			for (PostList post : previewList) {
				if (jjimPostNos.contains(post.getRecruitmentNo())) {
					post.setJjim(true);
				}
			}

			// 프로필 이미지 조회
			String profileFileName = myService.selectLatestProfileByMemberNo(memberNo);

			// null 또는 "null" 문자열 처리
			if (profileFileName == null || "null".equals(profileFileName)) {
				model.addAttribute("profileFileName", null); // JSP에서 empty로 인식 가능
			} else {
				model.addAttribute("profileFileName", "/thepain/resources/img/" + profileFileName);
			}
		}

		model.addAttribute("previewList", previewList);

		return "main";
	}

	@RequestMapping("updateReadme")
	@ResponseBody
	public ResponseEntity<String> updateReadme(@RequestBody Map<String, Object> requestData, HttpSession session) {

		// ✅ 세션에서 로그인된 GitHub 회원 정보 확인
		Member gitLoginMember = (Member) session.getAttribute("gitLoginMember");
		if (gitLoginMember == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("🔒 깃허브 로그인 정보가 없습니다.");
		}

		// ✅ 세션에서 액세스 토큰 확인
		String token = (String) session.getAttribute("token");
		if (token == null || token.isBlank()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("❌ 액세스 토큰이 없습니다.");
		}

		// ✅ 요청 데이터 확인
		String repo = (String) requestData.get("repoName");
		if (repo == null || repo.isBlank()) {
			return ResponseEntity.badRequest().body("❌ repoName이 비어있습니다.");
		}

		Object paramsObj = requestData.get("params");
		if (!(paramsObj instanceof Map)) {
			return ResponseEntity.badRequest().body("❌ params가 유효하지 않습니다.");
		}

		@SuppressWarnings("unchecked")
		Map<String, Object> params = (Map<String, Object>) paramsObj;

		String filePath = (String) params.get("filePath");
		String base64Content = (String) params.get("content");

		if (filePath == null || filePath.isBlank() || base64Content == null || base64Content.isBlank()) {
			return ResponseEntity.badRequest().body("❌ filePath 또는 content가 누락되었거나 비어있습니다.");
		}

		String owner = gitLoginMember.getGitNick();


		try {
			// ✅ SHA 조회
			String sha = gService.getFileSha(session, owner, repo, filePath);
			if (sha == null) {
				return ResponseEntity.badRequest().body("❌ SHA 조회에 실패했습니다.");
			}

			// ✅ 요청 본문 구성
			Map<String, Object> bodyParams = Map.of("message", "Update README", "content", base64Content, "sha", sha);

			Git gitRequest = new Git();
			gitRequest.setToken(token);
			gitRequest.setMethod("PUT");
			gitRequest.setUri("/repos/" + owner + "/" + repo + "/contents/" + filePath);
			gitRequest.setParams(bodyParams);

			boolean result = gService.updateGitFile(gitRequest);

			return result ? ResponseEntity.ok("✅ README 업데이트 성공")
					: ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("❌ README 업데이트 실패");

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("🔥	 서버 오류: " + e.getMessage());
		}
	}

}
