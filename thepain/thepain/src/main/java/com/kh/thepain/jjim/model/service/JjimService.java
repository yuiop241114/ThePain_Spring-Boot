package com.kh.thepain.jjim.model.service;

import com.kh.thepain.jjim.jjimMapper.jjimMapper;
import com.kh.thepain.jjim.model.dao.JjimDao;
import com.kh.thepain.postList.model.vo.PostList;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JjimService {

    @Autowired
    private JjimDao jDao;

    @Autowired
    private jjimMapper jMapper;

//    @Autowired
//    private SqlSessionTemplate sqlSession;

    public boolean isAlreadyJjim(int memberNo, int postNo) {
        return jDao.isAlreadyJjim(memberNo, postNo) > 0;
    }

    public int addJjim(int memberNo, int postNo) {
        return jDao.addJjim( memberNo, postNo);
    }

    public int removeJjim(int memberNo, int postNo) {
        return jDao.removeJjim(memberNo, postNo);
    }

    public ArrayList<PostList> selectJjimList(int memberNo) {
        return jDao.selectJjimList(memberNo);
    }

    public ArrayList<Integer> jjimListByMember(int memberNo) {
        return jDao.selectJjimPostNos(memberNo);
    }


}