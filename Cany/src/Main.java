import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.ConvolveOp;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.Kernel;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.awt.image.WritableRenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.GrayFilter;

public class Main {

	public static void main(String[] args)
	{
		System.out.println("Starting Image Processing");
		BufferedImage frame = null;
		BufferedImage frame2 = null;
		

		try 
		{
		    frame = ImageIO.read(new File("./src/nayeem3mb.jpg")); // eventually C:\\ImageTest\\pic2.jpg
		    
		    frame2 = new BufferedImage(frame.getWidth(), frame.getHeight(), 5);
		    double ratioWbH = (double)frame.getWidth()/(double)frame.getHeight() ;
		    
		    
		    int W = frame.getWidth() ;
		    int H = frame.getHeight() ;
		    int cut = 50 ;
		    
		    
		    double scale = 1.0 ; //may have to change
		    H *= scale ;
		    //frame = resize(frame,(int)( ratioWbH*(double)(H)),H) ;
		    //H -= cut ;
		    //frame = frame.getSubimage(cut,cut,(int)( ratioWbH*(double)H),H) ;
		  //frame = getScaledInstance(frame,(int)( ratioWbH*(double)900),900,RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR , true ) ;
		    System.out.println("Height = "+frame.getHeight()+" bytes");
		    System.out.println("Width = "+frame.getWidth()+" bytes");
		    System.out.println("Input File Size = "+frame.getHeight()*frame.getWidth()+" bytes");
		    frame2.getGraphics().drawImage(frame, 0, 0, null);
		} 
		catch (IOException e) 
		{
		    e.printStackTrace();
		}
		
		//no processing here
		BufferedImage edges = frame2 ; //new BufferedImage(frame.getWidth(), frame.getHeight(), 5);
		
		//System.out.println("No Canny done");
		
		
//		int[] pixels = (int[]) edges.getData().getDataElements(0, 0,  edges.getWidth(), edges.getHeight(), null);
//		int[] pixels2 = (int[]) frame2.getData().getDataElements(0, 0, frame2.getWidth(), frame2.getHeight(), null);
		
		int rgb=0, rgb2=0;
		Color myWhite = new Color(0,0,0); // Color white
		int z = myWhite.getRGB();
		System.out.println(z);
		rgb = edges.getRGB( 1, 1);
		int y=0;
		int p = rgb;
		
		int up=0, down=edges.getHeight()-1, right=0, left= edges.getWidth();
		int fl=0;

		
		for(int i=1; i<edges.getHeight(); i++)
		{
//			System.out.print(edges.getRGB(i, 1));
			int first=0, last=edges.getWidth()-1;
			for(int j=1; j<edges.getWidth(); j++)
			{
//				System.out.print(edges.getRGB(j,i)+ " ");
				int p1 = edges.getRGB(j, i);
				if(p1==p)
				{
					first++;
				}
				else
				{
					break;
				}
			}
//			System.out.println("\n");
			for(int j=edges.getWidth()-1; j>=0; j--)
			{
				int p1 = edges.getRGB(j, i);
				if(p1==p)
				{
					last--;
				}
				else
				{
					break;
				}
			}
			for(int j=0; j<first && j<edges.getWidth(); j++)
			{
				frame2.setRGB(j, i, 0);
			}
			for(int j=last+1; j<edges.getWidth(); j++)
			{
				frame2.setRGB(j, i, z);
			}
			y+= edges.getWidth();
			
			if(first<left) left= first;
			if(last>right) right= last;
			if(fl==0)
			{
				if(first>last)up++;
				else
				{
					fl=1;
					down=up;
				}
			}
			else
			{
				if(first<=last) down=i;
			}
		}
		//
		//binary_type for creating black and white, not so good result
		//BufferedImage cropped = new BufferedImage(right-left+1, down-up+1, BufferedImage.TYPE_BYTE_BINARY);
		//
		
		
		
		BufferedImage cropped = new BufferedImage(right-left+1, down-up+1,5);
		//BufferedImage croppedDemo = new BufferedImage(right-left+1, down-up+1, 5);
		//BufferedImage cropped = thresholdImage(croppedDemo,32) ; // start threshold from = 128
		
		
		for(int i=up; i<=down; i++)
		{
			for(int j=left; j<=right; j++)
			{
				cropped.setRGB(j-left, i-up, frame2.getRGB(j, i));
				/*
				int rgbBefore = cropped.getRGB(j-left,i-up) ;
				if( rgbBefore < 1 )
				{
					cropped.setRGB(j-left, i-up, 0); //all white
				}*/
				
			}
		}
		
//		detector = new CannyEdgeDetector();
//		detector.setLowThreshold(0.2f);
//		detector.setHighThreshold(0.3f);
//		detector.setSourceImage(cropped);
//		detector.process();
//		
////		BufferedImage crpd= detector.getEdgesImage();
//		
////		BufferedImage crpd= new BufferedImage(cropped.getWidth(), cropped.getHeight(), BufferedImage.TYPE_USHORT_GRAY);
//		
////		crpd.getGraphics().drawImage(cropped, 0, 0, null);
//		ImageFilter filter = new GrayFilter(true, 50);  
//		ImageProducer producer = new FilteredImageSource(cropped.getSource(), filter);  
//		Image img= Toolkit.getDefaultToolkit().createImage(producer); 
//		
//		BufferedImage crpd= convertToBufferedImage(img);
		
//		BufferedImage crpd= threshold(cropped, 150,160,160);
		
		
		//BufferedImage crpd= Grayscale(cropped);
//		crpd= threshold(crpd, 160,160,160);
		
		//BufferedImage crpd= myThreshold(cropped);
		
		
		//if we use crop
		//BufferedImage crpd= cropped;
		
		
		//NOt using crop... hardcode first ,then detect value later .. vertical cut and horizontal_cut later.
		BufferedImage crpd= frame; // original
		//crpd is orignal now
		
		
		File outputfile = new File("original.jpg");
	    try {
			ImageIO.write(crpd, "jpg", outputfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    /*
	    File contourfile = new File("savedC.jpg");
//	    ((WritableRenderedImage) contourfile).getWritableTile(0, 0).setDataElements(0, 0, frame2.getWidth(), frame2.getHeight(), pixels2);
	    try {
			ImageIO.write(frame2, "jpg", contourfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    */
	    File croppedfileLQ = new File("croppedLQ.jpg");
	    
	    try {
			ImageIO.write(crpd, "jpg", croppedfileLQ);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    //making the original image HQ
	    BufferedImage croppedHQ = makeSharp(cropped) ;
	    File croppedfileHQ = new File("croppedHQ.jpg");
	    
	    try {
			ImageIO.write(croppedHQ, "jpg", croppedfileHQ);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	  //mycode starts
	    //cutting crpd right in the middle a
	    BufferedImage croppedLeftPart = null ;// ImageIO.read(input) ;
	    BufferedImage croppedRightPart = null;
	    
	    croppedLeftPart = new BufferedImage(crpd.getWidth()/2,crpd.getHeight(),5) ;
	    croppedRightPart = new BufferedImage(crpd.getWidth()/2,crpd.getHeight(),5) ;
	    
	    croppedLeftPart.getGraphics().drawImage(crpd, 0, 0, crpd.getWidth(),crpd.getHeight(),  null) ;
	    //croppedRightPart.getGraphics().drawImage(crpd, 0,0, crpd.getWidth(),crpd.getHeight(),  null)  ;
	    croppedRightPart = crpd.getSubimage(crpd.getWidth()/2, 0, crpd.getWidth()/2, crpd.getHeight()) ;
	    
	    
	    /*// not showing for the moment
	    File croppedLeftfile = new File("croppedLeft.jpg");
	    
	    try {
			ImageIO.write(croppedLeftPart, "jpg", croppedLeftfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    File croppedRightfile = new File("croppedRight.jpg");
	    
	    try {
			ImageIO.write(croppedRightPart, "jpg", croppedRightfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    */
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    //trimming the table part
	    BufferedImage croppedLeftTrimmedPart = null ;// ImageIO.read(input) ;
	    BufferedImage croppedRightTrimmedPart = null;
	    
	    int edge_to_cut = 400 ; //pash theke , emoni ekta lagbe uporer theker jonno.
	    croppedLeftTrimmedPart = new BufferedImage(croppedLeftPart.getWidth()-edge_to_cut,croppedLeftPart.getHeight(),5) ;
	    croppedRightTrimmedPart = new BufferedImage(croppedRightPart.getWidth()-edge_to_cut,croppedRightPart.getHeight(),5) ;
	    
	    //croppedLeftTrimmedPart.getGraphics().drawImage(crpd, 0, 0, crpd.getWidth(),crpd.getHeight(),  null) ;
	    //croppedRightPart.getGraphics().drawImage(crpd, 0,0, crpd.getWidth(),crpd.getHeight(),  null)  ;
	    //croppedRightTrimmedPart = crpd.getSubimage(croppedRightPart.getWidth()/2, 0, croppedLeftTrimmedPart.getWidth()/2, crpd.getHeight()) ;
	    
	    croppedLeftTrimmedPart = croppedLeftPart.getSubimage(edge_to_cut, 0,  croppedLeftPart.getWidth()-edge_to_cut, croppedLeftPart.getHeight()) ;
	    croppedRightTrimmedPart = croppedRightPart.getSubimage(0, 0,  croppedLeftPart.getWidth()-edge_to_cut, croppedLeftPart.getHeight()) ;
	    
	    /*
	    // not showing these now
	    File croppedLeftTrimmedFile = new File("croppedLeftTrimmed.jpg");
	    
	    try {
			ImageIO.write(croppedLeftTrimmedPart, "jpg", croppedLeftTrimmedFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    File croppedRightTrimmedFile = new File("croppedRightTrimmed.jpg");
	    
	    try {
			ImageIO.write(croppedRightTrimmedPart, "jpg", croppedRightTrimmedFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    */
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	   
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    // High Quality Black-White
	    BufferedImage croppedLeftTrimmedPartHQ1 = null ;// ImageIO.read(input) ;
	    BufferedImage croppedRightTrimmedPartHQ1 = null;
	    
	   // int edge_to_cut = 220 ;
	    croppedLeftTrimmedPartHQ1 = myThreshold(croppedLeftTrimmedPart);
	    croppedRightTrimmedPartHQ1 = myThreshold(croppedRightTrimmedPart);
	    
	    
	    
	    //sub-task
		//making sharp
	    //BufferedImage croppedLeftTrimmedPartHQ2 = null ;// ImageIO.read(input) ;
	    //BufferedImage croppedRightTrimmedPartHQ2 = null;
	    //croppedLeftTrimmedPartHQ2 = makeSharp(croppedLeftTrimmedPartHQ1) ;
	    //croppedRightTrimmedPartHQ2 = makeSharp(croppedRightTrimmedPartHQ1) ;
	    
		 
	    
	    
	
	    File croppedLeftTrimmedHQFile1 = new File("croppedLeftTrimmedHQ1.jpg");
	    
	    try {
			ImageIO.write(croppedLeftTrimmedPartHQ1, "jpg", croppedLeftTrimmedHQFile1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    File croppedRightTrimmedHQFile1 = new File("croppedRightTrimmedHQ1.jpg");
	    
	    try {
			ImageIO.write(croppedRightTrimmedPartHQ1, "jpg", croppedRightTrimmedHQFile1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    /*
	    //printing HQ2
	    File croppedLeftTrimmedHQFile2 = new File("croppedLeftTrimmedHQ2.jpg");
	    
	    try {
			ImageIO.write(croppedLeftTrimmedPartHQ2, "jpg", croppedLeftTrimmedHQFile2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    File croppedRightTrimmedHQFile2 = new File("croppedRightTrimmedHQ2.jpg");
	    
	    try {
			ImageIO.write(croppedRightTrimmedPartHQ2, "jpg", croppedRightTrimmedHQFile2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    */
	    
	    
	  //mycode ends
	    
	}//main() ends
	
	public static BufferedImage convertToBufferedImage(Image image)
	{
	    BufferedImage newImage = new BufferedImage(
	        image.getWidth(null), image.getHeight(null),
	        BufferedImage.TYPE_BYTE_GRAY);
	    Graphics2D g = newImage.createGraphics();
	    g.drawImage(image, 0, 0, null);
	    g.dispose();
	    return newImage;
	}
	
	public static BufferedImage threshold(BufferedImage x, int r, int g, int b)
	{
		for(int i=0; i<x.getHeight(); i++)
		{
			for(int j=0; j<x.getWidth(); j++)
			{
				int p= x.getRGB(j, i);
				int r1= p&(0xff0000);
				int g1= p&(0xff00);
				int b1= p&(0xff);
				if(r1>r && g1>g && b1>b)
				{
					x.setRGB(j, i, -1);
				}
				else
				{
					x.setRGB(j, i, 0);
				}
			}
		}
		return x;
	}
	
	public static BufferedImage Grayscale(BufferedImage x)
	{
		
		for(int i=0; i<x.getHeight(); i++)
		{
			for(int j=0; j<x.getWidth(); j++)
			{
				Color cl= new Color(x.getRGB(j, i));
				int r= (int)(cl.getRed()*0.299);
				int g= (int)(cl.getGreen()*0.587);
				int b= (int)(cl.getBlue()*0.114);
				
				Color nw= new Color(r+g+b, r+g+b, r+g+b);
				x.setRGB(j, i, nw.getRGB());
			}
		}
		
		return x;
	}
	/**
	 * Converts an image to a binary one based on given threshold
	 * @param image the image to convert. Remains untouched.
	 * @param threshold the threshold in [0,255]
	 * @return a new BufferedImage instance of TYPE_BYTE_GRAY with only 0'S and 255's
	 */
	public static BufferedImage thresholdImage(BufferedImage image, int threshold) {
	    BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
	    result.getGraphics().drawImage(image, 0, 0, null);
	    WritableRaster raster = result.getRaster();
	    int[] pixels = new int[image.getWidth()];
	    for (int y = 0; y < image.getHeight(); y++) {
	        raster.getPixels(0, y, image.getWidth(), 1, pixels);
	        for (int i = 0; i < pixels.length; i++) {
	            if (pixels[i] < threshold) pixels[i] = 0;
	            else pixels[i] = 255;
	        }
	        raster.setPixels(0, y, image.getWidth(), 1, pixels);
	    }
	    return result;
	}
	
	public static BufferedImage myThreshold(BufferedImage image )
	{
			BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB ) ; //BufferedImage.TYPE_BYTE_BINARY);
		 	int THRESHOLD = 110 ; //180,kalo, have to change according to light, for now set it greedily or binary searching manually
		 	//110 = perfect medium
	        //String filename = args[0];
	        //Picture pic = new Picture(filename);
	        //pic.show();
	        for (int i = 0; i < image.getHeight(); i++) {
	            for (int j = 0; j < image.getWidth(); j++) {
	            	int rgba = image.getRGB(j,i) ;
	                Color color = new Color(rgba,true) ;
	                //color.getTransparency() ;
	                int r = color.getRed();
	                int g = color.getGreen();
	                int b = color.getBlue();
	                double lum = .299*r + .587*g + .114*b;
	                
	                //double lum = Luminance.lum(color);
	                if (lum >= THRESHOLD) result.setRGB(j, i, -1); //white
	                else                  result.setRGB(j, i, 0); //black
	            }
	        }
	        //pic.show();
	        return result;
	        
	        //http://introcs.cs.princeton.edu/java/31datatype/Luminance.java.html
	}
	
	/*//to reduce complexity n^3
	public static BufferedImage resize(BufferedImage img, int newW, int newH) { 
	    Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
	    BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2d = dimg.createGraphics();
	    g2d.drawImage(tmp, 0, 0, null);
	    g2d.dispose();

	    return dimg;
	}  */
	//https://web.archive.org/web/20080516181120/http://today.java.net/pub/a/today/2007/04/03/perils-of-image-getscaledinstance.html
	
		/*public static BufferedImage getScaledInstance(BufferedImage img,
	            int targetWidth,
	            int targetHeight,
	            Object hint,
	            boolean higherQuality)
		{
			int type = (img.getTransparency() == Transparency.OPAQUE) ?
			        BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
			    BufferedImage ret = (BufferedImage)img;
			    int w, h;
			    if (higherQuality) {
			        // Use multi-step technique: start with original size, then
			        // scale down in multiple passes with drawImage()
			        // until the target size is reached
			        w = img.getWidth();
			        h = img.getHeight();
			    } else {
			        // Use one-step technique: scale directly from original
			        // size to target size with a single drawImage() call
			        w = targetWidth;
			        h = targetHeight;
			    }

			    do {
			        if (higherQuality && w > targetWidth) {
			            w /= 1.2;
			            if (w < targetWidth) {
			                w = targetWidth;
			            }
			        }

			        if (higherQuality && h > targetHeight) {
			            h /= 1.2;
			            if (h < targetHeight) {
			                h = targetHeight;
			            }
			        }

			        BufferedImage tmp = new BufferedImage(w, h, type);
			        Graphics2D g2 = tmp.createGraphics();
			        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
			        g2.drawImage(ret, 0, 0, w, h, null);
			        g2.dispose();

			        ret = tmp;
			    } while (w != targetWidth || h != targetHeight);

			    return ret;
		}*/
		
		//making sharp
		public static BufferedImage makeSharp( BufferedImage srcbimg )
		{
			/*
			
		 float[] SHARPEN3x3 = {
	                0.f, -1.f, 0.f,
	                -1.f, 5.0f, -1.f,
	                0.f, -1.f, 0.f}; 
	                */
			
			
			
			/*
			//these below values 
			//https://examples.javacodegeeks.com/desktop-java/awt/image/sharpening-a-buffered-image/
			//High pass
			float[] SHARPEN3x3 ={
					-1, -1, -1,
                    -1, 9, -1,
                    -1, -1, -1}; */
			
			
			
			
			
			//these below values 
			//PDF: 
			 //low pass: convoultion not working
			float[] SHARPEN3x3 ={ -1.0f, -1.0f, -1.0f, -1.0f, 9.0f, -1.0f, -1.0f, -1.0f,
        -1.0f }; 
                    
			
			
			
			/*
			//these below values 
			//PDF: Laplacian not so good
			float[] SHARPEN3x3 ={
					1, -2, 1,
                    -2, 5, -2,
                    1, -2, 1}; 
			
			*/
		 
		 
		 
			BufferedImage dstbimg = new 
			  BufferedImage(srcbimg.getWidth(),srcbimg.getHeight(),5);
			Kernel kernel = new Kernel(3,3,SHARPEN3x3);
			ConvolveOp cop = new ConvolveOp(kernel,
			                    ConvolveOp.EDGE_NO_OP,
			                    null);
			cop.filter(srcbimg,dstbimg);
			return dstbimg ;
		}
		
	
	
	
	
}
