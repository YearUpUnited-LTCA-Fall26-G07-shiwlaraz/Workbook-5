package com.dealership;

import java.io.*;

public class ContractDataManager {

    public void ShowContract() {
        try {
            FileReader fr1 = new FileReader("contracts.csv");
            BufferedReader br1 = new BufferedReader(fr1);
            String line;

            while ((line = br1.readLine()) != null) {
                System.out.println(line);
            }



        } catch (IOException e) {
            System.out.println("Error handling File");
        }
    }
    public static void saveContract(Contract contract) {
        try {
            FileWriter fw1 = new FileWriter("contracts.csv", true);
            BufferedWriter bw1 = new BufferedWriter(fw1);

            Vehicle vehicle = contract.getVehicle();

            if (contract instanceof SalesContract) {
                bw1.write("SALE|");
            } else if (contract instanceof LeaseContract) {
                bw1.write("LEASE|");
            }

            bw1.write(contract.getDate() + "|");
            bw1.write(contract.getCustomerName() + "|");
            bw1.write(contract.getCustomerEmail() + "|");

            bw1.newLine();

            bw1.write(vehicle.getVin() + "|");
            bw1.write(vehicle.getYear() + "|");
            bw1.write(vehicle.getMake() + "|");
            bw1.write(vehicle.getModel() + "|");
            bw1.write(vehicle.getVehicleType() + "|");
            bw1.write(vehicle.getColor() + "|");
            bw1.write(vehicle.getOdometer() + "|");
            bw1.write(vehicle.getPrice() + "|");

            bw1.newLine();




            if (contract instanceof SalesContract) {
                SalesContract sale = (SalesContract) contract;

                bw1.write(sale.getSalesTaxAmount() + "|");
                bw1.write(sale.getRecordingFee() + "|");
                bw1.write(sale.getProcessingFee() + "|");
                bw1.write(sale.getTotalPrice() + "|");
                bw1.write((sale.isFinance() ? "YES" : "NO") + "|");
                bw1.write(sale.getMonthlyPayment() + "");
            }
            else if (contract instanceof LeaseContract) {
                LeaseContract lease = (LeaseContract) contract;

                bw1.write(lease.getEndingValue() + "|");
                bw1.write(lease.getLeaseFee() + "|");
                bw1.write(lease.getTotalPrice()+ "|");
                bw1.write(lease.getMonthlyPayment() + "");


            }
            bw1.newLine();
            System.out.println("Contract saved successfully!");
            bw1.close();

        } catch (IOException e) {
            System.out.print("Error Handling File") ;
        }

    }
}
