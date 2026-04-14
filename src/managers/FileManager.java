// Класс для загрузки и сохранения коллекции в XML-файл. Чувствителен к структуре XML, которая должна строго соответствовать тому, что он ожидает.

package managers;

import data.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;

import java.io.*;
import java.util.LinkedList;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;

/**
 * Менеджер чтения и записи коллекции учебных групп в XML-файл.
 */
public class FileManager {
    private final String filePath;

    /**
     * Создает файловый менеджер.
     *
     * @param filePath путь к XML-файлу коллекции
     */
    public FileManager(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Загружает коллекцию из файла.
     *
     * @return коллекция учебных групп
     * @throws Exception ошибка чтения или разбора XML
     */
    public LinkedList<StudyGroup> load() throws Exception {
        String xml = readAllFromFile();
        if (xml.isEmpty()) {
            return new LinkedList<>();
        }

        return parseXmlToCollection(xml);
    }

    /**
     * Сохраняет коллекцию в файл.
     *
     * @param collection коллекция для сохранения
     * @throws IOException ошибка записи файла
     */
    public void save(LinkedList<StudyGroup> collection) throws IOException {
        String xml = buildXmlFromCollection(collection);
        writeAllToFile(xml);
    }

    /**
     * Считывает все содержимое файла в строку.
     *
     * @return XML-строка или пустая строка, если файл отсутствует
     * @throws IOException ошибка чтения
     */
    private String readAllFromFile() throws IOException {
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

    /**
     * Записывает строку в целевой файл.
     *
     * @param content текст для записи
     * @throws IOException ошибка записи
     */
    private void writeAllToFile(String content) throws IOException {
        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath))) {
            bos.write(bytes);
        }
    }

    /**
     * Разбирает XML-строку в коллекцию объектов StudyGroup.
     *
     * @param xml исходный XML
     * @return коллекция учебных групп
     * @throws Exception ошибка парсинга
     */
    private LinkedList<StudyGroup> parseXmlToCollection(String xml) throws Exception {
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
                if (node.getNodeType() != Node.ELEMENT_NODE) {
                    continue;
                }

                Element e = (Element) node;
                StudyGroup group = parseStudyGroupElement(e);
                result.add(group);
            }
        }

        return result;
    }

    /**
     * Строит XML-представление коллекции.
     *
     * @param collection исходная коллекция
     * @return XML-строка
     */
    private String buildXmlFromCollection(LinkedList<StudyGroup> collection) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("<studyGroups>\n");

        for (StudyGroup g : collection) {
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

    /**
     * Экранирует специальные символы XML.
     *
     * @param s исходная строка
     * @return экранированная строка
     */
    private String escape(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }

    /**
     * Парсит XML-элемент studyGroup в объект.
     *
     * @param e XML-элемент группы
     * @return объект StudyGroup
     */
    private StudyGroup parseStudyGroupElement(Element e) {
        long id = Long.parseLong(getText(e, "id"));
        String name = getText(e, "name");

        Element coordsEl = (Element) e.getElementsByTagName("coordinates").item(0);
        long x = Long.parseLong(getText(coordsEl, "x"));
        long y = Long.parseLong(getText(coordsEl, "y"));
        Coordinates coordinates = new Coordinates(x, y);

        String creationStr = getText(e, "creationDate");
        ZonedDateTime creationDate = ZonedDateTime.parse(creationStr);

        long studentsCount = Long.parseLong(getText(e, "studentsCount"));
        int shouldBeExpelled = Integer.parseInt(getText(e, "shouldBeExpelled"));
        FormOfEducation formOfEducation = FormOfEducation.valueOf(getText(e, "formOfEducation"));
        Semester semesterEnum = Semester.valueOf(getText(e, "semesterEnum"));

        Element adminEl = (Element) e.getElementsByTagName("groupAdmin").item(0);
        String adminName = getText(adminEl, "name");
        Long adminWeight = Long.parseLong(getText(adminEl, "weight"));
        String passportID = getText(adminEl, "passportID");
        Color eyeColor = Color.valueOf(getText(adminEl, "eyeColor"));
        Color hairColor = Color.valueOf(getText(adminEl, "hairColor"));

        Person groupAdmin = new Person(adminName, adminWeight, passportID, eyeColor, hairColor);

        return new StudyGroup(id, name, coordinates, creationDate,
                studentsCount, shouldBeExpelled, formOfEducation, semesterEnum, groupAdmin);
    }

    /**
     * Возвращает текстовое содержимое первого дочернего тега.
     *
     * @param parent родительский элемент
     * @param tagName имя дочернего тега
     * @return текст тега или пустая строка
     */
    private String getText(Element parent, String tagName) {
        NodeList list = parent.getElementsByTagName(tagName);
        if (list == null || list.getLength() == 0) return "";
        return list.item(0).getTextContent().trim();
    }

}