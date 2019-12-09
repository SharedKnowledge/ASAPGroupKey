package nodesV2;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Collection;
import java.util.HashSet;

import net.sharksystem.asap.ASAPException;
import net.sharksystem.asap.apps.ASAPMessageReceivedListener;
import net.sharksystem.asap.apps.ASAPMessages;

public class GKENode_Impl {
	public static void main (String[] args) throws ASAPException, IOException {


	final CharSequence APP = "GKE";

	GKENode alice;
	GKENode bob;
	
	CharSequence uriAlice = "gke://alice";
	CharSequence uriBob = "gke://bob";
	
	Collection<CharSequence> formats = new HashSet<>();
	formats.add(APP);
	
	String name1 = "alice";
	String name2 = "bob";
	String folder1 = "root/alice";
	String folder2 = "root/bob";
	Collection<CharSequence> recipents = new HashSet<>();
	recipents.add("bob");
	BigInteger pub1 = new BigInteger("11111");
	BigInteger pub2 = new BigInteger("222222");
	ASAPMessageReceivedListener bobListener = new ASAPMessageReceivedListener() {
		
		@Override
		public void asapMessagesReceived(ASAPMessages messages) {

			this.asapMessagesReceived(messages);
		}
	};


	
	//alice = new GKENode(111111, name1, folder1, formats);
	alice = new GKENode(pub1, name1, folder1, formats, recipents);
	bob = new GKENode(pub2, name2, folder2, formats, recipents);
	
	alice.sendASAPMessage(APP, uriBob, recipents, "Hello alice bob whatever bye".getBytes());
	
	bob.setASAPMessageReceivedListener(APP, bobListener);
	
	
	}
	
}
