package game;

import account.Account;
import characters.Character;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import exceptions.InvalidCommandException;
import utils.JsonInput;

public class Game {
    private ArrayList<Account> existingAccounts;
    private Grid map;
    private Account loggedAccount;
    private Character selectedCharacter;
    Scanner scanner = new Scanner(System.in);

    public void init() throws InterruptedException {
        existingAccounts = JsonInput.deserializeAccounts();
        loginScreen();
        flushScreen();
        TimeUnit.SECONDS.sleep(0);
        selectCharacterAndStartGame();
    }

    public void run() throws InterruptedException {
        while (true) {
            if (selectedCharacter.getHp() != null && selectedCharacter.getHp() < 0.0) {
                System.out.println("GAME OVER! YOU DIED.");
                TimeUnit.SECONDS.sleep(3);
                flushScreen();
                selectCharacterAndStartGame();
            }
            String key = scanner.nextLine();
            switch (key.toLowerCase()) {
                case "w":
                    map.moveHero("north");
                    break;
                case "s":
                    map.moveHero("south");
                    break;
                case "a":
                    map.moveHero("west");
                    break;
                case "d":
                    map.moveHero("east");
                    break;
                case "q":
                    flushScreen();
                    selectCharacterAndStartGame();
                    return;
            }
        }
    }

    private void loginScreen() {
        boolean accountFound = false;
        while (true) {
            System.out.print("Enter your email: ");
            String email = scanner.nextLine();
            System.out.print("Enter your password: ");
            String password = scanner.nextLine();

            for (Account account : existingAccounts) {
                if (account.getEmail().equals(email) && account.getPassword().equals(password)) {
                    accountFound = true;
                    this.loggedAccount = account;
                    break;
                }
            }

            if (accountFound) break;
            else System.out.println("Account not found!");
        }
    }

    private void selectCharacterAndStartGame() throws InterruptedException {
        System.out.println("Hello " + this.loggedAccount.getName() + "!");
        System.out.println("Please choose your hero :)");
        int i = 1;
        System.out.println("-1: Go back to login screen.");
        for (Character character : this.loggedAccount.getOwnedCharacters()) {
            System.out.println(i + ": (" + character.getName() + ") -> type: " + character.getProfession() + ", level: " + character.getLevel() + ", xp: " + character.getXp());
            i++;
        }

        while (true) {
            int choice;
            try {
                if (!scanner.hasNextInt()) {
                    throw new InvalidCommandException("Invalid command: Input must be an integer.");
                }
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InvalidCommandException e) {
                System.out.println(e.getMessage());
                scanner.nextLine();
                continue;
            }

            if (choice == -1) {
                flushScreen();
                this.init();
                break;
            }

            if (choice > 0 && choice <= this.loggedAccount.getOwnedCharacters().size()) {
                this.selectedCharacter = this.loggedAccount.getOwnedCharacters().get(choice - 1);
                break;
            } else {
                System.out.println("Please choose a valid character!");
            }
        }
        flushScreen();
        Random rand = new Random();
        int length = rand.nextInt(3, 11);
        int height = rand.nextInt(3, 11);
        map = Grid.generateMap(length, height, selectedCharacter);
        map.printMap();

        this.run();
    }

    static void flushScreen() {
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }
    }
}
