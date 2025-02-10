package ru.job4j.github.analysis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class GitHubAnalysis {

    public static void main(String[] args) {
        SpringApplication.run(GitHubAnalysis.class, args);
        System.out.println("Go to http://localhost:8080/");
    }
}
