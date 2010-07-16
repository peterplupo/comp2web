package org.jboss.weld.examples.zk;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.NamingException;

@Named
@SessionScoped
public class Game implements Serializable
{
   private static final long serialVersionUID = 7097690441026467911L;
   
   private int number;
   private int guess;
   private int smallest;
   private int biggest;
   private int remainingGuesses;
   private String message;
   
   @Inject @MaxNumber private int maxNumber;
   
   //@Inject Generator generator;

   @Inject @Random Instance<Integer> randomNumber;
   
   public Game() throws NamingException {}

   public int getNumber()
   {
      return number;
   }
   
   public int getGuess()
   {
      return guess;
   }
   
   public void setGuess(int guess)
   {
      this.guess = guess;
      check(); //update state
   }
   
   public int getSmallest()
   {
      return smallest;
   }
   
   public int getBiggest()
   {
      return biggest;
   }
   
   public int getRemainingGuesses()
   {
      return remainingGuesses;
   }

   public boolean check() 
   {
	  if (guess > biggest || guess < smallest) {
		  message = "Oops! You have wasted one guess. It is between "+smallest+" and "+biggest;
	  }
	  else if (guess>number)
      {
         biggest = guess - 1;
         message = "Lower!";
      } 
      else if (guess<number)
      {
         smallest = guess + 1;
         message = "Higher!";
      }
      else {
    	 message = "You got it! The number is "+number;
      }
      remainingGuesses--;
      if (remainingGuesses <= 0)
      {
         message = "Too bad! You did not get the number: "+number+". Press the 'Reset' button to try again!";
      }
      return (guess == number);
   }

   @PostConstruct
   public void reset()
   {
      this.smallest = 0;
      this.guess = 0;
      this.remainingGuesses = 10;
      this.biggest = maxNumber;
      this.number = randomNumber.get();
      this.message = "Start guessing...";
   }
   
   public boolean isNoMoreGuess() {
	   return guess == number || remainingGuesses <= 0;
   }
   
   public String getMessage() {
	   return this.message;
   }
}
