/**
 * @projectName Demo
 * @package tech.songjian.exception
 * @className tech.songjian.exception.SystemException
 */
package tech.songjian.exception;

/**
 * SystemException
 * @description 系统异常
 * @author SongJian
 * @date 2022/11/25 22:28
 * @version
 */
public class SystemException extends RuntimeException{

    /**
     * 异常号
      */
    private Integer code;

    public SystemException(String message, Throwable cause, Integer code) {
        super(message, cause);
        this.code = code;
    }

    public SystemException(String message, Integer code) {
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
 
