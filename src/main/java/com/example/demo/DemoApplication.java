package com.example.demo;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@Controller
public class DemoApplication {

  private static Set<AlphaNumericTelephone> currentList;

  private  static int getMultiplier(char val) {
    int value = 0;
    switch(val){
      case '1':
      case '0':
        value = 1;
        break;
      case '7':
      case '9':
        value = 5;
        break;
      default:
        value = 4;
    }
    return value;
  }

  public static int maxOptionCounter(String phonenumber) {
      int value = 1;
      for(char val: phonenumber.toCharArray()) {
        value *= getMultiplier(val);
      }
      return value;
  }

  private static Set<AlphaNumericTelephone> generateAlphaNumberBasedOnIndex(String phonenumber, int lengthToStore){
    Set<AlphaNumericTelephone> perm = new TreeSet<AlphaNumericTelephone>();
    //Handling error scenarios
    if (phonenumber == null) {
        return null;
    } else if (phonenumber.length() == 0) {
        perm.add(new AlphaNumericTelephone(""));
        return perm;
    }
    for(String item : permutateStuff(phonenumber)) {
      perm.add(new AlphaNumericTelephone(item));
    }
    return perm;
  }

  private static Set<String> permutateStuff(String phonenumber){
    Set<String> answer = new TreeSet<String>();
    if(phonenumber != null && phonenumber.length() != 0) {
      Character currentChar = phonenumber.charAt(0);
      Set<Character> keys = AlphaNumericTelephone.alphaMatch(currentChar);
      Set<String> combs = permutateStuff(phonenumber.substring(1));
      if(keys.isEmpty()){
        if(combs.isEmpty()){
          answer.add(phonenumber);
        }
        for(String item: combs){
          answer.add(currentChar + item);
        }
      }
      for (Character j : keys){
        if(combs.isEmpty()){
          answer.add(j + phonenumber.substring(1));
        }
        for(String item : combs) {
          answer.add(j + item);
        }
      }
    }
    return answer;
  }

  @GetMapping("/resource")
  @ResponseBody
  public Map<String, Object> home() {
    Map<String, Object> model = new HashMap<String, Object>();
    model.put("id", UUID.randomUUID().toString());
    model.put("content", "Hello World");
    return model;
  }

  @GetMapping("/tel/{number}/{size}/{index}")
  @ResponseBody
  public Collection<AlphaNumericTelephone> alphaNumericList(@PathVariable String number, @PathVariable int size, @PathVariable int index) {
    Map<Integer, AlphaNumericTelephone> phones = new HashMap<Integer, AlphaNumericTelephone>();

    int currentIndex = index * size;
    int currentMax = currentIndex + size;
    int maxOptionCounter = maxOptionCounter(number);
    if(DemoApplication.currentList == null) {
      DemoApplication.currentList = generateAlphaNumberBasedOnIndex(number, number.length());
    }

    for(int i = currentIndex; i < currentMax && i < maxOptionCounter && i < DemoApplication.currentList.size(); i++){
      phones.put(i, (AlphaNumericTelephone) DemoApplication.currentList.toArray()[i]);
    }
    return phones.values();
  }

  @PostMapping("/tel")
  @ResponseBody
  public TelephoneResults all(@RequestBody String phonenumber){
    PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
    TelephoneResults results = new TelephoneResults();
    results.setValid(false);
    results.setValidAlphaNumericAccount(DemoApplication.maxOptionCounter(phonenumber));
    try {
      phoneUtil.parse(phonenumber, "US");
      results.setValid(true);
    } catch (NumberParseException e) {
      return results;
    }
    DemoApplication.currentList = null;
    return results;
  }

  @GetMapping(value = "/{path:[^\\.]*}")
  public String redirect() {
    return "forward:/";
  }

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }
}

@Component
@ConfigurationProperties("demo")
class DemoProperties {
  private String value;

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
