package com.estheryoo.yoo_esther_cookingmemories_casestudy;

import com.estheryoo.yoo_esther_cookingmemories_casestudy.dto.RecipeBookDTO;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.dto.RecipeStepDTO;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.dto.UserDTO;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.entity.*;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.repository.*;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.service.RecipeBookService;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.service.RecipePageService;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.service.RecipeStepService;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class YooEstherCookingMemoriesCaseStudyApplicationTests {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RecipeBookRepository recipeBookRepository;
    @Autowired
    private RecipePageRepository recipePageRepository;
    @Autowired
    private RecipeStepRepository recipeStepRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private RecipeBookService recipeBookService;
    @Autowired
    private RecipePageService recipePageService;
    @Autowired
    private RecipeStepService recipeStepService;


    //tests for user repository
    @Test
    void testFindVByEmail() {
        User exist = userRepository.findByEmail("jDoe@gmail.com");

        if(exist == null){
        User user1 = new User();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user1.setFirstName("Jane");
        user1.setLastName("Doe");
        user1.setEmail("jDoe@gmail.com");
        String password = passwordEncoder.encode("123");
        user1.setPassword(password);
        userRepository.save(user1);

        User testUser = userRepository.findByEmail("jDoe@gmail.com");
        assertThat(testUser.getFirstName()).isEqualTo(user1.getFirstName());

        }else{
            assertThat(exist.getEmail()).isEqualTo("jDoe@gmail.com");
        }

    }

    //testing Recipe Book Repository
    @Test
    void testFindByTitle(){
        Recipe_Book exist1 = recipeBookRepository.findByTitle("Book 1");
        User testUser = userRepository.findByEmail("jDoe@gmail.com");

        if(testUser == null){
            testUser = new User();
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            testUser.setFirstName("Jane");
            testUser.setLastName("Doe");
            testUser.setEmail("jDoe@gmail.com");
            String password = passwordEncoder.encode("123");
            testUser.setPassword(password);
            userRepository.save(testUser);
        }

        if(exist1 == null){
        Recipe_Book recipeBook1 = new Recipe_Book();
        recipeBook1.setTitle("Book 1");
        recipeBook1.setUser(testUser);
        Recipe_Book saved1 = recipeBookRepository.save(recipeBook1);
        assertThat(saved1.getTitle()).isEqualTo("Book 1");
        }else{
            assertThat(exist1.getTitle()).isEqualTo("Book 1");
        }
    }

    //testing recipe page repository (had to change method name b/c it was the same as recipe book)
    @Test
    void testFindByTitleInPage(){
       User testUser = userRepository.findByEmail("jDoe@gmail.com");
       Recipe_Page exist = recipePageRepository.findByTitle("Page 1");

       if(testUser == null){
           testUser = new User();
           PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
           testUser.setFirstName("Jane");
           testUser.setLastName("Doe");
           testUser.setEmail("jDoe@gmail.com");
           testUser.setRole(roleRepository.findByName("ROLE_USER"));
           String password = passwordEncoder.encode("123");
           testUser.setPassword(password);
           userRepository.save(testUser);
       }

       if(exist == null){
           List<String> ingredients = new ArrayList<>();
           ingredients.add("eggs");
           ingredients.add("butter");
        Recipe_Page recipePage1 = new Recipe_Page();
        recipePage1.setTitle("Page 1");
        recipePage1.setDescription("Description 1");
        recipePage1.setIngredients(ingredients);
        recipePage1.setUser(testUser);
        recipePageRepository.save(recipePage1);

        Recipe_Page test1 = recipePageRepository.findByTitle("Page 1");
        assertThat(test1.getTitle()).isEqualTo(recipePage1.getTitle());
       }else{
           assertThat(exist.getTitle()).isEqualTo("Page 1");
       }
    }


    //Testing step repository queries
    @Test
    void testFindByPage_Id(){
        User testUser = userRepository.findByEmail("jDoe@gmail.com");
        Recipe_Page recipePage = recipePageRepository.findByTitle("Page 2");

        if(recipePage == null){

            List<String> ingredients = new ArrayList<>();
            ingredients.add("eggs");
            ingredients.add("butter");

            recipePage = new Recipe_Page();
            recipePage.setTitle("Page 2");
            recipePage.setDescription("Description 2");
            recipePage.setIngredients(ingredients);
            recipePage.setUser(testUser);
            Recipe_Page saved = recipePageRepository.save(recipePage);


            Recipe_Step step1 = new Recipe_Step();
            step1.setSubtitle("Step 1");
            step1.setRecipePage(saved);


            Recipe_Step step2 = new Recipe_Step();
            step2.setSubtitle("Step 2");
            step2.setRecipePage(saved);

            Recipe_Step step3 = new Recipe_Step();
            step3.setSubtitle("Step 3");
            step3.setRecipePage(saved);

            recipeStepRepository.save(step1);
            recipeStepRepository.save(step2);
            recipeStepRepository.save(step3);

            List<Recipe_Step> testSteps = recipeStepRepository.findByPage_Id(saved.getId());
            assertThat(testSteps.size()).isEqualTo(3);

        }else{
            List<Recipe_Step> testSteps = recipeStepRepository.findByPage_Id(recipePage.getId());
            assertThat(testSteps.size()).isEqualTo(3);
        }


    }

    //Test Recipe Step service
    @Test
    void testGetRecipeStepById(){
        RecipeStepDTO stepFound = recipeStepService.getRecipeStepById(1L);
        assertThat(stepFound.getSubtitle()).isEqualTo("Clean");
    }


    //Testing Parameterized
    @ParameterizedTest
    @ValueSource(longs={1,2,3})
    void testGetStepById(Long id){
        RecipeStepDTO stepFound = recipeStepService.getRecipeStepById(id);
        assertThat(stepFound.getSubtitle()).isNotEmpty();
    }
}
