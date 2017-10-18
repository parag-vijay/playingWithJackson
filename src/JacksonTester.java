import org.codehaus.jackson.*;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class JacksonTester {
    public static void main(String[] args) {

        JacksonTester tester = new JacksonTester();
        tester.jsonGenratorTest();
    }

    public void jsonGenratorTest() {
        JacksonTester tester = new JacksonTester();

        try {
            JsonFactory factory = new JsonFactory();
            JsonGenerator jsonGenerator = factory.createJsonGenerator(new File("student.json"), JsonEncoding.UTF8);

            jsonGenerator.writeStartObject(); // {
            jsonGenerator.writeStringField("name", "Parag");
            jsonGenerator.writeNumberField("age", 24);
            jsonGenerator.writeBooleanField("verified", Boolean.FALSE);
            jsonGenerator.writeFieldName("marks");

            jsonGenerator.writeStartArray(); // [
            jsonGenerator.writeNumber(1);
            jsonGenerator.writeNumber(2);
            jsonGenerator.writeNumber(3);
            jsonGenerator.writeEndArray(); // ]

            jsonGenerator.writeEndObject(); //}
            jsonGenerator.close();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void genericDataBindingTest() {
//        JacksonTester tester = new JacksonTester();

        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, UserData> userDataMap = new HashMap<String, UserData>();
            UserData studentData = new UserData();
            int[] marks = {1, 2, 3};

            Student student = new Student();
            student.setName("Mamta");
            student.setAge(44);

//            Java Object

            studentData.setStudent(student);

//            Java String
            studentData.setName("Mamta Vijay");

//            Java Boolean
            studentData.setVerfied(Boolean.FALSE);

//            Array
            studentData.setMarks(marks);

            TypeReference ref = new TypeReference<Map<String, UserData>>() {
            };

            userDataMap.put("studentData1", studentData);
            mapper.writeValue(new File("student.json"), userDataMap);

            userDataMap = mapper.readValue(new File("student.json"), ref);

            System.out.println(userDataMap.get("studentData1").getStudent().toString());
            System.out.println(userDataMap.get("studentData1").getName());
            System.out.println(userDataMap.get("studentData1").getVerfied());
            System.out.println(Arrays.toString(userDataMap.get("studentData1").getMarks()));
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void simpleDataBindingTest() {
        JacksonTester tester = new JacksonTester();

        try {
            ObjectMapper mapper = new ObjectMapper();

            Map<String, Object> studentDataMap = new HashMap<String, Object>();
            int[] marks = {1, 2, 3};

            Student newStudent = new Student();

            newStudent.setAge(55);
            newStudent.setName("Rajendra");

//            Java Object
            studentDataMap.put("Student", newStudent);

//            JAVA String
            studentDataMap.put("name", "Rajendra Vijay");

//            Java Boolean
            studentDataMap.put("verified", Boolean.FALSE);

//            Array
            studentDataMap.put("marks", marks);

            mapper.writeValue(new File("student.json"), studentDataMap);

            studentDataMap = mapper.readValue(new File("student.json"), Map.class);

            System.out.println(studentDataMap.get("Student"));
            System.out.println(studentDataMap.get("name"));
            System.out.println(studentDataMap.get("verified"));
            System.out.println(studentDataMap.get("marks"));
        } catch (JsonMappingException jme) {
            jme.printStackTrace();
        } catch (JsonParseException jpe) {
            jpe.printStackTrace();
        } catch (JsonGenerationException jge) {
            jge.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void JSONandFileIOTest() {
        //        ObjectMapper mapper = new ObjectMapper();
//        String jsonString = "{\"name\":\"Parag\",\"age\":21}";
        JacksonTester tester = new JacksonTester();

//        Map json to student
        try {

            Student student = new Student();
            student.setAge(22);
            student.setName("Palash");
            tester.writeJSON(student);
            Student student1 = tester.readJSON();
            System.out.println(student1);
//            Student student = mapper.readValue(jsonString, Student.class);

//            System.out.println(student);

//            mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT,true);
//            mapper.configure(SerializationConfig.Feature.SORT_PROPERTIES_ALPHABETICALLY,false);
//            jsonString = mapper.writeValueAsString(student);

//            System.out.println(jsonString);
        } catch (JsonParseException jpe) {
            jpe.printStackTrace();
        } catch (JsonMappingException jme) {
            jme.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }


    public void writeJSON(Student student) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("student.json"), student);
    }

    public Student readJSON() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Student student = mapper.readValue(new File("student.json"), Student.class);
        return student;
    }
}

class Student {
    private String name;
    private int age;

    public Student() {
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student [ name: " + name + ", age : " + age + "]";
    }
}

class UserData {
    private Student student;
    private String name;
    private Boolean verfied;
    private int[] marks;

    public UserData() {
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getVerfied() {
        return verfied;
    }

    public void setVerfied(Boolean verfied) {
        this.verfied = verfied;
    }

    public int[] getMarks() {
        return marks;
    }

    public void setMarks(int[] marks) {
        this.marks = marks;
    }
}