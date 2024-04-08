package com.climax.tech.Services.Impl;

import com.climax.tech.Entities.Client;
import com.climax.tech.Services.FileReader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class XmlFileReaderServiceImpl implements FileReader {

    @Override
    public List<Client> readFile(MultipartFile file) {
        List<Client> clients = new ArrayList<>();
        try {
            InputStream inputStream = file.getInputStream();

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            Document doc = dBuilder.parse(inputStream);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("client");

            for (int temp = 0; temp < nodeList.getLength(); temp++) {
                Node node = nodeList.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String nom = element.getElementsByTagName("nom").item(0).getTextContent();
                    String prenom = element.getElementsByTagName("prenom").item(0).getTextContent();
                    int age = Integer.parseInt(element.getElementsByTagName("age").item(0).getTextContent());
                    String profession = element.getElementsByTagName("profession").item(0).getTextContent();
                    int salaire = Integer.parseInt(element.getElementsByTagName("salaire").item(0).getTextContent());

                    clients.add(new Client(nom, prenom, age, profession, salaire));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la lecture du fichier XML", e);
        }

        return clients;
    }
}
