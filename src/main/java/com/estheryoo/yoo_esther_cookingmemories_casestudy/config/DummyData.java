package com.estheryoo.yoo_esther_cookingmemories_casestudy.config;

import com.estheryoo.yoo_esther_cookingmemories_casestudy.entity.*;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DummyData implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RecipeBookRepository recipeBookRepository;
    private final RecipePageRepository recipePageRepository;
    private final RecipeStepRepository recipeStepRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DummyData(UserRepository userRepository,
                     PasswordEncoder passwordEncoder,
                     RoleRepository roleRepository,
                     RecipeBookRepository recipeBookRepository,
                     RecipePageRepository recipePageRepository, RecipeStepRepository recipeStepRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.recipeBookRepository = recipeBookRepository;
        this.recipePageRepository = recipePageRepository;
        this.recipeStepRepository = recipeStepRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        createRoles();
        createUsers();
        createBooks();
        createPages();
        createSteps();
        //assigning page to a book
        assignPageToBook(1L,1L);
        assignPageToBook(2L,2L);
        assignPageToBook(3L,2L);

        //assigning a step to a page
        assignStepToPage(1L,1L);
        assignStepToPage(2L,1L);
        assignStepToPage(3L,1L);
    }

    private void createUsers() throws Exception{
        //Create 1 User
        createUserIfNotExist("Esther","Yoo","eyoo@gmail.com");
    }


    private void createRoles() throws Exception{
        // Create 3 Roles
        createRoleIfNotExist(1L, "ROLE_ADMIN");
        createRoleIfNotExist(2L, "ROLE_USER");
        createRoleIfNotExist(3L, "ROLE_WITH_LINK");
    }


    private void createBooks() throws Exception{
        //Create 3 Recipe Books
        createRecipeBookIfNotExist(1L,"Kimchi Recipes","How to ferment different types of vegetables");
        createRecipeBookIfNotExist(2L, "Mom's Korean Stews","Mom's Secret Stew Recipes");
        createRecipeBookIfNotExist(3L, "Mom's Side Dishes","Mom's korean side dish recipes" );
    }

    private void createPages() throws Exception{
        //ingredients arraylist:
        ArrayList<String> ingredients1 = new ArrayList<>(Arrays.asList("Korean Cabbage","Grounded Pepper Flakes","Onion", "Garlic","Flour","Apples","Salt, Salted Shrimp"));
        ArrayList<String> ingredients2 = new ArrayList<>(Arrays.asList("Kimchi", "Tofu", "Pork", "Soybean Paste", "Red Chili Paste"));
        ArrayList<String> ingredients3 = new ArrayList<>(Arrays.asList("Soybean Paste", "Tofu","Green Squash", "Red Chili Paste"));
        //Create 3 Pages
        createRecipePageIfNotExist(1L,"Cabbage Kimchi", "Fermented spicy cabbage", ingredients1);
        createRecipePageIfNotExist(2L, "Kimchi Stew", "Spicy cabbage stew.", ingredients2);
        createRecipePageIfNotExist(3L, "Soybean Paste Stew", "Soybean based stew.",ingredients3);
    }

    private void createSteps(){
        //create steps for pages
        createStepIfNotExist(1L, "Clean", "Soak korean cabbage in salt brine. When the cabbage is soft and limp, take them out of the water.");
        createStepIfNotExist(2L, "Making the paste", "Boil water and flour on the stove. When it looks like mash potatoes, let it cool. Add pepper flakes, cooked flour, grounded garlic, grounded onion, and grounded apples together. Mix well.");
        createStepIfNotExist(3L, "Put it together", "Spread the paste you made onto every leaf of the brined korean cabbage.");
    }

    private void createUserIfNotExist(String firstName, String lastName, String email) {
        User existingUser = userRepository.findByEmail(email);
        if (existingUser == null) {
            User user1 = new User();
            user1.setFirstName(firstName);
            user1.setLastName(lastName);
            user1.setEmail(email);
            String password = passwordEncoder.encode("123");
            user1.setPassword(password);// You may want to encode this password

            //setting admin role
            Role admin = roleRepository.findByName("ROLE_ADMIN");
            user1.setRole(admin);
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

    private void createRecipePageIfNotExist(Long id, String title, String description, ArrayList<String> ingredients){
        Recipe_Page recipePage = recipePageRepository.findById(id).orElse(null);
        if (recipePage == null) {
            recipePage = new Recipe_Page();
            recipePage.setId(id);
            recipePage.setTitle(title);
            recipePage.setDescription(description);
            recipePage.setIngredients(ingredients);
            //set user
            User user = userRepository.findById(1L).orElseThrow(()-> new RuntimeException("User not found"));
            recipePage.setUser(user);

            recipePageRepository.save(recipePage);
            System.out.println("Recipe Page saved successfully.");
        }else{
            System.out.println("Recipe Page already exists. Skipping creation.");
        }
    }

    private void createStepIfNotExist(Long id, String subtitle, String description){
        Recipe_Step recipeStep = recipeStepRepository.findById(id).orElse(null);
        if (recipeStep == null) {
            recipeStep = new Recipe_Step();
            recipeStep.setId(id);
            recipeStep.setSubtitle(subtitle);
            recipeStep.setDescription(description);
            recipeStepRepository.save(recipeStep);
            System.out.println("Step saved successfully.");
        }else{
            System.out.println("Step already exists. Skipping creation.");
        }

    }

    private void assignPageToBook(Long pageId, Long bookId){
        Recipe_Book recipeBook = recipeBookRepository.findById(bookId).orElse(null);
        Recipe_Page recipePage = recipePageRepository.findById(pageId).orElse(null);

        if (recipeBook != null && recipePage != null) {
            if (recipeBook.getPages() == null) {
                recipeBook.setPages(new ArrayList<>()); // Initialize if null
            }
            if(!recipeBook.getPages().contains(recipePage)){
            recipeBook.getPages().add(recipePage);
            recipeBookRepository.save(recipeBook);
            System.out.println("Page added successfully.");
            }else{
            System.out.println("Page already exists in book. Skipping creation.");
            }
        }else{
            System.out.println("Recipe book and/or recipe page does not exist. Skipping creation.");
        }
    }

    private void assignStepToPage(Long stepId, Long pageId){
        Recipe_Step recipeStep = recipeStepRepository.findById(stepId).orElse(null);
        Recipe_Page recipePage = recipePageRepository.findById(pageId).orElse(null);

        if (recipeStep != null && recipePage != null) {
//            if (recipePage.getSteps() == null) {
//                recipePage.setSteps(new ArrayList<>()); // Initialize if null
//            }
            if(!recipePage.getSteps().contains(recipeStep)){
                recipeStep.setRecipePage(recipePage);
                recipePage.getSteps().add(recipeStep);
                recipePageRepository.save(recipePage);

                System.out.println("Step added to page successfully.");
            }else{
                System.out.println("Step already exists in page. Skipping creation.");
            }
        }else{
            System.out.println("Recipe page and/or recipe step does not exist. Skipping creation.");
        }
    }
}
