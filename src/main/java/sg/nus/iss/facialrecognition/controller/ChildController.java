package sg.nus.iss.facialrecognition.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import sg.nus.iss.facialrecognition.model.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import sg.nus.iss.facialrecognition.model.*;
import sg.nus.iss.facialrecognition.service.*;
import sg.nus.iss.facialrecognition.validator.QuizObjectValidator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.userdetails.UserDetails;

@Controller
@RequestMapping("/child")
public class ChildController {

    @Autowired
	private UserService userService;

    @Autowired
    private QuizQuestionService quizQuestionService;

    @Autowired
    private QuizService quizService;

    @Autowired
    private QuizQuestionScoreService quizQuestionScoreService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private VideoWatchedService videoWatchedService;

    @Autowired
    private QuizObjectValidator quizObjectValidator;
    @InitBinder("quiz")
    private void initquizObjectBinder(WebDataBinder binder){
        binder.addValidators(quizObjectValidator);
    }

    @GetMapping("/menu")
	public String getChildPage(@AuthenticationPrincipal UserDetails userDetails,
	Model model) {
		User user = userService.getUserByUserName(userDetails.getUsername());
		model.addAttribute("fullName", user.getFullName());
		return "childPage";
	}

	@GetMapping("/learn")
    public String learn() {
        return "learn";
    }

    @GetMapping("/watchvideo")
    public String watchvideo(Model model) {
        List<Video> videos = videoService.getAllVideos();
        model.addAttribute("videos", videos);
        return "videolist";
    }

    @PostMapping("/quiz")
    public String submitquiz(@AuthenticationPrincipal UserDetails userDetails, @Valid @ModelAttribute("quiz") QuizObject quiz, BindingResult bindingResult,Model model) {
        if (bindingResult.hasErrors()){
            return "takequiz";
        }
        int score = 0;
        Quiz quizData = new Quiz();
        quizData.setAttemptDate(LocalDate.now());
        QuizQuestion question1 = quizQuestionService.findQuizQuestion(quiz.getQuestion1());
        String question1answer = question1.getActualEmotion().toUpperCase();
        QuizQuestionScore question1score = new QuizQuestionScore();
        model.addAttribute("question1answer", question1answer);
        question1score.setEmotionGuessed(quiz.getQuestion1Guess());
        question1score.setQuestion(question1);
        question1score.setQuiz(quizData);
        if (quiz.getQuestion1Guess().equalsIgnoreCase(question1answer)){
            score += 1;
            model.addAttribute("question1", "correct");
            question1score.setCorrect(true);
        }
        else{
            model.addAttribute("question1", "wrong");
            question1score.setCorrect(false);
        }
        QuizQuestion question2 = quizQuestionService.findQuizQuestion(quiz.getQuestion2());
        String question2answer = question2.getActualEmotion().toUpperCase();
        model.addAttribute("question2answer", question2answer);
        QuizQuestionScore question2score = new QuizQuestionScore();
        question2score.setEmotionGuessed(quiz.getQuestion2Guess());
        question2score.setQuestion(question2);
        question2score.setQuiz(quizData);
        if (quiz.getQuestion2Guess().equalsIgnoreCase(question2answer)){
            score += 1;
            model.addAttribute("question2", "correct");
            question2score.setCorrect(true);
        }
        else{
            model.addAttribute("question2", "wrong");
            question2score.setCorrect(false);
        }
        QuizQuestion question3 = quizQuestionService.findQuizQuestion(quiz.getQuestion3());
        String question3answer = question3.getActualEmotion().toUpperCase();
        model.addAttribute("question3answer", question3answer);
        QuizQuestionScore question3score = new QuizQuestionScore();
        question3score.setEmotionGuessed(quiz.getQuestion3Guess());
        question3score.setQuestion(question3);
        question3score.setQuiz(quizData);
        if (quiz.getQuestion3Guess().equalsIgnoreCase(question3answer)){
            score += 1;
            model.addAttribute("question3", "correct");
            question3score.setCorrect(true);
        }
        else{
            model.addAttribute("question3", "wrong");
            question3score.setCorrect(false);
        }
        QuizQuestion question4 = quizQuestionService.findQuizQuestion(quiz.getQuestion4());
        String question4answer = question4.getActualEmotion().toUpperCase();
        model.addAttribute("question4answer", question4answer);
        QuizQuestionScore question4score = new QuizQuestionScore();
        question4score.setEmotionGuessed(quiz.getQuestion4Guess());
        question4score.setQuestion(question4);
        question4score.setQuiz(quizData);
        if (quiz.getQuestion4Guess().equalsIgnoreCase(question4answer)){
            score += 1;
            model.addAttribute("question4", "correct");
            question4score.setCorrect(true);
        }
        else{
            model.addAttribute("question4", "wrong");
            question4score.setCorrect(false);
        }
        QuizQuestion question5 = quizQuestionService.findQuizQuestion(quiz.getQuestion5());
        String question5answer = question5.getActualEmotion().toUpperCase();
        model.addAttribute("question5answer", question5answer);
        QuizQuestionScore question5score = new QuizQuestionScore();
        question5score.setEmotionGuessed(quiz.getQuestion5Guess());
        question5score.setQuestion(question5);
        question5score.setQuiz(quizData);
        if (quiz.getQuestion5Guess().equalsIgnoreCase(question5answer)){
            score += 1;
            model.addAttribute("question5", "correct");
            question5score.setCorrect(true);
        }
        else{
            model.addAttribute("question5", "wrong");
            question5score.setCorrect(false);
        }
        QuizQuestion question6 = quizQuestionService.findQuizQuestion(quiz.getQuestion6());
        String question6answer = question6.getActualEmotion().toUpperCase();
        model.addAttribute("question6answer", question6answer);
        QuizQuestionScore question6score = new QuizQuestionScore();
        question6score.setEmotionGuessed(quiz.getQuestion6Guess());
        question6score.setQuestion(question6);
        question6score.setQuiz(quizData);
        if (quiz.getQuestion6Guess().equalsIgnoreCase(question6answer)){
            score += 1;
            model.addAttribute("question6", "correct");
            question6score.setCorrect(true);
        }
        else{
            model.addAttribute("question6", "wrong");
            question6score.setCorrect(false);
        }
        QuizQuestion question7 = quizQuestionService.findQuizQuestion(quiz.getQuestion7());
        String question7answer = question7.getActualEmotion().toUpperCase();
        model.addAttribute("question7answer", question7answer);
        QuizQuestionScore question7score = new QuizQuestionScore();
        question7score.setEmotionGuessed(quiz.getQuestion7Guess());
        question7score.setQuestion(question7);
        question7score.setQuiz(quizData);
        if (quiz.getQuestion7Guess().equalsIgnoreCase(question7answer)){
            score += 1;
            model.addAttribute("question7", "correct");
            question7score.setCorrect(true);
        }
        else{
            model.addAttribute("question7", "wrong");
            question7score.setCorrect(false);
        }
        QuizQuestion question8 = quizQuestionService.findQuizQuestion(quiz.getQuestion8());
        String question8answer = question8.getActualEmotion().toUpperCase();
        model.addAttribute("question8answer", question8answer);
        QuizQuestionScore question8score = new QuizQuestionScore();
        question8score.setEmotionGuessed(quiz.getQuestion8Guess());
        question8score.setQuestion(question8);
        question8score.setQuiz(quizData);
        if (quiz.getQuestion8Guess().equalsIgnoreCase(question8answer)){
            score += 1;
            model.addAttribute("question8", "correct");
            question8score.setCorrect(true);
        }
        else{
            model.addAttribute("question8", "wrong");
            question8score.setCorrect(false);
        }
        QuizQuestion question9 = quizQuestionService.findQuizQuestion(quiz.getQuestion9());
        String question9answer = question9.getActualEmotion().toUpperCase();
        model.addAttribute("question9answer", question9answer);
        QuizQuestionScore question9score = new QuizQuestionScore();
        question9score.setEmotionGuessed(quiz.getQuestion9Guess());
        question9score.setQuestion(question9);
        question9score.setQuiz(quizData);
        if (quiz.getQuestion9Guess().equalsIgnoreCase(question9answer)){
            score += 1;
            model.addAttribute("question9", "correct");
            question9score.setCorrect(true);
        }
        else{
            model.addAttribute("question9", "wrong");
            question9score.setCorrect(false);
        }
        QuizQuestion question10 = quizQuestionService.findQuizQuestion(quiz.getQuestion10());
        String question10answer = question10.getActualEmotion().toUpperCase();
        model.addAttribute("question10answer", question10answer);
        QuizQuestionScore question10score = new QuizQuestionScore();
        question10score.setEmotionGuessed(quiz.getQuestion10Guess());
        question10score.setQuestion(question10);
        question10score.setQuiz(quizData);
        if (quiz.getQuestion10Guess().equalsIgnoreCase(question10answer)){
            score += 1;
            model.addAttribute("question10", "correct");
            question10score.setCorrect(true);
        }
        else{
            model.addAttribute("question10", "wrong");
            question10score.setCorrect(false);
        }
        int inverse = 10 - score;
        model.addAttribute("score", score);
        model.addAttribute("inverse", inverse);
        model.addAttribute("quiz", quiz);
        quizData.setScore(score);
        User user = userService.getUserByUserName(userDetails.getUsername());
        quizData.setUser(user);
        quizService.saveQuiz(quizData);
        quizQuestionScoreService.saveQuizQuestionScore(question1score);
        quizQuestionScoreService.saveQuizQuestionScore(question2score);
        quizQuestionScoreService.saveQuizQuestionScore(question3score);
        quizQuestionScoreService.saveQuizQuestionScore(question4score);
        quizQuestionScoreService.saveQuizQuestionScore(question5score);
        quizQuestionScoreService.saveQuizQuestionScore(question6score);
        quizQuestionScoreService.saveQuizQuestionScore(question7score);
        quizQuestionScoreService.saveQuizQuestionScore(question8score);
        quizQuestionScoreService.saveQuizQuestionScore(question9score);
        quizQuestionScoreService.saveQuizQuestionScore(question10score);
        return "quizresult";
    }

    @GetMapping("/quiz")
    public String quiz(Model model) {
        int min = 1;
        int max = 7178;
        List <Integer> randomNumbers = new ArrayList<Integer>();
        for (int i =0; i<10; i++){
            randomNumbers.add((int)Math.floor(Math.random()* (max-min + 1) + min));
        }
        QuizObject newQuiz = new QuizObject();
        newQuiz.setId(1);
        newQuiz.setQuestion1(randomNumbers.get(0));
        newQuiz.setQuestion2(randomNumbers.get(1));
        newQuiz.setQuestion3(randomNumbers.get(2));
        newQuiz.setQuestion4(randomNumbers.get(3));
        newQuiz.setQuestion5(randomNumbers.get(4));
        newQuiz.setQuestion6(randomNumbers.get(5));
        newQuiz.setQuestion7(randomNumbers.get(6));
        newQuiz.setQuestion8(randomNumbers.get(7));
        newQuiz.setQuestion9(randomNumbers.get(8));
        newQuiz.setQuestion10(randomNumbers.get(9));
        model.addAttribute("quiz",newQuiz);
        return "takequiz";
    }

    @GetMapping("/quiz/display/{id}")
	@ResponseBody
	void showImage(@PathVariable("id") int id, HttpServletResponse response)
			throws ServletException, IOException {
		QuizQuestion question = quizQuestionService.findQuizQuestion(id);
		response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
		response.getOutputStream().write(question.getData());
		response.getOutputStream().close();
	}

    @PostMapping("/selectedvideo")
    public String selectedvideo(@AuthenticationPrincipal UserDetails userDetails,@RequestParam("id") String id, Model model) {
        Video video = videoService.findVideoById(Integer.parseInt(id));
        String videoURL = "https://www.youtube.com/embed/" + video.getVideoURL();
        VideoWatched videoWatched = new VideoWatched();
        User user = userService.getUserByUserName(userDetails.getUsername());
        videoWatched.setDateWatched(LocalDate.now());
        videoWatched.setVideo(video);
        videoWatched.setUser(user);
        videoWatchedService.recordVideoWatch(videoWatched);
        model.addAttribute("videoURL", videoURL);
        model.addAttribute("video", video);
        return "video";
    }
}