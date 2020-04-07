package DistributionPatterns.DataTransferObject.AlbumSample;

import java.util.HashMap;
import java.util.Map;

public class TrackDTO {
    public Map writeMap() {
        Map result = new HashMap();
        result.put("title", title);
        result.put("performers", performers);
        return result;
    }

    public static TrackDTO readMap(Map arg) {
        TrackDTO result = new TrackDTO();
        result.title = (String) arg.get("title");
        result.performers = (String[]) arg.get("performers");
        return result;
    }
}
