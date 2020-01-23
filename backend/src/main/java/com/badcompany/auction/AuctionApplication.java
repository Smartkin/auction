package com.badcompany.auction;

import com.badcompany.auction.entities.*;
import com.badcompany.auction.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

@SpringBootApplication
public class AuctionApplication implements CommandLineRunner {

	@Autowired
	UserRepository userRepository;

	@Autowired
	LotRepository lotRepository;

	@Autowired
	CategoryRepository categoryRepository;

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
		if (userRepository.findAll().size() == 0) {
			initCategories();
			initImages();
			initRoles();
			initUsers();
			initLots();
		}
		System.out.println("Finished initial database initialization!");
	}

	// Init test categories
	private void initCategories() {
		Category mainCat = new Category("Букинистические антикварные книги");
		categoryRepository.save(mainCat);
		categoryRepository.save(new Category("Старинные книги", mainCat));
		categoryRepository.save(new Category("Репринтные издания", mainCat));
		categoryRepository.save(new Category("Автографы", mainCat));
		categoryRepository.save(new Category("Газеты, журналы", mainCat));
		categoryRepository.save(new Category("Разное в букинистике", mainCat));

		mainCat = new Category("Гравюры, карты, плакаты");
		categoryRepository.save(mainCat);
		categoryRepository.save(new Category("Карты и атласы", mainCat));
		categoryRepository.save(new Category("Русские гравюры", mainCat));
		categoryRepository.save(new Category("Европейские гравюры", mainCat));
		categoryRepository.save(new Category("Японские гравюры", mainCat));
		categoryRepository.save(new Category("Плакаты", mainCat));
		categoryRepository.save(new Category("Другое в гравюрах, картах, атласах", mainCat));

		mainCat = new Category("Живопись, графика");
		categoryRepository.save(mainCat);
		categoryRepository.save(new Category("Русская живопись до 1917 г.", mainCat));
		categoryRepository.save(new Category("Советская живопись 1917-1990 гг.", mainCat));
		categoryRepository.save(new Category("Зарубежная живопись до 1945 г.", mainCat));
		categoryRepository.save(new Category("Зарубежная живопись 1946-1990 гг.", mainCat));
		categoryRepository.save(new Category("Современная живопись после 1991 г.", mainCat));
		categoryRepository.save(new Category("Рисунок, акварель, графика", mainCat));
		categoryRepository.save(new Category("Копии и репродукции", mainCat));
		categoryRepository.save(new Category("Разное в живописи, графике", mainCat));

		mainCat = new Category("Иконы и утварь");
		categoryRepository.save(mainCat);
		categoryRepository.save(new Category("Иконы", mainCat));
		categoryRepository.save(new Category("Медная пластика", mainCat));
		categoryRepository.save(new Category("Церковная утварь", mainCat));
		categoryRepository.save(new Category("Лампады", mainCat));
		categoryRepository.save(new Category("Религиозные книги", mainCat));
		categoryRepository.save(new Category("Разное в иконах и утвари", mainCat));

		mainCat = new Category("Фарфор, фаянс, керамика");
		categoryRepository.save(mainCat);
		categoryRepository.save(new Category("Статуэтки, скульптуры", mainCat));
		categoryRepository.save(new Category("Посуда", mainCat));
		categoryRepository.save(new Category("Фаянс, майолика, керамика", mainCat));
		categoryRepository.save(new Category("Другое в фарфоре, фаянсе, керамике", mainCat));

		mainCat = new Category("Стекло, хрусталь");
		categoryRepository.save(mainCat);
		categoryRepository.save(new Category("Стекло, хрусталь 1918-1990 гг.", mainCat));
		categoryRepository.save(new Category("Стекло, хрусталь до 1917 г.", mainCat));
		categoryRepository.save(new Category("Стекло, хрусталь после 1990 г.", mainCat));

		mainCat = new Category("Серебро");
		categoryRepository.save(mainCat);
		categoryRepository.save(new Category("Русское серебро", mainCat));
		categoryRepository.save(new Category("Серебро Европы, Америки", mainCat));
		categoryRepository.save(new Category("Серебро других стран", mainCat));

		mainCat = new Category("Ювелирные изделия");
		categoryRepository.save(mainCat);
		categoryRepository.save(new Category("Серебряные ювелирные изделия", mainCat));
		categoryRepository.save(new Category("Украшения из других металлов", mainCat));
		categoryRepository.save(new Category("Золотые ювелирные изделия", mainCat));
		categoryRepository.save(new Category("Футляры, шкатулки для ювелирных украшений", mainCat));
		categoryRepository.save(new Category("Другое в ювелирных изделиях", mainCat));
	}

	// Init test images
	private void initImages() {
		imageRepository.save(new Image("/avatars/placeholder.png", EImageType.TYPE_USER));
		imageRepository.save(new Image("/lots/placeholder.png", EImageType.TYPE_LOT));
	}

	// Init roles
	private void initRoles() {
		roleRepository.save(new Role(ERole.ROLE_ADMIN));
		roleRepository.save(new Role(ERole.ROLE_EVALUATOR));
		roleRepository.save(new Role(ERole.ROLE_USER));
	}

	// Init test users with roles
	private void initUsers() {
		int userAmount = 1000; // Normal users
		int evalAmount = 10; // Evaluators
		int adminAmount = 1; // Admins

		Set<Role> roleEval = new HashSet<>();
		Set<Role> roleAdmin = new HashSet<>();
		Set<Role> roleUser = new HashSet<>();
		roleEval.add(roleRepository.findByName(ERole.ROLE_EVALUATOR).get());
		roleAdmin.add(roleRepository.findByName(ERole.ROLE_ADMIN).get());
		roleUser.add(roleRepository.findByName(ERole.ROLE_USER).get());

		// Init test users
		for(int i = 0; i < userAmount; ++i) {
			User user = new User("Имя_"+i,
					"Фамилия_"+i,
					"user" + i,
					encoder.encode("password"+i),
					"email"+i+"@simpmail.com",
					imageRepository.getOne(1L));
			user.setRoles(roleUser);
			userRepository.save(user);
		}

		// Init test evaluators
		for(int i = 0; i < evalAmount; ++i) {
			User eval = new User("Имя_"+i,
					"Фамилия_"+i,
					"evaluator" + i,
					encoder.encode("password"+i),
					"evaluator"+i+"@simpmail.com",
					imageRepository.getOne(1L));
			eval.setRoles(roleEval);
			userRepository.save(eval);
		}

		// Init admins
		for(int i = 0; i < adminAmount; ++i) {
			User admin = new User("Имя_"+i,
					"Фамилия_"+i,
					"admin" + i,
					encoder.encode("password"+i),
					"admin"+i+"@simpmail.com",
					imageRepository.getOne(1L));
			admin.setRoles(roleAdmin);
			userRepository.save(admin);
		}
	}

	// Init test lots
	private void initLots() {
		Random rand = new Random(new Date().getTime());
		List<Category> categories = categoryRepository.findAll();
		// Find amount of lots in each category
		int lotAmount = 100000;
		String lotDescription = "Таким образом реализация намеченных плановых заданий позволяет оценить значение новых предложений. Равным образом консультация с широким активом требуют определения и уточнения модели развития. Повседневная практика показывает, что укрепление и развитие структуры обеспечивает широкому кругу (специалистов) участие в формировании дальнейших направлений развития. Если у вас есть какие то интересные предложения, обращайтесь! Студия Web-Boss всегда готова решить любую задачу. Значимость этих проблем настолько очевидна, что дальнейшее развитие различных форм деятельности обеспечивает широкому кругу (специалистов) участие в формировании новых предложений. Таким образом реализация намеченных плановых заданий позволяет оценить значение новых предложений. Идейные соображения высшего порядка, а также укрепление и развитие структуры играет важную роль в формировании существенных финансовых и административных условий.";
		for(int i = 0; i < lotAmount; ++i) {
			User user = userRepository.getOne(1L + rand.nextInt(999));
			Category category = categories.get(rand.nextInt(categories.size()));
			String lotName = "Лот " + category.getName() + " " + i;
			String fullLotDesc = lotName + ". " + lotDescription;
			Lot lot = new Lot(user, 10000L + Math.abs(rand.nextInt()) % 1000000, lotName, lotName + ". " + lotDescription, rand.nextBoolean());
			Set<Category> categories1 = new HashSet<>();
			categories1.add(category);
			lot.setCategories(categories1);
			lotRepository.save(lot);
		}
	}
}
