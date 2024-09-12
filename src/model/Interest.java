package model;

public class Interest {
	InterestType type;
	
	
	public double calculateInterest(double principal, double interestRate, double numOfYears, InterestType type) {
		double interest = 0;
		if (type == InterestType.SimpleInterest) {
			interest = principal + (principal * (interestRate/100) * numOfYears);
		}
	
		return interest;
	}
}
