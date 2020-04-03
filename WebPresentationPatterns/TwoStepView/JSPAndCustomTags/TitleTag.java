package WebPresentationPatterns.TwoStepView.JSPAndCustomTags;

import java.io.IOException;

public class TitleTag {
    public int doStartTag() throws JspException {
        try {
            pageContext.getOut().print("<H1>");
        } catch (IOException e) {
            throw new JspException("unable to print start");
        }
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {
        try {
            pageContext.getOut().print("</H1>");
        } catch (IOException e) {
            throw new JspException("unable to print end");
        }
        return EVAL_PAGE;
    }
}
