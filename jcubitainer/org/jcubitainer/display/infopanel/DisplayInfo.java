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

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jcubitainer.display.theme.ThemeManager;
import org.jcubitainer.manager.Game;
import org.jcubitainer.meta.MetaInfo;
import org.jcubitainer.tools.Messages;

public class DisplayInfo extends JPanel implements ActionListener {

    InfoValue score = null;

    InfoValue ligne = null;

    InfoValue bonus_des = null;

    InfoValue bonus_sup = null;

    InfoValue bonus_slow = null;

    JButton button = null;

    InfoLabel bonus = null;

    InfoValue level = null;

    InfoValue hit = null;

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
        ii = new InfoImage(ThemeManager.getCurrent().getImage("ititle"), this); //$NON-NLS-1$
        button = new JButton(Messages.getString("DisplayInfo.start")); //$NON-NLS-1$
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(Color.gray);
        button.setForeground(Color.white);
        // Seulement pour le JDK 1.4 :
        //button.setFocusable(false);
        button.addActionListener(this);
        add(button);

        //new InfoLabel("-----", this); //$NON-NLS-1$
        new InfoLabel(Messages.getString("DisplayInfo.score"), this); //$NON-NLS-1$
        score = new InfoValue("-", this); //$NON-NLS-1$
        new InfoLabel(Messages.getString("DisplayInfo.ligne"), this); //$NON-NLS-1$
        ligne = new InfoValue("-", this); //$NON-NLS-1$
        new InfoLabel(Messages.getString("DisplayInfo.niveau"), this); //$NON-NLS-1$
        level = new InfoValue("-", this); //$NON-NLS-1$
        new InfoLabel(Messages.getString("DisplayInfo.hit"), this); //$NON-NLS-1$
        hit = new InfoValue("" + mi.getHit(), this); //$NON-NLS-1$

        //level.setVisible(false);
        new InfoLabel("*", this); //$NON-NLS-1$
        new InfoHelp(Messages.getString("DisplayInfo.musique"), this); //$NON-NLS-1$
        new InfoHelp(Messages.getString("DisplayInfo.theme"), this); //$NON-NLS-1$
        new InfoHelp(Messages.getString("DisplayInfo.piece"), this); //$NON-NLS-1$
        new InfoHelp(Messages.getString("DisplayInfo.rotation"), this); //$NON-NLS-1$
        new InfoHelp(Messages.getString("DisplayInfo.chutte"), this); //$NON-NLS-1$
        //        new InfoHelp(Messages.getString("DisplayInfo.ajouter"), this); //$NON-NLS-1$
        new InfoHelp(Messages.getString("DisplayInfo.pause"), this); //$NON-NLS-1$
        new InfoLabel("*", this); //$NON-NLS-1$
        new InfoLabel(Messages.getString("DisplayInfo.bonus_destruction"), this); //$NON-NLS-1$
        bonus_des = new InfoValue("-", this); //$NON-NLS-1$
        new InfoLabel(Messages.getString("DisplayInfo.bonus_lignes"), this); //$NON-NLS-1$
        bonus_sup = new InfoValue("-", this); //$NON-NLS-1$
        bonus = new InfoLabel(Messages.getString("DisplayInfo.bonus_slow"),
                this); //$NON-NLS-1$
        bonus_slow = new InfoValue("-", this); //$NON-NLS-1$
        new InfoMsg(Messages.getString("DisplayInfo.rien"), this); //$NON-NLS-1$
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

    public void setHitDisplay(int l) {
        hit.setText(String.valueOf(l));
        hit.setBorder(BorderFactory.createLineBorder(Color.green));
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

            String[] levels = { "1", "2", "3", "4", "5", "6", "7", "8", "9", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$
                    "10"}; //$NON-NLS-1$
            String s = (String) JOptionPane.showInputDialog(this.getParent(),
                    Messages.getString("DisplayInfo.quel_niveau")
                            + Messages.getString("DisplayInfo.debutant") //$NON-NLS-1$ //$NON-NLS-2$
                            + Messages.getString("DisplayInfo.dieu"), Messages
                            .getString("DisplayInfo.choixniveau"), //$NON-NLS-1$ //$NON-NLS-2$
                    JOptionPane.PLAIN_MESSAGE, null, levels, "level"); //$NON-NLS-1$

            if (s == null) return;

            mi.setNiveau(s == null ? 1 : Integer.parseInt(s));

            JOptionPane.showMessageDialog(this.getParent(), Messages
                    .getString("DisplayInfo.pourgagner1") //$NON-NLS-1$
                    + Messages.getString("DisplayInfo.pourgagner2") //$NON-NLS-1$
                    + Messages.getString("DisplayInfo.pourgagner3") //$NON-NLS-1$
                    + Messages.getString("DisplayInfo.pourgagner4") //$NON-NLS-1$
                    + Messages.getString("DisplayInfo.pourgagner5") //$NON-NLS-1$
                    + Messages.getString("DisplayInfo.pourgagner6") //$NON-NLS-1$
                    + Messages.getString("DisplayInfo.pourgagner7"), //$NON-NLS-1$
                    Messages.getString("DisplayInfo.pourgagner_titre"),
                    JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$

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
        if (!b) {
            hit.setBorder(BorderFactory.createEmptyBorder());
        }
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
        ii.setIcon(new ImageIcon(ThemeManager.getCurrent().getImage("ititle"))); //$NON-NLS-1$
    }

}