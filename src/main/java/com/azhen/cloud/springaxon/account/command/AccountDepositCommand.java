package com.azhen.cloud.springaxon.account.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
public class AccountDepositCommand {
    @TargetAggregateIdentifier
    private String accountId;
    private Double amount;
}
