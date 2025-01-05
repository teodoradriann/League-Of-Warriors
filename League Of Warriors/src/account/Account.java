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

    public Integer getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(Integer gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public static class Information {
        private Credentials loginCredentials;
        private String name;
        private String country;
        private SortedSet<String> favouriteGames;

        private Information(Builder builder) {
            this.loginCredentials = builder.loginCredentials;
            this.name = builder.name;
            this.country = builder.country;
            this.favouriteGames = builder.favouriteGames;
        }

        public static class Builder {
            private Credentials loginCredentials;
            private String name;
            private String country;
            private SortedSet<String> favouriteGames;

            public Builder setLoginCredentials(Credentials loginCredentials) {
                this.loginCredentials = loginCredentials;
                return this;
            }

            public Builder setName(String name) {
                this.name = name;
                return this;
            }

            public Builder setCountry(String country) {
                this.country = country;
                return this;
            }

            public Builder setFavouriteGames(SortedSet<String> favouriteGames) {
                this.favouriteGames = favouriteGames;
                return this;
            }

            public Information build() {
                if (loginCredentials == null) {
                    throw new IllegalStateException("Login credentials must be provided");
                }
                if (name == null || name.isEmpty()) {
                    throw new IllegalStateException("Name must be provided");
                }
                return new Information(this);
            }
        }
    }
}