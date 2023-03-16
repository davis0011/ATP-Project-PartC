package Server;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Interface for the strategy a server applies to its connected clients
 */
public interface IServerStrategy {
    void applyStrategy(InputStream inFromClient, OutputStream outToClient);
}
