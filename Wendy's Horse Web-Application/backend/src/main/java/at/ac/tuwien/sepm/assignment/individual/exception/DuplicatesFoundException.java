package at.ac.tuwien.sepm.assignment.individual.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class DuplicatesFoundException extends ServiceException{

    private static final long serialVersionUID = -7766710819642880317L;

    public DuplicatesFoundException(String message) {
        super(message);
    }


}
