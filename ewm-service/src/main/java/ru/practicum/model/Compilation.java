package ru.practicum.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "ewm_compilation")
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String title;

    @Column(nullable = false)
    Boolean pinned;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "compilation", orphanRemoval = true)
    @Setter(AccessLevel.NONE)
    @OrderBy("id")
    private List<Event> events = new ArrayList<>();
}
