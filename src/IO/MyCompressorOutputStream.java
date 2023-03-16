package IO;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class MyCompressorOutputStream extends OutputStream {
    private OutputStream out;

    /**
     * Constractor for the MyCompressorOutputStream object
     * @param outputStream: stream to write data to
     */
    public MyCompressorOutputStream(OutputStream outputStream) {
        this.out = outputStream;
    }

    /**
     * @implNote start by ignoring the first 24 bytes since they represent integers and are not binary.
     * For the rest of the array we save every 8 integers as a single byte scince the integers are 1 or 0.
     * If the maze isn't a multiple of 8 we will add 0's to the end of the last byte and since the decompressed method
     * knows the size of the maze because of the first two integers we know the exact number of bits to read and can
     * therefore ignore the added 0's.
     * @param maze a {@link Byte} array that represents a maze
     * @return a compressed representation of the maze passed as input
     */
    public byte[] compress(byte[] maze)
    {
        ByteBuffer buff = ByteBuffer.allocate(25+(maze.length-24)/8);


        // the first 24 bytes are 6 integers: 2 for maze size, 2 for start pos, 2 for goal pos
        int i;
        for (i = 0; i < 24; i++){
            buff.put(maze[i]);
        }
        i = 24;
        int iMax = maze.length;
        StringBuilder sb = new StringBuilder();
        while (i < iMax){
            while (sb.length()<8){

                if(i >= iMax){
                    sb.append('0');
                }
                else{
                    sb.append(Byte.toString(maze[i]));
                }
                i++;
            }

            int temp = Integer.parseInt(sb.toString(),2);
            buff.put((byte)temp);
            sb = new StringBuilder();
        }

        byte[]compressed =buff.array();
        return compressed;
    }

    /**
     * @implNote calls compress on the parameter and write to the output
     * @param b the uncompressed maze.
     * @throws IOException
     */
    @Override
    public void write(byte[] b) throws IOException {
        out.write(compress(b));
        super.write(b);
    }

    @Override
    public void write(int b) throws IOException {
    }
}
