/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UI;

import DAO.UserDAO;
import Utils.DatabaseUtils;
import Utils.TableModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 * Panel for displaying and managing user traits in a searchable table.
 * <p>
 * Provides functionality to load, refresh, filter, add, rename, and
 * delete traits associated with the current user.
 * </p>
 * 
 * @see UserDAO
 * @see TableModel
 * @see TableRowSorter
 * 
 * @author evandex
 */
public class TraitUI extends javax.swing.JPanel {

    /**
     * Table model holding the trait data.
     */
    private DefaultTableModel model;
    
    /**
     * Sorter used to filter and sort the table rows.
     */
    private TableRowSorter<DefaultTableModel> sorter;
    
    /**
     * Constructs the TraitUI panel and initializes its components
     * and search listener.
     */
    public TraitUI() {
        initComponents();
        initSearchListener();
    }
    
    /**
     * Loads or reloads the trait data into the table.
     * <p>
     * Intended to be called when the panel is first shown.
     * </p>
     */
    public void initData() {
        loadData();
    }
    
    /**
     * Refreshes the table by reloading data from the database
     * and reapplying the current search filter.
     */
    public void refreshTable() {
        loadData();
        filterTable();
    }
    
    /**
     * Loads trait data from the database into the table model
     * and attaches a row sorter for filtering.
     */
    private void loadData() {
        String[] columns = {"Trait ID", "Trait Name"};
        Object[][] data = fetchData();
        model = new TableModel(data, columns);
        Table.setModel(model);
        sorter = new TableRowSorter<>(model);
        Table.setRowSorter(sorter);
    }
    
    /**
     * Retrieves the trait data for the current user from the database.
     *
     * @return a 2D Object array where each sub-array is a row of {id, name},
     *         or an empty array if no user is logged in or on error
     */
    private Object[][] fetchData() {
        if (UserDAO.currentUser == null)
            return new Object[0][]; // Return empty data
        
        List<Object> rows = new ArrayList<>();
        try {
            Connection connection = DatabaseUtils.ConnecttoDB();
            PreparedStatement fetch = connection.prepareStatement("SELECT * FROM traits where user_id = ?");
            fetch.setInt(1, UserDAO.currentUser.getID());
            ResultSet data = fetch.executeQuery();
            while (data.next()) {
                Object[] row = {
                    data.getInt("trait_id"),
                    data.getString("name")
                };
                rows.add(row);
            }
            return rows.toArray(new Object[0][]);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
            return new Object[0][];
        }
    }
    
    /**
     * Applies the current text in the search field as a regex filter
     * to the table row sorter.
     */
    private void filterTable() {
        String query = searchTraitField.getText().trim().toLowerCase();
        if (query.equals("search...") || query.isEmpty())
            sorter.setRowFilter(null);
        else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query, 1));
        }
    }
    
    /**
     * This method is called from within the constructor to initialize
     * the form. WARNING: Do NOT modify this code, it is generated
     * by the GUI Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        addTraitButton = new javax.swing.JButton();
        renameTraitButton = new javax.swing.JButton();
        deleteTraitButton = new javax.swing.JButton();
        searchTraitField = new javax.swing.JTextField();
        traitsTable = new javax.swing.JScrollPane();
        Table = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        jLabel1.setText("Traits");
        add(jLabel1, java.awt.BorderLayout.PAGE_START);

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(100, 100, 100));
        jPanel2.setMinimumSize(new java.awt.Dimension(10, 20));

        addTraitButton.setText("Add");
        addTraitButton.setPreferredSize(new java.awt.Dimension(60, 23));
        addTraitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addTraitButtonActionPerformed(evt);
            }
        });
        jPanel2.add(addTraitButton);

        renameTraitButton.setText("Rename");
        renameTraitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                renameTraitButtonActionPerformed(evt);
            }
        });
        jPanel2.add(renameTraitButton);

        deleteTraitButton.setText("Delete");
        deleteTraitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteTraitButtonActionPerformed(evt);
            }
        });
        jPanel2.add(deleteTraitButton);

        searchTraitField.setText("Search...");
        searchTraitField.setPreferredSize(new java.awt.Dimension(150, 22));
        searchTraitField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                searchTraitFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                searchTraitFieldFocusLost(evt);
            }
        });
        jPanel2.add(searchTraitField);

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Trait ID", "Trait Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        traitsTable.setViewportView(Table);

        jPanel1.add(traitsTable, java.awt.BorderLayout.CENTER);

        add(jPanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Attaches a DocumentListener to the search field to update
     * the filter on each text change.
     */
    private void initSearchListener() {
        searchTraitField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { filterTable(); }
            @Override
            public void removeUpdate(DocumentEvent e) { filterTable(); }
            @Override
            public void changedUpdate(DocumentEvent e) { filterTable(); }
        });
    }
    
    /**
     * Handles the "Add" button click: opens the add-trait dialog
     * and refreshes the table upon completion.
     *
     * @param evt the action event (ignored)
     */
    private void addTraitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addTraitButtonActionPerformed
        TraitDialog dialog = new TraitDialog((Frame) SwingUtilities.getWindowAncestor(addTraitButton), true);
        dialog.setVisible(true);
        refreshTable();
    }//GEN-LAST:event_addTraitButtonActionPerformed

    /**
     * Handles the "Rename" button click: opens the rename-trait dialog
     * and refreshes the table upon completion.
     *
     * @param evt the action event (ignored)
     */
    private void renameTraitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_renameTraitButtonActionPerformed
        TraitEditDialog dialog = new TraitEditDialog((Frame) SwingUtilities.getWindowAncestor(renameTraitButton), true);
        dialog.setVisible(true);
        refreshTable();
    }//GEN-LAST:event_renameTraitButtonActionPerformed

    /**
     * Clears the placeholder text when the search field gains focus.
     *
     * @param evt the focus event (ignored)
     */
    private void searchTraitFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchTraitFieldFocusGained
        if (searchTraitField.getText().equals("Search...")) {
            searchTraitField.setText("");
        }
    }//GEN-LAST:event_searchTraitFieldFocusGained

    /**
     * Restores the placeholder text when the search field loses focus
     * and is empty.
     *
     * @param evt the focus event (ignored)
     */
    private void searchTraitFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchTraitFieldFocusLost
        if (searchTraitField.getText().isEmpty()) {
            searchTraitField.setText("Search...");
        }
    }//GEN-LAST:event_searchTraitFieldFocusLost

    /**
     * Handles the "Delete" button click: opens the delete-trait dialog
     * and refreshes the table upon completion.
     *
     * @param evt the action event (ignored)
     */
    private void deleteTraitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteTraitButtonActionPerformed
        TraitDeleteDialog dialog = new TraitDeleteDialog((Frame) SwingUtilities.getWindowAncestor(deleteTraitButton), true);
        dialog.setVisible(true);
        refreshTable();
    }//GEN-LAST:event_deleteTraitButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Table;
    private javax.swing.JButton addTraitButton;
    private javax.swing.JButton deleteTraitButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton renameTraitButton;
    private javax.swing.JTextField searchTraitField;
    private javax.swing.JScrollPane traitsTable;
    // End of variables declaration//GEN-END:variables
}
