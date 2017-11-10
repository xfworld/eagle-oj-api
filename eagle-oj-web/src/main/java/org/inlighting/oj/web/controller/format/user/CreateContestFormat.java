package org.inlighting.oj.web.controller.format.user;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

/**
 * @author Smith
 **/
public class CreateContestFormat {

    @NotNull
    @Length(min = 1, max = 50)
    private String name;

    @NotNull
    @Length(max = 100)
    private String slogan;

    @NotNull
    @Length(max = 500)
    private String description;

    @JSONField(name = "start_time")
    @NotNull
    private long startTime;

    @JSONField(name = "end_time")
    @NotNull
    private long endTime;

    @JSONField(name = "total_time")
    @NotNull
    private long totalTime;

    @Length(max = 6)
    private String password;

    @NotNull
    @Range(max = 4)
    private int type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
