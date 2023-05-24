package Model;

public class Orders {
    private int id;
    private int idClient;
    private int idProduct;
    private int amount;

    /**
     * In aceasta clasa se gaseste blueprint-ul unui order
     */
    public Orders() {

    }

    public Orders(int id, int idClient, int idProduct, int amount)
    {
        this.id = id;
        this.idClient = idClient;
        this.idProduct = idProduct;
        this.amount = amount;

    }

    @Override
    public String toString() {
        return "Order{" +
                "idOrder=" + id +
                ", idClient=" + idClient +
                ", idProduct=" + idProduct +
                ", amount=" + amount +
                '}';
    }

    /**
     * Setter pentru id-ul clientului
     * @param idClient
     */
    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    /**
     * Setter pentru id-ul produsului
     * @param idProduct
     */
    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    /**
     * Setter pentru id-ul clientului
     * @param amount
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * Setter pentru id-ul order-ului
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter pentru id
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Getter pentru id-ul clientului
     * @return idClient
     */
    public int getIdClient() {
        return idClient;
    }

    /**
     * Getter pentru id-ul produsului
     * @return idProduct
     */
    public int getIdProduct() {
        return idProduct;
    }

    /**
     * Getter pentru amount
     * @return amount
     */
    public int getAmount() {
        return amount;
    }
}
