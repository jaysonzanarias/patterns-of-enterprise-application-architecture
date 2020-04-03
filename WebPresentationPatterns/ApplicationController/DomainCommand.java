package WebPresentationPatterns.ApplicationController;

import java.util.Map;

public interface DomainCommand {
    abstract public void run(Map params);
}
