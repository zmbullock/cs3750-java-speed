
public class Card{
	
	//1 through 13 where 1 is Ace and 13 is King
	private int value;
	// 0-3 where 0 = spades, 1 = clubs, 2 = Hearts, 3 = Diamonds
	private int suit;
	
	public Card(int value, int suit){
		this.value = value;
		this.suit = suit;
	}
	
	public int get_suit(){
		return suit;
	}
	
	public int get_value(){
		return value;
	}
	
}
