//package com.programming.courseservice.repository;
//
//import com.programming.courseservice.domain.persistent.entity.Category;
//import com.programming.courseservice.domain.persistent.entity.Topic;
//import static org.assertj.core.api.Assertions.assertThat;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@DataJpaTest
//public class CategoryRepositoryTests {
//    @Autowired
//    private CategoryRepository categoryRepository;
//
//    // JUnit test for save category operation
//    @Test
//    @DisplayName("JUnit test for save category operation")
//    public void givenCategoryObject_whenSave_thenReturnSavedCategory() {
//        // given - preconditions or setup
//        Category category = Category.builder()
//                .name("IT Software")
//                .description("IT Software description")
//                .build();
//        Topic topic1 = new Topic("Java", "Java description");
//        Topic topic2 = new Topic("C#", "C# description");
//        category.setTopics(List.of(topic1, topic2));
//        // when - action or behaviour that we are going test
//        Category savedCategory = categoryRepository.save(category);
//        // then - verify the output
//        assertThat(savedCategory).isNotNull();
//        assertThat(savedCategory.getTopics().size()).isEqualTo(2);
//        assertThat(savedCategory.getName()).isEqualTo("IT Software");
//        assertThat(savedCategory.getTopics().get(0).getCategory()).isNotNull();
//        System.out.println(savedCategory);
//    }
//
//    // JUnit test for get all operation
//    @Test
//    @DisplayName("JUnit test for get all operation")
//    public void givenCategoryList_whenFindAll_thenReturnCategoryList() {
//        // given - preconditions or setup
//        Category category = Category.builder()
//                .name("IT Software")
//                .description("IT Software description")
//                .build();
//        Topic topic1 = new Topic("Java", "Java description");
//        Topic topic2 = new Topic("C#", "C# description");
//        category.setTopics(List.of(topic1, topic2));
//
//        Category category2 = Category.builder()
//                .name("IT Network")
//                .description("IT Network description")
//                .build();
//        Topic topic3 = new Topic("TCP/IP", "TCP/IP description");
//        Topic topic4 = new Topic("Websocket", "Websocket description");
//        category2.setTopics(List.of(topic3, topic4));
//        categoryRepository.save(category);
//        categoryRepository.save(category2);
//        // when - action or behaviour that we are going test
//        List<Category> categoryList = categoryRepository.findAll();
//        // then - verify the output
//        assertThat(categoryList.size()).isEqualTo(2);
//        assertThat(categoryList.get(0).getTopics().size()).isEqualTo(2);
//        categoryList.forEach(System.out::println);
//    }
//
//    // JUnit test for get category by id operation
//    @Test
//    @DisplayName("JUnit test for get by id operation")
//    public void givenCategoryList_whenFindById_thenReturnCategoryObject() {
//        // given - preconditions or setup
//        Category category = Category.builder()
//                .name("IT Software")
//                .description("IT Software description")
//                .build();
//        Topic topic1 = new Topic("Java", "Java description");
//        Topic topic2 = new Topic("C#", "C# description");
//        category.setTopics(List.of(topic1, topic2));
//        categoryRepository.save(category);
//        // when - action or behaviour that we are going test
//        Category categoryDb = categoryRepository.findById(category.getId()).get();
//        // then - verify the output
//        assertThat(categoryDb).isNotNull();
//        assertThat(categoryDb.getTopics().size()).isEqualTo(2);
//        System.out.println(category);
//    }
//
//    // JUnit test for category by name operation
//    @Test
//    @DisplayName("JUnit test for category by name operation")
//    public void givenCategoryName_whenFindByName_thenReturnCategoryObject() {
//        // given - preconditions or setup
//        Category category = Category.builder()
//                .name("IT Software")
//                .description("IT Software description")
//                .build();
//        Topic topic1 = new Topic("Java", "Java description");
//        Topic topic2 = new Topic("C#", "C# description");
//        category.setTopics(List.of(topic1, topic2));
//        categoryRepository.save(category);
//        // when - action or behaviour that we are going test
//        Category categoryDb = categoryRepository.findByName(category.getName()).get();
//        // then - verify the output
//        assertThat(categoryDb).isNotNull();
//        System.out.println(category);
//    }
//    // JUnit test for update category operation
//    @Test
//    @DisplayName("JUnit test for update category operation")
//    public void givenCategoryObject_whenUpdateCategory_thenReturnUpdatedCategory() {
//        // given - preconditions or setup
//        Category category = Category.builder()
//                .name("IT Software")
//                .description("IT Software description")
//                .build();
//        Topic topic1 = new Topic("Java", "Java description");
//        Topic topic2 = new Topic("C#", "C# description");
//        List<Topic> topics = new ArrayList<>();
//        topics.add(topic1);
//        topics.add(topic2);
//        category.setTopics(topics);
//        categoryRepository.save(category);
//        // when - action or behaviour that we are going test
//        Category savedCategory = categoryRepository.findById(category.getId()).get();
//        savedCategory.setName("IT Software (1)");
//        savedCategory.setDescription("IT Software description (1)");
//        Category updatedCategory = categoryRepository.save(savedCategory);
//        // then - verify the output
//        assertThat(updatedCategory.getName()).isEqualTo("IT Software (1)");
//        assertThat(updatedCategory.getDescription()).isEqualTo("IT Software description (1)");
//        System.out.println(updatedCategory);
//    }
//    // JUnit test for set removed operation
//    @Test
//    @DisplayName("JUnit test for set removed operation")
//    public void givenCategoryObject_whenSetRemoved_thenReturnCategoryObject() {
//        // given - preconditions or setup
//        Category category = Category.builder()
//                .name("IT Software")
//                .description("IT Software description")
//                .build();
//        Topic topic1 = new Topic("Java", "Java description");
//        Topic topic2 = new Topic("C#", "C# description");
//        List<Topic> topics = new ArrayList<>();
//        topics.add(topic1);
//        topics.add(topic2);
//        category.setTopics(topics);
//        categoryRepository.save(category);
//        // when - action or behaviour that we are going test
//        Category savedCategory = categoryRepository.findById(category.getId()).orElse(null);
//        savedCategory.setRemoved(true);
//        Category updatedCategory = categoryRepository.save(savedCategory);
//        // then - verify the output
//        assertThat(updatedCategory).isNotNull();
//        assertThat(updatedCategory.getRemoved()).isTrue();
//    }
//
//    // JUnit test for delete category operation
//    @Test
//    @DisplayName("JUnit test for delete category operation")
//    public void givenCategoryObject_whenDelete_thenDeletedCategory() {
//        // given - preconditions or setup
//        Category category = Category.builder()
//                .name("IT Software")
//                .description("IT Software description")
//                .build();
//        Topic topic1 = new Topic("Java", "Java description");
//        List<Topic> topics = new ArrayList<>();
//        topics.add(topic1);
//        category.setTopics(topics);
//        categoryRepository.save(category);
//        // when - action or behaviour that we are going test
//        categoryRepository.deleteById(category.getId());
//        Optional<Category> optionalCategory = categoryRepository.findById(category.getId());
//        // then - verify the output
//        assertThat(optionalCategory).isEmpty();
//    }
//
//    // JUnit test for get category by name operation
//    @Test
//    @DisplayName("JUnit test for get category by name operation")
//    public void given_when_then() {
//        // given - preconditions or setup
//        Category category = Category.builder()
//                .name("IT Software")
//                .description("IT Software description")
//                .build();
//        Topic topic1 = new Topic("Java", "Java description");
//        List<Topic> topics = new ArrayList<>();
//        topics.add(topic1);
//        category.setTopics(topics);
//        categoryRepository.save(category);
//        // when - action or behaviour that we are going test
//        Optional<Category> optionalCategory = categoryRepository.findByName(category.getName());
//        // then - verify the output
//        assertThat(optionalCategory).isPresent();
//    }
//}
