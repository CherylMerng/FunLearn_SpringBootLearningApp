package sg.nus.iss.facialrecognition.controller;

import org.springframework.stereotype.Controller;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import sg.nus.iss.facialrecognition.model.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sg.nus.iss.facialrecognition.service.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import javax.validation.Valid;
import sg.nus.iss.facialrecognition.validator.*;
import org.springframework.validation.BindingResult;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private VideoService vidService;

    @Autowired
    private VideoWatchedService videoWatchedService;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private VideoValidator videoValidator;

    @InitBinder("newVideo")
    private void initVideoBinder(WebDataBinder binder) {
        binder.addValidators(videoValidator);
    }

    @GetMapping("/menu")
    public String getAdminPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.getUserByUserName(userDetails.getUsername());
        model.addAttribute("fullName", user.getFullName());
        return "adminPage";
    }

    @GetMapping("/viewvideolist")
    public String viewVideoList(Model model) {

        List<Video> videos = new ArrayList<Video>();
        videos = vidService.getAllVideos();
        model.addAttribute("videos", videos);
        model.addAttribute("video", new Video());

        return "video-list";
    }

    @PostMapping("/viewvideolist")
    public String videoList(Model model, @Param("keyword") String keyword) {
        List<Video> videos = new ArrayList<Video>();

        if (keyword == null) {
            videos = vidService.getAllVideos();
        } else {
            videos = vidService.findVideosByKeyword(keyword);
        }
        model.addAttribute("videos", videos);
        model.addAttribute("video", new Video());

        return "video-list";
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping("/playvideo")
    public String playVideo(Model model) {
        Video video = (Video) model.asMap().get("selectedVideo");
        if (video.getVideoURL() == null) {
            return "error-video-page";
        }
        model.addAttribute("selectedVideo", video);
        model.addAttribute("video", new Video());
        return "video-play";
    }

    @PostMapping("/playvideo")
    public String playVideo(@ModelAttribute("video") Video video, Model model) {

        if (video.getVideoURL() == null) {
            return "error-video-page";
        }
        model.addAttribute("selectedVideo", video);
        return "video-play";
    }

    @GetMapping("/addvideo")
    public String addVideo(Model model) {
        Video newVideo = new Video();
        model.addAttribute("newVideo", newVideo);

        return "video-add";
    }

    @PostMapping("/addvideo")
    public String addVideo(@Valid @ModelAttribute("newVideo") Video vid, BindingResult bindingResult, Model model,
            RedirectAttributes redirectAttributes) {
        String videoURL = extractVideoId(vid.getVideoURL());

        if (bindingResult.hasErrors()) {
            return "video-add";
        }
        // if(videoURL == "invalid"){
        // return "redirect:/video/add";
        // }
        String imageURL = "https://img.youtube.com/vi/" + videoURL + "/hqdefault.jpg";

        vid.setVideoURL(videoURL);
        vid.setImageURL(imageURL);
        vid.setPostDate(LocalDate.now());
        vidService.addVideo(vid);
        Video vid2 = vidService.findVideoById(vid.getVideoId());
        redirectAttributes.addFlashAttribute("selectedVideo", vid2);

        return "redirect:/admin/playvideo";
    }

    @PostMapping("/deletevideo")
    public String deletevideo(@RequestParam("id") String id) {

        Video v = vidService.findVideoById(Integer.parseInt(id));

        videoWatchedService.removeVideoWatchedbyId(Integer.parseInt(id));

        vidService.removeVideo(v);

        return "redirect:/admin/viewvideolist";
    }

    @PostMapping("/editvideo")
    public String editVideo(@ModelAttribute("video") Video video, Model model) {
        model.addAttribute("newVideo", video);
        return "video-edit";
    }

    @PostMapping("/updatevideo")
    public String updateVideo(@Valid @ModelAttribute("newVideo") Video video,BindingResult bindingResult,
                             Model model, RedirectAttributes redirectAttributes) {
        String videoURL = extractVideoId(video.getVideoURL());

        if (bindingResult.hasErrors()) {
            return "video-edit";
        }
        String imageURL = "https://img.youtube.com/vi/" + videoURL + "/hqdefault.jpg";

        video.setVideoURL(videoURL);
        video.setImageURL(imageURL);

        vidService.editVideo(video);
        redirectAttributes.addFlashAttribute("selectedVideo", video);
        return "redirect:/admin/playvideo";
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static String extractVideoId(String url) {
        String videoId = "";
        String[] splittedStr;

        if (url.startsWith("https://www.youtube.com")) {
            // splittedStr = url.split("/");
            // String lastSegment = splittedStr[splittedStr.length - 1];
            // String[] splittedLastSegment = lastSegment.split("=");
            // videoId = splittedLastSegment[splittedLastSegment.length - 1];
            Scanner scanner = new Scanner(url);

            String urlString = scanner.nextLine();

            try {
                URL url1 = new URL(urlString);
                String query = url1.getQuery();

                Pattern pattern = Pattern.compile("v=([^&]+)");
                Matcher matcher = pattern.matcher(query);

                if (matcher.find()) {
                    videoId = matcher.group(1);
                    return videoId;
                } else {
                    return "invalid";
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else if (url.startsWith("https://youtu.be")) {
            splittedStr = url.split("/");
            videoId = splittedStr[splittedStr.length - 1];
        } else {
            return url;
        }

        return videoId;
    }

    @GetMapping("/viewfeedback")
    public String getFeedback(Model model) {
        Page<Feedback> results = feedbackService.getUnreadFeedback();
        int totalPages = results.getTotalPages();
        List<Feedback> list = results.getContent();
        FeedbackDTO feedbackDTO = new FeedbackDTO();
        feedbackDTO.setViewType("unread");
        feedbackDTO.setSortBy("date_desc");
        feedbackDTO.setPageNumber(0);

        model.addAttribute("feedbacks", list);
        model.addAttribute("feedbackDTO", feedbackDTO);
        model.addAttribute("totalPages", totalPages);
        return "viewfeedback";
    }

    @PostMapping("/viewfeedback")
    public String getFeedback(@ModelAttribute("feedbackDTO") FeedbackDTO feedbackDTO, Model model, BindingResult br) {
        if (br.hasErrors() || feedbackDTO == null) {
            return "viewfeedback";
        }

        int totalPages = feedbackService.getTotalPages(feedbackDTO.getViewType());
        if (totalPages < feedbackDTO.getPageNumber() + 1) {
            feedbackDTO.setPageNumber(totalPages - 1);
            if (feedbackDTO.getPageNumber() < 0) {
                feedbackDTO.setPageNumber(0);
            }
        }
        List<Feedback> results = feedbackService.getFeedbackByConditions(feedbackDTO);

        model.addAttribute("feedbacks", results);
        model.addAttribute("feedbackDTO", feedbackDTO);
        model.addAttribute("totalPages", totalPages);
        return "viewfeedback";
    }

    @PostMapping("/viewfeedback_update")
    public ResponseEntity<String> updateFeedback(@ModelAttribute("feedbacks") List<Long> feedbacks,
            @ModelAttribute("read") boolean read, BindingResult br) {
        if (br.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            feedbackService.setReadStatus(feedbacks, read);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/viewfeedback_reply")
    public ResponseEntity<String> updateFeedback(@AuthenticationPrincipal UserDetails userDetails,
            @ModelAttribute("feedbackid") Long feedbackId, @ModelAttribute("reply") String reply, BindingResult br) {
        if (br.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            feedbackService.setReply(userDetails, feedbackId, reply);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/viewaccounts")
    public String viewAccounts(Model model) {
        List<User> userAccounts = userServiceImpl.getAllUserAccounts();
        model.addAttribute("userAccounts", userAccounts);
        return "userAccount";
    }

    @PostMapping("/viewaccounts")
    public String viewAccountsByCondition(@ModelAttribute("displayType") String displayType, Model model) {
        List<User> userAccounts = userServiceImpl.getUserAccountsByConditions(displayType);
        model.addAttribute("userAccounts", userAccounts);
        model.addAttribute("displayType", displayType);
        return "userAccount";
    }

    @PostMapping("/inactivateaccount")
    public String inactivateAccount(@ModelAttribute("isActive") boolean isActive, @ModelAttribute("userName") String userName, Model model) {
        try{
            userServiceImpl.updateUserStatus(userName, isActive);
            model.addAttribute("action", "success");
        }
        catch(Exception e){
            model.addAttribute("action", "error");
        }
        List<User> userAccounts = userServiceImpl.getAllUserAccounts();
        model.addAttribute("userAccounts", userAccounts);
        return "userAccount";
    }

}