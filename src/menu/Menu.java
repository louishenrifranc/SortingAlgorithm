package menu;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Constante.Constante;
import affichage.Affichage;

public class Menu extends JFrame {

	class MDialog extends JDialog {
		public MDialog(int i) {
			super();
			JEditorPane website = null;
			String path = Paths.get("").toAbsolutePath().toString()
					+ File.separator + "ressource" + File.separator;
			try {
				if (i == Constante.codeQuickSort)
					website = new JEditorPane(
							"http://zanotti.univ-tln.fr/algo/TRI-RAPIDE.html");
				else if (i == Constante.codeHeapSort)
					website = new JEditorPane(
							"http://zanotti.univ-tln.fr/algo/TRI-TAS.html");
				else if (i == Constante.codeBubbleSort)
					website = new JEditorPane(
							"http://zanotti.univ-tln.fr/algo/TRI-BULLES.html");
				else if (i == Constante.codeSelectionSort)
					website = new JEditorPane(
							"http://zanotti.univ-tln.fr/algo/TRI-SELECTION.html");
				website.setEditable(false);
				this.add(new JScrollPane(website));
				this.setVisible(true);
				this.setSize(400, 600);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
	class MOptionPane extends JOptionPane {
		public MOptionPane(String message) {
			super();
			showMessageDialog(null, message);
		}
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu window = new Menu();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	private JCheckBox checkbox;

	private JList liste_Alg;
	private JList generer_Nombre;
	private JSpinner temps_animation;
	private Map<JCheckBox, JSpinner> correspondance;
	private int[] index_algo_choisis;
	private int[] index_generation_choisis;
	private int temps_animation_choisi;

	private int valeur_min_choisi;

	private int valeur_max_choisi;

	private JFrame frame;

	/**
	 * Create the frame.
	 */
	@SuppressWarnings("unchecked")
	public Menu() {

		frame = new JFrame();

		frame.setBounds(100, 100, 593, 518);
		ImageIcon ii = new ImageIcon(Paths.get("").toAbsolutePath().toString()
				+ System.getProperty("file.separator") + "img"
				+ System.getProperty("file.separator") + "fondecran.jpg");

		JLabel background = new JLabel("", ii, JLabel.CENTER);
		JLabel image = new JLabel("", ii, JLabel.CENTER);
		frame.add(background);

		BorderLayout bl = new BorderLayout();
		background.setLayout(bl);
		/**
		 * /** Pour la partie Nord
		 */
		JPanel reglageCommun = new JPanel(new GridLayout(4, 2));
		JLabel lblNewLabel = new JLabel("Temps Animation");
		reglageCommun.add(lblNewLabel);

		temps_animation = new JSpinner();
		temps_animation.setModel(new SpinnerNumberModel(2, 0, 2000, 1));
		reglageCommun.add(temps_animation);

		JLabel lblValeurMin = new JLabel("Valeur Min");
		reglageCommun.add(lblValeurMin);
		JSpinner valeurmin = new JSpinner();
		valeurmin.setToolTipText("Valeur Min\r\n\r\n");
		valeurmin.setModel(new SpinnerNumberModel(0, 0, 3000, 1));
		reglageCommun.add(valeurmin);
		JLabel lblValeurMax = new JLabel("Valeur Max");
		reglageCommun.add(lblValeurMax);
		JSpinner valeurmax = new JSpinner();
		valeurmax.setModel(new SpinnerNumberModel(100, 0, 500, 1));
		valeurmax.setToolTipText("Valeur Max\r\n");
		reglageCommun.add(valeurmax);
		reglageCommun
				.setBorder(BorderFactory.createTitledBorder(
						BorderFactory.createLineBorder(Color.blue, 4),
						"Reglage Commun"));
		background.add(reglageCommun, BorderLayout.NORTH);

		/************************************************************************************************************
		 * Pour la partie West
		 *************************************************************************************************************/
		JCheckBox visu_check_button = new JCheckBox("\r\n"); // Pour le
																// ActionListener
		Vector<Component> listeWest = new Vector();
		int value = 1000;
		JPanel statistiques = new JPanel(new GridLayout(5, 3));
		JLabel choix_Stats = new JLabel("Choix Statistiques");
		statistiques.add(choix_Stats);
		JCheckBox stat_check_button = new JCheckBox("\r\n");

		stat_check_button.setSelected(false);
		statistiques.add(stat_check_button);
		statistiques.add(new Canvas());

		JCheckBox checkBox1 = new JCheckBox("");
		statistiques.add(checkBox1);
		checkBox1.setSelected(true);
		JLabel lblNewLabel_1 = new JLabel("Nombre de valeurs");
		statistiques.add(lblNewLabel_1);
		JSpinner spinner1 = new JSpinner();
		spinner1.setValue(value *= 2);
		statistiques.add(spinner1);
		checkBox1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AbstractButton abstractButton = (AbstractButton) e.getSource();
				if (abstractButton.getModel().isSelected()) {
					spinner1.setEnabled(true);
				} else {
					spinner1.setEnabled(false);

				}
			}
		});

		JCheckBox checkBox2 = new JCheckBox("");
		checkBox2.setSelected(true);
		statistiques.add(checkBox2);
		JLabel lblNewLabel_2 = new JLabel("Nombre de valeurs");
		statistiques.add(lblNewLabel_2);
		JSpinner spinner2 = new JSpinner();
		spinner2.setValue(value *= 2);
		statistiques.add(spinner2);
		checkBox2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AbstractButton abstractButton = (AbstractButton) e.getSource();
				if (abstractButton.getModel().isSelected()) {
					spinner2.setEnabled(true);
				} else {
					spinner2.setEnabled(false);

				}
			}
		});

		JCheckBox checkBox3 = new JCheckBox("");
		checkBox3.setSelected(true);
		statistiques.add(checkBox3);
		JLabel lblNewLabel_3 = new JLabel("Nombre de valeurs");
		statistiques.add(lblNewLabel_3);
		JSpinner spinner3 = new JSpinner();
		statistiques.add(spinner3);
		spinner3.setValue(value *= 2);
		checkBox3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AbstractButton abstractButton = (AbstractButton) e.getSource();
				if (abstractButton.getModel().isSelected()) {
					spinner3.setEnabled(true);
				} else {
					spinner3.setEnabled(false);

				}
			}
		});

		JCheckBox checkBox4 = new JCheckBox("");
		checkBox4.setSelected(true);
		statistiques.add(checkBox4);
		JLabel lblNewLabel_4 = new JLabel("Nombre de valeurs");
		statistiques.add(lblNewLabel_4);
		JSpinner spinner4 = new JSpinner();
		spinner4.setValue(value *= 2);
		statistiques.add(spinner4);
		checkBox4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AbstractButton abstractButton = (AbstractButton) e.getSource();
				if (abstractButton.getModel().isSelected()) {
					spinner4.setEnabled(true);
				} else {
					spinner4.setEnabled(false);

				}
			}
		});
		statistiques.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.blue, 4), "Statistiques"));
		background.add(statistiques, BorderLayout.WEST);
		listeWest.add(checkBox1);
		listeWest.add(spinner1);
		listeWest.add(lblNewLabel_1);
		listeWest.add(checkBox2);
		listeWest.add(spinner2);
		listeWest.add(lblNewLabel_2);
		listeWest.add(checkBox3);
		listeWest.add(spinner3);
		listeWest.add(lblNewLabel_3);
		listeWest.add(checkBox4);
		listeWest.add(spinner4);
		listeWest.add(lblNewLabel_4);

		stat_check_button.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				AbstractButton abstractButton = (AbstractButton) e.getSource();
				if (abstractButton.getModel().isSelected()) {
					for (Component cp : listeWest)
						cp.setEnabled(true);
					visu_check_button.setSelected(false);

				} else {
					for (Component cp : listeWest)
						cp.setEnabled(false);
					visu_check_button.setSelected(true);
				}
			}
		});

		/**************************************************************************************************************
		 * Pour la partie centrale
		 **************************************************************************************************************/
		JPanel visualisation = new JPanel(new GridLayout(5, 3));
		JLabel choix_Visu = new JLabel("Choix Statistiques");
		visualisation.add(choix_Visu);
		stat_check_button.setSelected(false);
		visualisation.add(visu_check_button);
		visualisation.add(new Canvas());
		JLabel lbla = new JLabel("Nombre de valeurs");
		visualisation.add(lbla);
		JSpinner visualisation_nbvaleurs = new JSpinner();
		visualisation_nbvaleurs.setValue((int) 100);
		visualisation.add(visualisation_nbvaleurs);
		visualisation.add(new Canvas());
		visualisation.add(new Canvas());
		visualisation.add(new Canvas());
		visualisation.add(new Canvas());
		visualisation.add(new Canvas());
		visualisation.add(new Canvas());

		visualisation
				.setBorder(BorderFactory.createTitledBorder(
						BorderFactory.createLineBorder(Color.blue, 4),
						"Visualisation"));
		background.add(visualisation, BorderLayout.CENTER);
		visu_check_button.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				AbstractButton abstractButton = (AbstractButton) e.getSource();

				if (abstractButton.getModel().isSelected()) {
					visualisation.setEnabled(true);
					lbla.setEnabled(true);
					visualisation_nbvaleurs.setEnabled(true);
					stat_check_button.setSelected(false);
				} else {
					visualisation.setEnabled(false);
					lbla.setEnabled(false);
					visualisation_nbvaleurs.setEnabled(false);
					stat_check_button.setSelected(true);

				}
			}
		});

		/*******************************************************************************************************************
		 * Pour la partie du Sud
		 *******************************************************************************************************************/
		JPanel south = new JPanel(new FlowLayout());
		liste_Alg = new JList<String>();
		south.add(liste_Alg, BorderLayout.EAST);
		liste_Alg.setModel(new AbstractListModel<String>() {

			private static final long serialVersionUID = 1L;
			String[] values = Constante.algorithme;

			public String getElementAt(int index) {
				return values[index];
			}

			public int getSize() {
				return values.length;
			}
		});
		int t[]={0,1,2,3};
		liste_Alg.setSelectedIndices(t);

		generer_Nombre = new JList<String>(Constante.generation_nombre);
		generer_Nombre.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		generer_Nombre.setSelectedIndex(1);
		south.add(generer_Nombre, BorderLayout.WEST);
		south.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.blue, 4),
				"Algorithmes et Generation de nombres"));

		background.add(south, BorderLayout.SOUTH);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenuItem mntmNewMenuItem = new JMenuItem(
				"Informations sur les Algorithmes");
		mntmNewMenuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int index[] = liste_Alg.getSelectedIndices();
				if (index.length == 1) {
					switch (index[0]) {
					case 0:
						new MDialog(Constante.codeHeapSort);
						break;
					case 1:
						new MDialog(Constante.codeBubbleSort);
						break;
					case 2:
						new MDialog(Constante.codeQuickSort);
						break;
					case 3:
						new MDialog(Constante.codeSelectionSort);
						break;
					}
				}
			}
		});
		menuBar.add(mntmNewMenuItem);

		/**
		 * Pour la partie Est
		 */
		JButton btnLancer = new JButton("Lancer");

		background.add(btnLancer, BorderLayout.EAST);

		btnLancer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				index_algo_choisis = liste_Alg.getSelectedIndices(); // Les
																		// algos
																		// choisis
				index_generation_choisis = generer_Nombre.getSelectedIndices(); // Les
																				// manieres
																				// de
																				// générer
																				// un
																				// algo
				temps_animation_choisi = (int) temps_animation.getValue(); // Temps
																			// de
																			// l'animation
				valeur_min_choisi = (int) valeurmin.getValue(); // Valeur min
				valeur_max_choisi = (int) valeurmax.getValue(); // Valeur max

				/**
				 * Si on a choisi aucun algorithme
				 */
				if (index_algo_choisis.length == 0) {
					JOptionPane.showMessageDialog(null,
							"Entree au moins une algorithme de tri");
				} else if (index_generation_choisis.length == 0) {
					new MOptionPane(
							"Choississez au moins une manière de générer des nombres");
				} else if (valeur_max_choisi <= valeur_min_choisi) {
					new MOptionPane("Verifier votre maximum et votre minimum");

				} else {
					if (stat_check_button.isSelected()) {
						Vector<Integer> tableauValeurs = new Vector<>();
						int valeur;
						if (checkBox1.isSelected()) {
							valeur = (int) spinner1.getValue();
							if(tableauValeurs.size() != 0 && valeur < tableauValeurs.lastElement()){
								new MOptionPane("Triez les valeurs");
								return;
							}
							if (valeur > 0)
								tableauValeurs.addElement(valeur);
						}
						if (checkBox2.isSelected()) {
							valeur = (int) spinner2.getValue();
							if(tableauValeurs.size() != 0 && valeur < tableauValeurs.lastElement()){
								new MOptionPane("Triez les valeurs");
								return;
							}
							if (valeur > 0)
								tableauValeurs.addElement(valeur);
						}
						if (checkBox3.isSelected()) {
							valeur = (int) spinner3.getValue();
							if(tableauValeurs.size() != 0 && valeur < tableauValeurs.lastElement()){
								new MOptionPane("Triez les valeurs");
								return;
							}
							if (valeur > 0)
								tableauValeurs.addElement(valeur);
						}
						if (checkBox4.isSelected()) {
							valeur = (int) spinner4.getValue();
							if(tableauValeurs.size() != 0 && valeur < tableauValeurs.lastElement()){
								new MOptionPane("Triez les valeurs");
								return;
							}
							if (valeur > 0)
								tableauValeurs.addElement(valeur);
						}

						Affichage affichage = new Affichage(index_algo_choisis,
								index_generation_choisis,
								temps_animation_choisi, tableauValeurs, true,
								valeur_min_choisi, valeur_max_choisi);

					} else if(visu_check_button.isSelected()){
						if (((int) visualisation_nbvaleurs.getValue()) > 1500) {
							new MOptionPane(
									"Impossible de génerer un affichage pour plus de 1500 valeurs");
						} else {
							Vector<Integer> nbValeur = new Vector<>();
							nbValeur.addElement((Integer) visualisation_nbvaleurs
									.getValue());
							Affichage affichage = new Affichage(
									index_algo_choisis,
									index_generation_choisis,
									temps_animation_choisi, nbValeur, false,
									valeur_min_choisi, valeur_max_choisi);

						}
					}
				}

			}
		});

	}

}
