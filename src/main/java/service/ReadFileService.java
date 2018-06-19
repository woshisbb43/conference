package service;

import model.Talk;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReadFileService {

    public List<Talk> extractTalksFromFile(){
        List<String> talksStr = getTalksFromFile(ServiceConfig.FILE_NAME);
        try {
            return parseTalks(talksStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Talk> parseTalks(List<String> talksStr) throws Exception {
        if (talksStr.isEmpty())
            throw  new Exception("Empty talk list");
        List<Talk> parsedTalkList = new ArrayList<Talk>();
        String minSuffix = "min";
        String lightningSuffix = "lightning";

        for(String talk : talksStr) {
            int lastSpaceIndex = talk.lastIndexOf(" ");
            if(lastSpaceIndex == -1)
                throw new Exception("Invalid talk: " + talk + ". Invalid talk time.");

            String name = extractName(talk, lastSpaceIndex);
            String timeStr = extractTime(minSuffix, lightningSuffix, talk, lastSpaceIndex);

            int time = 0;
            boolean isLighting = false;
            try{
                if(timeStr.endsWith(minSuffix)) {
                    time = Integer.parseInt(timeStr.substring(0, timeStr.indexOf(minSuffix)));
                }
                else if(timeStr.endsWith(lightningSuffix)) {
                    isLighting = true;
                    String lightningTime = timeStr.substring(0, timeStr.indexOf(lightningSuffix));
                    if("".equals(lightningTime))
                        time = 5;
                    else
                        time = Integer.parseInt(lightningTime) * 5;
                }
            }catch(NumberFormatException e) {
                throw new Exception("Unable to get time information from file.  Error on time: " + timeStr + " for talk " + talk);
            }
            parsedTalkList.add(new Talk()
                    .setTopic(name)
                    .setDuration(time)
                    .setLighting(isLighting));
        }
        return parsedTalkList;

    }

    private String extractTime(String minSuffix, String lightningSuffix, String talk, int lastSpaceIndex) throws Exception {
        String timeStr = talk.substring(lastSpaceIndex + 1);
        if(!timeStr.endsWith(minSuffix) && !timeStr.endsWith(lightningSuffix))
            throw new Exception("Invalid talk time: " + talk + ". Enter time in min or in lightning");
        return timeStr;
    }

    private String extractName(String talk, int lastSpaceIndex) throws Exception {
        String name = talk.substring(0, lastSpaceIndex);
        if(name == null || "".equals(name.trim()))
            throw new Exception("Invalid talk name: " + talk);
        return name;
    }

    public List<String> getTalksFromFile(String fileName) {
        List<String> talkList = new ArrayList<String>();
        try {
            FileReader inputFile = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(inputFile);
            String strLine = bufferedReader.readLine();
            while (strLine !=null){
                talkList.add(strLine);
                strLine = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error on the file");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return talkList;
    }
}
