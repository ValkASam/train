package json.hand_serial;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Valk on 19.01.16.
 */
public class JsonTest_hand {
    public static void main(String[] args) throws IOException {
        {
            A a = new A("");
            System.out.println(a);

            ObjectMapper objectMapper = new ObjectMapper();

            Writer writer = new StringWriter();
            objectMapper.writeValue(writer, a);

            System.out.println(writer.toString());

            Reader reader = new StringReader(writer.toString());
            A retA = objectMapper.readValue(reader, A.class);
            System.out.println(retA);
        }
        System.out.println("=============================");
        {
            A a = new A("");
            System.out.println(a);

            ObjectMapper objectMapper = new ObjectMapper();

            SimpleModule simpleModule = new SimpleModule();
            simpleModule.addSerializer(A.class, new Ser1());
            simpleModule.addSerializer(Date.class, new Ser2());
            simpleModule.addDeserializer(A.class, new Deser1());
            objectMapper.registerModule(simpleModule);

            Writer writer = new StringWriter();
            objectMapper.writeValue(writer, a);

            System.out.println(writer.toString());

            Reader reader = new StringReader(writer.toString());
            A retA = objectMapper.readValue(reader, A.class);
            System.out.println(retA);
        }
        System.out.println("=============================");
        {
            A a = new A("");
            System.out.println(a);

            ObjectMapper objectMapper = new MyObjectMapper();

            Writer writer = new StringWriter();
            objectMapper.writeValue(writer, a);

            System.out.println(writer.toString());

            Reader reader = new StringReader(writer.toString());
            A retA = objectMapper.readValue(reader, A.class);
            System.out.println(retA);
        }
    }
}

@JsonAutoDetect
class A {
    private String str;
    private Integer i;
    private Date d;
    private List<Integer> a;

    public A() {
    }

    public A(String s) {
        str = "string";
        i = 100;
        d = new Date(1000);
        a = new ArrayList<Integer>(){{
            add(10);
            add(20);
        }};
    }

    @Override
    public String toString() {
        return "A{" +
                "str='" + str + '\'' +
                ", i=" + i +
                ", d=" + d +
                ", a=" + a +
                '}';
    }

    public String getStr() {
        return str;
    }

    public Integer getI() {
        return i;
    }

    public Date getD() {
        return d;
    }

    public List<Integer> getA() {
        return a;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public void setI(Integer i) {
        this.i = i;
    }

    public void setD(Date d) {
        this.d = d;
    }

    public void setA(List<Integer> a) {
        this.a = a;
    }
}

class Ser1 extends JsonSerializer<A>{

    @Override
    public void serialize(A a, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("str", a.getStr());
        jsonGenerator.writeObjectField("d", a.getD());
        jsonGenerator.writeObjectField("a",a.getA());
        jsonGenerator.writeNumberField("i", a.getI());
        jsonGenerator.writeEndObject();
    }
}

class Ser2 extends JsonSerializer<Date>{
    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeObject(1000 * 1000);
        //jsonGenerator.writeObject(new Date()) - вызовет переполнение стека - сеиализатор чтобы записать дату вызовет себя же и т.д.
    }
}

class Deser1 extends JsonDeserializer<A> {
    @Override
    public A deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);
        A a = new A();
        a.setStr(node.get("str").asText());
        a.setI(node.get("i").asInt());
        a.setD(new Date(node.get("d").asLong()));
        List<Integer> lst = new ArrayList<Integer>(){{
            for (Iterator<JsonNode> iterator = node.get("a").iterator(); iterator.hasNext();) {
                add(iterator.next().asInt());
            }
        }};
        a.setA(lst);
        return a;
    }
}

class MyObjectMapper extends ObjectMapper{
    {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(A.class, new Ser1());
        simpleModule.addSerializer(Date.class, new Ser2());
        registerModule(simpleModule);
    }
}

