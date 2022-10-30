package model;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class UserImplTest {

  @Test
  public void testUserConstructor() {
    try{
      User usr = new UserImpl();
    }catch(IOException e){
      System.out.println(e.getMessage());
    }
  }

}