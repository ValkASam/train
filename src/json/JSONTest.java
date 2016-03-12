package json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Valk on 22.12.15.
 */
public class JSONTest {
    public static void main(String[] args) throws IOException {
        ForSerializeClass forSerializeClass = new ForSerializeClass(7, 8, "asdf");
        ObjectMapper objectMapper = new ObjectMapper();
        //
        Writer writer = new StringWriter();
        objectMapper.writeValue(writer, forSerializeClass);
        System.out.println(writer.toString());
        //
        Reader reader = new StringReader(writer.toString());
        ForSerializeClass forSerializeClassRecovered = objectMapper.readValue(reader, ForSerializeClass.class);
        System.out.println(forSerializeClassRecovered);
    }

}

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
class ForSerializeClass {
    private Integer intField1 = 1;
    public Integer intField2 = 2;

    @JsonProperty("stringFld") //@JsonProperty перед полем (гетером) влияет на имя поля в JSON-объекте
    public String strField = "qwerty";

    //т.к. нет конструктора без параметров, можем обойти это с помощью @JsonProperty. В качестве параметра похоже, можно указать любое значение. А также на имя поля в JSON-объекте не влияет
    public ForSerializeClass(@JsonProperty("1") Integer intField1,
                             @JsonProperty("2") Integer intField2,
                             @JsonProperty("3") String strField) {
        this.intField1 = intField1;
        this.intField2 = intField2;
        this.strField = strField;
    }

    @JsonDeserialize(as = ArrayList.class) //указываем конкретный тип интерфейса List
    public List<Integer> lstField1 = new ArrayList<Integer>() {{
        add(1);
        add(2);
    }};

    @JsonDeserialize(as = ArrayList.class)
    public List<Integer> lstField2 = new LinkedList<Integer>() {{
        add(10);
        add(20);
    }};

    @JsonDeserialize(as = ArrayList.class, contentAs = IMyImpl1.class)
    //указываем конкретный тип интерфейса List и конкретный тип содержимого списка
    public List<IMy> lstField3 = new LinkedList<IMy>() {{
        add(new IMyImpl1());
        add(new IMyImpl1());
    }};

    @JsonDeserialize(as = LinkedList.class)
    //указываем конкретный тип интерфейса List. Кстати, по умолчанию будет LinkedList. В методе toString для контроля выводим тип
    public List<IMyImpl> lstField4 = new LinkedList<IMyImpl>() {{
        add(new IMyImpl1()); //разнородные типы. Для того чтобы правильно идентифицировать при десериализации, добавляем @JsonTypeInfo и JsonSubTypes (см. ниже)
        add(new IMyImpl2());
    }};

    @Override
    public String toString() {
        return "ForSerializeClass{" +
                "intField1=" + intField1 +
                ", intField2=" + intField2 +
                ", strField='" + strField + '\'' +
                ", lstField1=" + lstField1 +
                ", lstField2=" + lstField2 +
                ", lstField3=" + lstField3 +
                ", lstField4=" + lstField4 +
                ", (" + lstField4.getClass().getName() + ")" +
                '}';
    }
}

//эти аннотации для возможности различать типы при десериализации. Нужно именно здесь, перед интерфейсов - в самом верху иерархии
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "classType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = IMyImpl1.class, name = "Impl1"),
        @JsonSubTypes.Type(value = IMyImpl2.class, name = "Impl2")
})
interface IMy {
}

class IMyImpl implements IMy {
    public int fld1 = 100;
}

class IMyImpl1 extends IMyImpl {
    public int fld1 = 100;
}

class IMyImpl2 extends IMyImpl {
    public int fld1 = 200;
}
