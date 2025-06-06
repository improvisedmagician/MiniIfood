package view;

import dao.OrdersDAO;
import model.Orders;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

public class OrderView extends JFrame {
    private JTextField txtClientId, txtRestaurantId, txtDeliveryTime, txtPaymentMethodId, txtTotalPrice;
    private DefaultListModel<String> listModel;
    private JList<String> orderList;
    private List<Orders> orders;

    public OrderView() {
        setTitle("Order Management");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(7, 2));
        txtClientId = new JTextField();
        txtRestaurantId = new JTextField();
        txtDeliveryTime = new JTextField();
        txtPaymentMethodId = new JTextField();
        txtTotalPrice = new JTextField();

        panel.add(new JLabel("Client ID:")); panel.add(txtClientId);
        panel.add(new JLabel("Restaurant ID:")); panel.add(txtRestaurantId);
        panel.add(new JLabel("Delivery Time:")); panel.add(txtDeliveryTime);
        panel.add(new JLabel("Payment Method ID:")); panel.add(txtPaymentMethodId);
        panel.add(new JLabel("Total Price:")); panel.add(txtTotalPrice);

        JButton btnSave = new JButton("Save");
        btnSave.addActionListener(e -> saveOrder());
        panel.add(btnSave);

        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(e -> loadOrders());
        panel.add(btnRefresh);

        add(panel, BorderLayout.NORTH);

        listModel = new DefaultListModel<>();
        orderList = new JList<>(listModel);
        add(new JScrollPane(orderList), BorderLayout.CENTER);

        loadOrders();
        setVisible(true);
    }

    private void saveOrder() {
        try {
            Orders order = new Orders();
            order.setClientId(Integer.parseInt(txtClientId.getText()));
            order.setRestaurantId(Integer.parseInt(txtRestaurantId.getText()));
            order.setOrderDate(LocalDateTime.now());
            order.setDeliveryTime(Integer.parseInt(txtDeliveryTime.getText()));
            order.setPaymentMethodId(Integer.parseInt(txtPaymentMethodId.getText()));
            order.setTotalPrice(Double.parseDouble(txtTotalPrice.getText()));
            new OrdersDAO().insert(order);
            loadOrders();
            clearFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saving order: " + e.getMessage());
        }
    }

    private void loadOrders() {
        try {
            orders = new OrdersDAO().getAll();
            listModel.clear();
            for (Orders o : orders) {
                listModel.addElement("Order #" + o.getId() + " | Client: " + o.getClientId() + " | Total: $" + o.getTotalPrice());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading orders: " + e.getMessage());
        }
    }

    private void clearFields() {
        txtClientId.setText("");
        txtRestaurantId.setText("");
        txtDeliveryTime.setText("");
        txtPaymentMethodId.setText("");
        txtTotalPrice.setText("");
    }

    public static void main(String[] args) {
        new OrderView();
    }
}
