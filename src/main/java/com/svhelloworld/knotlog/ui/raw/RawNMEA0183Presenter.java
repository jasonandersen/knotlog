package com.svhelloworld.knotlog.ui.raw;

import java.util.LinkedList;
import java.util.List;

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

    private final EventBus eventBus;

    private final ObservableList<NMEA0183Sentence> sentences;

    private ListProperty<NMEA0183Sentence> sentencesProperty;

    /**
     * Constructor
     * @param eventBus
     */
    @Autowired
    public RawNMEA0183Presenter(EventBus eventBus) {
        this.eventBus = eventBus;
        this.eventBus.register(this);
        /*
         * FIXME - use Apache Commons CircularFifoQueue as the backing queue for this presenter
         */
        LinkedList<NMEA0183Sentence> backingList = new LinkedList<>();
        sentences = FXCollections.observableList(backingList);
        sentencesProperty = new SimpleListProperty<NMEA0183Sentence>(sentences);
    }

    /**
     * Grab unparsed NMEA0183 sentences off the event bus and stash them in the queue.
     * @param sentence
     */
    @Subscribe
    public void handleRawNMEA0183Sentence(NMEA0183Sentence sentence) {
        UI.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                if (sentences.size() == 0) {
                    sentences.add(sentence);
                } else {
                    sentences.add(0, sentence);
                }
            }
        });
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
        return sentences;
    }

    public ListProperty<NMEA0183Sentence> rawSentencesProperty() {
        return sentencesProperty;
    }

}
