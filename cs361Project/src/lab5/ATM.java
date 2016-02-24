package lab5;

public class ATM 
{
	Bank bank;
	public ATM(Bank b) 
	{
		bank = b;
	}
	
	public String start()
	{
		return "ATM Started";
	}
	
	public boolean getAccount(int account, int pin)
	{
		return bank.validate(account, pin);
	}
	
	
}
