package hammingIHM.ihm;

import hammingIHM.Controleur;
import hammingIHM.ihm.panels.PanelHaut;

import javax.swing.*;
import java.awt.*;

public class FrameP extends JFrame
{
    private final Controleur ctrl;

    private final PanelHaut pnlHaut;

    public FrameP(Controleur ctrl )
    {
        super("Hamming");

        this.ctrl = ctrl;

        // créations
        this.pnlHaut = new PanelHaut(this);

        // ajouts
        this.add(this.pnlHaut, BorderLayout.NORTH);

        // Code de fin du constructeur
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
