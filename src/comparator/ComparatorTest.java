package comparator;

import java.io.*;
import java.util.Comparator;
import java.util.TreeSet;

/**
 * Created by Valk on 23.12.15.
 */
public class ComparatorTest {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        {
            TreeSet<TreeElement> treeSet = new TreeSet<TreeElement>() {
                {
                    add(new TreeElement(2));
                    add(new TreeElement(3));
                    add(new TreeElement(1));
                }
            };
            System.out.println(treeSet);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(treeSet);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            TreeSet<TreeElement> treeSet1 = (TreeSet<TreeElement>) ois.readObject();
            System.out.println(treeSet1);
        }
        System.out.println();
        {
            TreeSet<TreeElement> treeSet = new TreeSet<TreeElement>(new ForTreeElementComparator()) {
                {
                    add(new TreeElement(2));
                    add(new TreeElement(3));
                    add(new TreeElement(1));
                }
            };
            System.out.println(treeSet);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(treeSet);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            TreeSet<TreeElement> treeSet1 = (TreeSet<TreeElement>) ois.readObject();
            System.out.println(treeSet1);
        }
    }
}

class TreeElement implements Comparable<TreeElement>, Serializable {
    private Integer i;

    public TreeElement(Integer i) {
        this.i = i;
    }

    public Integer getI() {
        return i;
    }

    @Override
    public String toString() {
        return "TreeElement{" +
                "i=" + i +
                '}';
    }

    @Override
    public int compareTo(TreeElement o) {
        return i.compareTo(o.getI());
    }
}

class ForTreeElementComparator implements Comparator<TreeElement>, Serializable{
    @Override
    public int compare(TreeElement o1, TreeElement o2) {
        return o2.compareTo(o1);
    }
}
