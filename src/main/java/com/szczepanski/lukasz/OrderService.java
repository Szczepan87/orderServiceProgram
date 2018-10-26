package com.szczepanski.lukasz;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class OrderService implements OrderServiceInterface {

    private OrderImporter orderImporter;
    private List<Order> orders = new ArrayList<>();

    OrderService(List<File> files) {

        files.forEach(file -> {
            orderImporter = new OrderImporter(file);
            try {
                orders.addAll(orderImporter.getListOfOrders());
            } catch (IOException e) {
                System.out.println("Błąd: " + e.getMessage() + " w pliku: " + file.getName() + "!");
            }
        });
    }

    /**
     * Zwraca liczbę wszystkich zamówień
     */
    @Override
    public int getNumberOfAllOrders() {
        return orders.size();
    }

    /**
     * Zwraca liczbę zamówień danego klienta wg @Param{clientId}
     */
    @Override
    public int getNumberOfOrdersForClient(String clientId) {
        int counter = 0;
        for (Order order : orders) {
            if (order.getClientID().equals(clientId))
                counter++;
        }
        return counter;
    }

    /**
     * Zwraca sumę kwot wszystkich zamówień
     */
    @Override
    public double getSumOfAllOrders() {
        BigDecimal sum = new BigDecimal(0);
        for (Order order : orders) {
            sum = sum.add(order.getPrice());
        }
        return sum.setScale(2, RoundingMode.HALF_EVEN).doubleValue();
    }

    /**
     * Zwraca sumę kwot wszystkich zamówień danego klienta wg @Param{clientId}
     */
    @Override
    public double getSumOfOrdersForClient(String clientId) {
        BigDecimal sum = new BigDecimal(0);
        for (Order order : orders) {
            if (order.getClientID().equals(clientId)) {
                sum = sum.add(order.getPrice());
            }
        }
        return sum.setScale(2, RoundingMode.HALF_EVEN).doubleValue();
    }

    /**
     * Zwraca Stringa zawierającego wszystkie zamówienia,
     * a ich dane rozdziela przecinkiem(ostatnia linia bez przecinka, zgodnie z formatem CSV)
     */
    @Override
    public String getListOfAllOrders() {
        StringBuilder result = new StringBuilder("Client_Id,Request_id,Name,Quantity,Price\n");
        for (Order order : orders) {
            result.append(order.getClientID()).append(",").append(order.getRequestId()).append(",")
                    .append(order.getName()).append(",").append(order.getQuantity()).append(",")
                    .append(order.getPrice().toString()).append("\n");
        }
        return result.toString();
    }

    /**
     * Zwraca Stringa zawierającego wszystkie zamówienia danego klienta wg @Param{clientId},
     * a ich dane rozdziela przecinkiem(ostatnia linia bez przecinka, zgodnie z formatem CSV)
     */
    @Override
    public String getListOfOrdersForClient(String clientId) {
        StringBuilder result = new StringBuilder("Client_Id,Request_id,Name,Quantity,Price\n");
        for (Order order : orders) {
            if (order.getClientID().equals(clientId)) {
                result.append(order.getClientID()).append(",").append(order.getRequestId()).append(",")
                        .append(order.getName()).append(",").append(order.getQuantity()).append(",")
                        .append(String.valueOf(order.getPrice())).append("\n");
            }
        }
        return result.toString();
    }

    /**
     * Zwraca średnią kwotę dla wszystkich zamówień
     */
    @Override
    public double getAveragePriceOfAllOrders() {
        double result = 0;
        try {
            result = BigDecimal.valueOf(getSumOfAllOrders())
                    .divide(BigDecimal.valueOf(getNumberOfAllOrders()), 2, RoundingMode.HALF_EVEN)
                    .doubleValue();

        } catch (ArithmeticException e) {
            System.out.println("Niedozwolone działenie!");
            e.getMessage();
        }
        return result;
    }

    /**
     * Zwraca średnią kwotę dla wszystkich zamówień danego klienta wg @Param{clientId}
     */
    @Override
    public double getAveragePriceOfOrdersForClient(String clientId) {
        double result = 0;
        try {
            result = BigDecimal.valueOf(getSumOfOrdersForClient(clientId))
                    .divide(BigDecimal.valueOf(getNumberOfOrdersForClient(clientId)), 2, RoundingMode.HALF_EVEN)
                    .doubleValue();

        } catch (ArithmeticException e) {
            System.out.println("Niedozwolone działenie!");
            e.getMessage();
        }
        return result;
    }
}
