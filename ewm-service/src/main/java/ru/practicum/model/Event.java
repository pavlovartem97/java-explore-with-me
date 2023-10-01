package ru.practicum.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.dto.enums.State;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.EnumType.STRING;

@Entity
@Getter
@Setter
@Table(name = "ewm_event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 2000)
    String annotation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    Category category;

    @ManyToMany(mappedBy = "events", fetch = FetchType.LAZY)
    @Setter(AccessLevel.NONE)
    List<Compilation> compilations = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    @NotNull
    @Column(nullable = false, length = 7000)
    String description;

    @Column(nullable = false)
    LocalDateTime eventDate;

    @Column(nullable = false)
    Boolean paid;

    @Column(nullable = false)
    Integer participantLimit;

    @Column(nullable = false)
    Boolean requestModeration;

    @Column(nullable = false, length = 120)
    String title;

    @Column(nullable = false)
    Double lat;

    @Column(nullable = false)
    Double lon;

    @Setter(AccessLevel.NONE)
    @Column(nullable = false, updatable = false)
    LocalDateTime createdOn = LocalDateTime.now();

    @Column(nullable = false, length = 10)
    @Enumerated(STRING)
    State state = State.PENDING;

    LocalDateTime publishedOn;
}
