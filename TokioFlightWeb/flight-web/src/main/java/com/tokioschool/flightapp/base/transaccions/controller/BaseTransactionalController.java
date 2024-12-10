package com.tokioschool.flightapp.base.transaccions.controller;

import com.tokioschool.flightapp.base.transaccions.service.BaseTransactionalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class BaseTransactionalController {

    private final BaseTransactionalService baseTransactionalService;

    @GetMapping("/base/transaction/non-trasactional-with-one-commit")
    public ResponseEntity<Void> nonTransactionalWithOneCommit(){
        baseTransactionalService.nonTransactionalWithOneCommit();

        return ResponseEntity.ok().build();
    }
    @GetMapping("/base/transaction/non-trasactional-with-multiple-commit")
    public ResponseEntity<Void> nonTransactionalWithMultipleCommit(){
        baseTransactionalService.nonTransactionalWithMultipleCommit();

        return ResponseEntity.ok().build();
    }

    @GetMapping("/base/transaction/trasactional-with-one-commit")
    public ResponseEntity<Void> transactionalWithOneCommit(){
        baseTransactionalService.transactionalWithOneCommit();
        return ResponseEntity.ok().build();
    }
    @GetMapping("/base/transaction/trasactional-with-multiple-commit")
    public ResponseEntity<Void> transactionalWithMultipleCommit(){
        baseTransactionalService.transactionalWithMultipleCommit();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/base/transaction/non-trasactional-with-exception")
    public ResponseEntity<Void> nonTransactionalWithException(){
        baseTransactionalService.nonTransactionalWithException();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/base/transaction/trasactional-with-exception")
    public ResponseEntity<Void> transactionalWithException(){
        baseTransactionalService.transactionalWithException();

        return ResponseEntity.ok().build();
    }

    @GetMapping("/base/transaction/trasactional-with-checked-commit")
    public ResponseEntity<Void> transactionalWithCheckedCommit() throws IOException {
        baseTransactionalService.transactionalWithCheckedCommit();

        return ResponseEntity.ok().build();
    }

    @GetMapping("/base/transaction/trasactional-with-checked-rollback")
    public ResponseEntity<Void> transactionalWithCheckedRollback() throws IOException {
        baseTransactionalService.transactionalWithCheckedRollback();

        return ResponseEntity.ok().build();
    }
}
