package com.azhen.cloud.springaxon.account;

import com.azhen.cloud.springaxon.account.command.AccountCreateCommand;
import com.azhen.cloud.springaxon.account.command.AccountDepositCommand;
import com.azhen.cloud.springaxon.account.command.AccountWithdrawCommand;
import com.azhen.cloud.springaxon.account.event.AccountCreatedEvent;
import com.azhen.cloud.springaxon.account.event.AccountDepositedEvent;
import com.azhen.cloud.springaxon.account.event.AccountWithdrawedEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import javax.persistence.Entity;
import javax.persistence.Id;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
@Data
@Entity(name = "tb_account")
@NoArgsConstructor
public class Account {
    // @AggregateIdentifier
    @Id
    private String accountId;
    private Double deposit;

    @CommandHandler
    public Account(AccountCreateCommand command) {
        apply(new AccountCreatedEvent(command.getAccountId()));
    }

    @CommandHandler
    public void handle(AccountWithdrawCommand command) {
        if (this.deposit >= command.getAmount()) {
            apply(new AccountWithdrawedEvent(command.getAccountId(), command.getAmount()));
        } else {
            throw new IllegalArgumentException("余额不足");
        }
    }

    @CommandHandler
    public void handle(AccountDepositCommand command) {
        apply(new AccountDepositedEvent(command.getAccountId(), command.getAmount()));
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        this.accountId = event.getAccountId();
        this.deposit = 0D;
    }

    @EventSourcingHandler
    public void on(AccountWithdrawedEvent event) {
        this.deposit -= event.getAmount();
    }

    @EventSourcingHandler
    public void on(AccountDepositedEvent event) {
        this.deposit += event.getAmount();
    }
}
