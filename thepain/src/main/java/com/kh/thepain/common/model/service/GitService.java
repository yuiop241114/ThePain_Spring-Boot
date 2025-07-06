package com.kh.thepain.common.model.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.thepain.common.model.vo.Git;
import com.kh.thepain.member.model.vo.Member;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.stream.Collectors;

@PropertySource("classpath:config/git.properties")
@Service
public class GitService {

	@Value("${git.id}")
	private String gitId;

	@Value("${git.secret}")
	private String gitSecret;

	@Autowired
	private WebClient webClient;


	/**
	 * 깃허브 파일 업데이트 요청
	 * 
	 * @param git Git 요청 객체 (token, method, uri, params 포함)
	 * @return 성공하면 true, 실패하면 false
	 */
	public boolean updateGitFile(Git git) {
		try {



			String fullUrl = git.getUri().startsWith("http") ? git.getUri() : "https://api.github.com" + git.getUri();

			ResponseEntity<String> response = webClient.method(HttpMethod.PUT).uri(fullUrl)
					.header(HttpHeaders.AUTHORIZATION, "Bearer " + git.getToken())
					.header(HttpHeaders.ACCEPT, "application/vnd.github+json").bodyValue(git.getParams()).retrieve()
					.toEntity(String.class).block();

			if (response != null) {

				if (response.getStatusCode().is2xxSuccessful()) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} catch (WebClientResponseException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * GitHub API 호출하여 특정 파일의 SHA 값을 조회
	 * 
	 * @param token    GitHub OAuth 토큰
	 * @param owner    저장소 오너(유저명)
	 * @param repo     저장소 이름
	 * @param filePath 파일 경로 (예: README.md)
	 * @return SHA 문자열, 실패 시 null 반환
	 */
	public String getFileSha(HttpSession session, String owner, String repo, String filePath) {
		try {
			String token = (String) session.getAttribute("access_token");
			if (token == null || token.isEmpty()) {
				return null;
			}

			// 파일 경로 인코딩
			String encodedFilePath = Arrays.stream(filePath.split("/"))
					.map(s -> URLEncoder.encode(s, StandardCharsets.UTF_8)).collect(Collectors.joining("/"));

			String url = "https://api.github.com/repos/" + owner + "/" + repo + "/contents/" + encodedFilePath;

			// GitHub API 호출 (GET)
			String response = webClient.get().uri(url).header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
					.header(HttpHeaders.ACCEPT, "application/vnd.github+json").retrieve().bodyToMono(String.class)
					.block();

			if (response == null || response.isBlank()) {
				return null;
			}

			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode root = objectMapper.readTree(response);

			JsonNode shaNode = root.get("sha");
			if (shaNode == null || shaNode.isNull()) {
				return null;
			}

			return shaNode.asText();

		} catch (WebClientResponseException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @param code
	 * @return git 접속 토큰 가져오는 메소드
	 */
	public String getToken(String code, HttpSession session) {
		String url = "https://github.com/login/oauth/access_token";

		String response = webClient.post().uri(url)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
				.body(BodyInserters.fromFormData("client_id", gitId).with("client_secret", gitSecret).with("code", code))
				.retrieve()
				.bodyToMono(String.class).block();

		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode;

		String token = "";

		try {
			jsonNode = objectMapper.readTree(response);
			token = jsonNode.get("access_token").asText(); // ✅ access_token 추출
			// ✅ access_token을 세션에 저장
			session.setAttribute("access_token", token);

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return token;
	}

	/**
	 * @param token
	 * @return git 유저 정보 조회 메소드
	 */
	public Member getUserInfo(String token) {
		//로그인 시 사용하기 때문에 동기 방식으로 처리
		String response = webClient.get().uri("https://api.github.com/user")
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.header(HttpHeaders.ACCEPT, "application/vnd.github+json").retrieve().bodyToMono(String.class).block();

		ObjectMapper objecMapper = new ObjectMapper();
		JsonNode jsonNode = null;
		Member m = new Member();

		// login => nickname
		try {
			jsonNode = objecMapper.readTree(response);
			m.setGitNick(jsonNode.get("login").asText());
			m.setToken(jsonNode.get("id").asText());
			m.setProfile(jsonNode.get("avatar_url").asText());
			m.setGitRepos(jsonNode.get("repos_url").asText()); // GitNick으로 되어 있던 코드 수정
			m.setGitUrl(jsonNode.get("html_url").asText());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return m;
	}

	/**
	 * @param markdown
	 * @return git readme 마크다운 변환 메소드
	 */
	public String convertMarkdownToHtml(String markdown) {
		Parser parser = Parser.builder().build();
		org.commonmark.node.Node document = parser.parse(markdown);
		HtmlRenderer renderer = HtmlRenderer.builder().build();
		return renderer.render(document); // HTML로 변환된 결과
	}

	/**
	 * GitHub에서 파일 내용 가져오기
	 */
	public String getFileContent(String owner, String repoName, String filePath) {
		String url = "https://api.github.com/repos/" + owner + "/" + repoName + "/contents/" + filePath;

		// GitHub API에서 파일 내용을 가져오기 위한 WebClient 호출
		String response = webClient.get().uri(url).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.retrieve().bodyToMono(String.class).block();

		// 파일의 내용 반환 (Base64로 인코딩된 내용을 디코딩하여 반환)
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(response);
			String content = jsonNode.get("content").asText();
			byte[] decodedBytes = Base64.getDecoder().decode(content);
			return new String(decodedBytes, StandardCharsets.UTF_8);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return null; // 오류 시 null 반환
	}

	/**
	 * GitHub API를 사용해 특정 사용자의 프로필 정보를 가져오는 메소드
	 * 
	 * @param username 조회할 사용자 이름
	 * @return Member 사용자 정보 객체
	 */
	public Member getUserProfile(String username) {
		// WebClient로 GitHub API 호출
		String response = webClient.get().uri("https://api.github.com/users/" + username) // 특정 사용자 조회
				.header(HttpHeaders.ACCEPT, "application/vnd.github+json").retrieve().bodyToMono(String.class).block();

		// JSON 파싱
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = null;
		Member m = new Member();

		try {
			jsonNode = objectMapper.readTree(response);

			// JSON 데이터를 Member 객체에 매핑
			m.setGitNick(jsonNode.get("login").asText());
			m.setProfile(jsonNode.get("avatar_url").asText());
			m.setGitUrl(jsonNode.get("html_url").asText());

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return m;
	}
}