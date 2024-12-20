package io.github.leonardosf98.todolist.user;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody UserModel userModel) {
        UserModel userToFind = this.userRepository.findByUsername(userModel.getUsername());
        if (userToFind == null) {
            String hashedPass = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());
            userModel.setPassword(hashedPass);
            UserModel createdUser = this.userRepository.save(userModel);
            return ResponseEntity.ok().body(createdUser);
        }
        return ResponseEntity.status(400).body("Usuário já cadastrado");
    }
}
