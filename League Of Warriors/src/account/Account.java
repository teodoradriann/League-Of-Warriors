package account;

import characters.Character;

import java.util.ArrayList;
import java.util.SortedSet;

public class Account {
    private Information accountInfo;
    private ArrayList<Character> ownedCharacters;
    private Integer gamesPlayed;

    public Account(ArrayList<Character> ownedCharacters, Integer gamesPlayed, Information accountInfo) {
        this.accountInfo = accountInfo;
        this.ownedCharacters = ownedCharacters;
        this.gamesPlayed = gamesPlayed;
    }

    public String getEmail() {
        return accountInfo.loginCredentials.getEmail();
    }

    public String getPassword() {
        return accountInfo.loginCredentials.getPassword();
    }

    public String getName() {
        return accountInfo.name;
    }

    public String getCountry() {
        return accountInfo.country;
    }

    public SortedSet<String> getFavouriteGames() {
        return accountInfo.favouriteGames;
    }

    public ArrayList<Character> getOwnedCharacters() {
        return ownedCharacters;
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
