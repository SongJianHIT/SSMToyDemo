/**
 * @projectName Demo
 * @package tech.songjian
 * @className tech.songjian.BookServiceTest
 */
package tech.songjian;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tech.songjian.config.SpringConfig;
import tech.songjian.domain.Book;
import tech.songjian.service.BookService;

import java.util.List;

/**
 * BookServiceTest
 * @description
 * @author SongJian
 * @date 2022/11/25 19:29
 * @version
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
public class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Test
    public void testGetById() {
        Book book = bookService.getById(1);
        System.out.println(book);
    }

    @Test
    public void testGetAll() {
        List<Book> books = bookService.getAll();
        System.out.println(books);
    }

    @Test
    public void testSave() {
        Book book = new Book("计算机","人工智能通识","好书");
        System.out.println(bookService.save(book));

    }
}
 
