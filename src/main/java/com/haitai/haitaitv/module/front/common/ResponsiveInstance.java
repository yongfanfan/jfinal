package com.haitai.haitaitv.module.front.common;

/**
 * 提供BaseDTO的一些单例
 *
 * @author liuzhou
 *         create at 2017-07-12 15:35
 * @see BaseDTO
 */
public abstract class ResponsiveInstance implements Responsive {

    public static final Responsive INVALID_ACCESS_TOKEN = new ResponsiveInstance() {{
        setStatus(1002);
    }};
    public static final Responsive WRONG_OPERATOR_ID = new ResponsiveInstance() {{
        setStatus(1003);
    }};
    public static final Responsive WRONG_PARAMS = new ResponsiveInstance() {{
        setStatus(1004);
    }};
    public static final Responsive EXCEPTION = new ResponsiveInstance() {{
        setStatus(1005);
    }};
    public static final Responsive NO_DATA = new ResponsiveInstance() {{
        setStatus(1100);
    }};
    public static final Responsive SUCCESS = new ResponsiveInstance() {{
        setStatus(2000);
    }};

    private int status;

    @Override
    public int getStatus() {
        return status;
    }

    void setStatus(int status) {
        this.status = status;
    }

}
