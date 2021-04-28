package at.ac.tuwien.sepm.assignment.individual;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@SpringBootApplication
public class WendysRennpferdeApplication {

    public static void main(String[] args) {
        SpringApplication.run(WendysRennpferdeApplication.class, args);
    }


}

