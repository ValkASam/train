package generic;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Valk on 28.12.15.
 */
public class GenericTest {
    public static void main(String[] args) {
        Pair<Integer, String> pair = new Pair<Integer, String>(6, " Apr");
        System.out.println(pair.getFirst() + pair.getSecond());
        //
        System.out.println(pair.method(5).getClass().getName());
        System.out.println(pair.method(6L).getClass().getName());
        //
        //узнать тип джерерика
        //можем узнать только для родительского класса ...
        try {
            System.out.println(pair.getClass().getGenericSuperclass().getTypeName());
            //... поэтому тут будет ошибка, т.к родитель - это Object, а он не параметризирован
            System.out.println(((ParameterizedType) pair.getClass().getGenericSuperclass()).getActualTypeArguments());
        } catch (Exception e) {}
        System.out.println("-----------------------");
        //
        //узнаем через анонимный класс-наследник
        Pair<Integer, String> pair1 = new Pair<Integer, String>(6, " Apr"){{}};
        System.out.println(pair1.getClass().getGenericSuperclass().getTypeName());
        System.out.println(Arrays.toString(((ParameterizedType) pair1.getClass().getGenericSuperclass()).getActualTypeArguments()));
        //
        System.out.println("========================");
        //
        pair.method2(new ArrayList<Integer>() {{
            add(1);
        }});
        pair.method3(new ArrayList<Integer>() {{
            add(2);
        }});
        pair.method4(new ArrayList<Integer>() {{
            add(3);
        }});
        List<? extends Object> list = new ArrayList<>();
        //list.add(new Animal());
        Animal a = (Animal) list.get(0);
        System.out.println("************************");
        //
        {
            List<? extends Animal> animals = new ArrayList<Cat>() {{
                //<? extends Animal> : ссылка animals может указывать на объект список, который содержит Animal или его потомков
                //фактически animals указывает на список, содержащий Cat
                add(new Cat("1"));
                add(new Cat("2"));
            }};
            //так можно
            for (Animal animal : animals) { //элементы списка - объекты Cat - присваиваем с расширением типа (более широкому типу) - это законно
                System.out.println(animals);
            }
            for (Object animal : animals) { //элементы списка - объекты Cat - присваиваем с расширением типа (более широкому типу) - это законно
                System.out.println(animal);
            }
            //так нельзя
            /*for (Cat animal : animals) { //animals может содержать любых наследников Animal, а значит может содержать Dog и в таком случае мы присвоили бы  Cat animal = Dog
                System.out.println(animal);
            }*/
        }
        //=======================
        {
            List<? super Cat> animals = new ArrayList<Animal>() {{
                //<? super Animal> : ссылка animals может указывать на объект список, который содержит Cat или его родителей
                //фактически animals указывает на список, содержащий Animal
                add(new Animal());
                add(new Animal());
            }};
            //так нельзя
            /*for (Cat animal : animals) { //animals может содержать любых предков Cat, а значит может получиться, что предку более старшему присвоим предка менее старшего
                System.out.println(animals);
            }*/
            for (Object animal : animals) { //так можно так младше Object никого быть не может, а значит при любом раскладе не будет, что предку более старшему присвоим предка менее старшего
                System.out.println(animal);
            }
        }

    }
}

class Pair<T1, T2> {
    T1 object1;
    T2 object2;

    Pair(T1 one, T2 two) {
        object1 = one;
        object2 = two;
    }

    public T1 getFirst() {
        return object1;
    }

    public T2 getSecond() {
        return object2;
    }

    public <T> T method(T param){
        return param;
    }

    public void method3(List<? extends Number> param){
        System.out.println("method3: " + param.get(0).getClass().getTypeName());
        //param.add(new Integer(3)); //так нельзя, т.к. в список, который содержит какого-либо потомка Number, по факту
        // может содержать потомка, например Float, а мы положим в список Integer
    }

    //а вот так можно, т.к. мы знаем реальный тип
    public <T3 extends Number> void method2(List<T3> param){
        System.out.println("method2: "+param.get(0).getClass().getTypeName());
        param.add((T3)new Integer(3));
    }

    //public void method4(List<?> param){
    public void method4(List param){
        param.add(new Animal());
        Animal a = (Animal) param.get(param.size()-1);
        System.out.println("method4: " + a.getClass().getTypeName()+"  "+a);
    }
}

class Animal{
    String name;

    public String getName() {
        return name;
    }
}

class Pet extends Animal{
}

class Cat extends Pet{
    public Cat(String name) {
        this.name = name;
    }
}

class Dog extends Pet{
    public Dog(String name) {
        this.name = name;
    }
}