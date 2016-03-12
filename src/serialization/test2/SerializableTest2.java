package serialization.test2;

import java.io.*;

/**
 * Created by Valk on 06.01.16.
 */
public class SerializableTest2 {
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
        //сериализируются только поля, которые принадлежат непосредственно классу,
        // объет которого сериализируем, а fieldB - это поле из предка
        // поэтому, чтобы оно сериализировалось:
        // - или класс B д.б сериализируемый (см. пример с классом ВВ в serialization.SerializableTest)
        // - (это мы и сделали) или класс С заботится о сериализации необходимых дополнительных полей через расширение сериализации (см. данный пример)
        System.out.println("====================================================");
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

    private void writeObject(ObjectOutputStream out) throws IOException{
        out.defaultWriteObject();
        out.writeObject(this.fieldB);
    }
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
        in.defaultReadObject();
        this.fieldB = (String)in.readObject();
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
