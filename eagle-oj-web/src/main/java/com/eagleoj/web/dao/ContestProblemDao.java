package com.eagleoj.web.dao;

import com.eagleoj.web.entity.ContestProblemEntity;
import org.apache.ibatis.session.SqlSession;
import com.eagleoj.web.entity.ContestProblemEntity;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
@Repository
public class ContestProblemDao {

    public boolean add(SqlSession session, ContestProblemEntity entity) {
        return session.insert("contestProblem.insertContestProblem", entity) == 1;
    }

    public ContestProblemEntity getContestProblem(SqlSession sqlSession, ContestProblemEntity entity) {
        return sqlSession.selectOne("contestProblem.getContestProblem", entity);
    }

    public List<HashMap<String, Object>> getContestProblems(SqlSession sqlSession, int cid) {
        return sqlSession.selectList("contestProblem.getContestProblems", cid);
    }

    public List<HashMap<String, Object>> getContestProblemsWithStatus(SqlSession sqlSession, Map<String, Object> condition) {
        return sqlSession.selectList("contestProblem.getContestProblemsWithStatus", condition);
    }

    public boolean updateContestProblem(SqlSession sqlSession, ContestProblemEntity entity) {
        return sqlSession.update("contestProblem.updateContestProblem", entity) == 1;
    }

    public boolean updateContestProblemTimes(SqlSession sqlSession, ContestProblemEntity entity) {
        return sqlSession.update("contestProblem.updateTimes", entity) == 1;
    }

    public boolean deleteContestProblem(SqlSession sqlSession, ContestProblemEntity entity) {
        return sqlSession.delete("contestProblem.deleteContestProblem", entity) == 1;
    }
}