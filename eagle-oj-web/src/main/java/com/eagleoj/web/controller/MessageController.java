package com.eagleoj.web.controller;

import com.github.pagehelper.PageRowBounds;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import com.eagleoj.web.entity.ResponseEntity;
import com.eagleoj.web.security.SessionHelper;
import com.eagleoj.web.service.MessageService;
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
@RequestMapping(value = "/message", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping
    @RequiresAuthentication
    public ResponseEntity getMessage(@RequestParam("page") int page,
                                     @RequestParam("page_size") int pageSize) {
        int owner = SessionHelper.get().getUid();
        PageRowBounds pager = new PageRowBounds(page, pageSize);
        Map<String, Object> map = new HashMap<>(2);
        map.put("data", messageService.getMessage(owner, pager));
        map.put("total", pager.getTotal());
        return new ResponseEntity(map);
    }
}