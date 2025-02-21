import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

public class DecryptImage {
    public static void main(String[] args) {
        try {



            File inputFile = new File("encryptedImage.png");  
            BufferedImage img = ImageIO.read(inputFile);

            


            File passcodeFile = new File("passcode.txt");
            if (!passcodeFile.exists()) {
                System.out.println("Passcode file not found! Cannot decrypt.");
                return;
            }
            String savedPasscode = new String(Files.readAllBytes(passcodeFile.toPath())).trim();

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter passcode for decryption: ");
            String enteredPass = scanner.nextLine();

            if (savedPasscode.equals(enteredPass)) {
                System.out.print("Enter the expected message length: ");
                int messageLength = scanner.nextInt();
                scanner.nextLine(); 

                String decryptedMessage = decodeMessage(img, messageLength);
                System.out.println("Decrypted message: " + decryptedMessage);
            } else {
                System.out.println("YOU ARE NOT AUTHORIZED!");
            }

            scanner.close();
        } catch (IOException e) {
            System.err.println("Error processing image: " + e.getMessage());
        }
    }



    private static String decodeMessage(BufferedImage img, int length) {
        int width = img.getWidth();
        int height = img.getHeight();
        StringBuilder message = new StringBuilder();

        int charIndex = 0;
        for (int y = 0; y < height && charIndex < length; y++) {
            for (int x = 0; x < width && charIndex < length; x++) {
                int pixel = img.getRGB(x, y);
                int blue = pixel & 0xff; 

                message.append((char) blue);
                charIndex++;
            }
        }

        return message.toString();
    }
}
