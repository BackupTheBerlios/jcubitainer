/***********************************************************************
 * HelloChatWorld                                                      *
 * Version release date : March 22, 2005                               *
 * Author : Mounès Ronan metalm@users.berlios.de                       *
 *                                                                     *
 *     http://jcubitainer.berlios.de/                                  *
 *                                                                     *
 * This code is released under the GNU GPL license, version 2 or       *
 * later, for educational and non-commercial purposes only.            *
 * If any part of the code is to be included in a commercial           *
 * software, please contact us first for a clearance at                *
 * metalm@users.berlios.de                                             *
 *                                                                     *
 *   This notice must remain intact in all copies of this code.        *
 *   This code is distributed WITHOUT ANY WARRANTY OF ANY KIND.        *
 *   The GNU GPL license can be found at :                             *
 *           http://www.gnu.org/copyleft/gpl.html                      *
 *                                                                     *
 ***********************************************************************/

/* History & changes **************************************************
 *                                                                     *
 ******** March 22, 2005 ***********************************************
 *   - First release                                                   *
 ***********************************************************************/


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.jcubitainer.manager.Configuration;
import org.jxtainer.J3PeerManager;
import org.jxtainer.J3Pipe;
import org.jxtainer.J3xta;
import org.jxtainer.StartJXTA;
import org.jxtainer.util.J3Message;
import org.jxtainer.util.J3MessagePipe;
import org.jxtainer.util.JxMessageListener;
import org.jxtainer.util.JxPeerListener;
import org.jxtainer.util.JxStatutListener;

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
		StartJXTA.addJxMessageListener(new JxMessageListener() {
			public void receiveMessage() {
				J3Message message = J3MessagePipe.drop();
				consoleOut(message.getWho() + " a dit : " + message.getWhat());
			}
		});
		// Evenement de connexion		
		StartJXTA.addJxStatutListener(new JxStatutListener() {
			public void newStatut() {
				if (J3xta.JXTA_STATUT_ON == J3xta.getStatut())
					consoleOut("Vous êtes sur le chat !");
			}
		});
		// Evenement lorsqu'une nouvelle personne arrive sur le chat		
		StartJXTA.addJxPeerListener(new JxPeerListener() {
			public void newPeer() {
				consoleOut(J3PeerManager.getLatest().getName() + " vient d'arriver sur le chat !");
			}
			public void deletePeer() {
				consoleOut(J3PeerManager.getLatest_remove().getName() + " vient de partir du chat !");
			}
		});

		// Démarrage du chat...
		File home = null;
		if ( !firewall ) {
		    home = new File(System.getProperty("user.home") 
                    + File.separator
                    + ".jxtainer"); 
		}
		StartJXTA.wakeUp(login, SUFFIX, home, firewall);

		consoleOut("Recherche du chat sur le réseau...");

		try {
			while (J3xta.JXTA_STATUT_ON == J3xta.getStatut()) {
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
