//alert("Stop!");

"use strict";

var str;
var card1 = document.getElementById("playerCard1");
var card2 = document.getElementById("playerCard2");
var card3 = document.getElementById("playerCard3");
var card4 = document.getElementById("playerCard4");
var card5 = document.getElementById("playerCard5");
document.getElementById("playerCard1").style.border = "thick solid white";
document.getElementById("playerCard2").style.border = "thick solid white";
document.getElementById("playerCard3").style.border = "thick solid white";
document.getElementById("playerCard4").style.border = "thick solid white";
document.getElementById("playerCard5").style.border = "thick solid white";

var strArray = [];
var deckArr = ["AC", "AD", "AH", "AS", "2C", "2D", "2H", "2S", "3C", "3D", "3H", "3S",
				"4C", "4D", "4H", "4S", "5C", "5D", "5H", "5S", "6C", "6D", "6H", "6S",
				"7C", "7D", "7H", "7S", "8C", "8D", "8H", "8S", "9C", "9D", "9H", "9S",
				"10D", "10D", "10H", "10S",
				"JC", "JD", "JH", "JS", "QC", "QD", "QH", "QS", "KC", "KD", "KH", "KS"]
			
//	strArray[0]: opponentDeck				strArray[1]: # of opponentCards			strArray[2]: gameDeck1
//	strArray[3]: gameCard1 value			strArray[4]: gameCard1 suit				strArray[5]: gameCard2 value	
//	strArray[6]: gameCard2 suit				strArray[7]: gameDeck2					strArray[8]: playerCard1 value
//  strArray[9]: playerCard1 suit			strArray[10]: playerCard2 value			strArray[11]: playerCard2 suit
//  strArray[12]: playerCard3 value			strArray[13]: playerCard3 suit			strArray[14]: playerCard4 value
//  strArray[15]: playerCard4 suit			strArray[16]: playerCard5 value			strArray[17]: playerCard5 suit
//  strArray[18]: playerDeck				strArray[19]: win condition
strArr = str.split(",");

if (strArr[0] == 1) {
	//document.getElementById("oponentDeck") = src(img/red-knights---b.png);
	document.getElementById("oponentDeck").innerHTML='<img src="img/red-knights---b.png">';
}
else {
	document.getElementById("oponentDeck").innerHTML='<img src="none">';
}
for (i = 0; i < strArr[1]; i++) {
	//document.getElementById("oponentDeck") = src(img/red-knights---b.png);
	document.getElementById("oponentCard" + [i].toString).innerHTML='<img src="img/red-knights---b.png">';
}
if (strArr[2] == 1) {
	//document.getElementById("oponentDeck") = src(img/red-knights---b.png);
	document.getElementById("gameDeck1").innerHTML='<img src="img/red-knights---b.png">';
}
else {
	document.getElementById("oponentDeck").innerHTML='<img src="none">';
}


if (strArr[7] == 1) {
	//document.getElementById("oponentDeck") = src(img/red-knights---b.png);
	document.getElementById("oponentDeck").innerHTML='<img src="img/red-knights---b.png">';
}
else {
	document.getElementById("oponentDeck").innerHTML='<img src="none">';
}


if (strArr[18] == 1) {
	//document.getElementById("oponentDeck") = src(img/red-knights---b.png);
	document.getElementById("oponentDeck").innerHTML='<img src="img/red-knights---b.png">';
}
else {
	document.getElementById("oponentDeck").innerHTML='<img src="none">';
}


function select(id) {
	//alert(id);
	console.log("Card clicked");
	var card = id;
	//alert(card)
	switch (card) {
	//switch (document.getElementById("id").value) {
	case "playerCard1":
		if (document.getElementById("playerCard1").style.border == "thick solid white") {
			document.getElementById("playerCard1").style.border = "thick solid yellow";
		}
		else {
			document.getElementById("playerCard1").style.border = "thick solid white";
		}
		break;
	case "playerCard2":
		if (document.getElementById("playerCard2").style.border == "thick solid white") {
			document.getElementById("playerCard2").style.border = "thick solid yellow";
		}
		else {
			document.getElementById("playerCard2").style.border = "thick solid white";
		}
		break;
	case "playerCard3":
		if (document.getElementById("playerCard3").style.border == "thick solid white") {
			document.getElementById("playerCard3").style.border = "thick solid yellow";
		}
		else {
			document.getElementById("playerCard3").style.border = "thick solid white";
		}
		break;
	case "playerCard4":
		if (document.getElementById("playerCard4").style.border == "thick solid white") {
			document.getElementById("playerCard4").style.border = "thick solid yellow";
		}
		else {
			document.getElementById("playerCard4").style.border = "thick solid white";
		}
		break;
	case "playerCard5":
		if (document.getElementById("playerCard5").style.border == "thick solid white") {
			document.getElementById("playerCard5").style.border = "thick solid yellow";
		}
		else {
			document.getElementById("playerCard5").style.border = "thick solid white";
		}
		break;
	default:
		break;
	}	
}




