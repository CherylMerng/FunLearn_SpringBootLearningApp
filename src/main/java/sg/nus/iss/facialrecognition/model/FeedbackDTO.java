package sg.nus.iss.facialrecognition.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackDTO {
    private String viewType;
    private String sortBy;
    private int pageNumber;
}
