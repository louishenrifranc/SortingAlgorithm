package affichage;

import java.awt.Color;
import java.util.Vector;

import javax.swing.JPanel;

public abstract class TriPanel extends JPanel implements Runnable
{

    /**
	 * 
	 */
    private static final long  serialVersionUID = 3226827551480901298L;
    protected static final int BORDER_WIDTH     = 10;
    private String             nom;
    protected int              temps_animation;
    private double             largeur;
    private double             longeur;
    protected Vector<Integer>  list;
    protected int              taille;
    protected int              valeurmax;
    protected int              valeurmin;
    protected int              SIGNAL_FIN;

    protected float            tempsTri;
    protected int              nombreValeurDeplace;

    public TriPanel(String nom, int temps_animation)
    {
	super();
	this.nom = nom;
	this.temps_animation = temps_animation;
	setBackground( java.awt.Color.BLUE );

    }

    public abstract void clear();

    protected void paintComponent(java.awt.Graphics g)
    {
	super.paintComponent( g );

	g.setColor( Color.WHITE );
	g.drawRect( BORDER_WIDTH , BORDER_WIDTH ,
	        getWidth() - 2 * BORDER_WIDTH , getHeight() - 2 * BORDER_WIDTH );

	java.awt.Font nameFont = new java.awt.Font( java.awt.Font.MONOSPACED ,
	        java.awt.Font.BOLD , 18 );
	java.awt.FontMetrics nameFontMetrix = getFontMetrics( nameFont );

	g.setColor( Color.BLUE );
	g.fillRect( ( getWidth() - nameFontMetrix.stringWidth( nom ) ) / 2 , 0 ,
	        nameFontMetrix.stringWidth( nom ) , BORDER_WIDTH
	                + nameFontMetrix.getAscent() / 3 );
	g.setColor( Color.WHITE );
	g.setFont( nameFont );
	g.drawString( nom ,
	        ( getWidth() - nameFontMetrix.stringWidth( nom ) ) / 2 ,
	        BORDER_WIDTH + nameFontMetrix.getAscent() / 3 );

    }

    @Override
    public abstract void run();

    public void setList(Vector<Integer> list, int valeurmax, int valeurmin)
    {
	clear();
	this.taille = list.size();
	this.list = list;
	this.valeurmax = valeurmax;
	this.valeurmin = valeurmin;
    }

}
