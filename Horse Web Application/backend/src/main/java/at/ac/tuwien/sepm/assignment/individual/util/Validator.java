package at.ac.tuwien.sepm.assignment.individual.util;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.entity.Sport;
import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import at.ac.tuwien.sepm.assignment.individual.exception.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Validator {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());



    public static void validateNewSport(Sport sport) {

        if (sport.getName()==null) {
            throw new ValidationException("name cannot be null");
        }

        if (!validateName(sport.getName())){
            throw new ValidationException("The Name did not pass the Validator, please check your Input");
        }

        if (sport.getDesc()!=null) {
            if (!validateName(sport.getDesc())) {
                throw new ValidationException("The Description you entered was not accepted");
            }
        }


    }

    public static void validateNewHorse(Horse horse) {

        if (horse.getName()==null || horse.getDob()==null || horse.getSex()==null){
            throw new ValidationException("name, Date of Birth and Sex cannot be null");
        }
        if (!validateName(horse.getName())){
            throw new ValidationException("The Name did not pass the Validator, please check your Input");
        }
        if (LocalDate.now().compareTo(horse.getDob())<0){
            throw new ValidationException("The Horse was never born");
        }

        if (!(horse.getSex().toLowerCase().equals("male") || horse.getSex().toLowerCase().equals("female"))){
            throw new ValidationException("The Sex you entered was not accepted");
        }

        validateOptionalHorseInformation(horse);

    }

    public static void validateGivenHorse(Horse horse){

        if (horse.getName()!=null){
            if (!validateName(horse.getName())){
                throw new ValidationException("The Name did not pass the Validator, please check your Input");
            }
        }
        if (horse.getDob()!=null){
            if (LocalDate.now().compareTo(horse.getDob())<0){
                throw new ValidationException("The Horse was never born");
            }
        }
        if (horse.getSex()!=null){
            if (!(horse.getSex().toLowerCase().equals("male") || horse.getSex().toLowerCase().equals("female"))){
                throw new ValidationException("The Sex you entered was not accepted");
            }
        }
        validateOptionalHorseInformation(horse);

    }


    public static void validateOptionalHorseInformation(Horse horse){
        if (horse.getDesc()!=null) {
            if (!validateName(horse.getDesc())) {
                throw new ValidationException("The Description you entered was not accepted");
            }
        }



        if (horse.getFavoriteSport()!=null){
            validateNewSport(horse.getFavoriteSport());
        }
        if (horse.getDad()!= null){
            if (!horse.getDad().getSex().toLowerCase().equals("male")){
                throw new ValidationException("You entered the false Sex for your horse's dad");
            }
            validateNewHorse(horse.getDad());
            if (horse.getDad().getDob().compareTo(horse.getDob())>=0){
                throw new ValidationException("Parents cannot be younger than their children");
            }
        }
        if (horse.getMom()!=null){
            if (!horse.getMom().getSex().toLowerCase().equals("female")){
                throw new ValidationException("You entered the false Sex for your horse's mom");
            }
            validateNewHorse(horse.getMom());
            if (horse.getMom().getDob().compareTo(horse.getDob())>=0){
                throw new ValidationException("Parents cannot be younger than their children");
            }
        }
    }



    public static boolean validateName(String input) {
        String expression = "^[a-zA-Z\\s]+";
        return input.matches(expression);

    }

    public static boolean validateDescription(String input) {
        String expression = "/[\\pL\\pN_\\-]+/";
        return input.matches(expression);

    }

}
