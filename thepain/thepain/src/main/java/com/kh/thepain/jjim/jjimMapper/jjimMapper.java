package com.kh.thepain.jjim.jjimMapper;

import com.kh.thepain.postList.model.vo.PostList;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface jjimMapper {
    ArrayList<PostList> selectJjimList(int memberNo);

    ArrayList<Integer> selectJjimPostNos(int memberNo);

    int removeJjim(Map<String, Integer> map);

    int addJjim(Map<String, Integer> map);

    int isAlreadyJjim(Map<String, Integer> map);
}
