package at.ac.tuwien.sepm.assignment.individual.endpoint.dto;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.entity.Sport;

import java.time.LocalDate;

public class HorseDto {
    private Long id;
    private String name;
    private String desc;
    private LocalDate dob;
    private String sex;
    private SportDto favoriteSport;
    private HorseDto dad;
    private HorseDto mom;

    public HorseDto(Long id, String name, String desc, LocalDate dob, String sex, SportDto favoriteSport, HorseDto dad, HorseDto mom) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.dob = dob;
        this.sex = sex;
        this.favoriteSport = favoriteSport;
        this.dad = dad;
        this.mom = mom;
    }

    public HorseDto() {

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


    public HorseDto getDad() {
        return dad;
    }

    public void setDad(HorseDto dad) {
        this.dad = dad;
    }

    public HorseDto getMom() {
        return mom;
    }

    public void setMom(HorseDto mom) {
        this.mom = mom;
    }

    public Long getId() { return id; }

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

    public SportDto getFavoriteSport() {
        return favoriteSport;
    }

    public void setFavoriteSport(SportDto favoriteSport) {
        this.favoriteSport = favoriteSport;
    }
}
