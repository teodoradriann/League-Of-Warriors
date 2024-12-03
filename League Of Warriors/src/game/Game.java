package game;

import account.Account;
import characters.Character;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import utils.JsonInput;

public class Game {
    private ArrayList<Account> existingAccounts;
    private Grid map;
    private Account loggedAccount;
    private Character selectedCharacter;
    private String keyPressed;

    public void init() throws InterruptedException {
        existingAccounts = JsonInput.deserializeAccounts();
        Scanner scanner = new Scanner(System.in);
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

        // flush the screen
        flushScreen();

        // TimeUnit.SECONDS.sleep(1);
        selectCharacterAndStartGame();
    }

    public void run() {
        while (true) {
            if (selectedCharacter.getHp() < 0 || keyPressed.equals("q")) {
                selectCharacterAndStartGame();
            }
        }
    }

    private void selectCharacterAndStartGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello " + this.loggedAccount.getName() + "!");
        System.out.println("Please choose your hero :)");
        int i = 1;
        for (Character character : this.loggedAccount.getOwnedCharacters()) {
            System.out.println(i + ": (" + character.getName() + ") -> type: " + character.getProfession() + ", level: " + character.getLevel() + ", xp: " + character.getXp());
            i++;
        }

        while (true) {
            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Please choose a valid character!");
                scanner.nextLine();
                continue;
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

    private static void flushScreen() {
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }
    }
}