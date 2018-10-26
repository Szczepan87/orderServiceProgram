package com.szczepanski.lukasz;

import java.io.IOException;
import java.util.List;

public interface OrderServiceInterface {

    int getNumberOfAllOrders() throws IOException;

    int getNumberOfOrdersForClient(String customerId);

    double getSumOfAllOrders();

    double getSumOfOrdersForClient(String customerId);

    String getListOfAllOrders();

    String getListOfOrdersForClient(String customerId);

    double getAveragePriceOfAllOrders() throws IOException;

    double getAveragePriceOfOrdersForClient(String customerId);

}
