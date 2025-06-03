package com.kh.thepain.jjim.jjimMapper;

import com.kh.thepain.postList.model.vo.PostList;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface jjimMapper {
    ArrayList<PostList> selectJjimList(int memberNo);
}
