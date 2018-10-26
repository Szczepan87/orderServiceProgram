package com.szczepanski.lukasz;

import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class OrderServiceTest {

    private OrderService orderService;
    private List<File> allFiles = Arrays.asList(new File("src/main/FilesForTesting/orderSource.html"),
            new File("src/main/FilesForTesting/orderSource.csv"),
            new File("src/main/FilesForTesting/wrongOrderSource.csv"),
            new File("src/main/FilesForTesting/wrongOrderSource.html"),
            new File("src/main/FilesForTesting/empty.txt"));
    private List<File> goodHTMLFile = Collections.singletonList(new File("src/main/FilesForTesting/orderSource.html"));
    private List<File> goodCSVFile = Collections.singletonList(new File("src/main/FilesForTesting/orderSource.csv"));
    private List<File> wrongCSVFile = Collections.singletonList(new File("src/main/FilesForTesting/wrongOrderSource.csv"));
    private List<File> wrongHTMLFile = Collections.singletonList(new File("src/main/FilesForTesting/wrongOrderSource.html"));
    private List<File> wrongFile = Collections.singletonList(new File("src/main/FilesForTesting/empty.txt"));
    private List<File> emptyFile = Collections.singletonList(new File("src/main/FilesForTesting/empty.csv"));

    @Test
    public void getNumberOfAllOrdersInHTMLTest() {
        orderService = new OrderService(goodHTMLFile);
        assertEquals(4, orderService.getNumberOfAllOrders());
    }

    @Test
    public void getNumberOfAllOrdersInCSVTest() {
        orderService = new OrderService(goodCSVFile);
        assertEquals(4, orderService.getNumberOfAllOrders());
    }

    @Test
    public void getNumberOfAllOrdersInWrongCSVTest() {
        orderService = new OrderService(wrongCSVFile);
        assertEquals(2, orderService.getNumberOfAllOrders());
    }

    @Test
    public void getNumberOfAllOrdersInListOfFilesTest() {
        orderService = new OrderService(allFiles);
        assertEquals(12, orderService.getNumberOfAllOrders());
    }

    @Test
    public void wrongTypeOfFileTest() {
        orderService = new OrderService(wrongFile);
    }

    @Test
    public void getNumberOfAllOrdersInWrongHTMLTest() {
        orderService = new OrderService(wrongHTMLFile);
        assertEquals(2, orderService.getNumberOfAllOrders());
    }

    @Test
    public void getNumberOfAllOrdersInEmptyCSVTest() {
        orderService = new OrderService(emptyFile);
        assertEquals(0, orderService.getNumberOfAllOrders());
    }

    @Test
    public void getNumberOfOrdersForClientInHTMLTest() {
        orderService = new OrderService(goodHTMLFile);
        assertEquals(3, orderService.getNumberOfOrdersForClient("1"));
    }

    @Test
    public void getNumberOfOrdersForClientInCSVTest() {
        orderService = new OrderService(goodCSVFile);
        assertEquals(3, orderService.getNumberOfOrdersForClient("1"));
    }

    @Test
    public void getNumberOfOrdersForClientInWrongCSVTest() {
        orderService = new OrderService(wrongCSVFile);
        assertEquals(1, orderService.getNumberOfOrdersForClient("1"));
    }

    @Test
    public void getNumberOfOrdersForClientInWrongHTMLTest() {
        orderService = new OrderService(wrongHTMLFile);
        assertEquals(1, orderService.getNumberOfOrdersForClient("0"));
    }

    @Test
    public void getNumberOfOrdersForClientInEmptyCSVTest() {
        orderService = new OrderService(emptyFile);
        assertEquals(0, orderService.getNumberOfOrdersForClient("1"));
    }

    @Test
    public void getSumOfAllOrdersInHTMLTest() {
        orderService = new OrderService(goodHTMLFile);
        assertEquals(50.00, orderService.getSumOfAllOrders(),0.001);
    }

    @Test
    public void getSumOfAllOrdersInCSVTest() {
        orderService = new OrderService(goodCSVFile);
        assertEquals(50.00, orderService.getSumOfAllOrders(),0.001);
    }

    @Test
    public void getSumOfAllOrdersInWrongCSVTest() {
        orderService = new OrderService(wrongCSVFile);
        assertEquals(25.00, orderService.getSumOfAllOrders(),0.001);
    }

    @Test
    public void getSumOfAllOrdersInWrongHTMLTest() {
        orderService = new OrderService(wrongHTMLFile);
        assertEquals(25.00, orderService.getSumOfAllOrders(),0.001);
    }

    @Test
    public void getSumOfAllOrdersInEmptyCSVTest() {
        orderService = new OrderService(emptyFile);
        assertEquals(0.0, orderService.getSumOfAllOrders(),0.001);
    }

    @Test
    public void getSumOfOrdersForClientInHTMLTest() {
        orderService = new OrderService(goodHTMLFile);
        assertEquals(40.00, orderService.getSumOfOrdersForClient("1"),0.001);
    }

    @Test
    public void getSumOfOrdersForClientInCSVTest() {
        orderService = new OrderService(goodCSVFile);
        assertEquals(40.00, orderService.getSumOfOrdersForClient("1"),0.001);
    }

    @Test
    public void getSumOfOrdersForClientInWrongCSVTest() {
        orderService = new OrderService(wrongCSVFile);
        assertEquals(15.00, orderService.getSumOfOrdersForClient("1"),0.001);
    }

    @Test
    public void getSumOfOrdersForClientInWrongHTMLTest() {
        orderService = new OrderService(wrongHTMLFile);
        assertEquals(15.00, orderService.getSumOfOrdersForClient("0"),0.001);
    }

    @Test
    public void getSumOfOrdersForClientInEmptyCSVTest() {
        orderService = new OrderService(emptyFile);
        assertEquals(0.0, orderService.getSumOfOrdersForClient("1"),0.001);
    }

    @Test
    public void getListOfAllOrdersInHTMLTest() {
        orderService = new OrderService(goodHTMLFile);
        String result = "Client_Id,Request_id,Name,Quantity,Price\n"+
                "1,1,Bułka,1,10.00\n" +
                "1,2,Chleb,2,15.00\n" +
                "1,2,Chleb,5,15.00\n" +
                "2,1,Chleb,1,10.00\n";
        assertEquals(result, orderService.getListOfAllOrders());
    }

    @Test
    public void getListOfAllOrdersInCSVTest() {
        orderService = new OrderService(goodCSVFile);
        String result = "Client_Id,Request_id,Name,Quantity,Price\n" +
                "1,1,Bułka,1,10.00\n" +
                "1,1,Chleb,2,15.00\n" +
                "1,2,Chleb,5,15.00\n" +
                "2,1,Chleb,1,10.00\n";
        assertEquals(result, orderService.getListOfAllOrders());
    }

    @Test
    public void getListOfAllOrdersInWrongCSVTest() {
        String result = "Client_Id,Request_id,Name,Quantity,Price\n" +
                "1,3000000000,Chleb,30,15.00\n2,1,Chleb,1,10.00\n";
        orderService = new OrderService(wrongCSVFile);
        assertEquals(result, orderService.getListOfAllOrders());
    }

    @Test
    public void getListOfAllOrdersInWrongHTMLTest() {
        String result = "Client_Id,Request_id,Name,Quantity,Price\n" +
                "0,3000000000,Chleb,30,15.00\n2,1,Chleb,1,10.00\n";
        orderService = new OrderService(wrongHTMLFile);
        assertEquals(result, orderService.getListOfAllOrders());
    }

    @Test
    public void getListOfAllOrdersInEmptyCSVTest() {
        String result = "Client_Id,Request_id,Name,Quantity,Price\n";
        orderService = new OrderService(emptyFile);
        assertEquals(result, orderService.getListOfAllOrders());
    }

    @Test
    public void getListOfOrdersForClientInHTMLTest() {
        orderService = new OrderService(goodHTMLFile);
        String csvResultForClient = "Client_Id,Request_id,Name,Quantity,Price\n"
                +"1,1,Bułka,1,10.00\n" +
                "1,2,Chleb,2,15.00\n" +
                "1,2,Chleb,5,15.00\n";
        assertEquals(csvResultForClient, orderService.getListOfOrdersForClient("1"));
    }

    @Test
    public void getListOfOrdersForClientInCSVTest() {
        orderService = new OrderService(goodCSVFile);
        String csvResultForClient = "Client_Id,Request_id,Name,Quantity,Price\n" +
                "1,1,Bułka,1,10.00\n" +
                "1,1,Chleb,2,15.00\n" +
                "1,2,Chleb,5,15.00\n";
        assertEquals(csvResultForClient, orderService.getListOfOrdersForClient("1"));
    }

    @Test
    public void getListOfAllOrdersForClientInWrongCSVTest() {
        String result = "Client_Id,Request_id,Name,Quantity,Price\n" +
                "1,3000000000,Chleb,30,15.00\n";
        orderService = new OrderService(wrongCSVFile);
        assertEquals(result, orderService.getListOfOrdersForClient("1"));
    }

    @Test
    public void getListOfAllOrdersForClientInWrongHTMLTest() {
        String result = "Client_Id,Request_id,Name,Quantity,Price\n" +
                "0,3000000000,Chleb,30,15.00\n";
        orderService = new OrderService(wrongHTMLFile);
        assertEquals(result, orderService.getListOfOrdersForClient("0"));
    }

    @Test
    public void getListOfAllOrdersForClientInEmptyCSVTest() {
        String result = "Client_Id,Request_id,Name,Quantity,Price\n";
        orderService = new OrderService(emptyFile);
        assertEquals(result, orderService.getListOfOrdersForClient("1"));
    }

    @Test
    public void getAveragePriceOfAllOrdersInHTMLTest() {
        orderService = new OrderService(goodHTMLFile);
        assertEquals(12.5, orderService.getAveragePriceOfAllOrders(),0.001);
    }

    @Test
    public void getAveragePriceOfAllOrdersInCSVTest()  {
        orderService = new OrderService(goodCSVFile);
        assertEquals(12.5, orderService.getAveragePriceOfAllOrders(),0.001);
    }

    @Test
    public void getAveragePriceOfAllOrdersInWrongCSVTest()  {
        orderService = new OrderService(wrongCSVFile);
        assertEquals(12.5, orderService.getAveragePriceOfAllOrders(),0.001);
    }

    @Test
    public void getAveragePriceOfAllOrdersInWrongHTMLTest()  {
        orderService = new OrderService(wrongHTMLFile);
        assertEquals(12.5, orderService.getAveragePriceOfAllOrders(),0.001);
    }

    @Test
    public void getAveragePriceOfAllOrdersInEmptyCSVTest()  {
        orderService = new OrderService(emptyFile);
        assertEquals(0.0, orderService.getAveragePriceOfAllOrders(),0.001);
    }

    @Test
    public void getAveragePriceOfOrdersForClientInHTMLTest() {
        orderService = new OrderService(goodHTMLFile);
        assertEquals(13.33, orderService.getAveragePriceOfOrdersForClient("1"),0.001);
    }

    @Test
    public void getAveragePriceOfOrdersForClientInCSVTest() {
        orderService = new OrderService(goodCSVFile);
        assertEquals(13.33, orderService.getAveragePriceOfOrdersForClient("1"),0.001);
    }

    @Test
    public void getAveragePriceOfOrdersForClientInWrongCSVTest() {
        orderService = new OrderService(wrongCSVFile);
        assertEquals(15.0, orderService.getAveragePriceOfOrdersForClient("1"),0.001);
    }

    @Test
    public void getAveragePriceOfOrdersForClientInWrongHTMLTest() {
        orderService = new OrderService(wrongHTMLFile);
        assertEquals(15.0, orderService.getAveragePriceOfOrdersForClient("0"),0.001);
    }

    @Test
    public void getAveragePriceOfOrdersForClientInEmptyCSVTest() {
        orderService = new OrderService(emptyFile);
        assertEquals(0.0, orderService.getAveragePriceOfOrdersForClient("1"),0.001);
    }
}