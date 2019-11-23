/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vjezba2;

/**
 *
 * @author Zvonimir Cicvarić
 */
public
class myCRC32 implements myChecksum {
    private int crc;

    /**
     * Creates a new CRC32 object.
     */
    public myCRC32() {
    }
    
    private static int[] crc_table = make_crc_table();
    
    private static int[] make_crc_table () {
        int[] crc_table = new int[256];
        for (int n = 0; n < 256; n++){
            int c = n;
            for(int k = 8;  --k >= 0;) {
                if ((c & 1) != 0)
                    c = 0xedb88320 ^ (c >>> 1);
                else
                    c = c >>> 1;
            }
            crc_table[n] = c;
        }
        return crc_table;
    }


    /**
     * Updates the CRC-32 checksum with the specified byte (the low
     * eight bits of the argument b).
     *
     * @param b the byte to update the checksum with
     */
    public void update (int bval) {
        int c = ~crc;
        c = crc_table[(c ^ bval) & 0xff] ^ (c >>> 8);
        crc = ~c;
    }

    /**
     * Updates the CRC-32 checksum with the specified array of bytes.
     */
    public void update(byte[] b, int off, int len) {
        if (b == null) {
            throw new NullPointerException();
        }
        if (off < 0 || len < 0 || off > b.length - len) {
            throw new ArrayIndexOutOfBoundsException();
        }
            crc  = 0xFFFFFFFF;       // initial contents of LFBSR
        int poly = 0xEDB88320;   // reverse polynomial

        for (byte a : b) {
            int temp = (crc ^ a) & 0xff;

            for (int i = 0; i < 8; i++) {
                if ((temp & 1) == 1) temp = (temp >>> 1) ^ poly;
                else                 temp = (temp >>> 1);
            }
            crc = (crc >>> 8) ^ temp;
        }

        crc = crc ^ 0xffffffff;
    }

    /**
     * Updates the CRC-32 checksum with the specified array of bytes.
     *
     * @param b the array of bytes to update the checksum with
     */
    public void update(byte[] b) {
        crc = updateBytes(crc, b, 0, b.length);
    }

    /**
     * Resets CRC-32 to initial value.
     */
    public void reset() {
        crc = 0;
    }

    /**
     * Returns CRC-32 value.
     */
    public long getValue() {
        return (long)crc & 0xffffffffL;
    }

    private int updateBytes(int crc, byte[] b, int off, int len){

            crc  = 0xFFFFFFFF;       // initial contents of LFBSR
            int poly = 0xEDB88320;   // reverse polynomial

            for (byte a : b) {
                int temp = (crc ^ a) & 0xff;

                for (int i = 0; i < 8; i++) {
                    if ((temp & 1) == 1) temp = (temp >>> 1) ^ poly;
                    else                 temp = (temp >>> 1);
                }
                crc = (crc >>> 8) ^ temp;
            }

            crc = crc ^ 0xffffffff;
            return crc;
    }

}