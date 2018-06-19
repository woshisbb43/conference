package service;

import model.Session;
import model.Talk;
import model.Track;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ArrangeConferenceTrackService {


    public List<Track> scheduleTracks(List<Session> sessions) {
        Integer numberOfTracks = sessions.size()/2;
        return allocateSessionsToTracks(sessions, numberOfTracks);

    }

    public List<Session> scheduleSessions(List<Talk> talkList){

        talkList = talkList.stream().filter(talk -> talk.getDuration() <= ServiceConfig.AFTERNOON_SESSION_LENGTH).collect(Collectors.toList());
        Integer potentialNumOfTracks = calculateNumOfTracks(talkList);
        return allocateTalksToSessions(talkList, potentialNumOfTracks);

    }

    private List<Track> allocateSessionsToTracks(List<Session> sessions, Integer numberOfTracks) {

        List<Track> trackList = generateEmptyTracks(numberOfTracks);
        List<Session> morningSessions = sessions.stream()
                .filter(Session::isMorningSession)
                .collect(Collectors.toList());
        List<Session> afternoonSessions = sessions.stream()
                .filter(session -> !session.isMorningSession())
                .collect(Collectors.toList());

        for (Track track : trackList){
            if (morningSessions.isEmpty() || afternoonSessions.isEmpty())
                break;
            track.setMorningSession(morningSessions.get(0))
                    .setAfterNoonSession(afternoonSessions.get(0));
            morningSessions.remove(0);
            afternoonSessions.remove(0);
        }

        return trackList;
    }



    private List<Track> generateEmptyTracks(Integer numberOfTracks) {
        List<Track> trackList = new ArrayList<>();
        IntStream.range(0, numberOfTracks).forEach(n -> {
            trackList.add(new Track());
        });

        return trackList;
    }


    private List<Session> allocateTalksToSessions(List<Talk> talkList, Integer potentialNumOfTracks) {

        List<Session> sessionList = generateEmptySessionList(potentialNumOfTracks);

        talkList.sort(Comparator.comparingDouble(Talk::getDuration).reversed());

        sessionList.sort(Comparator.comparingDouble(Session::getLeftLength).reversed());

        while (!talkList.isEmpty() && sessionList.get(0).getLeftLength() >= talkList.get(0).getDuration()) {

            sessionList.sort(Comparator.comparingDouble(Session::getLeftLength).reversed());

            sessionList.get(0)
                    .setTalks(addTalkToSession(sessionList, talkList))
                    .setLeftLength(resetLeftLength(sessionList));
            talkList.remove(0);

            sessionList.sort(Comparator.comparingDouble(Session::getLeftLength).reversed());
        }

        return sessionList;

    }

    private Integer resetLeftLength(List<Session> sessionList) {
        return sessionList.get(0).getLeftLength() - sessionList.get(0).getTalks().get(0).getDuration();
    }

    private List<Talk> addTalkToSession(List<Session> sessionList, List<Talk> talkList) {
        List<Talk> addedTalks = sessionList.get(0).getTalks();
        addedTalks.add(0,talkList.get(0));
        return addedTalks;
    }

    private List<Session> generateEmptySessionList(Integer potentialNumOfTracks) {

        List<Session> sessions = new ArrayList<>();
        IntStream.range(0,potentialNumOfTracks).forEach(n -> {
            sessions.add(new Session()
                    .setTalks(new ArrayList<>())
                    .setMorningSession(true)
                    .setSessionLength(ServiceConfig.MORNING_SESSION_LENGTH)
                    .setLeftLength(ServiceConfig.MORNING_SESSION_LENGTH));
            sessions.add(new Session()
                    .setTalks(new ArrayList<>())
                    .setMorningSession(false)
                    .setSessionLength(ServiceConfig.AFTERNOON_SESSION_LENGTH)
                    .setLeftLength(ServiceConfig.AFTERNOON_SESSION_LENGTH));
        });

        return sessions;
    }

    private Integer calculateNumOfTracks(List<Talk> talkList) {
        Double totalTalkMin  = talkList
                .stream()
                .mapToDouble(Talk::getDuration)
                .sum();
        return (int) Math.ceil(totalTalkMin/ServiceConfig.MINUTE_FOR_ONE_TRACK);
    }
}
