import java.util.ArrayList;
import java.util.Collections;

public class Deck{
	
	private ArrayList<Card> cards;
	private int deck_index;
	
	public Deck(){
		deck_index = 0;
		cards = new ArrayList<Card>();
		for (int suit = 0; suit < 4; suit++){
			for (int value = 1; value < 14; value++){
				cards.add(new Card(value, suit));
			}
		}
		Collections.shuffle(cards);
	}
	
	public boolean can_draw(){
		return deck_index < cards.size();
	}
	
	public Card draw(){
		return cards.get(deck_index++);
	}
	
}