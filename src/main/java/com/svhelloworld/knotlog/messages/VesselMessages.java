package com.svhelloworld.knotlog.messages;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * A list of {@link ValidVesselMessage} objects. Will also track any {@link UnrecognizedMessage}s found
 * during a parsing event.
 */
public class VesselMessages implements List<ValidVesselMessage> {

    private final List<ValidVesselMessage> vesselMessages;

    /**
     * Constructor.
     */
    public VesselMessages() {
        vesselMessages = new LinkedList<ValidVesselMessage>();
    }

    /**
     * @param messageType
     * @return true if this list contains at least one message that is that type.
     */
    public boolean containsMessageType(Class<? extends ValidVesselMessage> messageType) {
        for (ValidVesselMessage message : vesselMessages) {
            if (message.getClass().equals(messageType)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @see java.util.List#add(java.lang.Object)
     * @throws IllegalArgumentException when message is an {@link UnrecognizedMessage} 
     */
    @Override
    public boolean add(ValidVesselMessage message) {
        if (message instanceof UnrecognizedMessage) {
            throw new IllegalArgumentException("cannot accept UnrecognizedMessage types");
        }
        return vesselMessages.add(message);
    }

    /*
     * {@link java.util.List} methods deferred to backing list
     */

    /**
     * @see java.util.List#size()
     */
    @Override
    public int size() {
        return vesselMessages.size();
    }

    /**
     * @see java.util.List#isEmpty()
     */
    @Override
    public boolean isEmpty() {
        return vesselMessages.isEmpty();
    }

    /**
     * @see java.util.List#contains(java.lang.Object)
     */
    @Override
    public boolean contains(Object o) {
        return vesselMessages.contains(o);
    }

    /**
     * @see java.util.List#iterator()
     */
    @Override
    public Iterator<ValidVesselMessage> iterator() {
        return vesselMessages.iterator();
    }

    /**
     * @see java.util.List#toArray()
     */
    @Override
    public Object[] toArray() {
        return vesselMessages.toArray();
    }

    /**
     * @see java.util.List#toArray(java.lang.Object[])
     */
    @Override
    public <T> T[] toArray(T[] a) {
        return vesselMessages.toArray(a);
    }

    /**
     * @see java.util.List#remove(java.lang.Object)
     */
    @Override
    public boolean remove(Object o) {
        return vesselMessages.remove(o);
    }

    /**
     * @see java.util.List#containsAll(java.util.Collection)
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        return vesselMessages.containsAll(c);
    }

    /**
     * @see java.util.List#addAll(java.util.Collection)
     */
    @Override
    public boolean addAll(Collection<? extends ValidVesselMessage> c) {
        throw new UnsupportedOperationException("Currently do not support add all operations.");
    }

    /**
     * @see java.util.List#addAll(int, java.util.Collection)
     */
    @Override
    public boolean addAll(int index, Collection<? extends ValidVesselMessage> c) {
        throw new UnsupportedOperationException("Currently do not support add all operations.");
    }

    /**
     * @see java.util.List#removeAll(java.util.Collection)
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        return vesselMessages.removeAll(c);
    }

    /**
     * @see java.util.List#retainAll(java.util.Collection)
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        return vesselMessages.retainAll(c);
    }

    /**
     * @see java.util.List#clear()
     */
    @Override
    public void clear() {
        vesselMessages.clear();
    }

    /**
     * @see java.util.List#get(int)
     */
    @Override
    public ValidVesselMessage get(int index) {
        return vesselMessages.get(index);
    }

    /**
     * @see java.util.List#set(int, java.lang.Object)
     */
    @Override
    public ValidVesselMessage set(int index, ValidVesselMessage element) {
        return vesselMessages.set(index, element);
    }

    /**
     * @see java.util.List#add(int, java.lang.Object)
     */
    @Override
    public void add(int index, ValidVesselMessage element) {
        vesselMessages.add(index, element);
    }

    /**
     * @see java.util.List#remove(int)
     */
    @Override
    public ValidVesselMessage remove(int index) {
        return vesselMessages.remove(index);
    }

    /**
     * @see java.util.List#indexOf(java.lang.Object)
     */
    @Override
    public int indexOf(Object o) {
        return vesselMessages.indexOf(o);
    }

    /**
     * @see java.util.List#lastIndexOf(java.lang.Object)
     */
    @Override
    public int lastIndexOf(Object o) {
        return vesselMessages.lastIndexOf(o);
    }

    /**
     * @see java.util.List#listIterator()
     */
    @Override
    public ListIterator<ValidVesselMessage> listIterator() {
        return vesselMessages.listIterator();
    }

    /**
     * @see java.util.List#listIterator(int)
     */
    @Override
    public ListIterator<ValidVesselMessage> listIterator(int index) {
        return vesselMessages.listIterator(index);
    }

    /**
     * @see java.util.List#subList(int, int)
     */
    @Override
    public List<ValidVesselMessage> subList(int fromIndex, int toIndex) {
        return vesselMessages.subList(fromIndex, toIndex);
    }

}
