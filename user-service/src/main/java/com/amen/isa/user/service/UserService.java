package com.amen.isa.user.service;

import com.amen.isa.component.repository.UserRepository;
import com.amen.isa.component.sse.Emitter;
import com.amen.isa.model.domain.StoreUser;
import com.amen.isa.model.mapper.UserMapper;
import com.amen.isa.model.request.AddUserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final Emitter<StoreUser> emitter = new Emitter<>();

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public Mono<StoreUser> getUserById(String id){
        return Mono.empty();
    }

    public Flux<StoreUser> getAll() {
        return userRepository.findAll().log();
    }

    public Mono<StoreUser> addUser(final AddUserRequest request) {
        var user = userMapper.addUserRequestToStoreUser(request);
        return userRepository.save(user)
                .map(storeUser -> {
                    emitter.emit(storeUser);
                    return storeUser;
                });
    }

    public Emitter<StoreUser> getEmitter() {
        return emitter;
    }

    public Flux<String> tailUsers() {
        return userRepository.findAllByFirstNameContaining("*");
    }
}
