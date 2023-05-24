package Presentation;

import BusinessLogic.ClientBLL;
import BusinessLogic.OrderBLL;
import BusinessLogic.ProductBLL;
import Model.Client;
import Model.Orders;
import Model.Product;
import Start.ReflectionExample;

import javax.swing.*;
import javax.swing.plaf.ProgressBarUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;

/**
 * In aceasta clasa se implementeaza interfata grafica
 * Fereastra de start, fereastra pentru client, fereastra pentru produs si fereastra pentru order
 */
public class View extends JFrame {
    private JFrame frame;
    private JLabel labelTitle;

    private JPanel panelMain;
    private JButton CLIENT;
    private JButton ORDER;
    private JButton PRODUCT;
    ClientBLL clientBll = new ClientBLL();
    ProductBLL productBll = new ProductBLL();

    OrderBLL orderBll = new OrderBLL();


    public View () {
        this.frame = new JFrame();
        this.labelTitle = new JLabel();
        this.panelMain = new JPanel();
        this.CLIENT = new JButton("CLIENT");
        this.ORDER = new JButton("ORDER");
        this.PRODUCT = new JButton("PRODUCT");

        labelTitle.setText("ORDERS MANAGEMENT");
        labelTitle.setFont(new Font("Times New Roman", Font.ITALIC | Font.BOLD, 20));


        frame.setTitle("Orders Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panelMain.setLayout(new GridLayout(3, 3, 30, 30));
        ;
        panelMain.setBorder(BorderFactory.createEmptyBorder(100, 170, 100, 50));
        panelMain.setBackground(Color.orange);
        panelMain.add(ORDER);
        panelMain.add(Box.createGlue());
        panelMain.add(CLIENT);
        panelMain.add(Box.createGlue());
        panelMain.add(PRODUCT);
        frame.setResizable(false);
        frame.setSize(500, 500);
        frame.setVisible(true);
        frame.add(panelMain);


        CLIENT.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SecondView();
            }
        });

        PRODUCT.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ProductView();
            }

        });

        ORDER.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OrderView();
            }
        });


    }


    public void OrderView() {
        JFrame frame2 = new JFrame();
        JPanel panelMain = new JPanel();
        JLabel titleLabel = new JLabel();
        JLabel idLabelOrder = new JLabel();
        JLabel idLabelPr = new JLabel();
        JLabel idLabelCli = new JLabel();
        JLabel amountLabel = new JLabel();
        JTextField idTextO = new JTextField();
        JTextField idTextP = new JTextField();
        JTextField idTextC = new JTextField();
        JTextField amountText = new JTextField();
        JButton addButtonO = new JButton("Add Order");
        JButton editButtonO = new JButton("Edit Order");
        JButton deleteButtonO = new JButton("Delete Order");
        JButton seeAllButtonO = new JButton("See All Orders");
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel = new JPanel();


        titleLabel.setText("ORDERS");
        titleLabel.setFont(new Font("Times New Roman", Font.ITALIC | Font.BOLD, 20));

        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 3, 2));
        panel.add(titleLabel);

        idLabelOrder.setText("ID Order = ");
        idLabelOrder.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
        idLabelOrder.setForeground(Color.black);

        idTextO.setFont(new Font("Tahoma", Font.ITALIC, 12));
        idTextO.setPreferredSize(new Dimension(220, 20));

        idLabelCli.setText("ID Client = ");
        idLabelCli.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
        idLabelCli.setForeground(Color.black);

        idTextC.setFont(new Font("Tahoma", Font.ITALIC, 12));
        idTextC.setPreferredSize(new Dimension(220, 20));

        idLabelPr.setText("ID Product = ");
        idLabelPr.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
        idLabelPr.setForeground(Color.black);

        idTextP.setFont(new Font("Tahoma", Font.ITALIC, 12));
        idTextP.setPreferredSize(new Dimension(220, 20));


        amountLabel.setText("Amount  = ");
        amountLabel.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
        amountLabel.setForeground(Color.black);

        amountText.setFont(new Font("Tahoma", Font.ITALIC, 12));
        amountText.setPreferredSize(new Dimension(220, 20));

        panel1.setLayout(new GridLayout(10, 2));
        panel1.add(idLabelOrder);
        panel1.add(idTextO);
        panel1.add(idLabelCli);
        panel1.add(idTextC);
        panel1.add(idLabelPr);
        panel1.add(idTextP);
        panel1.add(amountLabel);
        panel1.add(amountText);

        panel2.setLayout(new GridLayout(1, 3, 5, 5));
        panel2.add(addButtonO);
        panel2.add(deleteButtonO);
        panel2.add(editButtonO);
        panel2.add(seeAllButtonO);

        frame2.setTitle("Orders");
        frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        panelMain.setLayout(new BoxLayout(panelMain, BoxLayout.Y_AXIS));
        frame2.setResizable(false);
        frame2.setSize(500, 500);
        frame2.setVisible(true);


        panelMain.add(panel);
        panelMain.add(panel1);
        panelMain.add(panel2);
        frame2.add(panelMain);

        seeAllButtonO.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                /*List<Orders> o = null;
                try {
                    o = orderBll.findAll();

                } catch (Exception ex) {
                    System.out.println("Error");
                }
                assert o != null;
                for (Orders index : o) {
                    ReflectionExample.retrieveProperties(index);
                }*/
                TableViewO();
            }
        });

        addButtonO.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Client c = null;
                Product p = null;
                int idCl = Integer.parseInt(idTextC.getText());
                int idPr = Integer.parseInt(idTextP.getText());
                try {
                    c = clientBll.findClientById(idCl);
                }catch (Exception ex)
                {
                    System.out.println("Client id is not valid!");
                    return;
                }
                try {
                    p = productBll.findProductById(idPr);

                }catch (Exception ex)
                {
                    System.out.println("Product id is not valid!");
                    return;
                }

                Orders o = new Orders(Integer.parseInt(idTextO.getText()), Integer.parseInt(idTextC.getText()), Integer.parseInt(idTextP.getText()), Integer.parseInt(amountText.getText()));
                int want = Integer.parseInt(amountText.getText());
                int have = p.getStoc();
                if ( have < want)
                {
                    System.out.println("Not enough in stoc!");
                    return;

                }
                p.setStoc(have-want);
                try {
                    productBll.updateProduct(p);
                } catch (NoSuchFieldException ex) {
                    throw new RuntimeException(ex);
                } catch (IllegalAccessException ex) {
                    throw new RuntimeException(ex);
                }
                orderBll.insertOrder(o);
            }
        });

        deleteButtonO.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Orders aux = orderBll.findOrderById(Integer.parseInt(idTextO.getText()));
                orderBll.deleteOrder(aux);

            }
        });

        editButtonO.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Orders aux = orderBll.findOrderById(Integer.parseInt(idTextO.getText()));
                Orders o = new Orders(Integer.parseInt(idTextO.getText()), Objects.equals(idTextC.getText(), "") ? aux.getIdClient() : Integer.parseInt(idTextC.getText()), Objects.equals(idTextP.getText(), "") ? aux.getIdProduct() : Integer.parseInt(idTextP.getText()), Objects.equals(amountText.getText(), "") ? aux.getAmount() : Integer.parseInt(amountText.getText()));
                System.out.println(o);
                try {
                    orderBll.updateOrder(o);
                } catch (NoSuchFieldException ex) {
                    throw new RuntimeException(ex);
                } catch (IllegalAccessException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


    }

    public void TableViewC() {
        JFrame frameT = new JFrame();
        JTable jt = new JTable();
        JPanel panelMainT = new JPanel();
        panelMainT.add(jt);

        jt.setModel(clientBll.getTableData());
        frameT.setTitle("Clients Table");
        frameT.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        panelMainT.setLayout(new BoxLayout(panelMainT, BoxLayout.Y_AXIS));
        frameT.setResizable(false);
        frameT.setSize(700, 700);
        frameT.setVisible(true);
        panelMainT.add(jt);
        frameT.add(panelMainT);


    }

    public void TableViewP() {
        JFrame frameT = new JFrame();
        JTable jt = new JTable();
        JPanel panelMainT = new JPanel();
        panelMainT.add(jt);

        jt.setModel(productBll.getTableData());
        frameT.setTitle("Clients Table");
        frameT.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        panelMainT.setLayout(new BoxLayout(panelMainT, BoxLayout.Y_AXIS));
        frameT.setResizable(false);
        frameT.setSize(700, 700);
        frameT.setVisible(true);
        panelMainT.add(jt);
        frameT.add(panelMainT);


    }

    public void TableViewO() {
        JFrame frameT = new JFrame();
        JTable jt = new JTable();
        JPanel panelMainT = new JPanel();
        panelMainT.add(jt);

        jt.setModel(orderBll.getTableData());
        frameT.setTitle("Clients Table");
        frameT.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        panelMainT.setLayout(new BoxLayout(panelMainT, BoxLayout.Y_AXIS));
        panelMainT.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        frameT.setResizable(false);
        frameT.setSize(700, 700);
        frameT.setVisible(true);
        panelMainT.add(jt);
        frameT.add(panelMainT);


    }
    public void SecondView() {
        JFrame frame1 = new JFrame();
        JPanel panelMain = new JPanel();
        JLabel titleLabel = new JLabel();
        JLabel idLabel1 = new JLabel();
        JLabel addressLabel = new JLabel();
        JLabel nameLabel1 = new JLabel();
        JLabel emailLabel = new JLabel();
        JTextField idText1 = new JTextField();
        JTextField nameText1 = new JTextField();
        JTextField addressText = new JTextField();
        JTextField emailText = new JTextField();
        JButton addButtonC = new JButton("Add Client");
        JButton editButtonC = new JButton("Edit Client");
        JButton deleteButtonC = new JButton("Delete Client");
        JButton seeAllButtonC = new JButton("See All Clients");
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel = new JPanel();


        titleLabel.setText("CLIENTS");
        titleLabel.setFont(new Font("Times New Roman", Font.ITALIC | Font.BOLD, 20));

        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 3, 2));
        panel.add(titleLabel);

        idLabel1.setText("ID  = ");
        idLabel1.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
        idLabel1.setForeground(Color.black);

        idText1.setFont(new Font("Tahoma", Font.ITALIC, 12));
        idText1.setPreferredSize(new Dimension(220, 20));


        nameLabel1.setText("Name  = ");
        nameLabel1.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
        nameLabel1.setForeground(Color.black);

        nameText1.setFont(new Font("Tahoma", Font.ITALIC, 12));
        nameText1.setPreferredSize(new Dimension(220, 20));

        addressLabel.setText("Address  = ");
        addressLabel.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
        addressLabel.setForeground(Color.black);

        addressText.setFont(new Font("Tahoma", Font.ITALIC, 12));
        addressText.setPreferredSize(new Dimension(220, 20));

        emailLabel.setText("Email  = ");
        emailLabel.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
        emailLabel.setForeground(Color.black);

        emailText.setFont(new Font("Tahoma", Font.ITALIC, 12));
        emailText.setPreferredSize(new Dimension(220, 20));

        panel1.setLayout(new GridLayout(10, 2));
        panel1.add(idLabel1);
        panel1.add(idText1);
        panel1.add(nameLabel1);
        panel1.add(nameText1);
        panel1.add(addressLabel);
        panel1.add(addressText);
        panel1.add(emailLabel);
        panel1.add(emailText);


        panel2.setLayout(new GridLayout(1, 3, 5, 5));
        panel2.add(addButtonC);
        panel2.add(deleteButtonC);
        panel2.add(editButtonC);
        panel2.add(seeAllButtonC);

        frame1.setTitle("Clients");
        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        panelMain.setLayout(new BoxLayout(panelMain, BoxLayout.Y_AXIS));
        frame1.setResizable(false);
        frame1.setSize(500, 500);
        frame1.setVisible(true);


        panelMain.add(panel);
        panelMain.add(panel1);
        panelMain.add(panel2);
        frame1.add(panelMain);

        seeAllButtonC.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               // List<Client> c1 = null;
               // try {
                   TableViewC();
               // } catch (Exception ex) {
                //    System.out.println("Error");
               // }
               // assert c1 != null;
               // for (Client index : c1) {
               //     ReflectionExample.retrieveProperties(index);
                }
           // }
        });

        addButtonC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Client c = new Client(Integer.parseInt(idText1.getText()), nameText1.getText(), addressText.getText(), emailText.getText());
                clientBll.insertClient(c);
            }
        });

        editButtonC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Client aux = clientBll.findClientById(Integer.parseInt(idText1.getText()));
                Client c = new Client(Integer.parseInt(idText1.getText()), Objects.equals(nameText1.getText(), "") ? aux.getName() : nameText1.getText(), Objects.equals(addressText.getText(), "") ? aux.getAddress() : addressText.getText(), Objects.equals(emailText.getText(), "") ? aux.getEmail() : emailText.getText());
                System.out.println(c);
                try {
                    clientBll.updateClient(c);
                } catch (NoSuchFieldException ex) {
                    throw new RuntimeException(ex);
                } catch (IllegalAccessException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        deleteButtonC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Client aux = clientBll.findClientById(Integer.parseInt(idText1.getText()));
                clientBll.deleteClient(aux);

            }
        });

    }

    public void ProductView() {
        JFrame frame2 = new JFrame();
        JPanel panelMain = new JPanel();
        JLabel titleLabel = new JLabel();
        JLabel idLabel2 = new JLabel();
        JLabel stocLabel = new JLabel();
        JLabel nameLabel2 = new JLabel();
        JLabel pretLabel = new JLabel();
        JTextField idText2 = new JTextField();
        JTextField nameText2 = new JTextField();
        JTextField stocText = new JTextField();
        JTextField pretText = new JTextField();
        JButton addButtonP = new JButton("Add Product");
        JButton editButtonP = new JButton("Edit Product");
        JButton deleteButtonP = new JButton("Delete Product");
        JButton seeAllButtonP = new JButton("See All Products");
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel = new JPanel();


        titleLabel.setText("Products");
        titleLabel.setFont(new Font("Times New Roman", Font.ITALIC | Font.BOLD, 20));

        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 3, 2));
        panel.add(titleLabel);

        idLabel2.setText("ID  = ");
        idLabel2.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
        idLabel2.setForeground(Color.black);

        idText2.setFont(new Font("Tahoma", Font.ITALIC, 12));
        idText2.setPreferredSize(new Dimension(220, 20));


        nameLabel2.setText("Name  = ");
        nameLabel2.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
        nameLabel2.setForeground(Color.black);

        nameText2.setFont(new Font("Tahoma", Font.ITALIC, 12));
        nameText2.setPreferredSize(new Dimension(220, 20));

        stocLabel.setText("Stoc  = ");
        stocLabel.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
        stocLabel.setForeground(Color.black);

        stocText.setFont(new Font("Tahoma", Font.ITALIC, 12));
        stocText.setPreferredSize(new Dimension(220, 20));

        pretLabel.setText("Pret  = ");
        pretLabel.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
        pretLabel.setForeground(Color.black);

        pretText.setFont(new Font("Tahoma", Font.ITALIC, 12));
        pretText.setPreferredSize(new Dimension(220, 20));

        panel1.setLayout(new GridLayout(10, 2));
        panel1.add(idLabel2);
        panel1.add(idText2);
        panel1.add(nameLabel2);
        panel1.add(nameText2);
        panel1.add(stocLabel);
        panel1.add(stocText);
        panel1.add(pretLabel);
        panel1.add(pretText);


        panel2.setLayout(new GridLayout(1, 3, 5, 5));
        panel2.add(addButtonP);
        panel2.add(deleteButtonP);
        panel2.add(editButtonP);
        panel2.add(seeAllButtonP);

        frame2.setTitle("Products");
        frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        panelMain.setLayout(new BoxLayout(panelMain, BoxLayout.Y_AXIS));
        frame2.setResizable(false);
        frame2.setSize(600, 600);
        frame2.setVisible(true);


        panelMain.add(panel);
        panelMain.add(panel1);
        panelMain.add(panel2);
        frame2.add(panelMain);

        seeAllButtonP.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               /* List<Product> p = null;
                try {
                    p = productBll.findAll();

                } catch (Exception ex) {
                    System.out.println("Error");
                }
                assert p != null;
                for (Product index : p) {
                    ReflectionExample.retrieveProperties(index);
                }*/
                TableViewP();
            }
        });

        addButtonP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Product p = new Product(Integer.parseInt(idText2.getText()), nameText2.getText(), Integer.parseInt(stocText.getText()), Double.parseDouble(pretText.getText()));
                productBll.insertProduct(p);
            }
        });

        editButtonP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Product aux = productBll.findProductById(Integer.parseInt(idText2.getText()));
                Product c = new Product(Integer.parseInt(idText2.getText()), Objects.equals(nameText2.getText(), "") ? aux.getName() : nameText2.getText(), Objects.equals(stocText.getText(), "") ? aux.getStoc() : Integer.parseInt(stocText.getText()), Objects.equals(pretText.getText(), "") ? aux.getPret() : Integer.parseInt(pretText.getText()));
                System.out.println(c);
                try {
                    productBll.updateProduct(c);
                } catch (NoSuchFieldException ex) {
                    throw new RuntimeException(ex);
                } catch (IllegalAccessException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        deleteButtonP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Product aux = productBll.findProductById(Integer.parseInt(idText2.getText()));
                productBll.deleteProduct(aux);

            }
        });

    }


}



