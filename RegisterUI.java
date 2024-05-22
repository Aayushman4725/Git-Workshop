import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class RegisterUI extends MouseAdapter implements ItemListener {
    private JCheckBox show = new JCheckBox("Show Password");
    private JPasswordField passwordField = new JPasswordField(15);

    public RegisterUI() {
        JFrame frame = new JFrame();
        JLabel usernameLabel = new JLabel("Email:");
        JLabel passwordLabel = new JLabel("Password:");
        usernameLabel.setFont(new Font("Caliber", Font.BOLD, 20));
        passwordLabel.setFont(new Font("Caliber", Font.BOLD, 20));
        JTextField usernameField = new JTextField(15); // Set columns to define the width

        show.setBackground(Color.CYAN);
        show.setFont(new Font("Arial", Font.BOLD, 15));
        show.addItemListener(this);


        JButton loginButton = new JButton("Sign Up");
        loginButton.setPreferredSize(new Dimension(90, 40));
        JLabel titleLabel = new JLabel("Register");
        titleLabel.setFont(new Font("Caliber", Font.BOLD, 40));
        titleLabel.setForeground(Color.RED);
        usernameField.setPreferredSize(new Dimension(80, 30));
        passwordField.setPreferredSize(new Dimension(80, 30));
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.CYAN);
        gbc.insets = new Insets(15, 15, 15,20 ); // Padding

        // TitleLabel
        gbc.fill = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);
        
        // usernameLabel and userfield
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(usernameLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(usernameField, gbc);
        
        // passwordlabel and passwordfield
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(passwordField, gbc);

        //show password 
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(show,gbc);

        // login Button
        gbc.fill = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2; 
        panel.add(loginButton, gbc);
        
        frame.setSize(500, 500);
        frame.add(panel);
        frame.setTitle("Register");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    

    public void actionPerformed(ActionEvent e){
        //TO DO
    }

    public void mouseEntered(MouseEvent e){

    }
    public void mouseExited(MouseEvent e) {
    }
    
    
    @Override
    public void mouseClicked(MouseEvent e){}
    
    @Override
    public void mousePressed(MouseEvent e){}
    
    @Override
    public void mouseReleased(MouseEvent e) {}
        
    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            passwordField.setEchoChar((char) 0);
        }
        else{
            passwordField.setEchoChar('*');
        }
    }
    public static void main(String[] args) {
        new RegisterUI();
    }
}




