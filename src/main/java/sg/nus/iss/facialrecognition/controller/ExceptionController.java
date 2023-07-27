package sg.nus.iss.facialrecognition.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExceptionController implements ErrorController{
    @RequestMapping("/error")
    public String handleError(Model model, HttpServletRequest request) {
        Object routeObject = request.getAttribute(RequestDispatcher.FORWARD_REQUEST_URI);
        String route = routeObject.toString();
        if(route.equals("/android")){
            model.addAttribute("prediction", "An error has occurred. Please try again.");
            return "prediction.html";
        }
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String errormsg = "An error has occurred, please try again or contact our team for technical support.";
        if(status != null){
            Integer statusCode = Integer.valueOf(status.toString());
            if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()){
                errormsg = "A server error has occurred, please try again later. We apologize for the inconvenience caused.";
            }
            else if(statusCode == HttpStatus.NOT_FOUND.value()){
                errormsg = "Page not found. Please check that you have entered the correct address.";
            }
        }

       model.addAttribute("errormsg", errormsg);
        return "error-page";
    }
}
