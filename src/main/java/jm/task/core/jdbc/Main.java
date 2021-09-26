package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Tom", "Cat", (byte) 18);
        userService.saveUser("Nik", "Nikovich", (byte) 32);
        userService.saveUser("Poul", "Shon", (byte) 45);
        userService.saveUser("Boris", "Animal", (byte) 123);
        for (User user : userService.getAllUsers()) {
            System.out.println(user);
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
