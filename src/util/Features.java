package util;

import java.util.HashMap;

public class Features {

    private Integer type;
    private double value;
    private static final HashMap<Integer, String> filenames;

    public static final Integer LEXICO = 0, CORPUS = 1, QTD_CITACOES = 2,
            QTD_LINKS = 3, QTD_HASHTAGS = 4, AVG_CITACOES = 5, AVG_LINKS = 6,
            AVG_HASHTAGS = 7;
    public static final int TOTAL = 8;

    static {
        filenames = new HashMap<>();
        filenames.put(LEXICO, "LEXICO");
        filenames.put(CORPUS, "CORPUS");
        filenames.put(QTD_CITACOES, "QTD_CITACOES");
        filenames.put(QTD_LINKS, "QTD_LINKS");
        filenames.put(QTD_HASHTAGS, "QTD_HASHTAGS");
        filenames.put(AVG_CITACOES, "AVG_CITACOES");
        filenames.put(AVG_LINKS, "AVG_LINKS");
        filenames.put(AVG_HASHTAGS, "AVG_HASHTAGS");
    }

    public Features(Integer type, double value) {
        this.type = type;
        this.value = value;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public static String getFilename(Integer type) {
        return filenames.get(type) + FileOperations.EXTENSION;
    }
}
