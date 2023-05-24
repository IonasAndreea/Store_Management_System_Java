package Model;

public class Product {
    private int id;
    private String name;
    private int stoc;
    private double pret;

    public Product(int id, String name, int stoc, double pret)
    {
        this.id =id;
        this.name = name;
        this.stoc = stoc;
        this.pret = pret;

    }
    public Product() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStoc()
    {
        return stoc;
    }

    public void setStoc(int stoc)
    {
        this.stoc =stoc;
    }

    public double getPret()
    {
        return pret;
    }

    public void setPret(double pret)
    {
        this.pret = pret;
    }

    @Override
    public String toString() {
        return "Product [id = " + id + ", name = " + name + ", stoc = " + stoc + ", pret = " + pret + "]";
    }


}
