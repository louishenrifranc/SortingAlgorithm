package affichage;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.text.DecimalFormat;

public class QuickSort extends TriPanel
{
    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    private int               barreRouge       = -2;
    private int               barreVerte       = -2;
    private int               barreOrange      = -2;
    private int               barreJaune       = -2;
    private int               nombreSleep      = 0;

    public QuickSort(String nom, int temps_animation, double largeur,
	    double longeur)
    {
	super( nom , temps_animation );
	SIGNAL_FIN = 0;
    }

    @Override
    public void clear()
    {
	// TODO Auto-generated method stub
	barreVerte = -2;
	barreRouge = -2;
	barreOrange = -2;
	barreJaune = -2;
	tempsTri = 0;
	nombreValeurDeplace = 0;
    }

    protected void paintComponent(java.awt.Graphics g)
    {
	super.paintComponent( g );

	int columnWidth = ( getWidth() - 4 * BORDER_WIDTH ) / taille;
	int columnHeight = ( getHeight() - 4 * BORDER_WIDTH ) / valeurmax;
	if (SIGNAL_FIN == -1)
	{

	    Font timerFont = new Font( Font.MONOSPACED , Font.BOLD , 20 );
	    FontMetrics timerFontMetrix = getFontMetrics( timerFont );
	    DecimalFormat df = new DecimalFormat();
	    df.setMinimumFractionDigits( 10 );
	    df.setDecimalSeparatorAlwaysShown( true );
	    g.setFont( timerFont );
	    g.setFont( timerFont );
	    int x = ( getWidth() - timerFontMetrix
		    .stringWidth( ( "Temps de Tri:" + tempsTri
		            + " Nombre de valeur deplace " + nombreValeurDeplace ) ) ) / 2;
	    int y = ( getHeight() - timerFontMetrix.getLeading() ) / 2;
	    g.drawString( "Temps de Tri " + df.format( tempsTri )
		    + " Nombre de valeur deplace " + nombreValeurDeplace , x ,
		    y );
	}
	else
	{

	    for (int i = 0; i < ( barreVerte == -2 ? taille : barreVerte ); i++)
	    {
		g.setColor( java.awt.Color.GREEN );

		g.fillRect( 2 * BORDER_WIDTH + columnWidth * i , getHeight()
		        - list.get( i ) * columnHeight - 2 * BORDER_WIDTH ,
		        columnWidth , list.get( i ) * columnHeight );
		g.drawRect( 2 * BORDER_WIDTH + columnWidth * i , getHeight()
		        - list.get( i ) * columnHeight - 2 * BORDER_WIDTH ,
		        columnWidth , list.get( i ) * columnHeight );
	    }
	    if (barreVerte != -2)
	    {
		for (int i = barreVerte; i < taille; i++)
		{
		    g.setColor( java.awt.Color.white );
		    g.fillRect( 2 * BORDER_WIDTH + columnWidth * i ,
			    getHeight() - list.get( i ) * columnHeight - 2
			            * BORDER_WIDTH , columnWidth , list.get( i )
			            * columnHeight );
		    g.drawRect( 2 * BORDER_WIDTH + columnWidth * i ,
			    getHeight() - list.get( i ) * columnHeight - 2
			            * BORDER_WIDTH , columnWidth , list.get( i )
			            * columnHeight );
		}
	    }
	    if (barreRouge != -2)
	    {
		g.setColor( java.awt.Color.RED );
		g.fillRect( 2 * BORDER_WIDTH + columnWidth * barreRouge ,
		        getHeight() - list.get( barreRouge ) * columnHeight - 2
		                * BORDER_WIDTH , columnWidth ,
		        list.get( barreRouge ) * columnHeight );
		g.drawRect( 2 * BORDER_WIDTH + columnWidth * barreRouge ,
		        getHeight() - list.get( barreRouge ) * columnHeight - 2
		                * BORDER_WIDTH , columnWidth ,
		        list.get( barreRouge ) * columnHeight );
	    }
	    if (barreOrange != -2)
	    {
		g.setColor( Color.ORANGE );
		g.fillRect( 2 * BORDER_WIDTH + columnWidth * barreOrange ,
		        getHeight() - list.get( barreOrange ) * columnHeight
		                - 2 * BORDER_WIDTH , columnWidth ,
		        list.get( barreOrange ) * columnHeight );
		g.drawRect( 2 * BORDER_WIDTH + columnWidth * barreOrange ,
		        getHeight() - list.get( barreOrange ) * columnHeight
		                - 2 * BORDER_WIDTH , columnWidth ,
		        list.get( barreOrange ) * columnHeight );
	    }
	    if (barreJaune != -2)
	    {
		g.setColor( Color.YELLOW );
		g.fillRect( 2 * BORDER_WIDTH + columnWidth * barreJaune ,
		        getHeight() - list.get( barreJaune ) * columnHeight - 2
		                * BORDER_WIDTH , columnWidth ,
		        list.get( barreJaune ) * columnHeight );
		g.drawRect( 2 * BORDER_WIDTH + columnWidth * barreJaune ,
		        getHeight() - list.get( barreJaune ) * columnHeight - 2
		                * BORDER_WIDTH , columnWidth ,
		        list.get( barreJaune ) * columnHeight );
	    }

	}
    }

    private void quicksort(int low, int high) throws InterruptedException
    {
	int i = low;
	int j = high;
	Thread.sleep( temps_animation );
	nombreSleep++;
	repaint();
	int pivot = list.get( low + ( high - low ) / 2 );
	barreRouge = low + ( high - low ) / 2;
	while (i <= j)
	{
	    while (list.get( i ) < pivot)
	    {
		i++;
		barreOrange = i;
		Thread.sleep( temps_animation );
		nombreSleep++;
		repaint();
	    }
	    while (list.get( j ) > pivot)
	    {
		j--;
		barreJaune = j;
		Thread.sleep( temps_animation );
		nombreSleep++;
		repaint();
	    }

	    if (i <= j)
	    {
		int temp = list.get( i );
		list.set( i , list.get( j ) );
		list.set( j , temp );
		nombreValeurDeplace += 2;
		if (i == barreRouge)
		{
		    barreRouge = j;
		}
		else if (j == barreRouge)
		{
		    barreRouge = i;
		}
		Thread.sleep( temps_animation );
		nombreSleep++;
		repaint();
		i++;
		j--;
	    }
	}
	if (low > barreVerte)
	{
	    barreVerte = low;
	}
	if (low < j)
	{
	    quicksort( low , j );
	}
	if (i < high)
	{
	    quicksort( i , high );
	}

	repaint();
    }

    @Override
    public void run()
    {
	try
	{
	    long tempsFin = 0;
	    long tempsDebut = System.currentTimeMillis();
	    quicksort( 0 , list.size() - 1 );
	    tempsFin = System.currentTimeMillis();
	    SIGNAL_FIN = -1;
	    tempsTri = ( Math.abs( tempsDebut - tempsFin ) - ( nombreSleep * temps_animation ) ) / 1000F;
	    repaint();
	    barreVerte = -2;
	    barreRouge = -2;
	    barreOrange = barreJaune = -2;
	}
	catch (InterruptedException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

}
