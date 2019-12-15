package nodesV2;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Collection;
import java.util.HashSet;

import net.sharksystem.asap.ASAPChunkReceivedListener;
import net.sharksystem.asap.ASAPEngine;
import net.sharksystem.asap.ASAPException;
import net.sharksystem.asap.apps.ASAPHandleConnectionThread;
import net.sharksystem.asap.apps.ASAPMessageReceivedListener;
import net.sharksystem.asap.apps.ASAPMessages;
import net.sharksystem.cmdline.TCPChannel;
import net.sharksystem.util.localloop.BufferedStream;

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
	ASAPMessageReceivedListener aliceListener = new ASAPMessageReceivedListener() {
		
		@Override
		public void asapMessagesReceived(ASAPMessages messages) {

			System.out.println("******************* INSIDE ALICE LISTENER");
			
			ASAPMessages m = messages;
			try {
	            System.out.println(" received "
	                    + m.getMessages().toString());
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
	};
	ASAPMessageReceivedListener bobListener = new ASAPMessageReceivedListener() {
		
		@Override
		public void asapMessagesReceived(ASAPMessages messages) {

			System.out.println("******************* INSIDE BOB LISTENER");

			ASAPMessages m = messages;
			try {
	            System.out.println(" received "
	                    + m.getMessages().toString());
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
	};


	
	//alice = new GKENode(111111, name1, folder1, formats);
	System.out.println("GKENode_Impl: Create GKENodes:");
	alice = new GKENode(pub1, name1, folder1, formats, recipents);
	bob = new GKENode(pub2, name2, folder2, formats, recipents);

	
	//System.out.println("*******************" + bobListener.toString());
	//System.put.println
	
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    //                                        create a tcp connection                                //
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    // create connections for both sides
	BufferedStream alice2bobChannel = new BufferedStream("alice2bob");
	BufferedStream bob2aliceChannel = new BufferedStream("bob2alice");

    //aliceChannel..start(); bobChannel.start();
    // wait to connect
    //aliceChannel.waitForConnection(); bobChannel.waitForConnection();

    ///////////////////////////////////////////////////////////////////////////////////////////////////
    //                                        run asap session                                       //
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    // run engine as thread
    /*ASAPHandleConnectionThread alice = new ASAPHandleConnectionThread(asapJavaApplicationAlice,
            aliceChannel.getInputStream(), aliceChannel.getOutputStream());

    aliceEngineThread.start();

    // let's start communication
    asapJavaApplicationBob.handleConnection(bobChannel.getInputStream(), bobChannel.getOutputStream());

    // wait until communication probably ends
    Thread.sleep(2000); System.out.flush(); System.err.flush();
    // close connections: note ASAPEngine does NOT close any connection!!
    aliceChannel.close(); bobChannel.close(); Thread.sleep(1000);*/
	
	ASAPEngine aliceEngineGKE = alice.getMultiEngine().getEngineByFormat(APP);
	ASAPEngine bobEngineGKE = bob.getMultiEngine().getEngineByFormat(APP);
	
	alice.handleConnection(bob2aliceChannel.getInputStream(), alice2bobChannel.getOutputStream());

	bob.handleConnection(alice2bobChannel.getInputStream(), bob2aliceChannel.getOutputStream());
	
	
	System.out.println("GKENode_Impl: Set Received Listeners:");
	alice.setASAPMessageReceivedListener(APP, aliceListener);
	bob.setASAPMessageReceivedListener(APP, bobListener);
	

	System.out.println("GKENode_Impl: Send ASAP Message:");
	alice.sendASAPMessage(APP, uriBob, recipents, "Hello alice bob whatever bye".getBytes());
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    //                                            test results                                       //
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    // received?
	

/*
	try {
		Thread.sleep(5000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}*/

	//alice2bobChannel.getOutputStream().close();
	//bob2aliceChannel.getOutputStream().close();

	while (true) {}
	}
	
}
