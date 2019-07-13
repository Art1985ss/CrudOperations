package Main.UiConsole;

import Main.Database.Product;
import Main.Service.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;

public class ProductTable extends JPanel {
    private Service service;
    private JTable table;
    private List<Product> productList;
    private JFrame mainFrame;

    public ProductTable(List<Product> productList, Service service, JFrame mainFrame) {
        super();
        this.setSize(600,300);
        this.service = service;
        this.productList = productList;
        this.mainFrame = mainFrame;
        this.setLayout(new BorderLayout());
        this.repopulateTable();
        this.setVisible(true);
        this.repaint();
    }

    public void repopulateTable(List<Product> productList){
        this.removeAll();
        table = null;
        this.productList = productList;
        String[] columnNames = {"Category", "Name", "Id", "Price", "Discount", "Actual price", "Description"};
        String[][] data = new String[productList.size()][7];
        for (int i =0; i < productList.size(); i++){
            data[i][1] = productList.get(i).getName();
            data[i][2] = productList.get(i).getId().toString();
            data[i][3] = String.format("%.2f", productList.get(i).getPrice());
            data[i][0] = productList.get(i).getCategory().toString();
            data[i][4] = String.format("%.0f", productList.get(i).getDiscount().multiply(new BigDecimal(100)));
            data[i][5] = String.format("%.2f", productList.get(i).getActualPrice());
            data[i][6] = productList.get(i).getDescription();
        }
        table = new JTable(data, columnNames);
        //table.getSelectionModel().addListSelectionListener(this.createListener());
        table.getModel().addTableModelListener(this.createModeListener());
        table.setSize(this.getSize());
        table.repaint();
        this.add(table);
        this.repaint();
        mainFrame.repaint();
    }

    public void repopulateTable(){
        this.removeAll();
        table = null;
        String[] columnNames = {"Category", "Name", "Id", "Price", "Discount", "Actual price", "Description"};
        String[][] data = new String[productList.size()][7];
        for (int i =0; i < productList.size(); i++){
            data[i][1] = productList.get(i).getName();
            data[i][2] = productList.get(i).getId().toString();
            data[i][3] = String.format("%.2f", productList.get(i).getPrice());
            data[i][0] = productList.get(i).getCategory().toString();
            data[i][4] = String.format("%.0f", productList.get(i).getDiscount().multiply(new BigDecimal(100)));
            data[i][5] = String.format("%.2f", productList.get(i).getActualPrice());
            data[i][6] = productList.get(i).getDescription();
        }
        table = new JTable(data, columnNames);
        //table.getSelectionModel().addListSelectionListener(this.createListener());
        table.getModel().addTableModelListener(this.createModeListener());
        table.setSize(this.getSize());
        table.repaint();
        this.add(table);
        this.repaint();
        mainFrame.repaint();
    }

    private ListSelectionListener createListener(){
        ListSelectionListener listSelectionListener = new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                //if (!listSelectionEvent.getValueIsAdjusting()) return;
                int row = table.getSelectedRow();
                int column = table.getSelectedColumn();
                long id = Long.parseLong(table.getValueAt(row, 2).toString());
                System.out.println("Column = " + column + " Row = " + row + " id = " + id);
                switch (column){
                    case 3 :
                        service.changePrice(id, new BigDecimal(table.getValueAt(row, column).toString()));
                        System.out.println("Price change " + table.getValueAt(row, column));
                        break;
                    case 4 :
                        service.changeDiscount(id, new BigDecimal(table.getValueAt(row, column).toString()));
                        System.out.println("Discount change " + table.getValueAt(row, column));
                        break;
                    case 6 :
                        service.changeDescription(id, table.getValueAt(row, column).toString());
                        System.out.println("Description change " + table.getValueAt(row, column));
                        break;
                    default:
                        System.out.println("Maybe error in the code");
                    }
                }
            };
        return listSelectionListener;
    }

    private TableModelListener createModeListener(){
        TableModelListener listener = new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent tableModelEvent) {
                int row = tableModelEvent.getFirstRow();
                int column = tableModelEvent.getColumn();
                TableModel tableModel = (TableModel) tableModelEvent.getSource();
                Long id = Long.parseLong(tableModel.getValueAt(row,2).toString());
                System.out.println("Column = " + column + " Row = " + row + " id = " + id);
                switch (column){
                    case 3 :
                        service.changePrice(id, new BigDecimal(table.getValueAt(row, column).toString().replace(",",".")));
                        showMessage(String.format("New %s for product id %d : %s", "price", id, table.getValueAt(row, column).toString()));
                        System.out.println("Price change " + table.getValueAt(row, column));
                        break;
                    case 4 :
                        service.changeDiscount(id, new BigDecimal(table.getValueAt(row, column).toString().replace(",",".")));
                        showMessage(String.format("New %s for product id %d : %s", "discount", id, table.getValueAt(row, column).toString()));
                        System.out.println("Discount change " + table.getValueAt(row, column));
                        break;
                    case 6 :
                        service.changeDescription(id, table.getValueAt(row, column).toString());
                        showMessage(String.format("New %s for product id %d : %s", "description", id, table.getValueAt(row, column).toString()));
                        System.out.println("Description change " + table.getValueAt(row, column));
                        break;
                    default:
                        showMessage("This value can't be changed!");
                        System.out.println("Maybe error in the code");
                }
            }
            };
        return listener;
    }

    private void showMessage(String message){
        JFrame frame = new JFrame("Table message");
        frame.setLayout(new BorderLayout());
        frame.add(new Label(message),BorderLayout.PAGE_START);
        JButton btn = new JButton("Ok");
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                frame.dispose();
            }
        });
        frame.add(btn, BorderLayout.PAGE_END);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
