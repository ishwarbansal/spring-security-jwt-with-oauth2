package com.bansal.springsecurityjwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class ConfigOAuth2 extends AuthorizationServerConfigurerAdapter{

	private String clientId = "ishwar";
	private String clientSecret = "bansal-secret-key";
	private String privateKey = "-----BEGIN RSA PRIVATE KEY-----\r\n" + 
			"MIIEogIBAAKCAQEA7SdMc/cjqbVTDGKrE7Dfw8kXAlAxL+YDFZ7hlNrMkG7aGzdu\r\n" + 
			"pIIAv2Ji2j1qVNVLZ0Zvx2PXFwEU4XgcVapAHrqG8iYrbl0ibPrqtVOE0SNvLQ2T\r\n" + 
			"hxr/WkdeeXdLbpGh8pP7lufvKZoT5iOwaZGFw+tLUQ3SeMMW2+0MNOdmh+vpIPv/\r\n" + 
			"JdIDzz5ltY9y5P2ONRSXPMZ9F5ZKGkdnePRR9X6eITcMEQDB1Yrx7tbRDViLkP7C\r\n" + 
			"t9Wo7OwVcs+MWuklDeHt1TQC6PDhIDy/zWG+jzlW8yHzP9GSjY3UjheVS0J14kMm\r\n" + 
			"JEBMMZwXGtsPIJurg7rqmaNNXp7f9melP05ChwIDAQABAoIBAACde3tJhtQn3kFH\r\n" + 
			"+D2i1dHCMccVwirno9ZohRlHwAQX5YTwjd7rX2kfrjX2ttHlg76O1jdkMOikXhN/\r\n" + 
			"pkMqn+P0g+kY5D/zCeXSeM1jnBKWzvEE+f79uO/ZA4u5HX757budWqe3jk1DUw3j\r\n" + 
			"xc9gS0KI17csHAGoL+T+Jmv0tbVgjAECitW0KPjWWu6o3o/sDAXHTRLw4rwRfcEf\r\n" + 
			"1irOvA1pPz7b5zQNLYSXrfWOO2qooYcI0LEgJjkxwe12BRCnvNRVLyMyjc6T48Ow\r\n" + 
			"ZtjK0VcMZWSgIzMGFAsbTW0E5AAtjsT9lwB+8Zs7Sl1mWrOdaDFhx4jMD+ZHCxs8\r\n" + 
			"T8pW/JECgYEA+E8WisPrSSj/5WYA/IzIimMneI748RVCZqq3MBL2mhlH1WIScR1g\r\n" + 
			"rSc0IMFokdka/mYv/2rvr+yR5j2VNtR+CfEesr7SGaFO5rn4YJ5UHLHTgGTR/b8p\r\n" + 
			"aY/rVMuKi6E1vz76uAqSaXQCZrUCFYLp/p9Ij+fb2jiSg3mUuhkg27sCgYEA9H/B\r\n" + 
			"jBsh8l75k91oey0gBBU0HlB/usK9kTDupd0ainbedEHuTLbQ6ty5X+HZNNPK0vER\r\n" + 
			"bw9lcnXW/S9Imcd37dR1uQHDyXVEGSbFEhd22Ec0/CuhileUe4kpOgFSR01Zl7HM\r\n" + 
			"9YApNs5SA7APCLii5/CJEYs73AyIxXqwPn9cOaUCgYB5MoEaMg6BoxVj7qnEmyK3\r\n" + 
			"CTaWzZIMegfy/Jh1Sow7IuPA7yF5PJGXcm8rHbvglEtsqIv860EaaSm9a4mNPaJX\r\n" + 
			"T+1f6Wu7PtS4IUVuQ1liuBk3rf4hEFEySSDgPojdJk6Jjj/p3J8iAZG6d9cQkNmN\r\n" + 
			"gkl0lALnPH2m1o0VWHaFSQKBgGPuO4sqI8vOKa/X1LZTkTxZ9j1VpX8NzTEkkMXW\r\n" + 
			"At0JDI7zy5QrPGRh5ppb0s0Wq1VgFkIQRuRraX197+x6bHgWWC9Pm1ghx72H7RAh\r\n" + 
			"6v8my28t3g6PES8Utnbwx1JBORcZIq5MvYua9EZxlRN5c4vc8gjroYGk1dnw9Uv1\r\n" + 
			"/hnJAoGAC3bmhgVIycO1tH3WVkChQ+QxCH1NU+jWdlTqGHOVXwoFVR2nvyB/P68s\r\n" + 
			"8vbS3OOqF1h3EVyn1s1VqCsmK23r3MevobBGrRWltF4MfTIB80l20196ic/+a8oc\r\n" + 
			"BiTwMkoNA/E7UZAvhyVT/RoDEvQ5gwe3GYxceY9rf+afw20H2O0=\r\n" + 
			"-----END RSA PRIVATE KEY-----";
	private String publicKey = "-----BEGIN PUBLIC KEY-----\r\n" + 
			"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA7SdMc/cjqbVTDGKrE7Df\r\n" + 
			"w8kXAlAxL+YDFZ7hlNrMkG7aGzdupIIAv2Ji2j1qVNVLZ0Zvx2PXFwEU4XgcVapA\r\n" + 
			"HrqG8iYrbl0ibPrqtVOE0SNvLQ2Thxr/WkdeeXdLbpGh8pP7lufvKZoT5iOwaZGF\r\n" + 
			"w+tLUQ3SeMMW2+0MNOdmh+vpIPv/JdIDzz5ltY9y5P2ONRSXPMZ9F5ZKGkdnePRR\r\n" + 
			"9X6eITcMEQDB1Yrx7tbRDViLkP7Ct9Wo7OwVcs+MWuklDeHt1TQC6PDhIDy/zWG+\r\n" + 
			"jzlW8yHzP9GSjY3UjheVS0J14kMmJEBMMZwXGtsPIJurg7rqmaNNXp7f9melP05C\r\n" + 
			"hwIDAQAB\r\n" + 
			"-----END PUBLIC KEY-----";

	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Bean
	public JwtAccessTokenConverter tokenEnhancer() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey(privateKey);
		converter.setVerifierKey(publicKey);
		return converter;
	}

	@Bean
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore(tokenEnhancer());
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(authenticationManager).tokenStore(tokenStore())
		.accessTokenConverter(tokenEnhancer());
	}
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
	}
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient(clientId).secret(passwordEncoder.encode(clientSecret)).scopes("read", "write")
		.authorizedGrantTypes("password", "refresh_token").accessTokenValiditySeconds(20000)
		.refreshTokenValiditySeconds(20000);

	}
}
