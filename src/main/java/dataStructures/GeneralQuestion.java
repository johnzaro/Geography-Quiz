package dataStructures;

public class GeneralQuestion
{
	private String question;
	private String answer;
	private String[] possibleAnswers;

	private boolean isEasy;

	public String getQuestion()
	{
		return question;
	}

	public void setQuestion(String question)
	{
		this.question = question;
	}

	public String getAnswer()
	{
		return answer;
	}

	public void setAnswer(String answer)
	{
		this.answer = answer;
	}

	public String[] getPossibleAnswers()
	{
		return possibleAnswers;
	}

	public void setPossibleAnswers(String[] possibleAnswers)
	{
		this.possibleAnswers = possibleAnswers;
	}

	public boolean isEasy()
	{
		return isEasy;
	}

	public void setIsEasy(boolean isEasy)
	{
		this.isEasy = isEasy;
	}
}
