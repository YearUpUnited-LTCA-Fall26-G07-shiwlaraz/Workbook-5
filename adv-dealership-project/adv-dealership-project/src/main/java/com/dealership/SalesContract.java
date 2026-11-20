package com.dealership;

import java.math.BigDecimal;
import java.time.LocalDate;


public class SalesContract extends Contract{
    private BigDecimal salesTaxAmount;
    private double recordingFee = 100.00;
    private double processingFee;
    private boolean finance;

    public SalesContract(LocalDate date, String customerName, String customerEmail, Vehicle vehicle, boolean finance) {
        super (date, customerName, customerEmail, vehicle);
        this.finance = finance;

    }
    public double getRecordingFee() {
        return recordingFee;
    }
    public BigDecimal getSalesTaxAmount() {
        return getVehicle().getPrice().multiply(new BigDecimal("0.05"));
    }

    public double getProcessingFee() {
        if (getVehicle().getPrice().compareTo(new BigDecimal("10000")) < 0) {
            return 295.00;
        }
        else {
            return 495.00;
        }

    }
    public boolean isFinance() {
        return finance;
    }


    @Override
    public double getTotalPrice() {
        BigDecimal total = getVehicle().getPrice()
                .add(getSalesTaxAmount())
                .add(BigDecimal.valueOf(getRecordingFee()))
                .add(BigDecimal.valueOf(getProcessingFee()));

        return  total.doubleValue();
    }

    @Override
    public double getMonthlyPayment() {
        if (finance) {
            double interestRate;
            int months;
            if (getVehicle().getPrice().doubleValue() < 10000) {
                interestRate = 0.0525;
                months = 24;
            }
            else {
                interestRate = 0.0425;
                months = 48;
            }

            double monthlyRate = interestRate / 12;
            double totalPrice = getTotalPrice();

            double monthlyPayment = totalPrice *
                    (monthlyRate * Math.pow(1 + monthlyRate, months)) /
                    (Math.pow(1 + monthlyRate, months) - 1);

            return monthlyPayment;

            } return 0;
        }

}
