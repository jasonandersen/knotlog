package com.svhelloworld.knotlog.ui.raw;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.svhelloworld.knotlog.engine.parse.NMEA0183Sentence;
import com.svhelloworld.knotlog.ui.UI;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The presenter for the {@link RawNMEA0183Screen}. This presenter will grab any 
 * unparsed NMEA0183 sentences and make them available to the UI.
 */
@Component
public class RawNMEA0183Presenter {

    private static final int MAX_LIST_SIZE = 50;

    private final EventBus eventBus;

    private final ObservableList<NMEA0183Sentence> sentences;

    private final ListProperty<NMEA0183Sentence> sentencesProperty;

    private final ReadWriteLock lock;

    /**
     * Constructor
     * @param eventBus
     */
    @Autowired
    public RawNMEA0183Presenter(EventBus eventBus) {
        this.eventBus = eventBus;
        this.eventBus.register(this);
        LinkedList<NMEA0183Sentence> backingList = new LinkedList<>();
        sentences = FXCollections.observableList(backingList);
        sentencesProperty = new SimpleListProperty<NMEA0183Sentence>(sentences);
        lock = new ReentrantReadWriteLock();
    }

    /**
     * Grab unparsed NMEA0183 sentences off the event bus and stash them in the queue.
     * @param sentence
     */
    @Subscribe
    public void handleRawNMEA0183Sentence(NMEA0183Sentence sentence) {
        UI.runOnUIThread(new InsertSentenceTask(sentence));
    }

    /**
     * Clears out the list of sentences, useful for testing.
     */
    public void clear() {
        sentences.clear();
    }

    /*
     * Accessor methods
     */

    public List<NMEA0183Sentence> getRawSentences() {
        lock.readLock().lock();
        try {
            return sentences;
        } finally {
            lock.readLock().unlock();
        }
    }

    public ListProperty<NMEA0183Sentence> rawSentencesProperty() {
        lock.readLock().lock();
        try {
            return sentencesProperty;
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * A task to insert a sentence into the list and keep the list pruned in a
     * threadsafe manner.
     */
    private class InsertSentenceTask implements Runnable {

        final NMEA0183Sentence sentence;

        /**
         * Constructor
         * @param sentence
         */
        InsertSentenceTask(NMEA0183Sentence sentence) {
            Validate.notNull(sentence);
            this.sentence = sentence;
        }

        /**
         * @see java.lang.Runnable#run()
         */
        @Override
        public void run() {
            lock.writeLock().lock();
            try {
                insertNewSentence();
                pruneSentences();
            } finally {
                lock.writeLock().unlock();
            }
        }

        /**
         * Inserts a new sentence at the beginning of the list.
         * @param sentence
         */
        private void insertNewSentence() {
            if (sentences.size() == 0) {
                sentences.add(sentence);
            } else {
                sentences.add(0, sentence);
            }
        }

        /**
         * Prunes the list so the list size doesn't exceed a certain size limit.
         */
        private void pruneSentences() {
            while (sentences.size() > MAX_LIST_SIZE) {
                sentences.remove(sentences.size() - 1);
            }
        }
    }

}
