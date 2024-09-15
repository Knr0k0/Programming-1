package service;

import model.Car;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CarService {
    private final List<Car> cars;
    private static final String filePath = "src/database/Cars.txt"; // Path to the file

    public CarService() {
        this.cars = new ArrayList<>();
        loadCarsFromFile();
    }

    public void addCar(Car car) {
        cars.add(car);
        saveCarsToFile();
    }

    public Car getCarById(String carId) {
        return cars.stream().filter(car -> car.getCarId().equals(carId)).findFirst().orElse(null);
    }

    public void updateCar(Car car) {
        Car existingCar = getCarById(car.getCarId());
        if (existingCar != null) {
            cars.remove(existingCar);
            cars.add(car);
            saveCarsToFile();
        }
    }

    public void deleteCar(String carId) {
        cars.removeIf(car -> car.getCarId().equals(carId));
        saveCarsToFile();
    }

    public List<Car> getAllCars() {
        return new ArrayList<>(cars);
    }

    public List<Car> getSoldCars() {
        return cars.stream()
                .filter(car -> "sold".equalsIgnoreCase(car.getStatus()))  // Filter by status
                .collect(Collectors.toList());
    }

    private void loadCarsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 9) {
                    Car car = new Car(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]), Integer.parseInt(parts[4]),
                            parts[5], parts[6], Integer.parseInt(parts[7]), parts[8], Collections.singletonList(parts[9]));
                    cars.add(car);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveCarsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Car car : cars) {
                writer.write(String.format("%s,%s,%s,%d,%d,%s,%s,%.0f,%s%n",
                        car.getCarId(), car.getMaker(), car.getModel(), car.getYear(),
                        car.getMileage(), car.getColor(), car.getStatus(), car.getPrice(),
                        car.getNotes()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
