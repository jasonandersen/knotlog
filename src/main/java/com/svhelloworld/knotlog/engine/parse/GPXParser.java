package com.svhelloworld.knotlog.engine.parse;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.svhelloworld.knotlog.engine.MessageStreamProcessor;
import com.svhelloworld.knotlog.engine.sources.StreamedSource;
import com.svhelloworld.knotlog.messages.Track;
import com.svhelloworld.knotlog.messages.TrackPoint;
import com.svhelloworld.knotlog.messages.VesselMessage;
import com.svhelloworld.knotlog.messages.WayPoint;

/**
 * Parses GPX files into <tt>VesselMessage</tt> objects.<p />
 * 
 * @author Jason Andersen
 * @since Apr 1, 2010
 */
public class GPXParser extends BaseThreadedParser implements ContentHandler {
    
    /*
     * We're using the SAX parser to parse the GPX XML files. SAX is
     * not the easiest model to use but it's parse-as-you-go model
     * allows us to keep the memory footprint small, even on really
     * large GPX files.
     * 
     * Each parsed element gets put into a stack. When the end of that
     * element is reached, the element and it's child elements on the
     * stack get sent off for assembly. If the element is a VesselMessage
     * the object then gets sent off to the listeners.
     * 
     * The stack algorithm used here was borrowed liberally from
     * <em>Learning Java, 2nd Edition</em> by Patrick Niemeyer and
     * Jonathan Knudson, pgs 691-696.
     */
    
    
    /*
     * element name constants
     */
    private static final String EXTENSIONS = "extensions";
    private static final String TRACK_POINT = "trkpt";
    private static final String TRACK_SEGMENT = "trkseg";
    private static final String TRACK = "trk";
    private static final String WAYPOINT = "wpt";
    
    /**
     * A map of methods in this class used to assemble elements,
     * keyed off the name of the element.
     */
    private static final Map<String, Method> assemblyMethods;
    
    /**
     * Set up the assembly methods map. This map will maintain a relationship
     * between element types and the methods that are responsible for assembling
     * those element types into domain objects.
     */
    static {
        Map<String, Method> out = new HashMap<String, Method>();
        Class<GPXParser> thisClass = GPXParser.class;
        Class<Deque> argClass = Deque.class;
        try {
            out.put(EXTENSIONS, thisClass.getDeclaredMethod("assembleExtensions", argClass));
            out.put(TRACK_POINT, thisClass.getDeclaredMethod("assembleTrackPoint", argClass));
            out.put(TRACK_SEGMENT, thisClass.getDeclaredMethod("assembleTrackSegment", argClass));
            out.put(TRACK, thisClass.getDeclaredMethod("assembleTrack", argClass));
            out.put(WAYPOINT, thisClass.getDeclaredMethod("assembleWaypoint", argClass));
        } catch (Exception e) {
            throw new ParseException(e.getMessage(), e);
        }
        assemblyMethods = Collections.unmodifiableMap(out);
    }
    
    /**
     * Initialize all the external message stream processors.
     */
    private static List<MessageStreamProcessor> initExternalProcessors() {
        List<MessageStreamProcessor> out = new ArrayList<MessageStreamProcessor>();
        /*
         * initialize external message processors
         * for GPX parser:
         *      TrackAssembler
         *      RouteAssembler
         */
        return out;
    }
    
    /**
     * Parsed elements stack
     */
    private final Deque<Object> stack = new LinkedList<Object>();

    /**
     * Constructor
     */
    public GPXParser() {
        super(initExternalProcessors());
    }
    
    /**
     * @see com.svhelloworld.knotlog.engine.parse.BaseThreadedParser#parse()
     */
    @Override
    protected void parse() {
        XMLReader parser = getParser();
        StreamedSource source = getSource();
        InputSource input = new InputSource(source.open());
        try {
            parser.parse(input);
        } catch (Exception e) {
            throw new ParseException(e.getMessage(), e);
        } finally {
            source.close();
        }
    }

    /**
     * @see org.xml.sax.ContentHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    @Override
    public void startElement(String uri, String localName, String qName,
            Attributes atts) throws SAXException {

        //convert arguments to Element object
        Element element = new Element();
        element.name = qName;
        for (int i = 0; i < atts.getLength(); i++) {
            element.attributes.put(atts.getQName(i), atts.getValue(i));
        }

        stack.push(element);
    }
    
    /**
     * @see org.xml.sax.ContentHandler#characters(char[], int, int)
     */
    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        
        //add characters to the top element of the stack
        if (stack.peek() instanceof Element) {
            Element currentElement = (Element)stack.peek();
            String chars = new String(ch, start, length);
            if (!isIgnorableWhitespace(chars)) {
                currentElement.content.append(chars);
            }
        }
    }

    /**
     * @see org.xml.sax.ContentHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        
        if (assembleElementType(qName)) {
            /*
             * if element requires assembly, gather up all the 
             * child elements on the stack, wrap them in a stack
             * and find the proper method to send them to for 
             * assembly.
             */
            Deque<Object> elements = new LinkedList<Object>();
            boolean parentFound = false;
            while (!parentFound) {
                Object current = stack.pop();
                elements.push(current);
                if (current instanceof Element) {
                    parentFound = ((Element)current).name.equals(qName);
                }
            }
            /*
             * find and call assembly method
             */
            Method method = assemblyMethods.get(qName);
            Object result;
            try {
                result = method.invoke(this, elements);
            } catch (Exception e) {
                throw new ParseException(e.getMessage(), e);
            }
            /*
             * if we get a VesselMessage back, send it off for external 
             * processing, otherwise stick it onto the stack.
             */
            if (result instanceof VesselMessage) {
                externalProcessing((VesselMessage)result);
            } else {
                stack.push(result);
            }
        } else {
            /*
             * if the element does not require assembly, just leave it
             * on the stack as an element object.
             */
        }
    }
    
    /**
     * Determines if a string contains only ignorable whitespace.
     * @param string
     * @return true if string only contains ignorable whitespace
     */
    private boolean isIgnorableWhitespace(String string) {
        String test = string.replace("\n", "");
        test = test.trim();
        return test.length() == 0;
    }
    
    /**
     * Assembles extension elements into a map object.
     * @param elements
     * @return a map containing extension data
     */
    @SuppressWarnings("unused")
    private Map<String, String> assembleExtensions(Deque<Object> elements) {
        /*
         * map should retain insertion order, use LinkedHashMap
         */
        Map<String, String> out = new LinkedHashMap<String, String>();
        Object object;
        while (!elements.isEmpty()) {
            object = elements.pop();
            if (object instanceof Element) {
                Element element = (Element)object;
                /*
                 * we don't want the "extensions" element in the hashmap
                 */
                if (!element.name.equals(EXTENSIONS)) {
                    out.put(element.name, element.content.toString());
                }
            }
        }
        return out;
    }
    
    /**
     * Assembles elements into a waypoint.
     * @param elements
     * @return waypoint represented by the elements passed
     */
    @SuppressWarnings("unused")
    private WayPoint assembleWaypoint(Deque<Object> elements) {

        //debuggity
        System.out.println("assembleWayPoint:");
        for (Object element : elements) {
            System.out.println(element.toString());
        }
        
        /*
         * TODO implement
         */
        return null;
    }
    
    /**
     * Assembles elements into a track point object.
     * @param elements
     * @return track point represented by the elements passed
     */
    @SuppressWarnings("unused")
    private TrackPoint assembleTrackPoint(Deque<Object> elements) {
        
        //debuggity
        System.out.println("assembleTrackPoint:");
        for (Object element : elements) {
            System.out.println(element.toString());
        }
        
        /*
         * TODO implement
         */
        return null;
    }
    
    /**
     * Assembles trackpoints into a single track segment.
     * @param elements
     * @return a list of track points
     */
    @SuppressWarnings("unused")
    private List<TrackPoint> assembleTrackSegment(Deque<Object> elements) {

        //debuggity
        System.out.println("assembleTrackSegment:");
        for (Object element : elements) {
            System.out.println(element.toString());
        }
        
        /*
         * TODO implement
         */
        return null;
    }
    
    /**
     * Assembles track points and segments into a single track.
     * @param elements
     * @return a track object
     */
    @SuppressWarnings("unused")
    private Track assembleTrack(Deque<Object> elements) {

        //debuggity
        System.out.println("assembleTrack:");
        for (Object element : elements) {
            System.out.println(element.toString());
        }
        
        /*
         * TODO implement
         */
        return null;
    }
    
    /**
     * Callback once external message processing is complete.
     * @see com.svhelloworld.knotlog.engine.parse.BaseThreadedParser#externalProcessingComplete(com.svhelloworld.knotlog.messages.VesselMessage[])
     */
    @Override
    protected void externalProcessingComplete(VesselMessage... messages) {
        super.throwMessageEvent(messages);
    }
    
    /**
     * Initializes and returns a parser object.
     * @return an XML reader object
     */
    private XMLReader getParser() {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            XMLReader parser = saxParser.getXMLReader();
            parser.setContentHandler(this);
            return parser;
        } catch (Exception e) {
            throw new ParseException(e.getMessage(), e);
        }
    }
    
    /**
     * Determines if an element type requires assembly
     * @param elementType type of element
     * @return true if element type does require assembly
     */
    private boolean assembleElementType(String elementType) {
        return assemblyMethods.containsKey(elementType);
    }
    
    
    /**
     * @see org.xml.sax.ContentHandler#endDocument()
     */
    @Override
    public void endDocument() throws SAXException {
        //noop
    }

    /**
     * @see org.xml.sax.ContentHandler#endPrefixMapping(java.lang.String)
     */
    @Override
    public void endPrefixMapping(String prefix) throws SAXException {
        //noop
    }

    /**
     * @see org.xml.sax.ContentHandler#ignorableWhitespace(char[], int, int)
     */
    @Override
    public void ignorableWhitespace(char[] ch, int start, int length)
            throws SAXException {
        //noop
    }

    /**
     * @see org.xml.sax.ContentHandler#processingInstruction(java.lang.String, java.lang.String)
     */
    @Override
    public void processingInstruction(String target, String data)
            throws SAXException {
        //noop
    }

    /**
     * @see org.xml.sax.ContentHandler#setDocumentLocator(org.xml.sax.Locator)
     */
    @Override
    public void setDocumentLocator(Locator locator) {
        //noop
    }

    /**
     * @see org.xml.sax.ContentHandler#skippedEntity(java.lang.String)
     */
    @Override
    public void skippedEntity(String name) throws SAXException {
        //noop
    }

    /**
     * @see org.xml.sax.ContentHandler#startDocument()
     */
    @Override
    public void startDocument() throws SAXException {
        //noop
    }

    /**
     * @see org.xml.sax.ContentHandler#startPrefixMapping(java.lang.String, java.lang.String)
     */
    @Override
    public void startPrefixMapping(String prefix, String uri)
            throws SAXException {
        //noop    
    }

    /**
     * A simple XML element.
     */
    private class Element {
        /**
         * element name
         */
        String name;
        /**
         * element attributes
         */
        Map<String, String> attributes;
        /**
         * text content of element
         */
        StringBuilder content;

        /**
         * Constructor
         */
        Element() {
            attributes = new HashMap<String, String>();
            content = new StringBuilder();
        }
        
        /**
         * For debugging...
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            StringBuilder out = new StringBuilder(name);
            if (!attributes.isEmpty()) {
                out.append(" attribs [");
                for (String key : attributes.keySet()) {
                    out.append("(" + key);
                    out.append(":");
                    out.append(attributes.get(key) + ")");
                }
                out.append("]");
            }
            if (content.length() > 0) {
                out.append(" content [" + content.toString() + "]");
            }
            return out.toString();
        }
    }

}
