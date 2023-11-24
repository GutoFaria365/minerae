package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.acme.model.User;

@ApplicationScoped
public class UserService {

    @Transactional
    public void registerUser(String username, String password) {
        if (isUsernameUnique(username)) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password); // Note: You should hash and salt the password before storing it
            user.setRole("USER");
            user.setMetallum();
            user.persist();
        } else {
            throw new RuntimeException("Username is already in use");
        }
    }
    public boolean isUsernameUnique(String username) {
        return User.find("username", username).firstResult() == null;
    }

    public User findUserByUsername(String username) {
        return User.find("username", username).firstResult();
    }
}
