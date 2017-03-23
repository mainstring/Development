import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;

import javax.swing.JComponent;

public class drawingBoard extends JComponent
	{
		int grid[][];
		Color[] col= new Color[6];
		int x1=-1,y1=-1, x2=-1, y2=-1;
		boolean paintable=false, flag1=false, flag2=false;
		
		public drawingBoard()
		{
			grid= new int[8][8];
			
			addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e)
				{
					System.out.println(paintable);
					System.out.println(flag1);
					if(paintable)
					{
						if(flag1)
						{
							x1= e.getY();
							y1= e.getX();
							flag1=false;
							flag2=true;
						}
						else if(flag2)
						{
							x2= e.getY();
							y2= e.getX();
							paintable= false;
							flag2=false;
						}
					}
					System.out.println(x1 + " " + y1 + " " + x2 + " " + y2);
					System.out.println(flag1 + " " + flag2);
				}
				
				
				public void mouseReleased(MouseEvent e)
				{
					
				}
			});
			
			addMouseMotionListener(new MouseMotionAdapter() {
				
				@Override
				public void mouseDragged(MouseEvent e) {
					// TODO Auto-generated method stub
				
				}
			});
		}
		
		
		
		public void paint(Graphics g)
		{
			Graphics2D graphSettings= (Graphics2D)g;
			graphSettings.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			//graphSettings.setStroke(new BasicStroke(2));
			
			graphSettings.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
			graphSettings.setColor(Color.white);
			graphSettings.fill(new Rectangle(0, 0, 1200, 700));
			
			graphSettings.setColor(new Color(80,80,80));
			for(int i=0; i<8; i++)
			{
				for(int j=i%2; j<8; j+=2)
				{
					graphSettings.fill(new Rectangle(i*70,j*70,70,70));
				}
			}
			
			for(int i=0; i<8; i++)
			{
				for(int j=0; j<8; j++)
				{
					if(grid[i][j]>0)
					{
						graphSettings.setColor(new Color(150,0,0));
						graphSettings.fill(new Ellipse2D.Double(j*70, i*70, 70, 70));
						if(grid[i][j]==2)
						{
							graphSettings.setColor(new Color(200,0,0));
							graphSettings.fill(new Ellipse2D.Double(j*70 +20, i*70+20, 30, 30));
						}
					}
					else if(grid[i][j]<0)
					{
						graphSettings.setColor(new Color(0,0,150));
						graphSettings.fill(new Ellipse2D.Double(j*70, i*70, 70, 70));
						if(grid[i][j]==-2)
						{
							graphSettings.setColor(new Color(0,0,200));
							graphSettings.fill(new Ellipse2D.Double(j*70 +20, i*70+20, 30, 30));
						}
					}
				}
			}
			
			
			//graphSettings.draw(new Line2D.Float(21.50f, 132.50f, 459.50f, 132.50f));
			

		}
	}
