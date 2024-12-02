package Account;

import Characters.Character;
import Account.Credentials;

import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

public class Account {
    private Information accountInfo;
    private ArrayList<Character> ownedCharacters;
    private Integer gamesPlayed;

    public Account(ArrayList<Character> ownedCharacters, Integer gamesPlayed, Information accountInfo) {
        this.accountInfo = accountInfo;
        this.ownedCharacters = ownedCharacters;
        this.gamesPlayed = gamesPlayed;
    }

    public static class Information {
        private Credentials loginCredentials;
        private String name;
        private String country;
        private SortedSet<String> favouriteGames;

        public Information(Credentials loginCredentials, SortedSet<String> favouriteGames, String name, String country) {
            this.loginCredentials = loginCredentials;
            this.name = name;
            this.country = country;
            this.favouriteGames = favouriteGames;
        }
    }
}
