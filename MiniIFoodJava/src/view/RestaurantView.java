package view;

import dao.RestaurantDAO;
import model.Restaurant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class RestaurantView extends JFrame {
    private JTextField txtName, txtAddress, txtCity;
    private DefaultListModel<String> listModel;
    private JList<String> restaurantList;
    private List<Restaurant> restaurants;

    public RestaurantView() {
        setTitle("Restaurant Management");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(5, 2));
        txtName = new JTextField();
        txtAddress = new JTextField();
        txtCity = new JTextField();

        panel.add(new JLabel("Name:")); panel.add(txtName);
        panel.add(new JLabel("Address:")); panel.add(txtAddress);
        panel.add(new JLabel("City:")); panel.add(txtCity);

        JButton btnSave = new JButton("Save");
        btnSave.addActionListener(e -> saveRestaurant());
        panel.add(btnSave);

        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(e -> loadRestaurants());
        panel.add(btnRefresh);

        add(panel, BorderLayout.NORTH);

        listModel = new DefaultListModel<>();
        restaurantList = new JList<>(listModel);
        add(new JScrollPane(restaurantList), BorderLayout.CENTER);

        loadRestaurants();
        setVisible(true);
    }

    private void saveRestaurant() {
        try {
            Restaurant restaurant = new Restaurant();
            restaurant.setName(txtName.getText());
            restaurant.setAddress(txtAddress.getText());
            restaurant.setCity(txtCity.getText());
            new RestaurantDAO().insert(restaurant);
            loadRestaurants();
            clearFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saving restaurant: " + e.getMessage());
        }
    }

    private void loadRestaurants() {
        try {
            restaurants = new RestaurantDAO().getAll();
            listModel.clear();
            for (Restaurant r : restaurants) {
                listModel.addElement(r.getName() + " - " + r.getCity());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading restaurants: " + e.getMessage());
        }
    }

    private void clearFields() {
        txtName.setText("");
        txtAddress.setText("");
        txtCity.setText("");
    }

    public static void main(String[] args) {
        new RestaurantView();
    }
}