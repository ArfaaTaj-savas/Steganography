import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class EncryptImage {
    public static void main(String[] args) {
        try {


            File inputFile = new File("mypic.jpg");  
            BufferedImage img = ImageIO.read(inputFile);

            Scanner scanner = new Scanner(System.in);
            


            System.out.print("Enter the secret message: ");
            String msg = scanner.nextLine();
            System.out.print("Enter a passcode: ");
            String password = scanner.nextLine();



            encodeMessage(img, msg);


            File outputFile = new File("encryptedImage.png");
            ImageIO.write(img, "png", outputFile);
            System.out.println("Message encrypted successfully. The image is saved as 'encryptedImage.png'.");



            File passcodeFile = new File("passcode.txt");
            java.nio.file.Files.write(passcodeFile.toPath(), password.getBytes());
            System.out.println("Passcode saved securely.");

            scanner.close();
        } catch (IOException e) {
            System.err.println("Error processing image: " + e.getMessage());
        }
    }

    private static void encodeMessage(BufferedImage img, String msg) {
        int width = img.getWidth();
        int height = img.getHeight();
        int charIndex = 0;

        for (int y = 0; y < height && charIndex < msg.length(); y++) {
            for (int x = 0; x < width && charIndex < msg.length(); x++) {
                int pixel = img.getRGB(x, y);  
                int alpha = (pixel >> 24) & 0xff;
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = pixel & 0xff;

                blue = (int) msg.charAt(charIndex);

                int newPixel = (alpha << 24) | (red << 16) | (green << 8) | blue;
                img.setRGB(x, y, newPixel); 

                charIndex++; 
            }
        }
    }
}
