package com.example.demo;

import org.apache.commons.lang3.RandomUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@Aspect
public class FaithfulAspect {

    @Autowired
    private VehicleDao vehicleDao;

    @Pointcut("execution(* com.example..*(..))")
    public void allMethods() { }

    @Before("allMethods()")
    public void printRandomVehicle(final JoinPoint joinPoint) {
        doPrintRandomVehicle();
    }

    @After("allMethods()")
    public void deleteRandomAndPraise(final JoinPoint joinPoint) {
        doRandomAndPraise();
    }

    public void doPrintRandomVehicle() {
        int randomID = RandomUtils.nextInt(0, 100);
        Vehicle v = vehicleDao.getById(randomID);
        if (v != null) {
            System.out.println(v);
        } else {
            System.out.println("That vehicle doesn't exist.");
        }
    }

    private void doRandomAndPraise() {
        int randomID = RandomUtils.nextInt(0, 100);
        Vehicle v = vehicleDao.getById(randomID);
        if (v != null) {
            vehicleDao.delete(v);
            System.out.println("Vehicle successfully deleted");
        } else {
            System.out.println("That vehicle doesn't exist.");
        }
        System.out.println("Praise God!");
    }

    public void eat() {
        System.out.println("Eating");
        eatWithFriends();
    }

    private void eatWithFriends() {
        System.out.println("Eating with friends");
    }

    public void study() {
        System.out.println("Study");
        meetWithFriends();
    }

    private void meetWithFriends() {
        System.out.println("Meet with friends");
    }

    public void playGuitar() {
        System.out.println("Play guitar");
        playGuitarAtChurch();
    }

    private void playGuitarAtChurch() {
        System.out.println("Play guitar at church");
    }

}
