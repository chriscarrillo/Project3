package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class Controller {

    @Autowired
    private VehicleDao vehicleDao;

    @Autowired
    private GreetingDao greetingDao;

    @RequestMapping(value="/createGreeting", method=RequestMethod.POST)
    public Greeting createGreeting(@RequestBody String name) throws IOException {
        Greeting newGreeting = new Greeting(name);
        greetingDao.create(newGreeting);
        return newGreeting;
    }

    @RequestMapping(value="/getGreeting/{id}", method=RequestMethod.GET)
    public Greeting getGreeting(@PathVariable("id") int id) throws IOException {
        return greetingDao.getById(id);
    }

    /**
     * Insert a new vehicle record into the database
     * @param newVehicle the vehicle to be entered in the database
     * @return the new vehicle
     */
    @RequestMapping(value="/addVehicle", method=RequestMethod.POST)
    public Vehicle addVehicle(@RequestBody Vehicle newVehicle) throws IOException {
        vehicleDao.create(newVehicle);
        return newVehicle;
    }

    /**
     * Query from the database and return the vehicle if found
     * @param id the id of the vehicle to be found
     * @return the found vehicle
     */
    @RequestMapping(value="/getVehicle/{id}", method=RequestMethod.GET)
    public Vehicle getVehicle(@PathVariable("id") int id) throws IOException {
        return vehicleDao.getById(id);
    }

    /**
     * Update an existing vehicle in the database
     * @param newVehicle the new vehicle to replace the existing one
     * @return the updated vehicle
     */
    @RequestMapping(value = "/updateVehicle", method = RequestMethod.PUT)
    public Vehicle updateVehicle(@RequestBody Vehicle newVehicle) throws IOException {
        if (newVehicle != null) {
            vehicleDao.update(newVehicle);
            return getVehicle(newVehicle.getId());
        } else {
            return null;
        }
    }

    /**
     * Delete the record from the database if found
     * @param id the id of the vehicle to be deleted
     * @return ResponseEntity
     */
    @RequestMapping(value = "/deleteVehicle/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteVehicle(@PathVariable("id") int id) throws IOException {
        if (getVehicle(id) != null) {
            vehicleDao.delete(getVehicle(id));
            return new ResponseEntity("Deleted", HttpStatus.OK);
        }
        return new ResponseEntity("Not Found", HttpStatus.BAD_REQUEST);
    }

    /**
     * Returns the 10 most recent entries in the database
     * @return List of Vehicle of 10 most recent entries
     */
    @RequestMapping(value = "/getLatestVehicles", method = RequestMethod.GET)
    public List<Vehicle> getLatestVehicles() throws IOException {
        return vehicleDao.getLatestVehicles("SELECT * FROM vehicles ORDER BY id DESC LIMIT 10");
    }
}
