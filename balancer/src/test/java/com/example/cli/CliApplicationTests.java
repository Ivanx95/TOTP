package com.example.cli;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

class CliApplicationTests {

    @Test
    void mapTest() throws NoSuchFieldException, IllegalAccessException {

        Map<Integer,String> map = new HashMap<>(32,1000.0f);



        for(int i=1; i< 100;i++){
            System.out.println(Integer.valueOf(i*32).hashCode()%32);
            map.put(i*32, String.valueOf(Math.random()*10000));
        }

//        Class<HashMap> kClass =  HashMap.class;

//      Field f =  kClass.getDeclaredField("table");
        map.containsKey(Integer.valueOf(92));
//      f.setAccessible(true);

//      Object[] table = (Object[]) f.get(map);
//        Assertions.assertEquals(1,table.length);
    }

}
