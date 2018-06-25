package com.haitai.haitaitv.component.jfinal.annotation;

import com.haitai.haitaitv.component.util.ClassSearcher;
import com.jfinal.config.Routes;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * 自动绑定（来自jfinal ext)
 * 1.如果没用加入注解，必须以Controller结尾,自动截取前半部分为key
 * 2.加入ControllerBind的 获取 key
 */
public abstract class AutoBindRoutes<T> extends Routes {

    protected final Logger logger = LogManager.getLogger(AutoBindRoutes.class);

    private List<Class<? extends Controller>> excludeClasses = new ArrayList<>();

    private Class<T> baseClass;

    private String pkg;

    @SuppressWarnings({"rawtypes", "unchecked"})
    protected void autoAdd() {
        // 修改为寻找作为指定类的子类，并且位于指定包下的Controller
        List<Class<? extends T>> controllerClasses = ClassSearcher.findInPackage(baseClass, pkg);
        ControllerBind controllerBind;
        for (Class controller : controllerClasses) {
            if (excludeClasses.contains(controller)) {
                continue;
            }
            controllerBind = (ControllerBind) controller.getAnnotation(ControllerBind.class);
            if (controllerBind == null) {
                continue;
            }
            if (StrKit.isBlank(controllerBind.viewPath())) {
                this.add(controllerBind.controllerKey(), controller);
                logger.debug("routes.add(" + controllerBind.controllerKey() + ", " + controller.getName() + ")");
            } else {
                this.add(controllerBind.controllerKey(), controller, controllerBind.viewPath());
                logger.debug("routes.add(" + controllerBind.controllerKey() + ", " + controller + ","
                        + controllerBind.viewPath() + ")");
            }
        }
    }

    public void setBaseClass(Class<T> baseClass) {
        this.baseClass = baseClass;
    }

    public void setPkg(String pkg) {
        this.pkg = pkg;
    }

    public AutoBindRoutes addExcludeClass(Class<? extends Controller> clazz) {
        if (clazz != null) {
            excludeClasses.add(clazz);
        }
        return this;
    }

    public void addExcludeClasses(List<Class<? extends Controller>> clazzes) {
        excludeClasses.addAll(clazzes);
    }

    public List<Class<? extends Controller>> getExcludeClasses() {
        return excludeClasses;
    }

    public void setExcludeClasses(List<Class<? extends Controller>> excludeClasses) {
        this.excludeClasses = excludeClasses;
    }


}
