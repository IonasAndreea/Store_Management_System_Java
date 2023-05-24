package BusinessLogic;

import BusinessLogic.Validators.*;

import DataAccess.ProductDAO;
import Model.Client;
import Model.Product;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * In aceasta clasa apelam metodele din AbstractDAO pentu produse
 */
public class ProductBLL {
    private List<Validators<Product>> valid;
    private ProductDAO productDAO;

    /**
     * Un constructor in care cream un produs si un array cu validators pentru a valida input-ul
     */
    public ProductBLL() {
        ArrayList<Validators<Product>> valid = new ArrayList<Validators<Product>>();
        valid.add(new StocValidator());
        valid.add(new NameVProduct());

        productDAO = new ProductDAO();
    }

    /**
     * Apeleaza find all pentru tabela cu produse
     * @return toate produsele gasite
     */
    public List<Product> findAll() {
        List<Product> p = productDAO.findAll();
        if (p == null) {
            throw new NoSuchElementException("The product where not found!");
        }
        return p;

    }

    /**
     * Apeleaza findById
     * @param id
     * @return Produsul cu id dat ca parametru
     */
    public Product findProductById(int id) {
        Product c = productDAO.findById(id);
        if (c == null) {
            throw new NoSuchElementException("The product with id =" + id + " was not found!");
        }
        return c;

    }

    /**
     * Apeleaza insert
     * @param p
     * @return Produsul inserat
     */
    public Product insertProduct(Product p)
    {
        Product prod = productDAO.insert(p);
        if(prod == null)
        {
            throw new NoSuchElementException("Produsul nu a fost inserat!");
        }
        return prod;
    }

    /**
     * Apeleaza delete
     * @param c
     * @return produsul sters
     */
    public Product deleteProduct(Product c)
    {
        boolean succes = productDAO.delete(c);
        if(!succes)
        {
            throw new NoSuchElementException("Produsul nu a fost sters!");
        }
        return c;
    }

    /**
     * Apeleaza update
     * @param c
     * @return produsul cu datele editate
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public Product updateProduct(Product c) throws NoSuchFieldException, IllegalAccessException {
        Product prod = productDAO.update(c);
        if(prod == null)
        {
            throw new NoSuchElementException("Produsul nu a fost updatat!");
        }
        return prod;
    }

    public DefaultTableModel getTableData()
    {
        DefaultTableModel data = productDAO.populateTable(findAll());
        if(data == null)
        {
            throw new NoSuchElementException("There are no clients!");
        }
        return data;
    }
}
