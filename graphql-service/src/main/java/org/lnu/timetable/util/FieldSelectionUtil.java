package org.lnu.timetable.util;

import graphql.schema.DataFetchingFieldSelectionSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.lnu.timetable.constants.ModelConstants.ID;

@Component
public class FieldSelectionUtil {
    public static List<String> getSelectedDbFields(List<String> selectableDbFields, DataFetchingFieldSelectionSet fs) {
        List<String> dbFields = new ArrayList<>();
        dbFields.add(ID);

        selectableDbFields.forEach(declaredField -> {
            if (fs.contains(declaredField)) {
                dbFields.add(declaredField);
            }
        });

        return dbFields;
    }

    public static Set<String> getSelectedDbFieldSet(List<String> selectableDbFields, DataFetchingFieldSelectionSet fs) {
        Set<String> dbFields = new HashSet<>();
        dbFields.add(ID);

        selectableDbFields.forEach(declaredField -> {
            if (fs.contains(declaredField)) {
                dbFields.add(declaredField);
            }
        });

        return dbFields;
    }
}
