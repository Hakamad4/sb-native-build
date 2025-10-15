package br.com.hakamada.sbnativebuild.service;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SecondService {

    private static final Logger log = LoggerFactory.getLogger(SecondService.class);

    @PostConstruct
    public void init() {
        log.info("SecondService init");
    }

    public String example() {
        return "Second Service Call!";
    }

}
