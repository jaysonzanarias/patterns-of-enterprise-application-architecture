package DistributionPatterns.DataTransferObject.AlbumSample;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class DataTransferObject {
    public Map writeMapReflect() {
        Map result = null;
        try {
            Field[] fields = this.getClass().getDeclaredFields();
            result = new HashMap();
            for (int i = 0; i < fields.length; i++) {
                result.put(fields[i].getName(), fields[i].get(this));
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return result;
    }

    public static TrackDTO readMapReflect(Map arg) {
        TrackDTO result = new TrackDTO();
        try {
            Field[] fields = result.getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                fields[i].set(result, arg.get(fields[i].getName()));
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return result;
    }
}
