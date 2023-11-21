function card(name, description, image) //object
  {
    this.name = name;
    this.description = description;
    this.image = image;
}

let playedcards = new Array();

//RWS TAROT DECK OBJECT ARRAY, CARDNAME, DESCRIPTION, IMG NUMBER
var deck = [
  new card('The Fool', 'Folly, Expiation, Wavering; R. Hesitation, Instability, Trouble arising herefrom','00'),
  new card('The Magician', 'Will, Will-Power, Dexterity; R. Will applied to evil ends, Weakness of Will, Cunning, Knavishness.','01'),
  new card('The Priestess', 'Science, Wisdom, Knowledge, Education; R. Conceit, Ignorance, Unskilfulness, Superficial Knowledge.','02'),
  new card('The Empress', 'Action, Plan, Undertaking Movement in a matter, Initiative; R. Inaction, Frittering away of power, Want of Concentration Vacillation.','03'),
  new card('The Emperor', 'Realisation, Effect, Development; R. Stoppage, Check, Immature, Unripe.','04'),
  new card('The Hierophant', 'Mercy, Beneficence Kindness, Goodness; R. Over-kindness, weakness, Foolish exercise of generosity.','05'),
  new card('The Lovers', 'Wise Dispositions, Proof, Trials Surmounted; R. Unwise Plans, Failure when put to the test.','06'),
  new card('The Chariot', 'Triumph, Victory, Overcoming obstacles; R. Overthrown, Conquered by Obstacles at the last moment.','07'),
  new card('Strength', 'Power, Might, Force, Strength, Fortitude; R. Abuse of Power, Overbearingness, Want of Fortitude.','08'),
  new card('The Hermit', 'Prudence, Caution, Deliberation; R. Over-prudence, Timorousness, Fear.','09'),
  new card('Wheel Of Fortune', 'Good Fortune, Success, Unexpected Luck; R. Ill-Fortune, Failure, Unexpected Ill-Luck.','10'),
  new card('Justice', 'Equilibrium, Balance, Justice; R. Bigotry, Want of Balance, Abuse of Justice, Over-severity, Inequality, Bias','11'),
  new card('The Hanged Man', 'Self-sacrifice, Sacrifice, Devotion, Bound; R. Selfishness, Unbound, Partial sacrifice.','12'),
  new card('Death', 'Death, Change, Transformation, Alteration for the worse; R. Death just escaped, Partial change, Alteration for the better','13'),
  new card('Temperance', 'Combination, Conformation, Uniting; R. Ill-advised combinations, Disunion, Clashing interests','14'),
  new card('The Devil', 'Fatality for Good; R. Fatality for Evil.','15'),
  new card('The Tower', 'Ruin, Disruption, Over-throw, Loss, Bankruptcy; R. These in a more or less partial degree.','16'),
  new card('The Star', 'Hope, Expectation, Bright promises; R. Hopes not fulfilled, Expectations disappointed or fulfilled in a minor degree.','17'),
  new card('The Moon', 'Twilight, Deception, Error; R. Fluctuation, slight Deceptions, Trifling Mistakes.','18'),
  new card('The Sun', 'Happiness, Content, Joy; R. These in a minor degree.','19'),
  new card('Judgement', 'Renewal, Result, Determination of a Matter; R. Postponement of Result, Delay, Matter re-opened later.','20'),
  new card('The World', 'Completion, Good Reward; R. Evil Reward, or Recompense','21'),
]; 

//RANDOM NUMBER GENERATOR
function getRand(maxNumber){
  var rand = Math.floor(Math.random() * maxNumber);
  console.log(`${rand}` + "Console stuff");
  return rand;
}

document.querySelector("#pullcard1").addEventListener("click", pullcard1);
document.querySelector("#pullcard2").addEventListener("click", pullcard2);
document.querySelector("#pullcard3").addEventListener("click", pullcard3);
document.querySelector("#display").addEventListener("click", displaycards);
document.querySelector("#reset").addEventListener("click", resetcards);

//var sentinel = deck.length;
function cardPlayed(index)
 {
       console.log("validating");
   if (playedcards.includes(index)) //IF THE CARD HAS BEEN PLAYED RETURN TRUE
   return true;
   else 
   return false;
   
  }

//
//  while (!validateCard(cardIndex) ) //is the card in play? check the array
//  {
 //     var cardIndex = getRand(deck.length);
 // }


function pullcard1() 
{
  var cardIndex = getRand(deck.length);
  while (cardPlayed(cardIndex))
  {
    cardIndex = getRand(deck.length);
    console.log("while loop");
  }
  var currentCard = deck[cardIndex];
  document.querySelector("#displaycard1").innerHTML = 
    '<img src="img/' + currentCard.image + '.jpg"><h3>' + currentCard.name + '</h3> <p>' + currentCard.description + '</p>';
  
  playedcards.push(cardIndex);
  console.log(`${playedcards}` + "played cards");
  console.log(`${cardIndex}`);
  //ADD CARD TO PLAYED CARD INDEX ARRAY
  document.querySelector("#pullcard1").style.opacity = "0";
  document.querySelector("#pullcard1").removeEventListener("click", pullcard1);

  
}

function pullcard2() 
{
  var cardIndex = getRand(deck.length);
    while (cardPlayed(cardIndex))
  {
    cardIndex = getRand(deck.length);
    console.log("while loop");
  }
  var currentCard = deck[cardIndex];
  document.querySelector("#displaycard2").innerHTML = '<img src="img/' + currentCard.image + '.jpg"><h3>' + currentCard.name + '</h3> <p>' + currentCard.description + '</p>';
    playedcards.push(cardIndex);
  console.log(`${playedcards}` + "played cards");

  //MAKE BUTTON INVISIBLE AND REMOVE CLICK LISTENER
  document.querySelector("#pullcard2").style.opacity = "0";
  document.querySelector("#pullcard2").removeEventListener("click", pullcard2);

}

function pullcard3() 
{
  var cardIndex = getRand(deck.length);
    while (cardPlayed(cardIndex))
  {
    cardIndex = getRand(deck.length);
    console.log("while loop");
  }
  var currentCard = deck[cardIndex];
  document.querySelector("#displaycard3").innerHTML = '<img src="img/' + currentCard.image + '.jpg"><h3>' + currentCard.name + '</h3> <p>' + currentCard.description + '</p>';

    playedcards.push(cardIndex);
  console.log(`${playedcards}` + "played cards");

    //MAKE BUTTON INVISIBLE AND REMOVE CLICK LISTENER
  document.querySelector("#pullcard3").style.opacity = "0";
  document.querySelector("#pullcard3").removeEventListener("click", pullcard3);

}

//VALIDATE CORRECT NUMBER OF CARDS
function validate()
  {
    if (q1Response == 1 || q1Response == 2 || q1Response == 3)
       return true;
    else
    {
      return false
    }
  }

let q1Response = document.querySelector("#q1").value;

//DISPLAYS CARDS
function displaycards() {
    q1Response = document.querySelector("#q1").value;
    console.log("displaycard function");
    console.log(`${q1Response}`);

  if (!(validate()))
    {
         document.querySelector("#q1Feedback").innerHTML = "Warning you have not entered an appropriate number of cards";
         document.querySelector("#q1Feedback").className = "bg-warning";
         console.log("displaycard function validation failed");
      return;
    }
  else
  {
    console.log("displycard else function");
    console.log(`${q1Response}`);
    document.querySelector("#q1Feedback").innerHTML = ""; //SET FEEDBACK TO EMPTY
    document.querySelector("#displaycards").style.opacity = ".9"; //REVEAL GAMEPLAY BOARD
    if (q1Response == 1)
    {
          console.log("reponse is one");

          document.querySelector("#card1").style.opacity = "0"; //SET 2 CARDS INVISIBLE
          document.querySelector("#card2").style.opacity = "1";
          document.querySelector("#card3").style.opacity = "0";
      return;
    }
    if (q1Response == 2)
    {
          document.querySelector("#card1").style.opacity = "1";
          document.querySelector("#card2").style.opacity = "1";
          document.querySelector("#card3").style.opacity = "0";
      return;
    }
    if (q1Response == 3)
    {
          document.querySelector("#card1").style.opacity = "1";
          document.querySelector("#card2").style.opacity = "1";
          document.querySelector("#card3").style.opacity = "1";
      return;
    }
    
  }
}

//RELOADS PAGE
function resetcards() {
   location.reload(); 
}


/* RUBRIC
The page allows user's input.
There is at least two event listener. 
The JavaScript code is in an external file
Code must include at least two control structures such as conditions, loops, etc.
Code must include at least one array
Both, content and style of the web page changes via code 


At least one image should be displayed dynamically using JS
There is an external CSS file with at least 20 properties

Comment on at least one of your peers using at least 100 words
(or on two peers using 50 words)	
*/

