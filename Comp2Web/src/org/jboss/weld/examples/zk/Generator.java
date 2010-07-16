package org.jboss.weld.examples.zk;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class Generator implements Serializable 
{
   private static final long serialVersionUID = -7272128162191903266L;

   private java.util.Random random = new java.util.Random(System.currentTimeMillis());
   
   private int maxNumber = 100;
   
   java.util.Random getRandom()
   {
      return random;
   }
   
   @Produces @Random int next()
   {
      return getRandom().nextInt(maxNumber); 
   }
   
   @Produces @MaxNumber int getMaxNumber()
   {
      return maxNumber;
   }
} 
