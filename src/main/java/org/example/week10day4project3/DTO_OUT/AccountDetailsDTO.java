package org.example.week10day4project3.DTO_OUT;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountDetailsDTO {

    private String accountOwnerName;

    private String accountOwnerUserName;

    private String accountOwnerEmail;

    private String accountNumber;

    private Double balance;

    private Boolean isActive;

}
