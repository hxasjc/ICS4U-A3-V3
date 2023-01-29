package hxasjc.ics4ua3v3;

import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedList;

public class BattleTranscript {
    private final LinkedList<TranscriptComponent> transcriptComponents = new LinkedList<>();

    public void add(Object o) {
        transcriptComponents.add(new TranscriptComponent(Instant.now(), o));
    }

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
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private record TranscriptComponent(Instant instant, Object o) {
        @Override
        public String toString() {
            //System.out.println(instant);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[");
            stringBuilder.append(Util.DATE_TIME_FORMATTER.format(instant));
            stringBuilder.append("] ");
            stringBuilder.append(o);
            return stringBuilder.toString();
        }
    }
}
