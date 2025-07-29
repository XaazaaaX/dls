package de.dlsa.api.shared;

import java.util.Comparator;

public class AlphanumComparator {

    private AlphanumComparator() {
    }

    public static final Comparator<String> NATURAL_ORDER_CASE_INSENSITIVE = new Comparator<String>() {

        private boolean isDigit(char ch) {
            return ch >= '0' && ch <= '9';
        }

        private String getChunk(String s, int slength, int marker) {
            StringBuilder chunk = new StringBuilder();
            char c = s.charAt(marker);
            chunk.append(c);
            marker++;
            boolean isDigitChunk = isDigit(c);
            while (marker < slength) {
                c = s.charAt(marker);
                if (isDigit(c) != isDigitChunk) break;
                chunk.append(c);
                marker++;
            }
            return chunk.toString();
        }

        @Override
        public int compare(String s1, String s2) {
            s1 = s1.toLowerCase();
            s2 = s2.toLowerCase();

            int thisMarker = 0;
            int thatMarker = 0;
            int s1Length = s1.length();
            int s2Length = s2.length();

            while (thisMarker < s1Length && thatMarker < s2Length) {
                String thisChunk = getChunk(s1, s1Length, thisMarker);
                thisMarker += thisChunk.length();

                String thatChunk = getChunk(s2, s2Length, thatMarker);
                thatMarker += thatChunk.length();

                int result;
                if (isDigit(thisChunk.charAt(0)) && isDigit(thatChunk.charAt(0))) {
                    result = Integer.compare(Integer.parseInt(thisChunk), Integer.parseInt(thatChunk));
                } else {
                    result = thisChunk.compareTo(thatChunk);
                }

                if (result != 0) {
                    return result;
                }
            }

            return Integer.compare(s1Length, s2Length);
        }
    };
}
