package com.dealership;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

public class UserInterFace {
    private Dealership dealership;
    private final DealershipFileManager fileManager = new DealershipFileManager();

    private void init() {
        dealership = fileManager.getDealership();
    }
    public void display() {
        init();
        if (dealership == null) {
            System.out.print("Could nt load dealership");
            return;
        }
        while (true) {
            System.out.println("""
                                        1 - Find vehicles by price
                                        2 - Find vehicles by make/model
                                        3 - Find vehicles by year
                                        4 - Find vehicles by color
                                        5 - Find vehicles by mileage
                                        6 - Find vehicles by type
                                        7 - List all vehicles
                                        8 - Add a vehicle
                                        9 - Remove a vehicle
                                        10 - Buy Or Lease Vehicle
                                        99 - Quit
                    """);
            int choice = Console.promptForInt("Enter the choice: ");
            try {
                switch(choice) {
                    case 1 -> processGetByPriceRequest();
                    case 2 -> processGetByMakeModelRequest();
                    case 3 -> processGetByYearRequest();
                    case 4 -> processGetByColorRequest();
                    case 5 -> processGetByMileageRequest();
                    case 6 -> processGetByVehicleTypeRequest();
                    case 7 -> processGetAllVehiclesRequest();
                    case 8 -> processAddVehicleRequest();
                    case 9 -> processRemoveVehicleRequest();
                    case 10 -> processBuyOrLeaseVehicle();
                    case 99 -> {
                        System.out.println("Goodbye!");
                        return;
                    }
                    default -> System.out.println("Invalid choice.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
    private void processGetByPriceRequest() {
        dealership.getVehicleByPrice();
    }

    private void processGetByMakeModelRequest() {
        dealership.getVehicleByMakeModel();
    }

    private void processGetByYearRequest() {
        dealership.getVehicleByYear();
    }

    private void processGetByColorRequest() {
        dealership.getVehicleByColor();
    }

    private void processGetByMileageRequest() {
        dealership.getVehicleByMileage();
    }

    private void processGetByVehicleTypeRequest() {
        dealership.getVehicleByType();
    }

    private void processGetAllVehiclesRequest() {
        dealership.getAllTheVehicles();
    }
    private void processAddVehicleRequest() {

        String vinNumber = Console.promptForString("Enter The Vin number");
        Long vin = Long.parseLong(vinNumber);
        for (Vehicle car : dealership.getAllVehicles()) {
            if (car.getVin() == vin) {
                System.out.print("Vin already exits") ;
                return;
            }
        }
        int year = Console.promptForInt("Enter The year");
        String make =Console.promptForString("Enter The make");
        String model =Console.promptForString("Enter The model");
        String vehicleType =Console.promptForString("Enter The vehicle type");
        String color =Console.promptForString("Enter The color");
        int odometer = Console.promptForInt("Enter The odometer reading");
        double doublePrice = Console.promptForDouble("Enter the price");
        BigDecimal price = new BigDecimal(doublePrice);

        Vehicle vehicle = new Vehicle(
                vin, year, make, model, vehicleType, color, odometer, price
        );
        dealership.addVehicle(vehicle);
        fileManager.saveDealership(dealership);
    }
    private void processRemoveVehicleRequest() {
        String vinNumber = Console.promptForString("Enter vin number");
        long vin = Long.parseLong(vinNumber);
        Vehicle vehicleToRemove = null;

        for (Vehicle car : dealership.getAllVehicles()) {
            if (car.getVin() == vin) {
                vehicleToRemove = car;
                break;
            }
        }
        if (vehicleToRemove == null) {
            System.out.println("Vehicle not found.");
            return;
        }
        dealership.removeVehicle(vehicleToRemove);
        fileManager.saveDealership(dealership);
    }

    private void processBuyOrLeaseVehicle() {

        processGetAllVehiclesRequest();

        System.out.println("1 - Buy a Vehicle");
        System.out.println("2 - Lease a Vehicle");
        System.out.println("0 - Cancel");
        System.out.println("-".repeat(115));


        int choice = Console.promptForInt("Enter your choice: ");

        switch (choice) {
            case 1 -> processSellVehicle();
            case 2 -> processLeaseVehicle();
            case 0 -> System.out.println("Cancelled.");
            default -> System.out.println("Invalid choice.");
        }
    }
    public void processSellVehicle() {
        dealership.getAllTheVehicles();
        String choice = Console.promptForString("Enter the Vin number: ");
        Long longChoice = Long.parseLong(choice);

        for (Vehicle car : dealership.getAllVehicles()) {

            if (car.getVin() == longChoice) {
                System.out.print(car);
                String vehicleChoice = Console.promptForString(
                        "\nIs this is the vehicle your lookign for to buy? Yes or no: "
                );
                if (vehicleChoice.equalsIgnoreCase("yes")) {
                    LocalDate date = LocalDate.now();

                    String customerName = Console.promptForString("Enter your name");
                    String customerEmail = Console.promptForString("Enter the email address: ");
                    Vehicle vehicle = car;

                    String financeOption = Console.promptForString("Enter yes wish to get finance option from us yes or no?: ");
                    boolean finance = false;
                    if (financeOption.equalsIgnoreCase("yes")) {
                        finance = true;
                    }
                    SalesContract contract = new SalesContract(date, customerName, customerEmail, vehicle, finance);

                    System.out.println("Total Price: $" + contract.getTotalPrice());

                    System.out.println("========== SALES CONTRACT ==========");
                    System.out.println("Customer: " + customerName);
                    System.out.println("Vehicle: " + vehicle);
                    System.out.println("Total Price: $" + contract.getTotalPrice());

                    if (finance) {
                        System.out.println("Monthly Payment: $" + contract.getMonthlyPayment());
                    } else {
                        System.out.println("Payment Method: Full Payment");
                    }

                    ContractDataManager dataManager = new ContractDataManager();
                    String agreeToBuy = Console.promptForString("PLease enter yes if you wish to buy this vehicle");
                    if (agreeToBuy.equalsIgnoreCase("yes")) {

                        ContractDataManager.saveContract(contract);


                        System.out.println("Vehicle sold successfully!");

                        return;
                    }
                }
            }
        }

    }
    public void processLeaseVehicle() {
        dealership.getAllTheVehicles();
        String choice = Console.promptForString("Enter the vin number");
        Long longChoice = Long.parseLong(choice);

        for (Vehicle car : dealership.getAllVehicles()) {
            int carAge = LocalDate.now().getYear();
            int agedCars = carAge - car.getYear();
            if (car.getVin() == longChoice) {
                if (carAge > 3) {
                    System.out.println("This vehicle is too old to lease.");
                    return;
                }

                System.out.println(car);

                String leaseChoice = Console.promptForString("Is this the car you are looking for to lease? yes or No?");
                if (leaseChoice.equalsIgnoreCase("yes")) {
                    LocalDate date = LocalDate.now();

                    String customerName = Console.promptForString("Enter your name");
                    String customerEmail = Console.promptForString("Enter the email address: ");
                    Vehicle vehicle = car;

                    LeaseContract lease = new LeaseContract(date, customerName, customerEmail, vehicle);


                    System.out.println("========== LEASE CONTRACT ==========");
                    System.out.println("Customer: " + customerName);
                    System.out.println("Vehicle: " + vehicle);
                    System.out.println("Ending Value: $" + lease.getEndingValue());
                    System.out.println("Lease Fee: $" + lease.getLeaseFee());
                    System.out.println("Total Price: $" + lease.getMonthlyPayment());

                    System.out.println("Total Price: $" + lease.getTotalPrice());

                    String agreeToLease = Console.promptForString("You want to continue with the lease?");

                    if (agreeToLease.equalsIgnoreCase("yes")) {
                        ContractDataManager.saveContract(lease);

                        System.out.println("Lease saved successfully");
                    }


                }

            }
        }
    }

}
