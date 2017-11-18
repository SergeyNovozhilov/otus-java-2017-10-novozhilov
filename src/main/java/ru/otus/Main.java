package ru.otus;

import com.google.common.reflect.ClassPath;
import ru.otus.Tester.Tester;
import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {

        Tester.run("ru.otus");

    }
}
