package model;

import java.io.Serializable;

public class Client implements Serializable {
    private int id;
    private String name;
    private String email;
    private String address;
    private String cpf;
    private String city;
    private String state;
    private String cep;

    // Construtor completo
    public Client(int id, String name, String email, String address, String cpf, String city, String state, String cep) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.cpf = cpf;
        this.city = city;
        this.state = state;
        this.cep = cep;
    }

    // Construtor vazio
    public Client() {}

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }
}
