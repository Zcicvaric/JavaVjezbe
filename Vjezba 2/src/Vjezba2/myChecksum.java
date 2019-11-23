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
public interface myChecksum {
    /**
     * Updates the current checksum with the specified byte.
     *
     * @param b the byte to update the checksum with
     */
    public void update(int b);

    /**
     * Updates the current checksum with the specified array of bytes.
     * @param b the byte array to update the checksum with
     * @param off the start offset of the data
     * @param len the number of bytes to use for the update
     */
    public void update(byte[] b, int off, int len);

    /**
     * Returns the current checksum value.
     * @return the current checksum value
     */
    public long getValue();

    /**
     * Resets the checksum to its initial value.
     */
    public void reset();
}
