package sorting;

public enum DataTypeEnum {
    WORD("word"),
    LINE("line"),
    LONG("long");

    private final String dataType;

    DataTypeEnum(String s) {
        this.dataType = s;
    }

    public String getDataType() {
        return dataType;
    }//end of method getDataType

    public static DataTypeEnum valueOfDataType(String val) {

        for (DataTypeEnum value : values()) {
            if (value.getDataType().equalsIgnoreCase(val)) {
                return value;
            }
        }
        return null;
    }//end of method valueOfDataType
}
