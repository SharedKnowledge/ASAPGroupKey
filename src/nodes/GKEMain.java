package nodes;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

import net.sharksystem.asap.ASAPChunkReceivedListener;
import net.sharksystem.asap.ASAPException;
import net.sharksystem.asap.ASAPStorage;
import net.sharksystem.asap.apps.ASAPJavaApplication;
import net.sharksystem.asap.apps.ASAPJavaApplicationFS;
import net.sharksystem.asap.apps.ASAPMessageReceivedListener;
import net.sharksystem.asap.apps.ASAPMessages;


//first drafts - ignore completely!

public class GKEMain {
//	public static void main (String[] args) throws ASAPException, IOException {
//		final CharSequence APP = "GKE";
//		final String SECOND = "second";
//		final CharSequence uri = "yourSchema://yourURI";
//		Collection<CharSequence> formats = new HashSet<>();
//		formats.add(APP);
//
//		ASAPMessageReceivedListener listener = new ASAPMessageReceivedListener() {
//
//			@Override
//			public void asapMessagesReceived(ASAPMessages arg0) {
//				// TODO Auto-generated method stub
//				System.out.println("asapMessagesReceived");
//			}
//		};
//
//		GKENode first = new GKENode(BigInteger.valueOf(11111), "first", "root/first", formats);
//		GKENode second = new GKENode(BigInteger.valueOf(22222), "second", "root/second", formats);
//		//GKENode third = new GKENode(BigInteger.valueOf(33333), "third", "root/third", formats);
//		Collection<CharSequence> recipients = new HashSet<>();
//		recipients.add(SECOND);	
//		first.getMultiEngine().createEngineByFormat(APP);
//		second.getMultiEngine().createEngineByFormat(APP);
//
//		second.setASAPMessageReceivedListener(APP, listener);
//		first.setASAPMessageReceivedListener(APP, listener); // TODO: remove
//		
//		first.sendMessage(APP, uri, recipients, "This is test message from first".getBytes());
//		second.sendMessage(APP, uri, recipients, "This is test message from second".getBytes());	
//
//
//	}
	
	public static void main (String[] args) throws ASAPException, IOException {

		
		final CharSequence  owner_1 = "alice";
		final CharSequence  owner_2 = "bob";
		final CharSequence  rootFolder_1 = "root";
		final CharSequence  rootFolder_2 = "root_2";
		Collection<CharSequence> supportedFormats = new HashSet<>();
		final CharSequence APP = "GKE";
		supportedFormats.add(APP);	
		
		final CharSequence uri = "yourSchema://yourURI";

		

		ASAPJavaApplication asapApp_1 = ASAPJavaApplicationFS.createASAPJavaApplication(owner_1, rootFolder_1, supportedFormats);
		ASAPJavaApplication asapApp_2 = ASAPJavaApplicationFS.createASAPJavaApplication(owner_2, rootFolder_2, supportedFormats);	
		ASAPStorage storageBob ;
		ASAPMessages messagesBob;
		
		Collection<CharSequence> recipients = new HashSet<>();
		Queue<Byte> q_12 = new LinkedList<Byte>();
		Queue<Byte> q_21 = new LinkedList<Byte>();

		InputStream is_1 = new In(q_21);
		OutputStream os_1 = new Out(q_12);
		InputStream is_2 = new In(q_12);
		OutputStream os_2 = new Out(q_21);

		asapApp_1.handleConnection(is_1, os_1);
		asapApp_2.handleConnection(is_2, os_2);
		recipients.add(owner_2);
		
	
		
		asapApp_1.setASAPMessageReceivedListener(APP, new ASAPMessageReceivedListener() {
			  public void asapMessagesReceived(net.sharksystem.asap.apps.ASAPMessages msg) {
				  System.out.println("asapApp_1: asapMessagesReceived: " + msg.toString() + "uri=" + msg.getURI());
				  System.out.println("**************************");
			  }
		});
		
		asapApp_2.setASAPMessageReceivedListener(APP, new ASAPMessageReceivedListener() {
			  public void asapMessagesReceived(net.sharksystem.asap.apps.ASAPMessages msg) {
				  System.out.println("asapApp_2: asapMessagesReceived: " + msg.toString() + "uri=" + msg.getURI());
				  System.out.println("**************************");

			  }
		});
		
		asapApp_1.sendASAPMessage(APP, uri, recipients, "This is test message from first".getBytes());
		
		
	}
	
	static class In extends InputStream {
		private Queue<Byte> q;

		public In(Queue<Byte> q) {
			this.q = q; //new LinkedList<Byte>();
		}
		
		@Override
		public int read() throws IOException {
			//System.out.println("rrrrrrrrrrrrrrr");
			if (q.size() == 0) return -1;
			// while (q.size() == 0) {}
			int i = this.q.remove();
			//System.out.println("RRRRRRRRRRRRRRR " + String.valueOf((char) i)); // TODO: valueOf is ASCII only (no UTF8!!111!!!!!!!!!!!)
			return i;
		}
	}
	
	static class Out extends OutputStream {
		private Queue<Byte> q;

		public Out(Queue<Byte> q) {
			this.q = q; //new LinkedList<Byte>();
		}

		@Override
		public void write(int i) throws IOException {
			//System.out.println("WWWWWWWWWWWWWWWWWWWWW " + String.valueOf((char) i));
			// TODO Auto-generated method stub
			this.q.add((byte) i);
		}
	}

}


