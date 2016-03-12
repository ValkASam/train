package interfaceClass;

/**
 * Created by Valk on 21.11.15.
 */
public interface C {
   void print();
}

interface CC {
    void print();
}

interface CCC extends C, CC { //допустимо множественное наследование интерейсов
    void print();
}