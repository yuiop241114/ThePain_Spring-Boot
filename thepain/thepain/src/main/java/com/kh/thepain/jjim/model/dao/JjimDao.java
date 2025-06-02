package com.kh.thepain.jjim.model.dao;

import com.kh.thepain.postList.model.vo.PostList;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;

@Repository
public class JjimDao {

    public int isAlreadyJjim(SqlSession sqlSession, int memberNo, int postNo) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("memberNo", memberNo);
        map.put("postNo", postNo);
        return sqlSession.selectOne("jjimMapper.isAlreadyJjim", map);
    }

    public int addJjim(SqlSession sqlSession, int memberNo, int postNo) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("memberNo", memberNo);
        map.put("postNo", postNo);
        return sqlSession.insert("jjimMapper.addJjim", map);
    }

    public int removeJjim(SqlSession sqlSession, int memberNo, int postNo) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("memberNo", memberNo);
        map.put("postNo", postNo);
        return sqlSession.delete("jjimMapper.removeJjim", map);
    }
    public ArrayList<PostList> selectJjimList(SqlSessionTemplate sqlSession, int memberNo) {
        return (ArrayList)sqlSession.selectList("jjimMapper.selectJjimList", memberNo);
    }
    public ArrayList<Integer> selectJjimPostNos(SqlSession sqlSession, int memberNo) {
        return (ArrayList)sqlSession.selectList("jjimMapper.selectJjimPostNos", memberNo);
    }
    
    
    
    
    
    
    
    
    
    
    
}