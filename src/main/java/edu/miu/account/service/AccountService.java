package edu.miu.account.service;

import edu.miu.account.entity.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    Logger LOGGER = LoggerFactory.getLogger(AccountService.class);
    boolean updatePassword(String email,String password);
    Account authenticate(String email,String password);
    List<String> getFollowers(String email);
    Account save(Account account);
    void follow(String followed,String userEmail);
    public Optional<Account> getById(Long id);

}
