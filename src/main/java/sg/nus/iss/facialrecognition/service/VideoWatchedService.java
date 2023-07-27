package sg.nus.iss.facialrecognition.service;

import sg.nus.iss.facialrecognition.model.*;

import java.util.*;

public interface VideoWatchedService {
    public VideoWatched recordVideoWatch(VideoWatched videoWatched);

    public Map<User, List<VideoWatched>> getUserToVideo(List<User> users);
    public void saveVideoWatched(Video video, User child);
    public void removeVideoWatchedbyId(int id);
    public List<VideoWatched> getAllVideoWatched();
}
