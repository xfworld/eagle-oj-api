package org.inlighting.oj.web.postman.task;

/**
 * @author Smith
 **/
public class PullGroupUserIntoContestTask extends BaseTask {
    private int gid;

    private String groupName;

    private int cid;

    private String contestName;

    public PullGroupUserIntoContestTask(int gid, String groupName, int cid, String contestName) {
        this.gid = gid;
        this.groupName = groupName;
        this.cid = cid;
        this.contestName = contestName;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getContestName() {
        return contestName;
    }

    public void setContestName(String contestName) {
        this.contestName = contestName;
    }
}