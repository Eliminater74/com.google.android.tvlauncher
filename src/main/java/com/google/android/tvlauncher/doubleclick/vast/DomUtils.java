package com.google.android.tvlauncher.doubleclick.vast;

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

class DomUtils {
    DomUtils() {
    }

    static List<Element> getChildElements(Element element) {
        NodeList childNodes = element.getChildNodes();
        List<Element> childElements = new ArrayList<>(childNodes.getLength());
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            if (childNode.getNodeType() == 1) {
                childElements.add((Element) childNode);
            }
        }
        return childElements;
    }

    static Element getFirstChildElement(Element element, String localName) {
        NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            if (childNode.getNodeType() == 1) {
                Element childElement = (Element) childNode;
                if (localName.equals(childElement.getLocalName())) {
                    return childElement;
                }
            }
        }
        return null;
    }
}
