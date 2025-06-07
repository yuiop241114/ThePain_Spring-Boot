package com.kh.thepain.member.memberMapper;

import com.kh.thepain.member.model.vo.Member;
import com.kh.thepain.postList.model.vo.Apply;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MemberMapper {

    Member loginMember(Member m);

    int selectDupEnterprise(String enterpriseName);

    int insertEnterprise(String enterpriseName);

    int selectEnterprise(String enterpriseName);

    int memberEnroll(Member m);

    int checkMember(String email);

    int gitMemberEnroll(Member m);

    int userMemberEnroll(Member m);

    Member selectGitMember(Member githubInfo);

    Member selectMember(Member loginMember);

    int updateMember(Member loginMember);

    int insertSkill(Map<String, Object> map);

    List<Map<String, Object>> selectSkills(Member loginMember);

    List<Map<String, Object>> skillSearch(String englishKeyword);

    int deleteMemberSkill(Map<String, Object> map);

    List<Apply> selectApplyList(Member loginMember);

    Member resumeMemberInfo(Member memberInfo);
}
