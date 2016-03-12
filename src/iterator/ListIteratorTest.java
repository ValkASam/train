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
                //lastRet сразу стоит на -1
                //cursor сразу на 0 - на первой записи
             switch ((Integer)iterator.next()) {
                 //после первого next():
                 //lastRet = 0 - т.е. на первую запись
                 //cursor = 1 - на следующую запись
                 case 2: {
                     iterator.remove(); //удаляем в позиции lastRet и
                     //cursor = lastRet, т.е. сдвигается на шаг назад
                     //затем lastRet = -1;
                     //iterator.remove(); без движения по списку не можем два раза подряд удалять: lastRet после удаления = -1
                     //аналогично, если удалим вне итератора: lastRet станет = -1 и удаление в итераторе (даже первое) вызовет ошибку
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
