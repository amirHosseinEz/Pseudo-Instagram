package Instagram.user;

import java.io.File;
import java.io.FileInputStream;

public class Image {


    public static byte[] getImageInput(File file){

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
