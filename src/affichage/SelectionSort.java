package affichage;

import java.awt.Font;
import java.awt.FontMetrics;
import java.text.DecimalFormat;

public class SelectionSort extends TriPanel
{
    private int barreRouge  = -2;
    private int barreVerte  = -2;
    private int barreBleue  = -2;
    private int nombreSleep = 0;

    public SelectionSort(String nom, int temps_animation, double largeur,
	    double longeur)
    {
	super( nom , temps_animation );
	// TODO Auto-generated constructor stub
	SIGNAL_FIN = 0;
    }

    @Override
    public void clear()
    {
	// TODO Auto-generated method stub
	barreVerte = -2;
	barreRouge = -2;
	barreBleue = -2;
	tempsTri = 0;
	nombreValeurDeplace = 0;
    }

    protected void paintComponent(java.awt.Graphics g)
    {
	super.paintComponent( g );
	try
	{
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
		        + " Nombre de valeur deplace " + nombreValeurDeplace ,
		        x , y );
		SIGNAL_FIN = 0;
	    }
	    else
	    {
		for (int i = ( barreVerte == -2 ? 0 : barreVerte ); i < taille; i++)
		{
		    g.setColor( java.awt.Color.WHITE );
		    g.fillRect( 2 * BORDER_WIDTH + columnWidth * i ,
			    getHeight() - list.get( i ) * columnHeight - 2
			            * BORDER_WIDTH , columnWidth , list.get( i )
			            * columnHeight );
		    g.drawRect( 2 * BORDER_WIDTH + columnWidth * i ,
			    getHeight() - list.get( i ) * columnHeight - 2
			            * BORDER_WIDTH , columnWidth , list.get( i )
			            * columnHeight );
		}
		for (int i = 0; i <= barreVerte; i++)
		{
		    g.setColor( java.awt.Color.GREEN );
		    g.fillRect( 2 * BORDER_WIDTH + columnWidth * i ,
			    getHeight() - list.get( i ) * columnHeight - 2
			            * BORDER_WIDTH , columnWidth , list.get( i )
			            * columnHeight );
		    g.drawRect( 2 * BORDER_WIDTH + columnWidth * i ,
			    getHeight() - list.get( i ) * columnHeight - 2
			            * BORDER_WIDTH , columnWidth , list.get( i )
			            * columnHeight );
		}
		if (barreRouge != -2)
		{
		    g.setColor( java.awt.Color.RED );
		    g.fillRect( 2 * BORDER_WIDTH + columnWidth * barreRouge ,
			    getHeight() - list.get( barreRouge ) * columnHeight
			            - 2 * BORDER_WIDTH , columnWidth ,
			    list.get( barreRouge ) * columnHeight );
		    g.drawRect( 2 * BORDER_WIDTH + columnWidth * barreRouge ,
			    getHeight() - list.get( barreRouge ) * columnHeight
			            - 2 * BORDER_WIDTH , columnWidth ,
			    list.get( barreRouge ) * columnHeight );
		}
		if (barreBleue != -2)
		{
		    g.setColor( java.awt.Color.CYAN );
		    g.fillRect( 2 * BORDER_WIDTH + columnWidth * barreBleue ,
			    getHeight() - list.get( barreBleue ) * columnHeight
			            - 2 * BORDER_WIDTH , columnWidth ,
			    list.get( barreBleue ) * columnHeight );
		    g.drawRect( 2 * BORDER_WIDTH + columnWidth * barreBleue ,
			    getHeight() - list.get( barreBleue ) * columnHeight
			            - 2 * BORDER_WIDTH , columnWidth ,
			    list.get( barreBleue ) * columnHeight );
		}
	    }

	}
	catch (ArrayIndexOutOfBoundsException e)
	{

	}
    }

    @Override
    public void run()
    {
	// TODO Auto-generated method stub
	try
	{
	    long tempsDebut = System.currentTimeMillis();
	    for (int i = 0; i < taille - 1; i++)
	    {
		int currentMinIndex = i;
		barreRouge = currentMinIndex;
		for (int j = i + 1; j < taille; j++)
		{
		    barreBleue = j;
		    repaint();

		    Thread.sleep( temps_animation );
		    nombreSleep++;
		    if (list.get( currentMinIndex ) > list.get( j ))
		    {
			currentMinIndex = j;
			barreRouge = currentMinIndex;
			repaint();
		    }
		}

		if (currentMinIndex != i)
		{
		    int tmp = list.get( currentMinIndex );
		    list.set( currentMinIndex , list.get( i ) );
		    list.set( i , tmp );
		    nombreValeurDeplace += 2;

		    repaint();
		    Thread.sleep( temps_animation );
		    nombreSleep++;
		}
		if (barreVerte == -2) barreVerte += 2;
		else
		    barreVerte++;
		repaint();
	    }
	    long tempsFin = System.currentTimeMillis();
	    tempsTri = ( Math.abs( tempsDebut - tempsFin ) - ( nombreSleep * temps_animation ) ) / 1000F;

	    SIGNAL_FIN = -1;
	    repaint();

	}
	catch (InterruptedException e)
	{
	}

    }
}
