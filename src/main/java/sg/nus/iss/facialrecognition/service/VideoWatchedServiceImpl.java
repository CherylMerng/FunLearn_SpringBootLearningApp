package sg.nus.iss.facialrecognition.service;

import java.time.LocalDate;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.nus.iss.facialrecognition.model.*;
import sg.nus.iss.facialrecognition.repository.VideoWatchedRepository;

@Service
public class VideoWatchedServiceImpl implements VideoWatchedService {

    @Autowired
    private VideoWatchedRepository videoWatchedRepo;

    @Autowired
    private VideoService videoService;

    @Override
    public VideoWatched recordVideoWatch(VideoWatched videoWatched){
        return videoWatchedRepo.save(videoWatched);
    }

    @Override
    public Map<User, List<VideoWatched>> getUserToVideo(List<User> users){
        Map<User, List<VideoWatched>> userToVideo = new HashMap<User,List<VideoWatched>>();
        for (User u: users){
            List<VideoWatched> q =videoWatchedRepo.findByUser(u);
            Collections.sort(q,new Comparator<VideoWatched>(){
                @Override
                public int compare (VideoWatched v1, VideoWatched v2){
                    return v2.getDateWatched().compareTo(v1.getDateWatched());
                }
            });
            userToVideo.put(u,q);
        }
        return userToVideo;
    }

    @Override
    public void saveVideoWatched(Video video, User child){
        VideoWatched newVidWatched = new VideoWatched();
        newVidWatched.setUser(child);
        newVidWatched.setDateWatched(LocalDate.now());
        newVidWatched.setVideo(video);

        videoWatchedRepo.save(newVidWatched);
    }
    @Override
    public List<VideoWatched> getAllVideoWatched(){
        return videoWatchedRepo.findAll();
    }
    @Override
    public void removeVideoWatchedbyId(int id){
        List<VideoWatched> watchedList = videoWatchedRepo.findAll();

        for(VideoWatched v : watchedList){
            if(v.getVideo().getVideoId() == id){
                
                videoWatchedRepo.deleteById(v.getVideoWatchedId());
            }
        }
    }
    
}
