package sg.nus.iss.facialrecognition.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import sg.nus.iss.facialrecognition.model.Video;
import sg.nus.iss.facialrecognition.repository.VideoRepository;
import javax.transaction.Transactional;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoRepository videoRepo;

    @Override
    public Video saveVideo(Video video){
        return videoRepo.saveAndFlush(video);
    }
    @Override
    public List<Video> getAllVideos(){
        return videoRepo.findAll();
    }

    @Override
    public Video findVideoById(int id){
        return videoRepo.findById(id).get();
    }
    @Transactional
    @Override
    public Video addVideo(Video video){
        return videoRepo.saveAndFlush(video);
    };

    @Transactional
	@Override
    public Video editVideo(Video video){
        return videoRepo.saveAndFlush(video);
    };

    @Transactional
	@Override
    public void removeVideo(Video video){
        videoRepo.delete(video);
    };
    public void removeVideoById(int id){
        videoRepo.deleteById(id);
    };
    public List<Video> findVideosByKeyword(String keyword){
        return videoRepo.findVideosBySearchString(keyword);
    }



    
}
