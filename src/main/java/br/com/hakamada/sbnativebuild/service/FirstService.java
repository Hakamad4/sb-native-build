package br.com.hakamada.sbnativebuild.service;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FirstService {

    private static final Logger log = LoggerFactory.getLogger(FirstService.class);

    @PostConstruct
    public void init() {
        log.info("FirstService init");
    }

    public String example() {
        return "First Service Call!";
    }
}
