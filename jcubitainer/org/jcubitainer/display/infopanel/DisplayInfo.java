/***********************************************************************
 * JCubitainer                                                         *
 * Version release date : May 5, 2004                                  *
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
 ******** May 5, 2004 **************************************************
 *   - First release                                                   *
 ***********************************************************************/

package org.jcubitainer.display.infopanel;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jcubitainer.display.theme.ThemeManager;
import org.jcubitainer.manager.Game;
import org.jcubitainer.meta.MetaInfo;

public class DisplayInfo extends JPanel implements ActionListener {

    InfoValue score = null;

    InfoValue ligne = null;

    InfoValue bonus_des = null;

    InfoValue bonus_sup = null;

    InfoValue bonus_slow = null;

    JButton button = null;

    InfoLabel bonus = null;

    InfoValue level = null;

    MetaInfo mi = null;

    InfoImage ii = null;

    private static DisplayInfo this_ = null;

    /**
     *  
     */
    public DisplayInfo(MetaInfo pmi) {
        super();
        this_ = this;
        mi = pmi;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Color.black);
        //new InfoLabel("JCubitainer", this).setFont(Font.getFont("Verdana"));
        ii = new InfoImage(ThemeManager.getCurrent().getImage("ititle"), this);
        button = new JButton("Start");
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(Color.gray);
        button.setForeground(Color.white);
        // Seulement pour le JDK 1.4 :
        //button.setFocusable(false);
        button.addActionListener(this);
        add(button);

        new InfoLabel("-----", this);
        new InfoLabel("Score :", this);
        score = new InfoValue("-", this);
        new InfoLabel("Lignes :", this);
        ligne = new InfoValue("-", this);
        new InfoLabel("Niveau :", this);

        level = new InfoValue("-", this);
        //level.setVisible(false);
        new InfoLabel("*", this);
        new InfoHelp("Musique[on/off] : [m]", this);
        new InfoHelp("Changer de thème : [t]", this);
        new InfoHelp("Changer de pièce : [c ou v]", this);
        new InfoHelp("Rotation d'une pièce : [space]", this);
        new InfoHelp("Chutte de la pièce : [enter]", this);
        new InfoHelp("Ajouter une pièce : [n]", this);
        new InfoHelp("Pause : [p]", this);
        new InfoLabel("*", this);
        new InfoLabel("Bonus destruction pièce [d] :", this);
        bonus_des = new InfoValue("-", this);
        new InfoLabel("Bonus 2 lignes en moins [r] :", this);
        bonus_sup = new InfoValue("-", this);
        bonus = new InfoLabel("Bonus ralentisseur [s] :", this);
        bonus_slow = new InfoValue("-", this);
        new InfoMsg("Rien", this);
    }

    public void setLineDisplay(int l) {
        ligne.setText(String.valueOf(l));
    }

    public void setScoreDisplay(int l) {
        score.setText(String.valueOf(l));
    }

    public void setBonusDisplay(int l) {
        bonus_des.setText(String.valueOf(l));
    }

    public void setSlowDisplay(int l) {
        bonus_slow.setText(String.valueOf(l));
    }

    public void setDeleteLineDisplay(int l) {
        bonus_sup.setText(String.valueOf(l));
    }

    public void setLevelDisplay(int l) {
        level.setText(String.valueOf(l));
    }

    public void activeSlowDisplay(boolean b) {
        if (b)
            bonus.setForeground(Color.cyan);
        else
            bonus.setForeground(Color.white);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent arg0) {
        if (arg0.getSource() == button) {

            String[] levels = { "1", "2", "3", "4", "5", "6", "7", "8", "9",
                    "10"};
            String s = (String) JOptionPane.showInputDialog(this.getParent(),
                    "Quel niveau de jeu ?\n" + "1 pour les débutants à\n"
                            + "10 pour les dieux \n", "Choix du niveau",
                    JOptionPane.PLAIN_MESSAGE, null, levels, "level");

            if (s == null) return;

            mi.setNiveau(s == null ? 1 : Integer.parseInt(s));

            JOptionPane
                    .showMessageDialog(
                            this.getParent(),
                            "Pour gagner les bonus, il faut détruire plusieurs lignes en même temps.\n"
                                    + "2 lignes : pour gagner un bonus 'destruction de pièce',\n"
                                    + "3 lignes : pour gagner un bonus 'suppression des 2 dernières lignes',\n"
                                    + "4 lignes : pour gagner un bonus 'ralentisseur de la chutte des pièces',\n"
                                    + "5 lignes : pour gagner 2 bonus,\n"
                                    + "etc....\n"
                                    + "Utilisez les touches qui sont indiquées à côté des bonus pour les utiliser !",
                            "Aide bonus", JOptionPane.INFORMATION_MESSAGE);

            Game.getGameService().start();
            mi.setGame_over(false);
            return;
        }
        if (arg0.getSource() == button) {
            Game.getGameService().pause();
            System.exit(0);
        }
    }

    public void setGameOverDisplay(boolean b) {
        button.setVisible(b);
        button.setEnabled(b);
    }

    /**
     * @return
     */
    public static DisplayInfo getThis() {
        return this_;
    }

    /**
     * @return
     */
    public MetaInfo getMetaInfo() {
        return mi;
    }

    public void refreshTheme() {
        ii.setIcon(new ImageIcon(ThemeManager.getCurrent().getImage("ititle")));
    }

}