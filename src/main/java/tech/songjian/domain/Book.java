/**
 * @projectName Demo
 * @package tech.songjian.domain
 * @className tech.songjian.domain.Book
 */
package tech.songjian.domain;

/**
 * Book
 * @description Book POJO类
 * @author SongJian
 * @date 2022/11/25 14:56
 * @version
 */
public class Book {

    private int id;
    private String type;
    private String bookName;

    private String description;

    public Book(){}
    public Book(String type, String bookName, String description) {
        this.type = type;
        this.bookName = bookName;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", bookName='" + bookName + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
 
