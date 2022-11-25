/**
 * @projectName Demo
 * @package tech.songjian.service.impl
 * @className tech.songjian.service.impl.BookServiceImpl
 */
package tech.songjian.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.songjian.dao.BookDao;
import tech.songjian.domain.Book;
import tech.songjian.service.BookService;

import java.util.List;

/**
 * BookServiceImpl
 * @description
 * @author SongJian
 * @date 2022/11/25 16:42
 * @version
 */

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;

    @Override
    public boolean save(Book book) {
        bookDao.save(book);
        return true;
    }

    @Override
    public boolean update(Book book) {
        bookDao.update(book);
        return true;
    }

    @Override
    public boolean delete(Integer id) {
        bookDao.delete(id);
        return true;
    }

    @Override
    public Book getById(Integer id) {
        return bookDao.getById(id);
    }

    @Override
    public List<Book> getAll() {
        return bookDao.getAll();
    }
}
 
