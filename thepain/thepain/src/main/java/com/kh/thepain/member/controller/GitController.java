package com.kh.thepain.member.controller;//package com.kh.thepain.member.controller;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.Map;
//
//import javax.servlet.http.HttpSession;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//@Controller
//public class GitController {
//	
//    private static final String CLIENT_ID = "Ov23liydoA44JqnHWLOb"; // GitHub에서 받은 클라이언트 아이디
//    private static final String CLIENT_SECRET = "abd0691fe63d78e15b81a5d4df6d4d20862aff32"; // GitHub에서 받은 클라이언트 시크릿
//
//    @GetMapping("callback")
//    public String getCode(@RequestParam String code, HttpSession session) throws IOException {
//
//        URL url = new URL("https://github.com/login/oauth/access_token");
//
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setDoInput(true);
//        conn.setDoOutput(true);
//        conn.setRequestMethod("POST");
//        conn.setRequestProperty("Accept", "application/json");
//        conn.setRequestProperty("User-Agent", "Mozilla/5.0");
//
//        // POST 요청에 client_id, client_secret, code를 포함시킵니다.
//        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()))) {
//            String postData = "client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET + "&code=" + code;
//            bw.write(postData);
//            bw.flush();
//        }
//
//        int responseCode = conn.getResponseCode();
//        String responseData = getResponse(conn, responseCode);
//        conn.disconnect();
//
//        if (responseCode == 200) {
//            String accessToken = extractAccessToken(responseData);
//            session.setAttribute("access_token", accessToken);
//            return "redirect:/"; // 성공 시 홈 페이지로 리디렉션
//        } else {
//            return "redirect:/error"; // 실패 시 오류 페이지로 리디렉션
//        }
//    }
//
//    private String extractAccessToken(String responseData) throws IOException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        
//        // TypeReference를 사용하여 정확한 타입으로 매핑
//        Map<String, String> map = objectMapper.readValue(responseData, new TypeReference<Map<String, String>>() {});
//        System.out.println(map.get("access_token"));
//        return map.get("access_token");
//    }
//
//    private String getResponse(HttpURLConnection conn, int responseCode) throws IOException {
//        StringBuilder sb = new StringBuilder();
//        if (responseCode == 200) {
//            try (InputStream is = conn.getInputStream();
//                 BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
//                for (String line = br.readLine(); line != null; line = br.readLine()) {
//                    sb.append(line);
//                }
//            }
//        }
//        return sb.toString();
//    }
//
//}
