package com.ty.study.domain.impl;

import com.ty.study.domain.Shape;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 正方形
 *
 * @author relax tongyu
 * @create 2018-01-28 21:46
 **/
public class Square implements Shape {
    public static final Logger log = LogManager.getLogger(Square.class);

    @Override
    public void draw() {
        log.info("Inside Square::draw() method.");
    }
}
