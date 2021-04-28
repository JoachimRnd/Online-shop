package be.vinci.pae.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Upload {

  /**
   * Save uploadedInputStream to uploadedFileLocation.
   *
   * @param uploadedInputStream InputStream of file
   * @param uploadedFileLocation Location of file
   * @return boolean
   */
  public static boolean saveToFile(InputStream uploadedInputStream, String uploadedFileLocation) {

    try {
      OutputStream out;

      int read = 0;
      byte[] bytes = new byte[1024];

      out = new FileOutputStream(uploadedFileLocation);
      while ((read = uploadedInputStream.read(bytes)) != -1) {
        out.write(bytes, 0, read);
      }
      out.flush();
      out.close();
      return true;
    } catch (IOException e) {
      throw new FatalException("Error while copying the file on the disk");
    }
  }
}
