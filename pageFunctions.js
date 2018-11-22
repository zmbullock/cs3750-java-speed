/**
 * 
 */
var cardChoiceHand;
var cardChoiceBoard;
var playerDeck;
var gameDeck;
var playerMoveTurn = 0;	//0 if no card clicked or message has been sent; 1 if hand card clicked; 2 if middlecard clicked
var websocket = new WebSocket("ws://34.218.225.144:8080/cs3750-java-speed/chatroomServerEndpoint");
var str;
var start = 0;

function sendMessage(ID){
	var cardClass = document.getElementById(ID).className;
	
	if(cardClass == "handCard"){
		select(ID);
		var cardPlace;
		cardPlace = document.getElementById(ID).value;
		cardChoiceHand = "2,"+cardPlace+",";
		playerMoveTurn = 1;
	}
	if(cardClass == "gameCard" && playerMoveTurn == 1){
		var cardPlace;
		cardPlace = document.getElementById(ID).value;
		cardChoiceBoard = cardPlace;
		playerMoveTurn = 2;
		refresh();
	}
	if(cardClass == "playerDeck"){
		playerMoverTurn = 0; //make player click hand card again
		playerDeck = "0";
		//messagesTextArea.value += playerDeck + "\n";
		websocket.send(playerDeck);
		refresh();
	}
	if(cardClass == "gameDeck"){
		playerMoverTurn = 0; //make player click hand card again
		gameDeck = "1";
		//messagesTextArea.value += gameDeck + "\n";
		websocket.send(gameDeck);
		refresh();
	}
	//2 cards clicked, message ready to be sent
	if(playerMoveTurn == 2){
		playerMoveTurn = 0;
		var makeMoveString = cardChoiceHand+cardChoiceBoard;
		refresh();
		//messagesTextArea.value += makeMoveString + "\n";
		websocket.send(makeMoveString);
	}
}

websocket.onmessage = function processMessage(message){
	playerMoveTurn = 0;
	var jsonData = JSON.parse(message.data);
	var i;

	if(jsonData.message != null){
		
		var gameCard1, gameCard2, playerCard0, playerCard1, playerCard2, playerCard3, playerCard4
		str = jsonData.message;
		strArr = [];
		strArr = str.split(",");
		
		gameCard1 = strArr[3]+strArr[4];
		gameCard2 = strArr[5]+strArr[6];
		
		playerCard0 = strArr[8]+strArr[9];
		playerCard1 = strArr[10]+strArr[11];
		playerCard2 = strArr[12]+strArr[13];
		playerCard3 = strArr[14]+strArr[15];
		playerCard4 = strArr[16]+strArr[17];
		
		if(strArr[2] == 0){
			document.getElementById("gameDeck1").src = "images/00.png";
		}
		if(strArr[2] == 1){
			document.getElementById("gameDeck1").src = "images/red-knights---b.png";
			
		}
		if(strArr[7] == 0){
			document.getElementById("gameDeck2").src = "images/00.png";
			
		}
		if(strArr[7] == 1){
			document.getElementById("gameDeck2").src = "images/red-knights---b.png";
		}
		
		if(strArr[18] == 0){
			document.getElementById("playerDeck").src = "images/00.png";
		}
		if(strArr[18] == 1){
			document.getElementById("playerDeck").src = "images/red-knights---b.png";
		}
		
		document.getElementById("gameCard1").src = "images/"+gameCard1+".png";
		document.getElementById("gameCard2").src = "images/"+gameCard2+".png";
		
		document.getElementById("playerCard1").src = "images/"+playerCard0+".png";
		document.getElementById("playerCard2").src = "images/"+playerCard1+".png";
		document.getElementById("playerCard3").src = "images/"+playerCard2+".png";
		document.getElementById("playerCard4").src = "images/"+playerCard3+".png";
		document.getElementById("playerCard5").src = "images/"+playerCard4+".png";
		
		if(strArr[0] == 1){
			document.getElementById("opponentDeck").src = "images/red-knights---b.png";
		}
		if(strArr[0] == 0){
			document.getElementById("opponentDeck").src = "images/00.png";
			
		}
		
		//Disable 00.png cards
		var body = document.getElementById('gameBody');
	    allBody = body.getElementsByTagName('input');
	    var i0;
	    for (i0 = 0; i0 < allBody.length; i0++) {
	    	
	    	if(allBody[i0].src == "http://34.218.225.144:8080/cs3750-java-speed/images/00.png"){
				allBody[i0].disabled = true;
			}
			else{
				allBody[i0].disabled = false;
			}
	    	
	    	//Local Testing
			/*if(allBody[i0].src == "http://localhost:8080/WebSocketPrj02/00.png"){
				allBody[i0].disabled = true;
			}
			else{
				allBody[i0].disabled = false;
			}*/
		}
		
		
		if(strArr[19] == 1){
			document.getElementById("info").innerHTML = "YOU WIN";
			
			//disable board
			var el1 = document.getElementById('gameBody');
		    all1 = el1.getElementsByTagName('input');
		    var i1;
		    for (i1 = 0; i1 < all1.length; i1++) {
				all1[i1].disabled = true;
			}
		}
		if(strArr[19] == 2){
			document.getElementById("info").innerHTML = "YOU'RE A LOSER";
			
			//disable board
			var el2 = document.getElementById('gameBody');
		    all2 = el2.getElementsByTagName('input');
		    var i2;
		    for (i2 = 0; i2 < all2.length; i2++) {
				all2[i2].disabled = true;
			}
		}
		
		for(i = 1; i <= 5; i++){
			var id = "opponentCard"+i;
			
			if(i <= strArr[1]){
				document.getElementById(id).src = "images/red-knights---b.png";
			}
			else{
				document.getElementById(id).src = "images/00.png";
			}
		}

		//messagesTextArea.value += jsonData.message +"\n";
	}
}

function select(id) {
	var card = id;

	refresh();
	document.getElementById(card).style.border = "thick solid yellow";
}

function refresh() {
	document.getElementById("playerCard1").style.border = "thick solid green";
	document.getElementById("playerCard2").style.border = "thick solid green";
	document.getElementById("playerCard3").style.border = "thick solid green";
	document.getElementById("playerCard4").style.border = "thick solid green";
	document.getElementById("playerCard5").style.border = "thick solid green";
}

function buttonClick(){
	start++;
	var buttonStatus = start % 3;
	if(buttonStatus == 0){
		document.getElementById("gameButton").style.backgroundColor = "#FF0000";
		document.getElementById("gameButton").style.color = "white";
		gameButton.innerHTML = "Start";
	}
	else if(buttonStatus == 1){
		document.getElementById("gameButton").style.backgroundColor = "yellow";
		document.getElementById("gameButton").style.color = "black";
		gameButton.innerHTML = "waiting...";
	}
	else{
		document.getElementById("gameButton").style.backgroundColor = "lightgray";
		document.getElementById("gameButton").style.color = "black";
		gameButton.innerHTML = "Quit";
	}
}
