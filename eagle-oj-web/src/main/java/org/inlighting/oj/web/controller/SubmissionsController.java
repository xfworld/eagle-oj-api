package org.inlighting.oj.web.controller;

import com.github.pagehelper.PageRowBounds;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.inlighting.oj.web.entity.ResponseEntity;
import org.inlighting.oj.web.security.SessionHelper;
import org.inlighting.oj.web.service.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Smith
 **/
@RestController
@Validated
@RequestMapping(value = "/submissions", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SubmissionsController {

    private SubmissionService submissionService;

    @Autowired
    public void setSubmissionService(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @ApiOperation("获取用户的代码记录")
    @RequiresAuthentication
    @GetMapping
    public ResponseEntity getUserSubmissions(@RequestParam("cid") int cid,
                                             @RequestParam("pid") int pid,
                                             @RequestParam("page") int page,
                                             @RequestParam("page_size") int pageSize) {
        PageRowBounds pager = new PageRowBounds(page, pageSize);
        int owner = SessionHelper.get().getUid();
        Map<String, Object> data = new HashMap<>(2);
        data.put("data", submissionService.getSubmissions(owner, cid, pid, pager));
        data.put("total", pager.getTotal());
        return new ResponseEntity(data);
    }
}