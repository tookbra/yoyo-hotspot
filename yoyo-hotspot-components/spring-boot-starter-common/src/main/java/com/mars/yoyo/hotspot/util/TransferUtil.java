package com.mars.yoyo.hotspot.util;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @author tookbra
 * @date 2018/5/15
 * @description
 */
public class TransferUtil {

    public TransferUtil() {
    }

    public static <F, T> T transfer(F from, T to) {
        if(from == null) {
            return null;
        } else {
            BeanUtils.copyProperties(from, to);
            return to;
        }
    }
    public static <F, T> T transferBean(F from,Class<T> to){
        if(from == null) {
            return null;
        } else {
            try {
                T t1 = to.newInstance();
                BeanUtils.copyProperties(from, t1);
                return t1;
            } catch (Exception e) {
                return null;
            }
        }
    }

    public static <F, T> List<T> transferList(List<F> fromList, Class<T> toClass) {
        return Lists.transform(fromList, new TransferFunction(toClass));
    }

    public static class TransferFunction<F, T> implements Function<F, T> {
        private Class<T> clazz;

        public TransferFunction(Class<T> clazz) {
            this.clazz = clazz;
        }

        @Override
        public T apply(F input) {
            try {
                T t = this.clazz.newInstance();
                BeanUtils.copyProperties(input, t);
                return t;
            } catch (IllegalAccessException | InstantiationException var4) {
                throw new IllegalArgumentException("Class can not instantiation ", var4);
            }
        }
    }
}
