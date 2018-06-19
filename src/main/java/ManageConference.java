import model.Session;
import model.Talk;
import model.Track;
import service.ArrangeConferenceTrackService;
import service.OutPutService;
import service.ReadFileService;

import java.util.List;

public class ManageConference {

    ReadFileService readFileService;
    ArrangeConferenceTrackService trackService;
    OutPutService outPutService;

    public ManageConference(ReadFileService readFileService, ArrangeConferenceTrackService trackService, OutPutService outPutService) {
        this.readFileService = readFileService;
        this.trackService = trackService;
        this.outPutService = outPutService;
    }

    public void manageConference(){
        List<Talk> talkList = readFileService.extractTalksFromFile();
        List<Session> sessions = trackService.scheduleSessions(talkList);
        List<Track> trackList = trackService.scheduleTracks(sessions);
        outPutService.printOutTracks(trackList);
    }
}
