package BusinessLogic;

import BusinessLogic.Validators.Validators;

import DataAccess.OrderDAO;

import Model.Client;
import Model.Orders;


import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * In aceasta clasa se apeleaza metodele din AbstractDAO pentru Order
 */
public class OrderBLL {

    private List<Validators<Orders>> valid;
    private OrderDAO orderDAO;

    public OrderBLL() {
        orderDAO = new OrderDAO();
    }

    /**
     * Apeleaza findAll
     * @return toate obiectele de tip Orders din tabel
     */

    public List<Orders> findAll() {
        List<Orders> o = orderDAO.findAll();
        if (o == null) {
            throw new NoSuchElementException("The orders where not found!");
        }
        return o;

    }

    /**
     * Apeleaza insert
     * @param o
     * @return order-ul inserat
     */

    public Orders insertOrder(Orders o)
    {
        Orders order = orderDAO.insert(o);
        if(order == null)
        {
            throw new NoSuchElementException("Order-ul nu a fost inserat!");
        }
        return order;
    }

    /**
     * Apeleaza update
     * @param o1
     * @return order-ul editat
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public Orders updateOrder(Orders o1) throws NoSuchFieldException, IllegalAccessException {
        Orders or= orderDAO.update(o1);
        if(or == null)
        {
            throw new NoSuchElementException("Order-ul nu a fost updatat!");
        }
        return or;
    }

    /**
     * Apeleaza findById
     * @param id
     * @return order-ul care are id-ul dat ca parametru
     */
    public Orders findOrderById(int id) {
        Orders o = orderDAO.findById(id);
        if (o == null) {
            throw new NoSuchElementException("The order with id =" + id + " was not found!");
        }
        return o;

    }

    /**
     * Apeleaza delete
     * @param o2
     * @return order-ul sters
     */
    public Orders deleteOrder(Orders o2)
    {
        boolean succes = orderDAO.delete(o2);
        if(!succes)
        {
            throw new NoSuchElementException("Order-ul nu a fost sters!");
        }
        return o2;
    }


    public DefaultTableModel getTableData()
    {
        DefaultTableModel data = orderDAO.populateTable(findAll());
        if(data == null)
        {
            throw new NoSuchElementException("There are no clients!");
        }
        return data;
    }
}
