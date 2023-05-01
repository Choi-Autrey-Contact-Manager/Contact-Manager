package src;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Scanner;


public class Contacts {
    public static void main(String[] args) throws IOException {
        String directory = "src/data";
        String filename = "contacts.txt";
        Path filepath = Paths.get(directory, filename);
        Charset charConvert = StandardCharsets.UTF_8;

        if (Files.notExists(Path.of(directory))) {
            Files.createDirectories(Path.of(directory));
        }

        if (!Files.exists(filepath)) {
            Files.createFile(filepath);
        }
        Scanner input = new Scanner(System.in);

        int choice;

        do {
            System.out.println("1. View contacts");
            System.out.println("2. Add a new contact.");
            System.out.println("3. Search a contact by name.");
            System.out.println("4. Delete an existing contact.");
            System.out.println("5. Exit.");
            System.out.print("Enter an option (1, 2, 3, 4 or 5): ");

            choice = input.nextInt();

            switch (choice) {
                case 1:
                    viewContacts(filepath, charConvert);
                    break;
                case 2:
                    addContact(filepath, charConvert, input);
                    break;
                case 3:
                    searchByName(filepath, charConvert, input);
                    break;
                case 4:
                    deleteContact(filepath, charConvert, input);
                    break;
                case 5:
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice, please try again");
            }
        } while (choice != 5);
    }

    private static void viewContacts(Path filepath, Charset charConvert) throws IOException {
        List<String> lines = Files.readAllLines(filepath, charConvert);

        if (lines.isEmpty()) {
            System.out.println("No contacts found");
        } else {
            for (int i = 0; i < lines.size(); i++) {
                System.out.println((i + 1) + ": " + lines.get(i));
            }
        }

    }

    private static void addContact(Path filepath, Charset charConvert, Scanner input) throws IOException {
        System.out.print("Enter name: ");
        String name = input.next();
        input.nextLine();
        System.out.print("Enter phone number: ");
        String phoneNumber = input.nextLine();

        String newContact = name + "  |  " + phoneNumber;

        Files.writeString(filepath, newContact + "\n", charConvert, StandardOpenOption.APPEND);

        System.out.println("Contact added: " + newContact);
    }
    private static void searchByName(Path filepath, Charset charConvert, Scanner input) throws IOException {
        System.out.print("Enter name to search for: ");
        String name = input.next();

        List<String> lines = Files.readAllLines(filepath, charConvert);

        boolean found = false;

        for (String line : lines) {
            if (line.toLowerCase().contains(name.toLowerCase())) {
                System.out.println(line);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No contacts found for name: " + name);
        }
    }
    private static void deleteContact(Path filepath, Charset charConvert, Scanner input) throws IOException {
        System.out.print("Enter name to delete: ");
        String name = input.next();

        List<String> lines = Files.readAllLines(filepath, charConvert);

        boolean found = false;
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).toLowerCase().contains(name.toLowerCase())) {
                System.out.println("Are you sure you want to delete this contact?");
                System.out.println(lines.get(i));
                System.out.println("Enter y for yes or n for no:");
                String choice = input.next();

                if (choice.equalsIgnoreCase("y")) {
                    lines.remove(i);
                    System.out.println("Contact deleted");
                } else {
                    System.out.println("Contact not deleted");
                }

                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("No contacts found for name: " + name);
        } else {
            Files.write(filepath, lines, charConvert);
        }
    }

}

