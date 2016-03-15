package com.svhelloworld.knotlog.engine.parse;

import java.lang.reflect.Constructor;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import com.svhelloworld.knotlog.domain.messages.UnrecognizedMessage;
import com.svhelloworld.knotlog.domain.messages.VesselMessage;
import com.svhelloworld.knotlog.domain.messages.VesselMessageSource;
import com.svhelloworld.knotlog.measure.MeasurementUnit;
import com.svhelloworld.knotlog.util.StringUtil;

/**
 * Creates instrument messages.
 * 
 * @author Jason Andersen
 * @since Feb 24, 2010
 *
 */
@Service
public class InstrumentMessageFactory {

    /**
     * Default package to look into for message classes.
     */
    private static final String DEFAULT_MESSAGE_PKG = "com.svhelloworld.knotlog.domain.messages";

    @Autowired
    private ConversionService conversionService;

    /**
     * Constructs an instrument vessel message based on an instrument
     * message definition and a collection of field values culled
     * from an instrument sentence.
     * @param definition instrument message definition
     * @param fields field values that came in with instrument sentence
     * @param source the source of the instrument message
     * @param timestamp instrument message timestamp
     * @return an instance of a InstrumentMessage
     * @throws NullPointerException when timestamp is null
     * @throws NullPointerException when source is null
     * @throws NullPointerException when definition is null
     * @throws MessageCreationException when the message class when the
     *          specified message class could not be instantiated.
     */
    public VesselMessage createInstrumentMessage(
            final VesselMessageSource source,
            final Instant timestamp,
            final InstrumentMessageDefinition definition,
            final List<String> fields) {

        //validate source, date, and definition
        if (source == null) {
            throw new NullPointerException("source cannot be null");
        }
        if (timestamp == null) {
            throw new NullPointerException("timestamp cannot be null");
        }
        if (definition == null) {
            throw new NullPointerException("definition cannot be null");
        }

        //construct argument array
        List<String> rawArgs = getRawArguments(definition, fields);
        List<Object> arguments = new ArrayList<Object>(rawArgs.size() + 2);
        arguments.add(source);
        arguments.add(timestamp);
        for (String arg : rawArgs) {
            arguments.add(arg);
        }
        //reflect us up an instrument message
        Class<?> msgClass = getMessageClass(definition.getMessageClassName());
        Constructor<?> constructor = getMessageConstructor(msgClass, arguments);
        Object[] finalArgs = coerceArgsToConstructorTypes(constructor, arguments);
        VesselMessage message;

        try {
            //instantiate message
            message = (VesselMessage) constructor.newInstance(finalArgs);
        } catch (Exception e) {
            //if instantation fails, create an unrecognized message
            MessageFailure failureMode = MessageFailure.INVALID_SENTENCE_FIELDS;
            String identifier = definition.getIdentifier();
            rawArgs.add(0, definition.getMessageClassName());
            message = createUnrecognizedMessage(source, timestamp, failureMode,
                    identifier, rawArgs, msgClass, e);
        }
        return message;
    }

    /**
     * Creates an unrecognized message.
     * @param source source of instrument message
     * @param timestamp timestamp of instrument message
     * @param failureMode cause of message failure
     * @param identifier sentence identifier
     * @param sentenceFields sentence fields
     * @param debugInfo any additional debugging info
     * @return unrecognized message
     * @throws NullPointerException if failureMode is null
     */
    public UnrecognizedMessage createUnrecognizedMessage(
            VesselMessageSource source,
            Instant timestamp,
            MessageFailure failureMode,
            String identifier,
            List<String> sentenceFields,
            Object... debugInfo) {

        List<String> fields = sentenceFields;
        if (fields == null) {
            fields = new ArrayList<String>();
        }
        //add identifier to the beginning of the fields list
        fields.add(0, identifier);
        UnrecognizedMessage out = new UnrecognizedMessage(source, timestamp,
                failureMode, fields, debugInfo);
        return out;
    }

    /**
     * Creates an unrecognized message.
     * @param source source of instrument message
     * @param timestamp timestamp of instrument message
     * @param failureMode cause of message failure
     * @param sentenceFields sentence fields
     * @param debugInfo any additional debugging info
     * @return unrecognized message
     * @throws NullPointerException if failureMode is null
     */
    public UnrecognizedMessage createUnrecognizedMessage(
            VesselMessageSource source,
            Instant timestamp,
            MessageFailure failureMode,
            List<String> sentenceFields,
            Object... debugInfo) {

        UnrecognizedMessage out = new UnrecognizedMessage(source, timestamp,
                failureMode, sentenceFields, debugInfo);
        return out;
    }

    /**
     * Creates an argument list of strings from the definition and
     * instrument sentence fields.
     * @param definition
     * @param fields instrument sentence fields
     * @return a list of string arguments for the message constructor, will 
     *          never be null but can be an empty list.
     */
    private List<String> getRawArguments(
            final InstrumentMessageDefinition definition,
            final List<String> fields) {

        List<String> args = new ArrayList<String>();
        for (String parameter : definition.getParameters()) {
            if (StringUtil.isInteger(parameter)) {
                //if parameter is an integer, fetch the indexed field from sentence
                int idx = Integer.parseInt(parameter);
                String field;
                /*
                 * if the field index exists, add it to the arguments, otherwise 
                 * add an empty string
                 */
                if (idx - 1 < fields.size()) {
                    field = fields.get(idx - 1);
                } else {
                    field = "";
                }
                args.add(field);
            } else {
                //pass the string directly in
                args.add(parameter);
            }
        }
        return args;
    }

    /**
     * Resolves a class name to a <tt>Class</tt> object.
     * @param className can be either a simple class name or a fully 
     *          qualified class name. If className is a simple class
     *          name, the default package will be assumed.
     * @return the Class object for this class name
     * @throws MessageCreationException if message class cannot be found
     */
    private Class<?> getMessageClass(final String className) {
        String fullClassName = className;
        if (fullClassName.indexOf(".") < 0) {
            //no class package was specified so use default package
            fullClassName = DEFAULT_MESSAGE_PKG + "." + fullClassName;
        }
        Class<?> msgClass;
        try {
            msgClass = Class.forName(fullClassName);
        } catch (ClassNotFoundException e) {
            throw new MessageCreationException(e);
        }
        return msgClass;
    }

    /**
     * Gets the appropriate constructor the message class.
     * @param messageClass
     * @param arguments
     * @return constructor for message class
     * @throws IllegalArgumentException if no constructor can be found
     *          for the specified class
     */
    private Constructor<?> getMessageConstructor(
            final Class<?> messageClass,
            final List<Object> arguments) {

        /*
         * For now, let's just return the first constructor. At some
         * point, however, we should be evaluating the raw arguments
         * and compare them to each constructors' parameter list.
         */
        Constructor<?>[] constructors = messageClass.getConstructors();
        return constructors[0];
    }

    /**
     * Attempt to convert types in argument array to the types 
     * expected by the constructor.
     * @param constructor class constructor to inspect
     * @param arguments argument array
     */
    private Object[] coerceArgsToConstructorTypes(
            final Constructor<?> constructor,
            final List<Object> arguments) {

        Object[] out = new Object[constructor.getParameterTypes().length];
        int idx = 0;
        for (Class<?> paramType : constructor.getParameterTypes()) {
            Object argument = idx < arguments.size() ? arguments.get(idx) : null;
            if (argument != null && !paramType.equals(argument.getClass())) {
                out[idx] = coerceArgument(argument, paramType);
            } else {
                out[idx] = argument;
            }
            idx++;
        }
        return out;
    }

    /**
     * Converts a single argument to the specified class type.
     * @param argument argument to convert
     * @param paramType class type to convert to
     * @return converted argument, returns the original argument if it
     *          cannot be converted
     */
    @SuppressWarnings("unchecked")
    private Object coerceArgument(
            final Object argument,
            final Class paramType) {

        if (argument == null) {
            return null;
        }

        Object out = argument;
        /*
         * Attempt to coerce argument into the parameter type specified.
         * If we can't change the argument type, just return the original
         * argument.
         */
        try {
            if (float.class.equals(paramType) || Float.class.equals(paramType)) {
                //float values
                out = Float.parseFloat(argument.toString());
            } else if (int.class.equals(paramType) || Integer.class.equals(paramType)) {
                //integer values
                out = Integer.parseInt(argument.toString());
            } else if (double.class.equals(paramType) || Double.class.equals(paramType)) {
                //double values
                out = Double.parseDouble(argument.toString());
            } else if (long.class.equals(paramType) || Long.class.equals(paramType)) {
                //long values
                out = Long.parseLong(argument.toString());
            } else if (String.class.equals(paramType)) {
                //string values
                out = argument.toString();
            } else if (isMeasurementUnit(paramType)) {
                //measurement units
                out = resolveMeasurementUnit(argument.toString(), paramType);
            }
        } catch (Exception e) {
            //we're going to ignore exceptions and just return the original argument
        }

        return out;
    }

    /**
     * Resolves an abbreviation out to a specific measurement unit
     * enum object.
     * @param abbreviation abbreviation to check for
     * @param unitClass class of MeasurementUnit expected to be returned
     * @return a MeasurementUnit enum object resolved to the specified
     *          abbreviation. Will return null if no MeasurementUnit is 
     *          found.
     * @throws IllegalArgumentException when unitClass does not implement
     *          <tt>MeasurementUnit</tt>
     * @throws NullPointerException when unitClass is null
     */
    private MeasurementUnit resolveMeasurementUnit(
            final String abbreviation,
            final Class<? extends MeasurementUnit> unitClass) {

        //validate
        if (abbreviation == null || abbreviation.length() == 0) {
            return null;
        }
        if (unitClass == null) {
            throw new NullPointerException("unit class cannot be null");
        }
        if (!isMeasurementUnit(unitClass)) {
            throw new IllegalArgumentException("unit class does not implement MeasurementUnit: " +
                    unitClass.getCanonicalName());
        }

        MeasurementUnit[] units = unitClass.getEnumConstants();
        for (MeasurementUnit unit : units) {
            //check the name of the enum value
            if (unit instanceof Enum<?>) {
                Enum<?> enumUnit = (Enum<?>) unit;
                if (enumUnit.name().equals(abbreviation)) {
                    return unit;
                }
            }
            //check for abbreviations
            for (String abbr : unit.getAbbreviations()) {
                if (abbr.equals(abbreviation)) {
                    return unit;
                }
            }
        }
        return null;
    }

    /**
     * Determines if a class implements the <tt>MeasurementUnit</tt> interface
     * directly or through any implemented interfaces.
     * @param theClass
     * @return true if this class implements <tt>MeasurementUnit</tt> class.
     */
    private boolean isMeasurementUnit(final Class<?> theClass) {
        for (Class<?> interfaceClass : theClass.getInterfaces()) {
            if (interfaceClass.equals(MeasurementUnit.class)) {
                return true;
            } else if (isMeasurementUnit(interfaceClass)) {
                //check for extended interfaces
                return true;
            }
        }
        return false;
    }

}
