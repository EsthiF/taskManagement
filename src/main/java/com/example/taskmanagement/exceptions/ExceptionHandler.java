package com.example.taskmanagement.exceptions;

import java.util.LinkedList;

public class ExceptionHandler {
    private LinkedList<Exception> exceptions;
    private Exception e;
    private String response;


    public ExceptionHandler(Exception e) {
        if (e.getClass().equals(ApplicationException.class)){
            System.out.println("if was right");
        }

    }}
