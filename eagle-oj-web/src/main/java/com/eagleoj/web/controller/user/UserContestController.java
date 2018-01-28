package com.eagleoj.web.controller.user;

import com.eagleoj.web.DefaultConfig;
import com.eagleoj.web.controller.exception.WebErrorException;
import com.eagleoj.web.controller.format.user.*;
import com.eagleoj.web.entity.*;
import com.eagleoj.web.security.SessionHelper;
import com.eagleoj.web.service.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Smith
 **/
@RestController
@Validated
@RequestMapping(value = "/user/contest", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserContestController {

    private ProblemService problemService;

    private ContestService contestService;

    private ContestUserService contestUserInfoService;

    private ContestProblemService contestProblemService;

    private LeaderboardService leaderboardService;

    private UserService userService;

    @Autowired
    public void setLeaderboardService(LeaderboardService leaderboardService) {
        this.leaderboardService = leaderboardService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setContestProblemService(ContestProblemService contestProblemService) {
        this.contestProblemService = contestProblemService;
    }

    @Autowired
    public void setProblemService(ProblemService problemService) {
        this.problemService = problemService;
    }

    @Autowired
    public void setContestService(ContestService contestService) {
        this.contestService = contestService;
    }

    @Autowired
    public void setContestUserInfoService(ContestUserService contestUserInfoService) {
        this.contestUserInfoService = contestUserInfoService;
    }





    @ApiOperation("参加比赛")
    @PostMapping("/{cid}/enter")
    public ResponseEntity enterContest(@PathVariable("cid") int cid,
                                       @RequestBody @Valid EnterContestFormat format) {
        // 判断是否已经加入比赛
        int uid = SessionHelper.get().getUid();
        ContestUserEntity contestUserInfoEntity = contestUserInfoService.get(cid, uid);
        if (contestUserInfoEntity != null) {
            return new ResponseEntity("你已经加入比赛了");
        }

        // 校验密码
        ContestEntity contestEntity = contestService.getContest(cid);
        if (contestEntity.getPassword() != null) {
            String password = contestEntity.getPassword();
            if (! password.equals(format.getPassword())) {
                throw new WebErrorException("密码错误");
            }
        }

        // 加入比赛
        if (! contestUserInfoService.save(cid, uid)) {
            throw new WebErrorException("加入比赛失败");
        }

        // 添加加入比赛的记录
        UserEntity userEntity = new UserEntity();
        userEntity.setContestTimes(1);
        userService.updateUserTimes(uid, userEntity);
        return new ResponseEntity("加入比赛成功");
    }

    @ApiOperation("获取本人在某个比赛中的状况+题目列表")
    @GetMapping("/{cid}/data")
    public ResponseEntity getContestUserInfo(@PathVariable("cid") int cid) {
        int uid = SessionHelper.get().getUid();
        ContestUserEntity info = contestUserInfoService.get(cid, uid);
        if (info == null) {
            throw new WebErrorException("你不在此比赛中");
        }
        Map<String, Object> map = new HashMap<>(3);
        map.put("meta", leaderboardService.getUserMetaInContest(uid, cid));
        map.put("user", info);
        map.put("problems", contestProblemService.listContestProblems(cid, info.getUid()));
        return new ResponseEntity(map);
    }

   /* @ApiOperation("向比赛添加题目")
    @PostMapping("/{cid}/problem")
    public ResponseEntity addContestProblem(@PathVariable("cid") int cid,
                                            @RequestBody @Valid AddContestProblemFormat format) {
        // 校验数据
        int pid = format.getPid();
        int score = format.getScore();
        int displayId = format.getDisplayId();
        if (pid < 1 || score < 1 || displayId < 1) {
            throw new WebErrorException("数据格式错误");
        }

        // 校验比赛和权限
        ContestEntity contestEntity = contestService.getContest(cid);
        //havePermission(contestEntity);

        ContestProblemEntity contestProblemEntity = contestProblemService.getContestProblem(pid, cid);
        if (contestProblemEntity != null) {
            throw new WebErrorException("题目已经被添加");
        }

        ProblemEntity problemEntity = problemService.getProblem(pid);
        if (problemEntity == null) {
            throw new WebErrorException("此题不存在");
        }
        if (problemEntity.getStatus() != 2) {
            throw new WebErrorException("此题不能被添加");
        }

        if (contestProblemService.displayIdIsDuplicate(cid, displayId)) {
            throw new WebErrorException("显示题号重复");
        }

        if (! contestProblemService.save(pid, cid, displayId, score)) {
            throw new WebErrorException("题目添加失败");
        }

        return new ResponseEntity("题目添加成功");
    }*/

    @ApiOperation("获取比赛的题目列表")
    @GetMapping("/{cid}/problem")
    public ResponseEntity getContestProblems(@PathVariable("cid") int cid) {
        ContestEntity contestEntity = contestService.getContest(cid);
        // havePermission(contestEntity);
        return new ResponseEntity(contestProblemService.listContestProblems(cid));
    }

    /*@ApiOperation("更新比赛题目的分值和题号")
    @PutMapping("/{cid}/problem/{pid}")
    public ResponseEntity updateContestProblem(@PathVariable("cid") int cid,
                                               @PathVariable("pid") int pid,
                                               @RequestBody @Valid UpdateContestProblemFormat format) {
        ContestEntity contestEntity = contestService.getContest(cid);
        //havePermission(contestEntity);
        if (contestProblemService.displayIdIsDuplicate(cid, format.getDisplayId())) {
            throw new WebErrorException("显示题号重复");
        }

        if (! contestProblemService.updateContestProblemInfo(cid, pid, format.getDisplayId(), format.getScore())) {
            throw new WebErrorException("更新失败");
        }
        return new ResponseEntity("更新成功");
    }*/

    @ApiOperation("删除比赛的题目")
    @DeleteMapping("/{cid}/problem/{pid}")
    public ResponseEntity deleteContestProblem(@PathVariable("cid") int cid,
                                               @PathVariable("pid") int pid) {
        ContestEntity contestEntity = contestService.getContest(cid);
        //havePermission(contestEntity);
        if (! contestProblemService.deleteByCidPid(cid, pid)) {
            throw new WebErrorException("删除失败");
        }
        return new ResponseEntity("删除成功");
    }



}
