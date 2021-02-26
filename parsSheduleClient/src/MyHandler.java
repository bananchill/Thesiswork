import Model.GroupData;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class MyHandler extends DefaultHandler {
    private static ArrayList<GroupData> lessonsgroup = new ArrayList<>();

    private String day;
    private String group;
    private String lessons;
    private String podgroup;
    private String name;

    private String auditorium;
    private String numberauditorium;
    public String mygroup = "10-02к";
    private boolean IsMyGroup = false;


    @Override
    public void startDocument() throws SAXException {
        System.out.println("Start Parsing ...");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        String element = qName;
        if (element.equals("Group")) {
            if (attributes.getValue(0).equals(mygroup)) {
                System.out.println(attributes.getValue(0));
                group = attributes.getValue(0);
                IsMyGroup = true;
            } else
                IsMyGroup = false;
        }
        if (IsMyGroup) {
            switch (element) {
                case "Day":
                    System.out.println("Start : " + element + attributes.getValue(0));
                case "Lesson":
                    System.out.println(" " + attributes.getValue(0));
                case "Part":
                    podgroup = attributes.getValue(1);
                case "Auditorium":
                    numberauditorium = attributes.getValue(0);
                    auditorium = attributes.getValue(1);
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (IsMyGroup) {
            name = new String(ch, start, length);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

        String secondElement = qName;

        if (IsMyGroup) {
            if (!name.isEmpty())
                System.out.println(secondElement);



        }
        auditorium = "";
        day = "";
        podgroup = "";
        lessons = "";
        name = "";
        group = "";
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("End Parsing ...");
    }

}
