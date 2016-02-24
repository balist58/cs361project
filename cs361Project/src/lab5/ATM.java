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
	
	public boolean accountExists(int accountNum)
	{
		return bank.getAccount(accountNum) != null;
	}
	
	public boolean getAccount(int account, int pin)
	{
		if (bank.validate(account, pin) && isStarted) {
			acnt = bank.getAccount(account);
			System.out.println("Account Number: " + acnt.getAccountNumber());
			System.out.println("Account Balance: " + acnt.getBal());
			return true;
		} else {
			System.out.println("Account Validation Failed");
			isStarted = false;
			return false;
		}
	}
	
	public boolean doAction(char action, double amount) 
	{
		boolean result = false;
		if(isStarted) {
			if(action == 'W') {
				result = acnt.modifyBal(-amount);
			} else if(action == 'D') {
				result = acnt.modifyBal(amount);
			}
			
			System.out.println(result ? "Final Balance: " + acnt.getBal() : "Transaction failed. Insufficient funds.");
			acnt = null;
			isStarted = false;			
		}
		return result;
	}
}
