package model;

import java.time.LocalDateTime;

public class Orders {
    private int id;
    private int clientId;
    private int restaurantId;
    private LocalDateTime orderDate;
    private int deliveryTime;
    private int paymentMethodId;
    private double totalPrice;

    public Orders() {}

    public Orders(int id, int clientId, int restaurantId, LocalDateTime orderDate, int deliveryTime, int paymentMethodId, double totalPrice) {
        this.id = id;
        this.clientId = clientId;
        this.restaurantId = restaurantId;
        this.orderDate = orderDate;
        this.deliveryTime = deliveryTime;
        this.paymentMethodId = paymentMethodId;
        this.totalPrice = totalPrice;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getClientId() { return clientId; }
    public void setClientId(int clientId) { this.clientId = clientId; }

    public int getRestaurantId() { return restaurantId; }
    public void setRestaurantId(int restaurantId) { this.restaurantId = restaurantId; }

    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

    public int getDeliveryTime() { return deliveryTime; }
    public void setDeliveryTime(int deliveryTime) { this.deliveryTime = deliveryTime; }

    public int getPaymentMethodId() { return paymentMethodId; }
    public void setPaymentMethodId(int paymentMethodId) { this.paymentMethodId = paymentMethodId; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
}
