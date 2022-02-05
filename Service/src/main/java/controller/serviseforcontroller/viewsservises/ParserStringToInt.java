package controller.serviseforcontroller.viewsservises;


import helperutils.myexceptionutils.AppValidException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ParserStringToInt {
    public static int[] parseArrayString(String[] str) {
        int[] result = new int[str.length];
        for (int i = 0; i < str.length; i++) {
            try {
                result[i] = Integer.parseInt(str[i]);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                throw new AppValidException("parser exception");
            }
        }
        return result;
    }

    public static List<Integer> parseArrayStringToListInteger(String[] str) {
        return Arrays.stream(str)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    public static List<Integer> parseArraySIntegerToListInteger(int[] integers) throws AppValidException {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < integers.length; i++) {
            try {
                result.add(integers[i]);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                throw new AppValidException("parser exception");
            }
        }
        return result;
    }

    public static int simpleParserStringToInt(String str) {
        int result = 0;
        try {
            result = Integer.parseInt(str);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            throw new AppValidException("parser exception");
        }
        return result;
    }
}