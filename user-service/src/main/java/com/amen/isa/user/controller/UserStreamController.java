package com.amen.isa.user.controller;

import com.amen.isa.component.sse.Emitter;
import com.amen.isa.model.domain.StoreUser;
import com.amen.isa.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserStreamController {
    private final UserService userService;

    @GetMapping(path = "/sub", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<StoreUser> subscribeAll() {
        return Emitter.streamFlux(userService.getEmitter());
    }

    @GetMapping(path = "/tail", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<String> tailAll() {
        return userService.tailUsers();
    }

}
