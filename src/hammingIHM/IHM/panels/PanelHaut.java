package hammingIHM.IHM.panels;

import hammingIHM.IHM.FrameP;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * The type Panel haut.
 */
public class PanelHaut extends JPanel
{
    /**
     * The Frame.
     */
    FrameP frame;
    /**
     * The Txtf binaire.
     */
    JTextField txtfBinaire;
    /**
     * The Btn valider.
     */
    JButton btnValider;
    /**
     * The Pnl droite.
     */
    JPanel pnlDroite;

    /**
     * Instantiates a new Panel haut.
     *
     * @param frame the frame
     */
    public PanelHaut(FrameP frame)
    {
        this.setLayout(new BorderLayout());
        this.frame = frame;
        this.pnlDroite = new JPanel(new BorderLayout());

        this.txtfBinaire = new JTextField()
        {
            @Override
            public void paste()
            {

            }
        };
        this.txtfBinaire.setColumns(25);
        this.txtfBinaire.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {
                char key = e.getKeyChar();

                if( key == KeyEvent.VK_ENTER)
                {
                    // = activer script

                    e.consume();
                    return;
                }

                if (key != '0' && key != '1')
                {
                    String txt = PanelHaut.this.txtfBinaire.getText();

                    if( !txt.contains(String.valueOf('-')) )
                    {
                        return; // return sans consume n'annule pas l'evenement
                    }

                    try
                    {
                        if( key != KeyEvent.VK_BACK_SPACE )
                            Toolkit.getDefaultToolkit().beep();
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                    }

                    e.consume();
                }
            }
        });

        this.btnValider = new JButton("GO");
        this.btnValider.addActionListener(e ->
        {
            // activer script
        });

        this.add(this.txtfBinaire, BorderLayout.CENTER);
        this.add(pnlDroite, BorderLayout.EAST);
        this.pnlDroite.add(this.btnValider, BorderLayout.EAST);
    }

    /**
     * Retour string binaire string.
     *
     * @return the string
     */
    public String retourStringBinaire()
    {
        return txtfBinaire.getText();
    }
}
