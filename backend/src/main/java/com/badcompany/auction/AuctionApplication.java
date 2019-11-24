package com.badcompany.auction;

import com.badcompany.auction.entities.Lot;
import com.badcompany.auction.entities.User;
import com.badcompany.auction.repositories.LotRepository;
import com.badcompany.auction.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuctionApplication implements CommandLineRunner {

	@Autowired
	UserRepository userRepository;

	@Autowired
	LotRepository lotRepository;

	public static void main(String[] args) {
		SpringApplication.run(AuctionApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		userRepository.save(new User("Александр", "Кузнецов", "test_password", "testmail@mail.ru"));
		lotRepository.save(new Lot(userRepository.getOne(1L).getId(), 1000L, "Статуэтка из Украины 1880 года", false));
		lotRepository.save(new Lot(userRepository.getOne(1L).getId(), 2000L, "Монета царского двора 1789 года", false));
	}
}
