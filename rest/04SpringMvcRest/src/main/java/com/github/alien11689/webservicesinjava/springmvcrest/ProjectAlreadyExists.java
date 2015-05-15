package com.github.alien11689.webservicesinjava.springmvcrest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Project already exists")
public class ProjectAlreadyExists extends RuntimeException {
}
