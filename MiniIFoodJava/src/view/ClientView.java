package view;

import dao.ClientDAO;
import model.Client;
import service.ViaCEPService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class ClientView extends JFrame {
    private JTextField txtName, txtEmail, txtCep, txtAddress;
    private DefaultListModel<String> listModel;
    private JList<String> clientList;
    private List<Client> clients;

    public ClientView() {
        setTitle("Client Management");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(6, 2));
        txtName = new JTextField();
        txtEmail = new JTextField();
        txtCep = new JTextField();
        txtAddress = new JTextField();

        txtCep.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                String address = ViaCEPService.getAddressByCep(txtCep.getText());
                txtAddress.setText(address);
            }
        });

        panel.add(new JLabel("Name:")); panel.add(txtName);
        panel.add(new JLabel("Email:")); panel.add(txtEmail);
        panel.add(new JLabel("CEP:")); panel.add(txtCep);
        panel.add(new JLabel("Address:")); panel.add(txtAddress);

        JButton btnSave = new JButton("Save");
        btnSave.addActionListener(e -> saveClient());
        panel.add(btnSave);

        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(e -> loadClients());
        panel.add(btnRefresh);

        add(panel, BorderLayout.NORTH);

        listModel = new DefaultListModel<>();
        clientList = new JList<>(listModel);
        add(new JScrollPane(clientList), BorderLayout.CENTER);

        loadClients();
        setVisible(true);
    }

    private void saveClient() {
        try {
            Client client = new Client();
            client.setName(txtName.getText());
            client.setEmail(txtEmail.getText());
            client.setCep(txtCep.getText());
            client.setAddress(txtAddress.getText());
            new ClientDAO().insert(client);
            loadClients();
            clearFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saving client: " + e.getMessage());
        }
    }

    private void loadClients() {
        try {
            clients = new ClientDAO().getAll();
            listModel.clear();
            for (Client c : clients) {
                listModel.addElement(c.getName() + " - " + c.getEmail());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading clients: " + e.getMessage());
        }
    }

    private void clearFields() {
        txtName.setText("");
        txtEmail.setText("");
        txtCep.setText("");
        txtAddress.setText("");
    }

    public static void main(String[] args) {
        new ClientView();
    }
}