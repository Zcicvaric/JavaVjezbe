/*
        Generate CRC32 Checksum For Byte Array Example
        This Java example shows how to get the CRC32 cheksum value for
        array of bytes using CRC32 Java class.
 */

//import java.util.zip.CRC32;
//import java.util.zip.Checksum;

    public class CalculateCRC32ForByteArray {
        
          public static void main(String args[]) {
              
              String str = "Generate CRC32 Checksum For Byte Array Example";
              
              //Convert string to bytes
              byte bytes[] = str.getBytes();
              
              Checksum cheksum = new CRC32();
              
              /*
               * To compute the CRC32 cheksum for byte array, use
               *
               * void update(bytes[] b, int start, int length)
               * method of CRC32 class.
               */
              
              cheksum.update(bytes,0,bytes.length);
              
              /*
               * Get the generated cheksum using
               * getValue method of CRC32 class.
               */
              long lngCheksum = cheksum.getValue();
              
              System.out.println("CRC32 cheksum for byte array is: " + lngCheksum);
              
          }
          
          
          /*
          Output of this program would be
          CRC32 cheksum for byte array is :3510043186
          */
    
    }
