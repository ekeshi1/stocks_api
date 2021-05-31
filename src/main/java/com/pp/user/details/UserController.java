package com.pp.user.details;


import com.pp.account.AccountController;
import com.pp.account.AccountModel;
import com.pp.account.AccountRepository;
import com.pp.account.AccountService;
import com.pp.user.UserJpa;
import com.pp.user.UserRepository;
import com.pp.user.role.RoleJpa;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.pp.util.ControllerUtil.illegalArgumentWrapper;

@RestController
public class UserController {
    Logger logger = LogManager.getLogger(AccountController.class);
    AccountService accountService;
    UserRepository userRepository;
    AccountRepository accountRepository;
    public UserController(AccountService accountService,UserRepository userRepository, AccountRepository accountRepository) {
        this.accountService = accountService;
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }


    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/api/users/details", method = RequestMethod.GET)
    public ResponseEntity<UserDetailsModel> user() {
        var user = getLoggedInUser();
        return ResponseEntity.ok(UserDetailsModel.newInstance(user));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/api/users/{userId}/accounts")
    public ResponseEntity<?> getUserAccounts(@PathVariable UUID userId) {
        var loggedInUser = getLoggedInUser();
        var user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User doesn't exist.");
        }

        return  illegalArgumentWrapper(() -> accountService.getUserAccounts(loggedInUser.getId(), RoleJpa.toDomain(loggedInUser.getRoles()),userId));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/api/users/{userId}/accounts")
    public ResponseEntity<?> createNewUserAccount(@PathVariable UUID userId,@RequestBody  AccountCreationModel model){
        var user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("No such user");
        }

        return ResponseEntity.ok(accountService.createNewUserAccount(model,user.get()));
    }

    private UserJpa getLoggedInUser() {
        var springUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var username = springUser.getUsername();
        return userRepository.findByEmail(username);
    }



}
