package com.szczepanski.lukasz;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Menu extends JFrame {

    private JTextArea filepathTextArea;
    private List<File> fileList = new ArrayList<>();
    private Dimension screenDimensions = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
    private OrderService orderService;


    /**Okno startowe*/
    Menu() {
        super("Import raportów sprzedaży");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocation(screenDimensions.height / 2, screenDimensions.width / 6);
        setSize(screenDimensions.width / 3, screenDimensions.height / 2);
        setComponents();
    }

    private void setComponents() {
        JTextField welcomeTextField = new JTextField("Wrowadź ścieżki plików do importu:");
        filepathTextArea = new JTextArea();
        JButton nextButton = new JButton("Dalej");

        nextButton.addActionListener(e -> {
            String[] filePaths = filepathTextArea.getText().trim().split(";");
            for (String filePath : filePaths) {
                fileList.add(new File(filePath));
            }
            new Options();
            this.setVisible(false);
            orderService = new OrderService(fileList);
        });

        JButton clearButton = new JButton("Wyczyść");
        clearButton.addActionListener(e -> filepathTextArea.setText(""));

        filepathTextArea.setBackground(Color.WHITE);
        welcomeTextField.setEditable(false);

        Container buttonContainer = new Container();
        buttonContainer.setLayout(new GridLayout(1, 2, 10, 20));
        buttonContainer.add(clearButton);
        buttonContainer.add(nextButton);

        setLayout(new GridLayout(3, 1, 10, 20));
        add(welcomeTextField);
        add(filepathTextArea);
        add(buttonContainer);
        setVisible(true);
    }

    /**Okno opcji raportów*/
    class Options extends JFrame {

        private JButton numberOfAllOrdersButton;
        private JButton numberOfAllOrdersForClientButton;
        private JButton sumOfAllOrdersButton;
        private JButton sumOfAllOrdersForClientButton;
        private JButton listOfAllOrdersButton;
        private JButton listOfAllOrdersForClientButton;
        private JButton averagePriceButton;
        private JButton averagePriceForClientButton;

        Options() {
            super("Opcje raportów");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            pack();
            setLocation(screenDimensions.height / 2, screenDimensions.width / 6);
            setSize(screenDimensions.width / 3, screenDimensions.height / 2);
            setOptionComponents();
            setVisible(true);
        }

        private void setOptionComponents() {
            setLayout(new GridLayout(8, 1, 10, 10));

            numberOfAllOrdersButton = new JButton("Ilość wszystkich zamówień");
            add(numberOfAllOrdersButton);
            numberOfAllOrdersButton.addActionListener(e ->
                    new ResultWindow(String.valueOf(orderService.getNumberOfAllOrders())));

            numberOfAllOrdersForClientButton = new JButton("Ilość wszystkich zamówień danego klienta");
            add(numberOfAllOrdersForClientButton);
            numberOfAllOrdersForClientButton.addActionListener(e -> {
                String clientId = JOptionPane.showInputDialog("Podaj id klienta.");
                new ResultWindow(String.valueOf(orderService.getNumberOfOrdersForClient(clientId)));
        });

            sumOfAllOrdersButton = new JButton("Kwota wszystkich zamówień");
            add(sumOfAllOrdersButton);
            sumOfAllOrdersButton.addActionListener(e ->
                new ResultWindow(String.valueOf(orderService.getSumOfAllOrders())));

            sumOfAllOrdersForClientButton = new JButton("Kwota wszystkich zamówień dla klienta");
            add(sumOfAllOrdersForClientButton);
            sumOfAllOrdersForClientButton.addActionListener(e -> {
                String clientId = JOptionPane.showInputDialog("Podaj id klienta.");
                new ResultWindow(String.valueOf(orderService.getSumOfOrdersForClient(clientId)));

            });

            listOfAllOrdersButton = new JButton("Lista wszystkich zamówień");
            add(listOfAllOrdersButton);
            listOfAllOrdersButton.addActionListener(e ->
                new ResultWindow(String.valueOf(orderService.getListOfAllOrders())));

            listOfAllOrdersForClientButton = new JButton("Lista wszystkich zamówień klienta");
            add(listOfAllOrdersForClientButton);
            listOfAllOrdersForClientButton.addActionListener(e -> {
                String clientId = JOptionPane.showInputDialog("Podaj id klienta.");
                new ResultWindow(String.valueOf(orderService.getListOfOrdersForClient(clientId)));
            });

            averagePriceButton = new JButton("Srednia wartość zamówienia");
            add(averagePriceButton);
            averagePriceButton.addActionListener(e ->
                new ResultWindow(String.valueOf(orderService.getAveragePriceOfAllOrders())));

            averagePriceForClientButton = new JButton("Srednia wartość zamówienia klienta");
            add(averagePriceForClientButton);
            averagePriceForClientButton.addActionListener(e -> {
                String clientId = JOptionPane.showInputDialog("Podaj id klienta.");
                new ResultWindow(String.valueOf(orderService.getAveragePriceOfOrdersForClient(clientId)));
            });
        }
    }

    /**Okno rezultatu*/
    class ResultWindow extends JFrame{

        private JButton CSVButton;
        private JTextArea resultTextArea;
        private File resultCSVFile;
        private FileWriter writer;

        ResultWindow(String resultToShow) {

            setLayout(new GridLayout(2,1,10,10));

            resultTextArea = new JTextArea();
            add(resultTextArea);
            resultTextArea.setText(resultToShow);

            CSVButton = new JButton("Export do CSV");
            add(CSVButton);
            CSVButton.addActionListener(e -> {
                resultCSVFile = new File("Rezultat.csv");
                try {
                    writer = new FileWriter(resultCSVFile);
                    writer.write(resultToShow);
                    writer.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            });
            pack();
            setVisible(true);
        }
    }
}
