package sg.nus.iss.facialrecognition.service;


import java.util.List;

import sg.nus.iss.facialrecognition.model.Video;

public interface VideoService {
    public Video saveVideo(Video video);
    public List<Video> getAllVideos();
    public Video findVideoById(int id);

    public Video addVideo(Video video);
    public Video editVideo(Video video);
    public void removeVideo(Video video);
    public void removeVideoById(int id);
    public List<Video> findVideosByKeyword(String keyword);

}
