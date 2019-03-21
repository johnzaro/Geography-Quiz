package core;

/**
 * Created by John on 3/1/2017.
 */
public class GreekLanguageHelper
{
	private static final String[] masculineArticleWithN = {"Ο", "Του", "Τον", "Οι", "Των", "Τους"};
	private static final String[] masculineArticleWithoutN = {"Ο", "Του", "Το", "Οι", "Των", "Τους"};
	
	private static final String[] feminineArticleWithN = {"Η", "Της", "Την", "Οι", "Των", "Τις"};
	private static final String[] feminineArticleWithoutN = {"Η", "Της", "Τη", "Οι", "Των", "Τις"};
	
	private static final String[] neuterArticle = {"Το", "Του", "Το", "Τα", "Των", "Τα"};
	
	private static final String[] articleIn = {"Στον", "Στο", "Στην", "Στη", "Στο", "Στις", "Στα"};
	
	public static String getArticleIn(String nominative, boolean withN)
	{
		if(nominative.equals(masculineArticleWithN[0]))
		{
			if(withN) return articleIn[0];
			else return articleIn[1];
		}
		else if(nominative.equals(feminineArticleWithN[0]))
		{
			if(withN) return articleIn[2];
			else return articleIn[3];
		}
		else if(nominative.equals(neuterArticle[0])) return articleIn[4];
		else if(nominative.equals(feminineArticleWithN[3])) return articleIn[5];
		else if(nominative.equals(neuterArticle[3])) return articleIn[6];
		else return "";
	}
	
	public static String getGenitive(String nominative)
	{
		if(nominative.equals(masculineArticleWithN[0])) return masculineArticleWithN[1];
		else if(nominative.equals(feminineArticleWithN[0])) return feminineArticleWithN[1];
		else if(nominative.equals(neuterArticle[0])) return neuterArticle[1];
		else if(nominative.equals(feminineArticleWithN[3])) return feminineArticleWithN[4];
		else if(nominative.equals(neuterArticle[3])) return neuterArticle[4];
		else return "";
	}
	
	public static String getAccusative(String nominative, boolean withN)
	{
		if(nominative.equals(masculineArticleWithN[0]))
		{
			if(withN) return masculineArticleWithN[2];
			else return masculineArticleWithoutN[2];
		}
		else if(nominative.equals(feminineArticleWithN[0]))
		{
			if(withN) return feminineArticleWithN[2];
			else return feminineArticleWithoutN[2];
		}
		else if(nominative.equals(neuterArticle[0])) return neuterArticle[2];
		else if(nominative.equals(feminineArticleWithN[3])) return feminineArticleWithN[5];
		else if(nominative.equals(neuterArticle[3])) return neuterArticle[5];
		else return "";
	}
	
	public static boolean needsN(String word)
	{
		char c = word.charAt(0);
		c = Character.toLowerCase(c);
		
		switch (c)
		{
			case 'β':case 'γ':case 'δ':
			case 'φ':case 'θ':case 'χ':
			case 'μ':case 'ν':case 'λ':
			case 'ρ':case 'σ':case 'ζ':
				return false;
			default:
				return true;
		}
	}
}
