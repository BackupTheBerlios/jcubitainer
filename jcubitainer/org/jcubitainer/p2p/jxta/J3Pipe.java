package org.jcubitainer.p2p.jxta;

import java.io.IOException;
import java.io.InputStream;

import net.jxta.discovery.DiscoveryService;
import net.jxta.document.AdvertisementFactory;
import net.jxta.endpoint.Message;
import net.jxta.endpoint.MessageElement;
import net.jxta.endpoint.StringMessageElement;
import net.jxta.id.IDFactory;
import net.jxta.pipe.InputPipe;
import net.jxta.pipe.OutputPipe;
import net.jxta.pipe.PipeID;
import net.jxta.pipe.PipeMsgEvent;
import net.jxta.pipe.PipeMsgListener;
import net.jxta.pipe.PipeService;
import net.jxta.protocol.PipeAdvertisement;

import org.jcubitainer.p2p.StartJXTA;
import org.jcubitainer.tools.Process;

/**
 * This exapmle illustrates how to use the OutputPipeListener interface
 *  
 */

public class J3Pipe extends Process implements PipeMsgListener {

	private final static String SENDERNAME = "JxtaTalkSenderName";

	private final static String SENDERGROUPNAME = "GrpName";

	private final static String SENDERMESSAGE = "JxtaTalkSenderMessage";

	private InputPipe inputpipe;

	private OutputPipe outputpipe;

	private PipeService pipe;

	private DiscoveryService discovery;

	private PipeAdvertisement pipeAdv;

	private J3Group group;

	private int ping_id = 0;

	/**
	 * the thread which creates (resolves) the output pipe and sends a message
	 * once it's resolved
	 */

	public void action() {
		try {

			discovery.publish(pipeAdv);
			sendMsg("ping");
			System.out.print("|");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Starts jxta, and get the pipe, and discovery service
	 */
	public J3Pipe(J3Group g) {
		super(10000);

		group = g;

		pipe = g.getPipeService();

		// get the pipe service, and discovery
		discovery = group.getDiscoveryService();

		pipeAdv = (PipeAdvertisement) AdvertisementFactory
				.newAdvertisement(PipeAdvertisement.getAdvertisementType());

		pipeAdv.setPipeID(getUniquePipeID());
		pipeAdv.setName(J3xta.JXTA_ID + "PIPE" + StartJXTA.name);
		pipeAdv.setType(PipeService.PropagateType);

		try {
			inputpipe = pipe.createInputPipe(pipeAdv, this);
			outputpipe = pipe.createOutputPipe(pipeAdv, 100);

			sendMsg("*** has joined " + group.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Enqueue messages
	 * 
	 * @param gram
	 *            message to send
	 */
	public void sendMsg(String gram) {
		try {
			Message msg = new Message();
			msg.addMessageElement(null, new StringMessageElement(SENDERMESSAGE,
					gram, null));
			msg.addMessageElement(null, new StringMessageElement(SENDERNAME,
					StartJXTA.name, null));
			msg.addMessageElement(null, new StringMessageElement(
					SENDERGROUPNAME, group.toString(), null));
			outputpipe.send(msg);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void pipeMsgEvent(PipeMsgEvent event) {
		Message msg = event.getMessage();
		try {
			String sender = getTagString(msg, SENDERNAME, "anonymous");
			String groupname = getTagString(msg, SENDERGROUPNAME, "unknown");
			String senderMessage = getTagString(msg, SENDERMESSAGE, null);
			String msgstr;
			if (groupname.equals(group.toString())) {
				//message is from this group
				msgstr = sender + "> " + senderMessage;
			} else {
				msgstr = sender + "@" + groupname + "> " + senderMessage;
				//                return;
			}

			System.out.println("PIPEIN:" + msgstr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Retrieves the content of the message labeled by tag
	 * 
	 * @param msg
	 *            the message to retrieve the data from
	 * @param tag
	 *            the identifying tag for the message
	 * @return a byte array containg the content
	 * @throws Exception
	 *             if the data could not be retrieved
	 */
	protected byte[] getTagValue(Message msg, String tag) throws Exception {

		byte[] buffer = null;
		MessageElement elem = msg.getMessageElement(null, tag);
		// Remove the element from the message
		if (elem != null) {
			msg.removeMessageElement(elem);
			InputStream ip = elem.getStream();
			if (ip != null) {
				buffer = new byte[ip.available()];
				ip.read(buffer);
			}
			return buffer;
		} else {
			return null;
		}

	}

	/**
	 * Retrieves the content of the message labeled by tag
	 * 
	 * @param msg
	 *            the message to retrieve the data from
	 * @param tag
	 *            the identifying tag for the message
	 * @param defaultValue
	 *            the default value to return if no data are found
	 * @return the value of the indicated tag or the default value
	 * @throws Exception
	 *             if the data could not be retrieved
	 */
	protected String getTagString(Message msg, String tag, String defaultValue)
			throws Exception {
		byte[] buffer = getTagValue(msg, tag);
		String result;

		if (buffer != null) {
			result = new String(buffer);
		} else {
			result = defaultValue;
		}

		return result;
	}

	/**
	 * Generate uniquePipeID that is independantly unique within a group
	 * 
	 * @return The uniquePipeID value
	 */
	private PipeID getUniquePipeID() {

		byte[] preCookedPID = { (byte) 0xD1, (byte) 0xD1, (byte) 0xD1,
				(byte) 0xD1, (byte) 0xD1, (byte) 0xD1, (byte) 0xD1,
				(byte) 0xD1, (byte) 0xD1, (byte) 0xD1, (byte) 0xD1,
				(byte) 0xD1, (byte) 0xD1, (byte) 0xD1, (byte) 0xD1, (byte) 0xD1 };

		PipeID id = (PipeID) IDFactory.newPipeID(group.getPeerGroupID(),
				preCookedPID);

		return id;

	}
	
}

