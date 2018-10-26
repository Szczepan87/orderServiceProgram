package com.szczepanski.lukasz;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class OrderImporter {

    private File fileToImport;
    private List<Order> orders = new ArrayList<>();

    OrderImporter(File fileToImport) {
        this.fileToImport = fileToImport;
    }

    /**
     * Zwraca listę zamówień na podstawie pliku (HTML lub CSV)
     */
    List<Order> getListOfOrders() throws IOException {
        if (fileToImport.exists()) {
            switch (fileToImport.getName().substring(fileToImport.getName().lastIndexOf("."))) {
                case ".html":
                    htmlImporter(fileToImport);
                    return orders;
                case ".csv":
                    csvImporter(fileToImport);
                    return orders;
                default:
                    throw new IOException();
            }
        } else throw new IOException();
    }

    /**
     * Uzupełnia listę zamówień na podstawie pliku w formacie CSV
     */
    private void csvImporter(File file) throws FileNotFoundException {
        InputStream inputStream = new FileInputStream(file);
        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNext()) {
            String[] strings = scanner.nextLine().split(",");
            if (strings[0].equals("Client_Id"))
                continue;
            else if (strings.length < 5 || strings.length > 5)
                System.out.println("Błąd w pliku: " + file.getName() + "!");
            else {
                Order order = new Order(strings[0], Long.valueOf(strings[1]),
                        strings[2], Integer.valueOf(strings[3]),
                        BigDecimal.valueOf(Double.parseDouble(strings[4])).setScale(2, RoundingMode.HALF_EVEN));
                if (properOrder(order)) orders.add(order);
                else System.out.println("Błąd w pliku: " + file.getName() + "!");
            }
        }
        scanner.close();
    }

    private boolean properOrder(Order order) {
        if (order.getClientID().equals("------")) return false;
        else if (order.getRequestId() < 0) return false;
        else if (order.getName().equals("Nieprawidłowy format!")) return false;
        else if (order.getQuantity() < 0) return false;
        else if (order.getPrice().equals(BigDecimal.ZERO)) return false;
        else return true;
    }

    /**
     * Uzupełnia listę zamówień na podstawie pliku w formacie HTML z wykorzystaniem biblioteki Jsoup
     */
    private void htmlImporter(File file) throws IOException {
        Document document = Jsoup.parse(file, "UTF-8");
        if (!document.hasText()) {
            orders.add(new Order("------", -1, "Błędny format pliku " + file.getName() + "!", 0, BigDecimal.ZERO));
            return;
        }

        Elements requests = document.getElementsByTag("request");

        requests.forEach(request -> {
            Elements clientId = request.getElementsByTag("clientId");
            if (clientId.size() != 1) return;

            Elements requestId = request.getElementsByTag("requestId");
            if (requestId.size() != 1) return;

            Elements name = request.getElementsByTag("name");
            if (name.size() != 1) return;

            Elements quantity = request.getElementsByTag("quantity");
            if (quantity.size() != 1) return;

            Elements price = request.getElementsByTag("price");
            if (price.size() != 1) return;

            long requestIdLong;
            int quantityInt;
            BigDecimal priceBigDecimal;

            try {
                requestIdLong = Long.parseLong(requestId.text());
            } catch (Exception e) {
                return;
            }

            try {
                quantityInt = Integer.parseInt(quantity.text());
            } catch (Exception e) {
                return;
            }

            try {
                priceBigDecimal = BigDecimal.valueOf(Double.parseDouble(price.text())).setScale(2, RoundingMode.HALF_EVEN);
            } catch (Exception e) {
                return;
            }

            Order order = new Order(clientId.text(), requestIdLong, name.text(), quantityInt, priceBigDecimal);

            if (properOrder(order)) this.orders.add(order);
            else System.out.println("Błąd w pliku: " + file.getName() + "!");
        });
    }
}
