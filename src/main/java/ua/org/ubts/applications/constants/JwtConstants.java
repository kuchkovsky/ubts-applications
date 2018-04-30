package ua.org.ubts.applications.constants;

public final class JwtConstants {

	public static final long EXPIRATION_TIME = 1_209_600_000; // 14 days
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";

	private JwtConstants() {
	}

}
