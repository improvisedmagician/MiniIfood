package view;

import dao.ClientDAO;
import dao.RestaurantDAO;
import dao.PaymentMethodDAO;
import model.PaymentMethod;

import dao.OrdersDAO;
import model.Client;
import model.Restaurant;

import model.Orders;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class OrdersForm extends JFrame {
    private JComboBox<Client> clientComboBox;
    private JComboBox<Restaurant> restaurantComboBox;
    private JComboBox<PaymentMethod> paymentComboBox;
    private JTextField deliveryTimeField;
    private JTextField totalPriceField;
    private JTextField orderDateField;
    private JButton btnSave;

    public OrdersForm() {
        setTitle("Create Order");
        setSize(400, 350);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(7, 2));

        clientComboBox = new JComboBox<>();
        restaurantComboBox = new JComboBox<>();
        paymentComboBox = new JComboBox<>();
        deliveryTimeField = new JTextField();
        totalPriceField = new JTextField();
        orderDateField = new JTextField(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        btnSave = new JButton("Save");

        add(new JLabel("Client:"));
        add(clientComboBox);
        add(new JLabel("Restaurant:"));
        add(restaurantComboBox);
        add(new JLabel("Payment Method:"));
        add(paymentComboBox);
        add(new JLabel("Order Date:"));
        add(orderDateField);
        add(new JLabel("Delivery Time (min):"));
        add(deliveryTimeField);
        add(new JLabel("Total Price (R$):"));
        add(totalPriceField);
        add(new JLabel(""));
        add(btnSave);

        loadComboBoxes();

        btnSave.addActionListener(this::saveOrder);
        setVisible(true);
    }

    private void loadComboBoxes() {
        try {
            for (Client c : new ClientDAO().getAll()) clientComboBox.addItem(c);
            for (Restaurant r : new RestaurantDAO().getAll()) restaurantComboBox.addItem(r);
            for (PaymentMethod p : new PaymentMethodDAO().getAll()) paymentComboBox.addItem(p);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        }
    }

    private void saveOrder(ActionEvent e) {
        try {
            Orders order = new Orders();
            order.setClientId(((Client) clientComboBox.getSelectedItem()).getId());
            order.setRestaurantId(((Restaurant) restaurantComboBox.getSelectedItem()).getId());
            order.setPaymentMethodId(((PaymentMethod) paymentComboBox.getSelectedItem()).getId());
            order.setOrderDate(LocalDateTime.parse(orderDateField.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            order.setDeliveryTime(Integer.parseInt(deliveryTimeField.getText()));
            order.setTotalPrice(Double.parseDouble(totalPriceField.getText()));

            new OrdersDAO().insert(order);
            JOptionPane.showMessageDialog(this, "Order saved!");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error saving order: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new OrdersForm();
    }
}
