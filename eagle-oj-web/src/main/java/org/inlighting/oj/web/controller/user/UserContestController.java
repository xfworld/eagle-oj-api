package org.inlighting.oj.web.controller.user;

import com.alibaba.fastjson.JSONArray;
import io.swagger.annotations.ApiOperation;
import org.inlighting.oj.web.controller.format.user.CreateContestFormat;
import org.inlighting.oj.web.controller.format.user.EnterContestFormat;
import org.inlighting.oj.web.entity.ContestEntity;
import org.inlighting.oj.web.entity.ResponseEntity;
import org.inlighting.oj.web.security.SessionHelper;
import org.inlighting.oj.web.service.ContestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Smith
 **/
@RestController
@Validated
@RequestMapping(value = "/user/contest", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserContestController {

    private ContestService contestService;

    @Autowired
    public void setContestService(ContestService contestService) {
        this.contestService = contestService;
    }

    @ApiOperation("创建比赛")
    @PostMapping
    public ResponseEntity createContest(@RequestBody @Valid CreateContestFormat format) {
        // todo
        int owner = SessionHelper.get().getUid();
        JSONArray moderator = new JSONArray();
        int official = 0;

        long currentTime = System.currentTimeMillis();
        // valid time
        if (format.getStartTime() <= currentTime) {
            throw new RuntimeException("非法开始时间");
        }

        // endTime为0代表永远不结束
        if (format.getEndTime()!=0 || format.getStartTime() >= format.getEndTime()) {
            throw new RuntimeException("非法结束时间");
        }

        // 限时模式
        if (format.getType()==1 || format.getType()==3) {
            if (format.getTotalTime() == 0) {
                throw new RuntimeException("非法总时间");
            }
        } else {
            if (format.getTotalTime() != 0) {
                throw new RuntimeException("非法总时间");
            }
        }

        if (! contestService.addContest(format.getName(), owner, moderator,format.getSlogan(),
                format.getDescription(), format.getStartTime(), format.getEndTime(),
                format.getTotalTime(), format.getPassword(), official, format.getType(), currentTime)) {
            throw new RuntimeException("比赛创建失败");
        }
        return new ResponseEntity("比赛创建成功");
    }

    @ApiOperation("参加比赛")
    @PostMapping("/enter")
    public ResponseEntity enterContest(@RequestBody @Valid EnterContestFormat format) {
        ContestEntity contestEntity = contestService.getContestByCid(format.getCid());
        if (contestEntity.getPassword() != format.getPassword()) {
            throw new RuntimeException("密码错误");
        }

        // 加入比赛
        return null;
    }
}
