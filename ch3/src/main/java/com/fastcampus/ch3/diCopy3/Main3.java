package com.fastcampus.ch3.diCopy3;

import com.google.common.reflect.ClassPath;
import org.springframework.util.StringUtils;
import org.springframework.stereotype.Component;


import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

@Component class Car{}
@Component class SportsCar extends Car{}
    @Component class Truck extends Car{}
    @Component class Engine {}

    class Appcontext {
        Map map; // 객체 저장소

    Appcontext() {
        map = new HashMap();
        doComponentScan();
        

    }

    private void doComponentScan() {
        try {
            // 1. 패키지 내의 클래스 목록을 가져온다.
            // 2. 반복문으로 클래스를 하나씩 읽어와서 @Component가 붙어있는지 확인
            // 3. @Component가 붙어있으면 객체를 생성해서 map에 저장

            ClassLoader classLoader = Appcontext.class.getClassLoader();
            ClassPath classPath = ClassPath.from(classLoader);

            Set<ClassPath.ClassInfo> set = classPath.getTopLevelClasses("com.fastcampus.ch3.diCopy3");

            for(ClassPath.ClassInfo classInfo : set) {
                Class clazz = classInfo.load();
                Component component = (Component) clazz.getAnnotation(Component.class);
                if(component != null) {
                    String id = StringUtils.uncapitalize(classInfo.getSimpleName());
                    map.put(id, clazz.newInstance());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    Object getBean(String key) {
        return map.get(key);
    }

    Object getBean(Class clazz) {
        for(Object obj : map.values()) {
            if(clazz.isInstance(obj)) {
                return obj;
            }

        }
        return null;
    }

}

public class Main3 {
    public static void main(String[] args) throws Exception {

        Appcontext ac = new Appcontext();

        Car car = (Car) ac.getBean("car");
        Car car2 = (Car) ac.getBean(Car.class);
        Engine engine = (Engine) ac.getBean("engine");
        System.out.println("car = " + car);
        System.out.println("car2 = " + car2);
        System.out.println("engine = " + engine);
    }
}
