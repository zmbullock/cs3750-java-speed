package game_logic;

import java.util.*;

public class Game{
	
	public int winner;
	
	private Deck deck;
	
	//the player Hand, max 5
	private Hand p1_hand;
	private Hand p2_hand;
	
	// draw pile, 20 cards each
	private Stack<Card> p1_draw_stack; 
	private Stack<Card> p2_draw_stack;
	
	// face up piles where game is played
	private Stack<Card> game_stack1;
	private Stack<Card> game_stack2;
	
	// stack of cards on either side, 5 cards each
	private Stack<Card> p1_side_stack;
	private Stack<Card> p2_side_stack;
	
	public Game(){
		
		deck = new Deck();
		winner = 0;
		p1_draw_stack = new Stack<Card>();
		p2_draw_stack = new Stack<Card>();
		for (int i = 0; i<20;i++){
			p1_draw_stack.push(deck.draw());
			p2_draw_stack.push(deck.draw());
		}
		p1_side_stack = new Stack<Card>();
		p2_side_stack = new Stack<Card>();
		for (int i = 0; i < 5; i++){
			p1_side_stack.push(deck.draw());
			p2_side_stack.push(deck.draw());
		}
		
		p1_hand = new Hand();
		p2_hand = new Hand();
		
		game_stack1 = new Stack<Card>();
		game_stack2 = new Stack<Card>();
		game_stack1.push(deck.draw());
		game_stack2.push(deck.draw());
		// New Game Initialization done
	}

	// Check if move is valid
	public boolean check_move(int player, int player_hand_index, int game_stack_num){
		Hand hand = null;
		if(player == 1){
			hand = p1_hand;
		}
		else if (player == 2){
			hand = p2_hand;
		}
		Stack<Card> stack = null;
		if(game_stack_num == 1){
			stack = game_stack1;
		}
		else if (game_stack_num == 2){
			stack = game_stack2;
		}
		
		return is_valid(stack.peek().get_value(), hand.get_value_at_index(player_hand_index));
	}
	
	//performs the game move after checking if move is valid. sleep thread if board is locked
	public boolean perform_move(int player, int player_hand_index, int game_stack_num) throws InterruptedException{
		if(!check_move(player, player_hand_index, game_stack_num)){
			return false;
		}
		Hand hand = null;
		if(player == 1){
			hand = p1_hand;
		}
		else if (player == 2){
			hand = p2_hand;
		}
		Stack<Card> stack = null;
		if(game_stack_num == 1){
			stack = game_stack1;
		}
		else if (game_stack_num == 2){
			stack = game_stack2;
		}
		stack.push(hand.remove_card(player_hand_index));
		return true;
	}
	
	//returns true if the two values allow a play per the rules
	public boolean is_valid(int i, int j){
		if (i == (j+1)||i== (j-1)){
			return true;
		}
		else if ((i == 1 && j == 13)||(j == 1 && i == 13)){
			return true;
		}
		else
			return false;
	}
	
	//return true if no moves are possible
	public boolean no_moves(){
		boolean no_move = true;
		int s1 = game_stack1.peek().get_value();
		int s2 = game_stack2.peek().get_value();
		int check;
		
		for (int i = 0; i < p1_hand.get_size(); i++){
			check = p1_hand.get_value_at_index(i);
			if(is_valid(check, s1) || is_valid(check, s2)){
				no_move = false;
			}
		}
		for (int i = 0; i < p2_hand.get_size(); i++){
			check = p2_hand.get_value_at_index(i);
			if(is_valid(check, s1) || is_valid(check, s2)){
				no_move = false;
			}
		}
		
		return no_move;
	}
	
	//no moves are possible and both players have clicked their deck
	public void tie_breaker(){
		
		game_stack1.push(p1_side_stack.pop());
		game_stack2.push(p2_side_stack.pop());
		
	}
	
	public boolean draw_card(int player){
		Hand draw_hand = null;
		Stack<Card> draw_stack = null;
		if (player == 1){
			draw_hand = p1_hand;
			draw_stack = p1_draw_stack;
		}
		else if(player == 2){
			draw_hand = p2_hand;
			draw_stack = p2_draw_stack;
		}
		
		if (!draw_stack.isEmpty() && draw_hand.can_draw()){
			draw_hand.add_card(draw_stack.pop());
			return true; 
		}
		return false;
	}
	
	// this will be called any time there is a graphical change to the game
	// Player Draws a card, Valid play, (possibly if player has card selected?)
	//
	// Side stack: tie breaker stack
	// Game stack: player move stack
	// Draw stack: draw stack
	//
	// String format: 
	//
	// [0]p2_draw_stack.empty, [1]p2_hand.get_size, [2]p1_side_stack.empty, [3]game_stack1.peek.value, [4]game_stack1.peek.suit, [5]game_stack2.peek.value, [6]game_stack2.peek.suit, [7]p2_side_stack.empty, 
	//     [8]p1_hand0.value, [9]p1_hand0.suit, [10]p1_hand1.value, [11]p1_hand1.suit, [12]p1_hand2.value, [13]p1_hand2.suit, [14]p1_hand3.value, [15]p1_hand3.suit, [16]p1_hand4.value, [17]p1_hand4.suit, 
	//     [18]p1_draw_stack.empty, [19]winner
	//
	public String update_board(){
		String board = "";
		if(p2_draw_stack.empty()){
			board += "0,";
		}
		else {
			board += "1,";
		}
		board += p2_hand.get_size() + ",";
		if(p1_side_stack.empty()){
			board += "0,";
		}
		else {
			board += "1,";
		}
		board += game_stack1.peek().get_value() + "," + game_stack1.peek().get_suit() + ",";
		board += game_stack2.peek().get_value() + "," + game_stack2.peek().get_suit() + ",";
		if(p2_side_stack.empty()){
			board += "0,";
		}
		else {
			board += "1,";
		}
		for(int i = 0; i < 5; i++){
			if(p1_hand.get_size() > i){
				board += p1_hand.get_value_at_index(i) + "," + p1_hand.get_suit_at_index(i) + ",";
			}
			else{
				board += "0,0,";
			}
		}
		
		if(p1_draw_stack.empty()){
			board += "0,";
		}
		else {
			board += "1,";
		}
		
		//board += check_for_win(); //@zmb added in GameManager.java
		
		//Middle of the return string
		board += "T";
		//
		if(p1_draw_stack.empty()){
			board += "0,";
		}
		else {
			board += "1,";
		}
		board += p1_hand.get_size() + ",";
		if(p1_side_stack.empty()){
			board += "0,";
		}
		else {
			board += "1,";
		}
		board += game_stack1.peek().get_value() + "," + game_stack1.peek().get_suit() + ",";
		board += game_stack2.peek().get_value() + "," + game_stack2.peek().get_suit() + ",";
		if(p2_side_stack.empty()){
			board += "0,";
		}
		else {
			board += "1,";
		}
		for(int i = 0; i<5;i++){
			if(p2_hand.get_size() > i){
				board += p2_hand.get_value_at_index(i) + "," + p2_hand.get_suit_at_index(i) + ",";
			}
			else{
				board += "0,0,";
			}
		}
		if(p2_draw_stack.empty()){
			board += "0,";
		}
		else {
			board += "1,";
		}
		//board += check_for_win(); //@zmb - Added in GameManager.java
		//using interface, send current player hand, oposing player hand size, each card face up, and other decks/stacks if desired.
		
		return board;
	}

	//return 0 if no winner, 1 if p1, 2 if p2
	public int check_for_win(){
		if(p1_hand.get_size() == 0 && p1_draw_stack.empty()){
			return 1;
		}
		else if (p2_hand.get_size() == 0 && p2_draw_stack.empty()){
			return 2;
		}
		else {
			return 0;
		}
	}
	
}
