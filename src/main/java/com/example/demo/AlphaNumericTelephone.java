package com.example.demo;

import java.util.Collections;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.Map;
import java.util.Set;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import lombok.Data;

@Data
@EntityScan
public class AlphaNumericTelephone implements Comparable {
  private static final Map<Character, Character> ALPHA_PHONE_MAPPINGS;
  static {
    HashMap<Character, Character> alphaMap = new HashMap<Character, Character>(40);
    alphaMap.put('2', '2');
    alphaMap.put('A', '2');
    alphaMap.put('B', '2');
    alphaMap.put('C', '2');
    alphaMap.put('3', '3');
    alphaMap.put('D', '3');
    alphaMap.put('E', '3');
    alphaMap.put('F', '3');
    alphaMap.put('4', '4');
    alphaMap.put('G', '4');
    alphaMap.put('H', '4');
    alphaMap.put('I', '4');
    alphaMap.put('5', '5');
    alphaMap.put('J', '5');
    alphaMap.put('K', '5');
    alphaMap.put('L', '5');
    alphaMap.put('6', '6');
    alphaMap.put('M', '6');
    alphaMap.put('N', '6');
    alphaMap.put('O', '6');
    alphaMap.put('7', '7');
    alphaMap.put('P', '7');
    alphaMap.put('Q', '7');
    alphaMap.put('R', '7');
    alphaMap.put('S', '7');
    alphaMap.put('8', '8');
    alphaMap.put('T', '8');
    alphaMap.put('U', '8');
    alphaMap.put('V', '8');
    alphaMap.put('9', '9');
    alphaMap.put('W', '9');
    alphaMap.put('X', '9');
    alphaMap.put('Y', '9');
    alphaMap.put('Z', '9');
    ALPHA_PHONE_MAPPINGS = Collections.unmodifiableMap(alphaMap);
  }
  public static final Set<Character> alphaMatch(Character c){
    Set<Character> set = new TreeSet<Character>();
    for(Character ch : ALPHA_PHONE_MAPPINGS.keySet()) {
      if(ALPHA_PHONE_MAPPINGS.get(ch).equals(c)){
        set.add(ch);
      }
    }
    return set;
  }
  private String alpha;

  AlphaNumericTelephone(String alpha){
    this.alpha = alpha;
  }

@Override
  public int compareTo(Object o) {
    return this.alpha.compareTo(((AlphaNumericTelephone)o).alpha);
  }
}
