// Класс для загрузки и сохранения коллекции в XML-файл. Чувствителен к структуре XML, которая должна строго соответствовать тому, что он ожидает.

package managers;

import data.*;
import managers.CollectionManager;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;

import java.io.*;
import java.util.LinkedList;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;




public class FileManager {
    private final String filePath;

    public FileManager(String filePath) { // конструктор, который принимает путь к файлу и сохраняет его в поле класса
        this.filePath = filePath;
    }

    public LinkedList<StudyGroup> load() throws Exception { // Метод для загрузки коллекции из файла. Если файл не существует или пустой, возвращает пустую коллекцию.
        String xml = readAllFromFile();
        if (xml.isEmpty()) {
            return new LinkedList<>();
        }

        return parseXmlToCollection(xml);
    }

    public void save(LinkedList<StudyGroup> collection) throws IOException { // Метод для сохранения коллекции в файл. Сначала строит XML-строку из коллекции, а затем записывает её в файл.
        String xml = buildXmlFromCollection(collection);
        writeAllToFile(xml);
    }

    private String readAllFromFile() throws IOException { // Метод для чтения всего содержимого файла в строку. Если файл не существует, возвращает пустую строку.
        File file = new File(filePath);
        if (!file.exists()) {
            return "";
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        }
    }

    private void writeAllToFile(String content) throws IOException { // Метод для записи строки в файл. Если файл не существует, он будет создан. Содержимое файла будет перезаписано.
        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        try (BufferedOutputStream bos = new BufferedOutputStream(
                new FileOutputStream(filePath))) {
            bos.write(bytes);
        }
    }

    private LinkedList<StudyGroup> parseXmlToCollection(String xml) throws Exception { // Метод для парсинга XML-строки в коллекцию. Использует стандартные библиотеки для работы с XML.
        LinkedList<StudyGroup> result = new LinkedList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();


        try (StringReader sr = new StringReader(xml)) {
            org.xml.sax.InputSource is = new org.xml.sax.InputSource(sr);
            Document doc = builder.parse(is);
            doc.getDocumentElement().normalize();

            NodeList groups = doc.getElementsByTagName("studyGroup");
            for (int i = 0; i < groups.getLength(); i++) {
                Node node = groups.item(i);
                if (node.getNodeType() != Node.ELEMENT_NODE) continue;

                Element e = (Element) node;
                StudyGroup group = parseStudyGroupElement(e);
                result.add(group);
            }
        }

        return result;
    }

    private String buildXmlFromCollection(LinkedList<StudyGroup> collection) { // строим XML вручную, без сторонних библиотек
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("<studyGroups>\n");

        for (StudyGroup g : collection) { // для каждого элемента коллекции строим XML-узел
            sb.append("  <studyGroup>\n");
            sb.append("    <id>").append(g.getId()).append("</id>\n");
            sb.append("    <name>").append(escape(g.getName())).append("</name>\n");

            sb.append("    <coordinates>\n");
            sb.append("      <x>").append(g.getCoordinates().getX()).append("</x>\n");
            sb.append("      <y>").append(g.getCoordinates().getY()).append("</y>\n");
            sb.append("    </coordinates>\n");

            sb.append("    <creationDate>").append(g.getCreationDate()).append("</creationDate>\n");
            sb.append("    <studentsCount>").append(g.getStudentsCount()).append("</studentsCount>\n");
            sb.append("    <shouldBeExpelled>").append(g.getShouldBeExpelled()).append("</shouldBeExpelled>\n");
            sb.append("    <formOfEducation>").append(g.getFormOfEducation()).append("</formOfEducation>\n");
            sb.append("    <semesterEnum>").append(g.getSemesterEnum()).append("</semesterEnum>\n");

            Person a = g.getGroupAdmin();
            if (a != null) {
                sb.append("    <groupAdmin>\n");
                sb.append("      <name>").append(escape(a.getName())).append("</name>\n");
                sb.append("      <weight>").append(a.getWeight()).append("</weight>\n");
                sb.append("      <passportID>").append(escape(a.getPassportID())).append("</passportID>\n");
                sb.append("      <eyeColor>").append(a.getEyeColor()).append("</eyeColor>\n");
                sb.append("      <hairColor>").append(a.getHairColor()).append("</hairColor>\n");
                sb.append("    </groupAdmin>\n");
            }

            sb.append("  </studyGroup>\n");
        }

        sb.append("</studyGroups>\n");
        return sb.toString();
    }

    // простейший экранировщик спецсимволов XML
    private String escape(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }

    private StudyGroup parseStudyGroupElement(Element e) {
        long id = Long.parseLong(getText(e, "id"));
        String name = getText(e, "name");

        // coordinates
        Element coordsEl = (Element) e.getElementsByTagName("coordinates").item(0);
        long x = Long.parseLong(getText(coordsEl, "x"));
        long y = Long.parseLong(getText(coordsEl, "y"));
        Coordinates coordinates = new Coordinates(x, y);

        // creationDate
        String creationStr = getText(e, "creationDate");
        ZonedDateTime creationDate = ZonedDateTime.parse(creationStr);

        long studentsCount = Long.parseLong(getText(e, "studentsCount"));
        int shouldBeExpelled = Integer.parseInt(getText(e, "shouldBeExpelled"));
        FormOfEducation formOfEducation =
                FormOfEducation.valueOf(getText(e, "formOfEducation"));
        Semester semesterEnum =
                Semester.valueOf(getText(e, "semesterEnum"));

        // groupAdmin
        Element adminEl = (Element) e.getElementsByTagName("groupAdmin").item(0);
        String adminName = getText(adminEl, "name");
        Long adminWeight = Long.parseLong(getText(adminEl, "weight"));
        String passportID = getText(adminEl, "passportID");
        Color eyeColor = Color.valueOf(getText(adminEl, "eyeColor"));
        Color hairColor = Color.valueOf(getText(adminEl, "hairColor"));

        Person groupAdmin = new Person(adminName, adminWeight, passportID, eyeColor, hairColor);

        // Конструктор StudyGroup подстрой под свою сигнатуру
        return new StudyGroup(id, name, coordinates, creationDate,
                studentsCount, shouldBeExpelled, formOfEducation, semesterEnum, groupAdmin);
    }

    private String getText(Element parent, String tagName) {
        NodeList list = parent.getElementsByTagName(tagName);
        if (list == null || list.getLength() == 0) return "";
        return list.item(0).getTextContent().trim();
    }

}