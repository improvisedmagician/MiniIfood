package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

// Importe as views corretas:
import view.ClientView;
import view.OrdersForm;
import view.OrderItemListView;
import view.ProductForm;
import view.RestaurantForm;

public class MainView extends JFrame {

    public MainView() {
        setTitle("Mini iFood - Main Menu");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new GridLayout(6, 1, 10, 10));

        JButton btnClient = new JButton("Manage Clients");
        JButton btnOrder = new JButton("Create Order");
        JButton btnOrderItem = new JButton("Manage Order Items");
        JButton btnProduct = new JButton("Manage Products");
        JButton btnRestaurant = new JButton("Manage Restaurants");
        JButton btnExit = new JButton("Exit");

        btnClient.addActionListener(this::openClientView);
        btnOrder.addActionListener(this::openOrderForm);
        btnOrderItem.addActionListener(this::openOrderItemList);
        btnProduct.addActionListener(this::openProductForm);
        btnRestaurant.addActionListener(this::openRestaurantForm);
        btnExit.addActionListener(e -> System.exit(0));

        add(btnClient);
        add(btnOrder);
        add(btnOrderItem);
        add(btnProduct);
        add(btnRestaurant);
        add(btnExit);

        setVisible(true);
    }

    private void openClientView(ActionEvent e) {
        new ClientView();
    }

    private void openOrderForm(ActionEvent e) {
        new OrdersForm();
    }

    private void openOrderItemList(ActionEvent e) {
        new OrderItemListView();
    }

    private void openProductForm(ActionEvent e) {
        new ProductForm();
    }

    private void openRestaurantForm(ActionEvent e) {
        new RestaurantForm();
    }

    public static void main(String[] args) {
        new MainView();
    }
}
