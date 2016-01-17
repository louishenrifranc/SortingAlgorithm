package statistique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingWorker;
import javax.swing.plaf.TableUI;

import Constante.Constante;

public class StatistiqueFenetre extends JFrame {

	public class Tuple{
		BigInteger a;
		BigInteger b;
		public Tuple(BigInteger a,BigInteger b) {
			this.a = a;
			this.b = b;
		}
		
		public BigInteger A() 
		{
			return this.a;
		}
		public BigInteger B()
		{
			return this.b;
		}	
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final static BigInteger add2 = new BigInteger(Integer.toString(2)); // BigInteger

	// de
	// base
	public final static BigInteger add1 = new BigInteger(Integer.toString(1));
	private JTextArea textArea;
	private JProgressBar progressBar;
	public Vector<Integer> _valeurs; // Stocke les tailles des differentes
	// listes
	public int _algos; // Un integer représentant les algorithmes séléctionnés
	Vector<Vector<Integer>> _listes; // Un tableau de liste d'elements à
	// trier

	private BigInteger nombreValeurDeplace; // Le nombre de valeurs deplaces,
	private BigInteger temps;
	private int nombreAlgo;
	
	public StatistiqueFenetre(Vector<Integer> nombreValeurs, int algos,
			Vector<Vector<Integer>> liste) {
		
		/* Construction de l'interface graphique. */
		super("SwingWorkerDemo");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		textArea = new JTextArea(12, 40);
		textArea.setEnabled(false);
		JPanel content = new JPanel(new BorderLayout());
		content.add(new JScrollPane(textArea,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED));
		progressBar = new JProgressBar();
		JPanel south = new JPanel(new BorderLayout());
		south.add(progressBar);
		content.add(south, BorderLayout.SOUTH);
		setContentPane(content);
		//pack();
		setVisible(true);
		textArea.setBackground(Color.black);
		textArea.setForeground(Color.white);
		/* Recuperation des listes */
		this._algos = algos;
		_listes = liste;
		_valeurs = nombreValeurs;
		
		// Calcule le nombre d'algorithme séléctionnés
		while(algos > 0){
			if((algos & (1<<0) ) == 1)
				nombreAlgo++;
			algos = algos >> 1;
		}
	}
	
	public class StatistiqueFenetreWorker extends SwingWorker<Integer, String> {

		/**
		 * Constructeur
		 * 
		 * @param nombreValeurs
		 *            : un vecteur contenant les différentes tailles des listes
		 * @param _algos
		 *            : un integer representant les algorithmes	 choisis
		 * @param liste
		 *            : un tableau de listes de valeurs
		 */
		public StatistiqueFenetreWorker() {
			super();
			addPropertyChangeListener(new PropertyChangeListener() {

				public void propertyChange(PropertyChangeEvent evt) {
					if ("progress".equals(evt.getPropertyName())) {
						progressBar.setValue((Integer) evt.getNewValue());
					}
				}
			});

		}

		public void ajouterHeapSortUtil(Integer nombre, ArrayList<Integer> _heap) {
			_heap.add(nombre); // Append to the heap
			nombreValeurDeplace = nombreValeurDeplace.add(add1);
			int currentIndex = _heap.size() - 1; // Le dernier element
			while (currentIndex > 0) {
				int parentIndex = (currentIndex - 1) / 2;
				if ( _heap.get(currentIndex).compareTo(_heap.get(parentIndex)) > 0) {
					Integer temp = _heap.get(currentIndex);
					_heap.set(currentIndex, _heap.get(parentIndex));
					_heap.set(parentIndex, temp);
					nombreValeurDeplace = nombreValeurDeplace.add(add2);
				} else {
					break;
				}
				currentIndex = parentIndex;

			}
		}

		private Tuple bubbleSort(Vector<Integer> list, int k) {
			int swap;
			long tempsFin = 0;
			long tempsDebut = System.nanoTime();
			for (int c = 1; c < _valeurs.elementAt(k); c++) {
				for (int d = 0; d < _valeurs.elementAt(k) - c; d++) {
					if (list.get(d) > list.get(d + 1)) {

						nombreValeurDeplace = nombreValeurDeplace.add(add2);
						
						swap = list.get(d);
						list.set(d, list.get(d + 1));
						list.set(d + 1, swap);
					

					}
				}
			}
			tempsFin = System.nanoTime();
			long tempsTri = (Math.abs(tempsDebut - tempsFin));
			return new Tuple(nombreValeurDeplace, new BigInteger(Long.toString(tempsTri)));
		}

		@Override
		protected Integer doInBackground() throws Exception {
			return gestionAlgorithmes();
		}

		protected void done() {
			try {
				/* Le traitement est terminé. */
				setProgress(100);
				/*
				 * À la fin du traitement, affichage du nombre de fichiers
				 * parcourus dans le textfield.
				 */
				// textField.setText(String.valueOf(get()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		/**
		 * Remove the root from the heap
		 * 
		 * @throws InterruptedException
		 */
		public void enleverHeapSortUtil(int showIndex, Vector<Integer> list,
				ArrayList<Integer> _heap) {
			if (_heap.size() == 0)
				return;
			Integer removedObject = _heap.get(0);
			_heap.set(0, _heap.get(_heap.size() - 1));
			_heap.remove(_heap.size() - 1);
			nombreValeurDeplace = nombreValeurDeplace.add(add2);
			list.set(showIndex, removedObject);

			int currentIndex = 0;
			while (currentIndex < _heap.size() - 1) {
				int leftChildIndex = 2 * currentIndex + 1;
				int rightChildIndex = 2 * currentIndex + 2;

				if (leftChildIndex >= _heap.size())
					break;

				int maxIndex = leftChildIndex;
				if (rightChildIndex < _heap.size()) {

					if (_heap.get(maxIndex).compareTo(
							_heap.get(rightChildIndex)) < 0) {
						maxIndex = rightChildIndex;
					}
				}
				if (_heap.get(currentIndex).compareTo(_heap.get(maxIndex)) < 0) {
					Integer temp = _heap.get(maxIndex);
					_heap.set(maxIndex, _heap.get(currentIndex));
					_heap.set(currentIndex, temp);
					nombreValeurDeplace = nombreValeurDeplace.add(add2);
					currentIndex = maxIndex;

				} else {
					break;
				}
			}
		}

		/**
		 * Methode qui est appelé par le constructeur principal Lance les
		 * différents algorithmes et récupere les temps et nombre de valeurs
		 * déplacés
		 */
		public Integer gestionAlgorithmes() {
			
			Tuple buffer;
			long pas = 100 / (_listes.size() * 4);
			long min = 0, max = 100;
			double progress;
			nombreValeurDeplace=new BigInteger("0");
			BigInteger temps[][]=new BigInteger[nombreAlgo+1][_valeurs.size()+2];
			BigInteger nbresvaleursdeplaces[][]=new BigInteger[nombreAlgo+1][_valeurs.size()+2];
			
			for(int i=1;i<= _valeurs.size() ; i++)
			{
				temps[0][i]= new BigInteger(Integer.toString(_valeurs.elementAt(i-1)));
				nbresvaleursdeplaces[0][i]= new BigInteger(Integer.toString(_valeurs.elementAt(i-1)));
			}
			
			System.out.println("[debug] nombreAlgo size ="+nombreAlgo+" nombre de valeurs = "+_valeurs.size());
			
			
			for (int i = 0; i <_listes.size(); i++) { // Pour toutes les listes
				int j = 1;
				int step = 1;

				Vector<Integer> liste = _listes.elementAt(i); // recupere la
																// liste
				Vector<Integer> l1=new Vector<>();
				
				progress = min + (i + step) * pas;
				setProgress(Math.min((int) progress, 100));
				if ((_algos & (Constante.codeSelectionSort)) != 0) {
					
					l1 = (Vector) liste.clone();
					nombreValeurDeplace=new BigInteger("0");

					buffer = selectionSort(l1, i);
					temps[j][i+1]=buffer.A();
					temps[j][0]=new BigInteger(Integer.toString(Constante.codeSSshort));
					nbresvaleursdeplaces[j][0]=new BigInteger(Integer.toString(Constante.codeSSshort));
					nbresvaleursdeplaces[j++][i+1]=buffer.B();


					publish("SelectionSort : Pour " + _valeurs.elementAt(i)
							+ " valeurs: " + buffer.B().toString() + "nanosecondes et "
							+ buffer.A().toString()
							+ " valeurs deplaces");
				}
				step++;
				progress = min + (i + step) * pas;
				setProgress(Math.min((int) progress, 100));
				
				if ((_algos & (Constante.codeBubbleSort)) != 0) {
					nombreValeurDeplace=new BigInteger("0");

					l1 = (Vector) liste.clone();

					buffer = bubbleSort(l1, i);
					temps[j][i+1]=buffer.A();
					temps[j][0]=new BigInteger(Integer.toString(Constante.codeBSshort));
					nbresvaleursdeplaces[j][0]=new BigInteger(Integer.toString(Constante.codeBSshort));
					nbresvaleursdeplaces[j++][i+1]=buffer.B();

					publish("BubbleSort : Pour " + _valeurs.elementAt(i)
							+ " valeurs: " + buffer.B().toString() + "nanosecondes et "
							+ buffer.A().toString()
							+ " valeurs deplaces");

				}
				step++;
				progress = min + (i + step) * pas;
				setProgress(Math.min((int) progress, 100));
				
				if ((_algos & (Constante.codeQuickSort)) != 0) { // Si QuickSort
																	// a
																	// été
																	// séléctionnés
				
					nombreValeurDeplace=new BigInteger("0");

					l1 = (Vector) liste.clone();
					

					buffer = quicksortUtil(l1, i); // Lance le quicksort en
														// récupérant le temps
					temps[j][i+1]=buffer.A();
					temps[j][0]=new BigInteger(Integer.toString(Constante.codeQSshort));
					nbresvaleursdeplaces[j][0]=new BigInteger(Integer.toString(Constante.codeQSshort));
					nbresvaleursdeplaces[j++][i+1]=buffer.B();

					publish("Quicksort : Pour " + _valeurs.elementAt(i)
							+ " valeurs: " + buffer.B().toString() + "nanosecondes et "
							+ buffer.A().toString()
							+ " valeurs deplaces");
				}
				step++;
				progress = min + (i + step) * pas;
				setProgress(Math.min((int) progress, 100));
				
				
				if ((_algos & (Constante.codeHeapSort)) != 0) {
			
					l1 = (Vector) liste.clone();

					nombreValeurDeplace=new BigInteger("0");
					

					buffer = heapSort(l1, i);
					
					temps[j][i+1]=buffer.A();
					temps[j][0]=new BigInteger(Integer.toString(Constante.codeHSshort));
					nbresvaleursdeplaces[j][0]=new BigInteger(Integer.toString(Constante.codeHSshort));
					nbresvaleursdeplaces[j++][i+1]=buffer.B();

					publish("HeapSort : Pour " + _valeurs.elementAt(i)
							+ " valeurs: " + buffer.B().toString() + "nanosecondes et "
							+ buffer.A().toString()
							+ " valeurs deplaces");

				}
			
				
			}

			for(int i=0 ; i<nombreAlgo+1;i++){
				for(int j=0;j<=_valeurs.size();j++)
				{
					System.out.print(nbresvaleursdeplaces[i][j]+" ");
				}
				System.out.println();
			}
			System.out.println("-------------------------");

			for(int i=0 ; i<nombreAlgo+1;i++){
				for(int j=0;j<=_valeurs.size();j++)
				{
					System.out.print(temps[i][j]+" ");
				}
				System.out.println();
			}
			Graphe graphePanel = new Graphe(temps,nombreAlgo+1,_valeurs.size()+1,_algos,false);
//		    Graphe graphePanel1 = new Graphe(nbresvaleursdeplaces,nombreAlgo+1,_valeurs.size()+1,_algos,true);

			JFrame frame = new JFrame("DrawGraph");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.getContentPane().add(graphePanel);
			frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 

			frame.pack();
			 //frame.setResizable(false);
			frame.setLocationByPlatform(true);
			frame.setVisible(true);
			return 0;
		}

		private Tuple heapSort(Vector<Integer> liste, int k) {
			long tempsDebut = System.nanoTime();
			ArrayList<Integer> _heap = new ArrayList<Integer>();
			for (int i = 0; i < _valeurs.elementAt(k); i++) { // Ajouter un
																// element
																// a la
				// pile
				ajouterHeapSortUtil(liste.get(i), _heap);
			}
			for (int i = _valeurs.elementAt(k) - 1; i >= 0; i--) { // Supprimer
																	// les
				// elements de la
				enleverHeapSortUtil(i, liste, _heap);
			}
			long tempsFin = System.nanoTime();
			long tempsTri = Math.abs(tempsDebut - tempsFin);
			return new Tuple(nombreValeurDeplace, new BigInteger(Long.toString(tempsTri)));
		}

		protected void process(List<String> strings) {
			/* Affichage des publications reçues dans le textarea. */
			for (String s : strings)
				textArea.append(s + '\n');
		}

		private BigInteger quicksort(Vector<Integer> list, int low, int high) {
			int i = low;
			int j = high;
			int pivot = list.get(low + (high - low) / 2);
			while (i <= j) {
				while (list.get(i) < pivot) {
					i++;
				}
				while (list.get(j) > pivot) {
					j--;
				}
				if (i <= j) {
					int temp = list.get(i);
					list.set(i, list.get(j));
					list.set(j, temp);
					nombreValeurDeplace = nombreValeurDeplace.add(add2);
					i++;
					j--;
				}
			}
			if (low < j) {
				quicksort(list, low, j);
			}
			if (i < high) {
				quicksort(list, i, high);
			}
			return nombreValeurDeplace;
		}

		private Tuple quicksortUtil(Vector<Integer> liste, int k) {
			long tempsDebut = System.nanoTime();
			BigInteger nbrevd = quicksort(liste, 0, _valeurs.elementAt(k) - 1);
			Long tempsFin = System.nanoTime();
			long tempsTri = (Math.abs(tempsDebut - tempsFin));

			return new Tuple(nbrevd, new BigInteger(Long.toString(tempsTri)));
		}

		/**
		 * Algorithme de SelectionSort
		 * @param liste
		 *            : la liste
		 * @param k
		 * @return
		 */
		private Tuple selectionSort(Vector<Integer> liste, int k) {
			long tempsDebut = System.nanoTime();
			for (int i = 0; i < _valeurs.elementAt(k) - 1; i++) {
				int currentMinIndex = i;
				for (int j = i + 1; j < _valeurs.elementAt(k); j++) {
					if (liste.get(currentMinIndex) > liste.get(j)) {
						
						currentMinIndex = j;
					}
				}
				  
				if (currentMinIndex != i) {
					int tmp = liste.get(currentMinIndex);
					liste.set(currentMinIndex, liste.get(i));
					liste.set(i, tmp);
					nombreValeurDeplace = nombreValeurDeplace.add(add2);
				}

			}
			long tempsFin = System.nanoTime();
			long tempsTri = Math.abs(tempsDebut - tempsFin);

			return new Tuple(nombreValeurDeplace,new BigInteger(Long.toString(tempsTri)));
		}
	}

}
