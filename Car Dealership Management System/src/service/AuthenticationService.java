package service;

import java.util.List;
import java.util.Scanner;
import model.user.User;

public class AuthenticationService {
    private User loggedInUser;

    public String[] login(List<User> users) {
//    public boolean login(User[] users, String role) {
        String[] usernamePassword = new String[2];
        System.out.println("--- Login ---");
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                loggedInUser = user;
                usernamePassword[0] = user.getUsername();
                usernamePassword[1] = user.getPassword();

                System.out.println("Login successful!");
                System.out.println("-------------------------");
                return usernamePassword;
            }
        }
        System.out.print("Invalid username or password.\n-------------------------\n");
        return usernamePassword;
    }

    public void logout() {
        if (loggedInUser != null) {
//            System.out.println("Logout successful for " + loggedInUser.getRole() + ": " + loggedInUser.getUsername());
            System.out.println("Logout successful !");
            loggedInUser = null;
        } else {
            System.out.println("No user is currently logged in.");
        }
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }
}
