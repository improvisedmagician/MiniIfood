package view;

import dao.OrderItemDAO;
import model.OrderItem;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class OrderItemView extends JFrame {
    private JTextField txtOrderId, txtProductId, txtQuantity, txtUnitPrice;
    private DefaultListModel<String> listModel;
    private JList<String> itemList;
    private List<OrderItem> items;

    public OrderItemView() {
        setTitle("Order Item Management");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(6, 2));
        txtOrderId = new JTextField();
        txtProductId = new JTextField();
        txtQuantity = new JTextField();
        txtUnitPrice = new JTextField();

        panel.add(new JLabel("Order ID:")); panel.add(txtOrderId);
        panel.add(new JLabel("Product ID:")); panel.add(txtProductId);
        panel.add(new JLabel("Quantity:")); panel.add(txtQuantity);
        panel.add(new JLabel("Unit Price:")); panel.add(txtUnitPrice);

        JButton btnSave = new JButton("Save");
        btnSave.addActionListener(e -> saveItem());
        panel.add(btnSave);

        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(e -> loadItems());
        panel.add(btnRefresh);

        add(panel, BorderLayout.NORTH);

        listModel = new DefaultListModel<>();
        itemList = new JList<>(listModel);
        add(new JScrollPane(itemList), BorderLayout.CENTER);

        loadItems();
        setVisible(true);
    }

    private void saveItem() {
        try {
            OrderItem item = new OrderItem();
            item.setOrderId(Integer.parseInt(txtOrderId.getText()));
            item.setProductId(Integer.parseInt(txtProductId.getText()));
            item.setQuantity(Integer.parseInt(txtQuantity.getText()));
            item.setUnitPrice(Double.parseDouble(txtUnitPrice.getText()));
            new OrderItemDAO().insert(item);
            loadItems();
            clearFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saving item: " + e.getMessage());
        }
    }

    private void loadItems() {
        try {
            items = new OrderItemDAO().getAll();
            listModel.clear();
            for (OrderItem i : items) {
                listModel.addElement("Item #" + i.getId() + " | Order: " + i.getOrderId() + " | Qty: " + i.getQuantity());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading items: " + e.getMessage());
        }
    }

    private void clearFields() {
        txtOrderId.setText("");
        txtProductId.setText("");
        txtQuantity.setText("");
        txtUnitPrice.setText("");
    }

    public static void main(String[] args) {
        new OrderItemView();
    }
}
