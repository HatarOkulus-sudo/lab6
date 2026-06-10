package Server.managers;

import Common.data.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.LinkedList;

public class FileManager {
    private final String filePath;

    public FileManager(String filePath) {
        this.filePath = filePath;
    }

    public LinkedList<StudyGroup> load() throws Exception {
        String xml = readAllFromFile();
        if (xml.isBlank()) return new LinkedList<>();
        return parseXmlToCollection(xml);
    }

    public void save(LinkedList<StudyGroup> collection) throws IOException {
        if (collection == null) collection = new LinkedList<>();
        String xml = buildXmlFromCollection(collection);
        writeAllToFile(xml);
    }

    private String readAllFromFile() throws IOException {
        File file = new File(filePath);
        if (!file.exists()) return "";
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)
        )) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) sb.append(line).append('\n');
            return sb.toString();
        }
    }

    private void writeAllToFile(String content) throws IOException {
        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath))) {
            bos.write(bytes);
            bos.flush();
        }
    }

    private LinkedList<StudyGroup> parseXmlToCollection(String xml) throws Exception {
        LinkedList<StudyGroup> result = new LinkedList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(false);
        DocumentBuilder builder = factory.newDocumentBuilder();

        try (StringReader sr = new StringReader(xml)) {
            InputSource is = new InputSource(sr);
            Document doc = builder.parse(is);
            doc.getDocumentElement().normalize();

            NodeList groups = doc.getElementsByTagName("studyGroup");
            for (int i = 0; i < groups.getLength(); i++) {
                Node node = groups.item(i);
                if (node.getNodeType() != Node.ELEMENT_NODE) continue;

                Element e = (Element) node;
                result.add(parseStudyGroupElement(e));
            }
        }

        return result;
    }

    private String buildXmlFromCollection(LinkedList<StudyGroup> collection) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("<studyGroups>\n");

        for (StudyGroup g : collection) {
            if (g == null) continue;

            sb.append("  <studyGroup>\n");
            sb.append("    <id>").append(g.getId()).append("</id>\n");
            sb.append("    <name>").append(escape(requireNonNull(g.getName(), "name"))).append("</name>\n");

            sb.append("    <coordinates>\n");
            Coordinates c = requireNonNull(g.getCoordinates(), "coordinates");
            sb.append("      <x>").append(c.getX()).append("</x>\n");
            sb.append("      <y>").append(c.getY()).append("</y>\n");
            sb.append("    </coordinates>\n");

            sb.append("    <creationDate>")
                    .append(requireNonNull(g.getCreationDate(), "creationDate"))
                    .append("</creationDate>\n");
            sb.append("    <studentsCount>").append(g.getStudentsCount()).append("</studentsCount>\n");
            sb.append("    <shouldBeExpelled>").append(g.getShouldBeExpelled()).append("</shouldBeExpelled>\n");
            sb.append("    <formOfEducation>")
                    .append(requireNonNull(g.getFormOfEducation(), "formOfEducation"))
                    .append("</formOfEducation>\n");
            sb.append("    <semesterEnum>")
                    .append(requireNonNull(g.getSemesterEnum(), "semesterEnum"))
                    .append("</semesterEnum>\n");

            Person a = requireNonNull(g.getGroupAdmin(), "groupAdmin");
            sb.append("    <groupAdmin>\n");
            sb.append("      <name>").append(escape(requireNonNull(a.getName(), "groupAdmin.name"))).append("</name>\n");
            sb.append("      <weight>").append(requireNonNull(a.getWeight(), "groupAdmin.weight")).append("</weight>\n");
            sb.append("      <passportID>").append(escape(requireNonNull(a.getPassportID(), "groupAdmin.passportID"))).append("</passportID>\n");
            sb.append("      <eyeColor>").append(requireNonNull(a.getEyeColor(), "groupAdmin.eyeColor")).append("</eyeColor>\n");

            Color hair = a.getHairColor();
            sb.append("      <hairColor>").append(hair == null ? "" : hair.name()).append("</hairColor>\n");

            sb.append("    </groupAdmin>\n");
            sb.append("  </studyGroup>\n");
        }

        sb.append("</studyGroups>\n");
        return sb.toString();
    }

    private StudyGroup parseStudyGroupElement(Element e) {
        long id = parseLong(requireText(e, "id"), "id");
        String name = requireText(e, "name");

        Element coordsEl = requireElement(e, "coordinates");
        long x = parseLong(requireText(coordsEl, "x"), "coordinates.x");
        long y = parseLong(requireText(coordsEl, "y"), "coordinates.y");
        Coordinates coordinates = new Coordinates(x, y);

        ZonedDateTime creationDate = ZonedDateTime.parse(requireText(e, "creationDate"));

        long studentsCount = parseLong(requireText(e, "studentsCount"), "studentsCount");
        int shouldBeExpelled = parseInt(requireText(e, "shouldBeExpelled"), "shouldBeExpelled");

        FormOfEducation formOfEducation = FormOfEducation.valueOf(requireText(e, "formOfEducation"));
        Semester semesterEnum = Semester.valueOf(requireText(e, "semesterEnum"));

        Element adminEl = requireElement(e, "groupAdmin");
        String adminName = requireText(adminEl, "name");
        Long adminWeight = parseLong(requireText(adminEl, "weight"), "groupAdmin.weight");
        String passportID = requireText(adminEl, "passportID");
        Color eyeColor = Color.valueOf(requireText(adminEl, "eyeColor"));

        String hairText = getTextAllowEmpty(adminEl, "hairColor");
        Color hairColor = (hairText == null || hairText.isBlank()) ? null : Color.valueOf(hairText.trim());

        Person groupAdmin = new Person(adminName, adminWeight, passportID, eyeColor, hairColor);

        return new StudyGroup(id, name, coordinates, creationDate,
                studentsCount, shouldBeExpelled, formOfEducation, semesterEnum, groupAdmin);
    }

    private static String escape(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }

    private static Element requireElement(Element parent, String tagName) {
        NodeList list = parent.getElementsByTagName(tagName);
        if (list == null || list.getLength() == 0) {
            throw new IllegalArgumentException("Отсутствует обязательный тег <" + tagName + ">.");
        }
        Node n = list.item(0);
        if (n.getNodeType() != Node.ELEMENT_NODE) {
            throw new IllegalArgumentException("Тег <" + tagName + "> имеет неверный тип.");
        }
        return (Element) n;
    }

    private static String requireText(Element parent, String tagName) {
        NodeList list = parent.getElementsByTagName(tagName);
        if (list == null || list.getLength() == 0) {
            throw new IllegalArgumentException("Отсутствует обязательный тег <" + tagName + ">.");
        }
        String text = list.item(0).getTextContent();
        if (text == null) throw new IllegalArgumentException("Пустое значение тега <" + tagName + ">.");
        String trimmed = text.trim();
        if (trimmed.isEmpty()) throw new IllegalArgumentException("Пустое значение тега <" + tagName + ">.");
        return trimmed;
    }

    private static String getTextAllowEmpty(Element parent, String tagName) {
        NodeList list = parent.getElementsByTagName(tagName);
        if (list == null || list.getLength() == 0) return null;
        return list.item(0).getTextContent();
    }

    private static long parseLong(String s, String field) {
        try {
            return Long.parseLong(s);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Некорректное число long в поле " + field + ": " + s);
        }
    }

    private static int parseInt(String s, String field) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Некорректное число int в поле " + field + ": " + s);
        }
    }

    private static <T> T requireNonNull(T v, String field) {
        if (v == null) throw new IllegalArgumentException("Поле " + field + " не может быть null.");
        return v;
    }
}