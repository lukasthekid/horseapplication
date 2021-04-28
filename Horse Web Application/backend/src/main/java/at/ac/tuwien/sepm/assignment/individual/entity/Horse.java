package at.ac.tuwien.sepm.assignment.individual.entity;

import java.time.LocalDate;


public class Horse {
    private Long id;
    private String name;
    private String desc;
    private LocalDate dob;
    private String sex;
    private Sport favoriteSport;
    private Horse dad;
    private Horse mom;

    public Horse(Long id, String name, String desc, LocalDate dob, String sex, Sport favoriteSport, Horse dad, Horse mom) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.dob = dob;
        this.sex = sex;
        this.favoriteSport = favoriteSport;
        this.dad = dad;
        this.mom = mom;
    }

    public Horse() {
    }

    public Horse(String name, LocalDate dob, String sex) {
        this.name = name;
        this.dob = dob;
        this.sex = sex;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Sport getFavoriteSport() {
        return favoriteSport;
    }

    public void setFavoriteSport(Sport favoriteSport) {
        this.favoriteSport = favoriteSport;
    }

    public Horse getDad() {
        return dad;
    }

    public void setDad(Horse dad) {
        this.dad = dad;
    }

    public Horse getMom() {
        return mom;
    }

    public void setMom(Horse mom) {
        this.mom = mom;
    }

    @Override
    public String toString() {
        return "Horse{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", desc='" + desc + '\'' +
            ", dob=" + dob +
            ", sex=" + sex +
            ", favoriteSport=" + favoriteSport +
            '}';
    }



}
