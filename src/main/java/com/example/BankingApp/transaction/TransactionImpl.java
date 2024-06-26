package com.example.BankingApp.transaction;

import com.example.BankingApp.account.enums.CurrencyEnum;
import com.example.BankingApp.account.model.Account;
import com.example.BankingApp.account.repository.AccountRepository;
import com.example.BankingApp.transaction.dto.TransactionDto;
import com.example.BankingApp.transaction.enums.TransactionType;
import com.example.BankingApp.transaction.model.Transaction;
import com.example.BankingApp.transaction.repository.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class TransactionImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionImpl(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public Integer add(TransactionDto dto) {
        Transaction transaction = new Transaction();
        transaction.setAmount(dto.getAmount());
        transaction.setCurrencyEnum(CurrencyEnum.fromValue(dto.getCurrency()));
        transaction.setTransactionType(TransactionType.fromValue(dto.getType()));

        Account account = accountRepository.findByIban(dto.getIban());

        if (dto.getType().equals(TransactionType.DEBIT.getValue())){
            account.setBalance(subtractedBalance(account.getBalance(),dto.getAmount()));
        }else {
            account.setBalance(addedBalance(account.getBalance(), dto.getAmount()));
        }
        transaction.setAccount(account);
        transactionRepository.save(transaction);

        return transaction.getId();
    }

    private Double subtractedBalance(Double balance,Double amount){
        if (balance>amount){
            return balance-amount;
        }else
            throw new ArithmeticException("transaction is interrupted because balance is less than amount");

    }
    private Double addedBalance(Double balance,Double amount){
        return balance+amount;
    }
}
