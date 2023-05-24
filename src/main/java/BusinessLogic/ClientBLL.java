package BusinessLogic;

import BusinessLogic.Validators.AddressValidator;
import BusinessLogic.Validators.NameValidator;
import BusinessLogic.Validators.Validators;
import DataAccess.ClientDAO;
import Model.Client;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


public class ClientBLL {
    private List<Validators<Client>> valid;
    private ClientDAO clientDAO;

    /**
     * In aceasta clasa se apeleaza metodele din AbstractDAO pentru un client
     */
    public ClientBLL() {
        valid = new ArrayList<Validators<Client>>();
        valid.add(new AddressValidator());
        valid.add(new NameValidator());

        clientDAO = new ClientDAO();
    }

    /**
     * Aceasta functie apeleaza findById din AbstractDAO
     * @param id
     * @return clientul care are id-ul dat ca parametru
     */
    public Client findClientById(int id) {
        Client c = clientDAO.findById(id);
        if (c == null) {
            throw new NoSuchElementException("The client with id =" + id + " was not found!");
        }
        return c;

    }

    /**
     * Apeleaza insert
     * @param c
     * @return clientu inserat
     */

    public Client insertClient(Client c)
    {
        Client client = clientDAO.insert(c);
        if(client == null)
        {
            throw new NoSuchElementException("Clientul nu a fost inserat!");
        }
        return client;
    }

    /**
     * Apeleaza update
     * @param c
     * @return clientul care a fost editat
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public Client updateClient(Client c) throws NoSuchFieldException, IllegalAccessException {
        Client client = clientDAO.update(c);
        if(client == null)
        {
            throw new NoSuchElementException("Clientul nu a fost updatat!");
        }
        return client;
    }

    /**
     * Apeleaza delete
     * @param c
     * @return clientul sters
     */
    public Client deleteClient(Client c)
    {
        boolean succes = clientDAO.delete(c);
        if(!succes)
        {
            throw new NoSuchElementException("Clientul nu a fost sters!");
        }
        return c;
    }

    /**
     * Apeleaza metoda findAll
     * @return toti clientii dintr-un tabel
     */

    public List<Client> findAll() {
        List<Client> c = clientDAO.findAll();
        if (c == null) {
            throw new NoSuchElementException("The clients where not found!");
        }
        return c;

    }

    public DefaultTableModel getTableData()
    {
        DefaultTableModel data = clientDAO.populateTable(findAll());
        if(data == null)
        {
            throw new NoSuchElementException("There are no clients!");
        }
        return data;
    }
}


