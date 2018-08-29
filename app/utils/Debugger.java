package utils;

import core.Constant;

/**
 * Created by atul on 20/06/17.
 */
public class Debugger {

    //Set debugging to false while checking-in code
    private static boolean debugging=true;

    public static void console(String arg){
        if(debugging){
            System.out.println(arg);
        }
    }

    public static void console(String ...args) {
        if(debugging){
            for (String arg : args)
                System.out.print(arg+ Constant.TAB.value());
            System.out.println();
        }
    }
}
