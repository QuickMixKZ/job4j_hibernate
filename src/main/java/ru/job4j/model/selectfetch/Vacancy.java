package ru.job4j.model.selectfetch;

import javax.persistence.*;

@Entity
@Table(name = "vacancy")
public class Vacancy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "base_id")
    private VacanciesBase base;

    public Vacancy() {
    }

    public Vacancy(String name, VacanciesBase base) {
        this.name = name;
        this.base = base;
    }
}
