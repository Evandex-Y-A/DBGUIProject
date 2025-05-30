/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UI;

import java.awt.CardLayout;
import java.awt.Dimension;
import javax.swing.JOptionPane;
import DAO.UserDAO;

/**
 * Main container panel for the authenticated user interface.
 * <p>
 * Hosts a side navigation panel and a central {@link CardLayout} panel
 * to switch between the Home, Traits, and Settings views.
 * </p>
 * <p>
 * When first shown, displays the Home view; automatically refreshes
 * trait data whenever the panel becomes visible.
 * </p>
 * 
 * @see Login
 * @see Homepage
 * @see Settings
 * @see TraitUI
 * 
 * @author evandex
 */
public class HomeLayout extends javax.swing.JPanel {
    
    /**
     * Reference to the login panel, used to reset credentials and navigate back.
     */
    private Login loginPanel;
    
    /**
     * The main home/dashboard panel shown after login.
     */
    private Homepage homePanel;
    
    /**
     * The traits panel displaying user-specific characteristics.
     */
    private TraitUI traitPanel;
    
    /**
     * Constructs the HomeLayout, initializing all sub-panels and
     * configuring the card-based navigation.
     *
     * @param login the {@link Login} panel instance used for logout navigation
     */
    public HomeLayout(Login login) {
        this.loginPanel = login; 
        homePanel = new Homepage();
        traitPanel = new TraitUI();
        initComponents();
        this.setPreferredSize(new Dimension(800, 600));
        cardPanel.add(homePanel, "main");
        cardPanel.add(traitPanel, "traits");
        CardLayout innerCard = (CardLayout) cardPanel.getLayout();
        innerCard.show(cardPanel, "main");
    }
    
    /**
     * Called whenever this panel is displayed or needs to refresh data.
     * <p>
     * Ensures that trait data is initialized before display.
     * </p>
     */
    public void onShow() {
        traitPanel.initData();
    }
    
    /**
     * Overrides {@link JPanel#setVisible(boolean)} to trigger
     * {@link #onShow()} whenever this panel becomes visible.
     *
     * @param visible {@code true} to make the panel visible, {@code false} to hide it
     */
    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) {
            onShow(); // Load data when the panel becomes visible
        }
    }
    
    /**
     * Initializes Swing components and layout.
     * <p>
     * WARNING: This code is generated by the Form Editor and will be
     * regenerated if the form is modified in the GUI builder.
     * Do NOT manually alter this block.
     * </p>
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sidePanel = new javax.swing.JPanel();
        mainButton = new javax.swing.JButton();
        traitsButton = new javax.swing.JButton();
        logoutButton = new javax.swing.JButton();
        deleteAccountButton = new javax.swing.JButton();
        cardPanel = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        sidePanel.setBackground(new java.awt.Color(51, 51, 51));
        sidePanel.setMaximumSize(new java.awt.Dimension(111, 92));
        sidePanel.setMinimumSize(new java.awt.Dimension(111, 92));
        sidePanel.setName(""); // NOI18N
        sidePanel.setPreferredSize(new java.awt.Dimension(111, 300));
        sidePanel.setLayout(new javax.swing.BoxLayout(sidePanel, javax.swing.BoxLayout.Y_AXIS));

        mainButton.setText("Main");
        mainButton.setMaximumSize(new java.awt.Dimension(111, 23));
        mainButton.setMinimumSize(new java.awt.Dimension(111, 23));
        mainButton.setPreferredSize(new java.awt.Dimension(111, 23));
        mainButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mainButtonActionPerformed(evt);
            }
        });
        sidePanel.add(mainButton);

        traitsButton.setText("Traits");
        traitsButton.setMaximumSize(new java.awt.Dimension(111, 23));
        traitsButton.setMinimumSize(new java.awt.Dimension(111, 23));
        traitsButton.setPreferredSize(new java.awt.Dimension(111, 23));
        traitsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                traitsButtonActionPerformed(evt);
            }
        });
        sidePanel.add(traitsButton);

        logoutButton.setText("Log Out");
        logoutButton.setMaximumSize(new java.awt.Dimension(111, 23));
        logoutButton.setMinimumSize(new java.awt.Dimension(111, 23));
        logoutButton.setPreferredSize(new java.awt.Dimension(111, 23));
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButtonActionPerformed(evt);
            }
        });
        sidePanel.add(logoutButton);

        deleteAccountButton.setText("Delete Account");
        deleteAccountButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteAccountButtonActionPerformed(evt);
            }
        });
        sidePanel.add(deleteAccountButton);

        add(sidePanel, java.awt.BorderLayout.WEST);

        cardPanel.setLayout(new java.awt.CardLayout());
        add(cardPanel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Handles the "Log Out" button click.
     * <p>
     * Resets the login form, switches the outer CardLayout back to the
     * login view, and resets the inner CardLayout to the main/home panel.
     * </p>
     *
     * @param evt the action event (ignored)
     */
    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButtonActionPerformed
        loginPanel.resetFields(); // Resets the text field in the login panel for security
        CardLayout outerCard = (CardLayout)getParent().getLayout();
        outerCard.show(getParent(), "login");
        CardLayout innerCard = (CardLayout) cardPanel.getLayout();
        innerCard.show(cardPanel, "main");
    }//GEN-LAST:event_logoutButtonActionPerformed

    /**
     * Handles the "Main" button click.
     * <p>
     * Switches the central card panel to display the home/main view.
     * </p>
     *
     * @param evt the action event (ignored)
     */
    private void mainButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mainButtonActionPerformed
        CardLayout innerCard = (CardLayout) cardPanel.getLayout();
        innerCard.show(cardPanel, "main");
    }//GEN-LAST:event_mainButtonActionPerformed

    /**
     * Handles the "Traits" button click.
     * <p>
     * Switches the central card panel to display the traits view.
     * </p>
     *
     * @param evt the action event (ignored)
     */
    private void traitsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_traitsButtonActionPerformed
        CardLayout innerCard = (CardLayout) cardPanel.getLayout();
        innerCard.show(cardPanel, "traits");
    }//GEN-LAST:event_traitsButtonActionPerformed

    /**
     * Handles the "Delete Account" button click.
     * <p>
     * Prompts the user for confirmation; if confirmed, attempts to delete
     * the current user via {@link UserDAO} and provides feedback dialogs.
     * After deletion or cancellation, returns to the login view.
     * </p>
     *
     * @param evt the action event (ignored)
     */
    private void deleteAccountButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteAccountButtonActionPerformed
        int response = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete this account?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
                );
        if (response == JOptionPane.YES_OPTION) {
            UserDAO deleteAccount = new UserDAO();
            try {
                if (deleteAccount.deleteUser(UserDAO.currentUser.getID())) {
                    JOptionPane.showMessageDialog(this, "Successfully deleted account.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete account.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            loginPanel.resetFields();
            CardLayout outerCard = (CardLayout)getParent().getLayout();
            outerCard.show(getParent(), "login");
            CardLayout innerCard = (CardLayout) cardPanel.getLayout();
            innerCard.show(cardPanel, "main");
        }
    }//GEN-LAST:event_deleteAccountButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel cardPanel;
    private javax.swing.JButton deleteAccountButton;
    private javax.swing.JButton logoutButton;
    private javax.swing.JButton mainButton;
    private javax.swing.JPanel sidePanel;
    private javax.swing.JButton traitsButton;
    // End of variables declaration//GEN-END:variables
}
