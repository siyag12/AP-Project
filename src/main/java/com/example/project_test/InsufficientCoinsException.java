package com.example.project_test;

public class InsufficientCoinsException extends Exception
{
    public InsufficientCoinsException()
    {
        super("You dont have enough coins");
    }

}
