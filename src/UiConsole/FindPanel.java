package UiConsole;

import Database.Category;
import Database.Product;
import Service.Service;

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
        this.setLayout(new GridLayout(0,1));

        this.populatePanelFindById();
        this.add(panelFindById);
        this.populatePanelGetAll();
        this.add(panelGetAllList);
        this.populatePanelGetCategory();
        this.add(panelGetCategory);
        this.setVisible(true);
    }
    public void populatePanelFindById(){
        panelFindById = new JPanel(new BorderLayout());
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
                }catch (NumberFormatException e){

                }
                table.repopulateTable(productList);
            }
        });

        panelFindById.add(label, BorderLayout.WEST);
        panelFindById.add(textField, BorderLayout.EAST);
        panelFindById.add(btn, BorderLayout.PAGE_END);
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
