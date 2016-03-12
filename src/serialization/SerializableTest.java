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
        //c.fieldC = "C"; //���� fieldC - private
        c.fld(); //��������� ��� ����� �����, �� �� ������ - ��� ����� �������������, ��� ����������������� ����� � ��������� ���� ��� �������
        System.out.println(c); //   C{fieldA='A' fieldB='B' fieldC='C'}
        System.out.println("------------------------------------");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(c);
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        C cc = (C) ois.readObject();
        System.out.println(cc);
        //����������� C ��� �������������� �� ����������
        //constructor A(...)
        //constructor B()    //��� �������� � ���������� ����������� ��� ���������� (�� ����������), � � ��� �������� constructor A(...)
        //C{fieldA='A*' fieldB='null' fieldC='C'}
        //fieldB='null' - �������, ��� ��������������� ������ ����, ������� ����������� ��������������� ������,
        // ����� �������� �������������, � fieldB - ��� ���� �� ������
        //
        // �������, ����� ��� �����������������:
        // - ��� ����� B �.� ��������������� (��. ������ � ������� �� ����)
        // - ��� ����� � ��������� � ������������ ����������� �������������� ����� ����� ���������� ������������ (��. serialization.test2.SerializableTest2)
        System.out.println("====================================================");
        BB bb = new BB();
        bb.fieldA = "AA";
        bb.fieldB = "BB";
        System.out.println(bb);
        System.out.println("------------------------------------");
        baos.reset(); //������� �������� ������
        oos = new ObjectOutputStream(baos); //oos ���� ������� �����, �.�. � ��� �������� �����-�� ����������,
        // ������� �������� java.io.StreamCorruptedException: invalid stream header. oos.reset(); - �� ��������
        oos.writeObject(bb); //��������� ���
        bais = new ByteArrayInputStream(baos.toByteArray()); //���������� ����� ��������� ������� ������
        ois = new ObjectInputStream(bais); //� ����������� ��� � �����
        BB bbb = (BB) ois.readObject();
        System.out.println(bbb);
        //------------------------------------
        //B{fieldA='AA', fieldB='BB'}
        //�.�. ���� ������ ���������������, �� ��� ���� ���� ������������ � ����������������� �� ������, ���
        //��������������� � ������� ������������ (����������� ���������������� ������ �� ����������)

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
//  E �������� ���������������, � ������ ������� � ������ ����������� ��� ����������
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