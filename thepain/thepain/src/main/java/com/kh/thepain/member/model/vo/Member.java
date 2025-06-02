package com.kh.thepain.member.model.vo;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Member {
	private int memberNo;
	private int enterpriseNo;
	private int recruitmentNo;
	private String email;
	private String memberPwd;
	private String memberName;
	private String phone;
	private String skill;
	private String corporation; // 채용담당자 회사 
	private String token; // 깃허브 회원 고유 id
	private String GitNick; // DB X
	private String profile; // DB X
	private String gitRepos; // DB X
	private String gitUrl;
	private int career;
	
	public Member(String profile) {
		this.profile = profile;
	}
	
}
