package edu.miu.account.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Indexed;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Account {
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    @Indexed
    private String email;
    private String password;
    private List<String> followers;
    private List<String> followings;
    private Gender gender;
    private Address address;
    private Date creationDate;

}
