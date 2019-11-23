
public class CRC32 implements Checksum {
    private int crc;

   
    public CRC32() {
    }

  
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

    @Override
    public long getValue() {
        return (long)crc & 0xffffffffL;
    }

    @Override
    public void reset() {
        crc = 0;
    }

    @Override
    public void update(int b) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}