import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.jxtainer.*;

/**
 * 
 * Voici un exemple d'implémentation d'un espace de discution (chat) en utilisant
 * la librairie JXtainer 1.0
 * 
 * @author Mounes Ronan
 *
 */

public class HelloChatWorld {

	// Pour les entrées au clavier :
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	// Identifiant du chat sur le réseau JXTA :
	static final String SUFFIX = "HelloChatWorldJXtainer";
	// Fenêtre JXTA pour configurer un proxy :
	static boolean firewall = false;

	public static void main(String[] args) {

		// Login par défaut :
		String login = "Anonyme";

		/** 
		 * Si vous êtes derrière un proxy :
		 * java HelloChatWorld --proxy --login Ronan 
		 * Si vous êtes directement connecté à internet :
		 * java HelloChatWorld --login Ronan
		 */
		for (int i = 0; i < args.length; i++) {
			if ("--login".equals(args[i]) && ++i < args.length)
				login = args[i];
			if ("--proxy".equals(args[i]))
				firewall = true;
		}

		// Evenement lorsque l'on reçoit un message du chat :
		StartJXTA.addJxMessageListener(new JxMessageLister() {
			public void receiveMessage() {
				J3Message message = J3MessagePipe.drop();
				consoleOut(message.getWho() + " a dit : " + message.getWhat());
			}
		});
		// Evenement de connexion		
		StartJXTA.addJxStatutListener(new JxStatutListener() {
			public void newStatut() {
				if (J3xta.JXTA_STATUT_CONNECT == J3xta.getStatut())
					consoleOut("Vous êtes sur le chat !");
			}
		});
		// Evenement lorsqu'une nouvelle personne arrive sur le chat		
		StartJXTA.addJxPeerListener(new JxPeerListener() {
			public void newPeer() {
				consoleOut(J3PeerManager.getLatest().getName() + " vient d'arriver sur le chat !");
			}
			public void deletePeer() {
				consoleOut(J3PeerManager.getLatest().getName() + " vient de partir du chat !");
			}
		});

		// Démarrage du chat...
		StartJXTA.wakeUp(login, SUFFIX, null, firewall);

		consoleOut("Recherche du chat sur le réseau...");

		try {
			while (StartJXTA.isConnected()) {
				Thread.sleep(100);
			}
		} catch (InterruptedException e) {
			consoleOut("Impossible de se connecter : " + e.toString());
			System.exit(0);
		}

		// Pipe pour envoyer les messages sur le chat :
		J3Pipe pipe = StartJXTA.getPipe();

		// On peut dialoguer !
		while (true) {
			pipe.sendMsg(consoleIn(":"));
		}
	}

	public static void consoleOut(String s) {
		System.out.println(s);
	}

	public static String consoleIn(String s) {
		try {
			return in.readLine();
		} catch (IOException e) {
			return "ERROR";
		}
	}
}
