/**
 * @projectName Demo
 * @package tech.songjian.controller
 * @className tech.songjian.controller.Result
 */
package tech.songjian.controller;

/**
 * Result
 * @description
 * @author SongJian
 * @date 2022/11/25 21:25
 * @version
 */
public class Result {
    /**
     * 要封装的数据
     */
    private Object data;

    /**
     * 自定义的状态码，给前端返回
     */
    private Integer code;

    /**
     * 自定义的消息
     */
    private String msg;

    public Result(Integer code, Object data, String msg) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    public Result(Integer code, Object data) {
        this.data = data;
        this.code = code;
    }

    public Result() {}

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}