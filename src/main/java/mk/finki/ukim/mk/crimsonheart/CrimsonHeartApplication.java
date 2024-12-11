package mk.finki.ukim.mk.crimsonheart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class CrimsonHeartApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrimsonHeartApplication.class, args);
    }

}
