package com.pluralsight;

import java.math.BigDecimal;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {

        ArrayList<Asset> myAssets = new ArrayList<>();


        House house = new House("My house", "11-17-2025", new BigDecimal("250000"),
                "46 Hurley Ct", 1, 2000, 4400
        );

        House vacationHouse = new House("My vacation house", "06-15-2023", new BigDecimal("180000"),
                "123 Beach Road", 2, 1500, 3000
        );

        Vehicle car = new Vehicle("My car", "11-17-2026", new BigDecimal("25000"),
                "Toyota Camry", 2022, 45000
        );

        Vehicle truck = new Vehicle("Tom's truck", "05-10-2022", new BigDecimal("40000"),
                "Ford F-150", 2020, 120000
        );


        myAssets.add(house);
        myAssets.add(vacationHouse);
        myAssets.add(car);
        myAssets.add(truck);

        for (Asset asset : myAssets) {

            System.out.println("Description: " + asset.getDescription());
            System.out.println("Date Acquired: " + asset.getDateAcquired());
            System.out.println("Original Cost: $" + asset.getOriginalCost());
            System.out.println("Current Value: $" + asset.getValue());
            System.out.println("--------------------------------");

            if (asset instanceof House) {
                House currentHouse = (House) asset;

                System.out.println("Address: " + currentHouse.getAddress());
                System.out.println("Condition: " + currentHouse.getCondition());
                System.out.println("Square Feet: " + currentHouse.getSquareFoot());
                System.out.println("Lot Size: " + currentHouse.getLotSize());
                System.out.println("--------------------------------");

            } else if (asset instanceof Vehicle) {
                Vehicle currentVehicle = (Vehicle) asset;

                System.out.println("Vehicle: "
                        + currentVehicle.getYear() + " "
                        + currentVehicle.getMakeModel());

                System.out.println("Odometer: "
                        + currentVehicle.getOdometer());
                System.out.println("--------------------------------");

            }

        }
    }
}
