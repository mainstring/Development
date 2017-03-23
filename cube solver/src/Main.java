import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class Main {
	
	static JFrame jf= new JFrame("watch out!!");

	static StringTokenizer st;
	static BufferedReader br;
	static PrintWriter pw;
	private static String next() throws IOException {
		while (st==null || !st.hasMoreTokens())
			st = new StringTokenizer(br.readLine());
		return st.nextToken();
	}
	
	static int[][] move_ara= {{5,6}, {11,12}, {1,2}, {9,10}, {3,4}, {7,8}};
	static int[][] two_side= {{1,37}, {3,10}, {5,28}, {7,19}, {12,41}, {14,21}, {23,30}, {32,39}, {16,48}, {25,46}, {34,50}, {43,52}};
	static int[][] side_by_side= {{4,1,2,3}, {0,4,5,2}, {0,1,5,3}, {0,2,5,4}, {0,3,5,1}, {2,1,4,3}};
	static int[][] side_of_plane= {{0,1,2}, {0,3,6}, {6,7,8}, {8,5,2}};
	static int[][] corners= {{0,0}, {0,2}, {2,0}, {2,2}};
	static int[][] corner_sides_bottom= {{2,1}, {3,2}, {1,4}, {4,3}};
	static int[][] three_side= {{0,38,9}, {2,36,29}, {6,18,11}, {8,20,27}, {45,24,17}, {47,26,33}, {51,15,44}, {53,35,42}};
	static int[][] top_corners= {{1,4}, {3,4}, {1,2}, {2,3}};
	static int debug_mode=0, move_cnt=0;

	static cube qb= new cube();
	static drawingBoard r;

	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		br = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("1");
		
		r= new drawingBoard(qb);
		jf.setSize(1000,700);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
		jf.setAlwaysOnTop(true);
		jf.add(r);
		r.repaint();
		
//		for(int i=0; i<6; i++)
//		{
//			for(int j=0; j<3; j++)
//			{
//				for(int k=0; k<3; k++)
//				{
//					int n= Integer.parseInt(next());
//					qb.grid[i][j][k]=n;
//					r.repaint();
//				}
//			}
//		}
//		r.repaint();
		int n;
		
//		int n= Integer.parseInt(next());

//		move(10);
//		move(1);
//		move(4);
		for(int i=0; i<100; i++)
		{
			int j= (int) (Math.random()*100000.0);
			move((j%12)+1);
		}
		
		n= Integer.parseInt(next());
		solve1();
//		n= Integer.parseInt(next());
		solve2();
//		n= Integer.parseInt(next());
		solve3();
		
		System.out.printf("move no: %d\n", move_cnt-100);
		
//		while(true)
//		{
//			int n = Integer.parseInt(next());
//			move(n);
//		}
	}
	
	static void solve3_helper1()
	{
		move(move_ara[3][1]);
		move(move_ara[0][1]);
		move(move_ara[3][0]);
		move(move_ara[0][1]);
		move(move_ara[3][1]);
		move(move_ara[0][1]);
		move(move_ara[0][1]);
		move(move_ara[3][0]);
		move(move_ara[0][1]);
	}
	
	static void solve3_helper2()
	{
		move(move_ara[2][0]);
		move(move_ara[3][1]);
		move(move_ara[0][1]);
		move(move_ara[3][0]);
		move(move_ara[0][0]);
		move(move_ara[2][1]);
	}
	static void solve3_helper3()
	{
		move(move_ara[0][1]);
		move(move_ara[3][1]);
		move(move_ara[0][0]);
		move(move_ara[1][1]);
		move(move_ara[0][1]);
		move(move_ara[3][0]);
		move(move_ara[0][0]);
		move(move_ara[1][0]);
	}
	static void solve3_helper4()
	{
		move(move_ara[3][0]);
		move(move_ara[5][1]);
		move(move_ara[3][1]);
		move(move_ara[5][0]);
	}
	
	static int detect_top_corner(int x)
	{
		for(int i=0; i<4; i++)
		{
			for(int j=0; j<3; j++)
			{
				if(det(three_side[x][j])== top_corners[i][0])
				{
					for(int k=0; k<3; k++)
					{
						if(det(three_side[x][k])== top_corners[i][1])
						{
							return i;
						}
					}
				}
			}
		}
		return 0;
	}
	
	static void solve3()
	{
		debug_mode=0;
		System.out.println("\nsolving 3");
		if(det(1)!=0 && det(3)!=0 && det(5)!=0 && det(7)!=0)
		{
			solve3_helper2();
		}
		System.out.printf("de %d %d %d %d\n", det(1), det(3), det(5), det(7));
		if((det(1)!=0 || det(7)!=0) && (det(3)!=0 || det(5)!=0))
		{
			System.out.printf("det %d %d\n", det(1), det(3));
			while(det(1)!=0 || det(3)!=0)
			{
				System.out.printf("det %d %d\n", det(1), det(3));
				move(move_ara[0][0]);
			}

			solve3_helper2();
		}
		while(det(3)!=0 || det(5)!=0)
		{
			move(move_ara[0][0]);
		}
		if(det(1)!=0)
		{
			solve3_helper2();
		}
		
		int fl=3, fl2=0;
		debug_mode=0;
		for(int i=0; i<4; i++)
		{
			if(det(10)==1 && det(19)==2 && det(28)==3 && det(37)==4)
			{
				fl=0;
				break;
			}
			if((det(10)==1 && det(28)==3))
			{
				fl=1;
				fl2=1;
				break;
			}
			if((det(19)==2 && det(37)==4))
			{
				fl=1;
				fl2=2;
				break;
			}
			if(det(10)==1 && det(37)==4)
			{
				fl=2;
				fl2=1;
				break;
			}
			if(det(10)==1 && det(19)==2)
			{
				fl=2;
				fl2=2;
				break;
			}
			if(det(19)==2 && det(28)==3)
			{
				fl=2;
				fl2=3;
				break;
			}
			if(det(28)==3 && det(37)==4)
			{
				fl=2;
				fl2=4;
				break;
			}
			move(move_ara[0][0]);
		}
		if(fl==2)
		{
			while(fl2!=4)
			{
				move(move_ara[0][0]);
				fl2++;
			}
			solve3_helper1();
		}
		if(fl==1)
		{
			while(fl2!=2)
			{
				move(move_ara[0][0]);
				fl2++;
			}
			solve3_helper1();
			move(move_ara[0][1]);
			move(move_ara[0][1]);
			solve3_helper1();
		}
		while(det(10)!=1)
		{
			move(move_ara[0][0]);
		}
		
		debug_mode=0;
		if(detect_top_corner(2)==3) {System.out.println("a"); move(move_ara[0][0]); move(move_ara[0][0]); solve3_helper3(); move(move_ara[0][0]); move(move_ara[0][0]);}
		else if(detect_top_corner(1)==3) {System.out.println("b"); move(move_ara[0][0]); move(move_ara[0][0]); solve3_helper3(); solve3_helper3(); move(move_ara[0][0]); move(move_ara[0][0]);}
		else if(detect_top_corner(0)==3) {System.out.println("c"); move(move_ara[0][1]); solve3_helper3(); move(move_ara[0][1]); solve3_helper3(); move(move_ara[0][0]); move(move_ara[0][0]);}
		int x= detect_top_corner(0);
		int y= detect_top_corner(2);
		int z= detect_top_corner(1);
//		x++; y++; z++;
		
		System.out.println("x "+ x+" "+y+" "+z);
//		if(x==0 && y==2 && z==1) return;
		if(x==2 && y==1 && z==0)
		{
			solve3_helper3();
		}
		else if(x!=0 || y!=2 || z!=1)
		{
			solve3_helper3();
			solve3_helper3();
		}
		
		int[] temp={3,1,0,2};
		for(int i=0; i<4; i++)
		{
			System.out.println(det(8));
			while(detect_top_corner(3)!=temp[i] || det(8)!=0)
			{
				solve3_helper4();
			}
			move(move_ara[0][1]);
		}
		while(detect_top_corner(3)!= 3) move(move_ara[0][0]);
	}
	
	static void solve2()
	{
		System.out.println("\nsolving 2");
//		debug_mode=1;
		for(int jj=1; jj<5; jj++)
		{
			int ss=jj+1, i1=0,i2=0,j1=0,j2=0, k1=0,k2=0;
			if(ss>4) ss-=4;
			int ii= detect_side(jj,ss);
			if(ii>11)
			{
				ii-=12;
				i1= two_side[ii][1]/9; j1= (two_side[ii][1]%9)/3; k1= two_side[ii][1]%3;
				i2= two_side[ii][0]/9; j2= (two_side[ii][0]%9)/3; k2= two_side[ii][0]%3;
			}
			else
			{
				i1= two_side[ii][0]/9; j1= (two_side[ii][0]%9)/3; k1= two_side[ii][0]%3;
				i2= two_side[ii][1]/9; j2= (two_side[ii][1]%9)/3; k2= two_side[ii][1]%3;
			}
			
			System.out.printf("%d %d %d\n", jj,i1,i2);
			if(i1==jj && i2==ss)
			{
				continue;
			}
			if(i1>0 && i2>0)
			{
				int mm=1,nn=0;
				if(i1>2) mm=0;
				if(k1==2) 
				{
					mm^=1;
					nn^=1;
				}
				move(move_ara[i1][mm]);
				move(move_ara[0][nn]);
				move(move_ara[i1][mm]);
				move(move_ara[0][nn]);
				move(move_ara[i1][mm]);
				
				move(move_ara[0][nn^1]);
				move(move_ara[i1][mm^1]);
				move(move_ara[0][nn^1]);
				move(move_ara[i1][mm^1]);
				
				ii= detect_side(jj,ss);
				if(ii>11)
				{
					ii-=12;
					i1= two_side[ii][1]/9; j1= (two_side[ii][1]%9)/3; k1= two_side[ii][1]%3;
					i2= two_side[ii][0]/9; j2= (two_side[ii][0]%9)/3; k2= two_side[ii][0]%3;
				}
				else
				{
					i1= two_side[ii][0]/9; j1= (two_side[ii][0]%9)/3; k1= two_side[ii][0]%3;
					i2= two_side[ii][1]/9; j2= (two_side[ii][1]%9)/3; k2= two_side[ii][1]%3;
				}
			}
			if(i1==0)
			{
				while(i2!=ss)
				{
					move(move_ara[0][0]);
					ii= detect_side(jj,ss);
					if(ii>11)
					{
						ii-=12;
						i1= two_side[ii][1]/9; j1= (two_side[ii][1]%9)/3; k1= two_side[ii][1]%3;
						i2= two_side[ii][0]/9; j2= (two_side[ii][0]%9)/3; k2= two_side[ii][0]%3;
					}
					else
					{
						i1= two_side[ii][0]/9; j1= (two_side[ii][0]%9)/3; k1= two_side[ii][0]%3;
						i2= two_side[ii][1]/9; j2= (two_side[ii][1]%9)/3; k2= two_side[ii][1]%3;
					}
				}

				int mm=1,nn=0;
				if(i2>2) mm=0;
				move(move_ara[i2][mm]);
				move(move_ara[0][nn]);
				move(move_ara[i2][mm]);
				move(move_ara[0][nn]);
				move(move_ara[i2][mm]);
				
				move(move_ara[0][nn^1]);
				move(move_ara[i2][mm^1]);
				move(move_ara[0][nn^1]);
				move(move_ara[i2][mm^1]);
				
				ii= detect_side(jj,ss);
				if(ii>11)
				{
					ii-=12;
					i1= two_side[ii][1]/9; j1= (two_side[ii][1]%9)/3; k1= two_side[ii][1]%3;
					i2= two_side[ii][0]/9; j2= (two_side[ii][0]%9)/3; k2= two_side[ii][0]%3;
				}
				else
				{
					i1= two_side[ii][0]/9; j1= (two_side[ii][0]%9)/3; k1= two_side[ii][0]%3;
					i2= two_side[ii][1]/9; j2= (two_side[ii][1]%9)/3; k2= two_side[ii][1]%3;
				}
			}
			if(i2==0)
			{
				while(i1!=jj)
				{
					move(move_ara[0][0]);
					ii= detect_side(jj,ss);
					if(ii>11)
					{
						ii-=12;
						i1= two_side[ii][1]/9; j1= (two_side[ii][1]%9)/3; k1= two_side[ii][1]%3;
						i2= two_side[ii][0]/9; j2= (two_side[ii][0]%9)/3; k2= two_side[ii][0]%3;
					}
					else
					{
						i1= two_side[ii][0]/9; j1= (two_side[ii][0]%9)/3; k1= two_side[ii][0]%3;
						i2= two_side[ii][1]/9; j2= (two_side[ii][1]%9)/3; k2= two_side[ii][1]%3;
					}
				}

				int mm=0,nn=1;
				if(i1>2) mm=1;
				move(move_ara[i1][mm]);
				move(move_ara[0][nn]);
				move(move_ara[i1][mm]);
				move(move_ara[0][nn]);
				move(move_ara[i1][mm]);
				
				move(move_ara[0][nn^1]);
				move(move_ara[i1][mm^1]);
				move(move_ara[0][nn^1]);
				move(move_ara[i1][mm^1]);
				
				ii= detect_side(jj,ss);
				if(ii>11)
				{
					ii-=12;
					i1= two_side[ii][1]/9; j1= (two_side[ii][1]%9)/3; k1= two_side[ii][1]%3;
					i2= two_side[ii][0]/9; j2= (two_side[ii][0]%9)/3; k2= two_side[ii][0]%3;
				}
				else
				{
					i1= two_side[ii][0]/9; j1= (two_side[ii][0]%9)/3; k1= two_side[ii][0]%3;
					i2= two_side[ii][1]/9; j2= (two_side[ii][1]%9)/3; k2= two_side[ii][1]%3;
				}
			}
		}
	}
	
	static void solve1()
	{
		for(int jj=1; jj<5; jj++)
		{
			int ss=jj, i1=0,i2=0,j1=0,j2=0, k1=0,k2=0;
			if(jj==2) ss=0;
			if(jj==4) ss=2;

			int ii= detect_side(5,jj);
			if(ii>11)
			{
				ii-=12;
				i1= two_side[ii][1]/9; j1= (two_side[ii][1]%9)/3; k1= two_side[ii][1]%3;
				i2= two_side[ii][0]/9; j2= (two_side[ii][0]%9)/3; k2= two_side[ii][0]%3;
			}
			else
			{
				i1= two_side[ii][0]/9; j1= (two_side[ii][0]%9)/3; k1= two_side[ii][0]%3;
				i2= two_side[ii][1]/9; j2= (two_side[ii][1]%9)/3; k2= two_side[ii][1]%3;
			}
			
			System.out.printf("%d %d %d\n",jj,i1,i2);
			if(i2==jj && j2==2 && k2==1)
			{
				continue;
			}
			if(i2>0 && i2<5 && j2==2 && k2==1)
			{
				move(move_ara[i2][0]);
				move(move_ara[i2][0]);
				move(move_ara[0][0]);
				move(move_ara[i2][1]);
				move(move_ara[i2][1]);			
				ii= detect_side(5,jj);
				if(ii>11)
				{
					ii-=12;
					i1= two_side[ii][1]/9; j1= (two_side[ii][1]%9)/3; k1= two_side[ii][1]%3;
					i2= two_side[ii][0]/9; j2= (two_side[ii][0]%9)/3; k2= two_side[ii][0]%3;
				}
				else
				{
					i1= two_side[ii][0]/9; j1= (two_side[ii][0]%9)/3; k1= two_side[ii][0]%3;
					i2= two_side[ii][1]/9; j2= (two_side[ii][1]%9)/3; k2= two_side[ii][1]%3;
				}
			}
			if(i1>0 && i1<5 && j1==2 && k1==1)
			{
				move(move_ara[i1][0]);
				move(move_ara[i1][0]);
				move(move_ara[0][0]);
				move(move_ara[i1][1]);
				move(move_ara[i1][1]);			
				ii= detect_side(5,jj);
				if(ii>11)
				{
					ii-=12;
					i1= two_side[ii][1]/9; j1= (two_side[ii][1]%9)/3; k1= two_side[ii][1]%3;
					i2= two_side[ii][0]/9; j2= (two_side[ii][0]%9)/3; k2= two_side[ii][0]%3;
				}
				else
				{
					i1= two_side[ii][0]/9; j1= (two_side[ii][0]%9)/3; k1= two_side[ii][0]%3;
					i2= two_side[ii][1]/9; j2= (two_side[ii][1]%9)/3; k2= two_side[ii][1]%3;
				}
			}
			if(i1>0 && i1<5 && i2>0 && i2<5)
			{
				int tm=0;
				if(i1>2 && (i2==i1-1 || (i2==4 && i1==1))) tm=1;
				if(i1<3 && (i2==i1+1 || (i2==1 && i1==4))) tm=1;
				System.out.printf("tm %d\n", tm);
				move(move_ara[i1][tm]);
				move(move_ara[0][0]);
				move(move_ara[i1][tm^1]);
				ii= detect_side(5,jj);
				if(ii>11)
				{
					ii-=12;
					i1= two_side[ii][1]/9; j1= (two_side[ii][1]%9)/3; k1= two_side[ii][1]%3;
					i2= two_side[ii][0]/9; j2= (two_side[ii][0]%9)/3; k2= two_side[ii][0]%3;
				}
				else
				{
					i1= two_side[ii][0]/9; j1= (two_side[ii][0]%9)/3; k1= two_side[ii][0]%3;
					i2= two_side[ii][1]/9; j2= (two_side[ii][1]%9)/3; k2= two_side[ii][1]%3;
				}
			}
			if(i1==0)
			{
				int i3= jj-1;
				if(i3<1) i3+=4;
				while(i2!=i3)
				{
					move(move_ara[0][0]);
					ii= detect_side(5,jj);
					if(ii>11)
					{
						ii-=12;
						i1= two_side[ii][1]/9; j1= (two_side[ii][1]%9)/3; k1= two_side[ii][1]%3;
						i2= two_side[ii][0]/9; j2= (two_side[ii][0]%9)/3; k2= two_side[ii][0]%3;
					}
					else
					{
						i1= two_side[ii][0]/9; j1= (two_side[ii][0]%9)/3; k1= two_side[ii][0]%3;
						i2= two_side[ii][1]/9; j2= (two_side[ii][1]%9)/3; k2= two_side[ii][1]%3;
					}
				}
				int tm=0;
				if(jj==1 || jj==4) tm=1;
				move(move_ara[side_by_side[jj][1]][tm]);
				move(move_ara[side_by_side[jj][3]][tm]);
				if(jj<3)move(move_ara[jj][0]);
				else move(move_ara[jj][1]);
				move(move_ara[side_by_side[jj][1]][tm^1]);
				move(move_ara[side_by_side[jj][3]][tm^1]);
				i1=jj;
			}
			if(i1>0 && i1<5)
			{
				System.out.println("a");
				int i3= jj-1;
				if(i3<1) i3+=4;
				
				System.out.printf(" %d %d\n",i1,i3);
				while(i1!=i3)
				{
					move(move_ara[0][0]);
					ii= detect_side(5,jj);
					if(ii>11)
					{
						ii-=12;
						i1= two_side[ii][1]/9; j1= (two_side[ii][1]%9)/3; k1= two_side[ii][1]%3;
						i2= two_side[ii][0]/9; j2= (two_side[ii][0]%9)/3; k2= two_side[ii][0]%3;
					}
					else
					{
						i1= two_side[ii][0]/9; j1= (two_side[ii][0]%9)/3; k1= two_side[ii][0]%3;
						i2= two_side[ii][1]/9; j2= (two_side[ii][1]%9)/3; k2= two_side[ii][1]%3;
					}
				}
				
				int tm=0;
				if(jj==1 || jj==4) tm=1;
				move(move_ara[side_by_side[jj][1]][tm]);
				move(move_ara[side_by_side[jj][3]][tm]);
				if(jj<3)move(move_ara[jj][1]);
				else move(move_ara[jj][0]);
				move(move_ara[side_by_side[jj][1]][tm^1]);
				move(move_ara[side_by_side[jj][3]][tm^1]);
			}
		}
		
		for(int jj=0; jj<4; jj++)
		{
			int xx= detect_corner(jj);
			
			int x= xx/9;
			int y= (xx%9)/3;
			int z= xx%3;
			
			System.out.printf("%d %d %d %d   %d\n",jj,x,y,z,xx);
//			try {
//				int ii= Integer.parseInt(next());
//			} catch (NumberFormatException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
			if(x==5 && y== corners[jj][0] && z== corners[jj][1])
			{
				continue;
			}
			if(x==5)
			{
				System.out.println("a");
				int mm=1, nn=1;
				if(z==2) mm=3;
				if(y==2) nn=0;
				move(move_ara[mm][nn]);
				move(move_ara[0][0]);
				move(move_ara[mm][nn^1]);
				
				xx= detect_corner(jj);
				x= xx/9;
				y= (xx%9)/3;
				z= xx%3;
			}
//			System.out.printf("%d %d %d %d   %d\n",jj,x,y,z,xx);
//			try {
//				int ii= Integer.parseInt(next());
//			} catch (NumberFormatException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			if(x==0)
			{
				System.out.println("b");
				int mm= 2;
				if(jj%2==1) mm=6;
				while(y*3+z!=mm)
				{
					move(move_ara[0][0]);
					xx= detect_corner(jj);
					x= xx/9;
					y= (xx%9)/3;
					z= xx%3;
//					System.out.printf("%d %d\n", y,z);
				}
				mm=1;
				if(jj%2==1) mm=3;
				int nn=1;
				if(jj>1) nn=0;
				move(move_ara[mm][nn]);
				move(move_ara[0][0]);
				move(move_ara[mm][nn^1]);

				xx= detect_corner(jj);
				x= xx/9;
				y= (xx%9)/3;
				z= xx%3;
			}
			if(x>0 && x<5 && y==2)
			{
				System.out.println("c");
				int nn= 0;
				if((x<3 && z==2) || (x>2 && z==0)) nn=1;
				move(move_ara[x][nn]);
				move(move_ara[0][0]);
				move(move_ara[0][0]);
				move(move_ara[x][nn^1]);
				
				xx= detect_corner(jj);
				x= xx/9;
				y= (xx%9)/3;
				z= xx%3;
			}
			if(x>0 && x<5 && y==0)
			{
				System.out.println("d");
				int mm=jj+3;
				if(jj==2) mm=2;
				if(jj==3) mm=1;
				if(z==2)
				{
					mm=4;
					if(jj==1) mm=1;
					if(jj==2) mm=3;
					if(jj==3) mm=2;
				}
				while(x!=mm)
				{
					move(move_ara[0][0]);
					xx= detect_corner(jj);
					x= xx/9;
					y= (xx%9)/3;
					z= xx%3;
				}
				mm+=2;
				if(mm>4) mm-=4;
				int nn=1;
				if(mm>2 && z==0) nn=0;
				if(mm<3 && z==2) nn=0;

				move(move_ara[mm][nn]);
				if(z==0)move(move_ara[0][1]);
				else move(move_ara[0][0]);
				move(move_ara[mm][nn^1]);
			}
		}
	}
	
	static int detect_side(int jj, int kk)
	{
		for(int i=0; i<12; i++)
		{
			if(det(two_side[i][0])==jj && det(two_side[i][1])==kk)
			{
				return i;
			}
			if(det(two_side[i][1])==jj && det(two_side[i][0])==kk)
			{
				return i+12;
			}
		}
		return 0;
	}
	
	static int detect_corner(int jj)
	{
		int xx=0;
		for(int i=0; i<8; i++)
		{
//			System.out.println("");
//			System.out.printf("col  %d   %d %d %d\n",i,det(three_side[i][0]),det(three_side[i][1]),det(three_side[i][2]));
			for(int j=0; j<3; j++)
			{
				if(det(three_side[i][j])==5 && det(three_side[i][(j+1)%3])==corner_sides_bottom[jj][0] && det(three_side[i][(j+2)%3])==corner_sides_bottom[jj][1])
				{
//					System.out.printf("i1  %d %d\n",i,j);
					xx=three_side[i][j];
					break;
				}
				if(det(three_side[i][j])==5 && det(three_side[i][(j+1)%3])==corner_sides_bottom[jj][1] && det(three_side[i][(j+2)%3])==corner_sides_bottom[jj][0])
				{
//					System.out.printf("i2  %d %d\n",i,j);
					xx=three_side[i][j];
					break;
				}
			}
		}
		return xx;
	}
	
	static int det(int x)
	{
		int i=x/9;
		int j= (x%9)/3;
		int k= x%3;

//		System.out.printf("i  %d %d %d\n",i,j,k);
		return qb.grid[i][j][k];
	}

	static void move(int x)
	{
		try {
			if(move_cnt>100)Thread.sleep(250);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		move_cnt++;
		if(x==1)
		{
			System.out.println("f1");
			qb.rotate(2,0, 0,3, 3,2, 5,5, 1,8);
		}
		if(x==2)
		{
			System.out.println("f2");
			qb.rotate(2,1, 1,4, 5,1, 3,6, 0,7 );
		}
		if(x==3)
		{
			System.out.println("b1");
			qb.rotate(4,1, 0,1, 3,4, 5,7, 1,6);
		}
		if(x==4)
		{
			System.out.println("b2");
			qb.rotate(4,0, 1,2, 5,3, 3,8, 0,5 );
		}
		if(x==5)
		{
			System.out.println("u1");
			qb.rotate(0,1, 2,1, 3,1, 4,1, 1,1);
		}
		if(x==6)
		{
			System.out.println("u2");
			qb.rotate(0,0, 1,1, 4,1, 3,1, 2,1);
		}
		if(x==7)
		{
			System.out.println("d1");
			qb.rotate(5,0, 1,3, 2,3, 3,3, 4,3);
		}
		if(x==8)
		{
			System.out.println("d2");
			qb.rotate(5,1, 4,3, 3,3, 2,3, 1,3);
		}
		if(x==9)
		{
			System.out.println("r1");
			qb.rotate(3,1, 0,4, 2,4, 5,4, 4,6);
		}
		if(x==10)
		{
			System.out.println("r2");
			qb.rotate(3,0, 4,2, 5,8, 2,8, 0,8);
		}
		if(x==11)
		{
			System.out.println("l1");
			qb.rotate(1,0, 0,2, 2,2, 5,2, 4,8);
		}
		if(x==12)
		{
			System.out.println("l2");
			qb.rotate(1,1, 4,4, 5,6, 2,6, 0,6);
		}
		r.repaint();
		
		if(debug_mode==1)
		{
			try {
				int i= Integer.parseInt(next());
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static class drawingBoard extends JComponent
	{
		Color[] col= new Color[6];
		cube qb;
		
		public drawingBoard(cube qbb)
		{
			qb=qbb;
			col[0]= Color.white;
			col[1]= Color.red;
			col[2]= Color.blue;
			col[3]= new Color(219, 149, 43);
			col[4]= Color.green;
			col[5]= Color.yellow;
			
			addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e)
				{
					
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
			graphSettings.setColor(Color.black);
			graphSettings.fill(new Rectangle(0, 0, 1200, 700));
			
			for(int i=0; i<3; i++)
			{
				for(int j=0; j<3; j++)
				{
					graphSettings.setColor(col[qb.grid[0][i][j]]);
					graphSettings.fill(new Rectangle(300+ j*40, 150 + i*40, 38, 38));
				}
			}
			
			for(int i=1; i<5; i++)
			{
				for(int j=0; j<3; j++)
				{
					for(int k=0; k<3; k++)
					{
						graphSettings.setColor(col[qb.grid[i][j][k]]);
						graphSettings.fill(new Rectangle(180+ ((i-1)*3 +k)*40, 270 + j*40, 38, 38));	
					}
				}
			}
			for(int i=0; i<3; i++)
			{
				for(int j=0; j<3; j++)
				{
					graphSettings.setColor(col[qb.grid[5][i][j]]);
					graphSettings.fill(new Rectangle(300+ j*40, 390 + i*40, 38, 38));
				}
			}
			
			
			//graphSettings.draw(new Line2D.Float(21.50f, 132.50f, 459.50f, 132.50f));
			

		}
	}

	
	public static class cube
	{
		public int[][][] grid= new int[6][3][3];
		public int[] ln1= new int[3], ln2= new int[3];
		
		cube()
		{
			for(int i=0; i<6; i++)
			{
				for(int j=0; j<3; j++)
				{
					for(int k=0; k<3; k++)
					{
						grid[i][j][k]=i;
					}
				}
			}
		}
		
		
		@SuppressWarnings("null")
		void rotate(int x, int d, int f1, int s1, int f2, int s2, int f3, int s3, int f4, int s4)
		{
			int[][] temp = new int[3][3];
			for(int i=0; i<3; i++)
			{
				for(int j=0; j<3; j++)
				{
					temp[i][j]= grid[x][i][j];
				}
			}
			
			int jj=0, ii=2;
			if(d==1) jj=2;
			
			for(int i=0; i<3; i++)
			{
				ii=2;
				if(d==1) ii=0;
				for(int j=0; j<3; j++)
				{
					grid[x][i][j]= temp[ii][jj];
					if(d==0) ii--;
					else ii++;
				}
				if(d==0) jj++;
				else jj--;
			}
			copy(f4, s4);
			ln2[0]= ln1[0];
			ln2[1]= ln1[1];
			ln2[2]= ln1[2];
			copy(f3, s3);
			paste(f4, s4);
			copy(f2, s2);
			paste(f3, s3);
			copy(f1, s1);
			paste(f2, s2);
			ln1[0]= ln2[0];
			ln1[1]= ln2[1];
			ln1[2]= ln2[2];
			paste(f1, s1);
		}
		
		void copy(int f1, int s1)
		{
			if(s1==1)
			{
				ln1[0]= grid[f1][0][0];
				ln1[1]= grid[f1][0][1];
				ln1[2]= grid[f1][0][2];
			}
			if(s1==2)
			{
				ln1[0]= grid[f1][0][0];
				ln1[1]= grid[f1][1][0];
				ln1[2]= grid[f1][2][0];
			}
			if(s1==3)
			{
				ln1[0]= grid[f1][2][0];
				ln1[1]= grid[f1][2][1];
				ln1[2]= grid[f1][2][2];
			}
			if(s1==4)
			{
				ln1[0]= grid[f1][0][2];
				ln1[1]= grid[f1][1][2];
				ln1[2]= grid[f1][2][2];
			}
			if(s1==5)
			{
				ln1[0]= grid[f1][0][2];
				ln1[1]= grid[f1][0][1];
				ln1[2]= grid[f1][0][0];
			}
			if(s1==6)
			{
				ln1[0]= grid[f1][2][0];
				ln1[1]= grid[f1][1][0];
				ln1[2]= grid[f1][0][0];
			}
			if(s1==7)
			{
				ln1[0]= grid[f1][2][2];
				ln1[1]= grid[f1][2][1];
				ln1[2]= grid[f1][2][0];
			}
			if(s1==8)
			{
				ln1[0]= grid[f1][2][2];
				ln1[1]= grid[f1][1][2];
				ln1[2]= grid[f1][0][2];
			}
		}

		void paste(int f1, int s1)
		{
			if(s1==1)
			{
				grid[f1][0][0]= ln1[0];
				grid[f1][0][1]= ln1[1];
				grid[f1][0][2]= ln1[2];
			}
			if(s1==2)
			{
				grid[f1][0][0]= ln1[0];
				grid[f1][1][0]= ln1[1];
				grid[f1][2][0]= ln1[2];
			}
			if(s1==3)
			{
				grid[f1][2][0]= ln1[0];
				grid[f1][2][1]= ln1[1];
				grid[f1][2][2]= ln1[2];
			}
			if(s1==4)
			{
				grid[f1][0][2]= ln1[0];
				grid[f1][1][2]= ln1[1];
				grid[f1][2][2]= ln1[2];
			}
			if(s1==5)
			{
				grid[f1][0][2]= ln1[0];
				grid[f1][0][1]= ln1[1];
				grid[f1][0][0]= ln1[2];
			}
			if(s1==6)
			{
				grid[f1][2][0]= ln1[0];
				grid[f1][1][0]= ln1[1];
				grid[f1][0][0]= ln1[2];
			}
			if(s1==7)
			{
				grid[f1][2][2]= ln1[0];
				grid[f1][2][1]= ln1[1];
				grid[f1][2][0]= ln1[2];
			}
			if(s1==8)
			{
				grid[f1][2][2]= ln1[0];
				grid[f1][1][2]= ln1[1];
				grid[f1][0][2]= ln1[2];
			}
		}
		
	}
}
