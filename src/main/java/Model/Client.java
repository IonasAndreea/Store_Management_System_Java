package Model;

public class Client {
    private int id;
    private String name;
    private String address;
    private String email;

    /**
     * In aceasta clasa este blueprint-ul pentru un client
     */
    public Client() {

    }

    public Client(int id, String name, String address, String email) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
    }

    /**
     *
     * @return id-ul clientului
     */
    public int getId() {
        return id;
    }

    /**
     * Prin aceasta metoda se seteaza id-ul clientului
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return numele clientului
     */
    public String getName() {
        return name;
    }

    /**
     * In aceasta metoda se seteaza numele clientului
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return adressa clientului
     */
    public String getAddress() {
        return address;
    }

    /**
     * In aceasta metoda se seteaza addressa clientului
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     * @return email-ul clientului
     */
    public String getEmail() {
        return email;
    }

    /**
     * In aceasta metoda se seteaza email-ul clientului
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Client [id = " + id + ", name = " + name + ", address = " + address + ", email = " + email + "]";
    }

}
