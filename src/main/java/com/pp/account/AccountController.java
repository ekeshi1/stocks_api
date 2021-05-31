package com.pp.account;

import com.pp.user.UserJpa;
import com.pp.user.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

import static com.pp.util.ControllerUtil.illegalArgumentWrapper;

@RestController
public class AccountController {

    Logger logger = LogManager.getLogger(AccountController.class);
    AccountService accountService;
    UserRepository userRepository;

    public AccountController(AccountService accountService,UserRepository userRepository) {
        this.accountService = accountService;
        this.userRepository = userRepository;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/api/accounts/{accountId}/top-up")
    public ResponseEntity<?> topUp(@Valid @RequestBody AmountModel payload, @PathVariable UUID accountId) {
        var userId = getLoggedInUser();
        return illegalArgumentWrapper(() -> accountService.topup(payload,accountId,userId.getId()));
    }



    @PreAuthorize("hasRole('USER')")
    @GetMapping("/api/accounts/{accountId}/assets")
    public ResponseEntity<?> getAssets(@PathVariable UUID accountId) {
        var userId = getLoggedInUser();
        return illegalArgumentWrapper(() -> accountService.retrieveAccountAssets(accountId,userId.getId()));
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/api/accounts/{accountId}/assets")
    public ResponseEntity<?> buySingleAsset(@RequestBody AssetBuyModel payload, @PathVariable UUID accountId) {
        var userId = getLoggedInUser();
        return illegalArgumentWrapper(() -> accountService.buySingleAsset(payload.getAmount().getAmount(), payload.getAssetName(), accountId,userId.getId()));
    }



    private UserJpa getLoggedInUser() {
        var springUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var username = springUser.getUsername();
        return userRepository.findByEmail(username);
    }



}
