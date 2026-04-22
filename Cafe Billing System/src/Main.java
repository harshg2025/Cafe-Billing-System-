package src;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        // Try to set system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Run UI on Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            CafeBillingUI ui = new CafeBillingUI();
            ui.setVisible(true);
        });
    }
}
