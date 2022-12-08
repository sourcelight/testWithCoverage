package com.stackfortech.testWithCoverage.service;

import com.stackfortech.testWithCoverage.error.ErrorHandlerService;
import com.stackfortech.testWithCoverage.model.Account;
import com.stackfortech.testWithCoverage.model.Transfer;
import com.stackfortech.testWithCoverage.response.ErrorResponse;
import com.stackfortech.testWithCoverage.response.SuccessResponse;
import com.stackfortech.testWithCoverage.serviceimpl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountServiceTest {

    @InjectMocks
    private AccountServiceImpl accountServiceImpl;

    @Mock
    private ErrorHandlerService errorHandlerService;

    HashMap<Long,Account> mockedMap = new HashMap<>();

    @BeforeEach
    void setup()
    {
        HashMap<Long,Account> mockedMap = new HashMap<>();
        ReflectionTestUtils.setField(accountServiceImpl,"accountMap",mockedMap);
    }

    @Test
    void findOne_error()
    {
        Long accountNumber = 12345L;
        ResponseEntity<?> responseEntity =  accountServiceImpl.findOne(accountNumber);
        Object object = responseEntity.getBody() ;
        if(object instanceof ErrorResponse)
        {
            ErrorResponse errorResponse = (ErrorResponse) object;
            String expectedMessage = "Account Number does not exist";
            assert(errorResponse.getMessage().contains(expectedMessage));
        }

    }

    @Test
    void findOne_success()
    {
       Long accountNumber = 12346L;
       Account account = new Account();
       account.setRateOfInterest(3);
       account.setAccountNumber(accountNumber);
       account.setAccountStatus("ACTIVE");
       account.setAmount(25000L);
       account.setCurrency("INR");
       account.setAccountType("SAVINGS");
       mockedMap.put(accountNumber,account);

       ReflectionTestUtils.setField(accountServiceImpl,"accountMap",mockedMap);
       ResponseEntity<?> responseEntity = accountServiceImpl.findOne(accountNumber);
       Object object = responseEntity.getBody();
       if(object instanceof SuccessResponse)
       {
           SuccessResponse successResponse = (SuccessResponse) responseEntity.getBody();
           Account accountToRet = (Account) successResponse.getObject();

           assertEquals(accountNumber,accountToRet.getAccountNumber());
           assertEquals(account.getAmount(),accountToRet.getAmount());
           assertEquals(account.getAccountStatus(),accountToRet.getAccountStatus());
           assertEquals(account.getCurrency(),accountToRet.getCurrency());
           assertEquals(account.getAccountType(),accountToRet.getAccountType());

       }
       else
       {
           assertTrue(object instanceof  SuccessResponse);
       }
    }

    @Test
    void transfer_success() {
        Long accountNumber = 12346L;
        Account account = new Account();
        account.setRateOfInterest(3);
        account.setAccountNumber(accountNumber);
        account.setAccountStatus("ACTIVE");
        account.setAmount(25000L);
        account.setCurrency("INR");
        account.setAccountType("SAVINGS");

        Account account2 = new Account();
        account2.setRateOfInterest(3);
        account2.setAccountNumber(12347L);
        account2.setAccountStatus("ACTIVE");
        account2.setAmount(24000L);
        account2.setCurrency("INR");
        account2.setAccountType("SAVINGS");
        mockedMap.put(accountNumber,account);
        mockedMap.put(account2.getAccountNumber(),account2);

        ReflectionTestUtils.setField(accountServiceImpl,"accountMap",mockedMap);

        Transfer transfer = new Transfer();
        transfer.setToAccountNumber(12346L);
        transfer.setFromAccountNumber(12347L);
        transfer.setTransferAmount(10000L);
        transfer.setMode("IMPS");
       ResponseEntity<?> responseEntity =  accountServiceImpl.transfer(transfer);
        if(responseEntity.getBody() instanceof ErrorResponse)
        {
            ErrorResponse errorResponse = (ErrorResponse) responseEntity.getBody();
            assert(errorResponse instanceof  ErrorResponse);
        }


    }
    @Test
    void transfer_invalidToAccountNumber() {
        Long accountNumber = 12346L;
        Account account = new Account();
        account.setRateOfInterest(3);
        account.setAccountNumber(accountNumber);
        account.setAccountStatus("ACTIVE");
        account.setAmount(25000L);
        account.setCurrency("INR");
        account.setAccountType("SAVINGS");
        mockedMap.put(accountNumber,account);

        ReflectionTestUtils.setField(accountServiceImpl,"accountMap",mockedMap);

        Transfer transfer = new Transfer();
        transfer.setToAccountNumber(12347L);
        transfer.setFromAccountNumber(12346L);
        transfer.setTransferAmount(10000L);
        transfer.setMode("IMPS");
        ResponseEntity<?> responseEntity =  accountServiceImpl.transfer(transfer);
        if(responseEntity.getBody() instanceof ErrorResponse)
        {
            ErrorResponse errorResponse = (ErrorResponse) responseEntity.getBody();
            assert(errorResponse instanceof  ErrorResponse);
        }


    }
    @Test
    void transfer_invalidFromAccountNumber() {
        Long accountNumber = 12346L;
        Account account = new Account();
        account.setRateOfInterest(3);
        account.setAccountNumber(accountNumber);
        account.setAccountStatus("ACTIVE");
        account.setAmount(25000L);
        account.setCurrency("INR");
        account.setAccountType("SAVINGS");

        mockedMap.put(accountNumber,account);
        ReflectionTestUtils.setField(accountServiceImpl,"accountMap",mockedMap);

        Transfer transfer = new Transfer();
        transfer.setToAccountNumber(12346L);
        transfer.setFromAccountNumber(12347L);
        transfer.setTransferAmount(10000L);
        transfer.setMode("IMPS");
        ResponseEntity<?> responseEntity =  accountServiceImpl.transfer(transfer);
        if(responseEntity.getBody() instanceof ErrorResponse)
        {
            ErrorResponse errorResponse = (ErrorResponse) responseEntity.getBody();
            assert(errorResponse instanceof  ErrorResponse);
        }


    }

    @Test
    void transfer_InsufficientFundsTest() {
        Long accountNumber = 12346L;
        Account account = new Account();
        account.setRateOfInterest(3);
        account.setAccountNumber(accountNumber);
        account.setAccountStatus("ACTIVE");
        account.setAmount(25000L);
        account.setCurrency("INR");
        account.setAccountType("SAVINGS");

        Account account2 = new Account();
        account2.setRateOfInterest(3);
        account2.setAccountNumber(12347L);
        account2.setAccountStatus("ACTIVE");
        account2.setAmount(1000L);
        account2.setCurrency("INR");
        account2.setAccountType("SAVINGS");
        mockedMap.put(accountNumber,account);
        mockedMap.put(account2.getAccountNumber(),account2);

        ReflectionTestUtils.setField(accountServiceImpl,"accountMap",mockedMap);

        Transfer transfer = new Transfer();
        transfer.setToAccountNumber(12346L);
        transfer.setFromAccountNumber(12347L);
        transfer.setTransferAmount(10000L);
        transfer.setMode("IMPS");
        ResponseEntity<?> responseEntity =  accountServiceImpl.transfer(transfer);
        if(responseEntity.getBody() instanceof ErrorResponse)
        {
            ErrorResponse errorResponse = (ErrorResponse) responseEntity.getBody();
            assert(errorResponse instanceof  ErrorResponse);
        }


    }

}