package Main.UiConsole;

import Main.Database.Category;
import Main.Service.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class AddPanel extends JPanel {
    private JButton button;
    private JButton populateBtn;
    private JLabel label;
    private JTextField countTextField;
    private Service service;
    private ProductTable table;

    public AddPanel(Service service, ProductTable table) {
        this.setLayout(new GridLayout(0,1,3,3));
        this.table = table;
        button = new JButton("Add product");
        this.service = service;
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                createAddForm();
            }
        });
        this.add(button);
        this.label = new JLabel("Number of random products to be inserted :");
        this.add(label);
        this.countTextField = new JTextField();
        this.add(countTextField);
        this.populateBtn = new JButton("Add random products");
        this.populateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try{
                    service.populateProductList(Integer.parseInt(countTextField.getText()));
                }catch (NumberFormatException e){
                    JOptionPane.showMessageDialog(null, "Please insert integer in the text field.");
                }
                table.repopulateTable(service.getProductList());
            }
        });
        this.add(populateBtn);
        this.setVisible(true);
    }
    private void createAddForm(){
        JFrame addFrame = new JFrame("Add new product");
        Container container = addFrame.getContentPane();
        GridLayout layout = new GridLayout(0,2,3,3);
        container.setLayout(layout);
        Map<JLabel, JTextField> fields = new HashMap<>();
        String[] fieldsNames = {"Name", "Price", "Discount", "Description"};
        for (String fieldName:fieldsNames) {
            fields.put(new JLabel(fieldName), new JTextField());
        }
        JLabel label = new JLabel("Category");
        JComboBox<Category> comboBox = new JComboBox<>(Category.values());
        container.add(label);
        container.add(comboBox);
        for (Map.Entry<JLabel,JTextField> fieldEntry:fields.entrySet()) {
            container.add(fieldEntry.getKey());
            container.add(fieldEntry.getValue());
        }
        JButton btnAdd = new JButton("Add");
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Map<String, String> userInput = new HashMap<>();
                for (Map.Entry<JLabel, JTextField> fieldEntry: fields.entrySet()){
                    userInput.put(fieldEntry.getKey().getText(),fieldEntry.getValue().getText());
                }
                if(service.addProduct((Category) comboBox.getSelectedItem(),userInput)){
                    table.repopulateTable(service.getProductList());
                    addFrame.dispose();
                }
            }
        });
        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                addFrame.dispose();
            }
        });

        container.add(btnAdd);
        container.add(btnCancel);
        addFrame.pack();
        addFrame.setLocationRelativeTo(null);
        addFrame.setVisible(true);
    }

}
