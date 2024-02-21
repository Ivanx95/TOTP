package com.example.totp.security;

import com.otp.TOTP;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

@Component
@Slf4j
public class TOTPAuthenticationProvider implements AuthenticationProvider {

    private Map<String, List<String>> registeredDevices =new HashMap<>();

    public  TOTPAuthenticationProvider(){
        //TODO, move to a database
        registeredDevices.put("jaisen", Arrays.asList("1234"));
        registeredDevices.put("pedro", Arrays.asList("4444"));

    }

    @PostConstruct
    public void setUp(){
        log.info("TOTP lapse time {}", TOTP.lapse);
    }
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
      String name = authentication.getName();
      String password = authentication.getCredentials().toString();


      TOTP totp = new TOTP();
      if(registeredDevices.containsKey(name)){
          for(String key: registeredDevices.get(name)){
              totp.setSecret(key);
              String otp =  totp.getOtp();
              if(password.equals(otp)){
                  List<GrantedAuthority> authorities = new ArrayList<>();
                  authorities.add(new SimpleGrantedAuthority("all"));
                  Authentication auth = new UsernamePasswordAuthenticationToken(name,
                          password, authorities);


                  return auth;
              }else{
                  log.warn("Password not equals,expected: {}, actual: {}",otp,password);
              }
          }
      }
        throw new BadCredentialsException("Not authenticated");

    }


    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
