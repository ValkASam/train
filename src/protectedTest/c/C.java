package protectedTest.c;

import protectedTest.ProtectedTest;

/**
 * Created by Valk on 17.12.15.
 */
class C extends ProtectedTest {
    public C(String string) {
        this.string = string;   //protected string ��������, �.�. ������ �������� �� ������-����������
    }

    public String getString() {
        return string;  //protected string ��������, �.�. ������ �������� �� ������-����������
    }

    public void accessToProtectedField() {
        ProtectedTest a = new ProtectedTest();
        // a.string = "bbbbb";   //protected string �� ��������, �.�. �������� �������� ������  ����� ��������� ������ ProtectedTest �
        //���������� private ����� ������ ProtectedTest, ��� �������� ������ ���� ����������: ���� ������ �� ���� �� ������, ��� �� ��������
        //(��. ����� �)
    }
}




