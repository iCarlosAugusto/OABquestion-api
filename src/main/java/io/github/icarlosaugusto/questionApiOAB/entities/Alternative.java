package io.github.icarlosaugusto.questionApiOAB.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
public class Alternative {

    @Id
    //@GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String text;

    @JsonIgnore
    private boolean correct;

    private String alternativeLetter;

    @ManyToOne()
    @JoinColumn(name = "question_id")
    @JsonIgnore
    private Question question;


    public void setAlternativeLetterByIndex(int index){
        switch (index) {
            case 0:
                this.setAlternativeLetter("a");
                break;
            case 1:
                this.setAlternativeLetter("b");
                break;
            case 2:
                this.setAlternativeLetter("c");
                break;
            case 3:
                this.setAlternativeLetter("d");
                break;
            case 4:
                this.setAlternativeLetter("e");
                break;
            default:
                System.out.println("Invalid alternative letter");
                break;
        }
    }
}
