package com.kh.thepain.common.model.vo;

import lombok.*;

import java.util.Map;

/**
 * GithubTemplate 호출을 위해 필요한 매개변수 객체VO
 * 
 * @author Target
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Git {
	
    private String token;    // access_token 값
    private String method;   // 요청방식(GET, POST, PUT, PATCH, DELETE)
    private String uri;      // Base Url 뒤에 추가할 URI(/XX/XX 형식으로 들어감)
    private Map<String, Object> params;  // Body에 요청할 매개변수들
    
    private String repoName;   // 레포지토리 이름 추가
    private String filePath;   // 파일 경로 추가
    private String content;    // 수정할 내용 추가 (base64 인코딩된 내용)
}