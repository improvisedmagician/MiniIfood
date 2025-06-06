package view;

import dao.RestaurantDAO;
import model.Restaurant;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class RestaurantForm extends JFrame {
    private JTextField nameField, addressField, cityField;
    private JButton btnSave, btnRefresh, btnDelete;
    private JTable table;
    private DefaultTableModel tableModel;

    public RestaurantForm() {
        setTitle("Manage Restaurants");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Form panel
        JPanel form = new JPanel(new GridLayout(4, 2, 5, 5));
        nameField = new JTextField();
        addressField = new JTextField();
        cityField = new JTextField();
        btnSave = new JButton("Save");

        form.add(new JLabel("Name:"));
        form.add(nameField);
        form.add(new JLabel("Address:"));
        form.add(addressField);
        form.add(new JLabel("City:"));
        form.add(cityField);
        form.add(new JLabel());
        form.add(btnSave);

        add(form, BorderLayout.NORTH);

        // Table
        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Address", "City"}, 0);
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
        refreshTable();

        // Actions
        btnSave.addActionListener(this::saveRestaurant);
        btnRefresh.addActionListener(e -> refreshTable());
        btnDelete.addActionListener(this::deleteSelected);

        setVisible(true);
    }

    private void saveRestaurant(ActionEvent e) {
        try {
            Restaurant r = new Restaurant();
            r.setName(nameField.getText());
            r.setAddress(addressField.getText());
            r.setCity(cityField.getText());
            new RestaurantDAO().insert(r);
            refreshTable();
            nameField.setText("");
            addressField.setText("");
            cityField.setText("");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error saving restaurant: " + ex.getMessage());
        }
    }

    private void refreshTable() {
        try {
            tableModel.setRowCount(0);
            List<Restaurant> list = new RestaurantDAO().getAll();
            for (Restaurant r : list) {
                tableModel.addRow(new Object[]{r.getId(), r.getName(), r.getAddress(), r.getCity()});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading restaurants: " + e.getMessage());
        }
    }

    private void deleteSelected(ActionEvent e) {
        int row = table.getSelectedRow();
        if (row >= 0) {
            int id = (int) tableModel.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Delete Restaurant ID " + id + "?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    new RestaurantDAO().delete(id);
                    refreshTable();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error deleting restaurant: " + ex.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a restaurant first.");
        }
    }

    public static void main(String[] args) {
        new RestaurantForm();
    }
}
