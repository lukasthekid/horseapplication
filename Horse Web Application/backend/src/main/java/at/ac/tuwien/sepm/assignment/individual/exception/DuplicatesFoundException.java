package at.ac.tuwien.sepm.assignment.individual.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class DuplicatesFoundException extends ServiceException{


    public DuplicatesFoundException(String message) {
        super(message);
    }


}
