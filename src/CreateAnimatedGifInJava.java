/* import java.io.File;
import java.io.FilenameFilter;
import com.aspose.imaging.Image;
import com.aspose.imaging.RasterImage;
import com.aspose.imaging.fileformats.gif.GifImage;
import com.aspose.imaging.fileformats.gif.blocks.GifFrameBlock;
import com.aspose.imaging.system.collections.Generic.List;
import com.aspose.imaging.License;

public class CreateAnimatedGifInJava {

	public static void main(String[] args)
	{
		// Create and initialize license to avoid trail version limitations
		// while creating animated GIF from images
		License licCreateAnimatedGif = new License(); 
		licCreateAnimatedGif.setLicense("Aspose.Imaging.lic");
		
		// Get list of all the images of type JPEG and PNG from the target library
		File dir = new File("/Users/TestProject/Images");
		File[] files = dir.listFiles(new FilenameFilter() 
		{
		    public boolean accept(File dir, String name) 
		    {
		        return (name.toLowerCase().endsWith(".jpg")  || name.toLowerCase().endsWith(".png"));
		    }
		});
		
		// Create an empty list of RasterImages
		List<RasterImage> mylist = new List<RasterImage>();

		// Iterate through all the image files and load them into RasterImage class
		// Add each raster image in the list of Rester images declared above
		for (File file : files) 
		{
		    if (file.isFile()) 
		    {
		    	System.out.println(file.getPath());
		    	
	    		RasterImage image1 =  (RasterImage)Image.load(file.getPath());
	    		mylist.add(image1);
		    }
		}

		// Create a GIF image using the first Raster image in the list
		GifImage gifImage = new GifImage(new GifFrameBlock(mylist.get(0)));
		
		// Iterate through all the remaining images and add them to the 
		// newly created GIF image
		for(RasterImage img:mylist)
		{
			try
			{
				gifImage.addPage(img);
			}
			catch(Exception ex)
			{
				System.out.println(ex.getMessage());
			}
		}
		
		
		// Save the output GIF file on the disk
		gifImage.save("output.gif");
		
		System.out.println("Done");
	}
} */