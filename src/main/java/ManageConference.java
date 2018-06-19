import model.Session;
import model.Talk;
import model.Track;
import service.ArrangeConferenceTrackService;
import service.OutPutService;
import service.ReadFileService;

import java.util.List;

public class ManageConference {

    ReadFileService readFileService = new ReadFileService();
    ArrangeConferenceTrackService trackService = new ArrangeConferenceTrackService();
    OutPutService outPutService = new OutPutService();

    public void manageConference(){
        List<Talk> talkList = readFileService.extractTalksFromFile();
        List<Session> sessions = trackService.scheduleSessions(talkList);
        List<Track> trackList = trackService.scheduleTracks(sessions);
        outPutService.printOutTracks(trackList);
    }
}
