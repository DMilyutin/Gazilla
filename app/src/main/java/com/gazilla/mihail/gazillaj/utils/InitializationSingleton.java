package com.gazilla.mihail.gazillaj.utils;

public class InitializationSingleton {
    private static volatile InitializationSingleton instance;



    private InitializationSingleton() {
    }

    public static InitializationSingleton getInstance(){
        if (instance==null)
            synchronized (InitializationSingleton.class){
            if (instance==null)
                instance = new InitializationSingleton();
            }
            return instance;
    }
}
