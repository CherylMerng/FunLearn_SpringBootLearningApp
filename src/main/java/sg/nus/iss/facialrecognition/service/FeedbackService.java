package sg.nus.iss.facialrecognition.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import sg.nus.iss.facialrecognition.model.Feedback;
import sg.nus.iss.facialrecognition.model.FeedbackDTO;
import sg.nus.iss.facialrecognition.model.User;
import sg.nus.iss.facialrecognition.repository.FeedbackRepository;
import sg.nus.iss.facialrecognition.util.EmailSender;

@Service
public class FeedbackService {
    @Autowired
    private FeedbackRepository feedbackRepo;
    @Autowired
    private UserService userService;
    @Autowired
    private EmailSender emailSender;

    public void saveFeedback(Feedback feedback, User parent) {
        Feedback newFeedback = new Feedback(feedback.getSubject(), feedback.getDescription());
        newFeedback.setUser(parent);
        feedbackRepo.save(newFeedback);
    }

    private int size = 10;

    public int getTotalPages(String viewType) {
        Pageable pageable = PageRequest.of(0, size);
        Page<Feedback> results;

        switch (viewType) {
            case "all":
                results = feedbackRepo.findAll(pageable);
                break;
            case "unread":
                results = feedbackRepo.findByReadFalse(pageable);
                break;
            case "read":
                results = feedbackRepo.findByReadTrue(pageable);
                break;
            case "replied":
                results = feedbackRepo.findByReplyNotNull(pageable);
                break;
            default:
                results = feedbackRepo.findByReadFalse(pageable);
        }

        return results.getTotalPages();
    }

    public int getUserTotalPages(UserDetails userDetails, String viewType) {
        User user = userService.getUserByUserName(userDetails.getUsername());
        Pageable pageable = PageRequest.of(0, size);
        Page<Feedback> results;

        switch (viewType) {
            case "all":
                results = feedbackRepo.findByUser(user, pageable);
                break;
            case "replied":
                results = feedbackRepo.findByUserAndReplyNotNull(user, pageable);
                break;
            default:
                results = feedbackRepo.findByUser(user, pageable);
        }

        return results.getTotalPages();
    }

    public Page<Feedback> getUnreadFeedback() {
        Pageable defaultPageable = PageRequest.of(0, size, Sort.by("feedbackDate").descending());
        return feedbackRepo.findByReadFalse(defaultPageable);
    }

    public Page<Feedback> getUserFeedback(UserDetails userDetails) {
        User user = userService.getUserByUserName(userDetails.getUsername());
        Pageable defaultPageable = PageRequest.of(0, size, Sort.by("feedbackDate").descending());
        return feedbackRepo.findByUser(user, defaultPageable);
    }

    public List<Feedback> getFeedbackByConditions(FeedbackDTO feedbackDTO) {
        int page = feedbackDTO.getPageNumber();
        Pageable pageable;

        switch (feedbackDTO.getSortBy()) {
            case "date_asc":
                pageable = PageRequest.of(page, size, Sort.by("feedbackDate").ascending());
                break;
            case "date_desc":
                pageable = PageRequest.of(page, size, Sort.by("feedbackDate").descending());
                break;
            case "name_asc":
                pageable = PageRequest.of(page, size, Sort.by("user.userName").ascending());
                break;
            case "name_desc":
                pageable = PageRequest.of(page, size, Sort.by("user.userName").descending());
                break;
            default:
                pageable = PageRequest.of(page, size, Sort.by("feedbackDate").descending());
        }

        switch (feedbackDTO.getViewType()) {
            case "all":
                return feedbackRepo.findAll(pageable).getContent();
            case "unread":
                return feedbackRepo.findByReadFalse(pageable).getContent();
            case "read":
                return feedbackRepo.findByReadTrue(pageable).getContent();
            case "replied":
                return feedbackRepo.findByReplyNotNull(pageable).getContent();
            default:
                return feedbackRepo.findByReadFalse(pageable).getContent();
        }
    }

    public List<Feedback> getUserFeedbackByConditions(UserDetails userDetails, FeedbackDTO feedbackDTO) {
        User user = userService.getUserByUserName(userDetails.getUsername());
        int page = feedbackDTO.getPageNumber();
        Pageable pageable;

        switch (feedbackDTO.getSortBy()) {
            case "date_asc":
                pageable = PageRequest.of(page, size, Sort.by("feedbackDate").ascending());
                break;
            case "date_desc":
                pageable = PageRequest.of(page, size, Sort.by("feedbackDate").descending());
                break;
            default:
                pageable = PageRequest.of(page, size, Sort.by("feedbackDate").descending());
        }

        switch (feedbackDTO.getViewType()) {
            case "all":
                return feedbackRepo.findByUser(user, pageable).getContent();
            case "replied":
                return feedbackRepo.findByUserAndReplyNotNull(user, pageable).getContent();
            default:
                return feedbackRepo.findByUser(user, pageable).getContent();
        }
    }

    public void setReadStatus(List<Long> feedbacks, boolean read) {
        for (Long id : feedbacks) {
            Feedback foundFeedback = feedbackRepo.findById(id).orElse(null);
            if (foundFeedback != null) {
                if (read) {
                    foundFeedback.setRead(true);
                } else {
                    foundFeedback.setRead(false);
                }
                feedbackRepo.save(foundFeedback);
            }
        }
    }

    public int getUserTotalPages(UserDetails userDetails) {
        User user = userService.getUserByUserName(userDetails.getUsername());
        Pageable pageable = PageRequest.of(0, size);
        Page<Feedback> results = feedbackRepo.findByUser(user, pageable);

        return results.getTotalPages();
    }


    public void setReply(UserDetails userDetails, Long feedbackId, String reply) throws Exception {
        Feedback foundFeedback = feedbackRepo.findById(feedbackId).orElse(null);
        String adminEmail = "autisticteam4@gmail.com";

        if (foundFeedback != null && StringUtils.hasText(adminEmail)) {
            foundFeedback.setRead(true);
            foundFeedback.setReply(reply);
            feedbackRepo.save(foundFeedback);
            String userEmail = foundFeedback.getUser().getEmail();
            if (!StringUtils.hasText(userEmail)) {
                throw new Exception();
            }
            emailSender.sendSimpleMessage(adminEmail, userEmail, feedbackId, reply);
        } else {
            throw new Exception();
        }
    }
}
