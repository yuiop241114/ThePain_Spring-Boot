package com.kh.thepain.jjim.model.service;

import com.kh.thepain.jjim.jjimMapper.JjimMapper;
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
    private JjimMapper jjimMapper;

    @Autowired
    private SqlSessionTemplate sqlSession;

    public boolean isAlreadyJjim(int memberNo, int postNo) {
        return jDao.isAlreadyJjim(sqlSession, memberNo, postNo) > 0;
    }

    public int addJjim(int memberNo, int postNo) {
        return jDao.addJjim(sqlSession, memberNo, postNo);
    }

    public int removeJjim(int memberNo, int postNo) {
        return jDao.removeJjim(sqlSession, memberNo, postNo);
    }

    /* Spring Legacy Project에서 Mybatis 사용 방법
    public ArrayList<PostList> selectJjimList(int memberNo) {
        return jDao.selectJjimList(sqlSession, memberNo);
    }
    */

    //Spring boot 에서 Mybatis 사용 방법
    public ArrayList<PostList> selectJjimList(int memberNo) {
        return jjimMapper.selectJjimList(memberNo);
    }

    public ArrayList<Integer> jjimListByMember(int memberNo) {
        return jDao.selectJjimPostNos(sqlSession, memberNo);
    }


}