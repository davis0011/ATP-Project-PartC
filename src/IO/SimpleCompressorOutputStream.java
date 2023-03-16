package IO;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * An extension of {@link OutputStream} that adds a compression method to reduce the size of binary inputs
 */
public class SimpleCompressorOutputStream extends OutputStream {
    private OutputStream out;
    public SimpleCompressorOutputStream(OutputStream outputStream) {
        out = outputStream;
    }

    /**
     * @implNote start by ignoring the first 24 bytes since they represent integers and are not binary.
     * For the rest of the array we start by counting the number of consecutive 0s, we then write that number
     * as the first byte. If the number of consecutive 0s is more than 255 we write 255, 0 to represent there being
     * 0 1s after the 255 0s, and we then write another byte for the remaining 0s.
     * @param maze a {@link Byte} array that represents a maze
     * @return a compressed representation of the maze passed as input
     */
    public byte[] compress(byte[] maze)
    {
        ArrayList<Byte> myList = new ArrayList<>();
        int i = 24;
        int counter = 0;
        int next = 24;
        byte curr = maze[i];
        while(i< maze.length)
        {
            if(maze[i] == curr && counter <255)
            {
                counter++;
                curr = maze[i];
            }
            else{
                myList.add((byte)(counter));
                curr = maze[i];
                if(i+1 < maze.length && counter == 255 && curr == maze[i+1])
                {
                    myList.add((byte) 0);
                }
                counter = 1;
            }
            i++;
        }
        myList.add((byte)counter);
        byte[] compressed = new byte[myList.size() + 25];
        for (int j = 0; j < 24; j++) {
            compressed[j] = maze[j];
        }
        if(maze[24] != 0)
        {
            myList.add((byte)0);
        }
        for (int j = 0; j < myList.size(); j++) {
            compressed[next] = myList.get(j);
            next++;
        }
        return compressed;
    }

    /**
     * @param b the data to write, can be compressed first with the compress method
     * @throws IOException
     */
    @Override
    public void write(byte[] b) throws IOException {
        out.write(compress(b));
        super.write(b);
    }

    /**
     * Not required to implement for assignment
     * @param b the <code>byte</code>.
     * @throws IOException
     */
    @Override
    public void write(int b) throws IOException {
    }
}
