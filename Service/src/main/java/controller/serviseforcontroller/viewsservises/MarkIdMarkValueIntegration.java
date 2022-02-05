package controller.serviseforcontroller.viewsservises;

import helperutils.myexceptionutils.AppValidException;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class MarkIdMarkValueIntegration {
    public static Map<Integer, Integer> doIntegration(String[] marks, String[] markId) throws AppValidException {
        Map<Integer, Integer> markIdMarkValue = new HashMap<>();

        for (int i = 0; i < markId.length; i++) {
            if (marks[i] != null && !marks[i].equals("")) {
                try {
                    int tempMarkId = Integer.parseInt(markId[i]);
                    int tempMarkValue = Integer.parseInt(marks[i]);
                    if (tempMarkValue < 0 || tempMarkValue > 100) throw new IllegalArgumentException();
                    markIdMarkValue.put(tempMarkId, tempMarkValue);
                } catch (IllegalArgumentException e) {
                    log.error(e.getMessage());
                    throw new AppValidException("mark for change not valid");
                }
            }

        }
        return markIdMarkValue;
    }
}
