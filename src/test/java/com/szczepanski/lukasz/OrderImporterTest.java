package com.szczepanski.lukasz;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.Assert.*;

public class OrderImporterTest {

    private File csvFile = new File(("src/main/FilesForTesting/orderSource.csv"));
    private File htmlFile = new File("src/main/FilesForTesting/orderSource.html");
    private File falseFile = new File("");
    private OrderImporter orderImporter;

    private void checkFirstItemInList(File csvFile) throws IOException {
        orderImporter = new OrderImporter(csvFile);
        assertEquals("1", orderImporter.getListOfOrders().get(0).getClientID());
        assertEquals(1,orderImporter.getListOfOrders().get(0).getRequestId());
        assertEquals("Bułka",orderImporter.getListOfOrders().get(0).getName());
        assertEquals(1,orderImporter.getListOfOrders().get(0).getQuantity());
        assertEquals(BigDecimal.valueOf(10.00).setScale(2, RoundingMode.HALF_EVEN),orderImporter.getListOfOrders().get(0).getPrice());
    }

    @Test
    public void csvFileTest() {
        assertTrue(csvFile.exists());
    }

    @Test
    public void htmlFileTest() {
        assertTrue(htmlFile.exists());
    }

    @Test(expected = IOException.class)
    public void wrongFileTest() throws IOException {
        orderImporter = new OrderImporter(falseFile);
        orderImporter.getListOfOrders().get(1);
    }

    @Test
    public void importFromCSVFileTest() throws IOException {
        checkFirstItemInList(csvFile);
    }

    @Test
    public void importFromHTMLFileTest() throws IOException {
        checkFirstItemInList(htmlFile);
    }
}