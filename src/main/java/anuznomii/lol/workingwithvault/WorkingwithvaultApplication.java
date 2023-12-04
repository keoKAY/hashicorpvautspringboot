package anuznomii.lol.workingwithvault;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class WorkingwithvaultApplication  {

    public static void main(String[] args) {
//        load the .env file
        Dotenv.load();
        SpringApplication.run(WorkingwithvaultApplication.class, args);
    }


}
