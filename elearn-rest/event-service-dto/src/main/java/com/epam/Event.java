package com.epam;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "event")
@Getter
@Setter
@NoArgsConstructor
public class Event extends RepresentationModel<Event> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String place;
    private String speaker;
    private String eventType;
    private LocalDateTime dateTime;

    @JsonCreator
    public Event(@JsonProperty("id") long id,
                 @JsonProperty("title") String title,
                 @JsonProperty("place") String place,
                 @JsonProperty("speaker") String speaker,
                 @JsonProperty("eventType") String eventType,
                 @JsonProperty("dateTime") LocalDateTime dateTime) {
        this.id = id;
        this.title = title;
        this.place = place;
        this.speaker = speaker;
        this.eventType = eventType;
        this.dateTime = dateTime;
    }
}
