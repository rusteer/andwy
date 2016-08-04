package image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;

public class ImageResize {
    public static void main(String[] args) throws Exception {
        testPng();
    }
    
    public static void testJPG() throws Exception{
        String srcPath = "C:/Users/Administrator/Desktop/output/bgMain.jpg";
        String destPath = srcPath + ".jpg";
        float quality = 0.5f;
        Iterator iter = ImageIO.getImageWritersByFormatName("jpg");
        ImageWriter writer = (ImageWriter) iter.next();
        ImageWriteParam iwp = writer.getDefaultWriteParam();
        iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        iwp.setCompressionQuality(quality);
        File file = new File(destPath);
        FileImageOutputStream output = new FileImageOutputStream(file);
        writer.setOutput(output);
        FileInputStream inputStream = new FileInputStream(srcPath);
        BufferedImage originalImage = ImageIO.read(inputStream);
        IIOImage image = new IIOImage(originalImage, null, null);
        writer.write(null, image, iwp);
        writer.dispose();
        System.out.println("DONE");
    }
    
    
    
    public static void testPng() throws IOException{
        BufferedImage bufferedImage = ImageIO.read(new File("C:/Users/Administrator/Desktop/output/wave_intro.png"));
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName( "png" );
        ImageWriter imageWriter = (ImageWriter) writers.next();
        ImageWriteParam pngparams = imageWriter.getDefaultWriteParam();
        pngparams.setCompressionMode( pngparams.MODE_EXPLICIT );
        pngparams.setCompressionQuality( 0.5F );
        pngparams.setProgressiveMode( pngparams.MODE_DISABLED );
        pngparams.setDestinationType(
        new ImageTypeSpecifier( bufferedImage.getColorModel(), bufferedImage.getSampleModel() ) );
        
    }
    
}
