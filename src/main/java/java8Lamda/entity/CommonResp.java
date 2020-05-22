

package java8Lamda.entity;

public class CommonResp<T> {

	private Provider<T> provider;

	public Provider<T> getProvider() {
		return provider;
	}

	public void setProvider(Provider<T> provider) {
		this.provider = provider;
	}

}
