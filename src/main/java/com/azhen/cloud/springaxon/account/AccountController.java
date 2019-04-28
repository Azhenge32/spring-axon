package com.azhen.cloud.springaxon.account;

import com.azhen.cloud.springaxon.account.command.AccountCreateCommand;
import com.azhen.cloud.springaxon.account.command.AccountDepositCommand;
import com.azhen.cloud.springaxon.account.command.AccountWithdrawCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    private CommandGateway commandGateway;

    @PostMapping("")
    public CompletableFuture<Object> create() {
        UUID accountId = UUID.randomUUID();
        AccountCreateCommand command = new AccountCreateCommand(accountId.toString());
        return commandGateway.send(command);
    }

    @PutMapping("/{accoundId}/deposit/{amount}")
    public void deposit(@PathVariable("accoundId") String accountId, @PathVariable("amount") Double amount) {
        commandGateway.send(new AccountDepositCommand(accountId, amount));
    }

    @PutMapping("/{accoundId}/withdraw/{amount}")
    public void withdraw(@PathVariable("accoundId") String accountId, @PathVariable("amount") Double amount) {
        commandGateway.send(new AccountWithdrawCommand(accountId, amount));
    }
}
