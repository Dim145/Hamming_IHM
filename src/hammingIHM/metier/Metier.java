package hammingIHM.metier;

public class Metier
{
	public Metier()
	{

	}

	public String correctionPreEmission(String code)
	{
		String sRet;

		int nbBitsDebug = 0;
		for (int i = 0; i < code.length() && Math.pow(2, i) < code.length(); i++)
			nbBitsDebug++;
		nbBitsDebug++; //si on a 2^2 alors on a 3 bit de correction
		
		//On allonge la chaine pour qu'elle fasse la taille de 2^nbBitsDebug-1-nbBitsDebug
		if(code.length() != Math.pow(2, nbBitsDebug)- 1 - nbBitsDebug)
			code = String.format("%" + (int)(Math.pow(2, nbBitsDebug) - 1 - nbBitsDebug) + "s", code);
		
		StringBuffer code2 = new StringBuffer(code);
		
		//on insere aux emplacement voulu des bits de hamming a la valeur _ car n'etant pas set
		for (int i = 0; i < nbBitsDebug; i++) {
			int bitHamming = code2.length() - (int)Math.pow(2, i) + 1;
			code2.insert(bitHamming, '_');
		}
		
		System.out.println(code2);
		
		sRet = nbBitsDebug +":";
		
		
		String[] tabBits = new String[nbBitsDebug];
		 
		for (int i = 0; i < tabBits.length; i++)
		{
			String  bits = "";
			int     j = i;
			int     nbBitsAPrendre = i+1;
			int     nbBitsPris     = 0;
			boolean toutPris       = false;
			
			for(int k=(int)(Math.pow(2, i)-1); k<code2.length(); k=(int)Math.pow(2, j++))
			{
				if(nbBitsPris < nbBitsAPrendre)
				{
					bits += code2.charAt(k)+"";
					nbBitsPris++;
					if(nbBitsPris == nbBitsAPrendre)
						toutPris = true;
				}
				else
					if(toutPris == true && nbBitsPris == 0)
						toutPris = false;
					else
						nbBitsPris--;
			}
			tabBits[i] = bits;
		}
		
		for (int i = 0; i < tabBits.length; i++)
		{
			int val = 0;
			for (int j = 0; j < tabBits[i].length(); j++)
			{
				if(val == 0 && tabBits[i].charAt(j) == 1)
					val = 1;
				else
					if(val == 1 && tabBits[i].charAt(j) == 1)
						val = 0;
			}
			sRet += val+"";
		}
		 
		return sRet;
	}

	public boolean isCodeCorrect(String code, boolean presenterEtapes)
	{
		int nbBitsDebug = 0;

		for (int i = 0; i < code.length(); i++)
			if (Math.pow(2, i) > code.length())
			{
				nbBitsDebug = i;
				break;
			}

		for (int i = 0; i < nbBitsDebug; i++)
			if (code.charAt((int) Math.pow(2, i)) != '0')
				return false;

		return true;
	}
	
	public static void main(String[] args)
	{
		Metier m = new Metier();
		System.out.println(m.correctionPreEmission("1011"));
	}
}
