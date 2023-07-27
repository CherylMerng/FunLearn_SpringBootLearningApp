package sg.nus.iss.facialrecognition.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.client.RestTemplate;

import org.springframework.util.MultiValueMap;

@Controller
public class APIController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/blank")
    public String blank(){
        return "blank.html";
    }
    @PostMapping(value = "/android", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String android(@RequestParam MultiValueMap<String, String> paramMap, Model model){
        //retrieving the base64 string sent by the android posturl request 
        String encodedString = paramMap.getFirst("imageBytes");
        //there are spacing when the bytesdata are sent over, hence need to replace with the plus sign to ensure the valid base64string sent over to the Flask serverAPI.
        encodedString = encodedString.replace(' ', '+');
        System.out.print(encodedString);
        HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(encodedString,headers);
		ResponseEntity<String> response = restTemplate.exchange("http://34.87.43.62:8080/android", HttpMethod.POST, entity,String.class);
        // System.out.println(response.getBody());
        if (response.getStatusCode().equals(HttpStatus.OK)){
            model.addAttribute("prediction", response.getBody());}
        else{
            model.addAttribute("prediction", "An error has occurred. Please try again.");
        }
        return "prediction.html";

    }
       
}