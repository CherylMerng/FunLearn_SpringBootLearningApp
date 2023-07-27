package sg.nus.iss.facialrecognition.validator;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import sg.nus.iss.facialrecognition.model.Video;
import java.net.URL;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class VideoValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Video.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Video video = (Video) obj;
        String videoURL = extractVideoId(video.getVideoURL());

        if (videoURL == "null") {
            errors.rejectValue("videoURL", "errors.videoURL", "This field cannot be blank.");
        }
        if (videoURL == "invalid") {
            errors.rejectValue("videoURL", "errors.videoURL", "Invalid URL, Please try again!");
        }
    }

    public static String extractVideoId(String url) {
        String videoId = "";
        String[] splittedStr;
        
        if(url.isEmpty()){
            return "null";
        }
        if (url.startsWith("https://www.youtube.com")) {
//            splittedStr = url.split("/");
//            String lastSegment = splittedStr[splittedStr.length - 1];
//            String[] splittedLastSegment = lastSegment.split("=");
//            videoId = splittedLastSegment[splittedLastSegment.length - 1];
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
}
