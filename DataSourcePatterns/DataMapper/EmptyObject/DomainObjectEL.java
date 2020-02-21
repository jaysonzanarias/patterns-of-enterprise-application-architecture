class DomainObjectEL {
	private int state = LOADING;
	private static final int LOADING = 0;
	private static final int ACTIVE = 1;

	public void beActive() {
		state = ACTIVE;
	}

	void assertStateIsLoading() {
		Asssert.isTrue(state == LOADING);
	}
}