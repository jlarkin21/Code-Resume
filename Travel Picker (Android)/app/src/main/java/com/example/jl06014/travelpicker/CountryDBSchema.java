package com.example.jl06014.travelpicker;

/**
 * Created by jl06014 on 3/10/2017.
 */

public class CountryDBSchema {
    public static final class CountryTable {
        public static final String NAME = "countries";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String NAME = "name";
            public static final String DESCRIPTION = "description";
            public static final String PICTURE = "picture";
        }
    }
}
