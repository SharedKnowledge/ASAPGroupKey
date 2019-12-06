package gkenodes;

import java.io.IOException;

import net.sharksystem.asap.ASAPChunkReceivedListener;
import net.sharksystem.asap.ASAPException;
import net.sharksystem.asap.ASAPStorage;

public class ReceivedListener implements ASAPChunkReceivedListener {

    private final GKEStorage gkeStorage;
    
    public ReceivedListener(GKEStorage gkeStorage) {
        this.gkeStorage = gkeStorage;
    }

    public ReceivedListener(ASAPStorage asapStorage) {
        this.gkeStorage = new GKEStorage_Impl(asapStorage);
    }
	@Override
	public void chunkReceived(String sender, String uri, String arg2, int era) {
	      try {
	            this.gkeStorage.getGKE(uri);
	            System.out.println(this.getLogStart() + "GKE exists: " + uri);
	        } catch (IOException e) {
	            System.err.println(this.getLogStart() + "read problems:" + e.getLocalizedMessage());
	            return;
	        } catch (ASAPException e) {
	            System.out.println(this.getLogStart() + "GKE does not exist: " + uri);
	            try {
	                this.gkeStorage.createGKE(uri, "From: " + sender);
	            } catch (IOException | ASAPException ex) {
	                System.err.println(this.getLogStart() + "could not create local copy of open GKE"
	                        + ex.getLocalizedMessage());
	                return;
	            }
	        }		
	}
	
    private String getLogStart() {
        return "OREceivedListener: ";
    }

}
