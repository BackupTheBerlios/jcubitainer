/***********************************************************************
 * HelloChatWorld                                                      *
 * Version release date : March 22, 2005                               *
 * Author : Mounes Ronan metalm@users.berlios.de                       *
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

import org.jxtainer.J3Peer;
import org.jxtainer.J3Pipe;
import org.jxtainer.J3xta;
import org.jxtainer.StartJXTA;
import org.jxtainer.util.J3Message;
import org.jxtainer.util.JxMessageListener;
import org.jxtainer.util.JxPeerListener;
import org.jxtainer.util.JxStatutListener;

/**
 * 
 * Voici un exemple d'implementation d'un espace de discution (chat) 
 * en utilisant la librairie JXtainer 1.0
 * Si vous etes derriere un proxy :
 * java HelloChatWorld --proxy --login Ronan 
 * Si vous etes directement connecte a internet :
 * java HelloChatWorld --login Ronan
 *
 */

public class HelloChatWorld {

    // Pour les entrees au clavier :
    static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    // Nom du groupe JXtainer :
    static final String GROUPE = "HelloChatWorldJXtainer";

    public static void main(String[] args) {
        // Login par defaut et variable pour stocker les messages
        String login = "Anonyme", mes = null;
        // Fenetre JXTA pour configurer un proxy ( proxy = true )
        boolean proxy = false;
        // Pipe du groupe
        J3Pipe pipe = null;

        // Analyse de la ligne de commande
        for (int i = 0; i < args.length; i++)
            if ("--login".equals(args[i]) && ++i < args.length)
                login = args[i--];
            else if ("--proxy".equals(args[i]))
                proxy = true;

        /***************************
         *        Etape n°1 :      *
         * Definir les evenements :*
         *- au changement de statut*
         *- a l'arrive d'un message*
         *- a l'arrive d'un peer   *
         *- au depart d'un peer    *
         ***************************/

        // Evenement de connexion		
        StartJXTA.addJxStatutListener(new JxStatutListener() {
            public void newStatut(int statut) {
                if (statut == J3xta.JXTA_STATUT_ON)
                    consoleOut("Vous venez d'arriver sur le chat !");
                else if (statut == J3xta.JXTA_STATUT_CONNECT)
                    consoleOut("Recherche du chat sur le réseau (5 minutes environ) ...");
            }
        });
        // Evenement lorsque l'on reçoit un message du chat
        StartJXTA.addJxMessageListener(new JxMessageListener() {
            public void receiveMessage(J3Message message) {
                consoleOut(message.getWho() + " a dit : " + message.getWhat());
            }
        });
        // Evenement lorsqu'une nouvelle personne arrive sur le chat		
        StartJXTA.addJxPeerListener(new JxPeerListener() {
            public void newPeer(J3Peer peer) {
                consoleOut(peer.getName() + " vient d'arriver sur le chat !");
            }

            public void deletePeer(J3Peer peer) {
                consoleOut(peer.getName() + " vient de partir du chat !");
            }
        });

        /***************************
         *        Etape n°2 :      *
         *  Se connecter sur JXTA  *
         ***************************/
        // Repertoire pour sauvegarder le cache JXTA ( non obligatoire )
        File home_config = new File(System.getProperty("user.home") + File.separator + ".jxtainer");
        // On se connecte sur JXtainer
        StartJXTA.wakeUp(login, GROUPE, home_config, proxy);

        /***************************
         *        Etape n°3 :      *
         *Dialoguer avec les autres*
         ***************************/
        // On boucle jusqu'a la commande "quit"
        while (!(mes = consoleIn(">")).equals("quit"))
            if ((pipe = StartJXTA.getPipe()) != null)
                pipe.sendMsg(mes);
            else
                consoleOut("Pas encore connecte au chat.");

        // fin...
        consoleOut("Vous avez quitte le chat.");
        System.exit(0);
    }

    public static void consoleOut(String s) {
        System.out.println(s);
    }

    public static String consoleIn(String s) {
        try {
            System.out.print(s);
            return in.readLine();
        } catch (IOException e) {
            return "ERROR";
        }
    }
}