package com.kh.thepain.apiDevel.mapper;

import com.kh.thepain.apiDevel.model.vo.ApiMember;
import com.kh.thepain.apiDevel.model.vo.JobPost;
import com.kh.thepain.apiDevel.model.vo.Skills;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface APIDevelMapper {

    /**
     * 특정 회사 채용공고 전체 조회
     * @param companyName
     * @return
     */
    List<JobPost> postList(String companyName);

    /**
     * 특정 회원 기본정보 조회
     * @param memberId
     * @return
     */
    ApiMember selectApiMember(String memberId);

    /**
     * 특정 회원 기술 스택 조회
     * @param memberNo
     * @return
     */
    List<Skills> selectSkills(int memberNo);
}
