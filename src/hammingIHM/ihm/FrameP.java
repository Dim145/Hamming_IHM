package hammingIHM.ihm;

import hammingIHM.Controleur;
import hammingIHM.ihm.panels.PanelHaut;
import hammingIHM.ihm.panels.PanelMilieu;

import javax.swing.*;
import java.awt.*;

public class FrameP extends JFrame
{
    private final Controleur ctrl;

    private final PanelHaut pnlHaut;
    private final PanelMilieu pnlMilieu;

    public FrameP(Controleur ctrl )
    {
        super("Hamming");

        this.ctrl = ctrl;

        // créations
        this.pnlHaut = new PanelHaut(this);
        this.pnlMilieu = new PanelMilieu(this);

        // ajouts
        this.add(this.pnlHaut, BorderLayout.NORTH);
        this.add(this.pnlMilieu, BorderLayout.CENTER);

        // Code de fin du constructeur
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public int isCodeCorrect(String code, boolean afficher)
    {
        return this.ctrl.isCodeCorrect(code, afficher);
    }

    public int isCodeCorrect( String code )
    {
        return this.isCodeCorrect(code, false);
    }

    public String calculeCodeEmission( String code )
    {
        return this.ctrl.calculeCodeEmission(code);
    }

    public void setResultat( String code, int posErreur )
    {
        if( posErreur == Integer.MIN_VALUE )
            this.pnlMilieu.setLblResultatCorriger(code);
        else
            this.pnlMilieu.setResulultatVerification(code, posErreur);

        this.pack();
    }

    public void setLblDetails( String details )
    {
        this.pnlMilieu.setLblDetails(details);

        this.pack();
    }
}
