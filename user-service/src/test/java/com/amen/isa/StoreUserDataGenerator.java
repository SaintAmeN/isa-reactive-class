package com.amen.isa;

import com.amen.isa.component.repository.UserRepository;
import com.amen.isa.model.domain.Basket;
import com.amen.isa.model.domain.StoreUser;
import com.amen.isa.user.UserServiceApplication;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Slf4j
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = UserServiceApplication.class)
@ExtendWith(SpringExtension.class)
class StoreUserDataGenerator {
    @Autowired
    private UserRepository userRepository;

    @Test
    void generateUsers() {
        var faker = new Faker();

        for (int i = 0; i < 3; i++) {
            var storeUser = new StoreUser(null, faker.name().firstName(), faker.name().lastName(), Basket.empty());
            userRepository.save(storeUser).log().block();
        }
    }
}
