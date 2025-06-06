package view;

import dao.AccompanimentDAO;
import dao.OrderItemAccompanimentDAO;
import model.Accompaniment;
import model.OrderItemAccompaniment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class OrderItemAccompanimentView extends JFrame {
    private int orderItemId;
    private JList<Accompaniment> accompanimentJList;
    private DefaultListModel<Accompaniment> listModel;

    public OrderItemAccompanimentView(int orderItemId) {
        this.orderItemId = orderItemId;
        setTitle("Select Accompaniments for OrderItem ID: " + orderItemId);
        setSize(400, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        listModel = new DefaultListModel<>();
        accompanimentJList = new JList<>(listModel);
        accompanimentJList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        add(new JScrollPane(accompanimentJList), BorderLayout.CENTER);

        JButton btnSave = new JButton("Save Accompaniments");
        btnSave.addActionListener(this::saveSelectedAccompaniments);
        add(btnSave, BorderLayout.SOUTH);

        loadAccompaniments();
        setVisible(true);
    }

    private void loadAccompaniments() {
        try {
            List<Accompaniment> list = new AccompanimentDAO().getAll();
            listModel.clear();
            for (Accompaniment a : list) {
                listModel.addElement(a);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading accompaniments: " + e.getMessage());
        }
    }

    private void saveSelectedAccompaniments(ActionEvent e) {
        try {
            new OrderItemAccompanimentDAO().deleteByOrderItemId(orderItemId);
            List<Accompaniment> selected = accompanimentJList.getSelectedValuesList();
            for (Accompaniment a : selected) {
                OrderItemAccompaniment oia = new OrderItemAccompaniment(orderItemId, a.getId());
                new OrderItemAccompanimentDAO().insert(oia);
            }
            JOptionPane.showMessageDialog(this, "Accompaniments saved successfully!");
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error saving accompaniments: " + ex.getMessage());
        }
    }

    // Para teste
    public static void main(String[] args) {
        new OrderItemAccompanimentView(1); // Use o ID do item do pedido que deseja testar
    }
}
