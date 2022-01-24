package Instagram.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Scanner;

public class Image {

    public static Scanner scanner = new Scanner(System.in);

    public static byte[] getImageInput(){
        String imagePath = scanner.nextLine();

        File file = new File(imagePath);
        byte[] bFile = new byte[(int) file.length()];

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            //convert file into array of bytes
            fileInputStream.read(bFile);
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bFile;
    }



}
