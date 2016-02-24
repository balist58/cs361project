package lab5;

public class ATM 
{
	Bank bank;
	boolean isStarted;
	Bank.Account acnt;
	
	public ATM(Bank b) 
	{
		bank = b;
		isStarted = false;
	}
	
	public String start()
	{
		isStarted = true;
		return "ATM Started";
	}
	
	public boolean getAccount(int account, int pin)
	{
		if (bank.validate(account, pin) && isStarted) {
			acnt = bank.getAccount(account);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean doAction(char action, double amount) 
	{
		boolean result = false;
		if(action == 'W') {
			
		} else if(action == 'D') {
			
		}
		

		System.out.print("Current Balance: " + acnt.getBal());
		acnt = null;
		isStarted = false;
		return result;
	}
}
