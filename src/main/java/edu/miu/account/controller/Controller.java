package edu.miu.account.controller;

import edu.miu.account.entity.Account;
import edu.miu.account.model.AuthenticateRequest;
import edu.miu.account.repository.AccountRepo;
import edu.miu.account.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/v1/account")
@AllArgsConstructor
public class Controller {

    private AccountService accountService;
    private AccountRepo accountRepo;

    @PostMapping
    public Boolean saveAccount(@RequestBody Account account){
        return accountService.save(account) !=null? Boolean.TRUE: Boolean.FALSE;
    }

    @GetMapping("/{id}")
    public Account getAccount(@PathVariable Long id){
        return accountRepo.findById(id).orElse(null);
    }
    @GetMapping("/email/{email}")
    public Account getAccountByEmail(@PathVariable String email){
        return accountRepo.findAccountByEmail(email);
    }
    @GetMapping
    public List<Account> findAll(){
        return accountRepo.findAll();
    }
    @PostMapping("/follow/{userEmail}/{followed}")
    public void follow(@PathVariable String followed,@PathVariable String userEmail){
        accountService.follow(followed,userEmail);
    }
    @PostMapping("/authenticate")
    public ResponseEntity<Account>  authenticate(@RequestBody AuthenticateRequest authenticateRequest){
        Account account = accountService.authenticate(authenticateRequest.getUserName(),authenticateRequest.getPassword());
        return new ResponseEntity<Account>(account, account != null ?HttpStatus.OK:HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/followers/{email}")
    public List<String> getFollowers(@PathVariable String email){
        return accountService.getFollowers(email);
    }
}
