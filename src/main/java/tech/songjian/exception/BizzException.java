/**
 * @projectName Demo
 * @package tech.songjian.exception
 * @className tech.songjian.exception.BizzException
 */
package tech.songjian.exception;

/**
 * BizzException
 * @description 业务异常
 * @author SongJian
 * @date 2022/11/25 22:31
 * @version
 */
public class BizzException extends RuntimeException{
    /**
     * 异常号
     */
    private Integer code;

    public BizzException(String message, Throwable cause, Integer code) {
        super(message, cause);
        this.code = code;
    }

    public BizzException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
 
