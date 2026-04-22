package src;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CafeBillingUI extends JFrame {

    private List<MenuItem> menuItems;
    private List<JCheckBox> checkBoxes;
    private List<JTextField> quantityFields;
    private BillCalculator calculator;

    private JLabel subtotalValueLabel;
    private JLabel taxValueLabel;
    private JLabel serviceChargeValueLabel;
    private JLabel grandTotalValueLabel;

    // Theme Colors
    private final Color BG_COLOR = new Color(250, 246, 240); // Warm Cream
    private final Color PRIMARY_COLOR = new Color(93, 64, 55); // Espresso Brown
    private final Color ACCENT_COLOR = new Color(200, 160, 120); // Latte
    private final Color TEXT_COLOR = new Color(62, 39, 35); // Dark Brown

    public CafeBillingUI() {
        setTitle("The Rustic Beans Cafe - Ultra Premium Edition");
        
        // Full screen to fit larger products perfectly
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        
        menuItems = new ArrayList<>();
        checkBoxes = new ArrayList<>();
        quantityFields = new ArrayList<>();
        calculator = new BillCalculator();

        initializeMenu();
        initializeUI();
    }

    private void initializeMenu() {
        // Drinks
        menuItems.add(new MenuItem("Classic Coffee", 80, "images/coffee.png"));
        menuItems.add(new MenuItem("Herbal Tea", 50, "images/tea.png"));
        menuItems.add(new MenuItem("Iced Cold Coffee", 120, "images/cold_coffee.png"));
        menuItems.add(new MenuItem("Rich Cappuccino", 150, "images/cappuccino.png"));
        
        // Cakes
        menuItems.add(new MenuItem("Chocolate Fudge", 200, "images/chocolate_cake.png"));
        menuItems.add(new MenuItem("Black Forest", 220, "images/black_forest.png"));
        menuItems.add(new MenuItem("Red Velvet Slice", 250, "images/red_velvet.png"));
        menuItems.add(new MenuItem("NY Cheesecake", 270, "images/cheesecake.png"));

        // Fast Food
        menuItems.add(new MenuItem("Gourmet Burger", 150, "images/burger.png"));
        menuItems.add(new MenuItem("Woodfire Pizza", 350, "images/pizza.png"));
        menuItems.add(new MenuItem("Crispy Fries", 100, "images/fries.png"));
        menuItems.add(new MenuItem("Club Sandwich", 120, "images/sandwich.png"));

        // Bakery & Coolers
        menuItems.add(new MenuItem("Blueberry Muffin", 90, "images/muffin.png"));
        menuItems.add(new MenuItem("Choco Croissant", 110, "images/croissant.png"));
        menuItems.add(new MenuItem("Lemon Iced Tea", 95, "images/iced_tea.png"));
        menuItems.add(new MenuItem("Belgian Waffles", 180, "images/waffles.png"));
        
        // Brand New Additions
        menuItems.add(new MenuItem("Fluffy Pancakes", 160, "images/pancakes.png"));
        menuItems.add(new MenuItem("Glazed Donut", 75, "images/donut.png"));
        menuItems.add(new MenuItem("Strawberry Shake", 140, "images/milkshake.png"));
        menuItems.add(new MenuItem("Hot Chocolate", 130, "images/hot_chocolate.png"));
        
        // Latest Premium Additions (making 25 total)
        menuItems.add(new MenuItem("Matcha Latte", 145, "images/matcha.png"));
        menuItems.add(new MenuItem("Vanilla Frappe", 165, "images/frappe.png"));
        menuItems.add(new MenuItem("Cinnamon Roll", 110, "images/cinnamon_roll.png"));
        menuItems.add(new MenuItem("Fruit Tart", 125, "images/tart.png"));
        menuItems.add(new MenuItem("Vegan Wrap", 175, "images/wrap.png"));
    }

    private void initializeUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(new EmptyBorder(15, 20, 15, 20));
        mainPanel.setBackground(BG_COLOR);

        // --- HEADER PANEL ---
        JPanel headerPanel = new JPanel(new BorderLayout(20, 10));
        headerPanel.setBackground(BG_COLOR);
        headerPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, ACCENT_COLOR));

        // Logo
        JLabel logoLabel = new JLabel();
        try {
            File logoFile = new File("images/logo.png");
            if (logoFile.exists()) {
                ImageIcon logoIcon = new ImageIcon("images/logo.png");
                Image img = logoIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
                logoLabel.setIcon(new ImageIcon(img));
                logoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));
            }
        } catch (Exception e) {}
        
        headerPanel.add(logoLabel, BorderLayout.WEST);

        // Titles
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(BG_COLOR);
        titlePanel.setBorder(new EmptyBorder(25, 0, 15, 0));

        JLabel titleLabel = new JLabel("The Rustic Beans Cafe");
        titleLabel.setFont(new Font("Georgia", Font.BOLD, 48)); // Bigger font
        titleLabel.setForeground(PRIMARY_COLOR);
        
        JLabel subtitleLabel = new JLabel("Premium Coffee, Fresh Bakes & Good Times");
        subtitleLabel.setFont(new Font("Georgia", Font.ITALIC, 24));
        subtitleLabel.setForeground(ACCENT_COLOR);

        titlePanel.add(titleLabel);
        titlePanel.add(Box.createRigidArea(new Dimension(0, 8)));
        titlePanel.add(subtitleLabel);

        headerPanel.add(titlePanel, BorderLayout.CENTER);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // --- MENU PANEL ---
        // 5 Columns for 20 items -> 5x4 Grid to fill screen
        JPanel menuPanel = new JPanel(new GridLayout(0, 5, 25, 30));
        menuPanel.setBackground(BG_COLOR);
        menuPanel.setBorder(new EmptyBorder(20, 10, 20, 10));

        for (int i = 0; i < menuItems.size(); i++) {
            MenuItem item = menuItems.get(i);
            
            // Item Card
            JPanel card = new JPanel(new BorderLayout(10, 10)) {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(Color.WHITE);
                    // Add subtle shadow effect
                    g2.fillRoundRect(2, 2, getWidth()-4, getHeight()-4, 25, 25);
                }
            };
            card.setOpaque(false);
            card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 210, 200), 2, true),
                new EmptyBorder(20, 20, 20, 20)
            ));

            // Image - Massive size!
            JLabel imageLabel = new JLabel();
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            try {
                File imgFile = new File(item.getImagePath());
                if(imgFile.exists()){
                    ImageIcon icon = new ImageIcon(item.getImagePath());
                    Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH); // HUGE IMAGES
                    imageLabel.setIcon(new ImageIcon(img));
                } else {
                    imageLabel.setText("No Image");
                    imageLabel.setPreferredSize(new Dimension(200, 200));
                    imageLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                }
            } catch (Exception e) {
                imageLabel.setText("No Image");
            }
            card.add(imageLabel, BorderLayout.NORTH);

            // Details Panel (Name & Price)
            JPanel detailsPanel = new JPanel(new GridLayout(2, 1, 0, 5));
            detailsPanel.setOpaque(false);
            JLabel nameLabel = new JLabel(item.getName(), SwingConstants.CENTER);
            nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 20)); // Bigger text
            nameLabel.setForeground(TEXT_COLOR);
            
            JLabel priceLabel = new JLabel("\u20B9 " + item.getPrice(), SwingConstants.CENTER);
            priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
            priceLabel.setForeground(new Color(46, 125, 50)); 
            
            detailsPanel.add(nameLabel);
            detailsPanel.add(priceLabel);
            card.add(detailsPanel, BorderLayout.CENTER);

            // Controls Panel
            JPanel controlsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
            controlsPanel.setOpaque(false);

            JCheckBox checkBox = new JCheckBox();
            checkBox.setOpaque(false);
            checkBox.setCursor(new Cursor(Cursor.HAND_CURSOR));
            checkBox.setIcon(new CustomCheckBoxIcon(false));
            checkBox.setSelectedIcon(new CustomCheckBoxIcon(true));
            checkBoxes.add(checkBox);
            
            JButton minusBtn = createQtyButton("-");
            JButton plusBtn = createQtyButton("+");

            JTextField quantityField = new JTextField("0", 2);
            quantityField.setHorizontalAlignment(JTextField.CENTER);
            quantityField.setFont(new Font("Segoe UI", Font.BOLD, 22)); // Huge font for qty
            quantityField.setForeground(PRIMARY_COLOR);
            quantityField.setEditable(false);
            quantityField.setBackground(Color.WHITE);
            quantityField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
            quantityFields.add(quantityField);

            int index = i;
            minusBtn.addActionListener(e -> {
                int qty = Integer.parseInt(quantityField.getText());
                if (qty > 0) {
                    qty--;
                    quantityField.setText(String.valueOf(qty));
                    if (qty == 0) {
                        checkBox.setSelected(false);
                    }
                }
            });

            plusBtn.addActionListener(e -> {
                int qty = Integer.parseInt(quantityField.getText());
                qty++;
                quantityField.setText(String.valueOf(qty));
                if (qty > 0 && !checkBox.isSelected()) {
                    checkBox.setSelected(true);
                }
            });

            checkBox.addActionListener(e -> {
                if (checkBox.isSelected()) {
                    if (quantityField.getText().equals("0")) {
                        quantityField.setText("1");
                    }
                } else {
                    quantityField.setText("0");
                }
            });

            controlsPanel.add(checkBox);
            controlsPanel.add(minusBtn);
            controlsPanel.add(quantityField);
            controlsPanel.add(plusBtn);
            
            card.add(controlsPanel, BorderLayout.SOUTH);
            menuPanel.add(card);
        }

        JScrollPane scrollPane = new JScrollPane(menuPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(BG_COLOR);
        scrollPane.getVerticalScrollBar().setUnitIncrement(30);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // --- BOTTOM PANEL ---
        JPanel bottomPanel = new JPanel(new BorderLayout(15, 20));
        bottomPanel.setBackground(BG_COLOR);
        bottomPanel.setBorder(BorderFactory.createMatteBorder(3, 0, 0, 0, ACCENT_COLOR));

        // Billing Summary
        JPanel billingPanel = new JPanel(new GridLayout(2, 4, 20, 15));
        billingPanel.setOpaque(false);
        billingPanel.setBorder(new EmptyBorder(25, 30, 10, 30));

        Font labelFont = new Font("Segoe UI", Font.BOLD, 22);
        Font valueFont = new Font("Segoe UI", Font.PLAIN, 22);

        billingPanel.add(createStyledLabel("Subtotal:", labelFont));
        subtotalValueLabel = createStyledLabel("\u20B9 0.00", valueFont);
        billingPanel.add(subtotalValueLabel);

        billingPanel.add(createStyledLabel("Tax (5%):", labelFont));
        taxValueLabel = createStyledLabel("\u20B9 0.00", valueFont);
        billingPanel.add(taxValueLabel);

        billingPanel.add(createStyledLabel("Service (10%):", labelFont));
        serviceChargeValueLabel = createStyledLabel("\u20B9 0.00", valueFont);
        billingPanel.add(serviceChargeValueLabel);

        JLabel grandTotalLabel = createStyledLabel("Grand Total:", new Font("Segoe UI", Font.BOLD, 28));
        grandTotalLabel.setForeground(PRIMARY_COLOR);
        billingPanel.add(grandTotalLabel);
        
        grandTotalValueLabel = createStyledLabel("\u20B9 0.00", new Font("Segoe UI", Font.BOLD, 30));
        grandTotalValueLabel.setForeground(new Color(211, 47, 47));
        billingPanel.add(grandTotalValueLabel);

        bottomPanel.add(billingPanel, BorderLayout.NORTH);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 25));
        buttonPanel.setOpaque(false);

        JButton totalBtn = new RoundedButton("TOTAL", PRIMARY_COLOR);
        JButton receiptBtn = new RoundedButton("PRINT RECEIPT (PDF)", PRIMARY_COLOR);
        JButton saveBtn = new RoundedButton("SAVE", PRIMARY_COLOR);
        JButton resetBtn = new RoundedButton("RESET", PRIMARY_COLOR);
        JButton exitBtn = new RoundedButton("EXIT", PRIMARY_COLOR);

        // Make buttons larger too!
        Dimension btnSize = new Dimension(220, 60);
        totalBtn.setPreferredSize(btnSize);
        receiptBtn.setPreferredSize(btnSize);
        saveBtn.setPreferredSize(btnSize);
        resetBtn.setPreferredSize(btnSize);
        exitBtn.setPreferredSize(btnSize);

        buttonPanel.add(totalBtn);
        buttonPanel.add(receiptBtn);
        buttonPanel.add(saveBtn);
        buttonPanel.add(resetBtn);
        buttonPanel.add(exitBtn);

        bottomPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Action Listeners
        totalBtn.addActionListener(e -> calculateTotal());
        receiptBtn.addActionListener(e -> generateReceipt());
        saveBtn.addActionListener(e -> saveBill());
        resetBtn.addActionListener(e -> resetForm());
        exitBtn.addActionListener(e -> System.exit(0));
    }

    private JLabel createStyledLabel(String text, Font font) {
        JLabel label = new JLabel(text, SwingConstants.RIGHT);
        label.setFont(font);
        label.setForeground(TEXT_COLOR);
        return label;
    }

    private JButton createQtyButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 22));
        btn.setBackground(new Color(240, 240, 240));
        btn.setForeground(PRIMARY_COLOR);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(40, 40));
        return btn;
    }

    // Custom Icon to make CheckBox Bigger (32x32)
    class CustomCheckBoxIcon implements Icon {
        private boolean selected;
        public CustomCheckBoxIcon(boolean selected) { this.selected = selected; }
        public int getIconWidth() { return 32; }
        public int getIconHeight() { return 32; }
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(x, y, 32, 32, 8, 8);
            g2.setColor(new Color(170, 170, 170));
            g2.drawRoundRect(x, y, 32, 32, 8, 8);
            if (selected) {
                g2.setColor(new Color(46, 125, 50)); // Green Check
                g2.fillRoundRect(x + 5, y + 5, 22, 22, 6, 6);
            }
            g2.dispose();
        }
    }

    class RoundedButton extends JButton {
        private Color bgColor;

        public RoundedButton(String text, Color bgColor) {
            super(text);
            this.bgColor = bgColor;
            setContentAreaFilled(false);
            setFocusPainted(false);
            setForeground(Color.WHITE);
            setFont(new Font("Segoe UI", Font.BOLD, 18));
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if (getModel().isPressed()) {
                g2.setColor(bgColor.darker());
            } else if (getModel().isRollover()) {
                g2.setColor(bgColor.brighter());
            } else {
                g2.setColor(bgColor);
            }
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            g2.dispose();
            super.paintComponent(g);
        }

        @Override
        protected void paintBorder(Graphics g) {
        }
    }

    private void updateMenuItems() {
        for (int i = 0; i < menuItems.size(); i++) {
            MenuItem item = menuItems.get(i);
            boolean isSelected = checkBoxes.get(i).isSelected();
            item.setSelected(isSelected);

            if (isSelected) {
                try {
                    int qty = Integer.parseInt(quantityFields.get(i).getText());
                    item.setQuantity(qty);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid quantity for " + item.getName());
                    item.setQuantity(0);
                }
            } else {
                item.setQuantity(0);
            }
        }
    }

    private void calculateTotal() {
        updateMenuItems();
        calculator.calculate(menuItems);

        subtotalValueLabel.setText(String.format("\u20B9 %.2f", calculator.getSubtotal()));
        taxValueLabel.setText(String.format("\u20B9 %.2f", calculator.getTax()));
        serviceChargeValueLabel.setText(String.format("\u20B9 %.2f", calculator.getServiceCharge()));
        grandTotalValueLabel.setText(String.format("\u20B9 %.2f", calculator.getGrandTotal()));
    }

    private void generateReceipt() {
        calculateTotal(); // Ensure latest calculation
        if (calculator.getGrandTotal() > 0) {
            
            // SHOW POPUP FOR CUSTOMER DETAILS
            JTextField nameField = new JTextField(15);
            nameField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            JTextField phoneField = new JTextField(15);
            phoneField.setFont(new Font("Segoe UI", Font.PLAIN, 18));

            JPanel myPanel = new JPanel();
            myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
            myPanel.setBackground(Color.WHITE);
            myPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
            
            JLabel nameLbl = new JLabel("Customer Name:");
            nameLbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
            myPanel.add(nameLbl);
            myPanel.add(nameField);
            myPanel.add(Box.createVerticalStrut(15));
            
            JLabel phoneLbl = new JLabel("Phone Number:");
            phoneLbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
            myPanel.add(phoneLbl);
            myPanel.add(phoneField);

            boolean valid = false;
            String customerName = "";
            String customerPhone = "";
            
            while (!valid) {
                int result = JOptionPane.showConfirmDialog(this, myPanel, 
                         "Enter Customer Details (Leave blank for Walk-in)", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                         
                if (result != JOptionPane.OK_OPTION) {
                    return; // User cancelled
                }
                
                customerName = nameField.getText().trim();
                customerPhone = phoneField.getText().trim();
                
                // Validation Logic
                boolean hasError = false;
                
                // 1. Validate Name: if provided, must be letters and spaces only, min 2 chars.
                if (!customerName.isEmpty() && !customerName.matches("^[a-zA-Z\\s]{2,50}$")) {
                    JOptionPane.showMessageDialog(this, "Invalid Name!\nName must contain only alphabets and spaces (minimum 2 characters).", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    hasError = true;
                }
                
                // 2. Validate Phone: if provided, must be exactly 10 digits.
                if (!hasError && !customerPhone.isEmpty() && !customerPhone.matches("^\\d{10}$")) {
                    JOptionPane.showMessageDialog(this, "Invalid Phone Number!\nPhone number must be exactly 10 digits (e.g., 9876543210).", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    hasError = true;
                }
                
                if (!hasError) {
                    valid = true; // All checks passed
                }
            }
            
            // Generate PDF
            String fileName = PDFGenerator.generateReceipt(menuItems, calculator, customerName, customerPhone);
            if (fileName != null) {
                JOptionPane.showMessageDialog(this, "Receipt saved successfully to your Downloads folder!\n" + fileName);
                try {
                    File pdfFile = new File(fileName);
                    if (pdfFile.exists() && Desktop.isDesktopSupported()) {
                        Desktop.getDesktop().open(pdfFile);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Could not open PDF automatically. File saved as: " + fileName);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select items before printing receipt.");
        }
    }

    private void saveBill() {
        calculateTotal(); // Ensure latest calculation
        if (calculator.getGrandTotal() > 0) {
            FileSaver.saveBill(calculator.getGrandTotal());
            JOptionPane.showMessageDialog(this, "Bill saved successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Please select items before saving.");
        }
    }

    private void resetForm() {
        for (JCheckBox cb : checkBoxes) {
            cb.setSelected(false);
        }
        for (JTextField qf : quantityFields) {
            qf.setText("0");
        }
        
        subtotalValueLabel.setText("\u20B9 0.00");
        taxValueLabel.setText("\u20B9 0.00");
        serviceChargeValueLabel.setText("\u20B9 0.00");
        grandTotalValueLabel.setText("\u20B9 0.00");
    }
}
