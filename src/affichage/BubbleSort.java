package affichage;

import java.awt.Font;
import java.awt.FontMetrics;
import java.text.DecimalFormat;

public class BubbleSort extends TriPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int barreRouge=-2;
	private int barreVerte=-2;
	
	private int nombreSleep=0;

	public BubbleSort(String nom, int temps_animation, double largeur,
			double longeur) {
		super(nom, temps_animation);
		// TODO Auto-generated constructor stub
		
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		barreVerte=-2;
		barreRouge=-2;
		tempsTri=0;
		nombreValeurDeplace=0;
	}

	protected void paintComponent(java.awt.Graphics g){
		super.paintComponent(g);
	
		int columnWidth = (getWidth() - 4 * BORDER_WIDTH) / taille;
		int columnHeight = (getHeight() - 4 * BORDER_WIDTH) / valeurmax;
		if(columnHeight == 0 || columnWidth == 0) System.out.println("La");
		if(SIGNAL_FIN == -1){
			
			Font timerFont=new Font(Font.MONOSPACED,Font.BOLD,20);
			FontMetrics timerFontMetrix=getFontMetrics(timerFont);
			DecimalFormat df=new DecimalFormat();
			df.setMinimumFractionDigits ( 10 ) ;
			df.setDecimalSeparatorAlwaysShown ( true ) ; 
			g.setFont(timerFont);
			int x=(getWidth() - timerFontMetrix.stringWidth(("Temps de Tri:"+tempsTri+" Nombre de valeur deplace "+nombreValeurDeplace)))/2;
			int y=(getHeight() - timerFontMetrix.getLeading())/2;
			g.drawString("Temps de Tri "+df.format(tempsTri)+" Nombre de valeur deplace "+nombreValeurDeplace,x,y);
			SIGNAL_FIN=0;
		}
		else{
			
		
		for (int i = 0; i < (barreVerte == -2 ? taille : barreVerte); i++) {
			g.setColor(java.awt.Color.WHITE);
			
			g.fillRect(2 * BORDER_WIDTH + columnWidth * i, getHeight() - list.get(i)
					* columnHeight - 2 * BORDER_WIDTH, columnWidth, list.get(i) * columnHeight);
			g.drawRect(2 * BORDER_WIDTH + columnWidth * i, getHeight() - list.get(i) 
					* columnHeight - 2 * BORDER_WIDTH, columnWidth, list.get(i) * columnHeight);			
		}
		if(barreVerte != -2) {
			for (int i = barreVerte; i < taille; i++) {
				g.setColor(java.awt.Color.GREEN);
				g.fillRect(2 * BORDER_WIDTH + columnWidth * i, getHeight() - list.get(i)
						* columnHeight - 2 * BORDER_WIDTH, columnWidth, list.get(i) * columnHeight);
				g.drawRect(2 * BORDER_WIDTH + columnWidth * i, getHeight() - list.get(i) 
						* columnHeight - 2 * BORDER_WIDTH, columnWidth, list.get(i) * columnHeight);			
			}
		}
		if(barreRouge != -2) {
			g.setColor(java.awt.Color.RED);
			g.fillRect(2 * BORDER_WIDTH + columnWidth * barreRouge, getHeight() - list.get(barreRouge)
					* columnHeight - 2 * BORDER_WIDTH, columnWidth, list.get(barreRouge) * columnHeight);
			g.drawRect(2 * BORDER_WIDTH + columnWidth * barreRouge, getHeight() - list.get(barreRouge) 
					* columnHeight - 2 * BORDER_WIDTH, columnWidth, list.get(barreRouge) * columnHeight);
		}
	}}
	@Override
	public void run() {
	try {
			nombreSleep=0;
			int swap;
			long tempsFin=0;
			long tempsDebut = System.currentTimeMillis();
			boolean listedejatrie=false;
			for (int c = 1; c < taille  && !listedejatrie; c++) {
			//	listedejatrie=true;
				for (int d = 0; d < taille - c; d++) {
					barreRouge = d;
					repaint();
					Thread.sleep(temps_animation);
					nombreSleep++;
					if (list.get(d) > list.get(d + 1)) 
					{
						barreRouge=d+1;
						nombreValeurDeplace+=2;
						swap = list.get(d);
						list.set(d, list.get(d + 1));
						list.set(d + 1, swap);
						repaint();
						Thread.sleep(temps_animation);
						nombreSleep++;
					}

				}
				barreVerte = list.size() - c;
				repaint();
				nombreSleep++;
				Thread.sleep(temps_animation);
			}

			tempsFin= System.currentTimeMillis();
			tempsTri=( Math.abs( tempsDebut - tempsFin) -(nombreSleep*temps_animation))
					/1000F;
			
			SIGNAL_FIN=-1;
			repaint();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

	}
	
}
