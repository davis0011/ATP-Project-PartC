package IO;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * The requested compression in stream, runs a simple compression on the binary part of an inputted maze
 */
public class SimpleDecompressorInputStream extends InputStream {
    private FileInputStream fi;
    public SimpleDecompressorInputStream(FileInputStream fileIn){fi = fileIn;}


    /**
     * Not needed for assignment
     * @return
     * @throws IOException
     */
    @Override
    public int read() throws IOException {
        return 0;
    }

    /**
     * @param b the buffer into which the data is read.
     * @return the length of the byte array, -1 if nothing was read
     * @throws IOException
     */
    public int read(byte[] b) throws IOException {
        byte[] arr = new byte[b.length];
        if (fi.read(arr) == -1){
            return -1;
        }
        arr = decompress(arr);
        for (int i = 0; i < arr.length; i++) {
            b[i] = arr[i];
        }

        return b.length;
    }

    /**
     * @param maze The uncompressed maze representation that we want to compress
     * @return  The compressed maze byte array
     */
    public byte[] decompress(byte[] maze){
//        int temp = i & 0xff;
        ArrayList<Byte> mylist = new ArrayList<>();
        int i = 24;
        int counter = 0;
        int next = 24;
        int currnum = 0;
        int curr;
        int forsize = 0;
        while(i< maze.length)
        {
            curr = maze[i] & 0xff;
            forsize += curr;
            for (int j = 0; j < curr; j++) {
                mylist.add((byte)currnum);
            }
            i++;
            if(currnum == 0){
                currnum = 1;
            }
            else{
                currnum = 0;
            }
        }
        byte[] decompressed = new byte[forsize + 25];
        for (int j = 0; j < 24; j++) {
            decompressed[j] = maze[j];
        }
        if(maze[24] != 0)
        {
            mylist.add((byte)0);
        }
        for (int j = 0; j < forsize; j++) {
            decompressed[next] = mylist.get(j);
            next++;
        }
        return decompressed;
    }
}
