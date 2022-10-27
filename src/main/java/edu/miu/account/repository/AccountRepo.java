package edu.miu.account.repository;

import edu.miu.account.entity.Account;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepo extends CassandraRepository<Account,Long> {

    Account findAccountByEmail(String email);
    Account findAccountByEmailAndPassword(String email, String password);
    List<Account> findAccountsByFirstName(String str);
    List<Account> findAccountsByFirstNameAndLastName(String firstName, String lastName);
}
