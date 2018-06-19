package service;

import model.Talk;
import model.Track;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

public class OutPutService {

    public void printOutTracks(List<Track> trackList){


        IntStream.range(0, trackList.size()).forEach(n -> {

            System.out.println(String.format("Track %d:", n+1));

            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mma ");
            Date date = new Date();
            date.setHours(9);
            date.setMinutes(0);
            date.setSeconds(0);
            String scheduledTime = dateFormat.format(date);

            for (Talk talk : trackList.get(n).getMorningSession().getTalks()){
                if (talk.isLighting()){
                    System.out.println(String.format("%s %s lighting", scheduledTime, talk.getTopic()));
                    scheduledTime = getNextScheduledTime(date, talk.getDuration());
                    continue;
                }
                System.out.println(String.format("%s %s %d min", scheduledTime, talk.getTopic(), talk.getDuration()));
                scheduledTime = getNextScheduledTime(date, talk.getDuration());
            }

            date.setHours(12);
            date.setMinutes(0);
            date.setSeconds(0);
            scheduledTime = dateFormat.format(date);

            System.out.println(String.format("%s %s", scheduledTime, "Lunch"));
            scheduledTime = getNextScheduledTime(date, 60);

            for (Talk talk : trackList.get(n).getAfterNoonSession().getTalks()){
                if (talk.isLighting()){
                    System.out.println(String.format("%s %s lighting", scheduledTime, talk.getTopic()));
                    scheduledTime = getNextScheduledTime(date, talk.getDuration());
                    continue;
                }
                System.out.println(String.format("%s %s %d min", scheduledTime, talk.getTopic(), talk.getDuration()));
                scheduledTime = getNextScheduledTime(date, talk.getDuration());
            }

            if (date.getHours() <16){
                date.setHours(16);
                date.setMinutes(0);
            }
            scheduledTime = dateFormat.format(date);
            System.out.println(String.format("%s Networking Event", scheduledTime));


        });

    }

    private String getNextScheduledTime(Date date, int timeDuration) {

        long timeInLong  = date.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat ("hh:mma ");

        long timeDurationInLong = timeDuration * 60 * 1000;
        long newTimeInLong = timeInLong + timeDurationInLong;

        date.setTime(newTimeInLong);
        String timeString = dateFormat.format(date);
        return timeString;
    }

}
