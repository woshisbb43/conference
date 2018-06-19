package model;

public class Talk {
    private String topic;
    private Integer duration;
    private boolean isLighting;

    public String getTopic() {
        return topic;
    }

    public Talk setTopic(String topic) {
        this.topic = topic;
        return this;
    }

    public Integer getDuration() {
        return duration;
    }

    public Talk setDuration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public boolean isLighting() {
        return isLighting;
    }

    public Talk setLighting(boolean lighting) {
        isLighting = lighting;
        return this;
    }
}
