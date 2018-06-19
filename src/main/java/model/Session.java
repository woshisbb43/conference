package model;

import java.util.List;

public class Session {

    private List<Talk> talks;
    private boolean morningSession;
    private Integer sessionLength;
    private Integer leftLength;

    public Integer getLeftLength() {
        return leftLength;
    }

    public Session setLeftLength(Integer leftLength) {
        this.leftLength = leftLength;
        return this;
    }

    public Integer getSessionLength() {
        return sessionLength;
    }

    public Session setSessionLength(Integer sessionLength) {
        this.sessionLength = sessionLength;
        return this;
    }

    public List<Talk> getTalks() {
        return talks;
    }

    public Session setTalks(List<Talk> talks) {
        this.talks = talks;
        return this;
    }

    public boolean isMorningSession() {
        return morningSession;
    }

    public Session setMorningSession(boolean morningSession) {
        this.morningSession = morningSession;
        return this;
    }
}
