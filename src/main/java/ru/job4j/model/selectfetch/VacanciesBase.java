package ru.job4j.model.selectfetch;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "vacancies_base")
public class VacanciesBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @OneToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;
    @OneToMany(mappedBy = "base")
    private Set<Vacancy> vacancies = new HashSet<>();

    public VacanciesBase() {
    }

    public VacanciesBase(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public Set<Vacancy> getVacancies() {
        return vacancies;
    }

    public void setVacancies(Set<Vacancy> vacancies) {
        this.vacancies = vacancies;
    }

    public boolean addVacancy(Vacancy vacancy) {
        return vacancies.add(vacancy);
    }

    @Override
    public String toString() {
        return "VacanciesBase{"
               + "id=" + id
               + ", name='" + name + '\''
               + ", candidate=" + candidate
               + ", vacancies=" + vacancies
               + '}';
    }
}
