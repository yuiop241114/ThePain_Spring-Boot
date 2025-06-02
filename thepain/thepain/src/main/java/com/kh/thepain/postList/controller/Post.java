package com.kh.thepain.postList.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.thepain.common.model.service.GitService;
import com.kh.thepain.common.model.service.GitTemplate;
import com.kh.thepain.common.model.vo.Attachment;
import com.kh.thepain.common.model.vo.Git;
import com.kh.thepain.jjim.model.service.JjimService;
import com.kh.thepain.member.model.vo.Member;
import com.kh.thepain.myPage.controller.cvListController;
import com.kh.thepain.myPage.model.service.MypageService;
import com.kh.thepain.postList.model.service.PostListServiceImpl;
import com.kh.thepain.postList.model.vo.Apply;
import com.kh.thepain.postList.model.vo.PostList;
import com.kh.thepain.postList.model.vo.PostWrite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class Post {

	@Autowired
	private PostListServiceImpl pService;

	@Autowired
	private GitTemplate gTemplate;

	@Autowired
	private GitService gService;

	@Autowired
	private MypageService mpService;

	@Autowired
	private JjimService jService;

	@Autowired
	private cvListController cvController;

	@RequestMapping("jobPostList.pl")
	public String jobPostList(Model model, HttpSession session) {
		ArrayList<PostList> list = pService.selectJobPostList();
		model.addAttribute("list", list);

		Member loginMember = (Member) session.getAttribute("loginMember");
		if (loginMember != null) {
			int memberNo = loginMember.getMemberNo();
			ArrayList<Integer> jjimList = jService.jjimListByMember(memberNo);
			model.addAttribute("jjimList", jjimList);
		}

		return "/jobPost/jobPostList";
	}

	/**
	 * 지원현황.jsp
	 * 
	 * @return
	 */
	@RequestMapping("applyState.pl")
	public String moveToApplyState(HttpSession session, Model model) {

		// 로그인 여부 확인
		Member loginMember = (Member) session.getAttribute("loginMember");

		// 로그인되지 않은 상태일 경우
		if (loginMember == null) {
			session.setAttribute("alertMsg", "로그인 후 이용할 수 있습니다.");
			return "redirect:/"; // 로그인 페이지로 리다이렉트
		}

		// 로그인된 상태에서만 처리
		int memberNo = loginMember.getMemberNo();
		List<Apply> applyList = pService.selectApplyList(memberNo);
		model.addAttribute("applyList", applyList);

		return "/myPage/applyView"; // 지원현황 페이지로 이동
	}

	/**
	 * 디테일뷰. 리스트에서 한개의 공고글 클릭시 디테일 뷰로 넘어감
	 * 
	 * @param postNo
	 * @param model
	 * @return
	 */
	@RequestMapping("detail.pl")
	public String selectJobDetail(@RequestParam("no") int postNo, Model model) {
		// db에 있는 해당 공고글에 대한 정보를 가져온다.
		
		//db에 있는 해당 공고글에 대한 img 파일을 불러온다. 
		
		model.addAttribute("post", pService.selectJob(postNo));
		model.addAttribute("img", mpService.selectImg(postNo) );
		return "jobPost/jobPostDetail"; // detail JSP 경로
	}

	/**
	 * 글 작성 폼. 화면단
	 * 
	 * @return
	 */
	@RequestMapping("postWriter.pl")
	public String jobPostWrite(HttpSession session) {
		Member loginMember = (Member) session.getAttribute("loginMember");
		if (loginMember == null || loginMember.getEnterpriseNo() == 0) {
			session.setAttribute("alertMsg", "채용담당자 로그인이 필요합니다.");
			return "redirect:/"; // 리다이렉트
		}
		loginMember.setCorporation(pService.selectCompanyName(loginMember.getEnterpriseNo()));
		session.setAttribute("loginMember", loginMember);
		return "jobPost/jobPostWrite"; // 로그인 된 경우 글쓰기 화면 이동
	}

	/**
	 * 글 작성 화면단 에서 '글 작성하기' 버튼 클릭했을때 작동.
	 * 
	 * @param pw
	 * @param session
	 * @param model
	 * @param rttr
	 * @return
	 */
	@RequestMapping("insertJob.pl")
	public String insertJob(MultipartFile companyLogo, PostWrite pw, HttpSession session, Model model,
			RedirectAttributes rttr) {

		Attachment fileVo = new Attachment();
		if (!companyLogo.getOriginalFilename().equals("")) {
			// 원본명 저장
			fileVo.setFileOriginName(companyLogo.getOriginalFilename());
			// 변경명 저장
			// saveFile 메소드를 만들어서 이름 변경 및 원하는 경로에 첨부파일 저장
			fileVo.setFileEditName(cvController.saveFile(session, companyLogo, "img"));
			// img를 저장할 회원번호
			fileVo.setMemberNo(((Member) session.getAttribute("loginMember")).getMemberNo());
			// fileTyep 지정
			fileVo.setFileType("img");
			// 저장 경로 작성
			fileVo.setFileRoot("/resources/img/");
		}

		// 급여 유효성 검사(salaryMax 가 salaryMin 보다 작을떄.)
		if (pw.getSalaryMin() > pw.getSalaryMax()) {
			session.setAttribute("alertMsg", "급여 선택에 오류가 있습니다. 최대 급여가 최소 급여보다 작습니다.");
			model.addAttribute("post", pw);
			return "jobPost/jobPostWrite"; // 글쓰기 화면으로 다시 돌아가!
		}

		Date today = new Date();
		boolean result11 = pw.getDeadLine().after(today);
		if (!result11) {
			session.setAttribute("alertMsg", "입력한 날짜가 유효하지 않습니다. 다시 입력해주세요. ");
			return "jobPost/jobPostWrite";
		}

		Member loginMember = (Member) session.getAttribute("loginMember");

		PostList pl = new PostList();
		pl.setMemberNo(loginMember.getMemberNo());
		pl.setEnterpriseNo(loginMember.getEnterpriseNo());

		pl.setDeadLine(pw.getDeadLine());

		// job_post 작성 폼.
		int result1 = pService.insertJobPost(pl);

		// job_post 에 insert 가 잘 됬으면,
		if (result1 > 0) {

			// job_post의 최신 글 넘버를 가져온다.
			int postNo = pService.postNo(loginMember.getMemberNo());
			pw.setPostNo(postNo);

			
			
			
			// db에 img 관련 정보 저장
			fileVo.setRecruitmentNo(pw.getPostNo());
			int result12 = mpService.resumeInsert(fileVo);
			
			
			if (result12 > 0) {

				// job_write_post 를 insert 하자.
				int result = pService.insertJob(pw);
				if (result > 0) {
					return "redirect:/jobPostList.pl";
				} else {
					session.setAttribute("alertMsg", "공고 작성 실패");
					model.addAttribute("post", pw);
					return "jobPost/jobPostWrite";
				}

			}else {
				session.setAttribute("alertMsg", "공고 작성 실패");
				model.addAttribute("post", pw);
				return "jobPost/jobPostWrite";
			}

		} else {
			session.setAttribute("alertMsg", "공고 작성 실패");
			model.addAttribute("post", pw);
			return "jobPost/jobPostWrite";
		}

	}

	@RequestMapping("deletePost.pl")
	public String deletePost(@RequestParam("no") int postNo, HttpSession session) {
		int result = pService.deleteJobPost(postNo);

		if (result > 0) {
			return "redirect:/jobPostList.pl";
		} else {
			session.setAttribute("alertMsg", "삭제에 실패했습니다.");
			return "redirect:/detail.pl?no=" + postNo;
		}
	}

	/**
	 * 채용공고 글 수정하기 버튼 클릭시 , 수정 폼 이동.
	 * 
	 * @param postNo
	 * @param model
	 * @return
	 */
	@RequestMapping("updateForm.pl")
	public String updateForm(@RequestParam("no") int postNo, Model model) {
		PostWrite pw = pService.selectJob(postNo);
		model.addAttribute("post", pw);
		return "jobPost/jobPostUpdate"; // 수정용 JSP 페이지
	}

	/**
	 * 채용 공고 글 수정 완료 버튼 클릭시. update 쿼리 실행.
	 * 
	 * @param pw
	 * @param session
	 * @return
	 */
	@RequestMapping("updatePost.pl")
	public String updatePost(PostWrite pw, HttpSession session) {
		int result = pService.updateJobPost(pw);

		if (result > 0) {
			return "redirect:/detail.pl?no=" + pw.getPostNo();
		} else {
			session.setAttribute("alertMsg", "수정에 실패했습니다.");
			return "redirect:/updateForm.pl?no=" + pw.getPostNo();
		}
	}

	@RequestMapping("applyinsert.at")
	public String applyinsert(@RequestParam("postNo") int postNo, @RequestParam("memberNo") int memberNo,
			HttpSession session, Model model) {

		// 1. 지원 공고 정보 가져오기
		PostWrite post = pService.selectJob(postNo);
		model.addAttribute("post", post);

		// 2. GitHub 정보 조회 및 레포지토리 정보 준비
		Member githubInfo = gService.getUserInfo((String) session.getAttribute("token"));

		Git g = new Git();
		g.setMethod("GET");
		g.setToken((String) session.getAttribute("token"));
		g.setUri(githubInfo.getGitRepos()); // gitService 수정 후 url 저장 위치 변경

		/*
		ObjectMapper objecMapper = new ObjectMapper();
		try {
			JsonNode repoTotal = objecMapper.readTree(gTemplate.getGitHubCode(g));

			ArrayList<String> readme = new ArrayList<>();
			ArrayList<String> repoTitle = new ArrayList<>();
			ArrayList<String> repoLink = new ArrayList<>();

			for (int i = 0; i < repoTotal.size(); i++) {
				String readmeContent = gTemplate.getReadme((String) session.getAttribute("token"),
						repoTotal.get(i).get("owner").get("login").asText(), repoTotal.get(i).get("name").asText());

				String a = gService.convertMarkdownToHtml(readmeContent);
				readme.add(a);
				repoTitle.add(repoTotal.get(i).get("name").asText());
				repoLink.add(repoTotal.get(i).get("html_url").asText());

			}

			model.addAttribute("readme", readme);
			model.addAttribute("repoTitle", repoTitle);
			model.addAttribute("repoLink", repoLink);
			model.addAttribute("gitUrl", githubInfo.getGitUrl());
			model.addAttribute("repoCount", repoTitle.size());

			ArrayList<Attachment> list = pService.selectResume(memberNo);
			model.addAttribute("list", list);

		} catch (Exception e) {
			e.printStackTrace();
		}
	*/
		//readme 조회 코드 비동기 전환 시 코드(Mono 방식)
		gTemplate.getGitHubCode(g).subscribe(repo -> {
			ObjectMapper objecMapper = new ObjectMapper();
			try {
				JsonNode repoTotal = objecMapper.readTree(repo);

				ArrayList<String> readme = new ArrayList<>();
				ArrayList<String> repoTitle = new ArrayList<>();
				ArrayList<String> repoLink = new ArrayList<>();

				for (int i = 0; i < repoTotal.size(); i++) {
					String readmeContent = gTemplate.getReadme((String) session.getAttribute("token"),
							repoTotal.get(i).get("owner").get("login").asText(), repoTotal.get(i).get("name").asText());

					String a = gService.convertMarkdownToHtml(readmeContent);
					readme.add(a);
					repoTitle.add(repoTotal.get(i).get("name").asText());
					repoLink.add(repoTotal.get(i).get("html_url").asText());

				}

				model.addAttribute("readme", readme);
				model.addAttribute("repoTitle", repoTitle);
				model.addAttribute("repoLink", repoLink);
				model.addAttribute("gitUrl", githubInfo.getGitUrl());
				model.addAttribute("repoCount", repoTitle.size());

				ArrayList<Attachment> list = pService.selectResume(memberNo);
				model.addAttribute("list", list);

			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return "jobPost/applyWrite";
	}

	/**
	 * 채용공고 '지원하기 버튼 클릭시'
	 * 
	 * @param postNo
	 * @param memberNo
	 * @param readme
	 * @param resumeNo
	 * @param session
	 * @return
	 */
	@RequestMapping("insert.at")
	public String insertArticle(@RequestParam("postNo") int postNo, @RequestParam("memberNo") int memberNo,
			@RequestParam(value = "readmeSelect", required = false, defaultValue = "") String readme,
			@RequestParam(value = "resumeSelect", required = false, defaultValue = "0") int resumeNo,
			HttpSession session) {
		// Apply 객체 생성
		Apply apply = new Apply();
		apply.setRmNo(postNo);
		apply.setMemberNo(memberNo);
		apply.setFileNo(resumeNo);

		// Case 1: 깃허브 레포지토리 지원
		if (readme != null && !readme.trim().isEmpty()) {
			apply.setRead(readme);
			apply.setFileNo(resumeNo); // 선택적으로 이력서도 같이 담을 수 있음
		}

		// Case 2: 이력서 또는 깃허브로 이미 지원했는지 확인
		if (resumeNo > 0) {
			// 이력서로 지원 여부 확인
			int count = mpService.selectApplyCount(apply);
			if (count > 0) {
				session.setAttribute("alertMsg", "이미 이력서로 지원한 공고입니다.!!!!");
				return "redirect:/detail.pl?no=" + postNo;
			}
		}
		// Case 2-1: 깃허브만 선택한 경우 중복 확인
		else if (readme != null && !readme.trim().isEmpty()) {
			int count = mpService.selectApplyCount(apply);
			if (count > 0) {
				session.setAttribute("alertMsg", "이미 레포지토리로 지원한 공고입니다.!!!!");
				return "redirect:/detail.pl?no=" + postNo;
			}
		}
		// Case 3: 아무것도 선택하지 않은 경우
		else {
			session.setAttribute("alertMsg", "레포지토리 또는 이력서 중 하나는 반드시 선택해야 합니다.");
			return "redirect:/detail.pl?no=" + postNo;
		}

		// 5. 중복 지원 방지
		int applyMember = pService.selectApply(apply);
		if (applyMember > 0) {
			session.setAttribute("alertMsg", "이미 지원한 공고입니다.");
			return "redirect:/detail.pl?no=" + postNo;
		}

		// 6. 지원 정보 등록
		// fileNo가 0이면 외래키 오류가 나므로 null로 변환
		if (apply.getFileNo() == 0) {
			apply.setFileNo(null); // fileNo가 0일 경우 null로 설정
		}


		int result = pService.insertApply(apply);
		if (result > 0) {
			session.setAttribute("alertMsg", "공고 지원 완료.");
		} else {
			session.setAttribute("alertMsg", "공고 지원 실패.");
		}

		return "redirect:/detail.pl?no=" + postNo;
	}

}
