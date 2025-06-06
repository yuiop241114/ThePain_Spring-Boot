package com.kh.thepain.common.model.service;

import com.kh.thepain.common.model.vo.Git;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Service
public class GitTemplate {

//	public static String getGitHubValue(Git g) {
//
//		// WebClient를 사용해서 Header 세팅
//		WebClient webClient = WebClient.builder().baseUrl("https://api.github.com")
//				.defaultHeader("Accept", "application/vnd.github+json")
//				.defaultHeader("Authorization", "Bearer " + g.getToken())
//				.defaultHeader("X-GitHub-Api-Version", "2022-11-28")
//				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
//
//		// 결과를 받을 response 생성 및 초기화
//		String response = "";
//
//		// 요청 방식에 따라 구분
//		switch (g.getMethod()) {
//		case "GET":
//			// path parameter와 query parameter만 필요하기 때문에 uri에 같이 태운다
//			response = webClient.get().uri(g.getUri()).retrieve().bodyToMono(String.class).block();
//			break;
//		case "POST":
//			// body parameter로 요청값을 넘기기 때문에 bodyValue() 메소드를 사용한다
//			response = webClient.post().uri(g.getUri()).bodyValue(g.getParams()).retrieve().bodyToMono(String.class)
//					.block();
//			break;
//		case "PUT":
//
//			break;
//		case "PATCH":
//			// body parameter로 요청값을 넘기기 때문에 bodyValue() 메소드를 사용한다
//			response = webClient.patch().uri(g.getUri()).bodyValue(g.getParams()).retrieve().bodyToMono(String.class)
//					.block();
//			break;
//		case "DELETE":
//			// path parameter와 query parameter만 필요하기 때문에 uri에 같이 태운다
//			response = webClient.delete().uri(g.getUri()).retrieve().bodyToMono(String.class).block();
//		}
//
//		// JSON값 반환
//		return response;
//
//	}

	public static ArrayList<Object> getGitHubList(Git g) {

		// WebClient를 사용해서 Header 세팅
		WebClient webClient = WebClient.builder().baseUrl("https://api.github.com")
				.defaultHeader("Accept", "application/vnd.github+json")
				.defaultHeader("Authorization", "Bearer " + g.getToken())
				.defaultHeader("X-GitHub-Api-Version", "2022-11-28")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();

		// 결과값을 담을 ArrayList 세팅
		ArrayList<Object> list = new ArrayList<>();

		// uri를 넘겨서 받은 결과값을 ArrayList 형식으로 받고 동기 처리
		list = webClient.get().uri(g.getUri()).retrieve().bodyToMono(ArrayList.class).block();

		// 컨트롤러로 반환
		return list;

	}

	/**
	 * 레파지토리 코드 조회용 템플릿 (코드 조회는 baseUrl이 다름)
	 * 
	 * @param g
	 * @return
	 */
	public static Mono<String> getGitHubCode(Git g) {

		//동기 방식, 리턴값은 String
		/*
		// WebClient를 사용해서 Header 세팅
		WebClient webClient = WebClient.builder()
				// 코드에 접근하는 url을 설정
				.baseUrl("https://raw.githubusercontent.com").defaultHeader("Accept", "application/vnd.github+json")
				.defaultHeader("Authorization", "Bearer " + g.getToken())
				.defaultHeader("X-GitHub-Api-Version", "2022-11-28")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();

		// 결과값을 담을 response
		String response = "";

		// uri를 넘겨서 받은 결과값을 text 형식으로 받고 동기 처리
		response = webClient.get().uri(g.getUri()).retrieve().bodyToMono(String.class).block();

		// 컨트롤러로 반환
		return response;
		*/
		
		//비동기 방식, 리턴 값은 Mono<String>
	    WebClient webClient = WebClient.builder()
	            .baseUrl("https://raw.githubusercontent.com")
	            .defaultHeader("Accept", "application/vnd.github+json")
	            .defaultHeader("Authorization", "Bearer " + g.getToken())
	            .defaultHeader("X-GitHub-Api-Version", "2022-11-28")
	            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
	            .build();

	    return webClient.get()
	            .uri(g.getUri())
	            .retrieve()
	            .bodyToMono(String.class); // ✅ Mono 반환 (비동기)
	}


	/**
	 * @param token
	 * @param owner
	 * @param repoName
	 * @return 래파지토리 readme 파일 조회
	 */
	public Mono<String> getReadme(String token, String owner, String repoName) {
		/* 동기 방식
		try {
			//본인이 아닌 다른 회원의 README 조회
			String response = "" ;
			if(token.equals("")) {
				response = WebClient.builder().baseUrl("https://api.github.com")
						.defaultHeader(HttpHeaders.ACCEPT, "application/vnd.github.v3.raw").build().get()
						.uri("/repos/" + owner + "/" + repoName + "/readme").retrieve()
						.onStatus(status -> status.value() == 404, errorResponse -> {
							// 404 오류 발생 시 마이페이지로 리디렉션
							throw new ResponseStatusException(HttpStatus.FOUND, "Redirecting to MyPage",
									new Exception("Redirecting to /mypage"));
						}).bodyToMono(String.class).block();
			}else {
				//본인의 토큰을 이용해서 README 조회
				response = WebClient.builder().baseUrl("https://api.github.com")
						.defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token)
						.defaultHeader(HttpHeaders.ACCEPT, "application/vnd.github.v3.raw").build().get()
						.uri("/repos/" + owner + "/" + repoName + "/readme").retrieve()
						.onStatus(status -> status.value() == 404, errorResponse -> {
							// 404 오류 발생 시 마이페이지로 리디렉션
							throw new ResponseStatusException(HttpStatus.FOUND, "Redirecting to MyPage",
									new Exception("Redirecting to /mypage"));
						}).bodyToMono(String.class).block();
			}

			return response; // 정상적으로 리드미 파일 내용 반환
		} catch (ResponseStatusException e) {
			// 리디렉션 처리
			if (e.getStatus() == HttpStatus.FOUND) {
				// 리디렉션할 URL을 반환
				return "readme 파일이 없습니다.";
			}
			throw e; // 예외가 다른 오류라면 다시 던짐
		}
		*/

		//비동기 처리 Mono 사용
		//본인이 아닌 다른 회원의 README 조회
		Mono<String> response;
		if(token.equals("")) {
			response = WebClient.builder()
					.baseUrl("https://api.github.com")
					.defaultHeader(HttpHeaders.ACCEPT, "application/vnd.github.v3.raw")
					.build().get()
					.uri("/repos/" + owner + "/" + repoName + "/readme")
					.exchangeToMono(result -> {
						if (result.statusCode().is2xxSuccessful()) {
							return result.bodyToMono(String.class);
						} else if (result.statusCode().value() == 404) {
							// 원하는 문자열 직접 반환
							return Mono.just("README 파일이 존재하지 않습니다.");
						} else {
							// 그 외 상태 코드면 오류로 처리
							return result.createException().flatMap(Mono::error);
						}
					});
		}else {
			//본인의 토큰을 이용해서 README 조회
			response = WebClient.builder()
					.baseUrl("https://api.github.com")
					.defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token)
					.defaultHeader(HttpHeaders.ACCEPT, "application/vnd.github.v3.raw")
					.build().get()
					.uri("/repos/" + owner + "/" + repoName + "/readme")
					.exchangeToMono(result -> {
						if (result.statusCode().is2xxSuccessful()) {
							return result.bodyToMono(String.class);
						} else if (result.statusCode().value() == 404) {
							// 원하는 문자열 직접 반환
							return Mono.just("README 파일이 존재하지 않습니다.");
						} else {
							// 그 외 상태 코드면 오류로 처리
							return result.createException().flatMap(Mono::error);
						}
					});
		}

		return response; // 정상적으로 리드미 파일 내용 반환
	}
	
	/**
	 * 해당 레포지토리 소스 코드를 위한 json 형태 정보 가져오는 메소드
	 */
	public String getRepoSource(String token, String owner, String repoName) {
		 String response = WebClient.builder()
		            .baseUrl("https://api.github.com")
		            .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token) // 인증 토큰
		            .defaultHeader(HttpHeaders.ACCEPT, "application/vnd.github.v3+json") // GitHub API 권장 헤더
		            .build()
		            .get()
		            .uri(uriBuilder -> uriBuilder
		            		.path("/users/{owner}/repos")
		                    //.path("/repos/{owner}/{repo}/contents")
		                    .build(owner)) // 올바른 URI 설정
		            .retrieve()
		            .bodyToMono(String.class) // JSON 응답을 String으로 처리
		            .block(); // 동기 처리
		 return response;
	}
//	public static String getGitHubFiles(Git g) {
//	    // WebClient를 사용해서 Header 세팅
//	    WebClient webClient = WebClient.builder()
//	            .baseUrl("https://api.github.com")
//	            .defaultHeader("Accept", "application/vnd.github+json")
//	            .defaultHeader("Authorization", "Bearer " + g.getToken())
//	            .defaultHeader("X-GitHub-Api-Version", "2022-11-28")
//	            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//	            .build();
//
//	    // 결과값을 담을 response
//	    String response = "";
//
//	    try {
//	        // 레포지토리의 파일 목록을 가져오는 API 호출
//	        response = webClient.get()
//	                .uri(uriBuilder -> uriBuilder.path("/repos/{owner}/{repo}/contents")
//	                        .build(g.getUri(), g.getRepoName()))  // owner와 repo 이름을 URI에 삽입
//	                .retrieve()
//	                .bodyToMono(String.class)
//	                .block();
//
//	        System.out.println("레포지토리 파일 목록 조회 성공: " + response);
//
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	        return "Error fetching repository files.";
//	    }
//
//	    return response;  // 파일 목록을 반환
//	}
//
//	public String getFileContent(Git g, String filePath) {
//	    WebClient webClient = WebClient.builder()
//	            .baseUrl("https://api.github.com/repos/" + g.getUri() + "/" + g.getRepoName() + "/contents/" + filePath)
//	            .defaultHeader("Accept", "application/vnd.github.v3.raw")  // raw 형식으로 파일 데이터 요청
//	            .defaultHeader("Authorization", "Bearer " + g.getToken())
//	            .defaultHeader("X-GitHub-Api-Version", "2022-11-28")
//	            .build();
//
//	    String response = "";
//
//	    try {
//	        // 파일 내용을 가져오는 GET 요청
//	        response = webClient.get().retrieve().bodyToMono(String.class).block();
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	    }
//
//	    return response;
//	}
	

//	public static ArrayList<Object> getProjectFiles(Git g) {
//	    // WebClient를 사용해서 Header 세팅
//	    WebClient webClient = WebClient.builder().baseUrl("https://api.github.com")
//	            .defaultHeader("Accept", "application/vnd.github+json")
//	            .defaultHeader("Authorization", "Bearer " + g.getToken())
//	            .defaultHeader("X-GitHub-Api-Version", "2022-11-28")
//	            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
//	
//	    // 결과값을 담을 ArrayList 세팅
//	    ArrayList<Object> fileList = new ArrayList<>();
//	
//	    // URI로 파일 목록을 받아오고 동기 처리
//	    fileList = webClient.get().uri(g.getUri()).retrieve().bodyToMono(ArrayList.class).block();
//	
//	    // 파일 목록 반환
//	    return fileList;
//	}
//	
//	public static String getFileContentFromUrl(String downloadUrl, String token) {
//	    // WebClient 설정
//	    WebClient webClient = WebClient.builder()
//	            .baseUrl("https://api.github.com") // GitHub API Base URL 설정
//	            .defaultHeader("Accept", "application/vnd.github+json") // Accept 헤더 설정
//	            .defaultHeader("Authorization", "Bearer " + token) // 인증을 위한 Bearer Token 설정
//	            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE) // Content-Type 설정
//	            .build();
//
//	    // 다운로드 URL을 사용하여 파일 내용 가져오기
//	    String content = webClient.get() // GET 요청
//	            .uri(downloadUrl) // 다운로드 URL 사용
//	            .retrieve() // 응답을 받음
//	            .bodyToMono(String.class) // 응답 Body를 String으로 변환
//	            .block(); // 동기 방식으로 처리
//
//	    return content; // 파일 내용 반환
//	}

}
