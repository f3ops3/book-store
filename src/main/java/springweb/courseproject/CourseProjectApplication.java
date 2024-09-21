package springweb.courseproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springweb.courseproject.model.Book;
import springweb.courseproject.service.BookService;
import java.math.BigDecimal;

@SpringBootApplication
public class CourseProjectApplication {
    @Autowired
    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(CourseProjectApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            Book book = new Book();
            book.setTitle("Java Spring Boot");
            book.setAuthor("James Bond");
            book.setIsbn("asd");
            book.setPrice(BigDecimal.valueOf(100));

            bookService.save(book);
            System.out.println(bookService.findAll());
        };
    }
}
