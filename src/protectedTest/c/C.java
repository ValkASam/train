package protectedTest.c;

import protectedTest.ProtectedTest;

/**
 * Created by Valk on 17.12.15.
 */
class C extends ProtectedTest {
    public C(String string) {
        this.string = string;   //protected string доступно, т.к. доступ получаем из класса-наследника
    }

    public String getString() {
        return string;  //protected string доступно, т.к. доступ получаем из класса-наследника
    }

    public void accessToProtectedField() {
        ProtectedTest a = new ProtectedTest();
        // a.string = "bbbbb";   //protected string НЕ доступно, т.к. пытаемся получить доступ  через экземпляр класса ProtectedTest к
        //фактически private члену класса ProtectedTest, для которого только одно исключение: есть доступ из того же пакета, где он объявлен
        //(см. класс В)
    }
}




