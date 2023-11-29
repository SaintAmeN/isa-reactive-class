package com.amen.isa.user.controller;

import com.amen.isa.model.domain.StoreUser;
import com.amen.isa.model.request.AddUserRequest;
import com.amen.isa.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping()
    public Flux<StoreUser> getAll() {
        return userService.getAll();
    }

    @PostMapping()
    public Mono<StoreUser> add(@RequestBody AddUserRequest request) {
        return userService.addUser(request);
    }
}
