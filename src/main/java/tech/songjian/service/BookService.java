/**
 * @projectName Demo
 * @package tech.songjian.service
 * @className tech.songjian.service.BookService
 */
package tech.songjian.service;

import org.springframework.transaction.annotation.Transactional;
import tech.songjian.domain.Book;

import java.util.List;

/**
 * BookService
 * @description
 * @author SongJian
 * @date 2022/11/25 16:40
 * @version
 */
@Transactional
public interface BookService {

    /**
     * 保存图书
     * @param book
     * @return
     */
    public boolean save(Book book);

    /**
     * 修改图书信息
     * @param book
     * @return
     */
    public boolean update(Book book);

    /**
     * 根据 id 删除
     * @param id
     * @return
     */
    public boolean delete(Integer id);

    /**
     * 根据 id 查询
     * @param id
     * @return
     */
    public Book getById(Integer id);

    /**
     * 查询全部
     * @return
     */
    public List<Book> getAll();
}