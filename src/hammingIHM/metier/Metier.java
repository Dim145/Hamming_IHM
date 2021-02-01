package hammingIHM.metier;

public class Metier
{
    public Metier()
    {

    }

    public boolean isCodeCorrect( String code, boolean presenterEtapes )
    {
        int nbBitsDebug = 0;

        for (int i = 0; i < code.length(); i++)
            if( Math.pow(2, i) > code.length() )
            {
                nbBitsDebug = i;
                break;
            }

        for (int i = 0; i < nbBitsDebug; i++)
            if( code.charAt((int) Math.pow(2, i)) != '0' )
                return false;

        return true;
    }
}
