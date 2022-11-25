/**
 * @projectName Demo
 * @package tech.songjian.dao
 * @className tech.songjian.dao.BookDao
 */
package tech.songjian.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import tech.songjian.domain.Book;

import java.util.List;

/**
 * BookDao
 * @description
 * @author SongJian
 * @date 2022/11/25 16:34
 * @version
 */

public interface BookDao {

    /**
     * 插入图书
     * @param book
     */
    @Insert("insert into books values (null, #{type}, #{bookName}, #{description})")
    public void save(Book book);

    /**
     * 修改图书信息
     * @param book
     */
    @Update("update books set type=#{type}, bookname=#{bookName}, description=#{description} where id=#{id}")
    public void update(Book book);

    /**
     * 删除图书
     * @param id
     */
    @Delete("delete from books where id=#{id}")
    public void delete(Integer id);

    /**
     * 根据 id 删除
     * @param id
     * @return
     */
    @Select("select * from books where id=#{id}")
    public Book getById(Integer id);

    /**
     * 查找全部图书
     * @return
     */
    @Select("select * from books")
    public List<Book> getAll();
}
 
