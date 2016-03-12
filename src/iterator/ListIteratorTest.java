package iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Valk on 04.01.16.
 */
public class ListIteratorTest {
    public static void main(String[] args) {
        {
            List<Integer> list = new ArrayList<Integer>() {{
                add(1);
                add(2);
                add(3);
            }};
            for (Iterator<Integer> iterator = list.iterator(); iterator.hasNext(); ) {
                if ((Integer)iterator.next()==2) {
                    iterator.remove();
                }
            }
            System.out.println(list);
        }
        //
        {
            List<Integer> list = new ArrayList<Integer>() {{
                add(1);
                add(2);
                add(3);
            }};
            for (ListIterator<Integer> iterator = list.listIterator(); iterator.hasNext(); ) {
                //lastRet ����� ����� �� -1
                //cursor ����� �� 0 - �� ������ ������
             switch ((Integer)iterator.next()) {
                 //����� ������� next():
                 //lastRet = 0 - �.�. �� ������ ������
                 //cursor = 1 - �� ��������� ������
                 case 2: {
                     iterator.remove(); //������� � ������� lastRet �
                     //cursor = lastRet, �.�. ���������� �� ��� �����
                     //����� lastRet = -1;
                     //iterator.remove(); ��� �������� �� ������ �� ����� ��� ���� ������ �������: lastRet ����� �������� = -1
                     //����������, ���� ������ ��� ���������: lastRet ������ = -1 � �������� � ��������� (���� ������) ������� ������
                     break;
                 }
                 case 3: {
                     iterator.add(4);
                     break;
                 }
                 case 1: {
                     iterator.set(0);
                     break;
                 }
             }
             }
            System.out.println(list);
        }
    }
}
