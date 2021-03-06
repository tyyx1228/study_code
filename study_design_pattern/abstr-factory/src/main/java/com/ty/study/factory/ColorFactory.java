package com.ty.study.factory;

import com.ty.study.domain.Color;
import com.ty.study.domain.Shape;

/**
 * 实例化形状的工厂
 *
 * @author relax tongyu
 * @create 2018-01-28 22:23
 **/
public class ColorFactory extends AbstractFactory {

    @Override
    public Color getColor(Class<? extends Color> clazz) {
        /*Color obj = null;
        try {
            obj =  (Color) Class.forName(clazz.getName()).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            log.error("无法实例化对象", e);
        }
        return obj;*/

        return getBean(clazz);
    }

    @Override
    public Shape getShape(Class<? extends Shape> clazz) throws NoSuchMethodException {
        throw new NoSuchMethodException("工厂未对此方法做具体实现");
    }
}
