package IO;

import java.io.IOException;
import java.io.InputStream;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class MyDecompressorInputStream extends InputStream {
    private InputStream input;


    public MyDecompressorInputStream(InputStream input){
        this.input = input;
    }

    /**
     * @implNote translate first 24 byts into 6 ints for the maze dim,start and goal, translate the rest of the bytes
     * into decompressed byte values Ex. 3 = 00000011 -> [0,0,0,0,0,0,1,1] see: {@link MyCompressorOutputStream}
     * @param maze The uncompressed maze representation that we want to compress
     * @return  The compressed maze byte array
     */
    public byte[] decompress(byte[] maze)
    {
        ByteBuffer buffer = ByteBuffer.wrap(maze);
        int mazeRows = buffer.getInt();
        int mazeCols = buffer.getInt();
        ByteBuffer buff = ByteBuffer.allocate(24+mazeCols*mazeRows);
        int i;
        for (i = 0; i < 24; i++){
            buff.put(maze[i]);
        }
        byte[] toBringBack = new byte[8];
        i = 24;
        int iMax = 24+mazeCols*mazeRows;
        buffer.position(24);
        while(true){
        try{
            String temp = Integer.toBinaryString(Byte.toUnsignedInt(buffer.get()));
            if(temp.length()!= 8){
                StringBuilder sb = new StringBuilder();
                int len = temp.length();
                while (len < 8){
                    sb.append(0);
                    len++;
                }
                temp = sb.append(temp).toString();
            }
            for (int k = 0; k < 8; k++) {


                byte b = (byte) Character.getNumericValue(temp.charAt(k));
                toBringBack[k] = b;
            }
            for (int k = 0; k < 8; k++) {
                if (i == iMax){throw new BufferUnderflowException();}
                buff.put(toBringBack[k]);
                i++;
            }
        }
        catch (BufferUnderflowException e){
            break;
        }
        }

        return buff.array();
    }


    /**
     * @param b the buffer into which the data is read.
     * @return the length of the byte array, -1 if nothing was read
     * @throws IOException
     */
    @Override
    public int read(byte[] b) throws IOException {
        byte[] arr = new byte[b.length];
        if (input.read(arr) == -1){
            return -1;
        }
        arr = decompress(arr);
        for (int i = 0; i < arr.length; i++) {
            b[i] = arr[i];
        }
        for (int i = arr.length; i < b.length; i++) {
            b[i] = (byte) 0;
        }
        return b.length;
    }


    @Override
    public int read() throws IOException {
        return 0;
    }
}
