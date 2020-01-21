package com.badcompany.auction;

import com.badcompany.auction.entities.*;
import com.badcompany.auction.repositories.ImageRepository;
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
	ImageRepository imageRepository;

	@Autowired
	PasswordEncoder encoder;

	public static void main(String[] args) {
		SpringApplication.run(AuctionApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		imageRepository.save(new Image("/avatars/placeholder.png", EImageType.TYPE_USER));
//		User eval = new User("Александр", "Кузнецов", "kuznec1337", encoder.encode("test_password"), "testmail@mail.ru", imageRepository.getOne(1L));
//		User admin = new User("Admin", "Adminishe", "admin", encoder.encode("admin"), "admin@admin.admin", imageRepository.getOne(1L));
//		User user = new User("Олег", "Простомолотов", "oleg228", encoder.encode("tester_pass"), "tester@localhost.com", imageRepository.getOne(1L));
//		roleRepository.save(new Role(ERole.ROLE_ADMIN));
//		roleRepository.save(new Role(ERole.ROLE_EVALUATOR));
//		roleRepository.save(new Role(ERole.ROLE_USER));
//		Set<Role> roleEval = new HashSet<>();
//		Set<Role> roleAdmin = new HashSet<>();
//		Set<Role> roleUser = new HashSet<>();
//		roleEval.add(roleRepository.findByName(ERole.ROLE_EVALUATOR).get());
//		roleAdmin.add(roleRepository.findByName(ERole.ROLE_ADMIN).get());
//		roleUser.add(roleRepository.findByName(ERole.ROLE_USER).get());
//		eval.setRoles(roleEval);
//		admin.setRoles(roleAdmin);
//		user.setRoles(roleUser);
//		userRepository.save(eval);
//		userRepository.save(admin);
//		userRepository.save(user);
//		lotRepository.save(new Lot(userRepository.getOne(1L), 1000L, "Статуэтка из Украины 1880 года", false));
//		lotRepository.save(new Lot(userRepository.getOne(1L), 2000L, "Монета царского двора 1789 года", false));
//		lotRepository.save(new Lot(userRepository.getOne(1L), 3000L, "Серебро высокой пробы", false));
//		lotRepository.save(new Lot(userRepository.getOne(3L), 4000L, "Кольцо из древнего Рима", false));
//		lotRepository.save(new Lot(userRepository.getOne(3L), 5000L, "Монета старых годов", false));
//		lotRepository.save(new Lot(userRepository.getOne(3L), 6000L, "Кувшин из советских времён", false));
//		lotRepository.save(new Lot(userRepository.getOne(1L), 7000L, "Ещё один лот", false));
//		lotRepository.save(new Lot(userRepository.getOne(1L), 8000L, "Придумывать лоты сложно", false));
//		lotRepository.save(new Lot(userRepository.getOne(3L), 9000L, "Особенно когда до дедлайна считанные часы", false));
//		lotRepository.save(new Lot(userRepository.getOne(3L), 10000L, "Монета царского двора 1889 года", false));
//		lotRepository.save(new Lot(userRepository.getOne(3L), 11000L, "Монета царского двора 1849 года", false));
//		lotRepository.save(new Lot(userRepository.getOne(1L), 12000L, "Золото высокой пробы", false));
	}
}
