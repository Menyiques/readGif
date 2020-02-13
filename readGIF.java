import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DirectColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.*;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class readGIF {
private static Object m_pixels=null;
private static ColorModel m_colorModel=null;
private static int m_iNumOfColors=0;  
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
		    String[] imageatt = new String[]{
		            "imageLeftPosition",
		            "imageTopPosition",
		            "imageWidth",
		            "imageHeight"
		    };    

		    ImageReader reader = (ImageReader)ImageIO.getImageReadersByFormatName("gif").next();
		    ImageInputStream ciis = ImageIO.createImageInputStream(new File("c:\\d\\negro_rojo.gif"));
		    reader.setInput(ciis, false);

		    int noi = reader.getNumImages(true);
		    BufferedImage master = null;

		    for (int i = 0; i < noi; i++) { 
		        BufferedImage image = reader.read(i);
		        PixelGrabber pixelGrabber=new PixelGrabber(image,0,0,2,2,false); 
		        try{pixelGrabber.grabPixels();}catch (Exception e){System.out.println("PixelGrabber exception");}
		        m_pixels=(Object)pixelGrabber.getPixels();
		        m_colorModel=pixelGrabber.getColorModel();
		        if (!(m_colorModel instanceof IndexColorModel))
		        {}else
		        {
		          m_iNumOfColors=((IndexColorModel)m_colorModel).getMapSize();

		        }
		        System.out.println(i+":"+image.getHeight()+"x"+image.getWidth()+" Colors:"+m_iNumOfColors);
			       
		        byte[] pixelData=(byte[])m_pixels; 
		        
		         
				for (int y=0; isIndexed() && y<pixelGrabber.getHeight(); y++)

				{


					for (int x=0; x<pixelGrabber.getWidth(); x++)

				    {
			
						  int pixel=(int)pixelData[y*pixelGrabber.getWidth()+x];
						  System.out.println("x="+x+" y="+y);
						  System.out.println(pixel);	
					      System.out.println("idx="+pixel+" R="+getRed(pixel)+" G="+getGreen(pixel)+" B="+getBlue(pixel));

				    }
		
				}
		        
		        IIOMetadata metadata = reader.getImageMetadata(i);

		        Node tree = metadata.getAsTree("javax_imageio_gif_image_1.0");
		        NodeList children = tree.getChildNodes();

		        for (int j = 0; j < children.getLength(); j++) {
		            Node nodeItem = children.item(j);

		            if(nodeItem.getNodeName().equals("ImageDescriptor")){
		                Map<String, Integer> imageAttr = new HashMap<String, Integer>();

		                for (int k = 0; k < imageatt.length; k++) {
		                    NamedNodeMap attr = nodeItem.getAttributes();
		                    Node attnode = attr.getNamedItem(imageatt[k]);
		                    imageAttr.put(imageatt[k], Integer.valueOf(attnode.getNodeValue()));
		                }
		                if(i==0){
		                    master = new BufferedImage(imageAttr.get("imageWidth"), imageAttr.get("imageHeight"), BufferedImage.TYPE_INT_ARGB);
		                }
		                master.getGraphics().drawImage(image, imageAttr.get("imageLeftPosition"), imageAttr.get("imageTopPosition"), null);
		            }
		        }
		        ImageIO.write(master, "GIF", new File( i + ".gif")); 
		    }
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}

	public static int getRed(int pixel)

	  {

	    if ((m_colorModel instanceof IndexColorModel))	  

			return ((IndexColorModel)m_colorModel).getRed(pixel);

		else

			return ((DirectColorModel)m_colorModel).getRed(pixel);

	  }



	  public static int getGreen(int pixel)

	  {

	    if ((m_colorModel instanceof IndexColorModel))	  

			return ((IndexColorModel)m_colorModel).getGreen(pixel);

		else

			return ((DirectColorModel)m_colorModel).getGreen(pixel);

	  }

	  

	  public static int getBlue(int pixel)

	  {

	    if ((m_colorModel instanceof IndexColorModel))	  

			return ((IndexColorModel)m_colorModel).getBlue(pixel);

		else

			return ((DirectColorModel)m_colorModel).getBlue(pixel);

	  }

	  

	  public static int getRGB(int pixel)

	  {

	    if ((m_colorModel instanceof IndexColorModel))	  

			return ((IndexColorModel)m_colorModel).getRGB(pixel);

		else

			return pixel;

	  }
	  
	  public static boolean isIndexed()

	  {

		if (m_colorModel==null)

		  return false;

	    return ((m_colorModel instanceof IndexColorModel));

	  }
	
}
