package sg.nus.iss.facialrecognition.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import sg.nus.iss.facialrecognition.model.Feedback;
import sg.nus.iss.facialrecognition.model.User;

public interface FeedbackRepository extends PagingAndSortingRepository<Feedback, Long> {
    public Page<Feedback> findByReadFalse(Pageable pageable);

    public Page<Feedback> findByReadTrue(Pageable pageable);

    public Page<Feedback> findByReplyNotNull(Pageable pageable);

    public Page<Feedback> findByUser(User user, Pageable pageable);

    public Page<Feedback> findByUserAndReplyNotNull(User user, Pageable pageable);
}
