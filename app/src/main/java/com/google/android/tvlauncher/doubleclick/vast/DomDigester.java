package com.google.android.tvlauncher.doubleclick.vast;

import com.google.android.libraries.stitch.util.Preconditions;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class DomDigester {
    /* access modifiers changed from: private */
    public final List<Object> stack = new ArrayList();
    private final Map<String, List<Rule>> rules = new HashMap();

    public void parse(InputStream inputStream) throws IOException, BadXmlException {
        parseFrom(inputStream, null);
    }

    public void parse(Reader xml) throws IOException, BadXmlException {
        parseFrom(null, xml);
    }

    public void parse(String xml) throws IOException, BadXmlException {
        parse(new StringReader(xml));
    }

    /* access modifiers changed from: package-private */
    public Object peek() {
        Preconditions.checkArgument(!this.stack.isEmpty());
        List<Object> list = this.stack;
        return list.get(list.size() - 1);
    }

    /* access modifiers changed from: package-private */
    public Object belowPeek() {
        boolean z = true;
        if (this.stack.size() <= 1) {
            z = false;
        }
        Preconditions.checkArgument(z);
        List<Object> list = this.stack;
        return list.get(list.size() - 2);
    }

    /* access modifiers changed from: package-private */
    public void push(Object object) {
        this.stack.add(object);
    }

    public void clear() {
        this.stack.clear();
        this.rules.clear();
    }

    /* access modifiers changed from: package-private */
    public void addRule(String path, Rule rule) {
        List<Rule> rulesForPath = this.rules.get(path);
        if (rulesForPath == null) {
            rulesForPath = new ArrayList<>();
            this.rules.put(path, rulesForPath);
        }
        rulesForPath.add(rule);
    }

    /* access modifiers changed from: package-private */
    public void createObjectRule(String path, final Class<?> clazz) {
        addRule(path, new Rule() {
            public void executeBeforeChildren(Element element) {
                DomDigester.this.stack.add(DomDigester.this.newInstance(clazz));
            }

            public void executeAfterChildren(Element element) {
                DomDigester.this.stack.remove(DomDigester.this.stack.size() - 1);
            }
        });
    }

    /* access modifiers changed from: package-private */
    public void collectChildResultIntoArrayListRule(String path, final String fieldName) {
        addRule(path, new Rule() {
            public void executeBeforeChildren(Element element) {
                ((List) ((Map) DomDigester.this.belowPeek()).get(fieldName)).add(DomDigester.this.peek());
            }
        });
    }

    /* access modifiers changed from: package-private */
    public void collectAllChildrenResultIntoArrayFieldRule(String path, final String[] fieldNames) {
        addRule(path, new Rule() {
            public void executeBeforeChildren(Element element) {
                Map<String, List> fieldNameResults = new HashMap<>();
                for (String fieldName : fieldNames) {
                    fieldNameResults.put(fieldName, new ArrayList());
                }
                DomDigester.this.stack.add(fieldNameResults);
            }

            public void executeAfterChildren(Element element) {
                Map<String, List> fieldNameResults = (Map) DomDigester.this.peek();
                for (String fieldName : fieldNameResults.keySet()) {
                    List results = (List) fieldNameResults.get(fieldName);
                    if (!results.isEmpty()) {
                        Object[] fieldNameResultList = (Object[]) Array.newInstance(results.get(0).getClass(), results.size());
                        for (int i = 0; i < results.size(); i++) {
                            fieldNameResultList[i] = results.get(i);
                        }
                        DomDigester domDigester = DomDigester.this;
                        domDigester.setField(domDigester.belowPeek(), fieldName, fieldNameResultList);
                    }
                }
                DomDigester.this.stack.remove(DomDigester.this.stack.size() - 1);
            }
        });
    }

    /* access modifiers changed from: package-private */
    public void setPropertiesRule(String path, final String attributeName, final String fieldName) {
        addRule(path, new Rule() {
            public void executeBeforeChildren(Element element) {
                if (element.getAttribute(attributeName) != null) {
                    DomDigester domDigester = DomDigester.this;
                    domDigester.setField(domDigester.peek(), fieldName, DomDigester.this.getAttributeValue(element, attributeName));
                }
            }
        });
    }

    public void setNodeValueRule(String path, final String fieldName) {
        addRule(path, new Rule() {
            public void executeBeforeChildren(Element element) {
                DomDigester domDigester = DomDigester.this;
                domDigester.setField(domDigester.peek(), fieldName, DomDigester.this.getElementValue(element));
            }
        });
    }

    public void addCallMethod(String path, final String methodName) {
        addRule(path, new Rule() {
            public void executeBeforeChildren(Element element) {
                DomDigester domDigester = DomDigester.this;
                domDigester.findAndInvokeMethod(domDigester.peek(), methodName, DomDigester.this.getElementValue(element));
            }
        });
    }

    /* access modifiers changed from: package-private */
    public void addSetNext(String path, final String methodName) {
        addRule(path, new Rule() {
            public void executeBeforeChildren(Element element) {
                DomDigester domDigester = DomDigester.this;
                domDigester.findAndInvokeMethod(domDigester.stack.get(DomDigester.this.stack.size() - 2), methodName, DomDigester.this.peek());
            }
        });
    }

    /* access modifiers changed from: package-private */
    public String getElementValue(Element element) {
        return element == null ? "" : element.getTextContent().trim();
    }

    /* access modifiers changed from: package-private */
    public String getAttributeValue(Element element, String attributeName) {
        return element.getAttribute(attributeName).trim();
    }

    /* access modifiers changed from: private */
    public void setField(Object target, String fieldName, Object fieldValue) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            Object convertedFieldValue = convertArgumentToType(fieldValue, field.getType());
            if (convertedFieldValue != null) {
                field.set(target, convertedFieldValue);
                return;
            }
        } catch (IllegalAccessException | NoSuchFieldException e) {
        }
        String upperCase = fieldName.substring(0, 1).toUpperCase();
        String substring = fieldName.substring(1);
        StringBuilder sb = new StringBuilder(String.valueOf(upperCase).length() + 3 + String.valueOf(substring).length());
        sb.append("set");
        sb.append(upperCase);
        sb.append(substring);
        findAndInvokeMethod(target, sb.toString(), fieldValue);
    }

    /* access modifiers changed from: private */
    public void findAndInvokeMethod(Object target, String methodName, Object argument) {
        Object convertedArgument;
        Method[] methods = target.getClass().getMethods();
        int length = methods.length;
        int i = 0;
        while (i < length) {
            Method method = methods[i];
            if (!method.getName().equals(methodName) || method.getParameterTypes().length != 1 || (convertedArgument = convertArgumentToType(argument, method.getParameterTypes()[0])) == null) {
                i++;
            } else {
                invokeMethod(method, target, convertedArgument);
                return;
            }
        }
        String valueOf = String.valueOf(target);
        StringBuilder sb = new StringBuilder(String.valueOf(methodName).length() + 26 + String.valueOf(valueOf).length());
        sb.append("Did not find method '");
        sb.append(methodName);
        sb.append("' in ");
        sb.append(valueOf);
        throw new IllegalArgumentException(sb.toString());
    }

    private Object convertArgumentToType(Object argument, Class<?> type) {
        if (type.isInstance(argument)) {
            return argument;
        }
        if (type == Integer.TYPE) {
            int intValue = 0;
            try {
                intValue = Integer.parseInt(argument.toString());
            } catch (NumberFormatException e) {
            }
            return Integer.valueOf(intValue);
        } else if (type == Boolean.TYPE) {
            return Boolean.valueOf(Boolean.parseBoolean(argument.toString()));
        } else {
            if (type.getComponentType() != null) {
                return ((List) argument).toArray();
            }
            return null;
        }
    }

    /* access modifiers changed from: private */
    public Object newInstance(Class<?> clazz) {
        try {
            return clazz.newInstance();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e2) {
            throw new RuntimeException(e2);
        }
    }

    private void invokeMethod(Method method, Object target, Object argument) {
        try {
            method.invoke(target, argument);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e2) {
            throw new RuntimeException(e2);
        }
    }

    private void parseFrom(InputStream inputStream, Reader reader) throws IOException, BadXmlException {
        boolean z = false;
        boolean z2 = inputStream != null;
        if (reader != null) {
            z = true;
        }
        Preconditions.checkArgument(z ^ z2);
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            if (inputStream != null) {
                processDocument(documentBuilder.parse(inputStream));
            } else {
                processDocument(documentBuilder.parse(new InputSource(reader)));
            }
        } catch (ParserConfigurationException | SAXException e) {
            throw new BadXmlException(this, e);
        }
    }

    private void processDocument(Document doc) {
        Element root = doc.getDocumentElement();
        replaceAsteriskWithRoot(root);
        processElement(root, "");
    }

    private void processElement(Element element, String path) {
        String str = path.isEmpty() ? "" : "/";
        String nodeName = element.getNodeName();
        StringBuilder sb = new StringBuilder(String.valueOf(path).length() + String.valueOf(str).length() + String.valueOf(nodeName).length());
        sb.append(path);
        sb.append(str);
        sb.append(nodeName);
        String path2 = sb.toString();
        if (this.rules.get(path2) != null) {
            for (Rule rule : this.rules.get(path2)) {
                rule.executeBeforeChildren(element);
            }
        }
        List<Element> childElements = DomUtils.getChildElements(element);
        for (int i = 0; i < childElements.size(); i++) {
            processElement(childElements.get(i), path2);
        }
        if (this.rules.get(path2) != null) {
            for (Rule rule2 : this.rules.get(path2)) {
                rule2.executeAfterChildren(element);
            }
        }
    }

    private void replaceAsteriskWithRoot(Element root) {
        String rootName = root.getLocalName();
        for (String path : new HashSet<>(this.rules.keySet())) {
            if (path.startsWith("*")) {
                List<Rule> rulesForPath = this.rules.remove(path);
                Map<String, List<Rule>> map = this.rules;
                String valueOf = String.valueOf(rootName);
                String valueOf2 = String.valueOf(path.substring(1));
                map.put(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf), rulesForPath);
            }
        }
    }

    public static abstract class Rule {
        public void executeBeforeChildren(Element element) {
        }

        public void executeAfterChildren(Element element) {
        }
    }

    class BadXmlException extends Exception {
        BadXmlException(DomDigester this$0, Exception e) {
            super(e);
        }
    }
}
