import java.util.ArrayList;

public class Hand{
	
	private ArrayList<Card> cards;
	
	public Hand(){
		cards = new ArrayList<Card>();
	}
	
	public void add_card(Card card){
		cards.add(card);
	}
	
	public Card remove_card(int index){
		return cards.remove(index);
	}
	
	public int get_value_at_index(int index){
		return cards.get(index).get_value();
	}
	
	public int get_suit_at_index(int index){
		return cards.get(index).get_suit();
	}
	
	public int get_size(){
		return cards.size();
	}
	
	//return true if less than 5 cards in the hand, else false
	public boolean can_draw(){
		
		return cards.size() < 5;
	}
}