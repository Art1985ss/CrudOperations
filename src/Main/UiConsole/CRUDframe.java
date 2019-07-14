package Main.UiConsole;

import Main.Database.*;
import Main.Service.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CRUDframe extends JFrame {
    private JSplitPane splitPane;
    private JPanel panelLeft;
    private JPanel panelRight;
    private ProductTable table;
    private Service service;



    public CRUDframe() throws HeadlessException {
        super("CRUD operations");
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        service = new Service(new ProductDatabase());
        //service.populateProductList(200);
        table = new ProductTable(service.getProductList(), service, this);


        splitPane = new JSplitPane();
        panelLeft = new JPanel();
        this.populateLeft();
        panelRight = new JPanel(new BorderLayout());
        panelRight.add(table);
        panelRight.setVisible(true);
        splitPane.setLeftComponent(panelLeft);
        splitPane.setRightComponent(new JScrollPane(panelRight));
        splitPane.setSize(900,600);
        this.add(splitPane);
        this.pack();
        this.setVisible(true);
    }

    @Override
    public void repaint() {
        super.repaint();
        if (this.panelRight != null) this.panelRight.repaint();
        if (this.panelLeft != null) this.panelLeft.repaint();
        if (this.splitPane != null) this.splitPane.repaint();
        super.repaint();
    }

    private void populateLeft(){
        GridLayout layout = new GridLayout(0, 1);
        layout.setVgap(2);
        panelLeft.setLayout(layout);
        panelLeft.add(new AddPanel(service, table));
        panelLeft.add(new FindPanel(service, table));
        panelLeft.add(new ChangePanel(service, table));
        //panelLeft.add(addProductButton());
        panelLeft.setVisible(true);
    }

    private JButton addProductButton(){
        JButton button = new JButton("Add product");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFrame addFrame = new JFrame("Add new product");
                Container container = addFrame.getContentPane();
                GridLayout layout = new GridLayout(0,2);
                container.setLayout(layout);
                JLabel label = new JLabel("Category");
                JComboBox<Category> comboBox = new JComboBox<>(Category.values());
                JTextField textField;
                container.add(label);
                container.add(comboBox);
                String[] fields = {"Name", "Price", "Discount", "Description"};
                for (String field: fields) {
                    label = new JLabel(field);
                    textField = new JTextField();
                    container.add(label);
                    container.add(textField);
                }
                label = new JLabel("Execute");
                container.add(label);
                JButton btn = new JButton("Add");
                btn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        for (Component component: getComponents()) {
                            System.out.println(component.getName());
                        }
                    }
                });
                container.add(btn);


                addFrame.pack();
                addFrame.setLocationRelativeTo(null);
                addFrame.setVisible(true);
            }
        });
        button.setVisible(true);
        return button;
    }


}
