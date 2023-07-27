package sg.nus.iss.facialrecognition.model;

import lombok.NoArgsConstructor;
import lombok.Data;
import java.util.*;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class SurveyObject {
    private int id;
    private int question1;
    private int question2;
    private int question3;
    private int question4;
    private int question5;
    private int question6;
    private int question7;
    private int question8;
    private int question9;
    private int question10;
    private int question11;
    private int question12;
    private int question13;
    private int question14;
    private int question15;
    private int question16;
    private int question17;
    private int question18;
    private int question19;
    private int question20;
    private int question21;
    private int question22;
    private int question23;
    private int question24;
    private int question25;
    private int question26;
    private int question27;
    private int question28;
    private int question29;
    private int question30;
    private int question31;
    private int question32;
    private int question33;
    private int question34;
    private int question35;
    private int question36;
    private int question37;
    private int question38;
    private int question39;
    private int question40;
    private int question41;
    private int question42;
    private int question43;
    private int question44;
    private int question45;
    private int question46;
    private int question47;
    private int question48;
    private String question1description;
    private String question2description;
    private String question3description;
    private String question4description;
    private String question5description;
    private String question6description;
    private String question7description;
    private String question8description;
    private String question9description;
    private String question10description;
    private String question11description;
    private String question12description;
    private String question13description;
    private String question14description;
    private String question15description;
    private String question16description;
    private String question17description;
    private String question18description;
    private String question19description;
    private String question20description;
    private String question21description;
    private String question22description;
    private String question23description;
    private String question24description;
    private String question25description;
    private String question26description;
    private String question27description;
    private String question28description;
    private String question29description;
    private String question30description;
    private String question31description;
    private String question32description;
    private String question33description;
    private String question34description;
    private String question35description;
    private String question36description;
    private String question37description;
    private String question38description;
    private String question39description;
    private String question40description;
    private String question41description;
    private String question42description;
    private String question43description;
    private String question44description;
    private String question45description;
    private String question46description;
    private String question47description;
    private String question48description;
    public SurveyObject (List<SurveyQuestion> questionIDs){
        this.question1 = questionIDs.get(0).getId();
        this.question1description = questionIDs.get(0).getDescription();
        this.question2 = questionIDs.get(1).getId();
        this.question2description = questionIDs.get(1).getDescription();
        this.question3 = questionIDs.get(2).getId();
        this.question3description = questionIDs.get(2).getDescription();
        this.question4 = questionIDs.get(3).getId();
        this.question4description = questionIDs.get(3).getDescription();
        this.question5 = questionIDs.get(4).getId();
        this.question5description = questionIDs.get(4).getDescription();
        this.question6 = questionIDs.get(5).getId();
        this.question6description = questionIDs.get(5).getDescription();
        this.question7 = questionIDs.get(6).getId();
        this.question7description = questionIDs.get(6).getDescription();
        this.question8 = questionIDs.get(7).getId();
        this.question8description = questionIDs.get(7).getDescription();
        this.question9 = questionIDs.get(8).getId();
        this.question9description = questionIDs.get(8).getDescription();
        this.question10 = questionIDs.get(9).getId();
        this.question10description = questionIDs.get(9).getDescription();
        this.question11 = questionIDs.get(10).getId();
        this.question11description = questionIDs.get(10).getDescription();
        this.question12 = questionIDs.get(11).getId();
        this.question12description = questionIDs.get(11).getDescription();
        this.question13 = questionIDs.get(12).getId();
        this.question13description = questionIDs.get(12).getDescription();
        this.question14 = questionIDs.get(13).getId();
        this.question14description = questionIDs.get(13).getDescription();
        this.question15 = questionIDs.get(14).getId();
        this.question15description = questionIDs.get(14).getDescription();
        this.question16 = questionIDs.get(15).getId();
        this.question16description = questionIDs.get(15).getDescription();
        this.question17 = questionIDs.get(16).getId();
        this.question17description = questionIDs.get(16).getDescription();
        this.question18 = questionIDs.get(17).getId();
        this.question18description = questionIDs.get(17).getDescription();
        this.question19 = questionIDs.get(18).getId();
        this.question19description = questionIDs.get(18).getDescription();
        this.question20 = questionIDs.get(19).getId();
        this.question20description = questionIDs.get(19).getDescription();
        this.question21 = questionIDs.get(20).getId();
        this.question21description = questionIDs.get(20).getDescription();
        this.question22 = questionIDs.get(21).getId();
        this.question22description = questionIDs.get(21).getDescription();
        this.question23 = questionIDs.get(22).getId();
        this.question23description = questionIDs.get(22).getDescription();
        this.question24 = questionIDs.get(23).getId();
        this.question24description = questionIDs.get(23).getDescription();
        this.question25 = questionIDs.get(24).getId();
        this.question25description = questionIDs.get(24).getDescription();
        this.question26= questionIDs.get(25).getId();
        this.question26description = questionIDs.get(25).getDescription();
        this.question27 = questionIDs.get(26).getId();
        this.question27description = questionIDs.get(26).getDescription();
        this.question28 = questionIDs.get(27).getId();
        this.question28description = questionIDs.get(27).getDescription();
        this.question29 = questionIDs.get(28).getId();
        this.question29description = questionIDs.get(28).getDescription();
        this.question30 = questionIDs.get(29).getId();
        this.question30description = questionIDs.get(29).getDescription();
        this.question31 = questionIDs.get(30).getId();
        this.question31description = questionIDs.get(30).getDescription();
        this.question32 = questionIDs.get(31).getId();
        this.question32description = questionIDs.get(31).getDescription();
        this.question33 = questionIDs.get(32).getId();
        this.question33description = questionIDs.get(32).getDescription();
        this.question34 = questionIDs.get(33).getId();
        this.question34description = questionIDs.get(33).getDescription();
        this.question35 = questionIDs.get(34).getId();
        this.question35description = questionIDs.get(34).getDescription();
        this.question36 = questionIDs.get(35).getId();
        this.question36description = questionIDs.get(35).getDescription();
        this.question37 = questionIDs.get(36).getId();
        this.question37description = questionIDs.get(36).getDescription();
        this.question38 = questionIDs.get(37).getId();
        this.question38description = questionIDs.get(37).getDescription();
        this.question39 = questionIDs.get(38).getId();
        this.question39description = questionIDs.get(38).getDescription();
        this.question40 = questionIDs.get(39).getId();
        this.question40description = questionIDs.get(39).getDescription();
        this.question41 = questionIDs.get(40).getId();
        this.question41description = questionIDs.get(40).getDescription();
        this.question42 = questionIDs.get(41).getId();
        this.question42description = questionIDs.get(41).getDescription();
        this.question43 = questionIDs.get(42).getId();
        this.question43description = questionIDs.get(42).getDescription();
        this.question44 = questionIDs.get(43).getId();
        this.question44description = questionIDs.get(43).getDescription();
        this.question45 = questionIDs.get(44).getId();
        this.question45description = questionIDs.get(44).getDescription();
        this.question46 = questionIDs.get(45).getId();
        this.question46description = questionIDs.get(45).getDescription();
        this.question47 = questionIDs.get(46).getId();
        this.question47description = questionIDs.get(46).getDescription();
        this.question48 = questionIDs.get(47).getId();
        this.question48description = questionIDs.get(47).getDescription();
    }
    @NotBlank
    private String question1Rating;
    @NotBlank
    private String question2Rating;
    @NotBlank
    private String question3Rating;
    @NotBlank
    private String question4Rating;
    @NotBlank
    private String question5Rating;
    @NotBlank
    private String question6Rating;
    @NotBlank
    private String question7Rating;
    @NotBlank
    private String question8Rating;
    @NotBlank
    private String question9Rating;
    @NotBlank
    private String question10Rating;
    @NotBlank
    private String question11Rating;
    @NotBlank
    private String question12Rating;
    @NotBlank
    private String question13Rating;
    @NotBlank
    private String question14Rating;
    @NotBlank
    private String question15Rating;
    @NotBlank
    private String question16Rating;
    @NotBlank
    private String question17Rating;
    @NotBlank
    private String question18Rating;
    @NotBlank
    private String question19Rating;
    @NotBlank
    private String question20Rating;
    @NotBlank
    private String question21Rating;
    @NotBlank
    private String question22Rating;
    @NotBlank
    private String question23Rating;
    @NotBlank
    private String question24Rating;
    @NotBlank
    private String question25Rating;
    @NotBlank
    private String question26Rating;
    @NotBlank
    private String question27Rating;
    @NotBlank
    private String question28Rating;
    @NotBlank
    private String question29Rating;
    @NotBlank
    private String question30Rating;
    @NotBlank
    private String question31Rating;
    @NotBlank
    private String question32Rating;
    @NotBlank
    private String question33Rating;
    @NotBlank
    private String question34Rating;
    @NotBlank
    private String question35Rating;
    @NotBlank
    private String question36Rating;
    @NotBlank
    private String question37Rating;
    @NotBlank
    private String question38Rating;
    @NotBlank
    private String question39Rating;
    @NotBlank
    private String question40Rating;
    @NotBlank
    private String question41Rating;
    @NotBlank
    private String question42Rating;
    @NotBlank
    private String question43Rating;
    @NotBlank
    private String question44Rating;
    @NotBlank
    private String question45Rating;
    @NotBlank
    private String question46Rating;
    @NotBlank
    private String question47Rating;
    @NotBlank
    private String question48Rating;
    
}
