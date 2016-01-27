package affichage;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import statistique.StatistiqueFenetre;
import statistique.StatistiqueFenetre.StatistiqueFenetreWorker;
import Constante.Constante;

/**
 * @author lh
 *
 */
public class Affichage extends JFrame
{

    private StatistiqueFenetre utilise;

    class FenetreHolder extends JPanel
    {
	private static final long serialVersionUID = 1L;

	@Override
	protected void paintComponent(Graphics g)
	{
	    super.paintComponent( g );

	}
    }

    private static Random          rand             = new Random();
    // nouveauw
    /**
	 * 
	 */
    private static final long      serialVersionUID = 1L;
    private static Vector<Integer> nombre_valeurs_statistique;
    private ArrayList<TriPanel>    listeTri;                        // Liste
								     // contenant
								     // les
								     // TriPanel
    // selectionnees
    private int[]                  index_des_Tri_Selectionnes;      // Liste
								     // des
								     // index
								     // des tris
    // choisis
    private int[]                  index_des_generation_de_valeurs; // Liste
								     // des
								     // index
								     // des
								     // nombre
    // a
    // générer
    private int                    nombre_valeur_visualisation;
    private int                    temps_raffraichissemnt_animation;
    private boolean                stat_ou_visualisation;
    private int                    valeur_min_liste;
    private int                    valeur_max_liste;
    private Vector<Integer>        list_valeurs_a_trier;
    private FenetreHolder          fenetres_holder;
    private JFrame                 frame;
    
    private int                    selectionAlgo;

    /**
     * Constructeur
     * 
     * @param index_tri_choisi
     *            : index des algorithmes de tri choisis
     * @param index_gene_nombre_choisi
     *            : index des manieres de génerer les algorithmes
     * @param temps_animation
     *            : temps de l'animation
     * @param nombre_valeurs
     *            : nombre de valeurs a générer
     * @param stat_ou_visu
     *            : si l'on veut visualiser / afficher des statistiques
     * @param valeurMin
     *            : valeur min dans le tableau a trier
     * @param valeurMax
     *            : valeur max dans le tableau a trier
     * @throws HeadlessException
     */
    public Affichage(int[] index_tri_choisi, int[] index_gene_nombre_choisi,
	    int temps_animation, Vector<Integer> nombre_valeurs,
	    boolean stat_ou_visu, int valeurMin, int valeurMax)
	    throws HeadlessException
    {

	super();
	this.index_des_Tri_Selectionnes = index_tri_choisi;
	this.index_des_generation_de_valeurs = index_gene_nombre_choisi;
	this.temps_raffraichissemnt_animation = temps_animation;
	selectionAlgo = 0;
	/*
	 * Enregistre un tableau de valeurs si la fonctionnnalité demandé sont
	 * les stats Sinon enregistre une seule valeur
	 */

	if (stat_ou_visu)
	{
	    this.nombre_valeurs_statistique = nombre_valeurs;
	}
	else if (!stat_ou_visu)
	{
	    this.nombre_valeur_visualisation = nombre_valeurs.get( 0 );
	}

	this.valeur_max_liste = valeurMax;
	this.valeur_min_liste = valeurMin;

	listeTri = new ArrayList<TriPanel>();
	list_valeurs_a_trier = new Vector<Integer>();
	this.stat_ou_visualisation = stat_ou_visu;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	double width = screenSize.getWidth() / 3;
	double height = screenSize.getHeight() / 3;

	FenetreHolder affichageholder = new FenetreHolder();
	affichageholder.setBackground( java.awt.Color.BLACK );
	affichageholder.setForeground( java.awt.Color.BLACK );

	if (!stat_ou_visualisation) affichageholder
	        .setLayout( optimizedLayout() );

	/*
	 * Pour tous les algorithmes de tri choisis
	 */
	for (int i = 0; i < index_tri_choisi.length; i++)
	{

	    switch (index_tri_choisi[i]) {
	    case Constante.codeHSshort:
		if (!stat_ou_visualisation) listeTri
		        .add( new affichage.HeapSort(
		                Constante.algorithme[index_tri_choisi[i]] ,
		                temps_animation , width , height ) );
		else if (stat_ou_visualisation) selectionAlgo |= Constante.codeHeapSort;
		break;
	    case Constante.codeBSshort:
		if (!stat_ou_visualisation) listeTri
		        .add( new affichage.BubbleSortUtil(
		                Constante.algorithme[index_tri_choisi[i]] ,
		                temps_animation , valeurMax , valeurMin ) );
		else if (stat_ou_visualisation) selectionAlgo |= Constante.codeBubbleSort;

		break;
	    case Constante.codeQSshort:
		if (!stat_ou_visualisation) listeTri
		        .add( new affichage.QuickSort(
		                Constante.algorithme[index_tri_choisi[i]] ,
		                temps_animation , width , height ) );
		else if (stat_ou_visualisation) selectionAlgo |= Constante.codeQuickSort;

		break;
	    case Constante.codeSSshort:
		if (!stat_ou_visualisation) listeTri
		        .add( new affichage.SelectionSort(
		                Constante.algorithme[index_tri_choisi[i]] ,
		                temps_animation , width , height ) );
		else if (stat_ou_visualisation) selectionAlgo |= Constante.codeSelectionSort;

		break;
	    default:
		break;
	    }

	    if (!stat_ou_visualisation)
	    {
		affichageholder.add( listeTri.get( i ) );
		listeTri.get( i ).setVisible( false );
		frame = new JFrame( "Sorting Algorithm Animations" );
		frame.add( affichageholder );
		frame.setSize( 1366 , 768 );
		frame.setLocationRelativeTo( null );
		frame.setVisible( true );
		// frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	    }
	}

	/**
	 * Visualisation = lancement de l'affichage
	 */
	if (!stat_ou_visualisation)
	{
	    genererNombre();
	    try
	    {
		beginAnimation();
	    }
	    catch (InterruptedException e)
	    {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
	else
	{
	    /*
	     * Lancement du décompte
	     */
	    Vector<Vector<Integer>> buffer = new Vector<Vector<Integer>>();

	    for (int i = 0; i < nombre_valeurs_statistique.size(); i++)
	    {
		nombre_valeur_visualisation = nombre_valeurs_statistique
		        .get( i );
		genererNombre();
		buffer.add( i , list_valeurs_a_trier );
	    }
	    SwingUtilities.invokeLater( new Runnable()
	    {

		public void run()
		{
		    /* Démarrage de l'interface graphique et du SwingWorker. */
		    StatistiqueFenetre demo = new StatistiqueFenetre(
			    nombre_valeurs , selectionAlgo , buffer );
		    StatistiqueFenetreWorker swingWorker = demo.new StatistiqueFenetreWorker();
		    swingWorker.execute();
		}
	    } );
	}
    }

    public void beginAnimation() throws InterruptedException
    {

	for (int i = 0; i < listeTri.size(); i++)
	{
	    listeTri.get( i ).setList( list_valeurs_a_trier , valeur_max_liste ,
		    valeur_min_liste );
	    listeTri.get( i ).setVisible( true );
	}

	repaint();
	ExecutorService executor = Executors.newFixedThreadPool( listeTri
	        .size() );
	for (int i = 0; i < listeTri.size(); i++)
	{
	    executor.execute( listeTri.get( i ) );
	}

    }

    private boolean genererNombre()
    {

	switch (index_des_generation_de_valeurs[0]) {
	case 0:
	    genererTendanceDecroissante();
	    break;
	case 1:
	    genererTendanceCroissante();
	    break;
	case 2:
	    genererRandom();
	    break;
	case 3:
	    genererQuasimentTrie();
	    break;
	}
	return true;
    }

    private Vector<Integer> genererQuasimentTrie()
    {
	list_valeurs_a_trier.clear();
	int diff = valeur_max_liste - valeur_min_liste;
	int compteur = 0 , valeur = valeur_min_liste;
	int changement = nombre_valeur_visualisation / diff;
	for (int i = 0; i < nombre_valeur_visualisation; i++)
	{
	    compteur++;
	    if (compteur == changement)
	    {
		valeur++;
		compteur = 0;

	    }
	    list_valeurs_a_trier.add( ( ( i % 75 == 0 ) ? rand.nextInt( Math
		    .abs( valeur_max_liste - valeur_min_liste ) )
		    + valeur_min_liste : valeur ) );
	}
	return list_valeurs_a_trier;
    }

    /*********************************************************************************************************************************
     * *************************************************************************
     * ****************************************************** Methode pour
     * générer des listes de valeurs de manière différente
     * 
     * @return
     */
    private Vector<Integer> genererRandom()
    {
	list_valeurs_a_trier.clear();
	for (int i = 0; i < nombre_valeur_visualisation; i++)
	{
	    list_valeurs_a_trier.add( rand.nextInt( Math.abs( valeur_max_liste
		    - valeur_min_liste ) )
		    + valeur_min_liste );
	}
	return list_valeurs_a_trier;
    }

    private Vector<Integer> genererTendanceCroissante()
    {
	list_valeurs_a_trier.clear();
	int diff = valeur_max_liste - valeur_min_liste;
	int compteur = 0 , valeur = valeur_min_liste;
	int changement = nombre_valeur_visualisation / diff;
	for (int i = 0; i < nombre_valeur_visualisation; i++)
	{
	    compteur++;
	    if (compteur == changement)
	    {
		valeur++;
		compteur = 0;

	    }
	    list_valeurs_a_trier.add( ( ( i % changement == 0 ) ? rand
		    .nextInt( Math.abs( valeur_max_liste - valeur_min_liste ) )
		    + valeur_min_liste : valeur ) );
	}
	return list_valeurs_a_trier;
    }

    private Vector<Integer> genererTendanceDecroissante()
    {
	list_valeurs_a_trier.clear();
	int diff = valeur_max_liste - valeur_min_liste;
	int compteur = 0 , valeur = valeur_max_liste;
	int changement = nombre_valeur_visualisation / diff;
	for (int i = 0; i < nombre_valeur_visualisation; i++)
	{
	    compteur++;
	    if (compteur == changement)
	    {
		valeur--;
		compteur = 0;

	    }
	    list_valeurs_a_trier.add( ( ( i % changement == 0 ) ? rand
		    .nextInt( Math.abs( valeur_max_liste - valeur_min_liste ) )
		    + valeur_min_liste : valeur ) );
	}
	return list_valeurs_a_trier;
    }

    private GridLayout optimizedLayout()
    {
	return new GridLayout( index_des_Tri_Selectionnes.length , 2 );
    }

}
