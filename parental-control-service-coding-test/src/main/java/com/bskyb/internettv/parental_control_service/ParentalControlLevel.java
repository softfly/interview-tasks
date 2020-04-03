package com.bskyb.internettv.parental_control_service;

public enum ParentalControlLevel {
    U("U"), PG("PG"), _12("12"), _15("15"), _18("18");

    protected static final String ILLEGAL_ARGUMENT_MSG = "No enum constant: ";

    protected String name;

    ParentalControlLevel(String name) {
        this.name = name;
    }

    public static ParentalControlLevel getEnum(String name) {
        /**
         for (ParentalControlLevel c : ParentalControlLevel.values()) {
         if (c.name.equals(name)) {
         return c;
         }
         }
         */
        switch (name) {
            case "U":
                return ParentalControlLevel.U;
            case "PG":
                return ParentalControlLevel.PG;
            case "12":
                return ParentalControlLevel._12;
            case "15":
                return ParentalControlLevel._15;
            case "18":
                return ParentalControlLevel._18;
        }
        throw new java.lang.IllegalArgumentException(new StringBuilder(ILLEGAL_ARGUMENT_MSG.length() + 2)
                .append(ILLEGAL_ARGUMENT_MSG).append(name).toString());
    }


}
