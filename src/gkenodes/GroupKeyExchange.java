package gkenodes;

import java.io.IOException;
import java.util.List;

import net.sharksystem.asap.ASAPException;

public interface GroupKeyExchange {
    CharSequence FORMAT = "gke";
    CharSequence APP = "gke";

    /**
     * user friendly name
     * @return
     */
    CharSequence getName() throws IOException;

    /**
     * get uri of this makan
     * @return
     */
    CharSequence getURI() throws IOException;

    /**
     * Return member ids
     */
    List<CharSequence> getMemberIDs() throws IOException;

    //void addMember(CharSequence newMemberID)  throws ASAPException, IOException;

    //void removeMember(CharSequence newMemberID)  throws ASAPException, IOException;

    GKEMessage getMessage(CharSequence uri)
            throws ASAPException, IOException;
    
    GKEMessage getMessage(int position, boolean chrono);

    void addMessage(CharSequence contentAsCharacter)
            throws ASAPException, IOException;

    //int size() throws IOException;
}
