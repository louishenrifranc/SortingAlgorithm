package affichage;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class HeapSort extends TriPanel {
	private static final long serialVersionUID = 1L;
	private int _barreRouge = -1;
	private int _barreVerte = -1;
	private ArrayList<Integer> _heap = new ArrayList<Integer>();
	private int nombreSleep = 0;

	public HeapSort(String nom, int temps_animation, double largeur,
			double longeur) {
		super(nom, temps_animation);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Construction de l'arbre binaire
	 * 
	 * @throws InterruptedException
	 */
	public void ajouter(Integer nombre) throws InterruptedException {

		_heap.add(nombre); // Append to the heap
		nombreValeurDeplace+=1;
		Thread.sleep(temps_animation);
		nombreSleep++;
		int currentIndex = _heap.size() - 1; // Le dernier element
		_barreRouge = currentIndex;
		while (currentIndex > 0) {
			repaint();
			int parentIndex = (currentIndex - 1) / 2;
			if (_heap.get(currentIndex).compareTo(_heap.get(parentIndex)) > 0) {
				Thread.sleep(temps_animation);
				nombreSleep++;
				Integer temp = _heap.get(currentIndex);
				_heap.set(currentIndex, _heap.get(parentIndex));
				_heap.set(parentIndex, temp);
				nombreValeurDeplace+=2;
			} else {
				break;
			}
			currentIndex = parentIndex;
			_barreRouge = currentIndex;
			Thread.sleep(temps_animation);
			nombreSleep++;
			repaint();
		}
		_barreRouge = -1;
	}

	@Override
	public void clear() {
		_barreRouge = -1;
		_barreVerte = -1;
		_heap.clear();
	}

	/**
	 * Remove the root from the heap
	 * 
	 * @throws InterruptedException
	 */
	public void enlever(int showIndex) throws InterruptedException {
		if (_heap.size() == 0)
			return;
		repaint();
		Integer removedObject = _heap.get(0);
		_heap.set(0, _heap.get(_heap.size() - 1));
		_heap.remove(_heap.size() - 1);
		nombreValeurDeplace+=2;
		list.set(showIndex, removedObject);

		int currentIndex = 0;
		while (currentIndex < _heap.size() -1) {
			int leftChildIndex = 2 * currentIndex + 1;
			int rightChildIndex = 2 * currentIndex + 2;

			if (leftChildIndex >= _heap.size())
				break; 
			
			int maxIndex = leftChildIndex;
			if (rightChildIndex < _heap.size()) {
				repaint();
				Thread.sleep(temps_animation);
				nombreSleep++;
				if (_heap.get(maxIndex).compareTo(_heap.get(rightChildIndex)) < 0) {
					maxIndex = rightChildIndex;
				}
			}
			if (_heap.get(currentIndex).compareTo(_heap.get(maxIndex)) < 0) {
				Integer temp = _heap.get(maxIndex);
				_heap.set(maxIndex, _heap.get(currentIndex));
				_heap.set(currentIndex, temp);
				nombreValeurDeplace+=2;
				currentIndex = maxIndex;
				_barreRouge=currentIndex;
				repaint();
				Thread.sleep(temps_animation);
				nombreSleep++;
			} else {
				break;
			}
		}
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int columnWidth = (getWidth() - 4 * BORDER_WIDTH) / taille;
		int columnHeight = (getHeight() - 4 * BORDER_WIDTH) / valeurmax;

		if (SIGNAL_FIN == -1) {

			Font timerFont = new Font(Font.MONOSPACED, Font.BOLD, 20);
			FontMetrics timerFontMetrix = getFontMetrics(timerFont);
			DecimalFormat df=new DecimalFormat();
			df.setMinimumFractionDigits ( 10 ) ;
			df.setDecimalSeparatorAlwaysShown ( true ) ; 
			g.setFont(timerFont);
			g.setFont(timerFont);
			int x = (getWidth() - timerFontMetrix
					.stringWidth(("Temps de Tri:" + tempsTri
							+ " Nombre de valeur deplace " + nombreValeurDeplace))) / 2;
			int y = (getHeight() - timerFontMetrix.getLeading()) / 2;
			g.drawString("Temps de Tri " + df.format(tempsTri)+ " Nombre de valeur deplace dans le tas " + nombreValeurDeplace, x, y);

		} else {
			for (int i = _heap.size(); i < list.size(); i++) {
				g.setColor(Color.WHITE);
				g.fillRect(2 * BORDER_WIDTH + columnWidth * i, getHeight()
						- list.get(i) * columnHeight - 2 * BORDER_WIDTH,
						columnWidth, list.get(i) * columnHeight);
				g.drawRect(2 * BORDER_WIDTH + columnWidth * i, getHeight()
						- list.get(i) * columnHeight - 2 * BORDER_WIDTH,
						columnWidth, list.get(i) * columnHeight);
			}
			for (int i = 0; i < _heap.size(); i++) {
				g.setColor(Color.orange);
				g.fillRect(2 * BORDER_WIDTH + columnWidth * i, getHeight()
						- _heap.get(i) * columnHeight - 2 * BORDER_WIDTH,
						columnWidth, _heap.get(i) * columnHeight);

				g.drawRect(2 * BORDER_WIDTH + columnWidth * i, getHeight()
						- _heap.get(i) * columnHeight - 2 * BORDER_WIDTH,
						columnWidth, _heap.get(i) * columnHeight);
			}

			if (_barreVerte != -1) {
				for (int i = _barreVerte; i < taille; i++) {
					g.setColor(Color.GREEN);
					g.fillRect(2 * BORDER_WIDTH + columnWidth * i, getHeight()
							- list.get(i) * columnHeight - 2 * BORDER_WIDTH,
							columnWidth, list.get(i) * columnHeight);
					g.drawRect(2 * BORDER_WIDTH + columnWidth * i, getHeight()
							- list.get(i) * columnHeight - 2 * BORDER_WIDTH,
							columnWidth, list.get(i) * columnHeight);
				}
			}
			if (_barreRouge != -1 && _barreRouge < _heap.size() ) {
				
				g.setColor(Color.RED);
				g.fillRect(2 * BORDER_WIDTH + columnWidth * _barreRouge,
						getHeight() - _heap.get(_barreRouge) * columnHeight - 2
								* BORDER_WIDTH, columnWidth,
						_heap.get(_barreRouge) * columnHeight);
				g.drawRect(2 * BORDER_WIDTH + columnWidth * _barreRouge,
						getHeight() - _heap.get(_barreRouge) * columnHeight - 2
								* BORDER_WIDTH, columnWidth,
						_heap.get(_barreRouge) * columnHeight);
			}
		}
	}

	@Override
	public void run() {
		try {
			long tempsFin = 0;
			long tempsDebut = System.currentTimeMillis();
			for (int i = 0; i < list.size(); i++) { // Ajouter un element a la
													// pile
				ajouter(list.get(i));
				repaint();
				Thread.sleep(temps_animation);
				nombreSleep++;
			}
			_barreVerte = list.size();
			for (int i = list.size() - 1; i >= 0; i--) { // Supprimer les
															// elements de la
				_barreVerte=i;// pile
				enlever(i);
				repaint();
				Thread.sleep(temps_animation);
				nombreSleep++;
			}
			tempsFin = System.currentTimeMillis();
			tempsTri=( Math.abs( tempsDebut - tempsFin) -(nombreSleep*temps_animation))
					/ 1000F;

			SIGNAL_FIN = -1;
			_barreVerte++;
			_barreRouge = -1;
		} catch (InterruptedException e) {
		}
		repaint();
	}

}
