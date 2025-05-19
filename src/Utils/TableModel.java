/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import javax.swing.table.DefaultTableModel;

/**
 * A simple table model that extends {@link DefaultTableModel} to initialize
 * with a two-dimensional array of row data and an array of column names.
 * <p>
 * This class can be used directly in Swing tables (JTable) to display
 * tabular data without needing to subclass DefaultTableModel each time.
 * </p>
 * 
 * @author evandex
 */
public class TableModel extends DefaultTableModel {
    /**
     * Constructs a {@code TableModel} with the specified row data and
     * column names.
     *
     * @param data
     *        a two-dimensional {@link Object} array representing the table’s
     *        row data; each sub-array is a row in the model
     * @param columnNames
     *        a one-dimensional {@link Object} array containing the names
     *        of each column; the length of this array defines the number
     *        of columns in the model
     */
    public TableModel(Object[][] data, Object[] columnNames) {
        super(data, columnNames);
    }
}
