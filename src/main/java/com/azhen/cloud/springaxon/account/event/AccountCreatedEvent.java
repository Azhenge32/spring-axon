package com.azhen.cloud.springaxon.account.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountCreatedEvent {
    private String accountId;
}
