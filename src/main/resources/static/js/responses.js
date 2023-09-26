function getBotResponse(input) {
    //rock paper scissors
    if (input == "rock") {
        return "paper";
    } else if (input == "paper") {
        return "scissors";
    } else if (input == "scissors") {
        return "rock";
    }

    // Simple responses
    if (input == "hello") {
        return "Hello there! what can i do for you";
    } else if (input == "goodbye") {
        return "Talk to you later!";
    }
    else if (input == "hi") {
        return "hi nice to meet you! may i help you ?";
    }else if (input == "yes") {
        return " what do you want to know";
    }

    else if( input=="what help"){
        return "i can guid you to shopping with us";
    }else if(input=="how to buy"){
        return "after log in you can buy the products in the shop, u can add the product to the cart and then pay";
      }
       else if (input == "how to login") {
            return "you can login after registration";
    } 
     else if (input == "how to register") {
            return " Go to login option and then sign-up ";
    } 
    else if (input == "thank you") {
            return " your welcome! visit us again ";
    } 
    else if (input == "project guide") {
            return " Mr.VARADHARAJAN ";
    } 
     else if (input == "team members") {
            return " JISHNU NAMBIAR ,      SATEESWARBABU KATEM,    S.SANJAI,           SUDHIR KUMBHAR,           R.RANJINI DEVI ";
    } 

      else if(input=="how to pay"){
                return " you can pay either card or paytm";
        }else if(input=="customer care"){
                return "you can contact by :autopartsplus@gmail.com"
    }else if(input==""){
                return " ask something";
    }else if (input == "help me") {
            return "i am here to help you";
    } 
    else if (input == "i am fine") {
            return "happy to talk to you";
    } 
     else if (input == "what is autoparts") {
            return " this a spare parts for automative vehicles";
    }
     else if (input == "how are you") {
            return "i am fine , how are you?";
    }  
     else if (input == "how are you") {
            return "i am fine , how are you?";
    } 
    else {
        return "Try asking something else!";
    }

}