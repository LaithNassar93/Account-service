package edu.miu.account.service.impl;


import edu.miu.account.entity.Account;
import edu.miu.account.repository.AccountRepo;
import edu.miu.account.service.AccountService;
import edu.miu.account.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    private CassandraTemplate template;
    @Autowired
    private RedisService redisService;

    @Override
    public Optional<Account> getById(Long id) {
        if (redisService.isExist(id.toString())) {
            return Optional.of(redisService.getValue(id.toString()));
        }
        LOGGER.info("GET Data from DB");
        Optional<Account> optionalUser = accountRepo.findById(id);
        optionalUser.ifPresent(user -> redisService.setValue(user.getId().toString(), user));
        return optionalUser;
    }


    public boolean updatePassword(String email,String password) {
        LOGGER.info(String.format("Updating User {%s} password",email));
        Account account= accountRepo.findAccountByEmailAndPassword(email,password);
        if(account == null){
            LOGGER.info(String.format("User {%s} is unAuthorized",email));
            return Boolean.FALSE;
        }
        account.setPassword(password);
        template.insert(account);
        LOGGER.info(String.format("User {%s} password Updated",email));
        return Boolean.TRUE;
    }

    public Account authenticate(String email,String password){
        LOGGER.info(String.format("Authenticate User {%s}",email));
        return accountRepo.findAccountByEmailAndPassword(email,password);

    }
    public List<String> getFollowers(String email){
        LOGGER.info(String.format("Getting all Followers for the User {%s}",email));
        Account accounts=accountRepo.findAccountByEmail(email);
        if(accounts==null)
            return null;
        else
            return accounts.getFollowers();

    }
    public Account save(Account account){
        LOGGER.info(String.format("Adding a new Account with Email {%s}, FirstName {%s}, LastName {%s}",account.getEmail(),account.getFirstName(),account.getLastName()));
        Account temp=accountRepo.findAccountByEmail(account.getEmail());
        if(temp!=null){
            // TODO :: Call Kafka Send email and message
            temp.setCreationDate(new Date());
            Account savedAccount = accountRepo.save(account);
            redisService.setValue(savedAccount.getId().toString(), savedAccount);
            return savedAccount;

        }
        return null;
    }
    public void follow(String followed,String userEmail){
        LOGGER.info(String.format("The User {%s} Followed {%s}",userEmail,followed));
        Account account= accountRepo.findAccountByEmail(userEmail);
        account.getFollowers().add(followed);

        Account savedAccount = accountRepo.save(account);
        redisService.setValue(savedAccount.getId().toString(), savedAccount);
    }

}
