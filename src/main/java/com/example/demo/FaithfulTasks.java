package com.example.demo;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class FaithfulTasks {

    @Scheduled(cron = "*/10 * * * * *")
    public void eat() {
        FaithfulAspect f = new FaithfulAspect();
        f.eat();
    }

    @Scheduled(cron = "*/5 * * * * *")
    public void study() {
        FaithfulAspect f = new FaithfulAspect();
        f.study();
    }

    @Scheduled(cron = "*/1 * * * * *")
    public void playGuitar() {
        FaithfulAspect f = new FaithfulAspect();
        f.playGuitar();
    }

}
