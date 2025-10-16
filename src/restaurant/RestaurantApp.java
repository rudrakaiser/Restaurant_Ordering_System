package restaurant;

import java.awt.*;
import javax.swing.*;

public class RestaurantApp {
    private JFrame mainFrame;
    private JPanel menuPanel, controlPanel;
    private DataStore dataStore;
    private Order currentOrder;

    private JFrame welcomeFrame;

    public RestaurantApp() {
        showWelcomeScreen();
    }

    private void showWelcomeScreen() {
        welcomeFrame = new JFrame("Restaurant Ordering System");
        welcomeFrame.setSize(900, 500);
        welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        welcomeFrame.setLocationRelativeTo(null);
        welcomeFrame.setLayout(new BorderLayout());

        // ===== Background Image =====
        ImageIcon bgIcon = new ImageIcon("src/restaurant/images/welcome_bg.jpeg");
        Image scaledBg = bgIcon.getImage().getScaledInstance(900, 500, Image.SCALE_SMOOTH);
        JLabel backgroundLabel = new JLabel(new ImageIcon(scaledBg));
        backgroundLabel.setLayout(new BorderLayout());

        // ===== Welcome Text =====
        JLabel welcomeLabel = new JLabel("<html><div style='text-align: center;'>"
                + "Welcome To The<br>"
                + "Restaurant Ordering System"
                + "</div></html>", JLabel.CENTER) {

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                // Create gradient for text
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(255, 215, 0),      // top - golden yellow
                    0, getHeight(), new Color(255, 100, 0)  // bottom - deep orange
                );

                // Shadow layer
                FontMetrics fm = g2.getFontMetrics(getFont());
                String text = "Welcome To The\nRestaurant Ordering System";

                // Soft outer glow
                g2.setColor(new Color(0, 0, 0, 120));
                for (int i = -2; i <= 2; i++) {
                    for (int j = -2; j <= 2; j++) {
                        if (Math.abs(i) + Math.abs(j) > 0) {
                            g2.drawString("", i, j);
                        }
                    }
                }

                g2.setPaint(gradient);
                super.paintComponent(g2);
                g2.dispose();
            }
        };

        // ===== Style =====
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 44));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setOpaque(false);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(80, 0, 0, 0));



        // ===== Button =====
        JButton getStartedBtn = new JButton("Get Started") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                // Gradient Background
                GradientPaint gp = new GradientPaint(
                    0, 0, new Color(255, 140, 0),    // orange start
                    getWidth(), getHeight(), new Color(255, 90, 0) // red-orange end
                );
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                super.paintComponent(g2);
                g2.dispose();
            }
            @Override
            protected void paintBorder(Graphics g) {
                // Remove default border
            }
        };

        // ===== Button Style =====
        getStartedBtn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        getStartedBtn.setForeground(Color.WHITE);
        getStartedBtn.setFocusPainted(false);
        getStartedBtn.setContentAreaFilled(false);
        getStartedBtn.setOpaque(false);
        getStartedBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        getStartedBtn.setPreferredSize(new Dimension(200, 50));

        // ===== Hover Effect =====
        getStartedBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                getStartedBtn.setForeground(new Color(255, 255, 255));
                getStartedBtn.setFont(new Font("Segoe UI", Font.BOLD, 21));
                getStartedBtn.setBackground(new Color(255, 100, 20));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                getStartedBtn.setFont(new Font("Segoe UI", Font.BOLD, 20));
                getStartedBtn.setBackground(new Color(255, 140, 0));
            }
        });

        // ===== Button Action =====
        getStartedBtn.addActionListener(e -> {
            welcomeFrame.dispose();
            initializeMainApp();
        });

        JPanel btnPanel = new JPanel();
        btnPanel.setOpaque(false); // Transparent panel
        btnPanel.add(getStartedBtn);
        btnPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 50, 0));

        // ===== Add Components to background =====
        backgroundLabel.add(welcomeLabel, BorderLayout.CENTER);
        backgroundLabel.add(btnPanel, BorderLayout.SOUTH);

        welcomeFrame.setContentPane(backgroundLabel);
        welcomeFrame.setVisible(true);
    }

    // ===== Category Label Factory =====
    private JLabel createCategoryLabel(String text) {
        JLabel label = new JLabel(text, JLabel.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 28));
        label.setForeground(new Color(0, 0, 0));
        label.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        // Subtle shadow effect
        label.setUI(new javax.swing.plaf.basic.BasicLabelUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                
                // Shadow
                // g2.setColor(new Color(0, 0, 0, 100));
                // g2.drawString(label.getText(), 3, label.getHeight() - 7);
                
                // Main text
                g2.setColor(label.getForeground());
                g2.drawString(label.getText(), 2, label.getHeight() - 8);
                g2.dispose();
            }
        });

        return label;
    }

    private void initializeMainApp() {
        dataStore = new DataStore();
        currentOrder = new Order();

        mainFrame = new JFrame("Restaurant Ordering System");
        mainFrame.setSize(900, 500);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setLocationRelativeTo(null);

        menuPanel = new JPanel(new GridLayout(0,4));

        // ===== Starter Items =====
        MenuItem samosa = new StarterItem("Samosa", 30);
        MenuItem springRoll = new StarterItem("Spring Roll", 50);
        MenuItem burger = new StarterItem("Burger", 150);

        // ===== Main Course Items =====
        MenuItem pizza = new MainCourseItem("Pizza", 300);
        MenuItem biryani = new MainCourseItem("Biryani", 250);
        MenuItem friedRice = new MainCourseItem("Fried Rice", 180);

        // ===== Drinks =====
        MenuItem coke = new DrinkItem("Coke", 80, "One Liter");
        MenuItem mojo = new DrinkItem("Mojo", 40, "Half Liter");
        MenuItem coffee = new DrinkItem("Coffee", 100, "Hot");

        // ===== Desserts =====
        MenuItem iceCream = new DessertItem("Ice Cream", 120);
        MenuItem cake = new DessertItem("Chocolate Cake", 200);
        MenuItem pudding = new DessertItem("Pudding", 150);

        // ===== Buttons =====

        // Samosa
        ImageIcon samosaImage = new ImageIcon("src/restaurant/images/samosa.png");
        Image scaledSamosa = samosaImage.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon samosaIcon = new ImageIcon(scaledSamosa);
        JButton samosaBtn = new JButton("Samosa - 30৳", samosaIcon);
        samosaBtn.setBackground(Color.BLACK);
        samosaBtn.setForeground(Color.WHITE);
        samosaBtn.setFocusPainted(false);

        // Spring Roll
        ImageIcon springRollImage = new ImageIcon("src/restaurant/images/springroll.png");
        Image scaledSpringRoll = springRollImage.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon springRollIcon = new ImageIcon(scaledSpringRoll);
        JButton springRollBtn = new JButton("Spring Roll - 50৳", springRollIcon);
        springRollBtn.setBackground(Color.BLACK);
        springRollBtn.setForeground(Color.WHITE);
        springRollBtn.setFocusPainted(false);

        // Burger
        ImageIcon burgerImage = new ImageIcon("src/restaurant/images/burger.png");
        Image scaledBurger = burgerImage.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon burgerIcon = new ImageIcon(scaledBurger);
        JButton burgerBtn = new JButton("Burger - 150৳",burgerIcon);
        burgerBtn.setBackground(Color.BLACK);
        burgerBtn.setForeground(Color.WHITE);
        burgerBtn.setFocusPainted(false);

        // Pizza
        ImageIcon pizzaImage = new ImageIcon("src/restaurant/images/pizza.png");
        Image scaledPizza = pizzaImage.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon pizzaIcon = new ImageIcon(scaledPizza);
        JButton pizzaBtn = new JButton("Pizza - 300৳", pizzaIcon);
        pizzaBtn.setBackground(Color.BLACK);
        pizzaBtn.setForeground(Color.WHITE);
        pizzaBtn.setFocusPainted(false);

        // Biriyani
        ImageIcon biriyaniImage = new ImageIcon("src/restaurant/images/biriyani.png");
        Image scaledBiriyani = biriyaniImage.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon biriyaniIcon = new ImageIcon(scaledBiriyani);
        JButton biryaniBtn = new JButton("Biryani - 250৳", biriyaniIcon);
        biryaniBtn.setBackground(Color.BLACK);
        biryaniBtn.setForeground(Color.WHITE);
        biryaniBtn.setFocusPainted(false);

        // Fried Rice
        ImageIcon friedRiceImage = new ImageIcon("src/restaurant/images/friedrice.png");
        Image scaledFriedRice = friedRiceImage.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon friedRiceIcon = new ImageIcon(scaledFriedRice);
        JButton friedRiceBtn = new JButton("Fried Rice - 180৳", friedRiceIcon);
        friedRiceBtn.setBackground(Color.BLACK);
        friedRiceBtn.setForeground(Color.WHITE);
        friedRiceBtn.setFocusPainted(false);

        // Coke
        ImageIcon cokeImage = new ImageIcon("src/restaurant/images/coke.png");
        Image scaledCoke = cokeImage.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon cokeIcon = new ImageIcon(scaledCoke);
        JButton cokeBtn = new JButton("Coke - 50৳", cokeIcon);
        cokeBtn.setBackground(Color.BLACK);
        cokeBtn.setForeground(Color.WHITE);
        cokeBtn.setFocusPainted(false);

        // Mojo
        ImageIcon mojoImage = new ImageIcon("src/restaurant/images/mojo.png");
        Image scaledMojo = mojoImage.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon mojoIcon = new ImageIcon(scaledMojo);
        JButton mojoBtn = new JButton("Mojo - 40৳", mojoIcon);
        mojoBtn.setBackground(Color.BLACK);
        mojoBtn.setForeground(Color.WHITE);
        mojoBtn.setFocusPainted(false);

        // Coffee
        ImageIcon coffeeImage = new ImageIcon("src/restaurant/images/coffee.png");
        Image scaledCoffee = coffeeImage.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon coffeeIcon = new ImageIcon(scaledCoffee);
        JButton coffeeBtn = new JButton("Coffee - 100৳", coffeeIcon);
        coffeeBtn.setBackground(Color.BLACK);
        coffeeBtn.setForeground(Color.WHITE);
        coffeeBtn.setFocusPainted(false);

        // Ice Cream
        ImageIcon iceCreamImage = new ImageIcon("src/restaurant/images/icecream.png");
        Image scaledIceCream = iceCreamImage.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon iceCreamIcon = new ImageIcon(scaledIceCream);
        JButton iceCreamBtn = new JButton("Ice Cream - 120৳", iceCreamIcon);
        iceCreamBtn.setBackground(Color.BLACK);
        iceCreamBtn.setForeground(Color.WHITE);
        iceCreamBtn.setFocusPainted(false);

        // Chocolate Cake
        ImageIcon chocolateCakeImage = new ImageIcon("src/restaurant/images/chocolatecake.png");
        Image scaledChocolateCake = chocolateCakeImage.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon chocolateCakeIcon = new ImageIcon(scaledChocolateCake);
        JButton cakeBtn = new JButton("Chocolate Cake - 200৳", chocolateCakeIcon);
        cakeBtn.setBackground(Color.BLACK);
        cakeBtn.setForeground(Color.WHITE);
        cakeBtn.setFocusPainted(false);

        // Pudding
        ImageIcon puddingImage = new ImageIcon("src/restaurant/images/pudding.png");
        Image scaledPudding = puddingImage.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon puddingIcon = new ImageIcon(scaledPudding);
        JButton puddingBtn = new JButton("Pudding - 150৳", puddingIcon);
        puddingBtn.setBackground(Color.BLACK);
        puddingBtn.setForeground(Color.WHITE);
        puddingBtn.setFocusPainted(false);

        // ===== Add to panel =====

        menuPanel.add(createCategoryLabel("1. Starters"));
        menuPanel.add(samosaBtn);
        menuPanel.add(springRollBtn);
        menuPanel.add(burgerBtn);

        menuPanel.add(createCategoryLabel("2. Main Course"));
        menuPanel.add(pizzaBtn);
        menuPanel.add(biryaniBtn);
        menuPanel.add(friedRiceBtn);

        menuPanel.add(createCategoryLabel("3. Drinks"));
        menuPanel.add(cokeBtn);
        menuPanel.add(mojoBtn);
        menuPanel.add(coffeeBtn);

        menuPanel.add(createCategoryLabel("4. Desserts"));
        menuPanel.add(iceCreamBtn);
        menuPanel.add(cakeBtn);
        menuPanel.add(puddingBtn);


        // ====== Action Listeners ======
        samosaBtn.addActionListener(e -> addItem(samosa));
        springRollBtn.addActionListener(e -> addItem(springRoll));
        burgerBtn.addActionListener(e -> addItem(burger));

        pizzaBtn.addActionListener(e -> addItem(pizza));
        biryaniBtn.addActionListener(e -> addItem(biryani));
        friedRiceBtn.addActionListener(e -> addItem(friedRice));

        cokeBtn.addActionListener(e -> addItem(coke));
        mojoBtn.addActionListener(e -> addItem(mojo));
        coffeeBtn.addActionListener(e -> addItem(coffee));

        iceCreamBtn.addActionListener(e -> addItem(iceCream));
        cakeBtn.addActionListener(e -> addItem(cake));
        puddingBtn.addActionListener(e -> addItem(pudding));

        // ===== Control Buttons =====

        controlPanel = new JPanel();
        Font btnFont = new Font("Arial", Font.BOLD, 16);
        JButton saveBtn = new JButton("Save Order");
        saveBtn.setBackground(Color.black);
        saveBtn.setForeground(Color.white);
        saveBtn.setFont(btnFont);
        JButton findBtn = new JButton("Find Order");
        findBtn.setBackground(Color.black);
        findBtn.setForeground(Color.white);
        findBtn.setFont(btnFont);
        JButton billBtn = new JButton("Generate Bill");
        billBtn.setBackground(Color.black);
        billBtn.setForeground(Color.white);
        billBtn.setFont(btnFont);
        JButton allOrdersBtn = new JButton("All Orders");
        allOrdersBtn.setBackground(Color.black);
        allOrdersBtn.setForeground(Color.white);
        allOrdersBtn.setFont(btnFont);

        controlPanel.add(saveBtn);
        controlPanel.add(findBtn);
        controlPanel.add(billBtn);
        controlPanel.add(allOrdersBtn);

        // Save order
        saveBtn.addActionListener(e -> {
            if(currentOrder.getItems().isEmpty()){
                JOptionPane.showMessageDialog(mainFrame,"No items in order!");
            } else {
                dataStore.saveOrder(currentOrder);
                JOptionPane.showMessageDialog(mainFrame,"✅ Order saved with ID: "+currentOrder.getOrderId());
                currentOrder = new Order();
            }
        });

        // Find order
        findBtn.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(mainFrame,"Enter Order ID:");
            if(input != null && !input.isEmpty()){
                try {
                    int id = Integer.parseInt(input);
                    Order found = dataStore.getOrderById(id);
                    if(found != null){
                        showOrderDetails(found);
                    } else {
                        JOptionPane.showMessageDialog(mainFrame,"❌ Order not found!");
                    }
                } catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(mainFrame,"Invalid Order ID!");
                }
            }
        });

        // Generate Bill
        billBtn.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(mainFrame,"Enter Order ID to generate bill:");
            if(input != null && !input.isEmpty()){
                try {
                    int id = Integer.parseInt(input);
                    Order found = dataStore.getOrderById(id);
                    if(found != null){
                        showBill(found);
                    } else {
                        JOptionPane.showMessageDialog(mainFrame,"❌ Order ID not found!");
                    }
                } catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(mainFrame,"Invalid Order ID!");
                }
            }
        });
        
        mainFrame.add(new JScrollPane(menuPanel),BorderLayout.CENTER);
        mainFrame.add(controlPanel,BorderLayout.SOUTH);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }

    private void addItem(MenuItem item){
        currentOrder.addItem(new OrderItem(item,1));
        JOptionPane.showMessageDialog(mainFrame,item.getName()+" added!");
    }

    private void showOrderDetails(Order order){
        StringBuilder sb = new StringBuilder("Order ID: "+order.getOrderId()+"\nItems:\n");
        for(OrderItem oi : order.getItems()){
            sb.append(oi.getItem().getName())
              .append(" x").append(oi.getQuantity())
              .append(" = ").append(oi.getTotalPrice())
              .append("৳\n\n");
        }
        // sb.append("Total: ").append(order.generateBill()).append("৳");
        JOptionPane.showMessageDialog(mainFrame,sb.toString(),"Order Found",JOptionPane.INFORMATION_MESSAGE);
    }

    private void showBill(Order order){
        StringBuilder bill = new StringBuilder("-----------|  Bill  |-----------\n");
        bill.append("Order ID: ").append(order.getOrderId()).append("\nItems:\n");
        for(OrderItem oi : order.getItems()){
            bill.append(oi.getItem().getName())
                .append(" x").append(oi.getQuantity())
                .append(" = ").append(oi.getTotalPrice())
                .append("৳\n");
        }
        bill.append("___________________\n");
        bill.append("Total: ৳").append(order.generateBill()).append("\n\n");
        JOptionPane.showMessageDialog(mainFrame,bill.toString(),"Bill for Order "+order.getOrderId(),JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> new RestaurantApp());
    }
}