package com.eagleoj.web.dao;

import com.eagleoj.judge.LanguageEnum;
import com.eagleoj.judge.ResultEnum;
import com.eagleoj.web.entity.SubmissionEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
@Repository
public interface SubmissionMapper {

    int save(SubmissionEntity submissionEntity);

    int countByPid(int pid);

    List<Map<String, Object>> listSubmissionsByOwnerPidCid(@Param("owner")Integer owner,
                                                           @Param("pid")Integer pid,
                                                           @Param("cid")Integer cid,
                                                           @Param("lang")LanguageEnum lang,
                                                           @Param("status")ResultEnum status,
                                                           @Param("sort") Integer sort,
                                                           @Param("nickname") String nickname);
}
