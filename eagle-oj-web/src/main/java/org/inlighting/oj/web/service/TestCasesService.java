package org.inlighting.oj.web.service;

import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.dao.TestCasesDao;
import org.inlighting.oj.web.entity.TestCaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Smith
 **/
@Service
public class TestCasesService {

    private final SqlSession sqlSession;

    private TestCasesDao testCaseDao;

    public TestCasesService(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Autowired
    public void setTestCaseDao(TestCasesDao testCaseDao) {
        this.testCaseDao = testCaseDao;
    }

    public int addTestCase(int pid, String stdin, String stdout, int strength) {
        // 添加一个TestCase
        TestCaseEntity testCaseEntity = new TestCaseEntity();
        testCaseEntity.setPid(pid);
        testCaseEntity.setStdin(stdin);
        testCaseEntity.setStdout(stdout);
        testCaseEntity.setStrength(strength);
        testCaseEntity.setCreateTime(System.currentTimeMillis());
        boolean flag = testCaseDao.addTestCase(sqlSession,testCaseEntity);
        return flag ? testCaseEntity.getTid() : 0;
    }

    public int getTestCaseCountByPid(int pid) {
        return testCaseDao.getTestCaseCountByPid(sqlSession,pid);
    }

    public List<TestCaseEntity> getAllTestCasesByPid(int pid){
        //通过pid来查询所有的TestCase
        return testCaseDao.getAllTestCasesByPid(sqlSession,pid);
    }

    public boolean updateTestCaseByTidPid(int tid, int pid, String stdin, String stdout, int strength) {
        //通过TestCase的ID来修改TestCase
        TestCaseEntity testCaseEntity = new TestCaseEntity();
        testCaseEntity.setTid(tid);
        testCaseEntity.setPid(pid);
        testCaseEntity.setStdin(stdin);
        testCaseEntity.setStdout(stdout);
        testCaseEntity.setStrength(strength);
        return testCaseDao.updateTestCaseByTidPid(sqlSession, testCaseEntity);
    }

    public boolean deleteTestCaseByTid(int tid) {
        //通过TestCase的ID删除TestCase
        return testCaseDao.deleteOneTestCaseByTid(sqlSession,tid);
    }
}
