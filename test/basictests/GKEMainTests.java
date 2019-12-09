//package basictests;
//
//
//import java.io.IOException;
//import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Locale;
//
//import org.junit.Assert;
//import org.junit.Test;
//
//import gkenodes.GKEMessage;
//import gkenodes.GKEMessage_Impl;
//import gkenodes.GKENode;
//import gkenodes.GKEStorage;
//import gkenodes.GKEStorage_Impl;
//import gkenodes.GroupKeyExchange;
//import gkenodes.ReceivedListener;
//import net.sharksystem.asap.ASAPChunkReceivedListener;
//import net.sharksystem.asap.ASAPEngine;
//import net.sharksystem.asap.ASAPEngineFS;
//import net.sharksystem.asap.ASAPException;
//import net.sharksystem.asap.ASAPStorage;
//import net.sharksystem.asap.MultiASAPEngineFS;
//import net.sharksystem.asap.MultiASAPEngineFS_Impl;
//import net.sharksystem.asap.util.ASAPChunkReceivedTester;
//import net.sharksystem.asap.util.ASAPEngineThread;
//import net.sharksystem.cmdline.TCPChannel;
//
//import static net.sharksystem.asap.MultiASAPEngineFS.DEFAULT_MAX_PROCESSING_TIME;
//
//public class GKEMainTests {
//    public static final String AN_OPEN_GKE_URI = "content://someOpenTopic";
//    public static final String AN_OPEN_GKE_TITLE = "an open topic";
//    public static final String ALICE_BOB_CHAT_URL = "content://aliceAndBob.talk";
//    public static final String ALICE_BOB_GKE_NAME = "Alice and Bob talk";
//    public static final String ALICE_FOLDER = "tests/alice";
//    public static final String BOB_FOLDER = "tests/bob";
//    public static final String ALICE = "alice";
//    public static final String BOB = "bob";
//    public static final String CLARA = "clara";
//    public static final String ALICE2BOB_MESSAGE = "Hi Bob";
//    public static final String BOB2ALICE_MESSAGE = "Hi Alice";
//    private static final CharSequence ALICE_ID = "42";
//    private static final CharSequence BOB_ID = "43";
//
//    private static int portnumber = 7777;
//
//    private int getPortNumber() {
//        portnumber++;
//        return portnumber;
//    }
//
//    @Test
//    public void create() throws IOException, ASAPException {
//        ASAPEngineFS.removeFolder(ALICE_FOLDER); // clean previous version before
//        ASAPEngineFS.removeFolder(BOB_FOLDER); // clean previous version before
//
//        ASAPStorage asapStorage = ASAPEngineFS.getASAPStorage(ALICE, ALICE_FOLDER, GKENode.FORMAT);
//
//        GKEStorage gkeStorage = new GKEStorage_Impl(asapStorage);
//        try {
//            gkeStorage.getGKE(ALICE_BOB_CHAT_URL);
//        }
//        catch(ASAPException e) {
//            System.out.println("no gke found ");
//        }
//
//        gkeStorage.createGKE(ALICE_BOB_CHAT_URL, ALICE_BOB_GKE_NAME);
//
//        Assert.assertNotNull(gkeStorage.getGKE(ALICE_BOB_CHAT_URL));
//    }
//
//    @Test
//    public void gkeChainTest() throws IOException, ASAPException, ParseException {
//        ASAPEngineFS.removeFolder(ALICE_FOLDER); // clean previous version before
//        ASAPEngineFS.removeFolder(BOB_FOLDER); // clean previous version before
//
//        ASAPEngine asapAliceStorage = ASAPEngineFS.getASAPStorage(ALICE, ALICE_FOLDER, GKENode.FORMAT);
//        GKEStorage_Impl aliceGKEStorage = new GKEStorage_Impl(asapAliceStorage);
//
//        // create incoming storages
//        ASAPEngineFS.getASAPStorage(BOB, ALICE_FOLDER + "/" + BOB, GKENode.FORMAT);
//        ASAPEngineFS.getASAPStorage(CLARA, ALICE_FOLDER + "/" + CLARA, GKENode.FORMAT);
//
//        GKEStorage_Impl bobAtAliceGKEStorage =
//                new GKEStorage_Impl(asapAliceStorage.getExistingIncomingStorage(BOB));
//
//        GKEStorage_Impl claraAtAliceGKEStorage =
//                new GKEStorage_Impl(asapAliceStorage.getExistingIncomingStorage(CLARA));
//
//        GKENode aliceGKE =
//                (GKENode) aliceGKEStorage.getGKE(AN_OPEN_GKE_URI);
//        GKENode bobGKE =
//                (GKENode) bobAtAliceGKEStorage.getGKE(AN_OPEN_GKE_URI);
//        GKENode claraGKE =
//                (GKENode) claraAtAliceGKEStorage.getGKE(AN_OPEN_GKE_URI);
//
//        String[] message = new String[] {"0", "Test Alice Bob Whatever Bye",};
//
//       // DateFormat df = DateFormat.getInstance();
//        String dateString = "01.01.01 10:00";
//        SimpleDateFormat sdf = new SimpleDateFormat("dd.mm.yy HH:mm",
//                Locale.ENGLISH);
//       // Date date = df.parse(dateString);
//        Date date = sdf.parse(dateString);
//        aliceGKE.addMessage(message[0], date);
//        aliceGKE.addMessage(message[1], sdf.parse("01.01.01 10:01"));
//
//        //get GKE
//        GKENode aliceM = aliceGKEStorage.getGKE(AN_OPEN_GKE_URI);
//        GKEMessage m = null;
//        String messageString = null;
//
//        // jump in the middle
//        m = aliceM.getMessage(7, true);
//        System.out.println("message is :" + m.toString() + "and date is :");
//        Assert.assertNotNull(m);
//        messageString = m.getContentAsString().toString();
//        Assert.assertNotNull(messageString);
//        System.out.println("Message 7 is:" + message[1]);
//        Assert.assertTrue(messageString.equalsIgnoreCase(message[1]));
//
//        //eset GKE 
//        aliceM = aliceGKEStorage.getGKE(AN_OPEN_GKE_URI);
//        for(int i = 0; i < 2; i++) {
//            Assert.assertNotNull(m);
//            messageString = m.getContentAsString().toString();
//            Assert.assertNotNull(messageString);
//            //Assert.assertTrue(messageString.equalsIgnoreCase(message[i]));  //currently fails since there is only 1 message 
//        }
//    //}
//    
//    for(int i = 0; i < 0x10; i++) {
//        /*
//        System.out.println("index = " + i);
//        if(i == 2) {
//            int x = 42;
//        }
//         */
//        m = aliceM.getMessage(i, true);
//        Assert.assertNotNull(m);
//        messageString = m.getContentAsString().toString();
//        Assert.assertNotNull(messageString);
//        Assert.assertTrue(messageString.equalsIgnoreCase(message[i]));
//    }
//}
//
//	@Test
//	public void oneWayExchangeOpenGKE() throws IOException, ASAPException, InterruptedException {
//    ASAPEngineFS.removeFolder(ALICE_FOLDER); // clean previous version before
//    ASAPEngineFS.removeFolder(BOB_FOLDER); // clean previous version before
//
//    ///////////////////////////////////////////////////////////////////////////////////////////////////
//    //                                        prepare multi engines                                  //
//    ///////////////////////////////////////////////////////////////////////////////////////////////////
//
//    ASAPChunkReceivedTester aliceListener = new ASAPChunkReceivedTester();
//    MultiASAPEngineFS aliceEngine = MultiASAPEngineFS_Impl.createMultiEngine(
//            ALICE, ALICE_FOLDER, DEFAULT_MAX_PROCESSING_TIME, aliceListener);
//
//    MultiASAPEngineFS bobEngine = MultiASAPEngineFS_Impl.createMultiEngine(
//            BOB, BOB_FOLDER, DEFAULT_MAX_PROCESSING_TIME, null);
//
//    ///////////////////////////////////////////////////////////////////////////////////////////////////
//    //                                        create some content                                    //
//    ///////////////////////////////////////////////////////////////////////////////////////////////////
//
//    ASAPEngine aliceGKEASAPEngine = aliceEngine.getASAPEngine(GroupKeyExchange.APP);
//    GKEStorage GKEAliceStorage = new GKEStorage_Impl(aliceGKEASAPEngine);
//
//    ASAPEngine bobGKEASAPEngine = bobEngine.getASAPEngine(GroupKeyExchange.APP);
//    ASAPChunkReceivedListener bobListener = new ReceivedListener(bobGKEASAPEngine);
//    bobEngine.setASAPChunkReceivedListener(GroupKeyExchange.APP, bobListener);
//
//    GKEStorage GKEBobStorage = new GKEStorage_Impl(bobGKEASAPEngine);
//
//    // create open GKE
//
// GKENode openAliceGKE = GKEAliceStorage.createGKE(AN_OPEN_GKE_URI, AN_OPEN_GKE_TITLE);
//
//    // put something in
//    String aliceOpenMessage = "I'd like to say something about that open topic";
//    openAliceGKE.addMessage(aliceOpenMessage);
//
//    ///////////////////////////////////////////////////////////////////////////////////////////////////
//    //                                        setup connection                                       //
//    ///////////////////////////////////////////////////////////////////////////////////////////////////
//
//    int portNumber = this.getPortNumber();
//    // create connections for both sides
//    TCPChannel aliceChannel = new TCPChannel(portNumber, true, "a2b");
//    TCPChannel bobChannel = new TCPChannel(portNumber, false, "b2a");
//
//    aliceChannel.start();
//    bobChannel.start();
//
//    // wait to connect
//    aliceChannel.waitForConnection();
//    bobChannel.waitForConnection();
//
//    ///////////////////////////////////////////////////////////////////////////////////////////////////
//    //                                        run asap connection                                    //
//    ///////////////////////////////////////////////////////////////////////////////////////////////////
//
//    // run engine as thread
//    ASAPEngineThread aliceEngineThread = new ASAPEngineThread(aliceEngine,
//            aliceChannel.getInputStream(), aliceChannel.getOutputStream());
//
//    aliceEngineThread.start();
//
//    // and better debugging - no new thread
//    bobEngine.handleConnection(bobChannel.getInputStream(), bobChannel.getOutputStream());
//
//    // wait until communication probably ends
//    System.out.flush();
//    System.err.flush();
//    Thread.sleep(5000);
//    System.out.flush();
//    System.err.flush();
//
//    // close connections: note ASAPEngine does NOT close any connection!!
//    aliceChannel.close();
//    bobChannel.close();
//    System.out.flush();
//    System.err.flush();
//    Thread.sleep(1000);
//    System.out.flush();
//    System.err.flush();
//
//    // check results
//    GKENode openBobGKE = GKEBobStorage.getGKE(AN_OPEN_GKE_URI);
//    GKEMessage message = openBobGKE.getMessage(0, true);
//    message.getContentAsString().toString().equalsIgnoreCase(aliceOpenMessage);
//}
//    
//}
