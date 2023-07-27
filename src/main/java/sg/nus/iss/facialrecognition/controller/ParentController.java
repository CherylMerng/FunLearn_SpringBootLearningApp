package sg.nus.iss.facialrecognition.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sg.nus.iss.facialrecognition.model.*;
import sg.nus.iss.facialrecognition.service.*;
import sg.nus.iss.facialrecognition.util.EmailSender;
import sg.nus.iss.facialrecognition.validator.QuizObjectValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/parent")
public class ParentController {
	private Logger log = LoggerFactory.getLogger(ParentController.class);
    @Autowired
	private UserService userService;

	@Autowired
	private QuizService quizService;

	@Autowired
	private VideoWatchedService videoWatchedService;

	@Autowired
	private VideoService videoService;

	@Autowired
	private SurveyService surveyService;

	@Autowired
	private SurveyQuestionService surveyQuestionService;

	@Autowired
	private SurveyScoreService surveyScoreService;

	@Autowired
	private SurveyQuestionScoreService surveyQuestionScoreService;

	@Autowired
	private RoleServiceImpl roleService;

	@Autowired
	private QuizQuestionService quizQuestionService;

	@Autowired
	private FamilyMemberServiceImpl familyService;

	@Autowired
	private FeedbackService feedbackService;
	
	@Autowired
	private EmailSender emailSender;

	@Autowired
    private QuizObjectValidator quizObjectValidator;
    @InitBinder("quiz")
    private void initquizObjectBinder(WebDataBinder binder){
        binder.addValidators(quizObjectValidator);
    }

	@GetMapping("/menu")
	public String getParentPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
		User user = userService.getUserByUserName(userDetails.getUsername());
		model.addAttribute("fullName", user.getFullName());
		return "redirect:/parent/viewdashboard";
	}

	@GetMapping("/viewdashboard")
	public String viewDashboard(@AuthenticationPrincipal UserDetails userDetails,Model model) {
		User user = userService.getUserByUserName(userDetails.getUsername());
		List<FamilyMember> members = user.getMembers();
		List<User> users = new ArrayList<User>();
		for(FamilyMember m: members){
			users.add(userService.getUserByUserName(m.getUserName()));
		}
		Map<User, List<Quiz>> userToQuiz = quizService.getUserToQuiz(users);
		Map<User, List<Quiz>> sorted = userToQuiz.entrySet().stream()
		.sorted(Map.Entry.comparingByKey())
		.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
				(oldValue, newValue) -> oldValue, LinkedHashMap::new));
		model.addAttribute("map", sorted);
		Map<User, List<VideoWatched>> userToVideo = videoWatchedService.getUserToVideo(users);
		Map<User, List<VideoWatched>> sorted2 = userToVideo.entrySet().stream()
		.sorted(Map.Entry.comparingByKey())
		.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
				(oldValue, newValue) -> oldValue, LinkedHashMap::new));
		model.addAttribute("videomap", sorted2);
		List<SurveyScore> surveyScores = surveyScoreService.findSurveyScoreByUsername(userDetails.getUsername());
		Collections.sort(surveyScores,new Comparator<SurveyScore>(){
			@Override
			public int compare (SurveyScore s1, SurveyScore s2){
				return s2.getAttemptDate().compareTo(s1.getAttemptDate());
			}
		});
		model.addAttribute("surveyScores", surveyScores);
		return "viewdashboard";
	}

	@GetMapping("/selectedquiz")
	public String viewQuizDetail(@RequestParam("id")String id, Model model){
		System.out.println(id);
		Quiz b = quizService.getQuizById(Integer.parseInt(id));
		QuizObject quiz = new QuizObject();
		List<QuizQuestionScore> questionScores = b.getQuestions();
		for (int i=0; i<questionScores.size(); i++){
			Integer a = i+1;
			String q = "question" + a;
			String g = "question" + a + "answer";
			quiz = updateQuizObjectQuestions(a, questionScores.get(i), quiz);
			if (questionScores.get(i).isCorrect()){
				model.addAttribute(q, "correct");
			}
			else{
				QuizQuestion question = questionScores.get(i).getQuestion();
				String answer = question.getActualEmotion();
				model.addAttribute(q, "wrong");
				model.addAttribute(g, answer.toUpperCase());
			}
		}

		model.addAttribute("quiz", quiz);
		model.addAttribute("attemptDate", b.getAttemptDate());
		model.addAttribute("score", b.getScore());
		model.addAttribute("inverse", 10-b.getScore());

		return "parentviewquiz";

	}

	public QuizObject updateQuizObjectQuestions(Integer i, QuizQuestionScore q, QuizObject o){
		switch (i){
			case 1:
			o.setQuestion1(q.getQuestion().getId());
			o.setQuestion1Guess(q.getEmotionGuessed());
			case 2:
			o.setQuestion2(q.getQuestion().getId());
			o.setQuestion2Guess(q.getEmotionGuessed());
			case 3:
			o.setQuestion3(q.getQuestion().getId());
			o.setQuestion3Guess(q.getEmotionGuessed());
			case 4:
			o.setQuestion4(q.getQuestion().getId());
			o.setQuestion4Guess(q.getEmotionGuessed());
			case 5:
			o.setQuestion5(q.getQuestion().getId());
			o.setQuestion5Guess(q.getEmotionGuessed());
			case 6:
			o.setQuestion6(q.getQuestion().getId());
			o.setQuestion6Guess(q.getEmotionGuessed());
			case 7:
			o.setQuestion7(q.getQuestion().getId());
			o.setQuestion7Guess(q.getEmotionGuessed());
			case 8:
			o.setQuestion8(q.getQuestion().getId());
			o.setQuestion8Guess(q.getEmotionGuessed());
			case 9:
			o.setQuestion9(q.getQuestion().getId());
			o.setQuestion9Guess(q.getEmotionGuessed());
			case 10:
			o.setQuestion10(q.getQuestion().getId());
			o.setQuestion10Guess(q.getEmotionGuessed());
		}
		return o;
	}

	@GetMapping("/selectedvideo")
	public String viewVideoDetail(@RequestParam("id")String id, Model model){
		Video video = videoService.findVideoById(Integer.parseInt(id));
        String videoURL = "https://www.youtube.com/embed/" + video.getVideoURL();
		model.addAttribute("videoURL", videoURL);
        model.addAttribute("video", video);
        return "video";
	}

	@GetMapping("/selectedsurvey")
	public String viewSurveyDetail(@RequestParam("id")String id, Model model){
		SurveyScore surveyScore = surveyScoreService.getSurveyScoreById(Integer.parseInt(id));
		List<SurveyQuestionScore> questionScores = surveyQuestionScoreService.findSurveyQuestionScoreBySurveyScoreID(Integer.parseInt(id));
		model.addAttribute("surveyname", questionScores.get(0).getSurveyQuestion().getSurvey().getName());
		model.addAttribute("surveyQuestionScore", questionScores);
		model.addAttribute("surveyScore", surveyScore);
        return "parentviewsurvey";
	}

	@GetMapping("/dosurvey")
	public String doSurvey(Model model) {
		List<SurveyQuestion> surveyQuestions = surveyQuestionService.getAllSurveyQuestions();
		Collections.shuffle(surveyQuestions);
		SurveyObject surveyObject =  new SurveyObject(surveyQuestions);
		List<Survey> surveys = surveyService.getAllSurveys();
		model.addAttribute("surveyObject", surveyObject);
		model.addAttribute("survey", surveys);
		String error = "";
        model.addAttribute("error", error);
		return "dosurvey";
	}

	@PostMapping("/submitsurvey")
	public String submitSurvey(@AuthenticationPrincipal UserDetails userDetails, @Valid @ModelAttribute("surveyObject")SurveyObject surveyObject, BindingResult bindingResult, Model model){
		if (bindingResult.hasErrors()){
			String error = "Please ensure all questions are rated before submitting.";
            model.addAttribute("error", error);
			List<Survey> surveys = surveyService.getAllSurveys();
			model.addAttribute("survey", surveys);
            return "dosurvey";
        }
		model.addAttribute("surveyObject", surveyObject);
		List<SurveyQuestion> questionsDone = getAllQuestionsDone(surveyObject);
		Scores scores = getSurveyResults(questionsDone, surveyObject);
		model.addAttribute("qolScore", scores.getQolScore());
		model.addAttribute("perceptionScore", scores.getPerceptionScore());
		User user = userService.getUserByUserName(userDetails.getUsername());
		SurveyScore qolScore = new SurveyScore();
		qolScore.setSurvey(surveyService.getQOLSurvey());
		qolScore.setTotalScore(scores.getQolScore());
		qolScore.setAttemptDate(LocalDate.now());
		qolScore.setUser(user);
		surveyScoreService.saveSurveyScore(qolScore);
		SurveyScore perceptionScore = new SurveyScore();
		perceptionScore.setSurvey(surveyService.getPerceptionSurvey());
		perceptionScore.setAttemptDate(LocalDate.now());
		perceptionScore.setTotalScore(scores.getPerceptionScore());
		perceptionScore.setUser(user);
		surveyScoreService.saveSurveyScore(perceptionScore);
		List<SurveyQuestionScore> questionScores = scores.getQuestionScores();
		for (SurveyQuestionScore s: questionScores){
			if (s.getSurveyQuestion().getSurvey().getId() == 1){
				s.setSurveyScore(qolScore);
				surveyQuestionScoreService.saveSurveyQuestionScore(s);
			}
			else{
				s.setSurveyScore(perceptionScore);
				surveyQuestionScoreService.saveSurveyQuestionScore(s);
			}
		}
		int averageQOLScore = surveyService.getQOLSurvey().getUsualAverageScore();
		int averagePerceptionScore = surveyService.getPerceptionSurvey().getUsualAverageScore();
		model.addAttribute("averageqolScore", averageQOLScore);
		model.addAttribute("averageperceptionScore", averagePerceptionScore);

		return "surveyresults";
	}

	@GetMapping("/viewprofile")
	public String viewprofile(@AuthenticationPrincipal UserDetails userDetails, Model model, @RequestParam("action") Optional<String> actionOpt){
		String action = actionOpt.orElse("");
		String userName = userDetails.getUsername();
		User user = userService.getUserByUserName(userName);
		model.addAttribute("user", user);
		List<User> children=userService.findChildrenByParentName(userName);
		model.addAttribute("children", children);
		model.addAttribute("action", action);
		return "viewprofile";
	}

	@RequestMapping(value = "/registerchildaccount", method = RequestMethod.GET)
	public String register(Model model) {
		User user = new User();
		Role defaultRole= roleService.getRole("child");
		user.setRole(defaultRole); // Set default role
		model.addAttribute("user", user);
		return "/registerchildaccount";
	}

	@RequestMapping(value = "/registerchildaccount", method = RequestMethod.POST)
	public String registerPost(@AuthenticationPrincipal UserDetails userDetails, @Valid User user, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "/registerchildaccount";
		}
		user.setParentName(userDetails.getUsername());
		User registeredUser = userService.register(user);
		if (registeredUser != null) {
			familyService.registerFamilyMember(registeredUser.getUserName(), userService.getUserByUserName(userDetails.getUsername()));
			emailSender.sendNewRegistration(user.getEmail(), registeredUser.getToken(),user);
			//sendUserDetailEmailPost(user);
			model.addAttribute("type", "child");
			return "register-success";
		} else {
			log.error("User already exists: " + user.getUserName());
			result.rejectValue("email", "error.alreadyExists", "This username already exists, please try to reset password instead.");
			model.addAttribute("user", user);
			return "/registerchildaccount";
		}
	}
	public void sendUserDetailEmailPost(User user) {
		emailSender.sendUserDetails(user.getEmail(), user);
	}

	@GetMapping("/updateprofile")
	public String updateProfile(@AuthenticationPrincipal UserDetails userDetails,@ModelAttribute(value="user")User user, Model model, RedirectAttributes rda){
		String userName = userDetails.getUsername();
		user = userService.getUserByUserName(userName);
		if(user == null){
			rda.addAttribute("action","user-error");
			return "redirect:/parent/viewprofile";
		}
		model.addAttribute("user",user);
		return "updateprofile";
	}

	@PostMapping("/submitprofile")
	public String submitUpdateProfile(@AuthenticationPrincipal UserDetails userDetails, @ModelAttribute(value="user")User newUser, Model model, RedirectAttributes rda){
		String userName = userDetails.getUsername();
		try{
			User oldUser = userService.getUserByUserName(userName);
			if(oldUser == null){
				model.addAttribute("user" , newUser);
				model.addAttribute("action","error");
				return "updateprofile";
			}
			if (!newUser.getEmail().equals(oldUser.getEmail())){
				BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String activationToken = passwordEncoder.encode(userName);
				newUser.setToken(activationToken);
				userService.updateUser(oldUser, newUser);
				rda.addAttribute("action","update-user");
				emailSender.sendNewActivationRequest(newUser.getEmail(), newUser.getToken(), oldUser);
				return "redirect:/logout";

			}
			else{
				userService.updateUser(oldUser, newUser);
				rda.addAttribute("action","update-user");
			}
			
		}
		catch(Exception e){
			model.addAttribute("user" , newUser);
			model.addAttribute("action","unsuccessful");
			return "updateprofile";
		}

		return "redirect:/parent/viewprofile";
	}

	@GetMapping(value = "/change-password")
	public String resetPassword(@AuthenticationPrincipal UserDetails userDetails, @RequestParam("child") Optional<String> child, Model model) {
		String childName = child.orElse(null);
		UserDTO userDTO = new UserDTO();
		if (childName != null){
			User foundChild = userService.getUserByUserName(childName);
			if(!foundChild.getParentName().equals(userDetails.getUsername())){
				model.addAttribute("action", "pswderror");
				return "updatechildprofile";
			}
			userDTO.setUserName(childName);
		}
		else{
			userDTO.setUserName(userDetails.getUsername());
		}

		model.addAttribute("userDTO", userDTO);
		return "change-password";
	}

	@PostMapping(value = "/change-password")
	public String resetPassword(@AuthenticationPrincipal UserDetails userDetails, @Valid @ModelAttribute("userDTO") UserDTO userDTO, BindingResult bindingResult, RedirectAttributes rda, Model model) {
		// Check if there are any validation errors
		if (bindingResult.hasErrors()) {
			return "change-password";
		}

		// Check if the new password matches the confirmed password
		if (!userDTO.getNewPassword().equals(userDTO.getConfirmPassword())) {
			bindingResult.rejectValue("confirmPassword", "error.password", "The new password and confirm password do not match");
			return "change-password";
		}

		String userName = userDTO.getUserName();
		User user = userService.getUserByUserName(userName);

		// Check if the current password matches the user's password
		if (!user.getPassword().equals(userDTO.getCurrentPassword())) {
			bindingResult.rejectValue("currentPassword", "error.password", "The current password is incorrect");
			return "change-password";
		}

		// If validation succeeds, update the user's password in the database and redirect to a success page
		try{
			userService.resetPassword(user, userDTO);
			rda.addAttribute("action","update-pswd");
		}
		catch(Exception e){
			model.addAttribute("action", "error");
			return "change-password";
		}
		return "redirect:/parent/viewprofile";
	}

	@PostMapping("/updatechildprofile")
	public String updateChildProfile(@ModelAttribute(value="child")User child, Model model, RedirectAttributes rda){
		String childName=child.getUserName();
		User foundChild = userService.getUserByUserName(childName);
		if(foundChild == null){
			rda.addAttribute("action","child-error");
			return "redirect:/parent/viewprofile";
		}
		model.addAttribute("child",foundChild);
		return "updatechildprofile";
	}

	@PostMapping("/submitchildprofile")
	public String UpdateChildProfile (@Valid @ModelAttribute(value="child")User child, Model model, RedirectAttributes rda) {
		try{
			User oldChild=userService.getUserByUserName(child.getUserName());
			if(oldChild == null){
				model.addAttribute("child", child);
				model.addAttribute("action", "error");
				return "updatechildprofile";
			}
			if (!child.getEmail().equals(oldChild.getEmail())){
				BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String activationToken = passwordEncoder.encode(child.getUserName());
				child.setToken(activationToken);
				userService.updateUser(oldChild, child);
				rda.addAttribute("action","update-user");
				emailSender.sendNewActivationRequest(child.getEmail(), child.getToken(), oldChild);
				return "redirect:/logout";

			}
			else{
				userService.updateUser(oldChild, child);
				rda.addAttribute("action","update-user");
			}
		}
		catch(Exception e){
			model.addAttribute("child", child);
			model.addAttribute("action", "unsuccessful");
			return "updatechildprofile";
		}
		return "redirect:/parent/viewprofile";
	}
	@PostMapping("/deletechild")
	public String deleteStaff(@ModelAttribute("child")User child, RedirectAttributes rda) {		
		try{
			userService.deleteChild(child);
			rda.addAttribute("action", "delete");
		}
		catch(Exception e){
			rda.addAttribute("action", "delete-unsuccessful");
		}
		return "redirect:/parent/viewprofile";
	}

	@GetMapping("/givefeedback")
	public String giveFeedback(Model model) {

		model.addAttribute("newFeedback", new Feedback());
		return "givefeedback";
	}


	@PostMapping("/submitfeedback")
	public String submitFeedback(@AuthenticationPrincipal UserDetails userDetails,
			@Valid @ModelAttribute("newFeedback") Feedback feedback,
			BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "givefeedback";
		}
		User parent = userService.getUserByUserName(userDetails.getUsername());

		feedbackService.saveFeedback(feedback, parent);
		return "redirect:/parent/viewfeedback";
	}

	@GetMapping("/viewfeedback")
	public String getFeedback(@AuthenticationPrincipal UserDetails userDetails, Model model) {
		Page<Feedback> results = feedbackService.getUserFeedback(userDetails);
		int totalPages = results.getTotalPages();
		List<Feedback> list = results.getContent();
		FeedbackDTO feedbackDTO = new FeedbackDTO();
		feedbackDTO.setViewType("all");
		feedbackDTO.setSortBy("date_desc");
		feedbackDTO.setPageNumber(0);

		model.addAttribute("feedbacks", list);
		model.addAttribute("feedbackDTO", feedbackDTO);
		model.addAttribute("totalPages", totalPages);
		return "viewfeedbackhistory";
	}

	@PostMapping("/viewfeedback")
	public String getFeedback(@AuthenticationPrincipal UserDetails userDetails,
			@ModelAttribute("feedbackDTO") FeedbackDTO feedbackDTO, Model model, BindingResult br) {
		if (br.hasErrors() || feedbackDTO == null) {
			return "viewfeedbackhistory";
		}

		int totalPages = feedbackService.getUserTotalPages(userDetails, feedbackDTO.getViewType());
		if (totalPages < feedbackDTO.getPageNumber() + 1) {
			feedbackDTO.setPageNumber(totalPages - 1);
			if (feedbackDTO.getPageNumber() < 0) {
				feedbackDTO.setPageNumber(0);
			}
		}
		List<Feedback> results = feedbackService.getUserFeedbackByConditions(userDetails, feedbackDTO);

		model.addAttribute("feedbacks", results);
		model.addAttribute("feedbackDTO", feedbackDTO);
		model.addAttribute("totalPages", totalPages);
		return "viewfeedbackhistory";
	}


	@GetMapping("/testingchildfeatures")
	public String testingChildFeatures() {
		return "childPage";
	}

	@GetMapping("/quiz")
    public String testingChildQuiz(Model model) {
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

	@PostMapping("/quiz")
    public String submitquiz(@AuthenticationPrincipal UserDetails userDetails, @Valid @ModelAttribute("quiz") QuizObject quiz, BindingResult bindingResult,Model model) {
        if (bindingResult.hasErrors()){
            return "takequiz";
        }
        int score = 0;
        QuizQuestion question1 = quizQuestionService.findQuizQuestion(quiz.getQuestion1());
        String question1answer = question1.getActualEmotion().toUpperCase();
        model.addAttribute("question1answer", question1answer);
        if (quiz.getQuestion1Guess().equalsIgnoreCase(question1answer)){
            score += 1;
            model.addAttribute("question1", "correct");
        }
        else{
            model.addAttribute("question1", "wrong");
        }
        QuizQuestion question2 = quizQuestionService.findQuizQuestion(quiz.getQuestion2());
        String question2answer = question2.getActualEmotion().toUpperCase();
        model.addAttribute("question2answer", question2answer);
        if (quiz.getQuestion2Guess().equalsIgnoreCase(question2answer)){
            score += 1;
            model.addAttribute("question2", "correct");
        }
        else{
            model.addAttribute("question2", "wrong");
        }
        QuizQuestion question3 = quizQuestionService.findQuizQuestion(quiz.getQuestion3());
        String question3answer = question3.getActualEmotion().toUpperCase();
        model.addAttribute("question3answer", question3answer);
        if (quiz.getQuestion3Guess().equalsIgnoreCase(question3answer)){
            score += 1;
            model.addAttribute("question3", "correct");
        }
        else{
            model.addAttribute("question3", "wrong");
        }
        QuizQuestion question4 = quizQuestionService.findQuizQuestion(quiz.getQuestion4());
        String question4answer = question4.getActualEmotion().toUpperCase();
        model.addAttribute("question4answer", question4answer);
        if (quiz.getQuestion4Guess().equalsIgnoreCase(question4answer)){
            score += 1;
            model.addAttribute("question4", "correct");
        }
        else{
            model.addAttribute("question4", "wrong");
        }
        QuizQuestion question5 = quizQuestionService.findQuizQuestion(quiz.getQuestion5());
        String question5answer = question5.getActualEmotion().toUpperCase();
        model.addAttribute("question5answer", question5answer);
        if (quiz.getQuestion5Guess().equalsIgnoreCase(question5answer)){
            score += 1;
            model.addAttribute("question5", "correct");
        }
        else{
            model.addAttribute("question5", "wrong");
        }
        QuizQuestion question6 = quizQuestionService.findQuizQuestion(quiz.getQuestion6());
        String question6answer = question6.getActualEmotion().toUpperCase();
        model.addAttribute("question6answer", question6answer);
        if (quiz.getQuestion6Guess().equalsIgnoreCase(question6answer)){
            score += 1;
            model.addAttribute("question6", "correct");
        }
        else{
            model.addAttribute("question6", "wrong");
        }
        QuizQuestion question7 = quizQuestionService.findQuizQuestion(quiz.getQuestion7());
        String question7answer = question7.getActualEmotion().toUpperCase();
        model.addAttribute("question7answer", question7answer);
        if (quiz.getQuestion7Guess().equalsIgnoreCase(question7answer)){
            score += 1;
            model.addAttribute("question7", "correct");
        }
        else{
            model.addAttribute("question7", "wrong");
        }
        QuizQuestion question8 = quizQuestionService.findQuizQuestion(quiz.getQuestion8());
        String question8answer = question8.getActualEmotion().toUpperCase();
        model.addAttribute("question8answer", question8answer);
        if (quiz.getQuestion8Guess().equalsIgnoreCase(question8answer)){
            score += 1;
            model.addAttribute("question8", "correct");
        }
        else{
            model.addAttribute("question8", "wrong");
        }
        QuizQuestion question9 = quizQuestionService.findQuizQuestion(quiz.getQuestion9());
        String question9answer = question9.getActualEmotion().toUpperCase();
        model.addAttribute("question9answer", question9answer);
        if (quiz.getQuestion9Guess().equalsIgnoreCase(question9answer)){
            score += 1;
            model.addAttribute("question9", "correct");
        }
        else{
            model.addAttribute("question9", "wrong");
        }
        QuizQuestion question10 = quizQuestionService.findQuizQuestion(quiz.getQuestion10());
        String question10answer = question10.getActualEmotion().toUpperCase();
        model.addAttribute("question10answer", question10answer);
        if (quiz.getQuestion10Guess().equalsIgnoreCase(question10answer)){
            score += 1;
            model.addAttribute("question10", "correct");
        }
        else{
            model.addAttribute("question10", "wrong");
        }
        int inverse = 10 - score;
        model.addAttribute("score", score);
        model.addAttribute("inverse", inverse);
        model.addAttribute("quiz", quiz);
        return "quizresult";
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

	@GetMapping("/learn")
	public String testingChildLearn() {
		return "learn";
	}
	@GetMapping("/watchvideo")
	public String testingChildWatch(Model model) {
		List<Video> videos = videoService.getAllVideos();
        model.addAttribute("videos", videos);
        return "videolist";
	}
	@PostMapping("/selectedvideo")
    public String selectedvideo(@AuthenticationPrincipal UserDetails userDetails,@RequestParam("id") String id, Model model) {
        Video video = videoService.findVideoById(Integer.parseInt(id));
        String videoURL = "https://www.youtube.com/embed/" + video.getVideoURL();
        model.addAttribute("videoURL", videoURL);
        model.addAttribute("video", video);
        return "video";
    }


	public List<SurveyQuestion> getAllQuestionsDone(SurveyObject surveyObject){
		List<SurveyQuestion> questionsDone = new ArrayList<SurveyQuestion>();
		SurveyQuestion question1 = surveyQuestionService.getSurveyQuestionById(surveyObject.getQuestion1());
		SurveyQuestion question2 = surveyQuestionService.getSurveyQuestionById(surveyObject.getQuestion2());
		SurveyQuestion question3 = surveyQuestionService.getSurveyQuestionById(surveyObject.getQuestion3());
		SurveyQuestion question4 = surveyQuestionService.getSurveyQuestionById(surveyObject.getQuestion4());
		SurveyQuestion question5 = surveyQuestionService.getSurveyQuestionById(surveyObject.getQuestion5());
		SurveyQuestion question6 = surveyQuestionService.getSurveyQuestionById(surveyObject.getQuestion6());
		SurveyQuestion question7 = surveyQuestionService.getSurveyQuestionById(surveyObject.getQuestion7());
		SurveyQuestion question8 = surveyQuestionService.getSurveyQuestionById(surveyObject.getQuestion8());
		SurveyQuestion question9 = surveyQuestionService.getSurveyQuestionById(surveyObject.getQuestion9());
		SurveyQuestion question10 = surveyQuestionService.getSurveyQuestionById(surveyObject.getQuestion10());
		SurveyQuestion question11 = surveyQuestionService.getSurveyQuestionById(surveyObject.getQuestion11());
		SurveyQuestion question12 = surveyQuestionService.getSurveyQuestionById(surveyObject.getQuestion12());
		SurveyQuestion question13 = surveyQuestionService.getSurveyQuestionById(surveyObject.getQuestion13());
		SurveyQuestion question14 = surveyQuestionService.getSurveyQuestionById(surveyObject.getQuestion14());
		SurveyQuestion question15 = surveyQuestionService.getSurveyQuestionById(surveyObject.getQuestion15());
		SurveyQuestion question16 = surveyQuestionService.getSurveyQuestionById(surveyObject.getQuestion16());
		SurveyQuestion question17 = surveyQuestionService.getSurveyQuestionById(surveyObject.getQuestion17());
		SurveyQuestion question18 = surveyQuestionService.getSurveyQuestionById(surveyObject.getQuestion18());
		SurveyQuestion question19 = surveyQuestionService.getSurveyQuestionById(surveyObject.getQuestion19());
		SurveyQuestion question20 = surveyQuestionService.getSurveyQuestionById(surveyObject.getQuestion20());
		SurveyQuestion question21 = surveyQuestionService.getSurveyQuestionById(surveyObject.getQuestion21());
		SurveyQuestion question22 = surveyQuestionService.getSurveyQuestionById(surveyObject.getQuestion22());
		SurveyQuestion question23 = surveyQuestionService.getSurveyQuestionById(surveyObject.getQuestion23());
		SurveyQuestion question24 = surveyQuestionService.getSurveyQuestionById(surveyObject.getQuestion24());
		SurveyQuestion question25 = surveyQuestionService.getSurveyQuestionById(surveyObject.getQuestion25());
		SurveyQuestion question26 = surveyQuestionService.getSurveyQuestionById(surveyObject.getQuestion26());
		SurveyQuestion question27 = surveyQuestionService.getSurveyQuestionById(surveyObject.getQuestion27());
		SurveyQuestion question28 = surveyQuestionService.getSurveyQuestionById(surveyObject.getQuestion28());
		SurveyQuestion question29 = surveyQuestionService.getSurveyQuestionById(surveyObject.getQuestion29());
		SurveyQuestion question30 = surveyQuestionService.getSurveyQuestionById(surveyObject.getQuestion30());
		SurveyQuestion question31 = surveyQuestionService.getSurveyQuestionById(surveyObject.getQuestion31());
		SurveyQuestion question32 = surveyQuestionService.getSurveyQuestionById(surveyObject.getQuestion32());
		SurveyQuestion question33 = surveyQuestionService.getSurveyQuestionById(surveyObject.getQuestion33());
		SurveyQuestion question34 = surveyQuestionService.getSurveyQuestionById(surveyObject.getQuestion34());
		SurveyQuestion question35 = surveyQuestionService.getSurveyQuestionById(surveyObject.getQuestion35());
		SurveyQuestion question36 = surveyQuestionService.getSurveyQuestionById(surveyObject.getQuestion36());
		SurveyQuestion question37 = surveyQuestionService.getSurveyQuestionById(surveyObject.getQuestion37());
		SurveyQuestion question38 = surveyQuestionService.getSurveyQuestionById(surveyObject.getQuestion38());
		SurveyQuestion question39 = surveyQuestionService.getSurveyQuestionById(surveyObject.getQuestion39());
		SurveyQuestion question40 = surveyQuestionService.getSurveyQuestionById(surveyObject.getQuestion40());
		SurveyQuestion question41 = surveyQuestionService.getSurveyQuestionById(surveyObject.getQuestion41());
		SurveyQuestion question42 = surveyQuestionService.getSurveyQuestionById(surveyObject.getQuestion42());
		SurveyQuestion question43 = surveyQuestionService.getSurveyQuestionById(surveyObject.getQuestion43());
		SurveyQuestion question44 = surveyQuestionService.getSurveyQuestionById(surveyObject.getQuestion44());
		SurveyQuestion question45 = surveyQuestionService.getSurveyQuestionById(surveyObject.getQuestion45());
		SurveyQuestion question46 = surveyQuestionService.getSurveyQuestionById(surveyObject.getQuestion46());
		SurveyQuestion question47 = surveyQuestionService.getSurveyQuestionById(surveyObject.getQuestion47());
		SurveyQuestion question48 = surveyQuestionService.getSurveyQuestionById(surveyObject.getQuestion48());
		questionsDone.add(question1);
		questionsDone.add(question2);
		questionsDone.add(question3);
		questionsDone.add(question4);
		questionsDone.add(question5);
		questionsDone.add(question6);
		questionsDone.add(question7);
		questionsDone.add(question8);
		questionsDone.add(question9);
		questionsDone.add(question10);
		questionsDone.add(question11);
		questionsDone.add(question12);
		questionsDone.add(question13);
		questionsDone.add(question14);
		questionsDone.add(question15);
		questionsDone.add(question16);
		questionsDone.add(question17);
		questionsDone.add(question18);
		questionsDone.add(question19);
		questionsDone.add(question20);
		questionsDone.add(question21);
		questionsDone.add(question22);
		questionsDone.add(question23);
		questionsDone.add(question24);
		questionsDone.add(question25);
		questionsDone.add(question26);
		questionsDone.add(question27);
		questionsDone.add(question28);
		questionsDone.add(question29);
		questionsDone.add(question30);
		questionsDone.add(question31);
		questionsDone.add(question32);
		questionsDone.add(question33);
		questionsDone.add(question34);
		questionsDone.add(question35);
		questionsDone.add(question36);
		questionsDone.add(question37);
		questionsDone.add(question38);
		questionsDone.add(question39);
		questionsDone.add(question40);
		questionsDone.add(question41);
		questionsDone.add(question42);
		questionsDone.add(question43);
		questionsDone.add(question44);
		questionsDone.add(question45);
		questionsDone.add(question46);
		questionsDone.add(question47);
		questionsDone.add(question48);

		return questionsDone;
		
	}


    public Scores getSurveyResults(List<SurveyQuestion> questionsDone, SurveyObject surveyObject){
		int qolValue = 0;
		int perceptionValue = 0;
		List<SurveyQuestionScore> questionScores = new ArrayList<SurveyQuestionScore>();
		for (int i=0; i< questionsDone.size(); i++){
			switch (i){
				case 0:
				SurveyQuestionScore questionScore = new SurveyQuestionScore();
				questionScore.setQuestionScore(Integer.parseInt(surveyObject.getQuestion1Rating()));
				questionScore.setSurveyQuestion(questionsDone.get(i));
				if (questionsDone.get(i).getSurvey().getId() == 1){
					qolValue += Integer.parseInt(surveyObject.getQuestion1Rating());
				}
				else{
					perceptionValue += Integer.parseInt(surveyObject.getQuestion1Rating());
				}
				questionScores.add(questionScore);
				break;
				case 1:
				SurveyQuestionScore questionScore2 = new SurveyQuestionScore();
				questionScore2.setQuestionScore(Integer.parseInt(surveyObject.getQuestion2Rating()));
				questionScore2.setSurveyQuestion(questionsDone.get(i));
				if (questionsDone.get(i).getSurvey().getId() == 1){
					qolValue += Integer.parseInt(surveyObject.getQuestion2Rating());
				}
				else{
					perceptionValue += Integer.parseInt(surveyObject.getQuestion2Rating());
				}
				questionScores.add(questionScore2);
				break;
				case 2:
				SurveyQuestionScore questionScore3 = new SurveyQuestionScore();
				questionScore3.setQuestionScore(Integer.parseInt(surveyObject.getQuestion3Rating()));
				questionScore3.setSurveyQuestion(questionsDone.get(i));
				if (questionsDone.get(i).getSurvey().getId() == 1){
					qolValue += Integer.parseInt(surveyObject.getQuestion3Rating());
				}
				else{
					perceptionValue += Integer.parseInt(surveyObject.getQuestion3Rating());
				}
				questionScores.add(questionScore3);
				break;
				case 3:
				SurveyQuestionScore questionScore4 = new SurveyQuestionScore();
				questionScore4.setQuestionScore(Integer.parseInt(surveyObject.getQuestion4Rating()));
				questionScore4.setSurveyQuestion(questionsDone.get(i));
				if (questionsDone.get(i).getSurvey().getId() == 1){
					qolValue += Integer.parseInt(surveyObject.getQuestion4Rating());
				}
				else{
					perceptionValue += Integer.parseInt(surveyObject.getQuestion4Rating());
				}
				questionScores.add(questionScore4);
				break;
				case 4:
				SurveyQuestionScore questionScore5 = new SurveyQuestionScore();
				questionScore5.setQuestionScore(Integer.parseInt(surveyObject.getQuestion5Rating()));
				questionScore5.setSurveyQuestion(questionsDone.get(i));
				if (questionsDone.get(i).getSurvey().getId() == 1){
					qolValue += Integer.parseInt(surveyObject.getQuestion5Rating());
				}
				else{
					perceptionValue += Integer.parseInt(surveyObject.getQuestion5Rating());
				}
				questionScores.add(questionScore5);
				break;
				case 5:
				SurveyQuestionScore questionScore6 = new SurveyQuestionScore();
				questionScore6.setQuestionScore(Integer.parseInt(surveyObject.getQuestion6Rating()));
				questionScore6.setSurveyQuestion(questionsDone.get(i));
				if (questionsDone.get(i).getSurvey().getId() == 1){
					qolValue += Integer.parseInt(surveyObject.getQuestion6Rating());
				}
				else{
					perceptionValue += Integer.parseInt(surveyObject.getQuestion6Rating());
				}
				questionScores.add(questionScore6);
				break;
				case 6:
				SurveyQuestionScore questionScore7 = new SurveyQuestionScore();
				questionScore7.setQuestionScore(Integer.parseInt(surveyObject.getQuestion7Rating()));
				questionScore7.setSurveyQuestion(questionsDone.get(i));
				if (questionsDone.get(i).getSurvey().getId() == 1){
					qolValue += Integer.parseInt(surveyObject.getQuestion7Rating());
				}
				else{
					perceptionValue += Integer.parseInt(surveyObject.getQuestion7Rating());
				}
				questionScores.add(questionScore7);
				break;
				case 7:
				SurveyQuestionScore questionScore8 = new SurveyQuestionScore();
				questionScore8.setQuestionScore(Integer.parseInt(surveyObject.getQuestion8Rating()));
				questionScore8.setSurveyQuestion(questionsDone.get(i));
				if (questionsDone.get(i).getSurvey().getId() == 1){
					qolValue += Integer.parseInt(surveyObject.getQuestion8Rating());
				}
				else{
					perceptionValue += Integer.parseInt(surveyObject.getQuestion8Rating());
				}
				questionScores.add(questionScore8);
				break;
				case 8:
				SurveyQuestionScore questionScore9 = new SurveyQuestionScore();
				questionScore9.setQuestionScore(Integer.parseInt(surveyObject.getQuestion9Rating()));
				questionScore9.setSurveyQuestion(questionsDone.get(i));
				if (questionsDone.get(i).getSurvey().getId() == 1){
					qolValue += Integer.parseInt(surveyObject.getQuestion9Rating());
				}
				else{
					perceptionValue += Integer.parseInt(surveyObject.getQuestion9Rating());
				}
				questionScores.add(questionScore9);
				break;
				case 9:
				SurveyQuestionScore questionScore10 = new SurveyQuestionScore();
				questionScore10.setQuestionScore(Integer.parseInt(surveyObject.getQuestion10Rating()));
				questionScore10.setSurveyQuestion(questionsDone.get(i));
				if (questionsDone.get(i).getSurvey().getId() == 1){
					qolValue += Integer.parseInt(surveyObject.getQuestion10Rating());
				}
				else{
					perceptionValue += Integer.parseInt(surveyObject.getQuestion10Rating());
				}
				questionScores.add(questionScore10);
				break;
				case 10:
				SurveyQuestionScore questionScore11 = new SurveyQuestionScore();
				questionScore11.setQuestionScore(Integer.parseInt(surveyObject.getQuestion11Rating()));
				questionScore11.setSurveyQuestion(questionsDone.get(i));
				if (questionsDone.get(i).getSurvey().getId() == 1){
					qolValue += Integer.parseInt(surveyObject.getQuestion11Rating());
				}
				else{
					perceptionValue += Integer.parseInt(surveyObject.getQuestion11Rating());
				}
				questionScores.add(questionScore11);
				break;
				case 11:
				SurveyQuestionScore questionScore12 = new SurveyQuestionScore();
				questionScore12.setQuestionScore(Integer.parseInt(surveyObject.getQuestion12Rating()));
				questionScore12.setSurveyQuestion(questionsDone.get(i));
				if (questionsDone.get(i).getSurvey().getId() == 1){
					qolValue += Integer.parseInt(surveyObject.getQuestion12Rating());
				}
				else{
					perceptionValue += Integer.parseInt(surveyObject.getQuestion12Rating());
				}
				questionScores.add(questionScore12);
				break;
				case 12:
				SurveyQuestionScore questionScore13 = new SurveyQuestionScore();
				questionScore13.setQuestionScore(Integer.parseInt(surveyObject.getQuestion13Rating()));
				questionScore13.setSurveyQuestion(questionsDone.get(i));
				if (questionsDone.get(i).getSurvey().getId() == 1){
					qolValue += Integer.parseInt(surveyObject.getQuestion13Rating());
				}
				else{
					perceptionValue += Integer.parseInt(surveyObject.getQuestion13Rating());
				}
				questionScores.add(questionScore13);
				break;
				case 13:
				SurveyQuestionScore questionScore14 = new SurveyQuestionScore();
				questionScore14.setQuestionScore(Integer.parseInt(surveyObject.getQuestion14Rating()));
				questionScore14.setSurveyQuestion(questionsDone.get(i));
				if (questionsDone.get(i).getSurvey().getId() == 1){
					qolValue += Integer.parseInt(surveyObject.getQuestion14Rating());
				}
				else{
					perceptionValue += Integer.parseInt(surveyObject.getQuestion14Rating());
				}
				questionScores.add(questionScore14);
				break;
				case 14:
				SurveyQuestionScore questionScore15 = new SurveyQuestionScore();
				questionScore15.setQuestionScore(Integer.parseInt(surveyObject.getQuestion15Rating()));
				questionScore15.setSurveyQuestion(questionsDone.get(i));
				if (questionsDone.get(i).getSurvey().getId() == 1){
					qolValue += Integer.parseInt(surveyObject.getQuestion15Rating());
				}
				else{
					perceptionValue += Integer.parseInt(surveyObject.getQuestion15Rating());
				}
				questionScores.add(questionScore15);
				break;
				case 15:
				SurveyQuestionScore questionScore16 = new SurveyQuestionScore();
				questionScore16.setQuestionScore(Integer.parseInt(surveyObject.getQuestion16Rating()));
				questionScore16.setSurveyQuestion(questionsDone.get(i));
				if (questionsDone.get(i).getSurvey().getId() == 1){
					qolValue += Integer.parseInt(surveyObject.getQuestion16Rating());
				}
				else{
					perceptionValue += Integer.parseInt(surveyObject.getQuestion16Rating());
				}
				questionScores.add(questionScore16);
				break;
				case 16:
				SurveyQuestionScore questionScore17 = new SurveyQuestionScore();
				questionScore17.setQuestionScore(Integer.parseInt(surveyObject.getQuestion17Rating()));
				questionScore17.setSurveyQuestion(questionsDone.get(i));
				if (questionsDone.get(i).getSurvey().getId() == 1){
					qolValue += Integer.parseInt(surveyObject.getQuestion17Rating());
				}
				else{
					perceptionValue += Integer.parseInt(surveyObject.getQuestion17Rating());
				}
				questionScores.add(questionScore17);
				break;
				case 17:
				SurveyQuestionScore questionScore18 = new SurveyQuestionScore();
				questionScore18.setQuestionScore(Integer.parseInt(surveyObject.getQuestion18Rating()));
				questionScore18.setSurveyQuestion(questionsDone.get(i));
				if (questionsDone.get(i).getSurvey().getId() == 1){
					qolValue += Integer.parseInt(surveyObject.getQuestion18Rating());
				}
				else{
					perceptionValue += Integer.parseInt(surveyObject.getQuestion18Rating());
				}
				questionScores.add(questionScore18);
				break;
				case 18:
				SurveyQuestionScore questionScore19 = new SurveyQuestionScore();
				questionScore19.setQuestionScore(Integer.parseInt(surveyObject.getQuestion19Rating()));
				questionScore19.setSurveyQuestion(questionsDone.get(i));
				if (questionsDone.get(i).getSurvey().getId() == 1){
					qolValue += Integer.parseInt(surveyObject.getQuestion19Rating());
				}
				else{
					perceptionValue += Integer.parseInt(surveyObject.getQuestion19Rating());
				}
				questionScores.add(questionScore19);
				break;
				case 19:
				SurveyQuestionScore questionScore20 = new SurveyQuestionScore();
				questionScore20.setQuestionScore(Integer.parseInt(surveyObject.getQuestion20Rating()));
				questionScore20.setSurveyQuestion(questionsDone.get(i));
				if (questionsDone.get(i).getSurvey().getId() == 1){
					qolValue += Integer.parseInt(surveyObject.getQuestion20Rating());
				}
				else{
					perceptionValue += Integer.parseInt(surveyObject.getQuestion20Rating());
				}
				questionScores.add(questionScore20);
				break;
				case 20:
				SurveyQuestionScore questionScore21 = new SurveyQuestionScore();
				questionScore21.setQuestionScore(Integer.parseInt(surveyObject.getQuestion21Rating()));
				questionScore21.setSurveyQuestion(questionsDone.get(i));
				if (questionsDone.get(i).getSurvey().getId() == 1){
					qolValue += Integer.parseInt(surveyObject.getQuestion21Rating());
				}
				else{
					perceptionValue += Integer.parseInt(surveyObject.getQuestion21Rating());
				}
				questionScores.add(questionScore21);
				break;
				case 21:
				SurveyQuestionScore questionScore22 = new SurveyQuestionScore();
				questionScore22.setQuestionScore(Integer.parseInt(surveyObject.getQuestion22Rating()));
				questionScore22.setSurveyQuestion(questionsDone.get(i));
				if (questionsDone.get(i).getSurvey().getId() == 1){
					qolValue += Integer.parseInt(surveyObject.getQuestion22Rating());
				}
				else{
					perceptionValue += Integer.parseInt(surveyObject.getQuestion22Rating());
				}
				questionScores.add(questionScore22);
				break;
				case 22:
				SurveyQuestionScore questionScore23 = new SurveyQuestionScore();
				questionScore23.setQuestionScore(Integer.parseInt(surveyObject.getQuestion23Rating()));
				questionScore23.setSurveyQuestion(questionsDone.get(i));
				if (questionsDone.get(i).getSurvey().getId() == 1){
					qolValue += Integer.parseInt(surveyObject.getQuestion23Rating());
				}
				else{
					perceptionValue += Integer.parseInt(surveyObject.getQuestion23Rating());
				}
				questionScores.add(questionScore23);
				break;
				case 23:
				SurveyQuestionScore questionScore24 = new SurveyQuestionScore();
				questionScore24.setQuestionScore(Integer.parseInt(surveyObject.getQuestion24Rating()));
				questionScore24.setSurveyQuestion(questionsDone.get(i));
				if (questionsDone.get(i).getSurvey().getId() == 1){
					qolValue += Integer.parseInt(surveyObject.getQuestion24Rating());
				}
				else{
					perceptionValue += Integer.parseInt(surveyObject.getQuestion24Rating());
				}
				questionScores.add(questionScore24);
				break;
				case 24:
				SurveyQuestionScore questionScore25 = new SurveyQuestionScore();
				questionScore25.setQuestionScore(Integer.parseInt(surveyObject.getQuestion25Rating()));
				questionScore25.setSurveyQuestion(questionsDone.get(i));
				if (questionsDone.get(i).getSurvey().getId() == 1){
					qolValue += Integer.parseInt(surveyObject.getQuestion25Rating());
				}
				else{
					perceptionValue += Integer.parseInt(surveyObject.getQuestion25Rating());
				}
				questionScores.add(questionScore25);
				break;
				case 25:
				SurveyQuestionScore questionScore26 = new SurveyQuestionScore();
				questionScore26.setQuestionScore(Integer.parseInt(surveyObject.getQuestion26Rating()));
				questionScore26.setSurveyQuestion(questionsDone.get(i));
				if (questionsDone.get(i).getSurvey().getId() == 1){
					qolValue += Integer.parseInt(surveyObject.getQuestion26Rating());
				}
				else{
					perceptionValue += Integer.parseInt(surveyObject.getQuestion26Rating());
				}
				questionScores.add(questionScore26);
				break;
				case 26:
				SurveyQuestionScore questionScore27 = new SurveyQuestionScore();
				questionScore27.setQuestionScore(Integer.parseInt(surveyObject.getQuestion27Rating()));
				questionScore27.setSurveyQuestion(questionsDone.get(i));
				if (questionsDone.get(i).getSurvey().getId() == 1){
					qolValue += Integer.parseInt(surveyObject.getQuestion27Rating());
				}
				else{
					perceptionValue += Integer.parseInt(surveyObject.getQuestion27Rating());
				}
				questionScores.add(questionScore27);
				break;
				case 27:
				SurveyQuestionScore questionScore28 = new SurveyQuestionScore();
				questionScore28.setQuestionScore(Integer.parseInt(surveyObject.getQuestion28Rating()));
				questionScore28.setSurveyQuestion(questionsDone.get(i));
				if (questionsDone.get(i).getSurvey().getId() == 1){
					qolValue += Integer.parseInt(surveyObject.getQuestion28Rating());
				}
				else{
					perceptionValue += Integer.parseInt(surveyObject.getQuestion28Rating());
				}
				questionScores.add(questionScore28);
				break;
				case 28:
				SurveyQuestionScore questionScore29 = new SurveyQuestionScore();
				questionScore29.setQuestionScore(Integer.parseInt(surveyObject.getQuestion29Rating()));
				questionScore29.setSurveyQuestion(questionsDone.get(i));
				if (questionsDone.get(i).getSurvey().getId() == 1){
					qolValue += Integer.parseInt(surveyObject.getQuestion29Rating());
				}
				else{
					perceptionValue += Integer.parseInt(surveyObject.getQuestion29Rating());
				}
				questionScores.add(questionScore29);
				break;
				case 29:
				SurveyQuestionScore questionScore30 = new SurveyQuestionScore();
				questionScore30.setQuestionScore(Integer.parseInt(surveyObject.getQuestion30Rating()));
				questionScore30.setSurveyQuestion(questionsDone.get(i));
				if (questionsDone.get(i).getSurvey().getId() == 1){
					qolValue += Integer.parseInt(surveyObject.getQuestion30Rating());
				}
				else{
					perceptionValue += Integer.parseInt(surveyObject.getQuestion30Rating());
				}
				questionScores.add(questionScore30);
				break;
				case 30:
				SurveyQuestionScore questionScore31 = new SurveyQuestionScore();
				questionScore31.setQuestionScore(Integer.parseInt(surveyObject.getQuestion31Rating()));
				questionScore31.setSurveyQuestion(questionsDone.get(i));
				if (questionsDone.get(i).getSurvey().getId() == 1){
					qolValue += Integer.parseInt(surveyObject.getQuestion31Rating());
				}
				else{
					perceptionValue += Integer.parseInt(surveyObject.getQuestion31Rating());
				}
				questionScores.add(questionScore31);
				break;
				case 31:
				SurveyQuestionScore questionScore32 = new SurveyQuestionScore();
				questionScore32.setQuestionScore(Integer.parseInt(surveyObject.getQuestion32Rating()));
				questionScore32.setSurveyQuestion(questionsDone.get(i));
				if (questionsDone.get(i).getSurvey().getId() == 1){
					qolValue += Integer.parseInt(surveyObject.getQuestion32Rating());
				}
				else{
					perceptionValue += Integer.parseInt(surveyObject.getQuestion32Rating());
				}
				questionScores.add(questionScore32);
				break;
				case 32:
				SurveyQuestionScore questionScore33 = new SurveyQuestionScore();
				questionScore33.setQuestionScore(Integer.parseInt(surveyObject.getQuestion33Rating()));
				questionScore33.setSurveyQuestion(questionsDone.get(i));
				if (questionsDone.get(i).getSurvey().getId() == 1){
					qolValue += Integer.parseInt(surveyObject.getQuestion33Rating());
				}
				else{
					perceptionValue += Integer.parseInt(surveyObject.getQuestion33Rating());
				}
				questionScores.add(questionScore33);
				break;
				case 33:
				SurveyQuestionScore questionScore34 = new SurveyQuestionScore();
				questionScore34.setQuestionScore(Integer.parseInt(surveyObject.getQuestion34Rating()));
				questionScore34.setSurveyQuestion(questionsDone.get(i));
				if (questionsDone.get(i).getSurvey().getId() == 1){
					qolValue += Integer.parseInt(surveyObject.getQuestion34Rating());
				}
				else{
					perceptionValue += Integer.parseInt(surveyObject.getQuestion34Rating());
				}
				questionScores.add(questionScore34);
				break;
				case 34:
				SurveyQuestionScore questionScore35 = new SurveyQuestionScore();
				questionScore35.setQuestionScore(Integer.parseInt(surveyObject.getQuestion35Rating()));
				questionScore35.setSurveyQuestion(questionsDone.get(i));
				if (questionsDone.get(i).getSurvey().getId() == 1){
					qolValue += Integer.parseInt(surveyObject.getQuestion35Rating());
				}
				else{
					perceptionValue += Integer.parseInt(surveyObject.getQuestion35Rating());
				}
				questionScores.add(questionScore35);
				break;
				case 35:
				SurveyQuestionScore questionScore36 = new SurveyQuestionScore();
				questionScore36.setQuestionScore(Integer.parseInt(surveyObject.getQuestion36Rating()));
				questionScore36.setSurveyQuestion(questionsDone.get(i));
				if (questionsDone.get(i).getSurvey().getId() == 1){
					qolValue += Integer.parseInt(surveyObject.getQuestion36Rating());
				}
				else{
					perceptionValue += Integer.parseInt(surveyObject.getQuestion36Rating());
				}
				questionScores.add(questionScore36);
				break;
				case 36:
				SurveyQuestionScore questionScore37 = new SurveyQuestionScore();
				questionScore37.setQuestionScore(Integer.parseInt(surveyObject.getQuestion37Rating()));
				questionScore37.setSurveyQuestion(questionsDone.get(i));
				if (questionsDone.get(i).getSurvey().getId() == 1){
					qolValue += Integer.parseInt(surveyObject.getQuestion37Rating());
				}
				else{
					perceptionValue += Integer.parseInt(surveyObject.getQuestion37Rating());
				}
				questionScores.add(questionScore37);
				break;
				case 37:
				SurveyQuestionScore questionScore38 = new SurveyQuestionScore();
				questionScore38.setQuestionScore(Integer.parseInt(surveyObject.getQuestion38Rating()));
				questionScore38.setSurveyQuestion(questionsDone.get(i));
				if (questionsDone.get(i).getSurvey().getId() == 1){
					qolValue += Integer.parseInt(surveyObject.getQuestion38Rating());
				}
				else{
					perceptionValue += Integer.parseInt(surveyObject.getQuestion38Rating());
				}
				questionScores.add(questionScore38);
				break;
				case 38:
				SurveyQuestionScore questionScore39 = new SurveyQuestionScore();
				questionScore39.setQuestionScore(Integer.parseInt(surveyObject.getQuestion39Rating()));
				questionScore39.setSurveyQuestion(questionsDone.get(i));
				if (questionsDone.get(i).getSurvey().getId() == 1){
					qolValue += Integer.parseInt(surveyObject.getQuestion39Rating());
				}
				else{
					perceptionValue += Integer.parseInt(surveyObject.getQuestion39Rating());
				}
				questionScores.add(questionScore39);
				break;
				case 39:
				SurveyQuestionScore questionScore40 = new SurveyQuestionScore();
				questionScore40.setQuestionScore(Integer.parseInt(surveyObject.getQuestion40Rating()));
				questionScore40.setSurveyQuestion(questionsDone.get(i));
				if (questionsDone.get(i).getSurvey().getId() == 1){
					qolValue += Integer.parseInt(surveyObject.getQuestion40Rating());
				}
				else{
					perceptionValue += Integer.parseInt(surveyObject.getQuestion40Rating());
				}
				questionScores.add(questionScore40);
				break;
				case 40:
				SurveyQuestionScore questionScore41 = new SurveyQuestionScore();
				questionScore41.setQuestionScore(Integer.parseInt(surveyObject.getQuestion41Rating()));
				questionScore41.setSurveyQuestion(questionsDone.get(i));
				if (questionsDone.get(i).getSurvey().getId() == 1){
					qolValue += Integer.parseInt(surveyObject.getQuestion41Rating());
				}
				else{
					perceptionValue += Integer.parseInt(surveyObject.getQuestion41Rating());
				}
				questionScores.add(questionScore41);
				break;
				case 41:
				SurveyQuestionScore questionScore42 = new SurveyQuestionScore();
				questionScore42.setQuestionScore(Integer.parseInt(surveyObject.getQuestion42Rating()));
				questionScore42.setSurveyQuestion(questionsDone.get(i));
				if (questionsDone.get(i).getSurvey().getId() == 1){
					qolValue += Integer.parseInt(surveyObject.getQuestion42Rating());
				}
				else{
					perceptionValue += Integer.parseInt(surveyObject.getQuestion42Rating());
				}
				questionScores.add(questionScore42);
				break;
				case 42:
				SurveyQuestionScore questionScore43 = new SurveyQuestionScore();
				questionScore43.setQuestionScore(Integer.parseInt(surveyObject.getQuestion43Rating()));
				questionScore43.setSurveyQuestion(questionsDone.get(i));
				if (questionsDone.get(i).getSurvey().getId() == 1){
					qolValue += Integer.parseInt(surveyObject.getQuestion43Rating());
				}
				else{
					perceptionValue += Integer.parseInt(surveyObject.getQuestion43Rating());
				}
				questionScores.add(questionScore43);
				break;
				case 43:
				SurveyQuestionScore questionScore44 = new SurveyQuestionScore();
				questionScore44.setQuestionScore(Integer.parseInt(surveyObject.getQuestion44Rating()));
				questionScore44.setSurveyQuestion(questionsDone.get(i));
				if (questionsDone.get(i).getSurvey().getId() == 1){
					qolValue += Integer.parseInt(surveyObject.getQuestion44Rating());
				}
				else{
					perceptionValue += Integer.parseInt(surveyObject.getQuestion44Rating());
				}
				questionScores.add(questionScore44);
				break;
				case 44:
				SurveyQuestionScore questionScore45 = new SurveyQuestionScore();
				questionScore45.setQuestionScore(Integer.parseInt(surveyObject.getQuestion45Rating()));
				questionScore45.setSurveyQuestion(questionsDone.get(i));
				if (questionsDone.get(i).getSurvey().getId() == 1){
					qolValue += Integer.parseInt(surveyObject.getQuestion45Rating());
				}
				else{
					perceptionValue += Integer.parseInt(surveyObject.getQuestion45Rating());
				}
				questionScores.add(questionScore45);
				break;
				case 45:
				SurveyQuestionScore questionScore46 = new SurveyQuestionScore();
				questionScore46.setQuestionScore(Integer.parseInt(surveyObject.getQuestion46Rating()));
				questionScore46.setSurveyQuestion(questionsDone.get(i));
				if (questionsDone.get(i).getSurvey().getId() == 1){
					qolValue += Integer.parseInt(surveyObject.getQuestion46Rating());
				}
				else{
					perceptionValue += Integer.parseInt(surveyObject.getQuestion46Rating());
				}
				questionScores.add(questionScore46);
				break;
				case 46:
				SurveyQuestionScore questionScore47 = new SurveyQuestionScore();
				questionScore47.setQuestionScore(Integer.parseInt(surveyObject.getQuestion47Rating()));
				questionScore47.setSurveyQuestion(questionsDone.get(i));
				if (questionsDone.get(i).getSurvey().getId() == 1){
					qolValue += Integer.parseInt(surveyObject.getQuestion47Rating());
				}
				else{
					perceptionValue += Integer.parseInt(surveyObject.getQuestion47Rating());
				}
				questionScores.add(questionScore47);
				break;
				case 47:
				SurveyQuestionScore questionScore48 = new SurveyQuestionScore();
				questionScore48.setQuestionScore(Integer.parseInt(surveyObject.getQuestion48Rating()));
				questionScore48.setSurveyQuestion(questionsDone.get(i));
				if (questionsDone.get(i).getSurvey().getId() == 1){
					qolValue += Integer.parseInt(surveyObject.getQuestion48Rating());
				}
				else{
					perceptionValue += Integer.parseInt(surveyObject.getQuestion48Rating());
				}
				questionScores.add(questionScore48);
				break;


			}
		}
		Scores scores = new Scores();
		scores.setPerceptionScore(perceptionValue);
		scores.setQolScore(qolValue);
		scores.setQuestionScores(questionScores);
		return scores;

	}





}