/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package giuakyjava;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

interface ICar {
    void showInfo();
}

class Vehicle implements ICar {
    protected String ID;
    protected String brand;
    protected int publishYear;
    protected double price;
    protected String color;

    public Vehicle(String ID, String brand, int publishYear, double price, String color) {
        this.ID = ID;
        this.brand = brand;
        this.publishYear = publishYear;
        this.price = price;
        this.color = color;
    }

    @Override
    public void showInfo() {
        System.out.println("ID: " + ID);
        System.out.println("Brand: " + brand);
        System.out.println("Year: " + publishYear);
        System.out.println("Price: " + price);
        System.out.println("Color: " + color);
    }
}

class Car extends Vehicle {
    int slots;
    String engineType;

    public Car(String ID, String brand, int publishYear, double price, String color, int slots, String engineType) {
        super(ID, brand, publishYear, price, color);
        this.slots = slots;
        this.engineType = engineType;
    }

    @Override
    public void showInfo() {
        super.showInfo();
        System.out.println("Slots: " + slots);
        System.out.println("Engine Type: " + engineType);
    }
}


class Motorcycle extends Vehicle {
    double capacity;

    public Motorcycle(String ID, String brand, int publishYear, double price, String color, double capacity) {
        super(ID, brand, publishYear, price, color);
        this.capacity = capacity;
    }

    @Override
    public void showInfo() {
        super.showInfo();
        System.out.println("Capacity: " + capacity);
    }
}

class Truck extends Vehicle {
    double loadWeight;

    public Truck(String ID, String brand, int publishYear, double price, String color, double loadWeight) {
        super(ID, brand, publishYear, price, color);
        this.loadWeight = loadWeight;
    }

    @Override
    public void showInfo() {
        super.showInfo();
        System.out.println("Load Weight: " + loadWeight);
    }
}

class CarManager {
    final List<ICar> vehicles;

    public CarManager() {
        this.vehicles = new ArrayList<>();
    }

    // Create
    public void addCar(ICar vehicle) {
        vehicles.add(vehicle);
    }

    // Read
    public void displayAllCars() {
        for (ICar vehicle : vehicles) {
            vehicle.showInfo();
            System.out.println();
        }
    }

    // Update
    // You can add update methods as needed
    public void updateAllCars()
    {
    
    }

    // Delete
    public void removeCar(ICar vehicle) {
        vehicles.remove(vehicle);
    }

    // Write to file
    public void writeToFile(String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (ICar vehicle : vehicles) {
                if (vehicle instanceof Vehicle v) {
                    switch (vehicle) {
                        case Car car -> writer.println("Car," + v.ID + "," + v.brand + "," + v.publishYear + "," + v.price + "," + v.color + "," + car.slots + "," + car.engineType);
                        case Motorcycle motorcycle -> writer.println("Motorcycle," + v.ID + "," + v.brand + "," + v.publishYear + "," + v.price + "," + v.color + "," + motorcycle.capacity);
                        case Truck truck -> writer.println("Truck," + v.ID + "," + v.brand + "," + v.publishYear + "," + v.price + "," + v.color + "," + truck.loadWeight);
                        default -> {
                        }
                    }
                }
            }
            System.out.println("Viet du lieu vao file thanh cong.");
        } catch (IOException e) {
            System.err.println("Loi khong co du lieu nao duoc ghi: " + e.getMessage());
        }
    }

    // Read from file
    public void readFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 7) {
                    String type = parts[0];
                    String ID = parts[1];
                    String brand = parts[2];
                    int publishYear = Integer.parseInt(parts[3]);
                    double price = Double.parseDouble(parts[4]);
                    String color = parts[5];
                    if (type.equals("Car")) {
                        int slots = Integer.parseInt(parts[6]);
                        String engineType = parts[7];
                        addCar(new Car(ID, brand, publishYear, price, color, slots, engineType));
                    } else if (type.equals("Motorcycle")) {
                        double capacity = Double.parseDouble(parts[6]);
                        addCar(new Motorcycle(ID, brand, publishYear, price, color, capacity));
                    } else if (type.equals("Truck")) {
                        double loadWeight = Double.parseDouble(parts[6]);
                        addCar(new Truck(ID, brand, publishYear, price, color, loadWeight));
                    }
                }
            }
            System.out.println("Doc tu file.");
        } catch (IOException e) {
            System.err.println("Khong doc duoc du lieu tu file " + e.getMessage());
        }
    }
}
    // Lớp chính
public class GiuaKyJava {
    public static void main(String[] args) {
        // Tạo đối tượng quản lý xe
        CarManager manager = new CarManager();

        // Thêm một số phương tiện
        manager.addCar(new Car("1", "Toyota", 2020, 25000.00, "Đỏ", 5, "Xăng"));
        manager.addCar(new Motorcycle("2", "Honda", 2018, 8000.00, "Đen", 250.0));
        manager.addCar(new Truck("3", "Ford", 2015, 40000.00, "Xanh", 5000.0));

        // Hiển thị thông tin tất cả các xe
        System.out.println("Thông tin tất cả các xe:");
        manager.displayAllCars();

        // Xóa một phương tiện
        System.out.println("\nXóa phương tiện có ID: 1");
        for (ICar vehicle : manager.vehicles) {
            if (vehicle instanceof Vehicle && ((Vehicle) vehicle).ID.equals("1")) {
                manager.removeCar(vehicle);
                break;
            }
        }
        for (ICar vehicle : manager.vehicles) {
            if (vehicle instanceof Vehicle && ((Vehicle) vehicle).ID.equals("2")) {
                manager.removeCar(vehicle);
                break;
            }
        }

        // Hiển thị thông tin sau khi xóa
        System.out.println("\nThông tin sau khi xóa:");
        manager.displayAllCars();

        // Ghi vào file
        manager.writeToFile("xe.txt");
        
        // Đọc từ file
        CarManager newManager = new CarManager();
        newManager.readFromFile("xe.txt");
        
        // Hiển thị thông tin sau khi đọc từ file
        System.out.println("\nThông tin sau khi đọc từ file:");
        newManager.displayAllCars();
    }
    //ss
}

