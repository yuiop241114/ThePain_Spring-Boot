package com.kh.thepain.apiDevel.mapper;

import com.kh.thepain.apiDevel.model.vo.JobPost;
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
}
