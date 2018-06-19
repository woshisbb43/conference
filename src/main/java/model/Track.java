package model;

public class Track {
    private Session morningSession;
    private Session afterNoonSession;

    public Session getMorningSession() {
        return morningSession;
    }

    public Track setMorningSession(Session morningSession) {
        this.morningSession = morningSession;
        return this;
    }

    public Session getAfterNoonSession() {
        return afterNoonSession;
    }

    public Track setAfterNoonSession(Session afterNoonSession) {
        this.afterNoonSession = afterNoonSession;
        return this;
    }
}
