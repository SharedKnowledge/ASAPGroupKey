package gkenodes;



import java.io.IOException;
import java.util.List;

import net.sharksystem.asap.ASAPException;
import net.sharksystem.asap.ASAPStorage;

public class GKEStorage_Impl implements GKEStorage {
    private final ASAPStorage asapStorage;

    public GKEStorage_Impl (ASAPStorage asapStorage) {
    	
    	this.asapStorage = asapStorage;
    }
	@Override
	public int size() throws IOException, ASAPException {

		return this.asapStorage.getChannelURIs().size();
	}

	@Override
	public CharSequence getOwner() {
		
		return this.asapStorage.getOwner();
	}

	@Override
	public GKENode createGKE(CharSequence uri, CharSequence userFriendlyName) throws IOException, ASAPException {
		//check presence
        try {
            this.getGKE(uri);
            throw new ASAPException("channel already exists: " + uri);
        }
        catch(ASAPException e) {
        	//nothing, this is what we need 
        }
        this.asapStorage.putExtra(uri, KEY_GKE_NAME, userFriendlyName.toString());
		
		return new GKENode(uri, this);
	
	}

	@Override
	public void removeGKE(CharSequence uri) throws IOException, ASAPException {
        this.asapStorage.removeChannel(uri);		
	}

	@Override
	public void removeAllGKE() throws IOException {
	    List<CharSequence> channelURIs = this.asapStorage.getChannelURIs();

        for(CharSequence uri : channelURIs) {
            try {
				this.removeGKE(uri);
			} catch (ASAPException e) {
				e.printStackTrace();
			}
        }		
	}

	@Override
	public GKENode getGKE(CharSequence uri) throws IOException, ASAPException {

        GKENode gke = new GKENode(uri, this);

        if(gke == null) {
            throw new ASAPException("this gke does not exist");
        }

        return gke;
	}

	@Override
	public GKENode getGKE(int position) throws IOException, ASAPException {

        try {
            return this.getGKE(this.getASAPStorage().getChannelURIs().get(position));
        }
        catch(IndexOutOfBoundsException e) {
            throw new ASAPException("position points out of available range. Position is : " + position);
        }
	}
	

	@Override
	public void refresh() throws IOException, ASAPException {
		this.asapStorage.refresh();		
	}

	@Override
	public ASAPStorage getASAPStorage() {
	
        return this.asapStorage;
	}


}
