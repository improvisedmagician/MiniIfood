package view;

import dao.ProductDAO;
import model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class ProductView extends JFrame {
    private JTextField txtName, txtPrice, txtRestaurantId;
    private DefaultListModel<String> listModel;
    private JList<String> productList;
    private List<Product> products;

    public ProductView() {
        setTitle("Product Management");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(5, 2));
        txtName = new JTextField();
        txtPrice = new JTextField();
        txtRestaurantId = new JTextField();

        panel.add(new JLabel("Name:")); panel.add(txtName);
        panel.add(new JLabel("Price:")); panel.add(txtPrice);
        panel.add(new JLabel("Restaurant ID:")); panel.add(txtRestaurantId);

        JButton btnSave = new JButton("Save");
        btnSave.addActionListener(e -> saveProduct());
        panel.add(btnSave);

        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(e -> loadProducts());
        panel.add(btnRefresh);

        add(panel, BorderLayout.NORTH);

        listModel = new DefaultListModel<>();
        productList = new JList<>(listModel);
        add(new JScrollPane(productList), BorderLayout.CENTER);

        loadProducts();
        setVisible(true);
    }

    private void saveProduct() {
        try {
            Product product = new Product();
            product.setName(txtName.getText());
            product.setPrice(Double.parseDouble(txtPrice.getText()));
            product.setRestaurantId(Integer.parseInt(txtRestaurantId.getText()));
            new ProductDAO().insert(product);
            loadProducts();
            clearFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saving product: " + e.getMessage());
        }
    }

    private void loadProducts() {
        try {
            products = new ProductDAO().getAll();
            listModel.clear();
            for (Product p : products) {
                listModel.addElement(p.getName() + " - $" + p.getPrice() + " (Rest. ID: " + p.getRestaurantId() + ")");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading products: " + e.getMessage());
        }
    }

    private void clearFields() {
        txtName.setText("");
        txtPrice.setText("");
        txtRestaurantId.setText("");
    }

    public static void main(String[] args) {
        new ProductView();
    }
}