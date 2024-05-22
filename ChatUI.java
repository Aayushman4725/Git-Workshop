import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ChatUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    private static void createAndShowGUI() {
        // Create the main frame
        JFrame frame = new JFrame("Chat Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);

        // Get the size of the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        JPanel container1 = new JPanel();
        JPanel headContainer1 = new JPanel();
        container1.setPreferredSize(new Dimension(screenSize.width / 20, screenSize.height / 4)); // Adjusted size
        container1.setLayout(new BorderLayout()); // Use BorderLayout for container1
        container1.setBackground(Color.getHSBColor(40 / 360.0f, 0.1f, 0.95f)); // Light gray background
        headContainer1.setBorder(new LineBorder(Color.getHSBColor(0, 0, 0.9f), 5)); // Light gray border
        headContainer1.setPreferredSize(new Dimension(screenSize.width / 10, screenSize.height / 22));
        headContainer1.setBorder(new LineBorder(Color.getHSBColor(0, 0, 0.2f), 5)); // Set red border
        container1.add(headContainer1, BorderLayout.NORTH); // Add headContainer1 to the top

        // Create the second container (as slim as possible but still visible)
        JPanel container2 = new JPanel();
        JPanel headContainer2 = new JPanel();
        container2.setBackground(Color.getHSBColor(120 / 360.0f, 0.3f, 0.9f)); // Light green background
        container2.setPreferredSize(new Dimension(screenSize.width, screenSize.height / 2)); // Adjusted size
        container2.setLayout(new BorderLayout()); // Use BorderLayout for container2
        headContainer2.setBackground(Color.getHSBColor(210 / 360.0f, 0.3f, 0.9f)); // Light blue background
        headContainer2.setPreferredSize(new Dimension(screenSize.width / 10, screenSize.height / 22));
        headContainer2.setBorder(new LineBorder(Color.BLACK, 4)); // Set blue border
        container2.add(headContainer2, BorderLayout.NORTH); // Add headContainer2 to the top

        // Set GridBagLayout for the frame's content pane
        frame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
    gbc.gridy = 0;
        gbc.weightx = 0.25; // Adjusted weight for container1
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        frame.add(container1, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.75; // Adjusted weight for container2
        frame.add(container2, gbc);

        // Make the frame visible
        frame.setVisible(true);
    }
}
