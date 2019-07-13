package Main.UiConsole;

import Main.Database.Category;
import Main.Database.Product;
import Main.Service.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class FindPanel extends JPanel {
    private JPanel panelFindById;
    private JPanel panelGetAllList;
    private JPanel panelGetCategory;
    private ProductTable table;
    private Service service;

    public FindPanel(Service service, ProductTable table) {
        this.table = table;
        this.service = service;
        GridLayout gridLayout = new GridLayout(0,1,3,3);
        this.setLayout(gridLayout);

        this.populatePanelFindById();
        this.add(panelFindById);
        this.populatePanelGetAll();
        this.add(panelGetAllList);
        this.populatePanelGetCategory();
        this.add(panelGetCategory);
        this.setVisible(true);
    }
    public void populatePanelFindById(){
        panelFindById = new JPanel(new GridLayout(0,2,3,3));
        JLabel label = new JLabel("Enter product id you want to find");
        JTextField textField = new JTextField();
        textField.setMinimumSize(new Dimension(50,0));
        textField.setColumns(20);
        label.setLabelFor(textField);
        JButton btn = new JButton("Find product by id.");
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                List<Product> productList = new ArrayList<>();
                try{
                    productList.add(service.findById(Long.parseLong(textField.getText())));
                    table.repopulateTable(productList);
                }catch (NumberFormatException e){
                    table.repopulateTable(service.getProductList());
                    JOptionPane.showMessageDialog(null, "Invalid ID");
                }catch (NullPointerException e){
                    table.repopulateTable(service.getProductList());
                    JOptionPane.showMessageDialog(null, "Invalid ID");
                }
            }
        });
        JButton removeBtn = new JButton("Remove it.");
        removeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    service.removeById(Long.parseLong(textField.getText()));
                }catch (NumberFormatException e){
                    JOptionPane.showMessageDialog(null, "Invalid ID");
                }
                table.repopulateTable(service.getProductList());
            }
        });

        panelFindById.add(label);
        panelFindById.add(textField);
        panelFindById.add(btn);
        panelFindById.add(removeBtn);
        panelFindById.setVisible(true);
    }

    private void populatePanelGetAll(){
        panelGetAllList = new JPanel(new BorderLayout());
        JButton button = new JButton("Get all products");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                List<Product> productList = service.getProductList();
                table.repopulateTable(productList);
            }
        });
        panelGetAllList.add(button);
        panelGetAllList.setVisible(true);
    }

    private void populatePanelGetCategory(){
        panelGetCategory = new JPanel(new BorderLayout());
        JComboBox<Category> comboBox = new JComboBox<>(Category.values());
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                List<Product> productList = new ArrayList<>();
                productList = service.getListByCategory((Category) comboBox.getSelectedItem());
                table.repopulateTable(productList);
            }
        });
        panelGetCategory.add(comboBox);
    }
}
