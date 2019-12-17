package com.badcompany.auction;

import com.badcompany.auction.entities.ERole;
import com.badcompany.auction.entities.Lot;
import com.badcompany.auction.entities.Role;
import com.badcompany.auction.entities.User;
import com.badcompany.auction.repositories.LotRepository;
import com.badcompany.auction.repositories.RoleRepository;
import com.badcompany.auction.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class AuctionApplication implements CommandLineRunner {

	@Autowired
	UserRepository userRepository;

	@Autowired
	LotRepository lotRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	public static void main(String[] args) {
		SpringApplication.run(AuctionApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User eval = new User("Александр", "Кузнецов", "kuznec1337", encoder.encode("test_password"), "testmail@mail.ru");
		User admin = new User("Admin", "Adminishe", "admin", encoder.encode("admin"), "admin@admin.admin");
		User user = new User("Олег", "Простомолотов", "oleg228", encoder.encode("tester_pass"), "tester@localhost.com");
		roleRepository.save(new Role(ERole.ROLE_ADMIN));
		roleRepository.save(new Role(ERole.ROLE_EVALUATOR));
		roleRepository.save(new Role(ERole.ROLE_USER));
		Set<Role> roleEval = new HashSet<>();
		Set<Role> roleAdmin = new HashSet<>();
		Set<Role> roleUser = new HashSet<>();
		roleEval.add(roleRepository.findByName(ERole.ROLE_EVALUATOR).get());
		roleAdmin.add(roleRepository.findByName(ERole.ROLE_ADMIN).get());
		roleUser.add(roleRepository.findByName(ERole.ROLE_USER).get());
		eval.setRoles(roleEval);
		admin.setRoles(roleAdmin);
		user.setRoles(roleUser);
		userRepository.save(eval);
		userRepository.save(admin);
		userRepository.save(user);
		lotRepository.save(new Lot(userRepository.getOne(1L).getId(), 1000L, "Статуэтка из Украины 1880 года", false));
		lotRepository.save(new Lot(userRepository.getOne(1L).getId(), 2000L, "Монета царского двора 1789 года", false));
	}
}
