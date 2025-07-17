package de.dlsa.api.shared;

import de.dlsa.api.responses.EvaluationResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 * Dienstkomponente zum Exportieren von {@link EvaluationResponse}-Daten als CSV-Datei.
 * Wird z. B. im Jahreslauf verwendet, um Ergebnisse für den CSV-Download aufzubereiten.
 */
@Component
public class CsvExporter {

    /**
     * Exportiert eine Liste von {@link EvaluationResponse}-Objekten als CSV-Datei.
     *
     * Die Datei wird mit UTF-8 BOM eingeleitet, um insbesondere bei Microsoft Excel die korrekte Anzeige sicherzustellen.
     * Die Felder werden mit Semikolon (;) getrennt.
     *
     * @param results die Liste von Evaluationsergebnissen
     * @param writer  ein {@link Writer}-Objekt, z. B. {@link java.io.OutputStreamWriter} zur HTTP-Antwort
     * @throws IOException wenn ein Fehler beim Schreiben auftritt
     */
    public void export(List<EvaluationResponse> results, Writer writer) throws IOException {
        // UTF-8 BOM für Excel-Kompatibilität (optional)
        writer.write('\uFEFF');

        // Header-Zeile
        writer.write("Vorname; Nachname; Monate mit DLS-Pflicht; Benötigte DLS; Geleistete DLS; Kosten Pro nicht geleisteter DLS; Zu Zahlen (in Euro); Bemerkung\n");

        // Inhalte je Zeile schreiben
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

    /**
     * Escaped einen CSV-Wert, indem Sonderzeichen wie Anführungszeichen doppelt geschrieben werden.
     * Falls der Text Kommas, Anführungszeichen oder Zeilenumbrüche enthält, wird er in doppelte Anführungszeichen gesetzt.
     *
     * @param value der zu escapenede String
     * @return der korrekt formatierte CSV-Wert
     */
    public static String escape(String value) {
        if (value == null) return "";
        String escaped = value.replace("\"", "\"\"");

        if (escaped.contains(",") || escaped.contains("\"") || escaped.contains("\n")) {
            return "\"" + escaped + "\"";
        }

        return escaped;
    }
}
