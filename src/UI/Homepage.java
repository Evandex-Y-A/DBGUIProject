/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UI;

import DAO.StoryDAO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import models.Story;
/**
 *
 * @author evandex
 */
public class Homepage extends javax.swing.JPanel {
    private JPanel storiesPanel;
    private JPanel charactersPanel;
    /**
     * Creates new form Homepage
     */
    public Homepage() {
        initComponents();
        UIManager.put("ScrollPane.background", new Color(60, 60, 60));
        UIManager.put("Viewport.background", new Color(45, 45, 45));
        UIManager.put("ScrollBar.background", new Color(60, 60, 60));
        UIManager.put("ScrollBar.thumb", new Color(80, 80, 80));
        setupScrollPanes();
    }

    private void customizeScrollPane(JScrollPane scrollPane) {
    // Main scroll pane background (border area)
    scrollPane.setBackground(new Color(60, 60, 60));
    scrollPane.setBorder(BorderFactory.createEmptyBorder());

    // Viewport (content area)
    JViewport viewport = scrollPane.getViewport();
    viewport.setBackground(new Color(45, 45, 45));

    // Scroll Bars
    customizeScrollBar(scrollPane.getVerticalScrollBar());
    customizeScrollBar(scrollPane.getHorizontalScrollBar());
}

    private void customizeScrollBar(JScrollBar scrollBar) {
        scrollBar.setBackground(new Color(60, 60, 60));
        scrollBar.setForeground(new Color(80, 80, 80));

        // Remove default arrow buttons and customize thumb
        scrollBar.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(80, 80, 80);
                this.trackColor = new Color(60, 60, 60);
                this.thumbDarkShadowColor = new Color(70, 70, 70);
                this.thumbHighlightColor = new Color(90, 90, 90);
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createInvisibleButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createInvisibleButton();
            }

            private JButton createInvisibleButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setBorder(BorderFactory.createEmptyBorder());
                return button;
            }

            @Override
            protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
                g.setColor(trackColor);
                g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
            }

            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
                g2.setColor(thumbColor);
                g2.fillRoundRect(
                    thumbBounds.x + 2, 
                    thumbBounds.y + 2,
                    thumbBounds.width - 4,
                    thumbBounds.height - 4,
                    8, 8
                );
            
                g2.dispose();
            }
        });
    }
    private void setupScrollPanes() {
        // Stories Section
        storiesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        storiesPanel.setBackground(new Color(45, 45, 45));
        jScrollPane1.setViewportView(storiesPanel);
        addStoryAddButton();  // Add button first

        // Characters Section
        charactersPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        charactersPanel.setBackground(new Color(45, 45, 45));
        jScrollPane2.setViewportView(charactersPanel);
        addCharacterAddButton();  // Add button first
    }

    private void addStoryAddButton() {
        JButton addBtn = createAddButton("+", () -> {
            String name = JOptionPane.showInputDialog("New Story Name:");
            if (name != null && !name.trim().isEmpty()) {
                insertStoryCard(name);
            }
        });
        storiesPanel.add(addBtn);  // Add button at position 0
    }

    private void addCharacterAddButton() {
        JButton addBtn = createAddButton("+", () -> {
            String name = JOptionPane.showInputDialog("New Character Name:");
            if (name != null && !name.trim().isEmpty()) {
                insertCharacterCard(name);
            }
        });
        charactersPanel.add(addBtn);  // Add button at position 0
    }

    private void insertStoryCard(String name) {
        JPanel card = createCard(name, new Color(180, 200, 220));
        // Add new card immediately after Add button (position 1)
        storiesPanel.add(card, 1);
        storiesPanel.revalidate();
        storiesPanel.repaint();
    }

    private void insertCharacterCard(String name) {
        JPanel card = createCard(name, new Color(220, 200, 180));
        // Add new card immediately after Add button (position 1)
        charactersPanel.add(card, 1);
        charactersPanel.revalidate();
        charactersPanel.repaint();
    }


    private void addStoryCard(String name) {
        JPanel card = createCard(name, new Color(180, 200, 220));
        storiesPanel.add(card);  // Add after existing components
    }

    private void addCharacterCard(String name) {
        JPanel card = createCard(name, new Color(220, 200, 180));
        charactersPanel.add(card);  // Add after existing components
    }

    private JButton createAddButton(String text, Runnable action) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(120, 160));
        btn.setBackground(new Color(80, 80, 80));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 24));
        btn.addActionListener(e -> action.run());
        return btn;
    }

    private JPanel createCard(String text, Color color, int entityId, boolean isStory) {
    JPanel card = new JPanel(new BorderLayout());
    card.setPreferredSize(new Dimension(120, 160));
    card.setBackground(color);
    card.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(Color.DARK_GRAY, 1),
        BorderFactory.createEmptyBorder(5, 5, 5, 5)
    ));

    JLabel label = new JLabel("<html><center>" + text + "</center></html>");
    label.setHorizontalAlignment(SwingConstants.CENTER);
    card.add(label, BorderLayout.CENTER);

    // Add mouse listener to handle clicks
    card.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 1) { // Single click
                showCardDialog(card, isStory, entityId);
            }
        }
    });
    
    return card;
}
    private void showCardDialog(Component parent, boolean isStory, int entityId) {
        JDialog dialog = new JDialog();
        dialog.setTitle(isStory ? "Edit Story" : "Edit Character");
        dialog.setLayout(new BorderLayout());
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(parent);

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        if (isStory) {
            // Fetch story details from database
            Story story = StoryDAO.getStoryById(entityId);
        
            JTextField titleField = new JTextField(story.getTitle());
            JTextField genreField = new JTextField(story.getGenre());
            JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Draft", "In Progress", "Completed"});
            statusCombo.setSelectedItem(story.getStatus());
            JTextArea synopsisArea = new JTextArea(story.getSynopsis());
            JScrollPane synopsisScroll = new JScrollPane(synopsisArea);
            JLabel createdLabel = new JLabel(story.getDate().toString());

            formPanel.add(new JLabel("Title:"));
            formPanel.add(titleField);
            formPanel.add(new JLabel("Genre:"));
            formPanel.add(genreField);
            formPanel.add(new JLabel("Status:"));
            formPanel.add(statusCombo);
            formPanel.add(new JLabel("Synopsis:"));
            formPanel.add(synopsisScroll);
            formPanel.add(new JLabel("Created:"));
            formPanel.add(createdLabel);
        } else {
            // Fetch character details from database
            Character character = CharacterDAO.getCharacterById(entityId);
        
            JTextField nameField = new JTextField(character.getName());
            JTextArea descArea = new JTextArea(character.getDescription());
            JScrollPane descScroll = new JScrollPane(descArea);
            JTextArea backstoryArea = new JTextArea(character.getBackstory());
            JScrollPane backstoryScroll = new JScrollPane(backstoryArea);

            formPanel.add(new JLabel("Name:"));
            formPanel.add(nameField);
            formPanel.add(new JLabel("Description:"));
            formPanel.add(descScroll);
            formPanel.add(new JLabel("Backstory:"));
            formPanel.add(backstoryScroll);
        }

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            try {
                if (isStory) {
                    Story updatedStory = new Story();
                    updatedStory.setId(entityId);
                    updatedStory.setTitle(titleField.getText());
                    updatedStory.setGenre(genreField.getText());
                    updatedStory.setStatus((String) statusCombo.getSelectedItem());
                    updatedStory.setSynopsis(synopsisArea.getText());
                    StoryDAO.updateStory(updatedStory);
                    } else {
                        Character updatedCharacter = new Character();
                        updatedCharacter.setId(entityId);
                        updatedCharacter.setName(nameField.getText());
                        updatedCharacter.setDescription(descArea.getText());
                        updatedCharacter.setBackstory(backstoryArea.getText());
                        CharacterDAO.updateCharacter(updatedCharacter);
                    }
                    JOptionPane.showMessageDialog(dialog, "Changes saved successfully!");
                    dialog.dispose();
                    refreshCardDisplay(entityId, isStory); // Refresh UI
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error saving changes: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(saveButton);
        buttonPanel.add(createCancelButton(dialog));

        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
    
    private JButton createCancelButton(JDialog dialog) {
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dialog.dispose());
        return cancelButton;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        assignButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));

        jPanel1.setBackground(new java.awt.Color(100, 100, 100));
        jPanel1.setMaximumSize(new java.awt.Dimension(32767, 25));
        jPanel1.setMinimumSize(new java.awt.Dimension(100, 25));
        jPanel1.setPreferredSize(new java.awt.Dimension(400, 25));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setText("Stories");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel1.setMaximumSize(new java.awt.Dimension(100, 20));
        jLabel1.setMinimumSize(new java.awt.Dimension(100, 20));
        jLabel1.setPreferredSize(new java.awt.Dimension(50, 20));
        jPanel1.add(jLabel1);

        assignButton.setText("Assign Character to Story");
        jPanel1.add(assignButton);

        add(jPanel1);

        jScrollPane1.setBackground(new java.awt.Color(51, 51, 51));
        jScrollPane1.setForeground(new java.awt.Color(51, 51, 51));
        add(jScrollPane1);

        jPanel2.setBackground(new java.awt.Color(100, 100, 100));
        jPanel2.setMaximumSize(new java.awt.Dimension(32767, 25));
        jPanel2.setMinimumSize(new java.awt.Dimension(100, 25));
        jPanel2.setPreferredSize(new java.awt.Dimension(400, 25));
        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel2.setText("Characters");
        jLabel2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel2.setMaximumSize(new java.awt.Dimension(100, 20));
        jLabel2.setMinimumSize(new java.awt.Dimension(100, 20));
        jLabel2.setName(""); // NOI18N
        jLabel2.setPreferredSize(new java.awt.Dimension(70, 20));
        jPanel2.add(jLabel2);

        add(jPanel2);

        jScrollPane2.setBackground(new java.awt.Color(51, 51, 51));
        jScrollPane2.setForeground(new java.awt.Color(51, 51, 51));
        add(jScrollPane2);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton assignButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
