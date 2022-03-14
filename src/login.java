import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class login extends JFrame {

	private JPanel contentPane;
	private JTextField user;
	private JTextField password;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					login frame = new login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public login() {
		String[] arguments = new String[] {"admin"};
		String name = "admin";
		String pass = "admin";
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblBundleRecommender = new JLabel("Sale Bundle Recommender");
		lblBundleRecommender.setFont(new Font("Tahoma", Font.PLAIN, 32));
		lblBundleRecommender.setHorizontalAlignment(SwingConstants.CENTER);
		lblBundleRecommender.setBounds(15, 16, 413, 39);
		contentPane.add(lblBundleRecommender);
		
		JLabel lblNewLabel = new JLabel("Username:");
		lblNewLabel.setBounds(57, 77, 86, 20);
		contentPane.add(lblNewLabel);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(57, 135, 84, 20);
		contentPane.add(lblPassword);
		
		user = new JTextField();
		user.setBounds(138, 77, 229, 26);
		contentPane.add(user);
		user.setColumns(10);
		
		password = new JPasswordField();
		password.setColumns(10);
		password.setBounds(138, 132, 229, 26);
		contentPane.add(password);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = user.getText();
				String password1 = password.getText();
				
				if(username.equals(name)&&password1.equals(pass)){
					JOptionPane.showMessageDialog(contentPane, "Welcome admin!");
					
					try {
						
						Main.main(null);
						dispose();
						
					} catch (Exception e1) {
						
					}
				}
				else{
					JOptionPane.showMessageDialog(contentPane, "Wrong login credentials");
				}
				
			}
		});
		btnLogin.setBounds(161, 199, 115, 29);
		contentPane.add(btnLogin);
		
		
	}
}
