package Client;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * An interface to dictate the behaviour of {@link Client}s to their connected {@link Server.Server}s
 */
public interface IClientStrategy {

    /**
     * Method for applying the strategy to a connected {@link Server.Server}
     * @param inFromServer Input Stream for the {@link Server.Server} so we can read incoming information
     * @param outToServer Output Stream for the {@link Server.Server} so we can write information
     */
    void clientStrategy(InputStream inFromServer, OutputStream outToServer);

}
