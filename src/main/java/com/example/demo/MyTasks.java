package com.example.demo;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;

@Component
public class MyTasks {

    RestTemplate restTemplate = new RestTemplate();
    private String url = "https://earnest-sandbox-184720.appspot.com/"; // <-- Cloud URL

    // Adds a new vehicle with random values every 1 second
    @Scheduled(cron = "1/2 * * * * *")
    public void addVehicle() {
        Random rand = new Random();
        RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange('A', 'z').build();
        int randomYear = rand.nextInt((2016 - 1986) + 1) + 1986;
        double randomPrice = (double) rand.nextInt((45000 - 15000) + 1) + 15000;

        String addUrl = url + "addVehicle";
        Vehicle vehicle = new Vehicle(0, generator.generate(35), randomYear, randomPrice);
        restTemplate.postForObject(addUrl, vehicle, Vehicle.class);
    }

    // Deletes a random vehicle with an id between 1 and 100 every 10 seconds
    @Scheduled(cron = "*/10 * * * * *")
    public void deleteVehicle() {
        int deleteID = RandomUtils.nextInt(0, 100);

        String getUrl = url + "getVehicle/" + deleteID;
        String deleteUrl = url + "deleteVehicle/" + deleteID;

        Vehicle v = restTemplate.getForObject(getUrl, Vehicle.class);
        if (v != null) {
            restTemplate.delete(deleteUrl);
            System.out.println("Successfully deleted: " + v);
        }
    }

    // Updates a random vehicle every minute with hard-coded values
    @Scheduled(cron = "* */1 * * * *")
    public void updateVehicle() {
        int updateID = RandomUtils.nextInt(0, 100);
        Vehicle vehicle = new Vehicle(updateID, "Toyota Corolla", 2009, 10000.0);

        String updateUrl = url + "updateVehicle";
        String getUrl = url + "getVehicle/" + updateID;


        Vehicle getVehicle = restTemplate.getForObject(getUrl, Vehicle.class);
        if (getVehicle != null) {
            restTemplate.put(updateUrl, vehicle, Vehicle.class);
            System.out.println("Successfully updated: " + getVehicle);
        }
    }

    // Returns the 10 most recent vehicles at the top of every hour
    @Scheduled(cron = "0 0 * * * *")
    public void latestVehiclesReport() {
        String getLatestUrl = url + "getLatestVehicles";
        List<Vehicle> recentVehicles = restTemplate.getForObject(getLatestUrl, List.class);
        System.out.println("-----LATEST VEHICLES-----");
        for (int i = 0; i < recentVehicles.size(); i++) {
            System.out.println(recentVehicles.get(i));
        }
        System.out.println("-------------------------");
    }

}
