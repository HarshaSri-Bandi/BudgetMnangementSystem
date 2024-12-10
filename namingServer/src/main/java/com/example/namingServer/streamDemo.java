package com.example.namingServer;
import java.util.*;
import java.util.stream.Collectors;

public class streamDemo {

    public static void main(String[] args) {
        System.out.println("Try programiz.pro");

        List<String> names = Arrays.asList("Harsha","Abhijeet","Jack");

        List<String> filteredNames = names.stream()
                .filter(name -> name.startsWith("h"))
                .collect(Collectors.toList());

        filteredNames.forEach(n -> System.out.println(n));
    }
}
