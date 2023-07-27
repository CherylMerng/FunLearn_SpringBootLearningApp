package sg.nus.iss.facialrecognition.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sg.nus.iss.facialrecognition.model.Role;
import sg.nus.iss.facialrecognition.model.User;
import sg.nus.iss.facialrecognition.service.RoleServiceImpl;
import sg.nus.iss.facialrecognition.service.UserService;
import sg.nus.iss.facialrecognition.util.EmailSender;

import javax.validation.Valid;

@Controller
public class HomeController {
	private Logger log = LoggerFactory.getLogger(HomeController.class);
	@Value("${app.user.verification}")
	private Boolean requireActivation;
	@Autowired
	private UserService userService;
	@Autowired
	private RoleServiceImpl roleService;
	@Autowired
	private EmailSender emailSender;

	@GetMapping({"/","/login"})
    public String login() {
        return "login";
    }
	
    @GetMapping("/accessDenied")
	public String getAccessDeniedPage() {
		return "accessDeniedPage";
	}


	@RequestMapping(value = "/registeraccount", method = RequestMethod.GET)
	public String register(Model model) {
		User user = new User();
		Role defaultRole= roleService.getRole("parent");
		user.setRole(defaultRole); // Set default role
		model.addAttribute("user", user);
		return "/registeraccount";
	}

	@RequestMapping(value = "/registeraccount", method = RequestMethod.POST)
	public String registerPost(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "/registeraccount";
		}

		User registeredUser = userService.register(user);
		if (registeredUser != null) {
			emailSender.sendNewRegistration(user.getEmail(), registeredUser.getToken(),user);
			//sendUserDetailEmailPost(user);
			model.addAttribute("type", "general");
			return "/register-success";
		} else {
			log.error("User already exists: " + user.getUserName());
			result.rejectValue("email", "error.alreadyExists", "This username already exists. Please try another username as each username is unique for a user.");
			model.addAttribute("user", user);
			return "/registeraccount";
		}
	}
	public void sendUserDetailEmailPost(User user) {
		// User u = userRepository.findOneByEmail(user.getEmail());
		emailSender.sendUserDetails(user.getEmail(), user);
	}

	@RequestMapping("/user/activation-send")
	public ModelAndView activationSend(User user) {
		return new ModelAndView("/user/activation-send");
	}

	@RequestMapping(value = "/user/activation-send", method = RequestMethod.POST)
	public ModelAndView activationSendPost(User user, BindingResult result) {
		User u = userService.resetActivation(user.getUserName());
		if(u != null) {
			emailSender.sendNewActivationRequest(u.getEmail(), u.getToken(),u);
			return new ModelAndView("activation-sent");
		} else {
			result.rejectValue("email", "error.doesntExist", "We could not find this email in our databse");
			return new ModelAndView("activation-send");
		}
	}
	@RequestMapping(value = "/user/activate")
	public String activate(@RequestParam("activation") String activation) {
		User u = userService.activate(activation);
		if(u != null) {
			return "redirect:/";
		}
		return "redirect:/error?message=Could not activate with this activation code, please contact support";
	}

}