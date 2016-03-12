package serialization;

import java.io.*;
import java.util.*;

/**
 * Created by Valk on 06.01.16.
 */
public class SerializableTest {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        C c = new C();
        c.fieldA = "A";
        c.fieldB = "B";
        //c.fieldC = "C"; //поле fieldC - private
        c.fld(); //установим его через метод, но не сеттер - тем самым демонстрируем, что сериализироваться будут и приватные поля без сеттера
        System.out.println(c); //   C{fieldA='A' fieldB='B' fieldC='C'}
        System.out.println("------------------------------------");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(c);
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        C cc = (C) ois.readObject();
        System.out.println(cc);
        //конструктор C при десериализации не вызывается
        //constructor A(...)
        //constructor B()    //для создания В вызывается конструктор без параметров (он обязателен), а н уже вызывает constructor A(...)
        //C{fieldA='A*' fieldB='null' fieldC='C'}
        //fieldB='null' - говорит, что сериализируются только поля, которые принадлежат непосредственно классу,
        // объет которого сериализируем, а fieldB - это поле из предка
        //
        // поэтому, чтобы оно сериализировалось:
        // - или класс B д.б сериализируемый (см. пример с классом ВВ ниже)
        // - или класс С заботится о сериализации необходимых дополнительных полей через расширение сериализации (см. serialization.test2.SerializableTest2)
        System.out.println("====================================================");
        BB bb = new BB();
        bb.fieldA = "AA";
        bb.fieldB = "BB";
        System.out.println(bb);
        System.out.println("------------------------------------");
        baos.reset(); //очищаем выходной массив
        oos = new ObjectOutputStream(baos); //oos надо создать новый, т.к. в нем остается какая-то информация,
        // которая вызывает java.io.StreamCorruptedException: invalid stream header. oos.reset(); - не помогает
        oos.writeObject(bb); //заполняем его
        bais = new ByteArrayInputStream(baos.toByteArray()); //инициируем новым значением входной массив
        ois = new ObjectInputStream(bais); //и оборачиваем его в поток
        BB bbb = (BB) ois.readObject();
        System.out.println(bbb);
        //------------------------------------
        //B{fieldA='AA', fieldB='BB'}
        //т.е. если предок сериализируемый, то его поля тоже записываются и восстанавливаются из потока, без
        //конструирования с помощью конструктора (конструктор сериализируемого предка не вызывается)

    }

}

class A {
    String fieldA;

    public A(String fieldA) {
        this.fieldA = fieldA;
        System.out.println("constructor A(...)");
    }
}

class B extends A {
    String fieldB;

    public B() {
        super("A*");
        System.out.println("constructor B()");
    }

    public B(String fieldA) {
        super(fieldA);
        System.out.println("constructor B(...)");
    }
}

class C extends B implements Serializable {
    private String fieldC;

    public C() {
        super("B*");
        System.out.println("constructor C()");
    }

    public C(String fieldA) {
        super(fieldA);
        System.out.println("constructor C(...)");
    }

    public void fld(){
        fieldC = "C";
    }

    @Override
    public String toString() {
        return "C{" +
                "fieldA='" + fieldA + '\'' +
                " fieldB='" + fieldB + '\'' +
                " fieldC='" + fieldC + '\'' +
                '}';
    }
}

class D extends C{
    D(int a){}
}

/*class E extends D{
//  E является сериализируемым, а потому требует у предка конструктор без параметров
}*/

class AA implements Serializable {
    String fieldA;

    public AA() {
        System.out.println("constructor AA()");
    }
}

class BB extends AA {
    String fieldB;

    @Override
    public String toString() {
        return "B{" +
                "fieldA='" + fieldA + '\'' +
                ", fieldB='" + fieldB + '\'' +
                '}';
    }
}