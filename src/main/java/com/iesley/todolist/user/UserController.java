package com.iesley.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iesley.todolist.task.ITaskRepository;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody UserModel userModel) {
        var user = this.userRepository.findByUsername(userModel.getUsername());
        
        if (user != null) {
            return ResponseEntity.badRequest().body("User already exists");
        }

        var passwordHashred = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());

        userModel.setPassword(passwordHashred);
        
        var userCreated = userRepository.save(userModel);
        return ResponseEntity.ok(userCreated);
    }

    @DeleteMapping("/")
    public ResponseEntity delete(@RequestBody UserModel userModel, HttpServletRequest request) {
        var user = this.userRepository.findByUsername(userModel.getUsername());
        
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        var userId = request.getAttribute("userId");
        if (!user.getId().equals(userId)) {
            return ResponseEntity.badRequest().body("User not found");
        }

        var passwordVerify = BCrypt.verifyer().verify(userModel.getPassword().toCharArray(), user.getPassword());
        if (!passwordVerify.verified) {
            return ResponseEntity.badRequest().body("Password incorrect");
        }

        this.taskRepository.deleteByIdUser(user.getId());
        this.userRepository.delete(user);
        return ResponseEntity.ok().build();
    }
}
