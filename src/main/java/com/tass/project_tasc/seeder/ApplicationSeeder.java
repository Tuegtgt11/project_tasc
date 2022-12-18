package com.tass.project_tasc.seeder;

import com.tass.project_tasc.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationSeeder implements CommandLineRunner {
    @Autowired
    UserSeeder userSeeder;


    @Autowired
    UserRepository userRepository;


    boolean seed = true;

    @Override
    public void run(String... args) throws Exception {
        if (seed){
            userRepository.deleteAll();

            userSeeder.generate();

        }
    }
}
