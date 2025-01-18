package com.example.e_voting_system.Services;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class SillyNameService {

    private static final List<String> NAMES = Arrays.asList(
            "SpongeBob", "Luffy", "CR7", "Messi", "Neymar", "Tommy Shelby",
            "IronMan", "Batman", "Goku", "Thor",
            "Pikachu", "ScoobyDoo", "Shrek", "Minion", "DonkeyKong",
            "Mario", "Sonic", "Spider-Man", "Hulk", "Captain America", "Harry Potter",
            "Walter White", "Jon Snow", "Deadpool", "Yoda", "Among Us", "Steve (Minecraft)",
            "Pac-Man", "Godzilla", "King Kong", "John Wick", "The Joker"
    );

    private final Random random = new Random();

    public String getRandomSillyName() {
        return NAMES.get(random.nextInt(NAMES.size()));
    }
}
