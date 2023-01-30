package hxasjc.ics4ua3v3;

import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.LinkedList;

public class BattleTranscript {
    /**
     * A list of all transcript components. Uses a LinkedList to ensure that the items stay in order.
     */
    private final LinkedList<TranscriptComponent> transcriptComponents = new LinkedList<>();

    /**
     * Adds a new battle transcript component using the specified object
     * @param o Object to create a transcript component for
     */
    public void add(Object o) {
        TranscriptComponent component = new TranscriptComponent(Instant.now(), o);
        System.out.println(component);
        transcriptComponents.add(component);
        //transcriptComponents.add(new TranscriptComponent(Instant.now(), o));
    }

    /**
     * Saves the battle transcript to a txt file. Opens a {@link FileChooser} on the specified Window to get the save location.
     * @param window Window to open the FileChooser on
     */
    public void save(Window window) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Battle Transcript");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

            File file = fileChooser.showSaveDialog(window);

            if (file != null) {
                FileWriter writer = new FileWriter(file);

                for (TranscriptComponent component : transcriptComponents) {
                    writer.append(component.toString());
                    writer.append("\n");
                }

                writer.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * A record that contains a timestamp and a description. Provides a compact way of storing and printing the transcript information
     * @param instant The timestamp at which the action was added to the transcript
     * @param o The object added to the transcript. Can be any type, although it is only used to produce a string.
     */
    private record TranscriptComponent(Instant instant, Object o) {
        @Override
        public String toString() {
            //System.out.println(instant);
            return "[" +
                    Util.DATE_TIME_FORMATTER.format(instant) +
                    "] " +
                    o;
        }
    }
}
