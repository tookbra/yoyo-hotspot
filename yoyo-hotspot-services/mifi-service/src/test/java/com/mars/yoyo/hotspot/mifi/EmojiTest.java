package com.mars.yoyo.hotspot.mifi;

import com.vdurmont.emoji.EmojiParser;

/**
 * @author tookbra
 * @date 2018/8/15
 * @description
 */
public class EmojiTest {

    public static void main(String[] args) {

        String str = "Here is a boy: \uD83D\uDC18biubiu";
        System.out.println(EmojiParser.parseToAliases(str));
    }
}
