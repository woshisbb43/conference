package service;

import model.Talk;
import model.Track;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

public class OutPutService {

    public void printOutTracks(List<Track> trackList){


        IntStream.range(0, trackList.size()).forEach(n -> {

            System.out.println(String.format("Track %d:", n+1));

            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mma ");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 9);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);

            String scheduledTime = dateFormat.format(calendar.getTime());

            for (Talk talk : trackList.get(n).getMorningSession().getTalks()){
                if (talk.isLighting()){
                    System.out.println(String.format("%s %s lighting", scheduledTime, talk.getTopic()));
                    scheduledTime = getNextScheduledTime(calendar.getTime(), talk.getDuration());
                    continue;
                }
                System.out.println(String.format("%s %s %d min", scheduledTime, talk.getTopic(), talk.getDuration()));
                scheduledTime = getNextScheduledTime(calendar.getTime(), talk.getDuration());
            }

            calendar.set(Calendar.HOUR_OF_DAY, 12);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            scheduledTime = dateFormat.format(calendar.getTime());

            System.out.println(String.format("%s %s", scheduledTime, "Lunch"));
            scheduledTime = getNextScheduledTime(calendar.getTime(), 60);

            for (Talk talk : trackList.get(n).getAfterNoonSession().getTalks()){
                if (talk.isLighting()){
                    System.out.println(String.format("%s %s lighting", scheduledTime, talk.getTopic()));
                    scheduledTime = getNextScheduledTime(calendar.getTime(), talk.getDuration());
                    continue;
                }
                System.out.println(String.format("%s %s %d min", scheduledTime, talk.getTopic(), talk.getDuration()));
                scheduledTime = getNextScheduledTime(calendar.getTime(), talk.getDuration());
            }

            if (calendar.get(Calendar.HOUR_OF_DAY) <16){
                calendar.set(Calendar.HOUR_OF_DAY, 16);
                calendar.set(Calendar.MINUTE, 0);
            }
            scheduledTime = dateFormat.format(calendar.getTime());
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
