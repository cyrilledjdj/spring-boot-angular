package com.example.demo;

import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

  @Autowired
  private DemoApplication demoApplicationMock;

  @Test
  public void permutationCounter(){
    assertTrue("Specific string will provide the right count of permutations", DemoApplication.maxOptionCounter("2014000000") == 16);
    assertTrue("Specific string will provide the right count of permutations", DemoApplication.maxOptionCounter("3014569856") == 81920);
  }

  @Test
  public void checkNumbers() throws Exception{
    TelephoneResults results = demoApplicationMock.all("3014567896");
    assertTrue(results.getValid());
    assertTrue(results.getValidAlphaNumericAccount() == 102400);
  }

  @Test
  public void list() throws Exception{
    Collection<AlphaNumericTelephone> collection = demoApplicationMock.alphaNumericList("2014000000", 15, 1);
    // There should only be one two pages for this specific size and the second page will have only one item
    for(AlphaNumericTelephone number : collection){
      assertTrue(number.getAlpha().equals("C01I000000"));
    }
  }


}
