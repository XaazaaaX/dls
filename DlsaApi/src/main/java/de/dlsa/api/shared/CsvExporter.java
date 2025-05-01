package de.dlsa.api.shared;

import de.dlsa.api.responses.EvaluationResponse;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class CsvExporter {

    public void export(List<EvaluationResponse> results, Writer writer) throws IOException {

        // UTF-8 BOM für Excel-Kompatibilität (optional)
        writer.write('\uFEFF');

        // Header
        writer.write("Vorname; Nachname; Monate mit DLS-Pflicht; Benötigte DLS; Geleistete DLS; Kosten Pro nicht geleisteter DLS; Zu Zahlen (in Euro); Bemerkung\n");

        for (EvaluationResponse result : results) {
            writer.write(String.format(
                    "%s;%s;%d;%.1f;%.1f;%.2f;%.2f;%s\n",
                    escape(result.getFirstName()),
                    escape(result.getLastName()),
                    result.getRequiredMonths(),
                    result.getRequiredDls(),
                    result.getAchievedDls(),
                    result.getCostPerDls(),
                    result.getToPay(),
                    escape(result.getComment())
            ));
        }
    }

    private static String escape(String value) {
        if (value == null) return "";
        // Falls Komma oder Anführungszeichen enthalten: escapen
        String escaped = value.replace("\"", "\"\"");
        if (escaped.contains(",") || escaped.contains("\"") || escaped.contains("\n")) {
            return "\"" + escaped + "\"";
        }
        return escaped;
    }
}
