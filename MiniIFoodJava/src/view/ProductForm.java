package view;

import javax.swing.table.DefaultTableModel;

import dao.ProductDAO;
import dao.RestaurantDAO;
import model.Product;
import model.Restaurant;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class ProductForm extends JFrame {
    private JComboBox<Restaurant> restaurantCombo;
    private JTextField nameField;
    private JTextField priceField;
    private JButton btnSave;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnRefresh, btnDelete;

    public ProductForm() {
        setTitle("Manage Products");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Form panel
        JPanel form = new JPanel(new GridLayout(4, 2, 5, 5));
        restaurantCombo = new JComboBox<>();
        nameField = new JTextField();
        priceField = new JTextField();
        btnSave = new JButton("Save");

        form.add(new JLabel("Restaurant:"));
        form.add(restaurantCombo);
        form.add(new JLabel("Name:"));
        form.add(nameField);
        form.add(new JLabel("Price:"));
        form.add(priceField);
        form.add(new JLabel());
        form.add(btnSave);

        add(form, BorderLayout.NORTH);

        // Table panel
        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Price", "Restaurant ID"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Buttons
        JPanel buttons = new JPanel();
        btnRefresh = new JButton("Refresh");
        btnDelete = new JButton("Delete");
        buttons.add(btnRefresh);
        buttons.add(btnDelete);
        add(buttons, BorderLayout.SOUTH);

        // Load data
        loadRestaurants();
        refreshTable();

        // Actions
        btnSave.addActionListener(this::saveProduct);
        btnRefresh.addActionListener(e -> refreshTable());
        btnDelete.addActionListener(this::deleteSelected);

        setVisible(true);
    }

    private void loadRestaurants() {
        try {
            RestaurantDAO dao = new RestaurantDAO();
            List<Restaurant> list = dao.getAll();
            for (Restaurant r : list) {
                restaurantCombo.addItem(r);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading restaurants: " + e.getMessage());
        }
    }

    private void saveProduct(ActionEvent e) {
        try {
            Product p = new Product();
            p.setName(nameField.getText());
            p.setPrice(Double.parseDouble(priceField.getText()));
            p.setRestaurantId(((Restaurant) restaurantCombo.getSelectedItem()).getId());
            new ProductDAO().insert(p);
            refreshTable();
            nameField.setText("");
            priceField.setText("");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error saving product: " + ex.getMessage());
        }
    }

    private void refreshTable() {
        try {
            tableModel.setRowCount(0);
            List<Product> list = new ProductDAO().getAll();
            for (Product p : list) {
                tableModel.addRow(new Object[]{p.getId(), p.getName(), p.getPrice(), p.getRestaurantId()});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading products: " + e.getMessage());
        }
    }

    private void deleteSelected(ActionEvent e) {
        int row = table.getSelectedRow();
        if (row >= 0) {
            int id = (int) tableModel.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Delete Product ID " + id + "?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    new ProductDAO().delete(id);
                    refreshTable();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error deleting product: " + ex.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a product first.");
        }
    }

    public static void main(String[] args) {
        new ProductForm();
    }
}
