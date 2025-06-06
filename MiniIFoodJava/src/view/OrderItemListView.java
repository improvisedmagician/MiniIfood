package view;

import dao.OrderItemDAO;
import model.OrderItem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class OrderItemListView extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnRefresh, btnDelete, btnAccompaniments;

    public OrderItemListView() {
        setTitle("Order Items");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new Object[]{"ID", "Order ID", "Product ID", "Qty", "Unit Price"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelButtons = new JPanel();
        btnRefresh = new JButton("Refresh");
        btnDelete = new JButton("Delete");
        btnAccompaniments = new JButton("Accompaniments");

        panelButtons.add(btnRefresh);
        panelButtons.add(btnDelete);
        panelButtons.add(btnAccompaniments);

        add(panelButtons, BorderLayout.SOUTH);

        btnRefresh.addActionListener(this::refreshList);
        btnDelete.addActionListener(this::deleteSelectedItem);
        btnAccompaniments.addActionListener(this::openAccompanimentSelector);

        refreshList(null);
        setVisible(true);
    }

    private void refreshList(ActionEvent e) {
        try {
            tableModel.setRowCount(0);
            List<OrderItem> items = new OrderItemDAO().getAll();
            for (OrderItem item : items) {
                tableModel.addRow(new Object[]{
                        item.getId(),
                        item.getOrderId(),
                        item.getProductId(),
                        item.getQuantity(),
                        item.getUnitPrice()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading order items: " + ex.getMessage());
        }
    }

    private void deleteSelectedItem(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int itemId = (int) tableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Delete OrderItem ID " + itemId + "?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    new OrderItemDAO().delete(itemId);
                    refreshList(null);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error deleting: " + ex.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a row first.");
        }
    }

    private void openAccompanimentSelector(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int itemId = (int) tableModel.getValueAt(selectedRow, 0);
            new OrderItemAccompanimentView(itemId);
        } else {
            JOptionPane.showMessageDialog(this, "Select a row first.");
        }
    }

    public static void main(String[] args) {
        new OrderItemListView();
    }
}
