package OfflineConcurrencyPatterns.PessimisticOfflineLock;

public interface Command {
    public void init(HttpServletRequest req, HttpServletResponse rsp);
    public void process() throws Exception;
}
