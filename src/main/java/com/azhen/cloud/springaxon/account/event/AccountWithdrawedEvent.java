package com.azhen.cloud.springaxon.account.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountWithdrawedEvent {
    private String accountId;
    private Double amount;
}
