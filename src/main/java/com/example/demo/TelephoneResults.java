package com.example.demo;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import lombok.Data;

@Data
@EntityScan
public class TelephoneResults {
  private Boolean valid;
  private Integer validAlphaNumericAccount;
}
