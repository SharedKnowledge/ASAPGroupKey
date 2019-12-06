package gkenodes;

import java.io.IOException;

import net.sharksystem.asap.ASAPException;
import net.sharksystem.asap.ASAPStorage;

public interface GKEStorage {
    String KEY_GKE_NAME = "GKEName";
    String KEY_ADMIN_ID = "GKEAdminID";

    /**
     *
     * @return number of GKE within that storage
     * @throws IOException
     * @throws ASAPException
     */
    int size() throws IOException, ASAPException;

    CharSequence getOwner();

//    /**
//     * Create a closed / administrated GKE - admin can change recipient list
//     * @param uri
//     * @param userFriendlyName
//     * @param adminID
//     * @return
//     * @throws IOException
//     * @throws ASAPException
//     */
//    GKENode createGKE(CharSequence uri, CharSequence userFriendlyName, CharSequence adminID)
//            throws IOException, ASAPException;

    /**
     * set up open GKE - no admin, no control who is sending what
     * @param uri
     * @param userFriendlyName
     * @return
     * @throws IOException
     * @throws ASAPException
     */
    GKENode createGKE(CharSequence uri, CharSequence userFriendlyName)
            throws IOException, ASAPException;

    void removeGKE(CharSequence uri) throws IOException, ASAPException;

    void removeAllGKE() throws IOException;

    /**
     * get GKE with a given uri
     * @param uri
     * @return
     * @throws IOException io problems when accessing data
     * @throws ASAPException GKE does not exist
     */
    GKENode getGKE(CharSequence uri) throws IOException, ASAPException;

    GKENode getGKE(int position) throws IOException, ASAPException;
    

    void refresh() throws IOException, ASAPException;

    public ASAPStorage getASAPStorage();
}
