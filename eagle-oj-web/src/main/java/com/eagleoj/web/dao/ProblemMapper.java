package com.eagleoj.web.dao;

import com.eagleoj.web.entity.ProblemEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
@Repository
public interface ProblemMapper {
    int save(ProblemEntity problemEntity);

    int updateByPid(@Param("pid") int pid, @Param("data") ProblemEntity data);

    ProblemEntity getByPid(int pid);

    List<Map<String, Object>> listProblemTagsByPid(int pid);

    List<ProblemEntity> listAll();

    List<Map<String, Object>> listShared(@Param("uid") Integer uid,
                                         @Param("difficult") Integer difficult,
                                         @Param("tag") String tag);

    List<ProblemEntity> listProblemsForContest(int uid);

    List<ProblemEntity> listAuditing();

    List<Map<String, Object>> listSharedWithUserStatus(@Param("difficult") int difficult,
                                                       @Param("tag") String tag, @Param("uid") int uid);

    List<ProblemEntity> listByUid(int uid);

    int refuseByPid(int pid);

    int acceptByPid(int pid);

    int deleteByPid(int pid);

}