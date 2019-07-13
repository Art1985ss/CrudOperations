package Main.UiConsole;


import Main.Database.*;
import Main.Service.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

public class ChangePanel extends JPanel {
    JComboBox<Category> comboBox;
    JLabel label;
    JTextField textField;
    JButton button;
    Service service;
    ProductTable table;

    public ChangePanel(Service service, ProductTable table) {
        super();
        this.service = service;
        this.table = table;
        this.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        this.label = new JLabel("Select category and enter new discount for category");
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 40;
        constraints.weightx = 0;
        constraints.gridwidth = 3;
        this.add(label,constraints);
        constraints = new GridBagConstraints();
        this.comboBox = new JComboBox<>(Category.values());
        constraints.fill = GridBagConstraints.CENTER;
        constraints.weightx = 0.5;
        constraints.gridx = 0;
        constraints.gridy = 1;
        this.add(comboBox,constraints);
        this.textField = new JTextField();
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                changeDiscount();
            }
        });
        this.textField.setColumns(20);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.5;
        constraints.gridx = 1;
        constraints.gridy = 1;
        this.add(textField, constraints);
        //this.button = new JButton("Change discount for category");
        //this.button.addActionListener(new ActionListener() {
        //    @Override
        //    public void actionPerformed(ActionEvent actionEvent) {
        //        changeDiscount();
        //    }
        //});
        //constraints.fill = GridBagConstraints.HORIZONTAL;
        //constraints.weightx = 0.5;
        //constraints.gridx = 2;
        //constraints.gridy = 1;
        //this.add(button, constraints);
        this.setVisible(true);
    }

    private void changeDiscount(){
        BigDecimal newDiscount = null;
        try {
            newDiscount = new BigDecimal(textField.getText());
        }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null,"Please insert valid value for new discount!");
            return;
        }
        service.setDiscountForCategory((Category)comboBox.getSelectedItem(), newDiscount);
        table.repopulateTable(service.getListByCategory((Category)comboBox.getSelectedItem()));
    }


}
