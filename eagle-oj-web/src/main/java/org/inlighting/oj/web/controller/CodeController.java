package org.inlighting.oj.web.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.inlighting.oj.judge.entity.TestCaseRequestEntity;
import org.inlighting.oj.web.controller.exception.WebErrorException;
import org.inlighting.oj.web.controller.format.index.SubmitCodeFormat;
import org.inlighting.oj.web.entity.ResponseEntity;
import org.inlighting.oj.web.judger.JudgerManager;
import org.inlighting.oj.web.judger.JudgerResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Smith
 **/
@RestController
@Validated
@RequestMapping(value = "/code", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CodeController {

    private JudgerManager judgerManager;

    @Autowired
    public void setJudgerManager(JudgerManager judgerManager) {
        this.judgerManager = judgerManager;
    }

    @ApiOperation("测试提交判卷，优先级为0")
    @PostMapping
    public ResponseEntity submitCode(@RequestBody @Valid SubmitCodeFormat format) {
        int length = format.getTestCases().size();
        if (length == 0) {
            throw new WebErrorException("没有测试用例");
        }
        List<TestCaseRequestEntity> testCases = new ArrayList<>(length);
        for(int i=0; i<length; i++) {
            JSONObject obj = format.getTestCases().getJSONObject(i);
            TestCaseRequestEntity testCaseRequestEntity = new TestCaseRequestEntity(obj.getString("stdin"), obj.getString("stdout"));
            testCases.add(testCaseRequestEntity);
        }
        String id = judgerManager.addTask(true, 0, 0, 0,
                format.getLang(), format.getSourceCode(), testCases,
                null, null, null, null);
        return new ResponseEntity(id);
    }

    @ApiOperation("根据id获取判卷任务状况")
    @GetMapping("/{id}")
    public ResponseEntity getStatus(@PathVariable("id") String id) {
        JudgerResult result = judgerManager.getTask(id);
        if (result == null) {
            throw new WebErrorException("不存在此任务");
        }
        return new ResponseEntity(result);
    }
}