package com.kh.thepain.apiDevel.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.thepain.apiDevel.jwtConfig.JwtUtil;
import com.kh.thepain.apiDevel.model.service.APIDevelService;
import com.kh.thepain.apiDevel.model.vo.ApiLogin;
import com.kh.thepain.apiDevel.model.vo.ApiMember;
import com.kh.thepain.common.model.vo.Attachment;
import com.kh.thepain.member.model.vo.Member;
import com.kh.thepain.myPage.controller.cvListController;
import com.kh.thepain.myPage.model.service.MypageService;
import com.kh.thepain.postList.controller.Post;
import com.kh.thepain.postList.model.service.PostListServiceImpl;
import com.kh.thepain.postList.model.vo.PostList;
import com.kh.thepain.postList.model.vo.PostWrite;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.Date;

@RestController
@RequestMapping("/postApi")
public class APIDevelPostController {

    @Autowired
    private Post pController;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private APIDevelService apiService;
    @Autowired
    private cvListController cvController;

    @Autowired
    private PostListServiceImpl pService;
    @Autowired
    private MypageService mpService;


    /**
     * 로그인시 토큰 발생 메소드
     * @param apiLogin
     * @return
     */
    @Operation(summary = "로그인시 토큰을 발행",description = "기본 토큰만 발행합니다. 만료시간은 1시간입니다")
    @PostMapping(value="/loginToken")
    public String loginToken(@RequestBody ApiLogin apiLogin) {
        //입력 받은 아이디 및 비밀번호 인증
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(apiLogin.getEmail(), apiLogin.getPassword())
        );
        //아이디로 토큰 발행 및 반환
        return jwtUtil.generateToken(authentication.getName());
    }

    /**
     * 회사 로고(이미지) 맟 채용공고 내용을 입력 받아서 채용 공고 등록
     * @param bearerToken
     * @param img
     * @param postInfo
     * @param session
     * @return
     */
    @Operation(summary = "채용 공고 등록", description = "회사 로고(이미지) 맟 채용공고 내용을 입력 받아서 채용 공고 등록합니다. 채용담당자 회원만 가능합니다")
    @PostMapping(value="/insertPost")
    public String insertPost(
            @RequestHeader("Authorization") String bearerToken,
            @RequestParam("img") MultipartFile img,
            @RequestParam("pw") String postInfo,
            HttpSession session){
        //이미지 및 채용공고 정보 받아서 등록
        String token = bearerToken.substring(7); //헤더에서 Token 추출
        String email = jwtUtil.getUsernameFromToken(token); //Token에 있는 아이디 추출
        //JSON 형태로 온 데이터 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        PostWrite pw = null;
        try {
            pw = objectMapper.readValue(postInfo, PostWrite.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        //채용담당자인지 확인
        ApiMember memberInfo = apiService.selectApiMember(email);
        if(memberInfo.getGitUserName() != null){
            return "채용담당자 회원만 채용 공고 등록 가능합니다";
        }else{
            //회사 로고 및 채용 공고 정보를 받아서 등록
            Attachment fileVo = new Attachment();
            if (!img.getOriginalFilename().equals("")) {
                // 원본명 저장
                fileVo.setFileOriginName(img.getOriginalFilename());
                // 변경명 저장
                // saveFile 메소드를 만들어서 이름 변경 및 원하는 경로에 첨부파일 저장
                fileVo.setFileEditName(cvController.saveFile(img, "postImg"));
                // img를 저장할 회원번호
                fileVo.setMemberNo(memberInfo.getMemberNo());
                // fileTyep 지정
                fileVo.setFileType("img");
                // 저장 경로 작성
                fileVo.setFileRoot("/postImg/");
            }

            // 급여 유효성 검사(salaryMax 가 salaryMin 보다 작을떄.)
            if (pw.getSalaryMin() > pw.getSalaryMax()) {
                return "급여 선택에 오류가 있습니다. 최대 급여가 최소 급여보다 작습니다.";
            }

            Date today = new Date();
            boolean result11 = pw.getDeadLine().after(today);
            if (!result11) {
                return "입력한 날짜가 유효하지 않습니다. 다시 입력해주세요.";
            }

            PostList pl = new PostList();
            pl.setMemberNo(memberInfo.getMemberNo());
            pl.setEnterpriseNo(memberInfo.getEnterpriseNo());

            pl.setDeadLine(pw.getDeadLine());

            // job_post 작성 폼.
            int result1 = pService.insertJobPost(pl);

            // job_post 에 insert 가 잘 됬으면,
            if (result1 > 0) {

                // job_post의 최신 글 번호 조회
                int postNo = pService.postNo(memberInfo.getMemberNo());
                pw.setPostNo(postNo);

                // db에 img 관련 정보 저장
                fileVo.setRecruitmentNo(pw.getPostNo());
                int result12 = mpService.resumeInsert(fileVo);

                if (result12 > 0) {
                    // job_write_post 를 insert
                    int result = pService.insertJob(pw);
                    if (result > 0) {
                        return "채용공고 등록 성공";
                    } else {
                        return "공고 내용 저장 실패";
                    }//job_write_post 를 insert

                }else {
                    return "공고 이미지 저장 실패";
                }//db에 img 관련 정보 저장

            } else {
                return "공고 작성 실패";
            }//job_post 에 insert
        }
    }

}
