package view;

import dao.OrdersDAO;
import dao.ProductDAO;
import dao.OrderItemDAO;
import model.Orders;
import model.Product;
import model.OrderItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class OrderItemForm extends JFrame {
    private JComboBox<Orders> orderComboBox;
    private JComboBox<Product> productComboBox;
    private JTextField quantityField;
    private JTextField priceField;
    private JButton btnSave, btnAccompaniments;
    private OrderItem currentItem;

    public OrderItemForm() {
        setTitle("Order Item Form");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6, 2));

        orderComboBox = new JComboBox<>();
        productComboBox = new JComboBox<>();
        quantityField = new JTextField();
        priceField = new JTextField();

        btnAccompaniments = new JButton("Select Accompaniments");
        btnSave = new JButton("Save");

        add(new JLabel("Order:"));
        add(orderComboBox);
        add(new JLabel("Product:"));
        add(productComboBox);
        add(new JLabel("Quantity:"));
        add(quantityField);
        add(new JLabel("Unit Price:"));
        add(priceField);
        add(btnAccompaniments);
        add(new JLabel());
        add(btnSave);

        loadComboBoxes();

        btnSave.addActionListener(this::saveOrderItem);
        btnAccompaniments.addActionListener(this::openAccompanimentSelector);

        setVisible(true);
    }

    private void loadComboBoxes() {
        try {
            List<Orders> orders = new OrdersDAO().getAll();
            for (Orders o : orders) {
                orderComboBox.addItem(o);
            }

            List<Product> products = new ProductDAO().getAll();
            for (Product p : products) {
                productComboBox.addItem(p);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        }
    }

    private void saveOrderItem(ActionEvent e) {
        try {
            int quantity = Integer.parseInt(quantityField.getText());
            double unitPrice = Double.parseDouble(priceField.getText());

            Orders selectedOrder = (Orders) orderComboBox.getSelectedItem();
            Product selectedProduct = (Product) productComboBox.getSelectedItem();

            OrderItem item = new OrderItem();
            item.setOrderId(selectedOrder.getId());
            item.setProductId(selectedProduct.getId());
            item.setQuantity(quantity);
            item.setUnitPrice(unitPrice);

            new OrderItemDAO().insert(item);
            JOptionPane.showMessageDialog(this, "Order item saved!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error saving order item: " + ex.getMessage());
        }
    }

    private void openAccompanimentSelector(ActionEvent e) {
        try {
            int orderItemId = Integer.parseInt(JOptionPane.showInputDialog("Enter OrderItem ID (after save):"));
            new OrderItemAccompanimentView(orderItemId);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new OrderItemForm();
    }
}

