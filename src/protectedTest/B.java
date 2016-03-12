package protectedTest;

/**
 * Created by Valk on 17.12.15.
 */
class B extends ProtectedTest{  //наследник ProtectedTest
    public B(String string) {
        this.string = string;  //protected string доступно, т.к. доступ получаем из класса-наследника
    }

    public String getString(){
        return string;  //protected string доступно, т.к. доступ получаем из класса-наследника
    }

    public void accessToProtectedField(){
        ProtectedTest a = new ProtectedTest();
        a.string = string; //здесь мы получаем доступ через объект класса ProtectedTest. ¬ отличее от доступа в getString
        // это нельз€ считать доступом из класса-наследника. Ќо тем не менее все нормально, т.к. находимс€ в одном пакете
        // а вот в классе C Ёто уже не сработает, т.к. класс — в другом пакете
    }
}

class D {  //Ќ≈ наследник ProtectedTest
    public void accessToProtectedField(){
        ProtectedTest a = new ProtectedTest();
        a.string = "bbbbb"; //здесь мы получаем доступ через объект класса ProtectedTest, т.к. находимс€ в одном пакете
    }
}

