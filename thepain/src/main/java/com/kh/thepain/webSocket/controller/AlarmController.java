package com.kh.thepain.webSocket.controller;

import com.kh.thepain.member.model.vo.Member;
import com.kh.thepain.postList.model.service.PostListServiceImpl;
import com.kh.thepain.postList.model.vo.PostList;
import com.kh.thepain.webSocket.model.service.AlarmService;
import com.kh.thepain.webSocket.model.vo.Alarm;
import com.kh.thepain.webSocket.model.vo.AlarmHistory;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Hidden
@Controller
public class AlarmController {

    @Autowired
    private AlarmService aService;
    
    @Autowired
    private PostListServiceImpl pService;
   

// =========================================    ✅ [1] 특정 사용자에게 알림 전송 + 이력 저장
    @ResponseBody
    @PostMapping(value = "/sendAlarm", produces = "text/plain;charset=UTF-8")
    public String sendAlarm(
    		@RequestParam("postNo") int postNo,
            @RequestParam("alarmNo") int alarmNo,
            HttpSession session) {
        Member loginMember = (Member) session.getAttribute("loginMember");
        
        // 알림 템플릿 조회(알람 번호, 알람 내용을 가져온다. )
        Alarm alarmTemplate = aService.selectAlarmByNo(alarmNo);
        String postTitle = pService.alarmPostTitle(postNo);
        String content = "‘" + postTitle + "’ " + alarmTemplate.getAlarmContent();
        
        //수신자, 발신자(글 작성자) 도 수소문해서 알아냈다. 
        int receiverNo = loginMember.getMemberNo();
        int senderNo = pService.senderNo(postNo);

        
    

        
        // 알림 이력 저장 (AlarmHistory 입력)
        AlarmHistory history = new AlarmHistory();
        history.setSenderNo(senderNo);
        history.setReceiverNo(receiverNo);
        history.setAlarmNo(alarmNo);
        history.setAlarmHistoryContent(content);
        
        boolean alreadySent = aService.isAlreadySentAlarm(history);
        
        if(alreadySent) {
        	return "already sent"; //이미 알림을 보낸것을 확인했다. continue 로 insert 구문 하지않고 빠져나가겠다. 오바.
        }
        
        // 3. DB 저장
        int result = aService.insertAlarmHistory(history);

        // 4. WebSocket으로 전송
        if (result > 0) {
            Socket.sendToUser(receiverNo, content);
            return content;
        } else {
            return "알림 저장 실패";
        }
    }
//========================================================================
    
//===============================================================alarmDeadLine(‘000000’ 공고의 마감일이 임박했어요)
    @PostMapping(value = "/alarmDeadLine.pl", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ArrayList<String> sendAlarmByDeadline(@RequestParam("alarmNo") int alarmNo,
                                                 HttpSession session) {
    	
    	//로그인 정보를 담는다. 
        Member loginMember = (Member) session.getAttribute("loginMember");


        if (loginMember == null) {
            return new ArrayList<>(); // 그냥 빈 리스트 리턴해서 오류 방지
        }
        
        //db에 있는 알람 서식을 불러온다. 
        Alarm alarmTemplate = aService.selectAlarmByNo(alarmNo);

        int receiverNo = loginMember.getMemberNo();

        //채용공고 글 작성자의 memberNo를 알아야 한다. 기필코.
        	ArrayList<Integer> recruitmentNo =  pService.selectPostWriter(receiverNo);
        //조건에 맞는 마감 공고 회사이름들을 리스트로 담아온다. 
        //company_name, memberNo 호출 메서드.	
    	ArrayList<PostList> companyList = pService.alarmDeadLine(recruitmentNo); 

    	//회사명+알람서식 을 합쳐 문장을 만든다. 
    	ArrayList<String> sentMessages = new ArrayList<>();
    	for (PostList company : companyList) {
    	    // 1. company_name과 member_no를 꺼낸다.
    	    String content = "‘" + company.getCompanyName() + "’ " + alarmTemplate.getAlarmContent();
    	    int postNo = company.getRecruitmentNo();  // 공고 번호
    	    

    	    AlarmHistory history = new AlarmHistory();
    	    history.setReceiverNo(receiverNo);
    	    history.setSenderNo(company.getMemberNo());
    	    history.setAlarmNo(alarmNo);
    	    history.setAlarmHistoryContent(content);
    	    boolean alreadySent = aService.isAlreadySentAlarm(history);
            
            if(alreadySent) {
            	continue;//이미 알림을 보낸것을 확인했다. continue 로 insert 구문 하지않고 빠져나가겠다. 오바.
            }
    	    int result = aService.insertAlarmHistory(history);
    	    if (result > 0) {
    	        Socket.sendToUser(receiverNo, content);
    	        sentMessages.add(content);
    	    }
    	}
    	//배열 return
        return sentMessages;
    }
//=============================================================================

    
//===================================================================postClosed(00000’ 공고가 마감되었습니다.)
    @ResponseBody
    @PostMapping(value = "/postClosed.pl", produces = "application/json;charset=UTF-8")
    public ArrayList<String> postClosed(@RequestParam("alarmNo") int alarmNo, HttpSession session) {
    	
        // 1. 로그인 사용자 세션에서 가져오기
        Member loginMember = (Member) session.getAttribute("loginMember");
        if (loginMember == null) {
            return new ArrayList<>(); // 그냥 빈 리스트 리턴해서 오류 방지
        }
        // 2. 알림 템플릿 내용 불러오기 (예: ‘000000’ 공고가 마감되었습니다. ...)
        //db에 있는 알람 서식을 불러온다. 
        Alarm alarmTemplate = aService.selectAlarmByNo(alarmNo);
        int receiverNo = loginMember.getMemberNo();
        //채용공고 글 작성자의 memberNo를 알아야 한다. 기필코.
        	ArrayList<Integer> postWriter =  pService.selectPostWriter(receiverNo);
        //조건에 맞는 마감 공고 회사이름들을 리스트로 담아온다. 
        //company_name, memberNo 호출 메서드.	
    	ArrayList<PostList> companyList = pService.selectClosedPosts(postWriter); 
    	

        
        
        
        
        // 4. 전송한 메시지를 담을 리스트
        ArrayList<String> sentMessages = new ArrayList<>();

        // 5. 마감된 공고 목록 반복문
        for (PostList post : companyList) {

            // 5-1. 중복 알림 여부 체크 (같은 sender + receiver + alarmNo 조합이 존재하면 생략)
        	
           

            // 5-2. 알림 내용 구성 (공고 회사명 + 템플릿 내용)
            String content = "‘" + post.getCompanyName() + "’ 공고가 마감되었습니다. 다음 기회를 기대해 주세요.";
            // 5-3. 알림 이력 객체 구성
            AlarmHistory history = new AlarmHistory();
            history.setReceiverNo(receiverNo);               // 알림 받는 사람
            history.setSenderNo(post.getMemberNo());         // 공고 작성자
            history.setAlarmNo(alarmNo);                     // 어떤 알림인지 구분
            history.setAlarmHistoryContent(content);         // 실제 보여줄 알림 메시지

            boolean alreadySent = aService.isAlreadySentAlarm(history);
            
            if(alreadySent) {
            	continue; //이미 알림을 보낸것을 확인했다. continue 로 insert 구문 하지않고 빠져나가겠다. 오바.
            }
            
            
            
            // 5-4. 알림 이력 DB에 저장
            int result = aService.insertAlarmHistory(history);

            // 5-5. 저장이 성공하면 소켓으로 실시간 전송 + li에 추가
            if (result > 0) {
            	sentMessages.add(content);                   // 화면 출력용 메시지 배열에 추가
                Socket.sendToUser(receiverNo, content);      // 웹소켓 전송
            }
        }
        // 6. 전송된 메시지들을 리턴 (ajax 응답)
        return sentMessages;
    }

  //================================================================= 
//================================================================= 로그인한 사용자의 알림 목록 조회
    @ResponseBody
    @GetMapping("/alarmList")
    public ArrayList<AlarmHistory> getAlarmList(HttpSession session) {

        Member loginMember = (Member) session.getAttribute("loginMember");
        if (loginMember == null) {
            return new ArrayList<>();
        }

        int memberNo = loginMember.getMemberNo();

        return aService.selectAlarmListByReceiver(memberNo);
    }
    
    
    
    
    
    
    //====================================================
    
  //====================================================
    
    /**
     * 알람 네번쨰 문구 기능 구현 메서드
     * @param recruitmentNo
     * @param memberNo
     * @return
     */
    
    @ResponseBody
    @GetMapping("/alarmApplyState.pl")
    public String alarmApplyState(@RequestParam("recruitmentNo") int recruitmentNo,
                                  @RequestParam("memberNo") int memberNo) {

        // 1. READ 상태 'Y'로 업데이트
        int updateResult = pService.updateReadStatus(recruitmentNo, memberNo);
        if (updateResult == 0) {
            return "fail: read update";
        }
        
        // 2. 회사명 + 작성자 번호 조회 (job_write_post, job_post JOIN)
        PostList postInfo = pService.selectCompany(recruitmentNo);
        if (postInfo == null) {
            return "fail: post info";
        }
        
        
        // 3. 알림 템플릿 번호 4번 내용 불러오기 ("기업에서 귀하의 프로필을 확인했습니다.")
        Alarm alarmTemplate = aService.selectAlarmByNo(4);
        if (alarmTemplate == null) {
            return "fail: no alarm template";
        }
        
        

        // 4. 알림 메시지 조립
        String content = "‘" + postInfo.getCompanyName() + "’ " + alarmTemplate.getAlarmContent();
        int senderNo = pService.senderNo(recruitmentNo);

        // 5. 알림 이력 구성 및 저장
        AlarmHistory history = new AlarmHistory();
        history.setReceiverNo(memberNo);             // 지원자
        history.setSenderNo(postInfo.getMemberNo()); // 채용담당자
        history.setAlarmNo(4);                       // ALARM_NO = 4
        history.setAlarmHistoryContent(content);
        
        boolean alreadySent = aService.isAlreadySentAlarm(history);
        
        if(alreadySent) {
        	return "already sent"; //이미 알림을 보낸것을 확인했다. continue 로 insert 구문 하지않고 빠져나가겠다. 오바.
        }
        
        int result = aService.insertAlarmHistory(history);
        
        
        if (result > 0) {
            Socket.sendToUser(memberNo, content);  // 웹소켓 알림 전송
            return content; // 성공 시 실제 전송된 메시지 반환
        } else {
            return "fail: insert";
        }
    }

    
}
