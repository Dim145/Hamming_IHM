package hammingIHM;

import hammingIHM.ihm.FrameP;
import hammingIHM.metier.Metier;

public class Controleur
{
    private final FrameP ihm;
    private final Metier metier;

    public Controleur()
    {
        this.ihm = new FrameP(this);
        this.metier = new Metier(this);
    }

    public int isCodeCorrect(String code, boolean afficher)
    {
        return this.metier.isCodeCorrect(code, afficher);
    }

    public int isCodeCorrect( String code )
    {
        return this.isCodeCorrect(code, false);
    }

    public String calculeCodeEmission( String code )
    {
        return this.metier.correctionPreEmission(code);
    }

    public void setLblDetails( String details )
    {
        this.ihm.setLblDetails(details);
    }

    public static void main(String[] args)
    {
        new Controleur();
    }
}
