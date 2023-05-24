package DataAccess;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import Connection.ConnectionFactory;

import javax.swing.table.DefaultTableModel;

/**
 * Programul implementeaza metode de inserare, editare si stergere folosind Reflection
 * @param <T>
 */
public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
    private static String insertString;
    private static String deleteString;
    private static String updateString;

    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        //  insertString = "INSERT INTO " + type.getSimpleName() + " VALUES (";
        // for (int i = 0; i < type.getDeclaredFields().length - 1; i++) {
        //  insertString += "?,";
        // }
        // insertString += "?);";
        //  deleteString = "DELETE FROM " + type.getSimpleName() + " WHERE id = ?;";
        // System.out.println(insertString);
        //findLastIdStatementString = "SELECT * FROM " + type.getSimpleName() + " ORDER BY id DESC LIMIT 1;";
    }

    /**
     *Aceasta metoda este folosita pentru a genera un query pentru operatia de inserare
     * @param t Un obiect de tip T
     * @return String un String care contine sintaxa de inserare a lui t in daza de date
     */
    private String insertQuery(T t) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(t.getClass().getSimpleName());
        sb.append(" VALUES (");
        for (int i = 0; i < t.getClass().getDeclaredFields().length - 1; i++)
            sb.append("?,");
        sb.append("?);");
        return sb.toString();
    }

    /**
     * Aceasta metoda este folosita pentru a genera un query pentru operatia de stergere
     * @param t
     * @return String
     */
    private String deleteQuery(T t) {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ");
        sb.append(t.getClass().getSimpleName());
        sb.append(" WHERE id = ?");
        return sb.toString();
    }

    /**
     * Aceasta metoda este folosita pentru a genera un query pentru operatia de editare
     * @param t
     * @return
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     */
    private String editQuery(T t) throws IllegalAccessException, NoSuchFieldException {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(t.getClass().getSimpleName());
        sb.append(" SET ");
        int id = -1;
        for (Field aux : t.getClass().getDeclaredFields()) {
            aux.setAccessible(true);
            sb.append(aux.getName() + "=" + "\"");
            Object value = aux.get(t);
            if (id == -1) {
                id = (int) value;
            }
            sb.append(value.toString()).append("\"").append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append(" WHERE id = ").append(id);
        System.out.println(sb);
        return sb.toString();
    }

    /**
     * Aceasta metoda este folosita pentru a genera un query pentru selectie
     * @param field
     * @return
     */
    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " = ?");
        return sb.toString();

    }

    /**
     * Aceasta metoda este folosita pentru a gasi toate instantele unui obiect
     * @return Un obiect
     */
    public List<T> findAll() {
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM " + type.getSimpleName();
        try {
            con = ConnectionFactory.getConnection();
            statement = con.prepareStatement(query);
            resultSet = statement.executeQuery();
            return createObjects(resultSet);


        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO: find All " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(con);
        }
        return null;
    }

    /**
     * Acesta metoda gaseste obiectul care are id-ul dat ca parametru
     * @param id
     * @return Un obiect
     */
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            System.out.println(statement);
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Aceasta metoda creaza un obiect
     * @param resultSet
     * @return O lista de obiecte T
     */
    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T) ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Aceasta metoda insereaza obiectul t in baza de date
     * @param t
     * @return Obictul inserat
     */
    public T insert(T t) {
        Connection connection = null;
        String query = insertQuery(t);
        PreparedStatement statement = null;
        int i = 1;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            for (Field field : type.getDeclaredFields()) {
                String fieldName = field.getName();
                PropertyDescriptor d = new PropertyDescriptor(fieldName, type);
                Method getter = d.getReadMethod();
                if (field.getType().getName().equals("int")) {
                    statement.setInt(i++, (int) getter.invoke(t));
                } else if (field.getType().getName().equals("double")) {
                    statement.setDouble(i++, (double) getter.invoke(t));
                } else {
                    statement.setString(i++, (String) getter.invoke(t));
                }

            }
            statement.executeUpdate();
        } catch (SQLException | IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:Insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return t;
    }

    /**
     * Aceasta metoda strge obiectul t din baza de date
     * @param t
     * @return true sau false
     */
    public boolean delete(T t) {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement deleteSt = null;
        try {

            connection = ConnectionFactory.getConnection();
            deleteSt = connection.prepareStatement(deleteQuery(t));
            int id = -1;
            for (Field aux : t.getClass().getDeclaredFields()) {
                aux.setAccessible(true);
                Object value = aux.get(t);
                if (id == -1) {
                    id = (int) value;
                }
                deleteSt.setInt(1, id);
                System.out.println(deleteSt);
            /*for (Field f : type.getDeclaredFields()) {
                String fieldName = f.getName();
                if (fieldName.equals("id")) {
                    PropertyDescriptor pd = new PropertyDescriptor(fieldName, type);
                    Method getter = pd.getReadMethod();
                    deleteSt.setInt(1, (int) getter.invoke(t));
                    break;
                }*/
                deleteSt.executeUpdate();

                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionFactory.close(deleteSt);
            ConnectionFactory.close(connection);
        }
        return false;
    }

    public List<String> getTableColumns() {
        List<String> c = new ArrayList<>();
        for (Field f : type.getDeclaredFields()) {
            f.setAccessible(true);
            c.add(f.getName().toUpperCase());
        }
        return c;
    }

    /**
     * Editeaza datele obiectului t
     * @param t
     * @return Obiectul cu noile date
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */

    public T update(T t) throws NoSuchFieldException, IllegalAccessException {
        Connection c = ConnectionFactory.getConnection();
        PreparedStatement updateS = null;
        String query = editQuery(t);
        try {
            int id = -1;
            updateS = c.prepareStatement(query);
            updateS.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            ConnectionFactory.close(updateS);
            ConnectionFactory.close(c);
        }

        return t;
    }

    public DefaultTableModel populateTable(List<T> obj) {
        DefaultTableModel table = new DefaultTableModel();

        Class<?> type = obj.get(0).getClass();

        for (Field f : type.getDeclaredFields()) {
            f.setAccessible(true);
            table.addColumn(f.getName());
        }

        Vector<String> columnNames = new Vector<>();
        for (int i = 0; i < table.getColumnCount(); i++) {
            columnNames.add(table.getColumnName(i));
        }
        table.addRow(columnNames);

        for (T t : obj) {
            Vector<Object> data = new Vector<>();
            for (Field f : type.getDeclaredFields()) {
                f.setAccessible(true);
                Object value;
                try {
                    value = f.get(t);
                    data.add(value);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
            table.addRow(data);
        }
        return table;
    }

}
