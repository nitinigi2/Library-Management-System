package com.info.utility;

import com.fasterxml.jackson.core.type.TypeReference;
import com.info.bean.BookType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ParseVendorData {

    Map<String, ArrayList<BookType>> vendorBooks = new LinkedHashMap<>();

    public Map<String, List<BookType>> getVendorsBook() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, List<BookType>> bookData = mapper.readValue(new File(
                "vendorData.json"), new TypeReference<Map<String, List<BookType>>>() {
        });

        return bookData;
    }
}
