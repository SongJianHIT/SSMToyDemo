/**
 * @projectName Demo
 * @package tech.songjian.controller
 * @className tech.songjian.controller.BookController
 */
package tech.songjian.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tech.songjian.dao.BookDao;
import tech.songjian.domain.Book;
import tech.songjian.service.BookService;

import java.util.List;

/**
 * BookController
 *
 * @author SongJian
 * @description
 * @date 2022/11/25 16:44
 */
@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public Result save(@RequestBody  Book book) {
        boolean flag = bookService.save(book);
        return new Result(flag ? Code.SAVE_OK : Code.SAVE_ERR, flag);
    }

    @PutMapping
    public Result update(@RequestBody Book book) {
        boolean flag = bookService.update(book);
        return new Result(flag ? Code.UPDATE_OK : Code.UPDATE_ERR, flag);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean flag = bookService.delete(id);
        return new Result(flag ? Code.DELETE_OK : Code.DELETE_ERR, flag);
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        Book book = bookService.getById(id);
        Integer code = book != null ? Code.GET_OK : Code.GET_ERR;
        String msg = book != null ? "" : "数据查询失败，请重试";
        return new Result(code, book, msg);
    }

    @GetMapping
    public Result getAll() {
        List<Book> books = bookService.getAll();
        Integer code = books != null ? Code.GET_OK : Code.GET_ERR;
        String msg = books != null ? "" : "数据查询失败，请重试";
        return new Result(code, books, msg);
    }
}