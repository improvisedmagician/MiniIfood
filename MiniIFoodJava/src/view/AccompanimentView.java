package view;

import dao.AccompanimentDAO;
import model.Accompaniment;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AccompanimentView extends JFrame {
    private JTextField txtName, txtPrice;
    private DefaultListModel<String> listModel;
    private JList<String> accompanimentList;
    private List<Accompaniment> accompaniments;

    public AccompanimentView() {
        setTitle("Accompaniment Management");
        setSize(400, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(3, 2));
        txtName = new JTextField();
        txtPrice = new JTextField();

        form.add(new JLabel("Name:")); form.add(txtName);
        form.add(new JLabel("Price:")); form.add(txtPrice);

        JButton btnSave = new JButton("Save");
        btnSave.addActionListener(e -> saveAccompaniment());
        form.add(btnSave);

        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(e -> loadAccompaniments());
        form.add(btnRefresh);

        add(form, BorderLayout.NORTH);

        listModel = new DefaultListModel<>();
        accompanimentList = new JList<>(listModel);
        add(new JScrollPane(accompanimentList), BorderLayout.CENTER);

        loadAccompaniments();
        setVisible(true);
    }

    private void saveAccompaniment() {
        try {
            Accompaniment a = new Accompaniment();
            a.setName(txtName.getText());
            a.setPrice(Double.parseDouble(txtPrice.getText()));
            new AccompanimentDAO().insert(a);
            loadAccompaniments();
            clearFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saving: " + e.getMessage());
        }
    }

    private void loadAccompaniments() {
        try {
            accompaniments = new AccompanimentDAO().getAll();
            listModel.clear();
            for (Accompaniment a : accompaniments) {
                listModel.addElement(a.toString());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading: " + e.getMessage());
        }
    }

    private void clearFields() {
        txtName.setText("");
        txtPrice.setText("");
    }

    public static void main(String[] args) {
        new AccompanimentView();
    }
}
