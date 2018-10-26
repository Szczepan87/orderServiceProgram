package com.szczepanski.lukasz;

import java.math.BigDecimal;
import java.util.Objects;

public class Order {

    private String clientID;
    private long requestId;
    private String name;
    private int quantity;
    private BigDecimal price;

    Order(String clientID, long requestId, String name, int quantity, BigDecimal price) {

        if (clientID.length() > 6) this.clientID = "------";
        else this.clientID = clientID;

        if (requestId > Long.valueOf("9223372036854775807") || requestId < 0) this.requestId = -1;
        else this.requestId = requestId;

        if (name.length() > 255) this.name = "Nieprawidłowy format!";
        else this.name = name;

        if (quantity > Integer.valueOf("2147483647") || quantity < 0) this.quantity = 0;
        else this.quantity = quantity;

        if (price.doubleValue() <= 0) this.price = BigDecimal.ZERO;
        else this.price = price;
    }

    String getClientID() {
        return this.clientID;
    }

    long getRequestId() {
        return this.requestId;
    }

    public String getName() {
        return this.name;
    }

    int getQuantity() {
        return this.quantity;
    }

    BigDecimal getPrice() {
        return this.price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return getRequestId() == order.getRequestId() &&
                getQuantity() == order.getQuantity() &&
                Objects.equals(getClientID(), order.getClientID()) &&
                Objects.equals(getName(), order.getName()) &&
                Objects.equals(getPrice(), order.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClientID(), getRequestId(), getName(), getQuantity(), getPrice());
    }

    @Override
    public String toString() {
        return "Order{" +
                "clientID='" + clientID + '\'' +
                ", requestId=" + requestId +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
