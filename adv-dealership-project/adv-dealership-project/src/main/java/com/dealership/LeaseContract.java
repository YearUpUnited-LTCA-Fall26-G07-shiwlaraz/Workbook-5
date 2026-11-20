package com.dealership;

import java.time.LocalDate;

public class LeaseContract extends Contract {
    public LeaseContract(LocalDate date, String customerName, String customerEmail, Vehicle vehicle) {
        super(date, customerName, customerEmail, vehicle);
    }
    public double getEndingValue() {
        double endingValue = getVehicle().getPrice().doubleValue() / 2;
        return endingValue;

    }
    public double getLeaseFee() {
        double leaseFee = getVehicle().getPrice().doubleValue() * 0.07;
        return leaseFee;
    }

    @Override
    public double getTotalPrice() {
        return getEndingValue() + getLeaseFee();
    }

    @Override
    public double getMonthlyPayment() {
        double annualRate = 0.04;
        double rate = annualRate /12;
        int months = 36;

        double monthlyPayments = getTotalPrice() *
            (rate * Math.pow(1+ rate , months) /
                    (Math.pow(1+ rate, months ) -1 ));
        return  monthlyPayments;

    }
}
