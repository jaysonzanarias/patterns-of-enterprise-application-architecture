package WebPresentationPatterns.TwoStepView.JSPAndCustomTags;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.StringTokenizer;

public class TableTag {
    private String collectionName;
    private String hostName;
    private String columns;

    public void setCollection(String collectionName) {
        this.collectionName = collectionName;
    }

    public void setHost(String hostName) {
        this.hostName = hostName;
    }

    public void setColumns(String columns) {
        this.columns = columns;
    }

    private Object getProperty(Object obj, String property) throws JspException {
        try {
            String methodName =
                "get" + property.substring(0, 1).toUpperCase() + property.substring(1);
            Object result = obj.getClass().getMethod(methodName, null).invoke(obj, null);
            return result;
        } catch (Exception e) {
            throw new JspException("Unable to get property " + property + " from " + obj);
        }
    }

    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            out.print("<table>");
            Collection coll = (Collection) getPropertyFromAttribute(hostName, collectionName);
            Iterator rows = coll.iterator();
            int rowNumber = 0;
            while (rows.hasNext()) {
                out.print("<tr");
                if ((rowNumber++ % 2) == 0) {
                    out.print(" bgcolor = " + HIGHLIGHT_COLOR);
                }
                out.print(">");
                printCells(rows.next());
                out.print("</tr>");
            }
            out.print("</table>");
        } catch (IOException e) {
            throw new JspException("unable to print out");
        }
        return SKIP_BODY;
    }

    private Object getPropertyFromAttribute(String attribute, String property) throws JspException {
        Object hostObject = pageContext.findAttribute(attribute);
        if (hostObject == null) {
            throw new JspException("Attribute " + attribute + " not found.");
        }
        return getProperty(hostObject, property);
    }

    public static final String HIGHLIGHT_COLOR = "'linen'";

    private void printCells(Object obj) throws IOException, JspException {
        JspWriter out = pageContext.getOut();
        for (int i = 0; i < getColumnList().length; i++) {
            out.print("<td>");
            out.print(getProperty(obj, getColumnList()[i]));
            out.print("</td>");
        }
    }

    private String[] getColumnList() {
        StringTokenizer tk = new StringTokenizer(columns, ", ");
        String[] result = new String[tk.countTokens()];
        for (int i = 0; tk.hasMoreTokens(); i++) {
            result[i] = tk.nextToken();
        }
        return result;
    }
}
