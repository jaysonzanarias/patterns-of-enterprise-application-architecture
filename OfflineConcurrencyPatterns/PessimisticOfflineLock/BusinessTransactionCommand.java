package OfflineConcurrencyPatterns.PessimisticOfflineLock;

abstract class BusinessTransactionCommand implements Command {
    public void init(HttpServletRequest req, HttpServletResponse rsp) {
        this.req = req;
        this.rsp = rsp;
    }

    protected void startNewBusinessTransaction() {
        HttpSession httpSession = getReq().getSession(true);
        AppSession appSession = (AppSession) httpSession.getAttribute(APP_SESSION);
        if (appSession != null) {
            ExclusiveReadLockManager.INSTANCE.relaseAllLocks(appSession.getId());
        }
        appSession =
            new AppSession(getReq().getRemoteUser(), httpSession.getId(), new IdentityMap());
        AppSessionManager.setSession(appSession);
        httpSession.setAttribute(APP_SESSION, appSession);
        httpSession.setAttribute(LOCK_REMOVER, new LockRemover(appSession.getId()));
    }

    protected void continueBusinessTransaction() {
        HttpSession httpSession = getReq().getSession();
        AppSession appSession = (AppSession) httpSession.getAttribute(APP_SESSION);
        AppSessionManager.setSession(appSession);
    }

    protected HttpServletRequest getReq() {
        return req;
    }

    protected HttpServletResponse getRsp() {
        return rsp;
    }
}
