package statistique;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.math.BigInteger;
import java.util.Random;

import javax.swing.JPanel;

import Constante.Constante;

public class Graphe extends JPanel
{

    private static final int    Largeur           = 1200;                 // dimension
	                                                                   // de
	                                                                   // la
	                                                                   // fenetre
    private static int          MaxTemps;
    private static int          MaxNbrDep;
    private static int          MaxNbrVal;
    private static final int    Hauteur           = 650;
    private static final int    BORDER_GAP        = 30;                   // La
	                                                                   // bordure
    private static final Color  GRAPH_COLOR       = Color.green;
    private static final Color  AXE_COLOR         = Color.WHITE;
    private static final Color  GRAPH_POINT_COLOR = Color.blue;
    private static final Stroke GRAPH_STROKE      = new BasicStroke( 3f );
    private static final int    GRAPH_POINT_WIDTH = 5;
    private static final int    Y_HATCH_CNT       = 10;
    private static String       legendeY;
    private static String       legendeX;
    private Long                m_matrice[][];
    private int                 HScol , QScol , SScol , BScol , X;
    private Long                maxX , maxY , minY;
    private BigInteger          biginteger[][];
    private double              yScale , xScale;
    private int                 largeur , longueur;
    private boolean             m_titre;

    public Graphe(BigInteger matrice[][], int longueur, int largeur,
	    int codeAlgo, boolean moduler)
    {
	X = 0;
	this.largeur = largeur;
	this.longueur = longueur;
	m_matrice = new Long[longueur][largeur];
	biginteger = new BigInteger[longueur][largeur];
	biginteger = matrice;
	m_titre = moduler;

	HScol = -1;
	BScol = -1;
	QScol = -1;
	SScol = -1;
	maxX = Long.MIN_VALUE;
	maxY = Long.MIN_VALUE;
	minY = Long.MAX_VALUE;
	// System.out.println(longueur+" "+largeur);
	for (int i = 1; i < longueur; i++)
	{

	    for (int j = 0; j < largeur; j++)
	    {
		if (j == 0)
		{
		    if (matrice[i][0].intValue() == Constante.codeBSshort) BScol = i - 1;
		    else if (matrice[i][0].intValue() == Constante.codeHSshort) HScol = i - 1;
		    else if (matrice[i][0].intValue() == Constante.codeQSshort) QScol = i - 1;
		    else if (matrice[i][0].intValue() == Constante.codeSSshort) SScol = i - 1;
		}
		else
		{
		    if (moduler && j > 0) m_matrice[i - 1][j - 1] = ( Long ) matrice[i][j]
			    .divide( new BigInteger( "100000" ) ).longValue();
		    else
			m_matrice[i - 1][j - 1] = ( Long ) matrice[i][j]
			        .longValue();
		    if (maxY < m_matrice[i - 1][j - 1])
		    {
			maxY = m_matrice[i - 1][j - 1];
		    }
		    if (minY > m_matrice[i - 1][j - 1])
		    {
			minY = m_matrice[i - 1][j - 1];
		    }
		}
	    }
	}
	legendeX = "nombre de valeurs";
	if (moduler) legendeY = "temps (10e-4 sec)";
	else if (!moduler) legendeY = "nombre de valeurs déplacés";

	// for(int i=0;i<longueur-1;i++)
	// {
	// for(int j=0;j<largeur-1;j++)
	// {
	// System.out.print(m_matrice[i][j]+" ");
	// }
	// System.out.println();
	// }
	this.setBackground( Color.DARK_GRAY );

    }

    @Override
    protected void paintComponent(Graphics g)
    {
	super.paintComponent( g );
	Graphics2D g2 = ( Graphics2D ) g;
	g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING ,
	        RenderingHints.VALUE_ANTIALIAS_ON );

	// L'ecart entre les points
	xScale = ( double ) ( ( double ) getWidth() - 2 * BORDER_GAP )
	        / biginteger[X][largeur - 1].floatValue();
	yScale = ( double ) ( ( ( double ) getHeight() - 2 * BORDER_GAP ) / ( maxY - minY ) );
	// System.out.println(xScale+" :xScale +yScale: "+yScale+"\n" );
	// System.out.println(getHeight()-2*BORDER_GAP);

	g2.setColor( AXE_COLOR );

	// creer axe x et y
	g2.drawLine( BORDER_GAP , getHeight() - BORDER_GAP , BORDER_GAP ,
	        BORDER_GAP );
	g2.drawLine( BORDER_GAP , getHeight() - BORDER_GAP , getWidth()
	        - BORDER_GAP , getHeight() - BORDER_GAP );

	// create hatch marks for y axis.
	for (int i = 0; i < Y_HATCH_CNT; i++)
	{
	    int x0 = BORDER_GAP;
	    int x1 = GRAPH_POINT_WIDTH + BORDER_GAP;
	    int y0 = getHeight()
		    - ( ( ( i + 1 ) * ( getHeight() - BORDER_GAP * 2 ) )
		            / Y_HATCH_CNT + BORDER_GAP );
	    int y1 = y0;

	    Long x2 = ( ( maxY - minY ) * i / ( Y_HATCH_CNT - 1 ) ) + minY;
	    g2.drawString( Long.toString( x2 ) , x0 - BORDER_GAP + 2 , y0
		    + BORDER_GAP  );
	}

	// and for x axis
	for (int i = 1; i < largeur; i++)
	{
	    int x0 = ( int ) ( ( xScale ) * biginteger[X][i].intValue() + BORDER_GAP );
	    int x1 = x0;
	    int y0 = getHeight() - BORDER_GAP;
	    int y1 = y0 - GRAPH_POINT_WIDTH;
	    g2.drawLine( x0 , y0 , x1 , y1 );
	    g2.drawLine( x0 , y0 , x1 , y1 );

	    g2.drawString( biginteger[X][i].toString() , x0 - 10 , getHeight()
		    - BORDER_GAP / 3 );
	}
	int nbre = 0;
	if (HScol != -1) tracerPoints( Constante.codeHSshort , HScol , g2 ,
	        nbre++ );
	if (SScol != -1) tracerPoints( Constante.codeSSshort , SScol , g2 ,
	        nbre++ );
	if (BScol != -1) tracerPoints( Constante.codeBSshort , BScol , g2 ,
	        nbre++ );
	if (QScol != -1) tracerPoints( Constante.codeQSshort , QScol , g2 ,
	        nbre++ );

	String titre = new String();
	if (m_titre == true)
	{
	    titre = "Temps des différents algorithmes";
	}
	else
	{
	    titre = "Nombre de valeurs déplacés";
	}
	g2.setColor( Color.WHITE );
	g2.setFont( new Font( "default" , Font.BOLD , 16 ) );
	g2.drawString( titre , getWidth() / 2 - titre.length() / 2 ,
	        3 * BORDER_GAP );

	g2.setFont( new Font( "Calisto MT" , Font.BOLD , 10 ) );
	g2.drawString( legendeX , getWidth() - BORDER_GAP - legendeX.length()
	        * 4 , getHeight() - BORDER_GAP );
	g2.drawString( legendeY , BORDER_GAP , BORDER_GAP );

    }

    void tracerPoints(int code, int ligne, Graphics2D g2, int nbre)
    {
	// System.out.println("Appelé "+code+" "+ligne);
	Random r = new Random();
	Stroke oldStroke = g2.getStroke();
	Color color = new Color( r.nextInt( 256 ) , r.nextInt( 256 ) ,
	        r.nextInt( 256 ) , r.nextInt( 256 ) );
	g2.setColor( color );
	g2.setStroke( GRAPH_STROKE );

	g2.setColor( color );
	g2.drawLine( 2 * BORDER_GAP , ( 3 + nbre ) * BORDER_GAP ,
	        4 * BORDER_GAP , ( 3 + nbre ) * BORDER_GAP );
	g2.drawString( Constante.algorithme[code] , 5 * BORDER_GAP ,
	        ( 3 + nbre ) * BORDER_GAP );

	for (int i = 0; i < largeur - 2; i++)
	{
	    // System.out.print(" "+m_matrice[ligne][i]);
	    int y1 = ( int ) ( getHeight() - 2* BORDER_GAP - m_matrice[ligne][i]
		    * yScale );
	    int x1 = ( int ) ( BORDER_GAP + biginteger[X][i + 1].intValue()
		    * xScale );
	    if (i == 0) g2.drawLine( BORDER_GAP , getHeight() - BORDER_GAP ,
		    x1 , y1 );
	    int y2 = ( int ) ( getHeight() - 2*BORDER_GAP - m_matrice[ligne][i + 1]
		    * yScale );
	    int x2 = ( int ) ( BORDER_GAP + biginteger[X][i + 2].intValue()
		    * xScale );
	    g2.drawLine( x1 , y1 , x2 , y2 );
	}

	g2.setStroke( oldStroke );
	g2.setColor( GRAPH_POINT_COLOR );
	for (int i = 0; i < largeur - 1; i++)
	{
	    int x = ( int ) ( BORDER_GAP + biginteger[X][i + 1].intValue()
		    * xScale - GRAPH_POINT_WIDTH / 2 );
	    int y = ( int ) ( getHeight() - 2* BORDER_GAP - m_matrice[ligne][i]
		    * yScale - GRAPH_POINT_WIDTH / 2 );
	    int ovalW = GRAPH_POINT_WIDTH;
	    int ovalH = GRAPH_POINT_WIDTH;
	    g2.fillOval( x , y , ovalW , ovalH );
	}
    }

}