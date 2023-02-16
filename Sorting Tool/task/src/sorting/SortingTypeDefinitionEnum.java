package sorting;

public enum SortingTypeDefinitionEnum {

    natural("natural"),
    byCount("byCount");


    private final String sortingType;

    SortingTypeDefinitionEnum(String sortingType) {
        this.sortingType = sortingType;
    }

    public String getValue() {
        return this.sortingType;
    }

    public static SortingTypeDefinitionEnum valueOfDataType(String val) {

        for (SortingTypeDefinitionEnum value : values()) {
            if (value.sortingType.equalsIgnoreCase(val)) {
                return value;
            }
        }
        return null;
    }//end of method valueOfDataType
}
