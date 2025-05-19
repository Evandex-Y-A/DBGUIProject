/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UI;

import DAO.StoryDAO;
import DAO.CharacterDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.function.Function;
import java.util.List;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import models.Story;
import models.Character;

/**
 * The Homepage class represents the main GUI panel for managing stories and characters.
 * It provides functionality to:
 * <ul>
 * <li>Display scrollable cards for stories and characters</li>
 * <li>Add new stories/characters with input dialogs</li>
 * <li>Search/filter existing entries with type-ahead search</li>
 * <li>Edit existing entries through detail dialogs</li>
 * <li>Delete entries with confirmation</li>
 * <li>Persist changes to a database via DAO classes</li>
 * </ul>
 * 
 * The UI features a dark theme with color-coded cards and maintains responsive layouts.
 * 
 * @author evandex
 */
public class Homepage extends javax.swing.JPanel {
    private JPanel storiesPanel;
    private JPanel charactersPanel;
    private Timer storySearchTimer;
    private Timer characterSearchTimer;
    /**
     * Background color used for story cards
     */
    private final Color STORY_COLOR = new Color(180, 200, 220);
    /**
     * Background color used for character cards
     */
    private final Color CHARACTER_COLOR = new Color(220, 200, 180);
    
    private JTextField titleField;
    private JTextField genreField;
    private JComboBox<String> statusCombo;
    private JTextArea synopsisArea;
    private JScrollPane synopsisScroll;
    private JLabel createdLabel;
            
    private JTextField nameField;
    private JTextArea descArea;
    private JScrollPane descScroll;
    private JTextArea backstoryArea;
    private JScrollPane backstoryScroll;
    
    /**
     * Constructs a new Homepage panel with initialized components.
     * Sets up:
     * - Scrollable card panels for stories and characters
     * - Search functionality with 300ms delay
     * - Initial data loading from database
     * - Custom UI styling for dark theme
     */
    public Homepage() {
        initComponents();
        UIManager.put("ScrollPane.background", new Color(60, 60, 60));
        UIManager.put("Viewport.background", new Color(45, 45, 45));
        UIManager.put("ScrollBar.background", new Color(60, 60, 60));
        UIManager.put("ScrollBar.thumb", new Color(80, 80, 80));
        storiesScrollPane.setViewportView(storiesPanel);
        charactersScrollPane.setViewportView(charactersPanel);
        storiesScrollPane.setPreferredSize(new Dimension(400, 200));
        storiesScrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
        charactersScrollPane.setPreferredSize(new Dimension(400, 200));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
        setupScrollPanes();
        setupSearchBars();
        loadInitialData();
    }
    
    /**
     * Configures the search functionality for both story and character panels.
     * Uses a 300ms delay timer to prevent excessive database queries during typing.
     */
    private void setupSearchBars() {
        // Initialize timers with 300ms delay
        storySearchTimer = new Timer(300, e -> searchStories());
        storySearchTimer.setRepeats(false);
        
        characterSearchTimer = new Timer(300, e -> searchCharacters());
        characterSearchTimer.setRepeats(false);

        // Add document listeners
        setupSearchBar(storySearchBar, storySearchTimer);
        setupSearchBar(characterSearchBar, characterSearchTimer);
    }
    
    private void setupSearchBar(JTextField searchBar, Timer timer) {
        searchBar.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { restartTimer(timer); }
            @Override
            public void removeUpdate(DocumentEvent e) { restartTimer(timer); }
            @Override
            public void changedUpdate(DocumentEvent e) { restartTimer(timer); }
            
            private void restartTimer(Timer t) {
                t.stop();
                t.start();
            }
        });
    }
    
    /**
     * Filters visible story cards based on search text.
     * Matching is case-insensitive and ignores HTML formatting in card labels.
     */
    private void searchStories() {
        String searchText = storySearchBar.getText().toLowerCase();
        Component[] components = storiesPanel.getComponents();
        
        for (int i = 1; i < components.length; i++) { // Skip Add button
            Component comp = components[i];
            if (comp instanceof JPanel) {
                JPanel card = (JPanel) comp;
                JLabel label = (JLabel) card.getComponent(0);
                String cardText = getCleanText(label);
                card.setVisible(cardText.contains(searchText));
            }
        }
        storiesPanel.revalidate();
        storiesPanel.repaint();
    }
    
    /**
     * Filters visible character cards based on search text.
     * Matching is case-insensitive and ignores HTML formatting in card labels.
     */
    private void searchCharacters() {
        String searchText = characterSearchBar.getText().toLowerCase();
        Component[] components = charactersPanel.getComponents();
        
        for (int i = 1; i < components.length; i++) { // Skip Add button
            Component comp = components[i];
            if (comp instanceof JPanel) {
                JPanel card = (JPanel) comp;
                JLabel label = (JLabel) card.getComponent(0);
                String cardText = getCleanText(label);
                card.setVisible(cardText.contains(searchText));
            }
        }
        charactersPanel.revalidate();
        charactersPanel.repaint();
    }
    
    private String getCleanText(JLabel label) {
        return label.getText()
                    .replaceAll("<[^>]*>", "") // Remove HTML tags
                    .toLowerCase();
    }
    
    private void setupScrollPanes() {
        // Stories Section
        storiesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        storiesPanel.setBackground(new Color(45, 45, 45));
        storiesScrollPane.setViewportView(storiesPanel);
        addStoryAddButton();  // Add button first

        // Characters Section
        charactersPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        charactersPanel.setBackground(new Color(45, 45, 45));
        charactersScrollPane.setViewportView(charactersPanel);
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

    /**
     * Creates a new story card and adds it to the UI.
     * 
     * @param name The title of the story to create
     * @throws SQLException if there's an error persisting to the database
     */
    private void insertStoryCard(String name) {
        try {
            Story newStory = new Story();
            newStory.setTitle(name);
            newStory.setStatus("Draft");
            Story createdStory = StoryDAO.createStory(newStory);
        
            JPanel card = createCard(createdStory.getTitle(), 
                               new Color(180, 200, 220),
                               createdStory.getId(),
                               true);
            storiesPanel.add(card, 1);
            storiesPanel.revalidate();
            storiesPanel.repaint();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error creating story: " + e.getMessage());
        }
    }

    /**
     * Creates a new character card and adds it to the UI.
     * 
     * @param name The name of the character to create
     * @throws SQLException if there's an error persisting to the database
     */
    private void insertCharacterCard(String name) {
        Character character = new Character();
        character.setName(name);
        Character createdCharacter = new Character();
        try {
            Character tempCharacter = CharacterDAO.createCharacter(character);
            createdCharacter = tempCharacter;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "An error occurred.");
        }
        
        JPanel card = createCard(createdCharacter.getName(), new Color(220, 200, 180), createdCharacter.getCharacterId(), false);
        // Add new card immediately after Add button (position 1)
        charactersPanel.add(card, 1);
        charactersPanel.revalidate();
        charactersPanel.repaint();
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

    /**
     * Creates a reusable card component for display in the scroll panels.
     * 
     * @param text      The text to display in the card (HTML supported)
     * @param color     The background color for the card
     * @param entityId  The database ID associated with this card
     * @param isStory   True if this is a story card, false for character
     * @return JPanel containing the formatted card component
     */
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
    
    if (isStory) {
            String searchText = storySearchBar.getText().toLowerCase();
            card.setVisible(getCleanText(label).contains(searchText));
        } else {
            String searchText = characterSearchBar.getText().toLowerCase();
            card.setVisible(getCleanText(label).contains(searchText));
        }
    card.setVisible(true);
    card.putClientProperty("entityId", entityId);
    return card;
}
    /**
     * Shows the edit dialog for a specific story or character.
     * 
     * @param parent    The parent component for dialog positioning
     * @param isStory   True if editing a story, false for character
     * @param entityId  The database ID of the entity being edited
     * @throws SQLException if there's an error loading entity data
     */
    private void showCardDialog(Component parent, boolean isStory, int entityId) {
        try {
            JDialog dialog = new JDialog();
            dialog.setTitle(isStory ? "Edit Story" : "Edit Character");
            dialog.setLayout(new BorderLayout());
            dialog.setSize(400, 300);
            dialog.setLocationRelativeTo(parent);
            
            JButton deleteButton = new JButton("Delete");
            deleteButton.setBackground(new Color(255, 100, 100));
            deleteButton.setForeground(Color.WHITE);
            deleteButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(dialog,
                    "Are you sure you want to delete this " + (isStory ? "story" : "character") + "?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION);
            
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        if (isStory) {
                            StoryDAO.deleteStory(entityId);
                        } else {
                            CharacterDAO.deleteCharacter(entityId);
                        }
                        dialog.dispose();
                        refreshCardDisplay(entityId, isStory);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(dialog, "Delete failed: " + ex.getMessage(),
                                "Database Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
        
            JPanel formPanel = new JPanel(new GridLayout(0, 2, 5, 5));
            formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            
            
            if (isStory) {
                // Fetch story details from database
                Story story = StoryDAO.getStoryById(entityId);
        
                titleField = new JTextField(story.getTitle());
                genreField = new JTextField(story.getGenre());
                statusCombo = new JComboBox<>(new String[]{"Draft", "In Progress", "Completed"});
                statusCombo.setSelectedItem(story.getStatus());
                synopsisArea = new JTextArea(story.getSynopsis());
                synopsisScroll = new JScrollPane(synopsisArea);
                createdLabel = new JLabel(story.getDate().toString());

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
            
                Character character = new Character();
                try {
                    character = CharacterDAO.getCharacterById(entityId);
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "An error occurred.");
                }
            
                nameField = new JTextField(character.getName());
                descArea = new JTextArea(character.getDescription());
                descScroll = new JScrollPane(descArea);
                backstoryArea = new JTextArea(character.getBackstory());
                backstoryScroll = new JScrollPane(backstoryArea);

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
                    updatedCharacter.setCharacterId(entityId);
                    updatedCharacter.setName(nameField.getText());
                    updatedCharacter.setDescription(descArea.getText());
                    updatedCharacter.setBackstory(backstoryArea.getText());
                    CharacterDAO.updateCharacter(updatedCharacter);
                }
                JOptionPane.showMessageDialog(dialog, "Changes saved successfully!");
                dialog.dispose();
                refreshCardDisplay(entityId, isStory);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(dialog, "Error saving changes: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

            JPanel buttonPanel = new JPanel(new BorderLayout());
            JPanel actionButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            actionButtons.add(saveButton);
            actionButtons.add(createCancelButton(dialog));
        
            buttonPanel.add(deleteButton, BorderLayout.WEST);
            buttonPanel.add(actionButtons, BorderLayout.EAST);

            dialog.add(formPanel, BorderLayout.CENTER);
            dialog.add(buttonPanel, BorderLayout.SOUTH);
            dialog.setVisible(true);
        } catch (SQLException e) {
            
        }
        
    }
    
    private void loadInitialData() {
        try {
            // Load stories without creating new database entries
            for (Story story : StoryDAO.getAllStories()) {
                JPanel card = createCard(story.getTitle(), STORY_COLOR, story.getId(), true);
                storiesPanel.add(card, 1);
            }
        
            // Load characters without creating new database entries
            for (Character character : CharacterDAO.getAllCharacters()) {
                JPanel card = createCard(character.getName(), CHARACTER_COLOR, character.getCharacterId(), false);
                charactersPanel.add(card, 1);
            }
        
            storiesPanel.revalidate();
            storiesPanel.repaint();
            charactersPanel.revalidate();
            charactersPanel.repaint();
        
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + ex.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Refreshes the UI display after entity modifications.
     * Handles both updates and deletions of existing cards.
     * 
     * @param entityId  The database ID of the modified entity
     * @param isStory   True if handling a story, false for character
     * @throws SQLException if there's an error accessing the database
     */
    private void refreshCardDisplay(int entityId, boolean isStory) {
        try {
            if (isStory) {
                // Handle story card refresh
                Story updatedStory = StoryDAO.getStoryById(entityId);
                refreshOrRemoveCard(
                    storiesPanel, 
                    updatedStory, 
                    entityId, 
                    s -> createCard(s.getTitle(), new Color(180, 200, 220), s.getId(), true)
                );
            } else {
                // Handle character card refresh
                Character updatedCharacter = CharacterDAO.getCharacterById(entityId);
                refreshOrRemoveCard(
                    charactersPanel, 
                    updatedCharacter, 
                    entityId, 
                    c -> createCard(c.getName(), new Color(220, 200, 180), c.getCharacterId(), false)
                );
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error refreshing data: " + ex.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
        private <T> void refreshOrRemoveCard(JPanel container, T entity, int entityId, 
                                    Function<T, JPanel> cardCreator) {
        // 1. Try to find existing card
        for (Component comp : container.getComponents()) {
            if (comp instanceof JPanel) {
                JPanel card = (JPanel) comp;
                Integer storedId = (Integer) card.getClientProperty("entityId");
                if (storedId != null && storedId == entityId) {
                    if (entity == null) {
                        // 2a. Entity deleted - remove card
                        container.remove(card);
                    } else {
                        // 2b. Entity updated - replace card
                        int index = container.getComponentZOrder(card);
                        container.remove(index);
                        container.add(cardCreator.apply(entity), index);
                    }
                    container.revalidate();
                    container.repaint();
                    return;
                }
            }
        }
    
        // 3. If new entity (unlikely but possible)
        if (entity != null) {
            container.add(cardCreator.apply(entity), 1); // Add after Add button
            container.revalidate();
            container.repaint();
        }
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
        storySearchBar = new javax.swing.JTextField();
        storyDeleteButton = new javax.swing.JButton();
        storiesScrollPane = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        characterSearchBar = new javax.swing.JTextField();
        characterDeleteButton = new javax.swing.JButton();
        charactersScrollPane = new javax.swing.JScrollPane();

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));

        jPanel1.setBackground(new java.awt.Color(100, 100, 100));
        jPanel1.setMaximumSize(new java.awt.Dimension(32767, 25));
        jPanel1.setMinimumSize(new java.awt.Dimension(100, 25));
        jPanel1.setPreferredSize(new java.awt.Dimension(400, 25));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 2));

        jLabel1.setText("Stories");
        jLabel1.setToolTipText("");
        jLabel1.setMaximumSize(new java.awt.Dimension(100, 20));
        jLabel1.setMinimumSize(new java.awt.Dimension(100, 20));
        jLabel1.setPreferredSize(new java.awt.Dimension(65, 20));
        jPanel1.add(jLabel1);

        storySearchBar.setText("Search...");
        storySearchBar.setPreferredSize(new java.awt.Dimension(200, 22));
        jPanel1.add(storySearchBar);

        storyDeleteButton.setText("Delete");
        jPanel1.add(storyDeleteButton);

        add(jPanel1);

        storiesScrollPane.setBackground(new java.awt.Color(51, 51, 51));
        storiesScrollPane.setForeground(new java.awt.Color(51, 51, 51));
        storiesScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        storiesScrollPane.setMinimumSize(new java.awt.Dimension(400, 200));
        storiesScrollPane.setPreferredSize(new java.awt.Dimension(400, 200));
        add(storiesScrollPane);

        jPanel2.setBackground(new java.awt.Color(100, 100, 100));
        jPanel2.setMaximumSize(new java.awt.Dimension(32767, 25));
        jPanel2.setMinimumSize(new java.awt.Dimension(100, 25));
        jPanel2.setPreferredSize(new java.awt.Dimension(400, 25));
        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 2));

        jLabel2.setText("Characters");
        jLabel2.setMaximumSize(new java.awt.Dimension(100, 20));
        jLabel2.setMinimumSize(new java.awt.Dimension(100, 20));
        jLabel2.setName(""); // NOI18N
        jLabel2.setPreferredSize(new java.awt.Dimension(65, 20));
        jPanel2.add(jLabel2);

        characterSearchBar.setText("Search...");
        characterSearchBar.setPreferredSize(new java.awt.Dimension(200, 22));
        jPanel2.add(characterSearchBar);

        characterDeleteButton.setText("Delete");
        jPanel2.add(characterDeleteButton);

        add(jPanel2);

        charactersScrollPane.setBackground(new java.awt.Color(51, 51, 51));
        charactersScrollPane.setForeground(new java.awt.Color(51, 51, 51));
        charactersScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        charactersScrollPane.setMinimumSize(new java.awt.Dimension(400, 200));
        charactersScrollPane.setPreferredSize(new java.awt.Dimension(400, 200));
        add(charactersScrollPane);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton characterDeleteButton;
    private javax.swing.JTextField characterSearchBar;
    private javax.swing.JScrollPane charactersScrollPane;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane storiesScrollPane;
    private javax.swing.JButton storyDeleteButton;
    private javax.swing.JTextField storySearchBar;
    // End of variables declaration//GEN-END:variables
}
