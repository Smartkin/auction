package com.badcompany.auction;

import com.badcompany.auction.entities.User;
import com.badcompany.auction.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuctionApplication implements CommandLineRunner {

	@Autowired
	UserRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(AuctionApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		repository.save(new User("Александр", "Кузнецов", "test_password", "testmail@mail.ru"));
	}
}
