package com.udacity.vehicles.service;

import com.udacity.vehicles.client.maps.MapsClient;
import com.udacity.vehicles.client.prices.Price;
import com.udacity.vehicles.client.prices.PriceClient;
import com.udacity.vehicles.domain.Location;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.CarRepository;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Implements the car service create, read, update or delete
 * information about vehicles, as well as gather related
 * location and price data when desired.
 */
@Service
public class CarService {

    private final CarRepository repository;
    private MapsClient mapsClient;
    private PriceClient priceClient;

    public CarService(CarRepository repository, @Qualifier("maps")WebClient mapsWebClient, @Qualifier("pricing")
            WebClient priceWebClient) {
        this.repository = repository;
        this.priceClient = new PriceClient(priceWebClient);
        this.mapsClient = new MapsClient(mapsWebClient, new ModelMapper());
    }

    /**
     * Gathers a list of all vehicles
     * @return a list of all vehicles in the CarRepository
     */
    public List<Car> list() {

        List<Car> carList= new ArrayList<>();

        repository.findAll().forEach(car -> {car.setPrice(priceClient.getPrice(car.getId()));
        car.setLocation(mapsClient.getAddress(new Location(car.getLocation().getLat(), car.getLocation().getLon())));

        carList.add(car);
        }
                );

        return repository.findAll();
    }

    /**
     * Gets car information by ID (or throws exception if non-existent)
     * @param id the ID number of the car to gather information on
     * @return the requested car's information, including location and price
     */
    public Car findById(Long id) {
        Car car = repository.findById(id).orElseThrow(() -> new CarNotFoundException
                ("Car Id=" + id + " not found."));

        car.setPrice(priceClient.getPrice(id));

        car.setLocation(mapsClient.getAddress(new Location(car.getLocation().getLat(), car.getLocation().getLon())));

        return car;
    }

    /**
     * Either creates or updates a vehicle, based on prior existence of car
     * @param car A car object, which can be either new or existing
     * @return the new/updated car is stored in the repository
     */
    public Car save(Car car) {
        if (car.getId() != null) {
            return repository.findById(car.getId())
                    .map(carToBeUpdated -> {
                        carToBeUpdated.setDetails(car.getDetails());
                        carToBeUpdated.setLocation(car.getLocation());
                        return repository.save(carToBeUpdated);
                    }).orElseThrow(CarNotFoundException::new);
        }

        return repository.save(car);
    }

    /**
     * Deletes a given car by ID
     * @param id the ID number of the car to delete
     */
    public void delete(Long id) throws CarNotFoundException{
        Car car = repository.findById(id).orElseThrow(() -> new CarNotFoundException
                ("Car Id=" + id + " not found."));

        repository.delete(car);
    }

}
