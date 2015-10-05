package com.svhelloworld.knotlog.engine.messages;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import com.svhelloworld.knotlog.messages.UnrecognizedMessage;
import com.svhelloworld.knotlog.messages.VesselMessage;

/**
 * A list of {@link VesselMessage} objects.
 */
public class VesselMessages implements List<VesselMessage> {

    private final List<VesselMessage> list;

    /**
     * Constructor.
     */
    public VesselMessages() {
        list = new LinkedList<VesselMessage>();
    }

    /**
     * @return true if this list has at least one {@link UnrecognizableMessage}
     */
    public boolean containsUnrecognizedMessage() {
        return containsMessageType(UnrecognizedMessage.class);
    }

    /**
     * @param messageType
     * @return true if this list contains at least one message that is that type.
     */
    public boolean containsMessageType(Class<? extends VesselMessage> messageType) {
        for (VesselMessage message : list) {
            if (message.getClass().equals(messageType)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return a list of all the {@link UnrecognizedMessage}s found in this list, returned
     *      in order of insertion. Will never return null but can return an empty list.
     */
    public List<UnrecognizedMessage> getUnrecognizedMessages() {
        List<UnrecognizedMessage> messages = new LinkedList<UnrecognizedMessage>();
        for (VesselMessage message : list) {
            if (message instanceof UnrecognizedMessage) {
                messages.add((UnrecognizedMessage) message);
            }
        }
        return messages;
    }

    /*
     * {@link java.util.List} methods deferred to backing list
     */

    /**
     * @see java.util.List#size()
     */
    @Override
    public int size() {
        return list.size();
    }

    /**
     * @see java.util.List#isEmpty()
     */
    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * @see java.util.List#contains(java.lang.Object)
     */
    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    /**
     * @see java.util.List#iterator()
     */
    @Override
    public Iterator<VesselMessage> iterator() {
        return list.iterator();
    }

    /**
     * @see java.util.List#toArray()
     */
    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    /**
     * @see java.util.List#toArray(java.lang.Object[])
     */
    @Override
    public <T> T[] toArray(T[] a) {
        return list.toArray(a);
    }

    /**
     * @see java.util.List#add(java.lang.Object)
     */
    @Override
    public boolean add(VesselMessage e) {
        return list.add(e);
    }

    /**
     * @see java.util.List#remove(java.lang.Object)
     */
    @Override
    public boolean remove(Object o) {
        return list.remove(o);
    }

    /**
     * @see java.util.List#containsAll(java.util.Collection)
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        return list.containsAll(c);
    }

    /**
     * @see java.util.List#addAll(java.util.Collection)
     */
    @Override
    public boolean addAll(Collection<? extends VesselMessage> c) {
        return list.addAll(c);
    }

    /**
     * @see java.util.List#addAll(int, java.util.Collection)
     */
    @Override
    public boolean addAll(int index, Collection<? extends VesselMessage> c) {
        return list.addAll(index, c);
    }

    /**
     * @see java.util.List#removeAll(java.util.Collection)
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        return list.removeAll(c);
    }

    /**
     * @see java.util.List#retainAll(java.util.Collection)
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        return list.retainAll(c);
    }

    /**
     * @see java.util.List#clear()
     */
    @Override
    public void clear() {
        list.clear();
    }

    /**
     * @see java.util.List#get(int)
     */
    @Override
    public VesselMessage get(int index) {
        return list.get(index);
    }

    /**
     * @see java.util.List#set(int, java.lang.Object)
     */
    @Override
    public VesselMessage set(int index, VesselMessage element) {
        return list.set(index, element);
    }

    /**
     * @see java.util.List#add(int, java.lang.Object)
     */
    @Override
    public void add(int index, VesselMessage element) {
        list.add(index, element);
    }

    /**
     * @see java.util.List#remove(int)
     */
    @Override
    public VesselMessage remove(int index) {
        return list.remove(index);
    }

    /**
     * @see java.util.List#indexOf(java.lang.Object)
     */
    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    /**
     * @see java.util.List#lastIndexOf(java.lang.Object)
     */
    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    /**
     * @see java.util.List#listIterator()
     */
    @Override
    public ListIterator<VesselMessage> listIterator() {
        return list.listIterator();
    }

    /**
     * @see java.util.List#listIterator(int)
     */
    @Override
    public ListIterator<VesselMessage> listIterator(int index) {
        return list.listIterator(index);
    }

    /**
     * @see java.util.List#subList(int, int)
     */
    @Override
    public List<VesselMessage> subList(int fromIndex, int toIndex) {
        return list.subList(fromIndex, toIndex);
    }

}
