package menu;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class tvuybi extends JFrame {

	/**
	 * Launch the application.
	 */
	   public static void main(String args[]) throws Exception {
	        JEditorPane website = new JEditorPane("http://zanotti.univ-tln.fr/algo/TRI-RAPIDE.html");
	        website.setEditable(false);

	        JFrame frame = new JFrame("Google");
	        frame.add(new JScrollPane(website));
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setVisible(true);
	        frame.pack();
	    }

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public tvuybi() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JComboBox comboBox = new JComboBox();
		contentPane.add(comboBox, BorderLayout.CENTER);
		
		JLabel lblNewLabel = new JLabel("New label");
		contentPane.add(lblNewLabel, BorderLayout.WEST);
		
		JComboBox comboBox_1 = new JComboBox();
		contentPane.add(comboBox_1, BorderLayout.NORTH);
		
		JButton btnNewButton = new JButton("New button");
		contentPane.add(btnNewButton, BorderLayout.EAST);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("New radio button");
		contentPane.add(rdbtnNewRadioButton, BorderLayout.SOUTH);
	}

}
