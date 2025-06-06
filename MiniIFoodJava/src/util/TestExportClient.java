package util;

import model.Client;
import java.util.ArrayList;
import java.util.List;

public class TestExportClient {
    public static void main(String[] args) {
        // Criando uma lista de clientes fictícios
        List<Client> clients = new ArrayList<>();
        clients.add(new Client(1, "Maria", "maria@email.com", "Rua A", "12345678900", "Cidade A", "MG", "12345-678"));
        clients.add(new Client(2, "João", "joao@email.com", "Rua B", "09876543211", "Cidade B", "SP", "98765-432"));

        // Exportando os dados para um arquivo
        DataExporter.exportData(clients, "clients.ser");

        // Importando os dados de volta
        List<Client> imported = DataExporter.importData("clients.ser");

        System.out.println("\n=== Clientes importados ===");
        if (imported != null) {
            for (Client c : imported) {
                System.out.println(c.getId() + " - " + c.getName() + " - " + c.getEmail());
            }
        } else {
            System.out.println("Falha ao importar.");
        }
    }
}
