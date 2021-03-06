package net.bestofcode.Facebook.service;

import lombok.AllArgsConstructor;
import net.bestofcode.Facebook.model.User;
import net.bestofcode.Facebook.persistence.forms.LoginFormData;
import org.springframework.stereotype.Service;
import net.bestofcode.Facebook.model.profile.VerificationPassword;

@Service
@AllArgsConstructor
public class LoginService {

    DatabaseService databaseService;

    public User loginUser(LoginFormData loginFormData) {

        User user = this.databaseService.getUserByEmail(loginFormData.getEmail());

        if(loginFormData.isValid() && user != null) {

            // validate password:
            VerificationPassword password = new VerificationPassword(loginFormData.getVerificationPassword().getVerificationPassword());
            if(password.getEncryptedPassword().equals(user.getPassword().getEncryptedPassword())) {
                return user;
            }
        } else {
            return User.generateErrorUser(loginFormData.getUsername());
        }
        return User.generateErrorUser(loginFormData.getUsername());
    }

}
