package util;

import java.io.*;
import java.util.List;

public class DataExporter {

    // Serializa uma lista de objetos
    public static <T extends Serializable> void exportData(List<T> data, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(data);
            System.out.println("Dados exportados com sucesso para: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Desserializa uma lista de objetos
    public static <T extends Serializable> List<T> importData(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (List<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
