package com.estheryoo.yoo_esther_cookingmemories_casestudy.config;

import com.estheryoo.yoo_esther_cookingmemories_casestudy.entity.Recipe_Book;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.entity.Recipe_Page;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.entity.Role;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.entity.User;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.repository.RecipeBookRepository;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.repository.RecipePageRepository;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.repository.RoleRepository;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class DummyData implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RecipeBookRepository recipeBookRepository;
    private final RecipePageRepository recipePageRepository;
//    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DummyData(UserRepository userRepository,
//            , PasswordEncoder passwordEncoder
                     RoleRepository roleRepository,
                     RecipeBookRepository recipeBookRepository,
                     RecipePageRepository recipePageRepository) {
        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.recipeBookRepository = recipeBookRepository;
        this.recipePageRepository = recipePageRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        createRoles();
        createUsers();
        createBooks();
        createPages();
    }

    private void createUsers() throws Exception{
        //Create 1 User
        createUserIfNotExist("Esther","Yoo","eyoo@gmail.com");
    }


    private void createRoles() throws Exception{
        // Create 3 Roles
        createRoleIfNotExist(1L, "ADMIN");
        createRoleIfNotExist(2L, "USER");
        createRoleIfNotExist(3L, "WITH_LINK");
    }


    private void createBooks() throws Exception{
        //Create 3 Recipe Books
        createRecipeBookIfNotExist(1L,"Kimchi Recipes","How to ferment different types of vegetables");
        createRecipeBookIfNotExist(2L, "Mom's Korean Stews","Mom's Secret Stew Recipes");
        createRecipeBookIfNotExist(3L, "Mom's Side Dishes","Mom's korean side dish recipes" );
    }

    private void createPages() throws Exception{
        //Create 3 Pages
        createRecipePageIfNotExist(1L,"Cabbage Kimchi", "Fermented spicy cabbage");
        createRecipePageIfNotExist(2L, "Kimchi Stew", "Spicy cabbage stew.");
        createRecipePageIfNotExist(3L, "Soy Bean Stew", "Soy Bean based stew.");
    }

    private void createUserIfNotExist(String firstName, String lastName, String email) {
        User existingUser = userRepository.findByEmail(email);
        if (existingUser == null) {
            User user1 = new User();
            user1.setFirstName(firstName);
            user1.setLastName(lastName);
            user1.setEmail(email);
            user1.setPassword("123");// You may want to encode this password

            //setting admin role
            List<Role> roles = new ArrayList<>();
            Role admin = roleRepository.findByName("ADMIN");
            roles.add(admin);

            user1.setRoles(roles);
            userRepository.save(user1);
            System.out.println("User saved successfully.");
        } else {
            System.out.println("User already exists. Skipping creation.");
        }
    }

    private void createRoleIfNotExist(Long id, String roleName) {
        Role role = roleRepository.findById(id).orElse(null);
        if (role == null) {
            role = new Role();
            role.setId(id);
            role.setName(roleName);
            roleRepository.save(role);
            System.out.println("Role saved successfully.");
        } else {
            System.out.println("Role already exists. Skipping creation.");
        }
    }

    private void createRecipeBookIfNotExist(Long id, String title, String description){
        Recipe_Book recipeBook = recipeBookRepository.findById(id).orElse(null);
        if (recipeBook == null) {
            recipeBook = new Recipe_Book();
            recipeBook.setId(id);
            recipeBook.setTitle(title);
            recipeBook.setDescription(description);

            User user = userRepository.findById(1L).orElseThrow(()-> new RuntimeException("User not found"));
            recipeBook.setUser(user);
            recipeBookRepository.save(recipeBook);
            System.out.println("Recipe Book saved successfully.");
        }else{
            System.out.println("Recipe Book already exists. Skipping creation.");
        }
    }

    private void createRecipePageIfNotExist(Long id, String title, String description){
        Recipe_Page recipePage = recipePageRepository.findById(id).orElse(null);
        if (recipePage == null) {
            recipePage = new Recipe_Page();
            recipePage.setId(id);
            recipePage.setTitle(title);
            recipePage.setDescription(description);

            User user = userRepository.findById(1L).orElseThrow(()-> new RuntimeException("User not found"));
            recipePage.setUser(user);
            recipePageRepository.save(recipePage);
            System.out.println("Recipe Page saved successfully.");
        }else{
            System.out.println("Recipe Page already exists. Skipping creation.");
        }
    }
}
