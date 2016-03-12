package protectedTest;

/**
 * Created by Valk on 17.12.15.
 */
class B extends ProtectedTest{  //��������� ProtectedTest
    public B(String string) {
        this.string = string;  //protected string ��������, �.�. ������ �������� �� ������-����������
    }

    public String getString(){
        return string;  //protected string ��������, �.�. ������ �������� �� ������-����������
    }

    public void accessToProtectedField(){
        ProtectedTest a = new ProtectedTest();
        a.string = string; //����� �� �������� ������ ����� ������ ������ ProtectedTest. � ������� �� ������� � getString
        // ��� ������ ������� �������� �� ������-����������. �� ��� �� ����� ��� ���������, �.�. ��������� � ����� ������
        // � ��� � ������ C ��� ��� �� ���������, �.�. ����� � � ������ ������
    }
}

class D {  //�� ��������� ProtectedTest
    public void accessToProtectedField(){
        ProtectedTest a = new ProtectedTest();
        a.string = "bbbbb"; //����� �� �������� ������ ����� ������ ������ ProtectedTest, �.�. ��������� � ����� ������
    }
}

