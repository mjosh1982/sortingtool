package sorting;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {

    private static String INPUT_FILE_KEY = "-inputFile";
    private static String OUTPUT_FILE_KEY = "-outputFile";

    public static StringBuffer fileOutput = new StringBuffer();


    public static void main(final String[] args) {

        boolean valueCheckPassed = false;
        String errorMessage = "";
        String inputFile = "";
        String outputFile = "";

        Map<String, String> argsMap = new HashMap<>();
        for (int i = 0; i < args.length; i += 2) {
            String key = args[i];
            try {
                String value = args[i + 1];
                valueCheckPassed = true;
                argsMap.put(key, value);


            } catch (Exception e) {
                errorMessage = TypeWork.MessageEnum.getMessage(key);
                fileOutput.append(errorMessage);
                break;
            }
        }

        inputFile = argsMap.getOrDefault(INPUT_FILE_KEY, "input.txt");
        outputFile = argsMap.getOrDefault(OUTPUT_FILE_KEY, "output.txt");
        File file = new File(outputFile);
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        if (valueCheckPassed) {
            switch (argsMap.getOrDefault("-dataType", "word")) {
                case "long":
                    TypeWork.workAll(argsMap.getOrDefault("-sortingType", "natural"), "numbers", 1L);
                    break;
                case "line":
                    TypeWork.workAll(argsMap.getOrDefault("-sortingType", "natural"), "lines", "lines");
                    break;
                default:
                    TypeWork.workAll(argsMap.getOrDefault("-sortingType", "natural"), "words", "words");
            }

        } else {
            System.out.println(errorMessage);
        }

    }
}


class MapUtil {
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        ValueComparator<K, V> bvc = new ValueComparator<K, V>(map);
        TreeMap<K, V> sorted_map = new TreeMap<K, V>(bvc);
        sorted_map.putAll(map);
        return sorted_map;
    }

}

class ValueComparator<K, V extends Comparable<? super V>> implements Comparator<K> {

    Map<K, V> base;

    public ValueComparator(Map<K, V> base) {
        this.base = base;
    }

    public int compare(K a, K b) {
        int result = (base.get(a).compareTo(base.get(b)));
        if (result == 0) result = 1;
        // returning 0 would merge keys
        return result;
    }
}

class TypeWork {
    static Scanner scanner = new Scanner(System.in);

    public static <T extends Comparable> void workAll(String sort, String type, T t) {

        List<T> arr = new ArrayList<>();
        while (scanner.hasNext()) {
            if ("numbers".equals(type)) {
                arr.add((T) new Integer(Integer.parseInt(scanner.next())));
            } else if ("words".equals(type)) {
                arr.add((T) scanner.next());
            } else {
                arr.add((T) scanner.nextLine());
            }
        }
        Collections.sort(arr);
        String format = String.format("Total %s: %s.", type, arr.size());
        System.out.println(format);
        Main.fileOutput.append(format + "\n");
        // Map for sortedCounts
        LinkedHashMap<T, Integer> dataEntryToCount = new LinkedHashMap<>();

        if ("natural".equals(sort)) {
            String lines = "Sorted data:" + (type.equals("lines") ? "\n" : "");
            System.out.print(lines);
            Main.fileOutput.append(lines + "\n");
            for (T i : arr
            ) {
                String lines1 = " " + i + (type.equals("lines") ? "\n" : "");
                System.out.print(lines1);
                Main.fileOutput.append(lines1 + "\n");
            }
        } else {
            for (T a : arr
            ) {
                dataEntryToCount.put(a, Collections.frequency(arr, a));
            }
            MapUtil.sortByValue(dataEntryToCount).forEach((key, value) -> System.out.println(key + ": " + value + " time(s), " + Math.round((double) value * 100 / arr.size()) + "%"));
        }
    }

    enum MessageEnum {
        DATA_TYPE_MESSAGE("-dataType", "No data type defined!"),
        SORTINGTYPE_MESSAGE("-sortingType", "No sorting type defined!");

        private String type;
        private String message;

        private MessageEnum(String type, String message) {
            this.type = type;
            this.message = message;
        }

        public static String getMessage(String type) {
            if (type.equalsIgnoreCase(DATA_TYPE_MESSAGE.type)) {
                return DATA_TYPE_MESSAGE.message;
            }
            return SORTINGTYPE_MESSAGE.message;
        }

        public static MessageEnum getMessageEnum(String type) {
            if (type.equalsIgnoreCase(DATA_TYPE_MESSAGE.type)) {
                return DATA_TYPE_MESSAGE;
            }
            return SORTINGTYPE_MESSAGE;
        }
    }

}